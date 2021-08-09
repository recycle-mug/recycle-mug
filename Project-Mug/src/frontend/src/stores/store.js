import Vuex from "vuex";
import Vue from "vue";
import axios from "axios";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

const resourceHost = "/backend";

const enhanceAccessToken = () => {
  const { accessToken } = localStorage;
  if (!accessToken) return;
  axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
};

enhanceAccessToken();

export default new Vuex.Store({
  state: {
    theme: "theme-light",
    accessToken: null,
  },
  getters: {
    theme: (state) => state.theme,
  },
  mutations: {
    SETSTYLE(state, mode) {
      state.theme = mode;
    },
    LOGIN(state, accessToken) {
      state.accessToken = accessToken;
      localStorage.accessToken = accessToken;
      axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    },
    LOGOUT(state) {
      state.accessToken = null;
      delete localStorage.accessToken;
    },
  },
  actions: {
    SETSTYLE({ commit }) {
      commit("SETSTYLE");
    },
    LOGIN({ commit }, { role, email, password }) {
      const payload = {
        email: email,
        password: password,
      };
      let sendData = axios.create();
      sendData.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
      sendData.defaults.headers.common["Content-Type"] = "application/json;charset=utf-8";

      return sendData
        .post(`${resourceHost}/login/${role}`, payload)
        .then(({ data }) => {
          commit("LOGIN", data.token);
        })
        .catch((error) => {
          throw error;
        });
    },
    LOGOUT({ commit }) {
      commit("LOGOUT");
    },
  },
  plugins: [createPersistedState()],
});
