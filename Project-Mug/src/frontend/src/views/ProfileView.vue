<template>
  <div :class="getTheme">
    <header-nav></header-nav>
    <div class="content-body">
      <router-link :to="{ name: 'main' }" tag="button">return to main page</router-link>
    </div>
  </div>
</template>

<script>
import HeaderNav from "../components/HeaderNav";

export default {
  components: { HeaderNav },
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
      max-width: 1140px;
      margin: auto;
      box-sizing: border-box;
    }
  }
}
</style>
