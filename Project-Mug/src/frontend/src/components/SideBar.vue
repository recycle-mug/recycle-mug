<template>
  <div class="menu" :class="isActive">
    <ul class="menu-categories" v-if="isActive">
      <li class="category-title">전체 카테고리</li>
      <li class="category">카테고리</li>
      <li class="category">카테고리</li>
      <li class="category">카테고리</li>
    </ul>
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
    @media screen and (min-width: 1620px) {
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

        &.active {
          left: 0;
        }

        .menu-categories {
          display: flex;
          flex-direction: column;
          padding: 70px 30px;
          list-style: none;

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
    @media screen and (max-width: 1620px) {
      .menu {
        display: inline-block;
        position: absolute;
        top: 100px;

        .menu-categories {
          list-style: none;
          border-radius: 6px;
          overflow: hidden;

          .category,
          .category-title {
            width: 140px;
            height: 65px;
            line-height: 65px;
            text-align: center;
          }

          .category {
            cursor: pointer;
            background-color: rgba(map-get($map: $theme, $key: "border"), 0.85);
            color: map-get($map: $theme, $key: "text-light");
            transition: background-color 0.2s ease-in-out;
            &:hover {
              background-color: darken(
                $color: rgba(map-get($map: $theme, $key: "border"), 0.85),
                $amount: 10%
              );
              font-weight: bold;
              color: map-get($map: $theme, $key: "text");
            }
            border-bottom: 1px solid map-get($map: $theme, $key: "background");
            &:last-child {
              border-bottom: none;
            }
          }

          .category-title {
            display: none;
          }

          @for $i from 1 through 4 {
            li:nth-child(#{$i}) {
              opacity: 0;
              animation: menu-animation ease-in-out forwards;
              animation-duration: 300ms;
              animation-delay: (300ms / 2) * ($i - 1);

              @keyframes menu-animation {
                from {
                  opacity: 0;
                  transform: rotateX(-180deg);
                }
                to {
                  opacity: 1;
                  transform: rotateX(0deg);
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
