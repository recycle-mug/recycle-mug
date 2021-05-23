<template>
  <div :class="getTheme">
    <header-nav></header-nav>
    <router-link to="/login/member" tag="button">login as member</router-link>
    <router-link to="/login/store" tag="button">login as store</router-link>

    <router-link to="/join/member" tag="button">join as member</router-link>
    <router-link to="/join/store" tag="button">join as store</router-link>

    <travel-map style="width:500px; height:500px; margin:20px"></travel-map>
  </div>
</template>

<script>
import HeaderNav from "../components/HeaderNav";
import TravelMap from "../components/TravelMap";

export default {
  components: { HeaderNav, TravelMap },
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
