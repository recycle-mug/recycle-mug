<template>
  <div class="admin-sidebar"></div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme,"name")} {
    @media screen and (max-width: 1019px) {
      .admin-sidebar {
        background-color: map-get($map: $theme, $key: "content-background");
        position: relative;
        width: 100%;
        height: 2rem;
        box-sizing: border-box;

        &::after {
          content: "";
          position: absolute;
          top: 0;
          border: 11px solid transparent;
          border-bottom-color: map-get($map: $theme, $key: "content-background");
          border-top: 0;
          margin-left: -11px;
          margin-top: -11px;
        }
      }
    }
    @media screen and (min-width: 1020px) {
      .admin-sidebar {
        background-color: map-get($map: $theme, $key: "content-background");
        position: fixed;
        top: 0;
        left: 0;
        height: 100vh;
        width: 80px;
        border-right: 1px solid map-get($map: $theme, $key: "border");
        z-index: 99;

        &:hover {
          transition: all 0.2s linear;
          width: 360px;
        }
      }
    }
  }
}
</style>
