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
          {{ conversation.name }}
          <q-popup-edit
            v-slot="scope"
            v-model="conversation.name"
            auto-save
            @update:model-value="useUserStore().updateConversation()"
          >
            <q-input
              v-model="scope.value"
              dense
              autofocus
              counter
              @keyup.enter="scope.set"
            >
              <template #append>
                <q-icon name="edit" />
              </template>
            </q-input>
          </q-popup-edit>
        </q-item-section>
        <q-item-label
          caption
          class="text-center"
        >
          <span class="text-primary text-bold">
            {{ conversation.begin_date ? getTime(conversation.begin_date) : 'błąd' }}
          </span><br>
          {{ conversation.begin_date ? getDate(conversation.begin_date) : 'błąd' }}
        </q-item-label>
      </q-item>
    </q-list>
  </q-drawer>
</template>

<script setup lang="ts">
import { useActionsStore } from 'src/stores/actionsStore';
import { useUserStore } from 'src/stores/userStore';
import { getDate, getTime } from 'src/utils/timeHelper';

</script>
