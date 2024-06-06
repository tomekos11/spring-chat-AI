import { defineStore } from 'pinia';
import { api } from 'src/boot/axios';
import { ChatCompletion } from 'src/types/messageType';

interface userStoreType {
  loading: boolean,
  name: string,
  surname: string,
  role: string,
  email: string,
  phone: string,
  currentConversation: {role: string, content: string}[]
}

export const useUserStore = defineStore('user', {
  state: () : userStoreType => ({
    loading: false,
    name: '',
    surname: '',
    role: '',
    email: '',
    phone: '',
    currentConversation: []
  }),
  actions: {
    sendMessage (message: string) {
      this.loading = true;
      this.currentConversation.push({
        role: 'user',
        content: message
      });
      api.post('/api/ask', {
        messages: this.currentConversation
      },
      {
        headers: {
          Authorization: 'Basic YWRtaW46cGFzc3dvcmQ='
        }
      })
        .then((res: { data : ChatCompletion }) => {
          this.currentConversation.push(res.data.choices[0].message);
          this.loading = false;
        })
        .catch(err => {
          console.error(err);
          this.loading = false;
        });
    },
    saveConversation () {
      api.post('/api/auth/save-conversation', {
        conversation: this.currentConversation
      },
      {
        headers: {
          Authorization: 'Basic YWRtaW46cGFzc3dvcmQ='
        }
      })
        .then(res => {
          console.log('zapisano', res.data);
        })
        .catch(err => {
          console.error(err);
          this.loading = false;
        });
    },
    isUserLogged () {
      return this.name.length <= 0;
    },
    isUserAdmin () {
      return this.role === 'ADMIN';
    }
  }
});
