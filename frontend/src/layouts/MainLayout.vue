<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="toggleLeftDrawer"
        />

        <q-toolbar-title>
          AI Chat
        </q-toolbar-title>
        <q-btn
          flat
          dense
          round
          icon="person"
          aria-label="Menu"
          @click="openedDialog = true"
        />
      </q-toolbar>
      <q-dialog v-model="openedDialog">
        <login-register />
      </q-dialog>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      bordered
    >
      <q-list>
        <q-item-label
          header
        >
          <div class="row justify-between items-center">
            <span>
              Wybierz konwersacje
            </span>
            <q-btn
              flat
              dense
              round
              icon="close"
              @click="leftDrawerOpen = false"
            />
          </div>
        </q-item-label>
        <q-item clickable>
          <q-item-section>
            XD
          </q-item-section>
          <q-item-label caption>
            11.05.2024
          </q-item-label>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import LoginRegister from 'components/LoginRegister.vue';
const openedDialog = ref(false);

defineOptions({
  name: 'MainLayout'
});

const leftDrawerOpen = ref(false);

function toggleLeftDrawer () {
  leftDrawerOpen.value = !leftDrawerOpen.value;
}
</script>
