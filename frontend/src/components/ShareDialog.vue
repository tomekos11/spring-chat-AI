<template>
  <q-dialog
    v-model="useActionsStore().share.isShareDialogOpened"
    persistent
    backdrop-filter="blur(4px) saturate(150%)"
  >
    <q-card
      v-if="useActionsStore().share.result.id === -1"
      class="q-px-lg"
    >
      <q-card-section>
        <div
          class="text-h6"
          style="min-width: 300px"
        >
          Udostępnij konwersację
        </div>
      </q-card-section>

      <q-card-section>
        <q-checkbox
          v-model="keepAnonymous"
          label="Utrzymaj anonimowość"
        />
        <q-select
          v-model="usernames"
          :options="usernamesOptions"
          label="Wybierz użytkowników"
          hint="Wybrani w tym polu użytkownicy dostaną powiadomienie oraz dostęp do konwersacji"
          filled
          use-input
          input-debounce="300"
          emit-value
          map-options
          popup-content-class="max-height-3-items"
          multiple
          @filter="filterUsers"
        />
        <div
          v-show="usernames.length > 0"
          class="q-mt-md"
        >
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
          class="q-mt-md"
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
        <q-btn
          flat
          label="Udostępnij"
          color="primary"
          @click="useUserStore().shareConversation(
            useActionsStore().share.conversationId,
            keepAnonymous,
            convertToSend(expireDate),
            usernames
          )"
        />
      </q-card-actions>
    </q-card>
    <q-card v-else>
      <q-card-section>
        <div
          class="text-h6 q-mb-md"
          style="min-width: 300px"
        >
          Udostępniłeś konwersacje!
        </div>
        <div
          style="display: flex;"
          class="items-center justify-between q-mb-sm"
        >
          <div>
            Link
            <a
              v-if="visibility"
              class="text-primary"
              :href="createLink()"
            >
              {{ createLink() }}
            </a>
          </div>
          <div style="display: flex; gap:4px">
            <q-btn
              rounded
              unelevated
              dense
              color="grey"
              size="xs"
              :icon="visibility ? 'visibility' : 'visibility_off'"
              @click="visibility = !visibility"
            />
            <q-btn
              rounded
              unelevated
              dense
              color="grey"
              size="xs"
              icon="content_copy"
              @click="copy()"
            />
          </div>
        </div>
        <div class="text-caption text-grey">
          <q-icon
            name="warning"
          />
          Na link mogą wejść wszyscy którzy posiadają link
        </div>
        <div>
          W szczególności
          <q-list bordered>
            <q-item
              v-for="username in usernames"
              :key="username"
            >
              <q-item-section>{{ username }}</q-item-section>
            </q-item>
          </q-list>
        </div>
      </q-card-section>
      <q-card-actions align="right">
        <q-btn
          v-close-popup
          flat
          icon="close"
          label="Zamknij"
          color="primary"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup lang="ts">
import { Ref, onMounted, ref } from 'vue';
import { useUserStore } from 'src/stores/userStore';
import { useActionsStore } from 'src/stores/actionsStore';
import { api } from 'src/boot/axios';
import { convertToSend } from 'src/utils/timeHelper';
import { Notify, copyToClipboard } from 'quasar';

const visibility = ref(false);

const usernamesList : Ref<string[]> = ref([]);
const usernamesOptions : Ref<string[]> = ref([]);

const keepAnonymous = ref(false);
const usernames : Ref<string[]> = ref([]);

const today = new Date();
const weekLater = new Date(today);
weekLater.setDate(today.getDate() + 7);

const expireDate = ref(weekLater.toISOString().slice(0, 10));

const removeUsername = (usernameIndex : number) => {
  usernames.value.splice(usernameIndex, 1);
};

onMounted(() => {
  api.get('/api/usernames')
    .then(res => {
      usernamesList.value = res.data.data;
      usernamesOptions.value = res.data.data;
    })
    .catch(err => console.error(err));
});

const filterUsers = (val: string, update: (arg0: { (): void; (): void; }) => void) => {
  if (val === '') {
    update(() => {
      usernamesOptions.value = usernamesList.value;
    });
  } else {
    update(() => {
      usernamesOptions.value = usernamesOptions.value.filter(username =>
        username.toLowerCase().includes(val.toLowerCase())
      );
    });
  }
};

const createLink = (): string => {
  return `http://${window.location.hostname}:9000/${useActionsStore().share.result.slug}`;
};

const copy = () => {
  copyToClipboard(createLink());
  Notify.create({
    message: 'Skopiowałeś link do schowka'
  });
};
</script>

<style>
.max-height-3-items {
  max-height: calc(3 * 48px) !important
}
</style>
