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
          <span class="dot"></span>
          <div class="dots">
            <span></span>
            <span></span>
            <span></span>
          </div>
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
      position: absolute;
      top: 50%;
      left: 50%;
      width: 142px;
      height: 40px;
      margin: -20px 0 0 -71px;
      background: white;
      filter: contrast(20);

      .dot {
        position: absolute;
        width: 16px;
        height: 16px;
        top: 12px;
        left: 15px;
        filter: blur(4px);
        background: #000;
        border-radius: 50%;
        transform: translateX(0);
        animation: dot 2.8s infinite;
      }

      .dots {
        transform: translateX(0);
        margin-top: 12px;
        margin-left: 31px;
        animation: dots 2.8s infinite;

        span {
          display: block;
          float: left;
          width: 16px;
          height: 16px;
          margin-left: 10px;
          filter: blur(4px);
          background: #000;
          border-radius: 50%;
        }
      }

      @keyframes dot {
        50% {
          transform: translateX(96px);
        }
      }
      @keyframes dots {
        50% {
          transform: translateX(-31px);
        }
      }
    }
  }
}
</style>
