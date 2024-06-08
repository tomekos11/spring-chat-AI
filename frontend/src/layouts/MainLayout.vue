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

defineOptions({
  name: 'MainLayout'
});

</script>
