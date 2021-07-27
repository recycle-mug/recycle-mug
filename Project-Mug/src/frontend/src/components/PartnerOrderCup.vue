<template>
  <div class="form-container">
    <form>
      <div class="form-content">
        <details class="cup-select-box">
          <summary class="radios">
            <input
              type="radio"
              name="cup"
              id="default"
              title="발주할 컵 선택"
              checked
              @click="selectCup"
            />
            <input
              type="radio"
              name="cup"
              :id="cup.id"
              :title="cup.name"
              v-for="(cup, index) in cups"
              :key="index"
              @click="selectCup"
            />
          </summary>
          <ul class="list">
            <li v-for="(cup, index) in cups" :key="index">
              <label :for="cup.id">{{ cup.name }}</label>
            </li>
          </ul>
        </details>

        <div class="cup-quantity-box">
          <input
            type="number"
            name="cup_quantity"
            id="cup_quantity"
            placeholder="수량"
            class="cup-quantity"
            v-model="quantity"
            required
          />
          <label for="cup_quantity" class="cup-quantity-label">수량</label>
          <span>개</span>
        </div>
      </div>

      <div class="submit-error-msg">
        <span>{{ errorMsg }}</span>
      </div>

      <div class="submit-btn-wrapper">
        <button type="submit" @click.prevent="validate">발주 신청</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      selectedCup: "",
      quantity: null,
      partnerId: null,
      cups: [],
      errorMsg: "",
    };
  },
  methods: {
    selectCup(e) {
      this.selectedCup = e.target.id;
    },
    getAllCups() {
      const { accessToken } = localStorage;

      if (accessToken) {
        const path = "/backend/get-all-cups";

        let cupList = axios.create();

        cupList.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
        cupList.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        cupList.defaults.headers.common["Access-Control-Allow-Methods"] =
          "GET,POST,PUT,DELETE,OPTIONS";
        cupList.defaults.headers.common["Content-Type"] =
          "application/x-www-form-urlencoded;charset=utf-8";

        cupList
          .get(path)
          .then((res) => {
            this.cups = res.data;
          })
          .catch((err) => {
            console.log("err :>> ", err);
          });
      }
    },
    getPartnerId() {
      const path = "/backend/profile";

      const { accessToken } = localStorage;
      if (accessToken) {
        const authUser = axios.create({ baseUrl: path });
        authUser.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
        authUser.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        authUser.defaults.headers.common["Access-Control-Allow-Methods"] =
          "GET,POST,PUT,DELETE,OPTIONS";

        authUser.defaults.headers.common["Content-Type"] =
          "application/x-www-form-urlencoded;charset=utf-8";

        authUser
          .get(path)
          .then((res) => {
            if (res.data.error) {
              throw res.data.error;
            } else {
              this.partnerId = res.data.id;
            }
          })
          .catch((error) => {
            console.error(error);
            alert(error);
            localStorage.removeItem("accessToken");
          });
      }
    },
    orderCup(cupId, partnerId, stockQuantity) {
      const path = "/backend/partner/cup/order";
      const payload = {
        cupId: parseInt(cupId),
        partnerId: partnerId,
        stockQuantity: parseInt(stockQuantity),
      };

      console.log("payload :>> ", payload);
      const { accessToken } = localStorage;

      let orderCup = axios.create();

      orderCup.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
      orderCup.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
      orderCup.defaults.headers.common["Access-Control-Allow-Methods"] =
        "GET,POST,PUT,DELETE,OPTIONS";
      orderCup.defaults.headers.common["Content-Type"] =
        "application/x-www-form-urlencoded;charset=utf-8";

      orderCup
        .post(path, payload)
        .then((res) => {
          this.makeToast("success", "컵을 주문했습니다.");
          this.$emit("getOrder");
          this.$emit("modeSwitch");
        })
        .catch((err) => {
          this.makeToast("waiting", err);
          console.log("err :>> ", err);
        });
    },
    validate() {
      if (this.selectedCup === "") {
        this.errorMsg = "발주할 컵을 선택해주세요";
      } else if (this.quantity === null) {
        this.errorMsg = "수량을 적어주세요";
      } else if (this.partnerId === null) {
        this.errorMsg = "다시 로그인해주세요";
      } else {
        this.errorMsg = "";
        this.orderCup(this.selectedCup, this.partnerId, this.quantity);
      }
    },
    makeToast(sta, message) {
      this.$emit("makeToast", { status: sta, msg: message });
    },
  },
  mounted() {
    if (localStorage.getItem("accessToken")) {
      this.getAllCups();
      this.getPartnerId();
    }
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .form-container {
      width: 100%;
      padding: 1rem;
      border: 1px solid map-get($map: $theme, $key: "border");
      border-radius: 14px;
      margin-bottom: 1rem;
      background-color: map-get($map: $theme, $key: "content-background");

      form {
        display: flex;
        align-items: center;
        justify-content: space-between;

        .form-content {
          display: flex;
          align-items: center;

          .cup-select-box {
            position: relative;
            width: 300px;
            margin-right: 1rem;

            &[open] {
              z-index: 1;

              .radios::before {
                content: "";
                display: block;
                width: 100%;
                height: 100%;
                background: transparent;
                position: fixed;
                top: 0;
                left: 0;
              }

              .radios::after {
                transform: rotate(-45deg) translate(0%, 0%);
              }
            }

            .radios {
              padding: 1rem;
              cursor: pointer;
              border-radius: 6px;
              border: 1px solid map-get($map: $theme, $key: "border");
              background-color: map-get($map: $theme, $key: "background");
              list-style: none;
              counter-reset: radios;
              display: flex;
              align-items: center;
              color: map-get($map: $theme, $key: "text");

              &::webkit-details-marker {
                display: none;
              }

              &::after {
                content: "";
                display: inline-block;
                width: 0.5rem;
                height: 0.5rem;
                border-bottom: 1px solid map-get($map: $theme, $key: "text");
                border-left: 1px solid map-get($map: $theme, $key: "text");
                border-bottom-left-radius: 2px;
                transform: rotate(45deg) translate(50%, 0%);
                transform-origin: center center;
                transition: transform 0.2s ease-in-out;
                position: absolute;
                right: 1rem;
                top: 35%;
              }

              &:focus {
                outline: none;
              }

              input[type="radio"] {
                counter-increment: radios;
                appearance: none;
                display: none;
                color: map-get($map: $theme, $key: "text");
                margin: 0;
                font-family: "Noto Serif KR", serif;

                &:checked {
                  display: inline;
                }

                &::after {
                  content: attr(title);
                  display: inline;
                  font-size: 1rem;
                }
              }
            }
            .list {
              position: absolute;
              background-color: map-get($map: $theme, $key: "background");
              counter-reset: labels;
              top: calc(100% + 0.5rem);
              left: 0;
              width: 100%;
              border-radius: 6px;
              border: 1px solid map-get($map: $theme, $key: "border");
              overflow: overlay;
              max-height: 200px;
              color: map-get($map: $theme, $key: "text-light");
              font-family: "Noto Serif KR", serif;

              &::-webkit-scrollbar {
                width: 5px;
              }

              &::-webkit-scrollbar-thumb {
                background: rgba($main-color, 1);
                border-radius: 6px;
              }

              &::-webkit-scrollbar-track {
                background: transparent;
              }

              li {
                margin: 0;

                border-bottom: 1px solid map-get($map: $theme, $key: "border");
                list-style: none;

                &:last-child {
                  border-bottom: none;
                }

                label {
                  width: 100%;
                  display: block;
                  padding: 1rem;
                  cursor: pointer;
                }
              }
            }
          }

          .cup-quantity-box {
            width: 200px;
            position: relative;
            margin: 10px;
            display: flex;
            padding: 10px 0 0;

            .cup-quantity {
              width: 100%;
              border: none;
              border-bottom: 2px solid map-get($map: $theme, $key: "border");
              outline: none;
              color: map-get($map: $theme, $key: "text");
              background-color: transparent;
              transition: all 0.2s ease;
              font-size: 1.2rem;
              padding: 0.5rem;

              &::placeholder {
                color: transparent;
              }

              &:placeholder-shown ~ .cup-quantity-label {
                cursor: text;
                font-size: 1.2rem;
                top: 10px;
                color: map-get($map: $theme, $key: "text-lighter");
              }
            }

            .cup-quantity-label {
              position: absolute;
              display: block;
              transition: 0.2s;
              color: map-get($map: $theme, $key: "border");
              top: -10px;
              padding: 0.5rem;
              font-size: 0.8rem;
            }

            .cup-quantity:focus {
              ~ .cup-quantity-label {
                color: $main-color;
                font-weight: bold;
                font-size: 0.8rem;
                position: absolute;
                top: -10px;
                display: block;
                transition: 0.2s;
                font-weight: 700;
              }
              border-bottom: 2px solid $main-color;
              padding-bottom: 6px;
            }

            span {
              color: map-get($map: $theme, $key: "text-light");
              font-size: 1.2rem;
              margin: auto;
            }
          }
        }

        .submit-error-msg {
          color: $error-msg;
          text-align: right;
          width: 100%;
          font-size: 0.85rem;
        }

        .submit-btn-wrapper {
          button {
            padding: 0.5rem 1rem;
            background-color: $main-color;
            color: $white;
            border-radius: 6px;
            border: none;
            cursor: pointer;
            width: 100px;
            margin-left: 10px;
          }
        }
      }
    }
  }
}
</style>
