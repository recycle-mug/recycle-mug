<template>
  <div :class="getTheme">
    <admin-header></admin-header>
  </div>
</template>

<script>
import AdminHeader from "../components/AdminHeader.vue";

export default {
  components: { AdminHeader },
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
