<template>
  <div class="menu" :class="isActive">
    <dl class="menu-categories" v-if="isActive">
      <dt class="category-title">전체 카테고리</dt>
      <dd class="category">카테고리</dd>
      <dd class="category">카테고리</dd>
      <dd class="category">카테고리</dd>
    </dl>
  </div>
</template>

<script>
export default {
  props: ["isActive"],
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    .menu {
      position: fixed;
      left: -50%;
      z-index: 998;
      background-color: rgba(map-get($map: $theme, $key: "background"), 0.85);
      max-width: calc((100% - 1140px) / 2);
      width: 50%;
      height: 100%;
      display: flex;
      flex-direction: column;
      transition: 300ms left cubic-bezier(0.77, 0, 0.175, 1);
      box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
      color: map-get($map: $theme, $key: "text");

      @media only screen and (max-width: 600px) {
        width: 250px;
        left: -250px;
        padding: 50px;
      }

      &.active {
        left: 0;
      }

      .menu-categories {
        display: flex;
        flex-direction: column;
        padding: 70px 30px;

        .category-title {
          font-weight: bold;
          font-size: 1.5rem;
          margin: 1rem;
          border-left: 8px solid $main-color;
          padding-left: 1rem;
          padding-top: 0.3rem;
          padding-bottom: 0.3rem;
          border-radius: 5px;
          overflow: hidden;
          user-select: none;
        }

        .category {
          font-size: 1.2rem;
          margin: 1rem;
          cursor: pointer;
          user-select: none;
          color: map-get($map: $theme, $key: "text-light");
        }
      }
    }
  }
}
</style>
