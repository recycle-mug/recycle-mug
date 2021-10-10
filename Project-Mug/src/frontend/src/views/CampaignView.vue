<template>
  <div :class="getTheme">
    <header-nav></header-nav>
    <div class="content-body">
      <div class="image-container">
        <img src="../assets/images/campaign-image-1.png" alt="" />
        <img src="../assets/images/campaign-image-2.png" alt="" />
      </div>
    </div>
    <footer-nav></footer-nav>
  </div>
</template>

<script>
import HeaderNav from "../components/HeaderNav";
import FooterNav from "../components/FooterNav";

export default {
  components: {
    HeaderNav,
    FooterNav,
  },
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

      .image-container {
        width: 100%;
        max-width: 1020px;
        display: flex;
        flex-direction: column;
        margin: auto;
      }
    }
  }
}
</style>
