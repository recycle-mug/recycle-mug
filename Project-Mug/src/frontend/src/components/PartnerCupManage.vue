<template>
  <div class="container">
    <div class="switch-wrapper">
      <input
        type="radio"
        v-model="radioInput"
        value="PartnerCupList"
        name="매장 내 컵 현황"
        id="option_1"
        checked
      />
      <label for="option_1" class="option option-1"><span>매장 내 컵 현황</span></label>

      <input
        type="radio"
        v-model="radioInput"
        value="PartnerOrderList"
        name="매장 내 컵 현황"
        id="option_2"
        checked
      />
      <label for="option_2" class="option option-2"><span>발주 신청 내역</span></label>
    </div>
    <!-- 매장 내 컵 현황 -->
    <div class="card-wrapper" v-if="radioInput === 'PartnerCupList'">
      <div class="card">
        <partner-cup-list></partner-cup-list>
      </div>
    </div>

    <div class="card-wrapper" v-else-if="radioInput === 'PartnerOrderList'">
      <!-- 발주 신청하기 -->
      <partner-order-cup @makeToast="makeToast" @modeSwitch="switchMode"></partner-order-cup>
      <!-- 발주 신청 내역 -->
      <div class="card">
        <partner-order-list></partner-order-list>
      </div>
    </div>
  </div>
</template>

<script>
import PartnerCupList from "./PartnerCupList.vue";
import PartnerOrderCup from "./PartnerOrderCup.vue";
import PartnerOrderList from "./PartnerOrderList.vue";

export default {
  data() {
    return {
      radioInput: "PartnerCupList",
    };
  },
  components: { PartnerCupList, PartnerOrderCup, PartnerOrderList },
  methods: {
    makeToast(toast) {
      this.$emit("makeToast", toast);
    },
    switchMode() {
      this.radioInput = "PartnerCupList";
    },
  },
  mounted() {},
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .container {
      max-width: 1140px;
      margin: 3rem auto;

      .switch-wrapper {
        width: 100%;
        display: flex;
        align-items: center;
        margin-bottom: 1rem;

        input[type="radio"] {
          display: none;
        }

        .option {
          margin-right: 1rem;
          padding: 0.5rem 1rem;
          border-radius: 6px;
          border: 1px solid map-get($map: $theme, $key: "border");
          background-color: map-get($map: $theme, $key: "content-background");
          color: map-get($map: $theme, $key: "text");
          letter-spacing: 0.05rem;
          font-size: 0.9rem;
          box-shadow: $shadow-lighter;
          cursor: pointer;
          transition: all 0.3s ease;

          &:hover {
            background-color: darken(
              $color: map-get($map: $theme, $key: "content-background"),
              $amount: 10%
            );
          }
        }

        #option_1:checked:checked ~ .option-1,
        #option_2:checked:checked ~ .option-2,
        #option_3:checked:checked ~ .option-3 {
          background-color: $main-color;
          color: $white;

          &:hover {
            background-color: darken($color: $main-color, $amount: 10%);
          }
        }
      }

      .card-wrapper {
        .card {
          width: 100%;
          background-color: map-get($map: $theme, $key: "content-background");
          overflow: hidden;
          border-radius: 14px;
          border: 1px solid map-get($map: $theme, $key: "border");
          box-shadow: $shadow-light;
        }
      }
    }
  }
}
</style>
