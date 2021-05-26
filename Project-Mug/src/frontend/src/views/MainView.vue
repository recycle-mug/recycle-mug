<template>
  <div :class="getTheme">
    <header-nav></header-nav>
    <div class="content-body">
      <travel-map style="width:500px; height:500px; margin:20px"></travel-map>
    </div>
    <footer-nav></footer-nav>
  </div>
</template>

<script>
import HeaderNav from "../components/HeaderNav";
import TravelMap from "../components/TravelMap";
import FooterNav from "../components/FooterNav";

export default {
  components: { HeaderNav, TravelMap, FooterNav },
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
