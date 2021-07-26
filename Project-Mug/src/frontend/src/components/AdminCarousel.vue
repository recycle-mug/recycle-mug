<template>
  <div class="cup-carousel-card">
    <div class="card-inner-box" v-if="!writingMode">
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
          v-if="cups.length > 0"
        >
          <slide class="cup-carousel-item" v-for="(cup, index) in cups" :key="index">
            <div class="item-wrapper" @click="openModal(cup.id)">
              <div class="item-title">{{ cup.name }}</div>
              <div class="item-image">
                <img :src="'data:image/jpeg;base64,' + cup.image" alt="" />
              </div>
              <div class="item-description">
                <div class="description-row">
                  <p>가격</p>
                  <div class="row-num">
                    <span>{{ addComma(cup.price) }}</span>
                    <span class="unit">(원)</span>
                  </div>
                </div>
                <div class="description-row">
                  <p>수량</p>
                  <div class="row-num">
                    <span>{{ addComma(cup.stockQuantity) }}</span>
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

        <div class="cup-add-btn-wrapper" v-if="cups.length > 0">
          <div class="cup-add-btn" @click="TurnOnWritingMode()">
            <font-awesome-icon
              :icon="['fas', 'plus']"
              style="width:100%; cursor:pointer;"
            ></font-awesome-icon>
          </div>
        </div>

        <div class="cup-add-btn-wrapper wide" v-else @click="TurnOnWritingMode()">
          <div class="cup-add-btn">
            <font-awesome-icon
              :icon="['fas', 'plus']"
              style="width:100%; cursor:pointer;"
            ></font-awesome-icon>
          </div>
          Click to Add New Cup
        </div>
      </div>
    </div>

    <div class="card-inner-box" v-else>
      <div class="box-title" style="display:flex; justify-content:space-between">
        <h3>Add New Cup</h3>
        <button class="mode-switch" @click="TurnOffWritingMode()">
          <font-awesome-icon :icon="['fas', 'times']" style="width:100%;"></font-awesome-icon>
        </button>
      </div>
      <admin-new-cup
        v-on:modeSwitch="TurnOffWritingMode()"
        @makeToast="makeToast"
        @refreshPage="getCups"
      ></admin-new-cup>
    </div>

    <div v-if="opened" style="position:absolute;top:0;left:0;" @click="closeModal()">
      <div class="modal-wrapper">
        <div class="modal" @click.stop>
          <admin-modal :cupId="clickedCupId" v-on:closeModal="closeModal"></admin-modal>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Carousel, Slide } from "vue-carousel";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faPlus, faTimes } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

import AdminModal from "./AdminModal.vue";
import AdminNewCup from "./AdminNewCup.vue";

import axios from "axios";

faLibrary.add(faPlus, faTimes);

export default {
  data() {
    return {
      opened: false,
      writingMode: false,
      cups: {},
      clickedCupId: 0,
    };
  },
  components: {
    Carousel,
    Slide,
    FontAwesomeIcon,
    AdminModal,
    AdminNewCup,
  },
  computed: {},
  methods: {
    openModal(id) {
      this.opened = true;
      this.clickedCupId = id;
    },
    closeModal() {
      this.opened = false;
    },
    TurnOnWritingMode() {
      this.writingMode = true;
    },
    TurnOffWritingMode() {
      this.writingMode = false;
    },
    async getCups() {
      const path = "/backend/cup/list";
      let getCup = axios.create();
      await getCup
        .get(path)
        .then((res) => {
          this.cups = res.data;
          this.cupLength = res.data.length;
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
    },
    addComma(str) {
      let text = str.toString().replace(/[^0-9]/g, "");
      const result = text.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      return result;
    },
    makeToast(toast) {
      this.$emit("makeToast", toast);
    },
  },
  mounted() {
    this.getCups();
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

          .mode-switch {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            border: 1px solid map-get($map: $theme, $key: "text-light");
            color: map-get($map: $theme, $key: "text-light");
            background-color: transparent;
            cursor: pointer;
            font-size: 1.2rem;
            text-align: center;
            display: flex;
            justify-content: center;
            align-items: center;

            &:hover {
              border: 1px solid $main-color;
              color: $main-color;
              transform: translateY(-2px);
            }
          }
        }

        .carousel-wrapper {
          padding: 15px;
          display: flex;

          .cup-carousel {
            max-width: 90%;
            width: 100%;

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
                margin-right: 1.5rem;

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
                  margin: 15px 0;
                  border-radius: 6px;
                  overflow: hidden;

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

            &.wide {
              width: 100%;
              border-radius: 6px;
              border: 2px dashed map-get($map: $theme, $key: "border");
              background-color: rgba(map-get($map: $theme, $key: "table"), 0.5);
              cursor: pointer;
              transition: all 0.3s ease;
              display: flex;
              flex-direction: column;
              justify-content: center;
              align-items: center;
              color: map-get($map: $theme, $key: "text-light");
              user-select: none;
              letter-spacing: 0.1rem;

              &:hover {
                background-color: rgba(map-get($map: $theme, $key: "table"), 1);
                box-shadow: $shadow-lighter;
                color: map-get($map: $theme, $key: "text");

                .cup-add-btn {
                  border: 1px solid map-get($map: $theme, $key: "text");
                  color: map-get($map: $theme, $key: "text");
                }
              }

              .cup-add-btn {
                margin: 1rem;
              }
            }
          }
        }
      }
    }

    .modal-wrapper {
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.75);
      position: fixed;
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 99;

      .modal {
        width: 720px;
        height: 800px;
        max-width: 90%;
        max-height: 90%;
        background-color: map-get($map: $theme, $key: "content-background");
        z-index: 999;
        position: fixed;
        border-radius: 6px;
      }
    }
  }
}
</style>
