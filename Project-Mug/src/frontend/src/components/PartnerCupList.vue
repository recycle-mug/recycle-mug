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

    <div class="cup-table-wrapper">
      <div class="table">
        <li class="table-header">
          <div class="col col-1">Index</div>
          <div class="col col-2">컵</div>
          <div class="col col-3">매장 내 컵 수량</div>
          <div class="col col-4">개당 가격</div>
        </li>
        <li class="table-row">
          <div class="col col-1" data-label="Index">1</div>
          <div class="col col-2" data-label="컵이름">
            <div class="image-wrapper"><img src="" alt="" /></div>
            <span>컵 A</span>
          </div>
          <div class="col col-3" data-label="컵수량">50개</div>
          <div class="col col-4" data-label="컵가격">1,000원</div>
        </li>
        <li class="table-row">
          <div class="col col-1" data-label="Index">1</div>
          <div class="col col-2" data-label="컵이름">
            <div class="image-wrapper"><img src="" alt="" /></div>
            <span>컵 A</span>
          </div>
          <div class="col col-3" data-label="컵수량">50개</div>
          <div class="col col-4" data-label="컵가격">1,000원</div>
        </li>
        <li class="table-row">
          <div class="col col-1" data-label="Index">1</div>
          <div class="col col-2" data-label="컵이름">
            <div class="image-wrapper"><img src="" alt="" /></div>
            <span>컵 A</span>
          </div>
          <div class="col col-3" data-label="컵수량">50개</div>
          <div class="col col-4" data-label="컵가격">1,000원</div>
        </li>
        <li class="table-row">
          <div class="col col-1" data-label="Index">1</div>
          <div class="col col-2" data-label="컵이름">
            <div class="image-wrapper"><img src="" alt="" /></div>
            <span>컵 A</span>
          </div>
          <div class="col col-3" data-label="컵수량">50개</div>
          <div class="col col-4" data-label="컵가격">1,000원</div>
        </li>
        <li class="table-row">
          <div class="col col-1" data-label="Index">1</div>
          <div class="col col-2" data-label="컵이름">
            <div class="image-wrapper"><img src="" alt="" /></div>
            <span>컵 A</span>
          </div>
          <div class="col col-3" data-label="컵수량">50개</div>
          <div class="col col-4" data-label="컵가격">1,000원</div>
        </li>
      </div>
      <div class="table-footer">다음페이지</div>
    </div>
  </div>
</template>

<script>
import { Carousel, Slide } from "vue-carousel";
import axios from "axios";
export default {
  components: {
    Carousel,
    Slide,
  },
  data() {
    return {
      partnerCups: null,
    };
  },
  methods: {
    getPartnerId() {
      const path = "/backend/profile";

      const { accessToken } = localStorage;

      if (accessToken) {
        const partner = axios.create();

        partner
          .get(path)
          .then((res) => {
            if (res.data.error) {
              throw res.data.error;
            } else {
              this.getCurrentCups(res.data.id);
            }
          })
          .catch((error) => {
            console.error(error);
            alert(error);
            localStorage.removeItem("accessToken");
          });
      }
    },
    getCurrentCups(id) {
      if (id) {
        const path = `/backend/partner-cup/${id}`;

        const currentCups = axios.create();

        currentCups
          .get(path)
          .then((res) => {
            console.log("res.data :>> ", res.data);
          })
          .catch((err) => {
            console.log("err :>> ", err);
          });
      }
    },
  },
  mounted() {
    this.getPartnerId();
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
      flex-direction: column;

      .cup-carousel {
        max-width: 90%;
        width: 100%;
        margin: 1rem auto;

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

      .cup-table-wrapper {
        width: 100%;
        border: 1px solid map-get($map: $theme, $key: "border");
        border-radius: 14px;
        overflow: hidden;
        margin: 1rem auto;

        .table {
          li {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid map-get($map: $theme, $key: "border");
          }

          .table-header {
            background-color: map-get($map: $theme, $key: "table");
            color: map-get($map: $theme, $key: "text");
            padding: 1.2rem;
          }

          .table-row {
            background-color: map-get($map: $theme, $key: "content-background");
            color: map-get($map: $theme, $key: "text-light");
            transition: all 0.2s ease;
            padding: 1rem;

            &:hover {
              background-color: darken(
                $color: map-get($map: $theme, $key: "content-background"),
                $amount: 3%
              );
            }
          }

          .col {
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
          }

          .col-1 {
            flex-basis: 25%;
          }
          .col-2 {
            flex-basis: 25%;
          }
          .col-3 {
            flex-basis: 25%;
            display: flex;
          }
          .col-4 {
            flex-basis: 25%;
          }
        }

        .table-footer {
          padding: 1rem;
        }
      }
    }
  }
}
</style>
