import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

import MainView from "../views/MainView";
import JoinView from "../views/JoinView";
import LoginView from "../views/LoginView";

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/login/:role",
      name: "login",
      component: LoginView,
      props: true,
    },
    {
      path: "/join/:role",
      name: "join",
      component: JoinView,
      props: true,
    },
  ],
});
