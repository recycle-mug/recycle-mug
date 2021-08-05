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
      <li class="table-row" v-for="(order, index) in orderList" :key="index">
        <div class="col col-1" data-label="Index">{{ index + 1 }}</div>
        <div class="col col-2" data-label="주문날짜">{{ order.orderDateTime.slice(0, 10) }}</div>
        <div class="col col-3" data-label="주문한 컵">
          <span>{{ order.cupName }}</span>
        </div>
        <div class="col col-4" data-label="주문수량">{{ addComma(order.stockQuantity) }}</div>
        <div class="col col-5" data-label="개당가격">{{ addComma(order.price) }}</div>
        <div class="col col-6" data-label="총가격">
          {{ addComma(order.stockQuantity * order.price) }}
        </div>
        <div class="col col-7" data-label="상태">
          <span :class="[order.state]">{{ order.state }}</span>
        </div>
      </li>
    </div>
    <div class="table-footer">다음페이지</div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      orderList: [],
    };
  },
  props: ["partnerId"],
  methods: {
    getOrders() {
      const path = `/backend/partner/orders/${this.partnerId}`;
      let orders = axios.create();

      orders
        .get(path)
        .then((res) => {
          this.orderList = res.data;
          this.setState();
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
    },
    setState() {
      for (let order in this.orderList) {
        if (this.orderList[order].state === "DELIVERY_WAITING") {
          this.orderList[order].state = "waiting";
        }

        if (this.orderList[order].state === "COMPLETE") {
          this.orderList[order].state = "complete";
        }

        if (this.orderList[order].state === "CANCEL") {
          this.orderList[order].state = "canceled";
        }
      }
    },
    addComma(str) {
      let text = str.toString().replace(/[^0-9]/g, "");
      const result = text.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      return result;
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
          padding: 1.2rem;

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

          span {
            user-select: none;
            border-radius: 10px;
            border: 1px solid rgba($white, 0.1);
            padding: 2px 5px;
            font-size: 0.85rem;

            &.complete {
              background-color: rgba(#20c997, 0.1);
              color: #20c997;
            }

            &.waiting {
              background-color: rgba(#fa8b0c, 0.1);
              color: #fa8b0c;
            }

            &.canceled {
              background-color: rgba(#ff4d4f, 0.1);
              color: #ff4d4f;
            }
          }
        }
      }

      .table-footer {
        padding: 1rem;
      }
    }
  }
}
</style>
