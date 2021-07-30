<template>
  <div class="table-container">
    <div class="table">
      <li class="table-header">
        <div class="col col-1">Index</div>
        <div class="col col-2">주문날짜</div>
        <div class="col col-3">주문한 컵</div>
        <div class="col col-4">주문수량</div>
        <div class="col col-5">개당가격</div>
        <div class="col col-6">총가격</div>
        <div class="col col-7">상태</div>
      </li>
      <li class="table-row">
        <div class="col col-1" data-label="Index">1</div>
        <div class="col col-2" data-label="주문날짜">2021-07-23 14:39:21</div>
        <div class="col col-3" data-label="주문한 컵">
          <div class="image-wrapper"><img src="" alt="" /></div>
          <span>컵 A</span>
        </div>
        <div class="col col-4" data-label="주문수량">50</div>
        <div class="col col-5" data-label="개당가격">1000</div>
        <div class="col col-6" data-label="총가격">50000</div>
        <div class="col col-7" data-label="상태">출고대기</div>
      </li>

      <li class="table-row">
        <div class="col col-1" data-label="Index">1</div>
        <div class="col col-2" data-label="주문날짜">2021-07-23 14:39:21</div>
        <div class="col col-3" data-label="주문한 컵">
          <div class="image-wrapper"><img src="" alt="" /></div>
          <span>컵 A</span>
        </div>
        <div class="col col-4" data-label="주문수량">50</div>
        <div class="col col-5" data-label="개당가격">1000</div>
        <div class="col col-6" data-label="총가격">50000</div>
        <div class="col col-7" data-label="상태">출고대기</div>
      </li>
      <li class="table-row">
        <div class="col col-1" data-label="Index">1</div>
        <div class="col col-2" data-label="주문날짜">2021-07-23 14:39:21</div>
        <div class="col col-3" data-label="주문한 컵">
          <div class="image-wrapper"><img src="" alt="" /></div>
          <span>컵 A</span>
        </div>
        <div class="col col-4" data-label="주문수량">50</div>
        <div class="col col-5" data-label="개당가격">1000</div>
        <div class="col col-6" data-label="총가격">50000</div>
        <div class="col col-7" data-label="상태">출고대기</div>
      </li>
      <li class="table-row">
        <div class="col col-1" data-label="Index">1</div>
        <div class="col col-2" data-label="주문날짜">2021-07-23 14:39:21</div>
        <div class="col col-3" data-label="주문한 컵">
          <div class="image-wrapper"><img src="" alt="" /></div>
          <span>컵 A</span>
        </div>
        <div class="col col-4" data-label="주문수량">50</div>
        <div class="col col-5" data-label="개당가격">1000</div>
        <div class="col col-6" data-label="총가격">50000</div>
        <div class="col col-7" data-label="상태">출고대기</div>
      </li>
      <li class="table-row">
        <div class="col col-1" data-label="Index">1</div>
        <div class="col col-2" data-label="주문날짜">2021-07-23 14:39:21</div>
        <div class="col col-3" data-label="주문한 컵">
          <div class="image-wrapper"><img src="" alt="" /></div>
          <span>컵 A</span>
        </div>
        <div class="col col-4" data-label="주문수량">50</div>
        <div class="col col-5" data-label="개당가격">1000</div>
        <div class="col col-6" data-label="총가격">50000</div>
        <div class="col col-7" data-label="상태">출고대기</div>
      </li>
    </div>
    <div class="table-footer">다음페이지</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      orderList: [],
    };
  },
  methods: {
    getOrders() {
      // const path =
    },
  },
  mounted() {
    this.getOrders();
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .table-container {
      width: 100%;

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
          flex-basis: 10%;
        }
        .col-2 {
          flex-basis: 20%;
        }
        .col-3 {
          flex-basis: 20%;
          display: flex;
        }
        .col-4 {
          flex-basis: 15%;
        }
        .col-5 {
          flex-basis: 15%;
        }
        .col-6 {
          flex-basis: 10%;
        }
        .col-7 {
          flex-basis: 10%;
        }
      }

      .table-footer {
        padding: 1rem;
      }
    }
  }
}
</style>
