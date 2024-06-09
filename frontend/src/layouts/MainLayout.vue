<template>
  <q-layout view="lHh Lpr lFf">
    <Header />
    <DrawerMenu />

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup lang="ts">
import Header from 'components/Header.vue';
import DrawerMenu from 'components/DrawerMenu.vue';
import { onMounted } from 'vue';
import { api } from 'src/boot/axios';
import { useUserStore } from 'src/stores/userStore';

defineOptions({
  name: 'MainLayout'
});

onMounted(() => {
  api.get('/api/auth/me', {})
    .then(res => {
      if (res.data.data) {
        useUserStore().data = res.data.data;
        api.get('/api/conversations', {})
          .then(res => { useUserStore().allConversations = res.data.data; })
          .catch(err => { console.log(err); });
      }
    })
    .catch(err => { console.log(err); });
});
</script>
