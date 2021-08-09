<template>
  <div :class="getTheme">
    <toast-message v-if="onToast" :status="toastStatus" :msg="toastMessage"></toast-message>
    <header-nav></header-nav>
    <div class="content-body">
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
import HeaderNav from "../components/HeaderNav";
import FooterNav from "../components/FooterNav";
import GetKakaoMap from "../components/GetKakaoMap";
import QrScanner from "../components/QrScanner.vue";
import PartnerCupManage from "../components/PartnerCupManage.vue";
import ToastMessage from "../components/ToastMessage.vue";

export default {
  data() {
    return {
      role: "",
      onToast: false,
      toastStatus: "",
      toastMessage: "",
    };
  },
  components: {
    HeaderNav,
    FooterNav,
    GetKakaoMap,
    QrScanner,
    PartnerCupManage,
    ToastMessage,
  },
  methods: {
    getProfile() {
      this.$store.dispatch("GETPROFILE").then(() => {
        const roleState = this.getUser.role;

        if (roleState === "ROLE_CUSTOMER") {
          this.role = "customer";
        } else if (roleState === "ROLE_PARTNER") {
          this.role = "partner";
        } else if (roleState === "ROLE_ADMIN") {
          this.role = "admin";
        }
      });
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
    getUser() {
      return this.$store.state.user;
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
