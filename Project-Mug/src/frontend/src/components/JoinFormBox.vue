<template>
  <div class="container">
    <div class="form-container market-form-container" v-if="role === 'market'">
      <form action="#">
        <h1>join as market</h1>
        <div class="social-container">
          <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon></a>
        </div>

        <span>or use your email for registration</span>
        <input type="text" placeholder="market ID" />
        <input type="password" placeholder="market Password" />
        <input type="password" placeholder="Password Check" @keydown.enter.prevent="telInput" />
        <div class="num-input-wrapper">
          <input
            type="number"
            name="tel_first"
            class="num-input"
            v-model="formData.joinTel.first"
            placeholder="010"
            @keydown.enter.prevent="nextTelInput"
            @keyup="limitNumber"
          />
          <div class="num-input-connector">
            <font-awesome-icon :icon="['fas', 'minus']"></font-awesome-icon>
          </div>
          <input
            type="number"
            name="tel_second"
            class="num-input"
            v-model="formData.joinTel.second"
            placeholder="0000"
            @keydown.enter.prevent="nextTelInput"
            @keyup="limitNumber"
          />
          <div class="num-input-connector">
            <font-awesome-icon :icon="['fas', 'minus']"></font-awesome-icon>
          </div>
          <input
            type="number"
            name="tel_third"
            class="num-input"
            v-model="formData.joinTel.third"
            placeholder="0000"
            @keydown.enter.prevent="onSubmitForm"
            @keyup="limitNumber"
          />
        </div>

        <get-address></get-address>
        <input type="text" placeholder="상세 주소" />
        <button>join</button>
        <router-link :to="{ name: 'login', query: { role: 'market' } }" tag="span" class="caption"
          >Already has Account</router-link
        >
      </form>
    </div>

    <div class="form-container user-form-container" v-else-if="role === 'user'">
      <form action="#">
        <h1>join as user</h1>
        <div class="social-container">
          <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon></a>
        </div>

        <span>or use your email for registration</span>
        <input type="text" placeholder="ID" />
        <input type="password" placeholder="Password" />
        <input type="password" placeholder="Password Check" @keydown.enter.prevent="telInput" />
        <div class="num-input-wrapper">
          <input
            type="number"
            name="tel_first"
            class="num-input"
            v-model="formData.joinTel.first"
            placeholder="010"
            @keydown.enter.prevent="nextTelInput"
            @keyup="limitNumber"
          />
          <div class="num-input-connector">
            <font-awesome-icon :icon="['fas', 'minus']"></font-awesome-icon>
          </div>
          <input
            type="number"
            name="tel_second"
            class="num-input"
            v-model="formData.joinTel.second"
            placeholder="0000"
            @keydown.enter.prevent="nextTelInput"
            @keyup="limitNumber"
          />
          <div class="num-input-connector">
            <font-awesome-icon :icon="['fas', 'minus']"></font-awesome-icon>
          </div>
          <input
            type="number"
            name="tel_third"
            class="num-input"
            v-model="formData.joinTel.third"
            placeholder="0000"
            @keydown.enter.prevent="onSubmitForm"
            @keyup="limitNumber"
          />
        </div>

        <button>join</button>
        <router-link :to="{ name: 'login', query: { role: 'user' } }" tag="span" class="caption"
          >Already has Account</router-link
        >
      </form>
    </div>

    <div class="overlay-container overlay-left" v-if="role === 'market'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>Welcome Back!</h1>
          <p>Hi there</p>
          <router-link :to="{ name: 'join', query: { role: 'user' } }" tag="button" class="ghost">
            join As user</router-link
          >
        </div>
      </div>
    </div>

    <div class="overlay-container overlay-right" v-else-if="role === 'user'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>Welcome Back!</h1>
          <p>Hi there</p>
          <router-link :to="{ name: 'join', query: { role: 'market' } }" tag="button" class="ghost"
            >join As market</router-link
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faFacebookF, faGooglePlusG } from "@fortawesome/free-brands-svg-icons";
import { faComment, faMinus } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

import GetAddress from "./GetAddress";

faLibrary.add(faFacebookF, faGooglePlusG, faComment, faMinus);

