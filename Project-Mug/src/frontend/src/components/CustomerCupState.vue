<template>
  <div class="container">
    <div class="card USE" :class="[cupState === 'USE' ? USE : OVERDUE]" v-if="cupState !== 'NONE'">
      <div class="fold">
        <div class="left">
          <div class="icon-wrapper">
            <font-awesome-icon :icon="['fas', 'coffee']" style="width:100%;"></font-awesome-icon>
          </div>
          <span v-if="cupState === 'USE'"> 컵 대여 중!</span>
          <span v-else-if="cupState === 'OVERDUE'"> 컵 연체 중!</span>
        </div>
        <div class="right">{{ returnTime }}</div>
      </div>
      <div class="unfold">컵 이미지, 이름, 반납기한 등이 나올 부분</div>
    </div>
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
      returnTime: "",
    };
  },
  props: ["user"],
  components: { FontAwesomeIcon },
  methods: {
    getCustomerInfo() {
      const path = `/backend/profile`;

      let customerInfo = axios.create();
      customerInfo.get(path).then((res) => {
        this.cupState = res.data.customerState;
        this.returnTime = res.data.returnDateTime;
        this.setTimeFormat();
      });
    },
    setTimeFormat() {
      const returnDateTime = new Date(this.returnTime);
      const nowDateTime = new Date();

      const diff = returnDateTime.getTime() - nowDateTime.getTime();

      const diffDay = parseInt(diff / (1000 * 60 * 60 * 24));
      const diffHour = parseInt(diff / (1000 * 60 * 60));
      const diffMin = parseInt(diff / (1000 * 60));

      console.log("returnDateTime :>> ", returnDateTime);
      console.log("nowDateTime :>> ", nowDateTime);

      let resString = "";
      if (diff < 0) {
        this.cupState = "OVERDUE";
        switch (-diffDay) {
          case 0:
            if (diffHour === 0) {
              resString = -diffMin + "분 연체";
            } else {
              resString = -diffHour + "시간 연체";
            }
            break;
          case 1:
            resString = "하루 연체";
            break;
          default:
            resString = -diffDay + "일 연체";
        }
      } else {
        switch (diffDay) {
          case 0:
            if (diffHour === 0) {
              resString = diffMin + "분 남음";
            } else {
              resString = diffHour + "시간 남음";
            }
            break;
          case 1:
            resString = "내일";
            break;
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          case 7:
            resString = diffDay + "일 남음";
            break;
          default:
            resString = `${returnDateTime.getUTCFullYear()}-${returnDateTime.getUTCMonth() +
              1}-${returnDateTime.getUTCDate()}`;
        }
      }

      this.returnTime = resString;
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
