<template>
  <q-page
    class="q-py-lg"
    style="height: 500px;"
  >
    <chat-readonly
      v-if="conversation"
      :conversation="conversation"
    />
  </q-page>
</template>

<script setup lang="ts">
import { api } from 'src/boot/axios';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import ChatReadonly from 'components/ChatReadonly.vue';

const router = useRouter();
const conversation = ref(null);

onMounted(() => {
  const slug = router.currentRoute.value.params.slug;
  console.log(slug);
  if (slug && slug.length === 8) {
    api.get(`/api/shares/${slug}`)
      .then(res => {
        conversation.value = res.data.data;
      })
      .catch(err => console.error(err));
  } else {
    router.push('/');
  }
});
</script>
