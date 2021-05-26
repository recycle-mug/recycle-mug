<template>
  <div :class="getTheme">
    <header-nav></header-nav>
    <div class="content-body">
      <join-form-box :role="role"></join-form-box>
    </div>
  </div>
</template>

<script>
import HeaderNav from "../components/HeaderNav";
import JoinFormBox from "../components/JoinFormBox";

export default {
  props: ["role"],
  components: { JoinFormBox, HeaderNav },
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
