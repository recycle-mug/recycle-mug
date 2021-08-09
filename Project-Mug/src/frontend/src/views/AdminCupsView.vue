<template>
  <div :class="getTheme">
    <admin-sidebar v-if="windowWidth >= 1020"></admin-sidebar>
    <toast-message v-if="onToast" :status="toastStatus" :msg="toastMessage"></toast-message>

    <div class="page">
      <admin-header></admin-header>

      <div class="row">
        <admin-carousel @makeToast="onToastMessage"></admin-carousel>
      </div>
      <div class="row">
        <admin-order-confirm-table @makeToast="onToastMessage"></admin-order-confirm-table>
      </div>

      <footer-nav></footer-nav>
    </div>
  </div>
</template>

<script>
import AdminHeader from "../components/AdminHeader.vue";
import AdminCarousel from "../components/AdminCarousel.vue";
import FooterNav from "../components/FooterNav.vue";
import AdminSidebar from "../components/AdminSidebar.vue";
import AdminOrderConfirmTable from "../components/AdminOrderConfirmTable.vue";
import ToastMessage from "../components/ToastMessage.vue";

export default {
  components: {
    AdminHeader,
    AdminCarousel,
    FooterNav,
    AdminSidebar,
    AdminOrderConfirmTable,
    ToastMessage,
  },
  data() {
    return {
      windowWidth: window.innerWidth,
      onToast: false,
      toastStatus: "",
      toastMessage: "",
    };
  },
  computed: {
    getTheme() {
      return this.$store.state.theme;
    },
  },
  methods: {
    onResize() {
      this.windowWidth = window.innerWidth;
    },
    onToastMessage({ status, msg }) {
      this.toastStatus = status;
      this.toastMessage = msg;
      this.onToast = true;
    },
  },
  mounted() {
    this.$nextTick(() => {
      window.addEventListener("resize", this.onResize);
    });
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");
    width: 100%;
    min-height: 100%;

    @media screen and (max-width: 1019px) {
      .page {
        position: fixed;
        width: 100%;
        height: 100%;
        overflow: auto;
      }
    }

    @media screen and (min-width: 1020px) {
      .page {
        position: fixed;
        left: 80px;
        width: calc(100% - 80px);
        height: 100%;
        overflow: auto;
      }
    }

    .row {
      margin: auto;
      padding: 1rem;
    }
  }
}
</style>
