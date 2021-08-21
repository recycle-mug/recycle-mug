<template>
  <div class="confirm-table-card-container">
    <div class="confirm-table-card">
      <div class="orderlist-wrapper">
        <div class="orderlist-header">
          <div class="header-nav">
            Cup log
          </div>
        </div>
        <div class="orderlist-body">
          <table class="orderlist-table">
            <thead class="table-header">
              <tr>
                <th class="id">#</th>
                <th class="customer" colspan="2">Customer</th>
                <th class="rent-date">대여일</th>
                <th class="return-date">반납예정일</th>
                <th class="returned-date">반납일</th>
              </tr>
            </thead>

            <tbody class="table-body">
              <tr v-for="index in parseInt(perPage)" :key="index">
                <td class="id" v-if="entryList[(currentPage - 1) * perPage + index - 1]">
                  <span>{{ (currentPage - 1) * perPage + index }}</span>
                </td>
                <td
                  class="customer email"
                  v-if="entryList[(currentPage - 1) * perPage + index - 1]"
                >
                  <span>{{ entryList[(currentPage - 1) * perPage + index - 1].email }}</span>
                </td>
                <td
                  class="customer nickname"
                  v-if="entryList[(currentPage - 1) * perPage + index - 1]"
                >
                  <span>{{ entryList[(currentPage - 1) * perPage + index - 1].nickname }}</span>
                </td>
                <td class="rent-date" v-if="entryList[(currentPage - 1) * perPage + index - 1]">
                  <span
                    >{{
                      entryList[(currentPage - 1) * perPage + index - 1].rentDateTime.split("T")[0]
                    }}<br />{{
                      entryList[(currentPage - 1) * perPage + index - 1].rentDateTime
                        .split("T")[1]
                        .slice(0, 8)
                    }}</span
                  >
                </td>
                <td class="return-date" v-if="entryList[(currentPage - 1) * perPage + index - 1]">
                  <span
                    >{{
                      entryList[(currentPage - 1) * perPage + index - 1].returnDateTime.split(
                        "T",
                      )[0]
                    }}<br />{{
                      entryList[(currentPage - 1) * perPage + index - 1].returnDateTime
                        .split("T")[1]
                        .slice(0, 8)
                    }}</span
                  >
                </td>
                <td class="returned-date" v-if="entryList[(currentPage - 1) * perPage + index - 1]">
                  <span v-if="entryList[(currentPage - 1) * perPage + index - 1].returnedDateTime"
                    >{{
                      entryList[(currentPage - 1) * perPage + index - 1].returnedDateTime.split(
                        "T",
                      )[0]
                    }}<br />{{
                      entryList[(currentPage - 1) * perPage + index - 1].returnedDateTime
                        .split("T")[1]
                        .slice(0, 8)
                    }}</span
                  >
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
    };
  },
  components: { FontAwesomeIcon },
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
    getCupLogs() {
      const path = `/backend/get-all-trade`;
      let findUsers = axios.create();

      findUsers
        .get(path)
        .then((res) => {
          console.log("res.data :>> ", res.data);
          this.entryList = res.data;
          this.total = this.entryList.length;
          this.maxPage = Math.ceil(this.total / this.perPage);
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
    },
  },
  mounted() {
    this.getCupLogs();
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
              color: map-get($map: $theme, $key: "text");
              font-weight: normal;
              position: relative;
              padding: 1rem 1.2rem;
              user-select: none;
              transition: all 0.2s linear;
              border-radius: 6px 6px 0 0;
              font-weight: bolder;
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
                  &.id {
                    width: 10%;
                  }
                  &.customer {
                    width: 45%;
                  }
                  &.rent-date {
                    width: 15%;
                  }
                  &.return-date {
                    width: 15%;
                  }
                  &.returned-date {
                    width: 15%;
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
                  vertical-align: middle;

                  &:first-child {
                    border-radius: 6px 0 0 6px;
                  }

                  &:last-child {
                    border-radius: 0 6px 6px 0;
                  }

                  &:hover {
                    background-color: darken($color: map-get($theme, "background"), $amount: 6%);
                  }

                  &.id {
                    width: 10%;
                  }
                  &.customer {
                    &.email {
                      text-align: end;
                      padding-right: 10px;
                      width: 25%;
                    }
                    &.nickname {
                      text-align: start;
                      width: 20%;
                    }
                  }
                  &.rent-date {
                    width: 15%;
                  }
                  &.return-date {
                    width: 15%;
                  }
                  &.returned-date {
                    width: 15%;
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
              display: flex;
              justify-content: flex-end;

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
    }
  }
}
</style>
