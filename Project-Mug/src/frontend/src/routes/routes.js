import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

import MainView from "../views/MainView";

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
  ],
});
