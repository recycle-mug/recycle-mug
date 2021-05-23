<template>
  <div :class="getTheme">
    <header-nav></header-nav>
    <login-form-box :role="role"></login-form-box>
  </div>
</template>

<script>
import HeaderNav from "../components/HeaderNav";
import LoginFormBox from "../components/LoginFormBox";
export default {
  props: ["role"],
  components: { LoginFormBox, HeaderNav },
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
