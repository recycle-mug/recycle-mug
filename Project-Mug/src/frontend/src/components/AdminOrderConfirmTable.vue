<template>
  <div class="confirm-table-container">Admin Confirm Table</div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .confirm-table-container {
      background-color: map-get($map: $theme, $key: "content-background");
      box-shadow: $shadow-light;
      padding: 1rem;
      border-radius: 6px;
      transition: all 0.3s ease-in-out;
      position: relative;
      display: flex;
      flex-direction: column;
      box-sizing: border-box;
      color: map-get($map: $theme, $key: "text-light");
      width: 100%;
      max-width: 1020px;
      margin: 1rem auto;
    }
  }
}
</style>
