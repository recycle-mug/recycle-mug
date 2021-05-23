import Vuex from "vuex";
import Vue from "vue";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    theme: "theme-light",
  },
  getters: {
    theme: (state) => state.theme,
  },
  mutations: {
    SETSTYLE(state, mode) {
      state.theme = mode;
    },
  },
  actions: {
    SETSTYLE({ commit }) {
      commit("SETSTYLE");
    },
  },
});
