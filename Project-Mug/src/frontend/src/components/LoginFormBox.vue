<template>
  <div class="wrapper">
    <div class="container">
      <div class="form-container partner-form-container" v-if="role === 'partner'">
        <form action="#">
          <h1>파트너 로그인하기</h1>
          <div class="social-container">
            <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
            <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
            <a href="#"><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon></a>
          </div>

          <span>or use your email for registration</span>
          <div class="row">
            <input
              type="email"
              placeholder="partner ID (Email)"
              v-model="formData.loginId"
              @keydown.enter.prevent="nextInput"
              @keyup="checkId"
            />
            <span class="error-msg">{{ errors.loginId }}</span>
          </div>
          <div class="row">
            <input
              type="password"
              placeholder="partner Password"
              maxlength="20"
              v-model="formData.loginPw"
              @keydown.enter.prevent="onSubmit"
              @keyup.prevent="checkPassword"
            />
            <span class="error-msg">{{ errors.loginPw }}</span>
          </div>
          <span class="error-msg">{{ errors.response }}</span>
          <button type="submit" @click.prevent="onSubmit">Login</button>
          <router-link :to="{ name: 'join', query: { role: 'partner' } }" tag="span" class="caption"
            >파트너 계정이 없으신가요?</router-link
          >
        </form>
      </div>

      <div class="form-container customer-form-container" v-else-if="role === 'customer'">
        <form action="#">
          <h1>일반 사용자로 로그인하기</h1>
          <div class="social-container">
            <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
            <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
            <a href="#"><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon></a>
          </div>

          <span>or use your email for registration</span>
          <div class="row">
            <input
              type="email"
              placeholder="ID (Email)"
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
              maxlength="20"
              v-model="formData.loginPw"
              @keydown.enter.prevent="onSubmit"
              @keyup="checkPassword"
            />
            <span class="error-msg">{{ errors.loginPw }}</span>
          </div>
          <span class="error-msg">{{ errors.response }}</span>
          <button type="submit" @click.prevent="onSubmit">Login</button>
          <router-link
            :to="{ name: 'join', query: { role: 'customer' } }"
            tag="span"
            class="caption"
            >계정이 없으신가요?</router-link
          >
        </form>
      </div>

      <div class="overlay-container overlay-left" v-if="role === 'partner'">
        <div class="overlay">
          <div class="overlay-panel ">
            <h1>위컵 사용자이신가요?</h1>
            <p>일반 로그인하러가기 👉</p>
            <router-link
              :to="{ name: 'login', query: { role: 'customer' } }"
              tag="button"
              class="ghost"
            >
              Login As Customer</router-link
            >
          </div>
        </div>
      </div>

      <div class="overlay-container overlay-right" v-else-if="role === 'customer'">
        <div class="overlay">
          <div class="overlay-panel ">
            <h1>카페 사장님이신가요?</h1>
            <p>파트너 로그인하러가기 👉</p>
            <router-link
              :to="{ name: 'login', query: { role: 'partner' } }"
              tag="button"
              class="ghost"
              >Login As Partner</router-link
            >
          </div>
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
        response: "",
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
      const pattern_loginId = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

      try {
        if (!pattern_loginId.test(this.formData.loginId)) {
          throw "이메일 형식을 확인해주세요";
        } else {
          this.errors.loginId = "";
        }
      } catch (error) {
        this.errors.loginId = error;
      }
    },
    checkPassword() {
      const pattern_loginPw = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d~!@#$%^&*]{8,}$/;

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

      const role = this.formData.role;
      const email = this.formData.loginId;
      const password = this.formData.loginPw;

      if (!this.errors.loginId && !this.errors.loginPw) {
        this.$store
          .dispatch("LOGIN", { role, email, password })
          .then(this.redirect)
          .catch((err) => (this.errors.response = err));
      }
    },
    redirect() {
      console.log("Login Success");
      this.$router.push({ name: "main" });
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

    .wrapper {
      background-color: map-get($map: $theme, $key: "background");
      box-sizing: border-box;
      width: 100%;
      height: 100%;

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
              user-select: none;
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
                padding-left: 1rem;
                user-select: none;
                height: 0.8rem;
              }
            }

            .error-msg {
              color: $error-msg;
              width: 100%;
              text-align: center;
              user-select: none;
              height: 0.8rem;
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
                transition: all 0.2s ease-in-out;
                transform: scale(1.05);
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

        .partner-form-container {
          right: 0;
          width: 50%;
          opacity: 1;
          z-index: 1;
        }

        .customer-form-container {
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
}
</style>
