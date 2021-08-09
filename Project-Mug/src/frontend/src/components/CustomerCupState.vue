<template>
  <div class="container">
    <div class="card USE" v-if="cupState === 'USE'">
      <div class="fold">
        <div class="left">
          <div class="icon-wrapper">
            <font-awesome-icon :icon="['fas', 'coffee']" style="width:100%;"></font-awesome-icon>
          </div>
          <span> 컵 대여 중!</span>
        </div>
        <div class="right">21시간 남음</div>
      </div>
      <div class="unfold">컵 이미지, 이름, 반납기한 등이 나올 부분</div>
    </div>
    <div class="card OVERDUE" v-else-if="cupState === 'OVERDUE'">
      <div class="fold">
        <div class="left">
          <div class="icon-wrapper">
            <font-awesome-icon :icon="['fas', 'coffee']" style="width:100%;"></font-awesome-icon>
          </div>
          <span> 컵 연체 중!</span>
        </div>
        <div class="right">1시간 초과</div>
      </div>
      <div class="unfold">컵 이미지, 이름, 반납기한 등이 나올 부분</div>
    </div>
    <div class="card NONE" v-else></div>
  </div>
</template>

<script>
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faCoffee } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faCoffee);

export default {
  data() {
    return {
      cupState: "",
    };
  },
  props: ["user"],
  components: { FontAwesomeIcon },
  methods: {
    getCustomerInfo() {
      const path = `/backend/customer/${this.user.id}`;

      let customerInfo = axios.create();
      customerInfo.get(path).then((res) => {
        console.log("res :>> ", res);
        this.cupState = res.data.customerState;
      });
    },
  },
  mounted() {
    this.getCustomerInfo();
  },
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
      margin: auto;

      .card {
        background-color: map-get($map: $theme, $key: "content-background");
        border: 1px solid map-get($map: $theme, $key: "border");
        border-radius: 12px;
        padding: 1rem;
        margin-bottom: 1.5rem;
        box-shadow: $shadow-light;
        transition: all 0.4s ease;
        height: 50px;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;
        position: relative;

        &:hover {
          height: 250px;
          .fold {
            display: none;
          }
          .unfold {
            display: block;
          }
        }

        &.USE {
          background-color: $main-color;
          color: $white;
        }
        &.NONE {
          display: none;
        }
        &.OVERDUE {
          background-color: $error-msg;
          color: $white;
        }

        .fold {
          width: 100%;
          display: flex;
          justify-content: space-between;
          align-items: center;

          .left {
            display: flex;

            .icon-wrapper {
              width: 30px;
              margin-right: 0.5rem;
            }
          }

          .right {
            color: $light-gray;
          }
        }

        .unfold {
          width: 100%;
          display: none;
        }
      }
    }
  }
}
</style>
