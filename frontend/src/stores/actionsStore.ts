import { defineStore } from 'pinia';

interface actionsStore {
  isDrawerOpened: boolean
}

export const useActionsStore = defineStore('actions', {
  state: () : actionsStore => ({
    isDrawerOpened: false
  }),
  actions: {
    openCloseDrawer () {
      this.isDrawerOpened = !this.isDrawerOpened;
    }
  }
});
