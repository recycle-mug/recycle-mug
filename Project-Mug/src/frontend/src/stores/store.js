import Vuex from "vuex";
import Vue from "vue";
import axios from "axios";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

const resourceHost = "http://localhost:9000";

const enhanceAccessToken = () => {
  const { accessToken } = localStorage;
  if (!accessToken) return;
  axios.defaults.headers.common["Authorization"] = `${accessToken}`;
};

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
    LOGIN(state, { accessToken }) {
      state.accessToken = accessToken;
      localStorage.accessToken = accessToken;
      console.log("state.accessToken :", state.accessToken);
      axios.defaults.headers.common["Authorization"] = `${accessToken}`;
    },
    LOGOUT(state) {
      state.accessToken = null;
      delete localStorage.accessToken;
      console.log("state.accessToken :", state.accessToken);
    },
  },
  actions: {
    SETSTYLE({ commit }) {
      commit("SETSTYLE");
    },
    LOGIN({ commit }, { email, password }) {
      return axios.post(`${resourceHost}/login`, { email, password }).then(({ data }) => {
        commit("LOGIN", data);
        axios.defaults.headers.common["Authorization"] = `${data.accessToken}`;
      });
    },
    LOGOUT({ commit }) {
      commit("LOGOUT");
    },
  },
  plugins: [createPersistedState()],
});
