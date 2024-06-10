<template>
  <q-page
    class="q-py-lg"
    style="height: 500px;"
  >
    <div
      class="full-width"
      style="display: unset;"
    >
      <div
        style="max-width:600px"
        class="q-mx-auto q-px-xs column justify-between full-height no-wrap"
      >
        <div>
          <q-chat-message
            v-for="(userMessage, index) in props.conversation.messages"
            :key="index"
            :avatar="userMessage.role === 'user' ? 'https://cdn.quasar.dev/img/avatar4.jpg' : 'https://cdn.quasar.dev/img/avatar3.jpg'"
            :text="[userMessage.content]"
            text-color="white"
            :bg-color="userMessage.role === 'user' ? 'primary' : 'secondary'"
            :sent="userMessage.role === 'user'"
            class="q-mx-sm"
          />
        </div>
      </div>
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { Conversation } from 'src/types/userStoreType';

const props = defineProps<{
  conversation: Conversation;
}>();

</script>
