import { defineStore } from 'pinia';
import { api } from 'src/boot/axios';
import { ChatCompletion } from 'src/types/messageType';
import { getTimeNow } from 'src/utils/timeHelper';

interface Message {
  content: string,
  date: null | number[],
  id?: number,
  role: 'user' | 'assistant',
}

interface Conversation {
  begin_date: null | number[],
  id: number,
  messages: Message[],
  name?: string
}

interface userStoreType {
  loading: boolean,
  wantToStartConversation: boolean,
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
    wantToStartConversation: true,
    data: {
      name: '',
      surname: '',
      role: '',
      email: '',
      phone: '',
    },
    allConversations: [],
    currentConversation: {
      begin_date: getTimeNow(),
      id: -1,
      messages: [
        {
          content: 'Witaj jestem botem z przyszłości. W czym mogę Ci dzisiaj pomóc?',
          role: 'assistant',
          date: getTimeNow()
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
    updateConversation () {
      api.patch('/api/conversations', {
        conversationId: this.currentConversation.id,
        name: this.currentConversation.name
      })
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.error(err);
        });
    },
    sendMessage (message: string) {
      this.loading = true;
      this.currentConversation.messages.push({
        date: getTimeNow(),
        role: 'user',
        content: message
      });
      api.post('/api/ask', {
        messages: this.currentConversation.messages,
        conversationId: this.currentConversation.id === -1 ? null : this.currentConversation.id,
        wantToStartConversation: this.wantToStartConversation
      },
      {
        headers: {
          Authorization: 'Basic YWRtaW46cGFzc3dvcmQ='
        }
      })
        .then((res: { data : ChatCompletion }) => {
          this.currentConversation.messages.push(res.data.choices[0].message);
          this.currentConversation.messages[this.currentConversation.messages.length - 1].date = getTimeNow();
          if (res.data.conversation !== '') {
            this.currentConversation.id = res.data.conversation.id;
            this.currentConversation.name = res.data.conversation.title;
            let foundConversation = this.allConversations.find(el => el.id === this.currentConversation.id);
            if (foundConversation) {
              foundConversation = this.currentConversation;
            } else {
              this.allConversations.push(this.currentConversation);
            }
          }

          this.loading = false;
        })
        .catch(err => {
          console.error(err);
          this.loading = false;
        });
    },
    saveConversation () {
      api.post('/api/conversations', this.currentConversation)
        .then(res => console.log(res))
        .catch(err => console.error(err));
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
    }
  }
});
