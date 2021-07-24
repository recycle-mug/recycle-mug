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
            <input type="radio" name="cup" id="cup1" title="cup 1" @click="selectCup" />
            <input type="radio" name="cup" id="cup2" title="cup 2" @click="selectCup" />
            <input type="radio" name="cup" id="cup3" title="cup 3" @click="selectCup" />
            <input type="radio" name="cup" id="cup4" title="cup 4" @click="selectCup" />
            <input type="radio" name="cup" id="cup5" title="cup 5" @click="selectCup" />
          </summary>
          <ul class="list">
            <li>
              <label for="cup1">cup 1</label>
            </li>
            <li>
              <label for="cup2">cup 2</label>
            </li>
            <li>
              <label for="cup3">cup 3</label>
            </li>
            <li>
              <label for="cup4">cup 4</label>
            </li>
            <li>
              <label for="cup5">cup 5</label>
            </li>
          </ul>
        </details>

        <div class="cup-quantity-box">
          <input
            type="text"
            name="cup_quantity"
            id="cup_quantity"
            placeholder="수량"
            class="cup-quantity"
            required
          />
          <label for="cup_quantity" class="cup-quantity-label">수량</label>
          <span>개</span>
        </div>
      </div>

      <div class="submit-btn-wrapper">
        <button type="submit">발주 신청</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedCup: "",
      quantity: 0,
    };
  },
  methods: {
    selectCup(e) {
      this.selectedCup = e.target.id;
    },
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

        .submit-btn-wrapper {
          button {
            padding: 0.5rem 1rem;
            background-color: $main-color;
            color: $white;
            border-radius: 6px;
            border: none;
            cursor: pointer;
          }
        }
      }
    }
  }
}
</style>
