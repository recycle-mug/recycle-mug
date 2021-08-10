<template>
  <div class="card">
    <nav>
      <div class="icon-wrapper" style="float:left;" @click="closeThisModal()">
        <font-awesome-icon :icon="['fas', 'times']" style="width:100%;"></font-awesome-icon>
      </div>
      <span @click="closeThisModal()">Close This Page</span>
    </nav>

    <div class="card-body">
      <div class="row" v-if="info.cupName">
        <div class="title">{{ info.cupName }} 대여 승인하기</div>
      </div>
      <div class="row" v-if="info.cupImage">
        <img :src="'data:image/jpeg;base64,' + info.cupImage" alt="" />
      </div>
      <div class="row">
        <button @click="rentCup">컵 대여 승인하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";
import { faTimes } from "@fortawesome/free-solid-svg-icons";

faLibrary.add(faTimes);

export default {
  components: { FontAwesomeIcon },
  data() {
    return {
      info: {},
    };
  },
  props: ["url"],
  methods: {
    closeThisModal() {
      this.$emit("closeModal");
    },
    getCupInfo() {
      let getCupInfo = axios.create();

      getCupInfo
        .get(this.url)
        .then((res) => {
          this.info = res.data;
          console.log("this.info :>> ", this.info);
        })
        .catch((err) => {
          console.log("err :>> ", err.response);
        });
    },
    rentCup() {
      let rentCup = axios.create();
      const payload = {
        customerId: this.info.customerId,
        partnerCupId: this.info.cupId,
      };
      rentCup
        .post(this.url.split("?")[0], payload)
        .then((res) => {
          if (res.status === 200) {
            this.makeToast("success", "컵을 대여했습니다.");
            this.closeThisModal();
          }
        })
        .catch((err) => {
          console.log("err :>> ", err.response);
          if (err.response.data.message === "Customer state not allowed") {
            this.makeToast("error", "컵 대여 실패");
            alert("이미 컵을 대여 중인 고객입니다.");
          }
        });
    },
    makeToast(sta, message) {
      this.$emit("makeToast", { status: sta, msg: message });
    },
  },
  mounted() {
    this.getCupInfo();
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .card {
      width: 100%;
      height: 100%;
      overflow: auto;

      nav {
        width: 100%;
        color: map-get($map: $theme, $key: "text-light");
        padding: 1rem;
        border-bottom: 2px solid map-get($map: $theme, $key: "border");
        display: flex;
        align-items: center;

        .icon-wrapper {
          width: 50px;
          align-items: center;
          cursor: pointer;
          padding: 10px;
          transition: all 0.3s ease;

          &:hover {
            transform: scale(1.2);
          }
        }

        .hover-change:hover {
          color: $error-msg;
          transform: scale(1.2);
        }

        span {
          user-select: none;
          width: 100%;
          cursor: pointer;
        }
      }

      .card-body {
        width: 100%;
        padding: 1rem;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        .row {
          width: 100%;
          padding: 1rem;
          display: flex;
          justify-content: center;
          align-items: center;

          .title {
            font-size: 1.2rem;
            color: map-get($map: $theme, $key: "text");
          }

          img {
            width: 100%;
            height: 100%;
            max-width: 300px;
            max-height: 300px;
            object-fit: contain;
          }
        }
      }
    }

    button {
      padding: 1rem;
      cursor: pointer;
      border-radius: 6px;
      border: 1px solid map-get($map: $theme, $key: "border");
      color: map-get($map: $theme, $key: "text");
      background-color: map-get($map: $theme, $key: "border");
      box-shadow: $shadow-light;
      font-size: 1rem;

      &:hover,
      &:active {
        color: $white;
        background-color: $main-color;
        box-shadow: $shadow;
        transition: all 0.3s ease;
        transform: translateY(-2px);
      }
    }
  }
}
</style>
