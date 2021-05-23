<template>
  <div style="width:100%" @click="onChangeTheme">
    <font-awesome-icon :icon="['fas', 'palette']"></font-awesome-icon>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faPalette } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faPalette);

export default {
  components: { FontAwesomeIcon },
  methods: {
    onChangeTheme() {
      const currentTheme = this.$store.state.theme;
      if (currentTheme === "theme-light") {
        this.$store.commit("SETSTYLE", "theme-dark");
        this.themeMode = this.$store.state.theme;
      } else if (currentTheme === "theme-dark") {
        this.$store.commit("SETSTYLE", "theme-light");
        this.themeMode = this.$store.state.theme;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");
  }
}
</style>
