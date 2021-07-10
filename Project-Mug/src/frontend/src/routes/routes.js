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

const requireAuth = () => (from, to, next) => {
  const path = "/backend/profile";

  const { accessToken } = localStorage;
  if (!accessToken) {
    alert("로그인 해주세요");
    next({ path: "/" });
  } else {
    const authUser = axios.create({ baseUrl: path });
    authUser.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
    authUser.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    authUser.defaults.headers.common["Access-Control-Allow-Methods"] =
      "GET,POST,PUT,DELETE,OPTIONS";

    authUser.defaults.headers.common["Content-Type"] =
      "application/x-www-form-urlencoded;charset=utf-8";

    authUser
      .get(path)
      .then((res) => {
        if (res.data.error) {
          throw res.data.error;
        } else {
          return next();
        }
      })
      .catch((error) => {
        console.error(error);
        alert("로그인 해주세요");
        next({ path: "/" });
      });
  }
};

const checkAdmin = () => (from, to, next) => {
  const path = "/backend/profile";

  const { accessToken } = localStorage;
  if (!accessToken) {
    alert("로그인 해주세요");
    next({ path: "/" });
  } else {
    const getRole = axios.create({ baseUrl: path });
    getRole.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
    getRole.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    getRole.defaults.headers.common["Access-Control-Allow-Methods"] = "GET,POST,PUT,DELETE,OPTIONS";

    getRole.defaults.headers.common["Content-Type"] =
      "application/x-www-form-urlencoded;charset=utf-8";

    getRole
      .get(path)
      .then((res) => {
        if (res.data.error) {
          throw res.data.error;
        } else {
          if (res.data.role === "ROLE_ADMIN") {
            return next();
          } else {
            throw "권한이 없습니다";
          }
        }
      })
      .catch((error) => {
        console.error(error);
        alert(error);
        next({ path: "/" });
      });
  }
};

export default new Router({
  mode: "hash",
  base: "/recycle-mug/",
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
      beforeEnter: requireAuth(),
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
      beforeEnter: checkAdmin(),
    },
    {
      path: "/adminLogin",
      name: "adminLogin",
      component: AdminLoginView,
      alias: "/admin",
    },
  ],
});
