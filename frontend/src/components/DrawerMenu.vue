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
        class="drawer-option"
        @click="useUserStore().loadConversation(conversation.id)"
      >
        <share-dialog />
        <q-item-section avatar>
          <q-avatar
            rounded
            text-color="primary"
            :icon="conversation.id === useUserStore().currentConversation.id ? 'play_arrow' : 'none'"
            size="md"
          />
        </q-item-section>
        <q-item-section :class="conversation.id === useUserStore().currentConversation.id ? 'text-bold' :''">
          <span v-if="conversation.id !== idOfConversationToChangeName">
            {{ conversation.name }}
          </span>
          <q-input
            v-else
            v-model="newName"
            dense
            class="q-mx-xs"
            @blur="hideInputAndSave(conversation.id)"
            @keydown.enter="hideInputAndSave(conversation.id)"
            @click.stop
          >
            <template #append>
              <q-icon
                name="edit"
              />
            </template>
          </q-input>
        </q-item-section>
        <q-item-label
          caption
          class="column"
        >
          <span
            class="text-primary text-bold q-mx-auto"
            style="font-size:11px"
          >
            {{ conversation.begin_date ? getTime(conversation.begin_date) : 'błąd' }}
          </span>
          <span
            style="font-size:10px"
            class="text-gray q-mx-auto"
          >
            {{ conversation.begin_date ? getDate(conversation.begin_date) : 'błąd' }}
          </span>
          <q-btn
            size="sm"
            dense
            class="text-grey q-mx-auto drawer-button"
            icon="more_horiz"
            round
            unelevated
            @click.stop=""
          >
            <q-menu>
              <q-list>
                <q-item
                  dense
                  clickable
                  @click="useUserStore().loadConversation(conversation.id)"
                >
                  <q-item-section avatar>
                    <q-avatar
                      rounded
                      text-color="white"
                      color="dark"
                      icon="download"
                      size="sm"
                      font-size="15px"
                    />
                  </q-item-section>
                  <q-item-section>Wczytaj</q-item-section>
                </q-item>
                <q-item
                  dense
                  clickable
                  @click="showChangeNameInput(conversation.id, conversation.name ?? '')"
                >
                  <q-item-section avatar>
                    <q-avatar
                      rounded
                      text-color="white"
                      color="primary"
                      font-size="15px"
                      icon="edit"
                      size="sm"
                    />
                  </q-item-section>
                  <q-item-section>Zmień nazwe</q-item-section>
                </q-item>
                <q-item
                  v-close-popup
                  dense
                  clickable
                  @click.stop="useActionsStore().isShareDialogOpened = true"
                >
                  <q-item-section avatar>
                    <q-avatar
                      rounded
                      text-color="white"
                      color="green-10"
                      font-size="15px"
                      icon="share"
                      size="sm"
                    />
                  </q-item-section>
                  <q-item-section>Udostępnij</q-item-section>
                </q-item>
                <q-item
                  v-close-popup
                  dense
                  clickable
                  @click.stop="useUserStore().deleteConversation(conversation.id)"
                >
                  <q-item-section avatar>
                    <q-avatar
                      rounded
                      text-color="white"
                      color="red-7"
                      font-size="15px"
                      icon="delete"
                      size="sm"
                    />
                  </q-item-section>
                  <q-item-section>Usuń</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
        </q-item-label>
      </q-item>
    </q-list>
  </q-drawer>
</template>

<script setup lang="ts">
import { useActionsStore } from 'src/stores/actionsStore';
import { useUserStore } from 'src/stores/userStore';
import { getDate, getTime } from 'src/utils/timeHelper';
import { Ref, ref } from 'vue';
import ShareDialog from 'components/ShareDialog.vue';

const newName = ref('');
const idOfConversationToChangeName : Ref<null|number> = ref(null);

const showChangeNameInput = async (conversationId : number, oldName: string) => {
  idOfConversationToChangeName.value = conversationId;
  newName.value = oldName;
};

const hideInputAndSave = (conversationId: number) => {
  idOfConversationToChangeName.value = null;
  useUserStore().updateConversation(conversationId, newName.value);
};

</script>

<style scoped>

.drawer-button {
  opacity: 0;
}

.drawer-option:hover .drawer-button {
  opacity: 100;
}

</style>
