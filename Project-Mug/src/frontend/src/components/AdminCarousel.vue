<template>
  <div class="cup-carousel-card">
    <div class="card-inner-box">
      <div class="box-title">
        <h3>Cups</h3>
      </div>
      <div class="carousel-wrapper">
        <carousel
          :perPageCustom="[
            [310, 1],
            [670, 2],
            [1199, 3],
          ]"
          :mouse-drag="true"
          :auto-play="false"
          :paginationPadding="5"
          :paginationSize="8"
          class="cup-carousel"
        >
          <slide class="cup-carousel-item">
            <div class="item-wrapper">
              <div class="item-title">Cup A</div>
              <div class="item-image"><img src="../assets/test-images/cup-a.png" alt="" /></div>
              <div class="item-description">
                <div class="description-row">
                  <p>가격</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>수량</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(개)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>price</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
              </div>
            </div>
          </slide>

          <slide class="cup-carousel-item">
            <div class="item-wrapper">
              <div class="item-title">Cup B</div>
              <div class="item-image"><img src="../assets/test-images/cup-b.png" alt="" /></div>
              <div class="item-description">
                <div class="description-row">
                  <p>가격</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>수량</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(개)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>price</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
              </div>
            </div>
          </slide>

          <slide class="cup-carousel-item">
            <div class="item-wrapper">
              <div class="item-title">Cup C</div>
              <div class="item-image"><img src="../assets/test-images/cup-c.png" alt="" /></div>
              <div class="item-description">
                <div class="description-row">
                  <p>가격</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>수량</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(개)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>price</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
              </div>
            </div>
          </slide>

          <slide class="cup-carousel-item">
            <div class="item-wrapper">
              <div class="item-title">Cup D</div>
              <div class="item-image"><img src="../assets/test-images/cup-d.png" alt="" /></div>
              <div class="item-description">
                <div class="description-row">
                  <p>가격</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>수량</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(개)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>price</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
              </div>
            </div>
          </slide>

          <slide class="cup-carousel-item">
            <div class="item-wrapper">
              <div class="item-title">Cup E</div>
              <div class="item-image"><img src="../assets/test-images/cup-e.png" alt="" /></div>
              <div class="item-description">
                <div class="description-row">
                  <p>가격</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>수량</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(개)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>price</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
              </div>
            </div>
          </slide>

          <slide class="cup-carousel-item">
            <div class="item-wrapper">
              <div class="item-title">Cup F</div>
              <div class="item-image"><img src="../assets/test-images/cup-f.png" alt="" /></div>
              <div class="item-description">
                <div class="description-row">
                  <p>가격</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>수량</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(개)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>price</p>
                  <div class="row-num">
                    <span>2,000</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
              </div>
            </div>
          </slide>
        </carousel>

        <div class="cup-add-btn-wrapper">
          <div class="cup-add-btn">
            <font-awesome-icon
              :icon="['fas', 'plus']"
              style="width:100%; cursor:pointer;"
            ></font-awesome-icon>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Carousel, Slide } from "vue-carousel";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faPlus);

export default {
  components: {
    Carousel,
    Slide,
    FontAwesomeIcon,
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .cup-carousel-card {
      background-color: map-get($map: $theme, $key: "content-background");
      border: 1px solid map-get($map: $theme, $key: "content-blocked");
      display: flex;
      padding: 20px;
      box-shadow: $shadow-lighter;
      border-radius: 6px;
      transition: all 0.3s;
      width: 100%;
      max-width: 1020px;
      margin: auto;

      .card-inner-box {
        padding: 20px;
        width: 100%;

        .box-title {
          padding: 5px 15px;
          font-weight: bold;
          font-size: 1.2rem;
          letter-spacing: 0.1rem;
        }

        .carousel-wrapper {
          padding: 15px;
          display: flex;

          .cup-carousel {
            max-width: 90%;

            .cup-carousel-item {
              .item-wrapper {
                position: relative;
                display: flex;
                flex-direction: column;
                width: 240px;
                height: 320px;
                border-radius: 14px;
                background-color: map-get($map: $theme, $key: "background");
                padding: 18px;
                cursor: pointer;

                &:hover {
                  transition: all 0.2s ease;
                  transform: scale(0.95);
                  box-shadow: $shadow;
                }

                .item-title {
                  font-size: 1.2rem;
                  font-weight: bold;
                }

                .item-image {
                  width: 100%;
                  height: 150px;
                  padding: 10px;
                  margin: 15px 0;

                  img {
                    object-fit: contain;
                    width: 100%;
                    height: 100%;
                  }
                }

                .item-description {
                  display: flex;
                  flex-direction: column;
                  width: 100%;

                  .description-row {
                    width: 100%;
                    display: flex;
                    justify-content: space-between;
                    letter-spacing: 0.15rem;
                    color: map-get($map: $theme, $key: "text-light");
                    margin: 5px 0;

                    p {
                    }

                    .row-num {
                      span {
                        color: map-get($map: $theme, $key: "text");
                        font-weight: bold;
                      }

                      .unit {
                        font-size: 0.8rem;
                        font-weight: lighter;
                        letter-spacing: 0;
                        color: map-get($map: $theme, $key: "text-light");
                      }
                    }
                  }
                }
              }
            }
          }
          .cup-add-btn-wrapper {
            height: 320px;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;

            .cup-add-btn {
              margin: auto;
              width: 50px;
              height: 50px;
              border-radius: 50%;
              border: 1px solid map-get($map: $theme, $key: "text-light");
              background-color: transparent;
              transition: all 0.3s;
              cursor: pointer;
              color: map-get($map: $theme, $key: "text-light");
              font-size: 1.5rem;
              display: flex;
              flex-direction: column;
              justify-content: center;
              align-items: center;
              text-align: center;

              &:hover {
                border: 1px solid $main-color;
                color: $main-color;
                transform: translateY(-2px);
              }
            }
          }
        }
      }
    }
  }
}
</style>