export default {
  data() {
    return {
      formData: {
        role: this.role,
        joinId: "",
        joinPw: "",
        joinTel: {
          first: "",
          second: "",
          third: "",
        },
      },
    };
  },
  props: ["role"],
  components: { FontAwesomeIcon, GetAddress },
  methods: {
    telInput() {
      event.target.nextElementSibling.children[0].focus();
    },
    nextTelInput() {
      event.target.nextElementSibling.nextElementSibling.focus();
    },
    limitNumber() {
      if ((event.target.name == "tel_first") & (event.target.value.length == 3)) {
        this.nextTelInput();
      }
      if ((event.target.name == "tel_first") & (event.target.value.length > 3)) {
        event.target.value = event.target.value.slice(0, 3);
        this.formData.joinTel.first = event.target.value;
      }
      if ((event.target.name == "tel_second") & (event.target.value.length == 4)) {
        this.nextTelInput();
      }
      if ((event.target.name == "tel_second") & (event.target.value.length > 4)) {
        event.target.value = event.target.value.slice(0, 4);
        this.formData.joinTel.second = event.target.value;
      }
      if ((event.target.name == "tel_third") & (event.target.value.length > 4)) {
        event.target.value = event.target.value.slice(0, 4);
        this.formData.joinTel.third = event.target.value;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    text-align: center;

    .container {
      background-color: map-get($map: $theme, $key: "content-background");
      border-radius: 10px;
      box-shadow: $shadow;
      position: relative;
      overflow: hidden;
      width: 1050px;
      max-width: 100%;
      min-height: 720px;
      margin: auto;

      .form-container {
        position: absolute;
        top: 0;
        height: 100%;
        transition: all 0.6s ease-in-out;

        form {
          background-color: map-get($map: $theme, $key: "content-background");
          display: flex;
          align-items: center;
          justify-content: center;
          flex-direction: column;
          padding: 1rem 3rem;
          height: 100%;
          text-align: center;

          h1 {
            font-weight: bold;
            margin: 0.5rem;
            font-size: 1.5rem;
          }

          .social-container {
            margin: 0.7rem 0;
            a {
              color: $main-color;
              border: 1px solid $main-color;
              border-radius: 50%;
              display: inline-flex;
              justify-content: center;
              align-items: center;
              margin: 0 0.1rem;
              height: 40px;
              width: 40px;
              transition: all 0.2s linear;

              &:hover {
                box-shadow: $shadow;
                background: $main-color;
                border: 1px solid white;
                color: white;
              }
            }
          }

          span {
            font-size: 0.7rem;
            margin-bottom: 0.5rem;
          }

          input {
            background-color: map-get($map: $theme, $key: "background");
            color: map-get($map: $theme, $key: "text");
            border: none;
            padding: 1rem 1.4rem;
            margin: 0.7rem 0;
            width: 100%;
            box-sizing: border-box;

            &:focus {
              outline-color: $sub-color;
              border: none;
            }
          }

          .num-input-wrapper {
            display: flex;
            justify-content: space-between;
            border: none;
            padding: 1rem 1.4rem;
            margin: 0.7rem 0;
            width: 100%;

            .num-input-connector {
              display: flex;
              justify-content: center;
              align-items: center;
              font-size: 0.6rem;
              margin: 3px;
              color: $main-color;
            }

            .num-input {
              margin: 0 1rem;
              height: 43px;
              box-sizing: border-box;
              font-weight: 700;
              text-align: center;
              transition: border-color 0.2s ease-in-out;
              background-color: map-get($map: $theme, $key: "background");

              &:focus {
                outline-color: $sub-color;
                border: none;
                box-sizing: border-box;
              }
            }
          }

          button {
            border-radius: 20px;
            margin-top: 1rem;
            border: 1px solid $main-color;
            background-color: $main-color;
            color: $white;
            border-radius: 20px;
            font-size: 1rem;
            font-weight: bold;
            padding: 1rem 3rem;
            text-transform: uppercase;
            cursor: pointer;

            &:active {
              transform: scale(0.95);
            }

            &:focus {
              outline: none;
            }

            &:hover {
              transform: scale(1.05);
              transition: all 0.2s ease-in-out;
            }
          }

          .caption {
            margin: 1rem;
            text-decoration: underline;
            cursor: pointer;
            font-size: 0.75rem;
          }
        }
      }

      .market-form-container {
        right: 0;
        width: 50%;
        opacity: 1;
        z-index: 1;
      }

      .user-form-container {
        left: 0;
        width: 50%;
        z-index: 2;
      }

      .overlay-container {
        position: absolute;
        top: 0;
        width: 50%;
        height: 100%;
        overflow: hidden;
        transform: translateX(0);
        transition: transform 0.6s ease-in-out;

        .overlay {
          background-color: $main-color;
          color: $white;
          position: relative;
          height: 100%;
          width: 100%;
          transform: translateX(0);
          transition: transform 0.6s ease-in-out;

          .overlay-panel {
            position: absolute;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            padding: 0 3rem;
            text-align: center;
            top: 0;
            height: 100%;
            width: 100%;
            transform: translateX(0);
            transition: transform 0.6s ease-in-out;
            box-sizing: border-box;

            h1 {
              font-weight: bold;
              margin: 0.5rem;
              font-size: 1.5rem;
            }

            p {
              letter-spacing: 0.1rem;
              margin: 1.5rem 0 2rem;
              line-height: 1.2rem;
            }

            button {
              border-radius: 20px;
              border: 1px solid $white;
              background-color: transparent;
              color: $white;
              border-radius: 20px;
              font-weight: bold;
              padding: 1rem 3rem;
              letter-spacing: 0.05rem;
              cursor: pointer;

              &:active {
                transform: scale(0.95);
              }

              &:focus {
                outline: none;
              }

              &:hover {
                transition: all 0.2s ease-in-out;
                border: 1px solid $white;
                background-color: $white;
                color: $main-color;
              }
            }
          }
        }
      }

      .overlay-left {
        left: 0;
      }

      .overlay-right {
        right: 0;
      }
    }
  }
}
</style>
