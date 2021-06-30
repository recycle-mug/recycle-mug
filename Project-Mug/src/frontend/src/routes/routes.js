import Vue from "vue";
import Router from "vue-router";
import axios from "axios";

Vue.use(Router);

import MainView from "../views/MainView";
import JoinView from "../views/JoinView";
import LoginView from "../views/LoginView";
import ProfileView from "../views/ProfileView";
import AdminView from "../views/AdminView";
import AdminLoginView from "../views/AdminLoginView";

import AddressPopup from "../components/AddressPopup";
import KakaoLogin from "../components/KakaoLogin";
import AdminLogin from "../components/AdminLogin";

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
      props: true,
    },
    {
      path: "/join",
      name: "join",
      component: JoinView,
      props: true,
    },
    {
      path: "/profile",
      name: "profile",
      component: ProfileView,
    },
    {
      path: "/address",
      name: "address",
      component: AddressPopup,
    },
    {
      path: "/kakao",
      name: "kakao",
      component: KakaoLogin,
    },
    {
      path: "/admin",
      component: AdminView,
      name: "admin",
    },
    {
      path: "/adminLogin",
      name: "adminLogin",
      component: AdminLoginView,
      alias: "/admin",
    },
  ],
});
