<template>
  <div class="confirm-table-card-container">
    <div class="confirm-table-card">
      <div class="card-left">
        <div class="orderlist-wrapper">
          <div class="orderlist-header">
            <div class="header-nav">
              <ul>
                <li class="active">All</li>
                <li>승인대기</li>
                <li>승인완료</li>
                <li>승인취소</li>
              </ul>
            </div>
          </div>
          <div class="orderlist-body">
            <table class="orderlist-table">
              <colgroup>
                <col class="selection-col" />
              </colgroup>

              <thead class="table-header">
                <tr>
                  <th class="selection-column">
                    <input type="checkbox" name="" id="" @input="checkAll" v-model="allSelected" />
                  </th>
                  <th class="id">Id</th>
                  <th class="customer">Customer</th>
                  <th class="status">Status</th>
                  <th class="amount">Amount</th>
                  <th class="date">Date</th>
                  <th class="action">Action</th>
                </tr>
              </thead>

              <tbody class="table-body">
                <tr v-for="index in parseInt(perPage)" :key="index">
                  <td
                    v-if="entryList[(currentPage - 1) * perPage + index - 1]"
                    class="selection-column"
                  >
                    <input
                      type="checkbox"
                      name=""
                      id=""
                      :value="(currentPage - 1) * perPage + index - 1"
                      v-model="orderIds"
                      @click="checkOne"
                    />
                  </td>
                  <td v-if="entryList[(currentPage - 1) * perPage + index - 1]" class="id">
                    <span>1</span>
                  </td>
                  <td v-if="entryList[(currentPage - 1) * perPage + index - 1]" class="customer">
                    <span>{{
                      entryList[(currentPage - 1) * perPage + index - 1].businessName
                    }}</span>
                  </td>
                  <td v-if="entryList[(currentPage - 1) * perPage + index - 1]" class="status">
                    <span class="status success">{{
                      entryList[(currentPage - 1) * perPage + index - 1].state
                    }}</span>
                  </td>
                  <td v-if="entryList[(currentPage - 1) * perPage + index - 1]" class="amount">
                    <span>{{
                      entryList[(currentPage - 1) * perPage + index - 1].stockQuantity
                    }}</span>
                  </td>
                  <td v-if="entryList[(currentPage - 1) * perPage + index - 1]" class="date">
                    <span>{{
                      entryList[(currentPage - 1) * perPage + index - 1].orderDateTime.slice(0, 19)
                    }}</span>
                  </td>
                  <td v-if="entryList[(currentPage - 1) * perPage + index - 1]" class="action">
                    <span>삭제하기</span>
                  </td>
                </tr>
              </tbody>
            </table>
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
                        ? { backgroundColor: userStyle }
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
      </div>
      <div class="card-right">
        <div class="order-summmary-wrapper">
          <div class="order-summmary">
            <div class="summary-card-content">
              <h1 class="card-title">Order Summary</h1>
              <div class="card-body">
                <div class="body-content">
                  <ul class="content-list">
                    <li>
                      <span class="content-list-title">승인대기</span>
                      <span class="content-list-text">500개</span>
                    </li>
                    <li>
                      <span class="content-list-title">승인완료</span>
                      <span class="content-list-text">500개</span>
                    </li>
                    <li>
                      <span class="content-list-title">승인취소</span>
                      <span class="content-list-text">500개</span>
                    </li>
                    <li>
                      <span class="content-list-title">총</span>
                      <span class="content-list-text">500개</span>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faAngleLeft, faAngleRight } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

import axios from "axios";

faLibrary.add(faAngleLeft, faAngleRight);

