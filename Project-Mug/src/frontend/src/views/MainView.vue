<template>
  <div :class="getTheme">
    <toast-message v-if="onToast" :status="toastStatus" :msg="toastMessage"></toast-message>
    <header-nav></header-nav>
    <div class="content-body">
      <customer-cup-state v-if="role === 'customer'" :user="user"></customer-cup-state>
      <qr-scanner v-if="role" :role="role" @makeToast="onToastMessage"></qr-scanner>
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
import axios from "axios";
import HeaderNav from "../components/HeaderNav";
import FooterNav from "../components/FooterNav";
import GetKakaoMap from "../components/GetKakaoMap";
import QrScanner from "../components/QrScanner.vue";
import PartnerCupManage from "../components/PartnerCupManage.vue";
import ToastMessage from "../components/ToastMessage.vue";
import CustomerCupState from "../components/CustomerCupState.vue";

export default {
  data() {
    return {
      role: "",
      onToast: false,
      toastStatus: "",
      toastMessage: "",
      user: null,
    };
  },
  components: {
    HeaderNav,
    FooterNav,
    GetKakaoMap,
    QrScanner,
    PartnerCupManage,
    ToastMessage,
    CustomerCupState,
  },
  methods: {
    getProfile() {
      const path = "/backend/profile";

      const { accessToken } = localStorage;
      if (accessToken) {
        const authUser = axios.create({ baseUrl: path });

        authUser
          .get(path)
          .then((res) => {
            if (res.data.error) {
              throw res.data.error;
            } else {
              this.user = res.data;
              const roleState = res.data.role;

              if (roleState === "ROLE_CUSTOMER") {
                this.role = "customer";
              } else if (roleState === "ROLE_PARTNER") {
                this.role = "partner";
              } else if (roleState === "ROLE_ADMIN") {
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
