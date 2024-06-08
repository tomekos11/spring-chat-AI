<template>
  <q-header
    elevated
    :class="useUserStore().isUserLogged() ? 'bg-green-7' : 'bg-primary'"
  >
    <q-toolbar>
      <q-btn
        flat
        dense
        round
        icon="menu"
        aria-label="Menu"
        @click="useActionsStore().openCloseDrawer()"
      />

      <q-toolbar-title>
        AI Chat
      </q-toolbar-title>
      {{ useUserStore().data?.name ?? '' }} {{ useUserStore().data?.surname ?? '' }}
      <q-btn
        flat
        dense
        round
        icon="person"
        aria-label="Menu"
        @click="OpenDialogOrMenu()"
      />
    </q-toolbar>
    <q-menu
      v-if="openedMenu"
      anchor="bottom end"
      self="top right"
    >
      <q-list style="min-width: 200px; text-wrap: nowrap;">
        <q-item
          v-close-popup
          clickable
        >
          <q-item-section avatar>
            <q-avatar
              rounded
              color="green-9"
              text-color="white"
              icon="forum"
              size="md"
            />
          </q-item-section>
          <q-item-section>Logi</q-item-section>
        </q-item>
        <q-item
          v-close-popup
          clickable
        >
          <q-item-section avatar>
            <q-avatar
              rounded
              color="green-9"
              text-color="white"
              icon="save"
              size="md"
            />
          </q-item-section>
          <q-item-section>Zapisz konwersacje</q-item-section>
        </q-item>
      </q-list>
    </q-menu>
    <q-dialog
      v-if="!useUserStore().isUserLogged()"
      v-model="openedDialog"
    >
      <login-register />
    </q-dialog>
  </q-header>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import LoginRegister from 'components/LoginRegister.vue';
import { useUserStore } from 'src/stores/userStore';
import { useActionsStore } from 'src/stores/actionsStore';

const openedDialog = ref(false);
const openedMenu = ref(false);

const OpenDialogOrMenu = () => {
  if (useUserStore().isUserLogged()) {
    openedMenu.value = true;
  } else {
    openedDialog.value = true;
  }
};
</script>
