import { defineStore } from 'pinia';
import { api } from 'src/boot/axios';
import { ChatCompletion } from 'src/types/messageType';

interface Message {
  content: string,
  date: null | number[],
  id?: number,
  role: 'user' | 'assistant',
}

interface Conversation {
  begin_date: null | number[],
  id: number,
  messages: Message[]
}

interface userStoreType {
  loading: boolean,
  wantToSartConversation: boolean,
  data: {
    name: string,
    surname: string,
    role: string,
    email: string,
    phone: string,
  },
  allConversations: Conversation[],
  currentConversation: Conversation
}

export const useUserStore = defineStore('user', {
  state: () : userStoreType => ({
    loading: false,
    wantToSartConversation: false,
    data: {
      name: '',
      surname: '',
      role: '',
      email: '',
      phone: '',
    },
    allConversations: [],
    currentConversation: {
      begin_date: null,
      id: -1,
      messages: [
        {
          content: 'Witaj jestem botem z przyszłości. W czym mogę Ci dzisiaj pomóc?',
          role: 'assistant',
          date: null
        }
      ]
    }
  }),
  actions: {
    logout () {
      api.get('/api/auth/logout', {})
        .then(res => {
          console.log(res);
          this.$reset();
        })
        .catch(err => console.error(err));
    },
    startNewChat () {
      this.currentConversation = {
        begin_date: null,
        id: -1,
        messages: [
          {
            content: 'Witaj jestem botem z przyszłości. W czym mogę Ci dzisiaj pomóc?',
            role: 'assistant',
            date: null
          }
        ]
      };
    },
    sendMessage (message: string) {
      this.loading = true;
      this.currentConversation.messages.push({
        date: this.getTimeNow(),
        role: 'user',
        content: message
      });
      api.post('/api/ask', {
        messages: this.currentConversation.messages,
        conversationId: this.currentConversation.id === -1 ? null : this.currentConversation.id,
        wantToStartConversation: true
      },
      {
        headers: {
          Authorization: 'Basic YWRtaW46cGFzc3dvcmQ='
        }
      })
        .then((res: { data : ChatCompletion }) => {
          this.currentConversation.messages.push(res.data.choices[0].message);
          this.loading = false;
        })
        .catch(err => {
          console.error(err);
          this.loading = false;
        });
    },
    loadConversation (conversationId: number) {
      const found = this.allConversations.find(el => el.id === conversationId);
      if (found) {
        this.currentConversation = found;
      }
    },
    isUserLogged () {
      return this.data.name.length > 0;
    },
    isUserAdmin () {
      return this.data.role === 'ADMIN';
    },
    getDate (date: number[]): string {
      return `${date[2] < 10 ? '0' : ''}${date[2]}-${date[1] < 10 ? '0' : ''}${date[1]}-${date[0]}`;
    },
    getTime (date: number[]): string {
      return `${date[3] < 10 ? '0' : ''}${date[3]}:${date[4] < 10 ? '0' : ''}${date[4]}:${date[5] < 10 ? '0' : ''}${date[5]}`;
    },
    getTimeNow () : number[] {
      const now = new Date();
      const year = now.getFullYear();
      const month = now.getMonth() + 1;
      const day = now.getDate();
      const hours = now.getHours();
      const minutes = now.getMinutes();
      const seconds = now.getSeconds();

      return [year, month, day, hours, minutes, seconds];
    }
  }
});
