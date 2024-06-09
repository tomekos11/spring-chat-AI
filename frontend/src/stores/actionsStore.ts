import { defineStore } from 'pinia';

interface actionsStore {
  isDrawerOpened: boolean,
  isLoginDialogOpened: boolean,
  isShareDialogOpened: boolean
}

export const useActionsStore = defineStore('actions', {
  state: () : actionsStore => ({
    isDrawerOpened: false,
    isLoginDialogOpened: false,
    isShareDialogOpened: false
  }),
  actions: {
    openCloseDrawer () {
      this.isDrawerOpened = !this.isDrawerOpened;
    },
    openLoginDialog () {
      this.isLoginDialogOpened = true;
    }
  }
});
