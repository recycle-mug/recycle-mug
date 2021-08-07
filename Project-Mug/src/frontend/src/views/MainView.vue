<template>
  <div :class="getTheme">
    <toast-message v-if="onToast" :status="toastStatus" :msg="toastMessage"></toast-message>
    <header-nav></header-nav>
    <div class="content-body">
      <qr-code-scanner></qr-code-scanner>
      <get-kakao-map v-if="role === 'customer'" @makeToast="onToastMessage"></get-kakao-map>
      <partner-cup-manage
        v-if="role === 'partner'"
        @makeToast="onToastMessage"
      ></partner-cup-manage>
    </div>
    <footer-nav></footer-nav>
  </div>
</template>

<script>
import HeaderNav from "../components/HeaderNav";
import FooterNav from "../components/FooterNav";
import GetKakaoMap from "../components/GetKakaoMap";
import QrCodeScanner from "../components/QrCodeScanner.vue";
import PartnerCupManage from "../components/PartnerCupManage.vue";
import ToastMessage from "../components/ToastMessage.vue";

import axios from "axios";

export default {
  data() {
    return {
      role: "",
      onToast: false,
      toastStatus: "",
      toastMessage: "",
    };
  },
  components: { HeaderNav, FooterNav, GetKakaoMap, QrCodeScanner, PartnerCupManage, ToastMessage },
  methods: {
    getProfile() {
      const path = "/backend/profile";

      const { accessToken } = localStorage;
      if (!accessToken) {
        this.isLogin = false;
        this.username = "";
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
              // this.username = res.data.id;
              // this.profileImg = res.data.profilePicture;
              console.log("res.data :>> ", res.data);
              if (res.data.role === "ROLE_CUSTOMER") {
                this.role = "customer";
              } else if (res.data.role === "ROLE_PARTNER") {
                this.role = "partner";
              } else if (res.data.role === "ROLE_ADMIN") {
                this.role = "admin";
              }
            }
          })
          .catch((error) => {
            console.error(error);
            alert(error);
            localStorage.removeItem("accessToken");
          });
      }
    },
    onToastMessage({ status, msg }) {
      this.toastStatus = status;
      this.toastMessage = msg;
      this.onToast = true;

      setTimeout(() => {
        this.onToast = false;
        this.toastStatus = "";
        this.toastMessage = "";
      }, 5000);
    },
  },
  computed: {
    getTheme() {
      return this.$store.state.theme;
    },
  },
  mounted() {
    if (localStorage.getItem("accessToken")) {
      this.getProfile();
    }
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");
    width: 100%;
    height: 100%;

    .content-body {
      width: 100%;
      margin: auto;
      box-sizing: border-box;
      background-color: map-get($map: $theme, $key: "background");
      color: map-get($map: $theme, $key: "text");
      padding-bottom: 4rem;
    }
  }
}
</style>
