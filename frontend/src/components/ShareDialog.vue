<template>
  <q-dialog
    v-model="useActionsStore().isShareDialogOpened"
    persistent
  >
    <q-card>
      <q-card-section>
        <div class="text-h6">
          Udostępnij konwersację
        </div>
      </q-card-section>

      <q-card-section>
        <q-checkbox
          v-model="keepAnonymous"
          label="Utrzymaj anonimowość"
        />
        <q-input
          v-model="newUsername"
          label="Dodaj użytkownika"
          @keyup.enter="addUsername"
        />
        <div class="q-mt-md">
          <q-list bordered>
            <q-item
              v-for="(username, usernameIndex) in usernames"
              :key="username"
            >
              <q-item-section>{{ username }}</q-item-section>
              <q-item-section side>
                <q-btn
                  flat
                  icon="delete"
                  @click="removeUsername(usernameIndex)"
                />
              </q-item-section>
            </q-item>
          </q-list>
        </div>
        <q-input
          v-model="expireDate"
          label="Data końca udostępniania"
          type="date"
        />
      </q-card-section>

      <q-card-actions align="right">
        <q-btn
          v-close-popup
          flat
          label="Anuluj"
          color="primary"
        />
        <!-- @click="useUserStore().shareConversation()" -->
        <q-btn
          flat
          label="Udostępnij"
          color="primary"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup lang="ts">
import { Ref, ref } from 'vue';
import { useUserStore } from 'src/stores/userStore';
import { useActionsStore } from 'src/stores/actionsStore';

const keepAnonymous = ref(false);
const newUsername = ref('');
const usernames : Ref<string[]> = ref([]);
const expireDate = ref('');

const addUsername = (newUsername : string) => {
  usernames.value.push(newUsername);
};

const removeUsername = (usernameIndex : number) => {
  usernames.value.splice(usernameIndex, 1);
};

</script>
