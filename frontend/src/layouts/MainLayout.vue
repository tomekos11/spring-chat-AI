<template>
  <q-layout view="lHh Lpr lFf">
    <Header />

    <q-drawer
      v-model="useActionsStore().isDrawerOpened"
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
              @click="useActionsStore().isDrawerOpened = false"
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
import Header from 'components/Header.vue';
import { useActionsStore } from 'src/stores/actionsStore';

defineOptions({
  name: 'MainLayout'
});

</script>
