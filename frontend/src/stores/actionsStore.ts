import { defineStore } from 'pinia';

interface actionsStore {
  isDrawerOpened: boolean,
  isLoginDialogOpened: boolean,
  share: {
    isShareDialogOpened: boolean,
    conversationId: number,
    conversationName: string,
    result: {
      id: number,
      slug: string,
      showName: boolean,
      date: number[],
      expireDate: number[] | null,
      users: string[] | null
    }
  }
}

export const useActionsStore = defineStore('actions', {
  state: () : actionsStore => ({
    isDrawerOpened: false,
    isLoginDialogOpened: false,
    share: {
      isShareDialogOpened: false,
      conversationId: -1,
      conversationName: '',
      result: {
        id: -1,
        slug: '',
        showName: false,
        date: [],
        expireDate: null,
        users: null
      }
    }
  }),
  actions: {
    openCloseDrawer () {
      this.isDrawerOpened = !this.isDrawerOpened;
    },
    openLoginDialog () {
      this.isLoginDialogOpened = true;
    },
    setShareDetails (isShareDialogOpened: boolean, conversationId: number, conversationName: string) {
      this.share.isShareDialogOpened = isShareDialogOpened;
      this.share.conversationId = conversationId;
      this.share.conversationName = conversationName;
    }
  }
});
