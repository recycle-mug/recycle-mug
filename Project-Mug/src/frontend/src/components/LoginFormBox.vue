<template>
  <div class="container">
    <div class="form-container store-form-container" v-if="role === 'market'">
      <form action="#">
        <h1>Login as Store</h1>
        <div class="social-container">
          <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon></a>
        </div>

        <span>or use your email for registration</span>
        <div class="row">
          <input
            type="text"
            placeholder="Store ID"
            v-model="formData.loginId"
            @keydown.enter.prevent="nextInput"
            @keyup="checkId"
          />
          <span class="error-msg">{{ errors.loginId }}</span>
        </div>
        <div class="row">
          <input
            type="password"
            placeholder="Store Password"
            v-model="formData.loginPw"
            @keydown.enter.prevent="onSubmit"
            @keyup.prevent="checkPassword"
          />
          <span class="error-msg">{{ errors.loginPw }}</span>
        </div>
        <button type="submit" @click.prevent="onSubmit">Login</button>
        <router-link to="../join/market" tag="span" class="caption"
          >Create Store Account</router-link
        >
      </form>
    </div>

    <div class="form-container member-form-container" v-else-if="role === 'user'">
      <form action="#">
        <h1>Login as Member</h1>
        <div class="social-container">
          <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon></a>
        </div>

        <span>or use your email for registration</span>
        <div class="row">
          <input
            type="text"
            placeholder="ID"
            v-model="formData.loginId"
            @keydown.enter.prevent="nextInput"
            @keyup="checkId"
          />
          <span class="error-msg">{{ errors.loginId }}</span>
        </div>

        <div class="row">
          <input
            type="password"
            placeholder="Password"
            v-model="formData.loginPw"
            @keydown.enter.prevent="onSubmit"
            @keyup="checkPassword"
          />
          <span class="error-msg">{{ errors.loginPw }}</span>
        </div>
        <button type="submit" @click.prevent="onSubmit">Login</button>
        <router-link to="../join/user" tag="span" class="caption"
          >Create Member Account</router-link
        >
      </form>
    </div>

    <div class="overlay-container overlay-left" v-if="role === 'market'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>Welcome Back!</h1>
          <p>Hi there</p>
          <router-link to="../login/user" tag="button" class="ghost"> Login As Member</router-link>
        </div>
      </div>
    </div>

    <div class="overlay-container overlay-right" v-else-if="role === 'user'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>Welcome Back!</h1>
          <p>Hi there</p>
          <router-link to="../login/market" tag="button" class="ghost">Login As Store</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faFacebookF, faGooglePlusG } from "@fortawesome/free-brands-svg-icons";
import { faComment } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faFacebookF, faGooglePlusG, faComment);

export default {
  data() {
    return {
      formData: {
        role: this.role,
        loginId: "",
        loginPw: "",
      },
      errors: {
        loginId: "",
        loginPw: "",
      },
    };
  },
  props: ["role"],
  components: { FontAwesomeIcon },
  methods: {
    nextInput() {
      event.target.parentElement.nextElementSibling.children[0].focus();
    },
    checkRequired() {
      for (const item in this.formData) {
        try {
          if (!this.formData[item]) {
            throw "빈 칸을 모두 채워주세요";
          }
        } catch (error) {
          this.errors[item] = error;
        }
      }
    },
    checkId() {
      const pattern_loginId = /^[a-zA-Z가-힣0-9]{4,20}$/;
      try {
        if (!pattern_loginId.test(this.formData.loginId)) {
          throw "한글, 영어, 숫자로만 이루어진 4~20글자로 입력해주세요";
        } else {
          console.log("test :>> ");
          this.errors.loginId = "";
        }
      } catch (error) {
        this.errors.loginId = error;
      }
    },
    checkPassword() {
      const pattern_loginPw = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,}$/;

      try {
        if (!pattern_loginPw.test(this.formData.loginPw)) throw "비밀번호를 확인해주세요";
        else this.errors.loginPw = "";
      } catch (error) {
        this.errors.loginPw = error;
      }
    },
    validate() {
      this.checkRequired();
      this.checkId();
      this.checkPassword();
    },
    onSubmit(e) {
      e.preventDefault();
      this.validate();

      console.log("this.formData.role :>> ", this.formData.role);
      console.log("this.formData.loginId :>> ", this.formData.loginId);
      console.log("this.formData.loginPw :>> ", this.formData.loginPw);
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
      width: 768px;
      max-width: 100%;
      min-height: 480px;
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
          padding: 0 3rem;
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

          .row {
            width: 100%;
            box-sizing: border-box;
            display: flex;
            flex-direction: column;

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
            .error-msg {
              color: $error-msg;
              width: 100%;
              text-align: left;
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

            &:active {
              transform: scale(0.95);
            }

            &:focus {
              outline: none;
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

      .store-form-container {
        right: 0;
        width: 50%;
        opacity: 1;
        z-index: 1;
      }

      .member-form-container {
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

              &:active {
                transform: scale(0.95);
              }

              &:focus {
                outline: none;
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
