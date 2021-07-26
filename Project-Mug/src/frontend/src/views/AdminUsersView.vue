<template>
  <div :class="getTheme">
    <admin-sidebar v-if="windowWidth >= 1020"></admin-sidebar>

    <div class="page">
      <admin-header></admin-header>
      <div class="row">
        <admin-user-table role="customer"></admin-user-table>
      </div>
      <div class="row">
        <admin-user-table role="partner"></admin-user-table>
      </div>
      <footer-nav></footer-nav>
    </div>
  </div>
</template>

<script>
import AdminHeader from "../components/AdminHeader.vue";
import AdminUserTable from "../components/AdminUserTable.vue";
import FooterNav from "../components/FooterNav.vue";
import AdminSidebar from "../components/AdminSidebar.vue";

export default {
  components: {
    AdminHeader,
    AdminUserTable,
    FooterNav,
    AdminSidebar,
  },
  data() {
    return {
      windowWidth: window.innerWidth,
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
