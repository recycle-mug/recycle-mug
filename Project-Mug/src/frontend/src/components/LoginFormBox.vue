<template>
  <div class="wrapper">
    <div class="container">
      <div class="form-container">
        <form action="#">
          <h1>로그인하기</h1>
          <div class="social-container">
            <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
            <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
            <a href="#"><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon></a>
          </div>

          <span>👇 이메일로 로그인하기 👇</span>
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
          </div>
          <span class="error-msg">{{ errors.response }}</span>
          <button type="submit" @click.prevent="onSubmit">로그인</button>
          <router-link
            :to="{ name: 'join', query: { role: 'customer' } }"
            tag="span"
            class="caption"
            >계정이 없으신가요?</router-link
          >
        </form>
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
    validate() {
      this.checkRequired();
      this.checkId();
    },
    onSubmit(e) {
      e.preventDefault();
      this.validate();

      const email = this.formData.loginId;
      const password = this.formData.loginPw;

      if (!this.errors.loginId) {
        this.$store
          .dispatch("LOGIN", { email, password })
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

    * {
      box-sizing: border-box;
    }

    .wrapper {
      background-color: map-get($map: $theme, $key: "background");
      box-sizing: border-box;
      width: 100%;
      height: 100%;
      padding: 1rem;

      .container {
        background-color: map-get($map: $theme, $key: "content-background");
        border-radius: 10px;
        box-shadow: $shadow;
        position: relative;
        overflow: hidden;
        width: 100%;
        max-width: 500px;
        min-height: 540px;
        margin: auto;
        display: flex;
        justify-content: center;
        align-items: center;

        .form-container {
          width: 100%;
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
      }
    }
  }
}
</style>
