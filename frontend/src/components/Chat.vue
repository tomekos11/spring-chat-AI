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
            v-for="(userMessage, index) in useUserStore().currentConversation.messages"
            :key="index"
            :avatar="userMessage.role === 'user' ? 'https://cdn.quasar.dev/img/avatar1.jpg' : 'https://cdn.quasar.dev/img/avatar5.jpg'"
            :text="[userMessage.content]"
            text-color="white"
            :bg-color="userMessage.role === 'user' ? 'primary' : 'secondary'"
            :sent="userMessage.role === 'user'"
            class="q-mx-sm"
          />
        </div>
        <div style="position: sticky; bottom: 10px; z-index: 300;">
          <div
            class="text-caption text-grey cursor-pointer text-center"
            @click="useUserStore().startNewChat()"
          >
            Zacznij nowy czat
          </div>
          <q-input
            v-model="message"
            class="q-mt-auto bg-grey-2"
            input-style="min-height: 60px;"
            outlined
            autogrow
            @keydown.enter="sendMessage()"
          >
            <template #append>
              <div class="column justify-end full-width q-mt-auto">
                <q-btn
                  v-if="useUserStore().currentConversation.messages.length > 0"
                  flat
                  rounded
                  dense
                  color="dark"
                  icon="save"
                  @click="useUserStore().saveConversation()"
                />
                <q-btn
                  flat
                  rounded
                  dense
                  color="dark"
                  icon="send"
                  class="q-mb-sm"
                  @click="sendMessage()"
                />
              </div>
            </template>
          </q-input>
        </div>
        <q-inner-loading :showing="useUserStore().loading">
          <q-spinner-hourglass
            color="dark"
            size="xl"
          />
          Oczekiwanie na odpowiedź
        </q-inner-loading>
      </div>
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { useActionsStore } from 'src/stores/actionsStore';
import { useUserStore } from 'src/stores/userStore';
import { ref, watch } from 'vue';

const message = ref('');

const sendMessage = () => {
  if (!useUserStore().isUserLogged()) {
    useActionsStore().openLoginDialog();
  } else {
    useUserStore().sendMessage(message.value);
    message.value = '';
  }
};

watch(() => useUserStore().loading, (newVal) => {
  if (!newVal) {
    message.value = '';
  }
});
</script>
