<template>
  <div :class="getTheme">
    <router-link :to="{ name: 'adminLogin' }"><button></button></router-link>
  </div>
</template>

<script>
export default {
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
