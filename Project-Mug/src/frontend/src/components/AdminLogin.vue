<template>
  <div class="wrapper">
    <div class="container">
      <div class="home-btn-wrapper">
        <router-link :to="{ name: 'main' }" class="home-btn" tag="div">
          <font-awesome-icon :icon="['fas', 'home']"></font-awesome-icon>
        </router-link>
        <div class="home-btn" @click="routerHistoryGo">
          <font-awesome-icon :icon="['fas', 'arrow-left']"></font-awesome-icon>
        </div>
      </div>
      <div class="form-container">
        <form action="#">
          <h1>
            <font-awesome-icon :icon="['fas', 'lock']" style="margin-right:10px"></font-awesome-icon
            >Login
          </h1>
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
            />
            <span class="error-msg">{{ errors.loginPw }}</span>
          </div>

          <div class="btn-wrapper">
            <button type="submit" @click.prevent="onSubmit">Login</button>
            <router-link
              :to="{ name: 'admin' }"
              tag="button"
              type="submit"
              @click.prevent="onSubmit"
              class="sub-btn"
            >
              Forgot Password?</router-link
            >
          </div>
          <span class="error-msg">{{ errors.response }}</span>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faLock, faHome, faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faLock, faHome, faArrowLeft);

export default {
  data() {
    return {
      formData: {
        role: "admin",
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
      // const pattern_loginId = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
      // try {
      //   if (!pattern_loginId.test(this.formData.loginId)) {
      //     throw "이메일 형식을 확인해주세요";
      //   } else {
      //     this.errors.loginId = "";
      //   }
      // } catch (error) {
      //   this.errors.loginId = error;
      // }
    },
    validate() {
      this.checkRequired();
      this.checkId();
    },
    onSubmit(e) {
      e.preventDefault();
      this.validate();

      const role = this.formData.role;
      const email = this.formData.loginId;
      const password = this.formData.loginPw;

      if (!this.errors.loginId) {
        this.$store
          .dispatch("LOGIN", { role, email, password })
          .then(this.redirect)
          .catch((err) => {
            this.errors.loginId = "";
            this.errors.loginPw = "";
            this.errors.response = err;
          });
      }
    },
    redirect() {
      console.log("Login Success");
      this.$router.push({ name: "admin" });
    },
    routerHistoryGo() {
      this.$router.go(-1);
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

    * {
      box-sizing: border-box;
    }

    .wrapper {
      background-color: map-get($map: $theme, $key: "background");
      box-sizing: border-box;
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;

      .container {
        background-color: map-get($map: $theme, $key: "content-background");
        border-radius: 10px;
        box-shadow: $shadow;
        position: relative;
        width: 700px;
        max-width: 100%;
        margin: auto;
        padding: 2rem;

        .home-btn-wrapper {
          position: absolute;
          top: -3rem;
          left: 1rem;
          display: flex;

          .home-btn {
            width: 40px;
            height: 40px;
            z-index: 50;
            border-radius: 50%;
            background-color: map-get($map: $theme, $key: "content-background");
            color: map-get($map: $theme, $key: "text");
            box-shadow: $shadow;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            margin-right: 5px;

            &:hover {
              background-color: $main-color;
              color: white;
              transition: background-color 0.3s linear;
            }
          }
        }

        .form-container {
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
            box-sizing: border-box;

            h1 {
              font-weight: bold;
              margin: 0.5rem;
              font-size: 1.5rem;
              text-align: left;
              width: 100%;
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
                box-shadow: 0 0px 4px 0 rgba(1, 1, 1, 0.2);

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
                margin-top: 0;
              }
            }

            .error-msg {
              color: $error-msg;
              width: 100%;
              text-align: left;
              user-select: none;
              height: 0.8rem;
              font-size: 0.8rem;
              margin-top: 1rem;
            }

            .btn-wrapper {
              display: flex;
              width: 100%;

              button {
                border-radius: 20px;
                margin-top: 1.2rem;
                margin-right: 10px;
                border: 1px solid $main-color;
                background-color: $main-color;
                color: $white;
                border-radius: 20px;
                font-size: 1rem;
                font-weight: bold;
                padding: 0.8rem 2rem;
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

              .sub-btn {
                background-color: map-get($map: $theme, $key: "background");
                color: map-get($map: $theme, $key: "text-light");
                font-weight: normal;
              }
            }
          }
        }
      }
    }
  }
}
</style>