export default {
  data() {
    return {
      total: 50,
      perPage: 10,
      maxPage: 20,
      currentPage: 1,
      entryList: [],
      orderIds: [],
      allSelected: false,
    };
  },
  components: { FontAwesomeIcon },
  methods: {
    nextPage() {
      this.currentPage = Math.ceil(this.currentPage / 5) * 5 + 1;
      this.allSelected = false;
      this.orderIds = [];
    },
    prevPage() {
      if (this.currentPage > 5) this.currentPage = Math.ceil(this.currentPage / 5) * 5 - 9;
      this.allSelected = false;
      this.orderIds = [];
    },
    selectPage() {
      this.currentPage = parseInt(event.target.textContent);
      this.allSelected = false;
      this.orderIds = [];
    },
    getOrderList() {
      const path = `/backend/partner/orders`;
      let findUsers = axios.create();

      findUsers
        .get(path)
        .then((res) => {
          this.entryList = res.data;
          console.log("res :>> ", res);
          this.total = this.entryList.length;
          this.maxPage = Math.ceil(this.total / this.perPage);
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
    },
    checkAll() {
      this.orderIds = [];

      if (!this.allSelected) {
        for (let index = 1; index <= this.perPage; index++) {
          if (this.entryList[(this.currentPage - 1) * this.perPage + index - 1]) {
            this.orderIds.push((this.currentPage - 1) * this.perPage + index - 1);
          }
        }
      }
    },
    checkOne() {
      this.allSelected = false;
    },
  },
  mounted() {
    this.getOrderList();
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .confirm-table-card-container {
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

      .confirm-table-card {
        display: flex;

        .card-left {
          max-width: 70%;
          width: 100%;
          margin-right: 1rem;

          .orderlist-wrapper {
            width: 100%;
            display: flex;
            flex-direction: column;

            .orderlist-header {
              display: flex;
              border-bottom: 1px solid map-get($map: $theme, $key: "border");

              .header-nav {
                display: flex;
                align-items: center;

                ul {
                  list-style: none;
                  display: flex;

                  li {
                    color: map-get($map: $theme, $key: "text-lighter");
                    font-weight: normal;
                    position: relative;
                    padding: 1rem 1.2rem;
                    user-select: none;
                    cursor: pointer;
                    transition: all 0.2s linear;
                    border-radius: 6px 6px 0 0;

                    &:hover {
                      background-color: rgba(map-get($theme, "background"), 0.4);
                      border-bottom: 2px solid rgba($main-color, 0.4);
                      font-weight: bold;
                    }

                    &.active {
                      border-bottom: 2px solid $main-color;
                      color: $main-color;
                      font-weight: bolder;
                      background-color: map-get($map: $theme, $key: "background");
                    }
                  }
                }
              }
            }

            .orderlist-body {
              padding: 1rem;
              background-color: map-get($map: $theme, $key: "background");
              width: 100%;
              height: 100%;

              .orderlist-table {
                width: 100%;
                table-layout: auto;

                .selection-col {
                  width: 32px;
                }

                .selection-column {
                  cursor: pointer;
                }

                .table-header {
                  color: map-get($map: $theme, $key: "text");
                  white-space: nowrap;
                  user-select: none;

                  tr th {
                    padding: 1rem 0;
                    text-align: center;
                    font-size: 0.9rem;
                    font-weight: bold;
                    background-color: map-get($map: $theme, $key: "content-background");

                    &:first-child {
                      border-radius: 6px 0 0 6px;
                    }

                    &:last-child {
                      border-radius: 0 6px 6px 0;
                    }

                    &.selection-column {
                      width: 5%;
                    }
                    &.id {
                      width: 5%;
                    }
                    &.customer {
                      width: 20%;
                    }
                    &.status {
                      width: 15%;
                    }
                    &.amount {
                      width: 15%;
                    }
                    &.date {
                      width: 20%;
                    }
                    &.action {
                      width: 20%;
                    }
                  }
                }

                .table-body {
                  padding: 1rem;
                  text-align: center;
                  font-size: 0.85rem;

                  tr:hover {
                    background-color: darken($color: map-get($theme, "background"), $amount: 3%);
                  }

                  tr td {
                    padding: 1rem 0;
                    cursor: pointer;

                    &:first-child {
                      border-radius: 6px 0 0 6px;
                    }

                    &:last-child {
                      border-radius: 0 6px 6px 0;
                    }

                    &:hover {
                      background-color: darken($color: map-get($theme, "background"), $amount: 6%);
                    }

                    &.selection-column {
                      width: 5%;
                    }
                    &.id {
                      width: 5%;
                    }
                    &.customer {
                      width: 20%;
                    }
                    &.status {
                      width: 15%;
                    }
                    &.amount {
                      width: 15%;
                    }
                    &.date {
                      width: 20%;
                    }
                    &.action {
                      width: 20%;
                    }
                  }
                }
              }
            }

            .orderlist-footer {
              padding: 0 1rem 1rem 1rem;
              background-color: map-get($map: $theme, $key: "background");
              width: 100%;
              border-radius: 0 0 6px 6px;

              .footer-nav {
                border-top: 1px solid map-get($map: $theme, $key: "border");

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
              }
            }
          }
        }

        .card-right {
          max-width: 30%;
          width: 100%;

          .order-summmary-wrapper {
            position: relative;
            max-width: 350px;
            width: 100%;
            height: 100%;

            .order-summmary {
              background-color: map-get($map: $theme, $key: "background");
              border-radius: 6px;
              padding: 1rem;
              height: 100%;

              .summary-card-content {
                margin: 0 auto;
                display: flex;
                flex-direction: column;
                height: 100%;

                .card-title {
                  font-size: 1rem;
                  font-weight: bolder;
                  color: map-get($map: $theme, $key: "text");
                  margin-bottom: 25px;
                  padding: 1rem 1rem 0 1rem;
                }

                .card-body {
                  list-style: none;
                  position: relative;
                  background-color: map-get($map: $theme, $key: "content-background");
                  border-radius: 6px;
                  box-shadow: $shadow-lighter;
                  padding: 1rem;
                  height: 100%;

                  .body-content .content-list {
                    list-style: none;

                    li {
                      margin-bottom: 18px;
                      display: flex;
                      justify-content: space-between;
                      align-items: center;
                      padding: 1rem;

                      &:last-child {
                        border-top: 1px solid map-get($map: $theme, $key: "border");
                      }

                      .content-list-title {
                      }

                      .content-list-text {
                        color: map-get($map: $theme, $key: "text");
                        font-weight: bolder;
                        font-size: 1.05rem;
                      }
                    }
                  }
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
