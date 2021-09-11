<template>
  <div class="container">
    <div v-if="opened" style="position:absolute;top:0;left:0;" @click="closeModal()">
      <div class="modal-wrapper">
        <div class="modal" @click.stop>
          <rent-cup
            v-if="scannerSelect === 'rent'"
            @closeModal="closeModal"
            @makeToast="makeToast"
            :url="cupUrl"
          ></rent-cup>
          <return-cup
            v-if="scannerSelect === 'return'"
            @closeModal="closeModal"
            @makeToast="makeToast"
            :url="cupUrl"
          ></return-cup>
        </div>
      </div>
    </div>

    <div class="scanner-container">
      <h1 v-if="scanStart" class="scanner-description">
        <div class="icon-wrapper">
          <font-awesome-icon :icon="['fas', 'qrcode']" style="width:100%;"></font-awesome-icon>
        </div>
        QR코드를 카메라 안에 비춰주세요
      </h1>
      <div class="scanner-wrapper" v-if="scanStart">
        <qrcode-stream
          class="scanner"
          :track="this.onScan"
          @init="onInit"
          :camera="camera"
          @decode="onDecode"
          v-if="!destroyed"
        >
          <div v-if="validationSuccess" class="validation-success">
            This is a URL
          </div>

          <div v-if="validationFailure" class="validation-failure">
            잘못된 QR 코드 형식입니다.
          </div>

          <div v-if="validationPending" class="validation-pending">
            QR 코드 읽는 중...
          </div>

          <loader v-if="loading"></loader>
        </qrcode-stream>
      </div>
      <button class="scanner-btn" @click="scanStart = false" v-if="scanStart">스캔 중지</button>
      <button class="scanner-btn" @click="scanStart = true" v-if="!scanStart">
        <div class="icon-wrapper">
          <font-awesome-icon :icon="['fas', 'camera']" style="width:100%;"></font-awesome-icon>
        </div>
        QR 코드 스캔하기
      </button>
    </div>
  </div>
</template>

<script>
import { QrcodeStream } from "vue-qrcode-reader";
import Loader from "./Loader.vue";
import RentCup from "./RentCup.vue";
import ReturnCup from "./ReturnCup.vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faQrcode, faCamera } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faQrcode, faCamera);

export default {
  components: { QrcodeStream, Loader, ReturnCup, RentCup, FontAwesomeIcon },
  data() {
    return {
      scanStart: false,
      scanValue: "",
      loading: false,
      destroyed: false,
      isValid: undefined,
      camera: "auto",
      getUrl: undefined,
      opened: false,
      scannerSelect: "",
      cupUrl: "",
    };
  },
  props: ["role"],
  methods: {
    async onScan(detectedCodes, ctx) {
      for (const detectedCode of detectedCodes) {
        const {
          boundingBox: { x, y, width, height },
        } = detectedCode;

        ctx.lineWidth = 2;
        ctx.strokeStyle = "#007bff";
        ctx.strokeRect(x, y, width, height);
      }
    },
    async onDecode(content) {
      this.scanValue = content;
      this.turnCameraOff();

      this.isValid = true;

      if (content.startsWith("/backend/customer/rent-cup")) {
        this.opened = true;
        this.scannerSelect = "rent";
        this.cupUrl = content;
        this.isValid = true;
      } else if (content.startsWith("/backend/customer/return-cup")) {
        this.opened = true;
        this.scannerSelect = "return";
        this.cupUrl = content;
        this.isValid = true;
      } else {
        this.opened = false;
        this.scannerSelect = "";
        this.cupUrl = "";
        this.isValid = undefined;
        this.turnCameraOn();
      }
    },
    async onInit(promise) {
      this.loading = true;

      try {
        await promise;
      } catch (error) {
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    turnCameraOn() {
      this.camera = "auto";
    },
    turnCameraOff() {
      this.camera = "off";
      this.isValid = undefined;
    },
    timeout(ms) {
      return new Promise((resolve) => {
        window.setTimeout(resolve, ms);
      });
    },
    closeModal() {
      this.opened = false;
      this.orderCupId = null;
      this.scannerSelect = "";
      this.cupUrl = "";
      this.isValid = false;
      this.turnCameraOn();
    },
    makeToast(toast) {
      this.$emit("makeToast", toast);
    },
  },
  computed: {
    validationPending() {
      return this.getUrl === undefined && this.camera === "off";
    },

    validationSuccess() {
      return this.getUrl === true && this.camera === "off";
    },

    validationFailure() {
      return this.getUrl === false && this.camera === "off";
    },
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    .container {
      max-width: 1140px;
      margin: auto;
      padding: 0 1rem;

      .modal-wrapper {
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.75);
        position: fixed;
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 99;
        top: 0;
        left: 0;

        .modal {
          width: 720px;
          max-width: 90%;
          max-height: 90%;
          background-color: map-get($map: $theme, $key: "content-background");
          z-index: 999;
          position: fixed;
          border-radius: 6px;
        }
      }

      .scanner-container {
        width: 100%;
        padding: 1rem;
        background-color: map-get($map: $theme, $key: "content-background");
        box-sizing: border-box;
        box-shadow: $shadow-lighter;
        border-radius: 6px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        .scanner-wrapper {
          width: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          border-radius: 6px;

          .scanner {
            margin: 0.5rem;
            box-sizing: border-box;
            box-shadow: $shadow-lighter;
            border-radius: 30px;
            border: 1px solid map-get($map: $theme, $key: "border");
            overflow: hidden;
            transition: all 0.3s ease-in-out;
            width: 350px;
            height: 350px;
            background-color: map-get($map: $theme, $key: "content-background");
          }
        }

        .scanner-description {
          margin: 0.5rem;
          white-space: nowrap;
          display: flex;
          align-items: center;
          justify-content: center;
        }

        .scanner-btn {
          margin: 0.5rem;
          white-space: nowrap;
          display: flex;
          align-items: center;
          justify-content: center;
          padding: 1rem;
          background-color: $main-color;
          border-radius: 6px;
          border: none;
          color: $white;
          cursor: pointer;

          &:active {
            transform: scale(0.95);
          }

          &:focus {
            outline: none;
          }

          &:hover {
            transition: all 0.2s ease-in-out;
            transform: scale(1.05);
          }
        }
      }

      .icon-wrapper {
        width: 30px;
      }

      .validation-success,
      .validation-failure,
      .validation-pending {
        width: 100%;
        height: 100%;
        background-color: rgba(map-get($map: $theme, $key: "content-background"), 0.8);
        display: flex;
        justify-content: center;
        align-items: center;
        text-align: center;
      }

      .validation-success {
        color: green;
      }

      .validation-failure {
        color: $error-msg;
      }
    }
  }
}
</style>
