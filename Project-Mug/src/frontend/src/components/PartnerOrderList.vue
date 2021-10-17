<template>
  <div class="table-container">
    <div class="table">
      <li class="table-header">
        <div class="col col-1">Index</div>
        <div class="col col-2">주문날짜</div>
        <div class="col col-3">주문한 컵</div>
        <div class="col col-4">주문수량</div>
        <!-- <div class="col col-5">개당가격</div>
        <div class="col col-6">총가격</div> -->
        <div class="col col-7">상태</div>
        <div class="col col-8">Action</div>
      </li>
      <div v-for="index in parseInt(perPage)" :key="index">
        <li class="table-row" v-if="orderList[(currentPage - 1) * perPage + index - 1]">
          <div class="col col-1" data-label="Index">
            {{ (currentPage - 1) * perPage + index }}
          </div>
          <div class="col col-2" data-label="주문날짜">
            {{ setTime(orderList[(currentPage - 1) * perPage + index - 1].orderDateTime) }}
          </div>
          <div class="col col-3" data-label="주문한 컵">
            <span>{{ orderList[(currentPage - 1) * perPage + index - 1].cupName }}</span>
          </div>
          <div class="col col-4" data-label="주문수량">
            {{ addComma(orderList[(currentPage - 1) * perPage + index - 1].stockQuantity) }}
          </div>
          <!-- <div class="col col-5" data-label="개당가격">
            {{ addComma(orderList[(currentPage - 1) * perPage + index - 1].price) }}
          </div>
          <div class="col col-6" data-label="총가격">
            {{
              addComma(
                orderList[(currentPage - 1) * perPage + index - 1].stockQuantity *
                  orderList[(currentPage - 1) * perPage + index - 1].price,
              )
            }}
          </div> -->
          <div
            class="col col-7"
            v-if="orderList[(currentPage - 1) * perPage + index - 1]"
            data-label="상태"
          >
            <span :class="[orderList[(currentPage - 1) * perPage + index - 1].state]">{{
              orderList[(currentPage - 1) * perPage + index - 1].state
            }}</span>
          </div>
          <div class="col col-8">
            <span
              v-if="orderList[(currentPage - 1) * perPage + index - 1].state === 'waiting'"
              class="icon-wrapper"
              @click="undoOrder((currentPage - 1) * perPage + index - 1)"
            >
              <font-awesome-icon :icon="['fas', 'times']"></font-awesome-icon>
            </span>
          </div>
        </li>
      </div>
    </div>
    <div class="orderlist-footer">
      <div class="footer-nav">
        <ul role="menubar" aria-label="Pagination">
          <li role="presentation">
            <button
              role="menuitem"
              type="button"
              tabindex="-1"
              class="page-item prev-item"
              @click="prevPage"
              v-if="currentPage > 5"
            >
              <font-awesome-icon :icon="['fas', 'angle-left']"></font-awesome-icon>
            </button>
          </li>
          <li role="presentation">
            <button
              role="menuitem"
              type="button"
              tabindex="-1"
              class="page-item "
              :class="{
                active: currentPage == (Math.ceil(currentPage / 5) - 1) * 5 + 1,
              }"
              @click="selectPage"
              v-if="(Math.ceil(currentPage / 5) - 1) * 5 + 1 <= maxPage"
            >
              {{ (Math.ceil(currentPage / 5) - 1) * 5 + 1 }}
            </button>
          </li>
          <li role="presentation">
            <button
              role="menuitem"
              type="button"
              tabindex="-1"
              class="page-item"
              :class="{
                active: currentPage == (Math.ceil(currentPage / 5) - 1) * 5 + 2,
              }"
              @click="selectPage"
              v-if="(Math.ceil(currentPage / 5) - 1) * 5 + 2 <= maxPage"
            >
              {{ (Math.ceil(currentPage / 5) - 1) * 5 + 2 }}
            </button>
          </li>
          <li role="presentation">
            <button
              role="menuitem"
              type="button"
              tabindex="-1"
              class="page-item"
              :class="{
                active: currentPage == (Math.ceil(currentPage / 5) - 1) * 5 + 3,
              }"
              @click="selectPage"
              v-if="(Math.ceil(currentPage / 5) - 1) * 5 + 3 <= maxPage"
            >
              {{ (Math.ceil(currentPage / 5) - 1) * 5 + 3 }}
            </button>
          </li>
          <li role="presentation">
            <button
              role="menuitem"
              type="button"
              tabindex="-1"
              class="page-item"
              :class="{
                active: currentPage == (Math.ceil(currentPage / 5) - 1) * 5 + 4,
                'last-item': (Math.ceil(currentPage / 5) - 1) * 5 + 4 == maxPage,
              }"
              :style="[
                currentPage == (Math.ceil(currentPage / 5) - 1) * 5 + 4
                  ? { backgroundColor: '#5d9b5e' }
                  : {},
              ]"
              @click="selectPage"
              v-if="(Math.ceil(currentPage / 5) - 1) * 5 + 4 <= maxPage"
            >
              {{ (Math.ceil(currentPage / 5) - 1) * 5 + 4 }}
            </button>
          </li>
          <li role="presentation">
            <button
              role="menuitem"
              type="button"
              tabindex="-1"
              class="page-item"
              :class="{
                active: currentPage == (Math.ceil(currentPage / 5) - 1) * 5 + 5,
                'last-item': (Math.ceil(currentPage / 5) - 1) * 5 + 5 == maxPage,
              }"
              @click="selectPage"
              v-if="(Math.ceil(currentPage / 5) - 1) * 5 + 5 <= maxPage"
            >
              {{ (Math.ceil(currentPage / 5) - 1) * 5 + 5 }}
            </button>
          </li>
          <li role="presentation">
            <button
              role="menuitem"
              type="button"
              tabindex="-1"
              class="page-item next-item"
              @click="nextPage"
              v-if="currentPage <= Math.floor((maxPage - 1) / 5) * 5"
            >
              <font-awesome-icon :icon="['fas', 'angle-right']"></font-awesome-icon>
            </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faAngleLeft, faAngleRight, faTimes } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

