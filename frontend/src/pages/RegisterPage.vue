<template>
  <q-page class="row items-center justify-center bg-amber">
    <q-card
      class="q-mt-md q-pb-none"
      style="max-width: 600px;"
    >
      <q-card-section>
        <q-tabs
          v-model="tab"
          class="text-primary"
        >
          <q-tab
            name="login"
            label="Logowanie"
          />
          <q-tab
            name="register"
            label="Rejestracja"
          />
        </q-tabs>

        <q-tab-panels v-model="tab">
          <q-tab-panel name="login">
            <q-form
              class="column"
              style="gap:4px"
              @submit="login"
            >
              <q-input
                v-model="loginUsername"
                label="Nazwa użytkownika"
                outlined
                dense
              >
                <template #prepend>
                  <q-icon name="person" />
                </template>
              </q-input>
              <q-input
                v-model="loginPassword"
                label="Hasło"
                type="password"
                outlined
                dense
              >
                <template #prepend>
                  <q-icon name="lock" />
                </template>
              </q-input>
              <q-btn
                type="submit"
                label="Zaloguj się"
                color="primary"
              />
            </q-form>
          </q-tab-panel>

          <q-tab-panel name="register">
            <q-form
              class="column"
              style="gap:4px"
              @submit="register"
            >
              <q-input
                v-model="registerUsername"
                label="Nazwa użytkownika"
                outlined
                dense
              >
                <template #prepend>
                  <q-icon name="person" />
                </template>
              </q-input>
              <q-input
                v-model="registerPassword"
                label="Hasło"
                type="password"
                outlined
                dense
              >
                <template #prepend>
                  <q-icon name="lock" />
                </template>
              </q-input>
              <q-input
                v-model="registerName"
                label="Imie"
                outlined
                dense
              >
                <template #prepend>
                  <q-icon name="badge" />
                </template>
              </q-input>
              <q-input
                v-model="registerSurname"
                label="Nazwisko"
                outlined
                dense
              >
                <template #prepend>
                  <q-icon name="badge" />
                </template>
              </q-input>
              <q-input
                v-model="registerEmail"
                type="email"
                label="Email"
                outlined
                dense
              >
                <template #prepend>
                  <q-icon name="mail" />
                </template>
              </q-input>
              <q-input
                v-model="registerPhone"
                label="Telefon"
                outlined
                dense
                mask="### ### ###"
              >
                <template #prepend>
                  <q-icon name="phone" />
                </template>
              </q-input>
              <q-btn
                type="submit"
                label="Zarejestruj się"
                color="primary"
                class="q-mt-sm"
              />
            </q-form>
          </q-tab-panel>
        </q-tab-panels>
      </q-card-section>
      <q-card-section
        v-if="response.error"
        class="text-negative"
        style="padding: 0 32px"
      >
        {{ response.error }}
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup>
import { Notify } from 'quasar';
import { api } from 'src/boot/axios';
import { ref } from 'vue';

const tab = ref('login');

const loginUsername = ref('');
const loginPassword = ref('');

const registerUsername = ref('');
const registerPassword = ref('');
const registerName = ref('');
const registerSurname = ref('');
const registerEmail = ref('');
const registerPhone = ref('');

const response = ref({});

const login = () => {
  response.value = {};
  api.post('/api/auth/login', {
    username: loginUsername.value,
    password: loginPassword.value
  })
    .then(res => {
      response.value = res.data;
      Notify.create({
        message: res.data.message,
        color: res.data.success ? 'green-7' : 'red-7'
      });
    // Handle successful login, maybe redirect to another page
    })
    .catch(err => {
      console.log(err);
    });
};

const register = () => {
  response.value = {};
  api.post('/api/auth/register', {
    username: registerUsername.value,
    password: registerPassword.value
  })
    .then(res => {
      response.value = res.data;
      Notify.create({
        message: res.data.message,
        color: res.data.success ? 'green-7' : 'red-7'
      });
    // Handle successful registration, maybe redirect to another page
    })
    .catch(err => {
      console.log(err);
    });
};
</script>
