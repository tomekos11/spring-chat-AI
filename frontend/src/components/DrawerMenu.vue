<template>
  <q-drawer
    v-model="useActionsStore().isDrawerOpened"
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
      <q-item
        v-for="(conversation, index) in useUserStore().allConversations"
        :key="index"
        clickable
        @click="useUserStore().loadConversation(conversation.id)"
      >
        <q-item-section avatar>
          <q-avatar
            rounded
            text-color="primary"
            :icon="conversation.id === useUserStore().currentConversation.id ? 'play_arrow' : 'none'"
            size="md"
          />
        </q-item-section>
        <q-item-section :class="conversation.id === useUserStore().currentConversation.id ? 'text-bold' :''">
          Konwersacja nr {{ conversation.id }}
        </q-item-section>
        <q-item-label
          caption
          class="text-center"
        >
          <span class="text-primary text-bold">
            {{ conversation.begin_date ? useUserStore().getTime(conversation.begin_date) : 'błąd' }}
          </span><br>
          {{ conversation.begin_date ? useUserStore().getDate(conversation.begin_date) : 'błąd' }}
        </q-item-label>
      </q-item>
    </q-list>
  </q-drawer>
</template>

<script setup lang="ts">
import { useActionsStore } from 'src/stores/actionsStore';
import { useUserStore } from 'src/stores/userStore';

</script>