import axios from "axios";

faLibrary.add(faAngleLeft, faAngleRight, faTimes);

export default {
  data() {
    return {
      orderList: [],
      total: 50,
      perPage: 10,
      maxPage: 20,
      currentPage: 1,
    };
  },
  components: { FontAwesomeIcon },
  props: ["partnerId"],
  methods: {
    nextPage() {
      this.currentPage = Math.ceil(this.currentPage / 5) * 5 + 1;
    },
    prevPage() {
      if (this.currentPage > 5) this.currentPage = Math.ceil(this.currentPage / 5) * 5 - 9;
    },
    selectPage() {
      this.currentPage = parseInt(event.target.textContent);
    },
    getOrders() {
      const path = `/backend/partner/orders/${this.partnerId}`;
      let orders = axios.create();

      orders
        .get(path)
        .then((res) => {
          this.orderList = res.data.reverse();
          this.total = this.orderList.length;
          this.maxPage = Math.ceil(this.total / this.perPage);
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
    setTime(timeString) {
      const orderDate = new Date(timeString);
      const nowDate = new Date();

      const diff = nowDate.getTime() - orderDate.getTime();
      const diffDay = parseInt(diff / (1000 * 60 * 60 * 24));
      const diffHour = parseInt(diff / (1000 * 60 * 60));
      const diffMin = parseInt(diff / (1000 * 60));

      let resString = "";

      switch (diffDay) {
        case 0:
          if (diffHour === 0) {
            resString = diffMin + "분 전";
          } else {
            resString = diffHour + "시간 전";
          }
          break;
        case 1:
          resString = "어제";
          break;
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
          resString = diffDay + "일 전";
          break;
        default:
          resString = `${orderDate.getUTCFullYear()}-${orderDate.getUTCMonth() +
            1}-${orderDate.getUTCDate()}`;
      }

      return resString;
    },
    addComma(str) {
      let text = str.toString().replace(/[^0-9]/g, "");
      const result = text.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      return result;
    },
    undoOrder(index) {
      const order = this.orderList[index];

      if (order.state === "waiting") {
        if (confirm(`${order.cupName} : ${order.stockQuantity}개\n이 발주를 취소하시겠습니까?`)) {
          const orderId = order.id;
          const path = `/backend/partner-cup/reject`;

          let rejectOrder = axios.create();

          rejectOrder
            .post(path, { partnerOrderId: orderId })
            .then(() => {
              this.$emit("makeToast", { status: "success", msg: "발주를 취소했습니다." });
              this.getOrders();
            })
            .catch((err) => {
              console.log("err :>> ", err);
            });
        }
      }
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
          flex-basis: 5%;
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
        .col-8 {
          flex-basis: 5%;

          .icon-wrapper {
            font-size: 1rem;
            background-color: rgba(map-get($map: $theme, $key: "text"), 0.1);
            border-radius: 6px;
            transition: all 0.2s ease;
            color: $error-msg;
            padding: 0.2rem 0.35rem;
            cursor: pointer;

            &:hover {
              background-color: $error-msg;
              color: white;
            }
          }
        }
      }

      .orderlist-footer {
        padding: 0 1rem 1rem 1rem;
        background-color: map-get($map: $theme, $key: "table");
        width: 100%;
        border-radius: 0 0 6px 6px;

        .footer-nav {
          border-top: 1px solid map-get($map: $theme, $key: "border");
          display: flex;
          justify-content: space-between;

          ul {
            display: flex;
            list-style: none;
            box-sizing: border-box;
            align-items: center;
            padding: 1rem 1rem 0 1rem;

            .prev-item {
              margin-right: 0.35rem;
              border-radius: 50%;
            }
            .next-item {
              margin-left: 0.35rem;
              border-radius: 50%;
            }

            .page-item {
              width: 25px;
              height: 25px;
              display: flex;
              justify-content: center;
              align-items: center;
              border: none;
              transition: all 0.2s ease-out;
              cursor: pointer;
              font-size: 0.9rem;
              background-color: map-get($map: $theme, $key: "content-background");
              position: relative;
              color: map-get($map: $theme, $key: "text-light");
              outline: none;
              border-radius: 50%;
              margin: 0 5px;
              box-shadow: $shadow-light;
              transition: all 0.3s ease-in-out;

              &:hover {
                background-color: rgba($main-color, 0.6);
                color: $white;
                box-shadow: $shadow;
              }

              &.active {
                background-color: $main-color;
                color: $white;
              }
            }
          }

          .footer-button-wrapper {
            display: flex;
            align-items: center;
            align-items: center;
            padding: 1rem 1rem 0 1rem;

            .footer-button {
              margin: 0 5px;
              width: 25px;
              height: 25px;
              border: 1px solid map-get($map: $theme, $key: "border");
              border-radius: 6px;
              display: flex;
              justify-content: center;
              align-items: center;
              cursor: pointer;
              color: $white;
              transition: all 0.2s ease;

              &.submit {
                background-color: $main-color;

                &:hover,
                &:active {
                  background-color: map-get($map: $theme, $key: "content-background");
                  border: 3px solid $main-color;
                  color: $main-color;
                  box-shadow: $shadow;
                }
              }

              &.reject {
                background-color: $error-msg;

                &:hover,
                &:active {
                  background-color: map-get($map: $theme, $key: "content-background");
                  border: 3px solid $error-msg;
                  color: $error-msg;
                  box-shadow: $shadow;
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
