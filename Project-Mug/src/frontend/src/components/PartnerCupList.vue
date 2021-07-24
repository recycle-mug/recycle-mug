<template>
  <div class="cup-container">
    <carousel
      :perPage="2"
      :mouse-drag="true"
      :auto-play="false"
      :paginationEnabled="true"
      :paginationPadding="5"
      :paginationSize="8"
      :navigationEnabled="true"
      :navigationClickTargetSize="16"
      class="cup-carousel"
    >
      <slide class="cup-carousel-item">
        <div class="cup-card">
          <img src="../assets/test-images/cup-a.png" alt="" />
          <div class="description">
            <p class="cup-name">Cup A</p>
            <p>수량 : 50개</p>
            <p>가격 : 1,000원</p>
          </div>
        </div>
      </slide>

      <slide class="cup-carousel-item">
        <div class="cup-card">
          <img src="../assets/test-images/cup-a.png" alt="" />
          <div class="description">
            <p class="cup-name">Cup A</p>
            <p>수량 : 50개</p>
            <p>가격 : 1,000원</p>
          </div>
        </div>
      </slide>

      <slide class="cup-carousel-item">
        <div class="cup-card">
          <img src="../assets/test-images/cup-a.png" alt="" />
          <div class="description">
            <p class="cup-name">Cup A</p>
            <p>수량 : 50개</p>
            <p>가격 : 1,000원</p>
          </div>
        </div>
      </slide>
    </carousel>

    <div class="cup-table-wrapper"></div>
  </div>
</template>

<script>
import { Carousel, Slide } from "vue-carousel";
export default {
  components: {
    Carousel,
    Slide,
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .cup-container {
      width: 100%;
      padding: 1rem;
      max-width: 90%;
      display: flex;
      justify-content: center;
      margin: auto;

      .cup-carousel {
        max-width: 90%;
        width: 100%;

        .cup-carousel-item {
          width: 100%;
          padding: 1rem;
          cursor: pointer;

          .cup-card {
            width: 100%;
            border: 1px solid map-get($map: $theme, $key: "border");
            border-radius: 6px;
            padding: 1rem;
            display: flex;

            img {
              width: 150px;
            }

            .description {
              width: 200px;
              padding: 1rem;
              line-height: 1.5rem;
              margin-left: 1rem;

              p {
                font-size: 1.2rem;
              }

              .cup-name {
                font-weight: bold;
                font-size: 1.3rem;
                margin-bottom: 1rem;
              }
            }
          }
        }
      }
    }
  }
}
</style>
