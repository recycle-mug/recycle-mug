<template>
  <div class="table-container">보유 컵 리스트</div>
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

    .table-container {
      width: 100%;
    }
  }
}
</style>
