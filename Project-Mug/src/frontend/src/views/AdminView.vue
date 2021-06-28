<template>
  <div :class="getTheme">
    <admin-header></admin-header>
    <admin-chart></admin-chart>
    <admin-carousel></admin-carousel>
    <admin-user-table></admin-user-table>
    <footer-nav></footer-nav>
  </div>
</template>

<script>
import AdminHeader from "../components/AdminHeader.vue";
import AdminCarousel from "../components/AdminCarousel.vue";
import AdminChart from "../components/AdminChart.vue";
import AdminUserTable from "../components/AdminUserTable.vue";
import FooterNav from "../components/FooterNav.vue";

export default {
  components: { AdminHeader, AdminCarousel, AdminUserTable, FooterNav, AdminChart },
  computed: {
    getTheme() {
      return this.$store.state.theme;
    },
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
  }
}
</style>
