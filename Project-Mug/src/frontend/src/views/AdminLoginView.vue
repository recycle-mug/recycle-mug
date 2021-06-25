<template>
  <div :class="getTheme">
    <admin-login></admin-login>
  </div>
</template>

<script>
import AdminLogin from "../components/AdminLogin.vue";

export default {
  components: {
    AdminLogin,
  },
  props: ["login"],
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

    div {
      margin: auto;
    }
  }
}
</style>
