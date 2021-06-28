<template>
  <div :class="getTheme">
    <admin-header></admin-header>
    <transition appear name="slide-fade" :duration="{ enter: 500 }">
      <admin-carousel></admin-carousel>
    </transition>

    <transition appear name="slide-fade" :duration="{ enter: 500 }">
      <admin-user-table style="transition-delay:0.3s"></admin-user-table>
    </transition>

    <footer-nav></footer-nav>
  </div>
</template>

<script>
import AdminHeader from "../components/AdminHeader.vue";
import AdminCarousel from "../components/AdminCarousel.vue";
import AdminUserTable from "../components/AdminUserTable.vue";
import FooterNav from "../components/FooterNav.vue";

export default {
  components: { AdminHeader, AdminCarousel, AdminUserTable, FooterNav },
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

    .slide-fade-enter-active {
      transition: all 0.8s ease;
    }
    .slide-fade-leave-active {
      transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
    }
    .slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
      transform: translateY(-10px);
      opacity: 0;
    }
  }
}
</style>
