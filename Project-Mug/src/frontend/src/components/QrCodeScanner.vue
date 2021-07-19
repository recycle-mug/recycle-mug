<template>
  <div class="container">
    <p class="decode-result">
      Last result: <b>{{ scanValue }}</b>
    </p>

    <qrcode-stream
      class="scanner-container"
      style="width:500px"
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
        This is NOT a URL!
      </div>

      <div v-if="validationPending" class="validation-pending">
        Reading QR...
      </div>
      <div class="loader-wrapper" v-if="loading">
        <div class="loader">
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
        </div>
      </div>
    </qrcode-stream>
  </div>
</template>

<script>
import { QrcodeStream } from "vue-qrcode-reader";

export default {
  components: { QrcodeStream },
  data() {
    return {
      scanValue: "",
      loading: false,
      destroyed: false,
      isValid: undefined,
      camera: "auto",
    };
  },
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

      // pretend it's taking really long
      await this.timeout(3000);
      this.isValid = content.startsWith("http");

      // some more delay, so users have time to read the message
      await this.timeout(2000);

      this.turnCameraOn();
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
  },
  computed: {
    validationPending() {
      return this.isValid === undefined && this.camera === "off";
    },

    validationSuccess() {
      return this.isValid === true && this.camera === "off";
    },

    validationFailure() {
      return this.isValid === false && this.camera === "off";
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  max-width: 1140px;
  margin: auto;

  .scanner-container {
    margin: 0.5rem;
    box-sizing: border-box;
    box-shadow: $shadow-lighter;
    border-radius: 30px;
    overflow: hidden;
    transition: all 0.3s ease-in-out;
    width: 100%;
    height: 100%;
    background-color: white;
  }

  .validation-success,
  .validation-failure,
  .validation-pending {
    width: 100%;
    height: 100%;
    background-color: rgba(white, 0.8);
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

  .loader-wrapper {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

    .loader {
      width: 150px;
      height: 150px;
      display: grid;
      grid-template-columns: 1fr 1fr 1fr;
      grid-gap: 8px;

      div {
        width: 30px;
        height: 30px;
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: $main-color;
        border-radius: 50%;

        &::before,
        &::after {
          content: "";
          width: 30px;
          height: 30px;
          position: absolute;
          border-radius: 50%;
        }

        &::before {
          background-color: $sub-color;
          animation: scale-1 2400ms linear infinite;
        }

        &::after {
          background-color: $main-color;
          animation: scale-2 2400ms linear infinite;
        }

        &:nth-child(2)::before,
        &:nth-child(2)::after {
          animation-delay: 300ms;
        }
        &:nth-child(3)::before,
        &:nth-child(3)::after {
          animation-delay: 600ms;
        }
        &:nth-child(4)::before,
        &:nth-child(4)::after {
          animation-delay: 900ms;
        }
        &:nth-child(5)::before,
        &:nth-child(5)::after {
          animation-delay: 1200ms;
        }
        &:nth-child(6)::before,
        &:nth-child(6)::after {
          animation-delay: 1500ms;
        }
        &:nth-child(7)::before,
        &:nth-child(7)::after {
          animation-delay: 1800ms;
        }
        &:nth-child(8)::before,
        &:nth-child(8)::after {
          animation-delay: 2100ms;
        }
        &:nth-child(9)::before,
        &:nth-child(9)::after {
          animation-delay: 2400ms;
        }

        @keyframes scale-1 {
          0% {
            transform: scale(0);
            z-index: 2;
          }
          50%,
          100% {
            transform: scale(1);
          }
        }

        @keyframes scale-2 {
          0%,
          50% {
            transform: scale(0);
          }
          100% {
            transform: scale(1);
          }
        }
      }
    }
  }
}
</style>
