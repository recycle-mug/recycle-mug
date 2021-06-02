<template>
  <div :class="getTheme">
    <header-nav></header-nav>
    <div class="content-body">
      <get-kakao-map></get-kakao-map>
    </div>
    <footer-nav></footer-nav>
  </div>
</template>

<script>
import HeaderNav from "../components/HeaderNav";
import FooterNav from "../components/FooterNav";
import GetKakaoMap from "../components/GetKakaoMap";

export default {
  components: { HeaderNav, FooterNav, GetKakaoMap },
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
      margin: auto;
      box-sizing: border-box;
      background-color: map-get($map: $theme, $key: "background");
      color: map-get($map: $theme, $key: "text");
      padding-bottom: 4rem;
    }
  }
}
</style>
