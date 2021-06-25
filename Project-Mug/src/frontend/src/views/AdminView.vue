<template>
  <div :class="getTheme">
    <admin-header></admin-header>
    <admin-carousel></admin-carousel>
  </div>
</template>

<script>
import AdminHeader from "../components/AdminHeader.vue";
import AdminCarousel from "../components/AdminCarousel.vue";

export default {
  components: { AdminHeader, AdminCarousel },
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
    height: 100%;
  }
}
</style>
