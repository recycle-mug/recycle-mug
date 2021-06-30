<template>
  <div :class="getTheme">
    <header-nav></header-nav>
    <div class="content-body">
      <login-form-box :role="role"></login-form-box>
    </div>
    <footer-nav></footer-nav>
  </div>
</template>

<script>
import HeaderNav from "../components/HeaderNav";
import LoginFormBox from "../components/LoginFormBox";
import FooterNav from "../components/FooterNav";

export default {
  data() {
    return {
      role: this.$route.query.role,
    };
  },
  components: { LoginFormBox, HeaderNav, FooterNav },
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

    .content-body {
      width: 100%;
      height: 100%;
      margin: auto;
      box-sizing: border-box;
      background-color: map-get($map: $theme, $key: "background");
    }
  }
}
</style>
