import Vue from "vue";
import Router from "vue-router";
import axios from "axios";

Vue.use(Router);

import MainView from "../views/MainView";
import JoinView from "../views/JoinView";
import LoginView from "../views/LoginView";
import ProfileView from "../views/ProfileView";
import PromotionView from "../views/PromotionView";
import AboutPageView from "../views/AboutPageView";
import CampaignView from "../views/CampaignView";

import AdminDashboardView from "../views/AdminDashboardView";
import AdminCupsView from "../views/AdminCupsView";
import AdminUsersView from "../views/AdminUsersView";
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
    const authUser = axios.create();
    authUser.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
    authUser.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    authUser.defaults.headers.common["Access-Control-Allow-Methods"] =
      "GET,POST,PUT,DELETE,OPTIONS";

    authUser
      .get(path)
      .then((res) => {
        if (res.data.error) {
          throw res.data.error;
        } else {
          return next();
        }
      })
      .catch(() => {
        alert("세션이 만료되었습니다. 다시 로그인 해주세요");
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
    const getRole = axios.create();
    getRole.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
    getRole.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    getRole.defaults.headers.common["Access-Control-Allow-Methods"] = "GET,POST,PUT,DELETE,OPTIONS";

    getRole.defaults.headers.common["Content-Type"] =
      "application/x-www-form-urlencoded;charset=utf-8";

    getRole
      .get(path)
      .then((res) => {
        console.log("res.data :>> ", res.data);
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

const checkFirstEnter = () => (from, to, next) => {
  const path = "/backend/profile";

  const { accessToken } = localStorage;
  if (!accessToken) {
    next({ path: "/promotion" });
  } else {
    const authUser = axios.create();
    authUser.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
    authUser.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    authUser.defaults.headers.common["Access-Control-Allow-Methods"] =
      "GET,POST,PUT,DELETE,OPTIONS";

    authUser
      .get(path)
      .then((res) => {
        if (res.data.error) {
          throw res.data.error;
        } else {
          return next();
        }
      })
      .catch(() => {
        alert("세션이 만료되었습니다. 다시 로그인 해주세요");
        next({ path: "/promotion" });
      });
  }
};

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/promotion",
      name: "promotion",
      component: PromotionView,
    },
    {
      path: "/",
      name: "main",
      component: MainView,
      beforeEnter: checkFirstEnter(),
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
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
      component: AdminDashboardView,
      name: "admin",
      beforeEnter: checkAdmin(),
    },
    {
      path: "/admin/login",
      name: "admin-login",
      component: AdminLoginView,
      alias: "/admin",
    },
    {
      path: "/admin/users",
      name: "admin-users",
      component: AdminUsersView,
      alias: "/admin",
      beforeEnter: checkAdmin(),
    },
    {
      path: "/admin/cups",
      name: "admin-cups",
      component: AdminCupsView,
      alias: "/admin",
      beforeEnter: checkAdmin(),
    },
    {
      path: "/campaign",
      name: "campaign",
      component: CampaignView,
    },
    {
      path: "/about",
      name: "about",
      component: AboutPageView,
    },
  ],
});
