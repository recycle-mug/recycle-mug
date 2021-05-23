<template>
  <div class="header">
    <div class="header-container">
      <div style="width:30px; padding:20px">
        <theme-picker></theme-picker>
      </div>
    </div>
  </div>
</template>

<script>
import ThemePicker from "./ThemePicker.vue";
export default {
  components: {
    ThemePicker,
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
