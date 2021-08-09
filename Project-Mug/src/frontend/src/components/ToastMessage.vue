<template>
  <div :class="styleObject">
    <div class="wrapper">
      <div class="img">
        <font-awesome-icon
          :icon="['fas', 'exclamation-triangle']"
          style="width:100%;"
          v-if="status === 'error'"
        ></font-awesome-icon>

        <font-awesome-icon
          :icon="['fas', 'exclamation']"
          style="width:100%;"
          v-if="status === 'waiting'"
        ></font-awesome-icon>

        <font-awesome-icon
          :icon="['fas', 'check']"
          style="width:100%;"
          v-if="status === 'success'"
        ></font-awesome-icon>
      </div>
      <div class="message">{{ msg }}</div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faExclamationTriangle, faExclamation, faCheck } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faExclamationTriangle, faExclamation, faCheck);

export default {
  props: ["status", "msg"],
  data() {
    return {
      styleObject: {
        toast: true,
        show: false,
        error: false,
        waiting: false,
        success: false,
      },
    };
  },
  components: { FontAwesomeIcon },
  methods: {
    launchToast() {
      this.styleObject.show = true;

      setTimeout(() => {
        this.styleObject.show = false;
      }, 5000);
    },
  },
  mounted() {
    if (this.status === "error") {
      this.styleObject.error = true;
    } else if (this.status === "waiting") {
      this.styleObject.waiting = true;
    } else if (this.status === "success") {
      this.styleObject.success = true;
    }

    this.launchToast();
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    @-webkit-keyframes fadein {
      from {
        bottom: 0;
        opacity: 0;
      }
      to {
        bottom: 30px;
        opacity: 1;
      }
    }

    @keyframes fadein {
      from {
        bottom: 0;
        opacity: 0;
      }
      to {
        bottom: 30px;
        opacity: 1;
      }
    }

    @-webkit-keyframes expand {
      from {
        min-width: 50px;
      }
      to {
        min-width: 350px;
      }
    }

    @keyframes expand {
      from {
        min-width: 50px;
      }
      to {
        min-width: 350px;
      }
    }
    @-webkit-keyframes stay {
      from {
        min-width: 350px;
      }
      to {
        min-width: 350px;
      }
    }

    @keyframes stay {
      from {
        min-width: 350px;
      }
      to {
        min-width: 350px;
      }
    }
    @-webkit-keyframes shrink {
      from {
        min-width: 350px;
      }
      to {
        min-width: 50px;
      }
    }

    @keyframes shrink {
      from {
        min-width: 350px;
      }
      to {
        min-width: 50px;
      }
    }

    @-webkit-keyframes fadeout {
      from {
        bottom: 30px;
        opacity: 1;
      }
      to {
        bottom: 60px;
        opacity: 0;
      }
    }

    @keyframes fadeout {
      from {
        bottom: 30px;
        opacity: 1;
      }
      to {
        bottom: 60px;
        opacity: 0;
      }
    }

    .toast {
      visibility: hidden;
      max-width: 50px;
      height: 50px;
      margin: auto;
      color: #fff;
      text-align: center;
      border-radius: 2px;
      user-select: none;
      position: fixed;
      z-index: 9999;
      left: 0;
      right: 0;
      bottom: 30px;
      font-size: 17px;
      white-space: nowrap;

      &.error {
        background-color: $error-msg;

        .img {
          background-color: darken($color: $error-msg, $amount: 10%);
        }
      }

      &.waiting {
        background-color: $sub-color;
        .img {
          background-color: darken($color: $sub-color, $amount: 10%);
        }
      }

      &.success {
        background-color: $main-color;
        .img {
          background-color: darken($color: $main-color, $amount: 10%);
        }
      }

      &.show {
        visibility: visible;
        -webkit-animation: fadein 0.5s, expand 0.5s 0.5s, stay 3s 1s, shrink 0.5s 2s,
          fadeout 0.5s 2.5s;
        animation: fadein 0.5s, expand 0.5s 0.5s, stay 3s 1s, shrink 0.5s 4s, fadeout 0.5s 4.5s;

        .message {
          visibility: visible;
        }
      }

      .img {
        width: 50px;
        height: 50px;
        float: left;
        padding-top: 16px;
        padding-bottom: 16px;
        box-sizing: border-box;
        user-select: none;
      }

      .message {
        padding: 16px;
        overflow: hidden;
        white-space: nowrap;
        user-select: none;
      }
    }

    .wrapper {
      width: 100%;
      height: 100%;
      position: relative;
      overflow: hidden;
    }
  }
}
</style>
