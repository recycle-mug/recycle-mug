<template>
  <div class="container">
    <div class="form-container partner-form-container" v-if="role === 'partner'">
      <form action="#">
        <h1>join as partner</h1>
        <div class="social-container">
          <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
          <a href="#" @click="openKakaoLogin"
            ><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon
          ></a>
        </div>

        <span>or use your email for registration</span>

        <!-- Partner ID -->
        <input
          type="email"
          placeholder="Partner ID (Email)"
          @keydown.enter.prevent="nextPwInput"
          @keyup="checkId"
          v-model="formData.joinId"
        />
        <span class="error-msg">{{ errors.joinId }}</span>

        <!-- Partner Password -->
        <span class="password-hint"
          >8자리 이상: 영어 대문자, 영어 소문자, 숫자, ~ ! @ # $ % ^ & * 중 3종류 조합</span
        >
        <input
          type="password"
          placeholder="Partner Password"
          @keydown.enter.prevent="nextInput"
          @blur="checkPasswordForm"
          v-model="formData.joinPw"
        />
        <span class="error-msg">{{ errors.joinPw }}</span>

        <!-- Partner Password Check -->
        <input
          type="password"
          placeholder="Password Check"
          @keydown.enter.prevent="telInput"
          @blur="checkPasswordCheck"
          v-model="formData.joinPwChk"
        />
        <span class="error-msg">{{ errors.joinPwChk }}</span>

        <!-- Partner Tel -->
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
            @keydown.enter.prevent="goPopup"
            @keyup="limitNumber"
          />
        </div>
        <span class="error-msg">{{ errors.joinTel }}</span>

        <!-- Partner Address -->
        <div class="address-input-wrapper">
          <input
            type="text"
            id="location"
            placeholder="주소"
            readonly
            @click.prevent="goPopup"
            class="address-input"
            v-model="formData.joinAddress.address"
          />
          <button @click.prevent="goPopup" class="address-btn">주소검색</button>
        </div>

        <div class="address-detail-input-wrapper">
          <input
            type="text"
            placeholder="우편번호"
            id="location_num"
            readonly
            v-model="formData.joinAddress.addressNum"
          />
          <input
            type="text"
            placeholder="상세 주소"
            v-model="formData.joinAddress.addressDetail"
            @keydown.enter.prevent="onSubmitForm"
          />
        </div>
        <span class="error-msg">{{ errors.joinAddress }}</span>

        <span class="error-msg" style="text-align:center;">{{ errors.response }}</span>
        <button @click.prevent="onSubmitForm">join</button>
        <router-link :to="{ name: 'login', query: { role: 'partner' } }" tag="span" class="caption"
          >Already has Account</router-link
        >
      </form>
    </div>

    <div class="form-container customer-form-container" v-else-if="role === 'customer'">
      <form action="#">
        <h1>join as customer</h1>
        <div class="social-container">
          <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
          <a href="#" @click="openKakaoLogin"
            ><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon
          ></a>
        </div>

        <span>or use your email for registration</span>

        <!-- ID -->
        <input
          type="email"
          placeholder="ID (Email)"
          @keydown.enter.prevent="nextPwInput"
          @keyup="checkId"
          v-model="formData.joinId"
        />
        <span class="error-msg">{{ errors.joinId }}</span>

        <!-- Password -->
        <span class="password-hint"
          >8자리 이상: 영어 대문자, 영어 소문자, 숫자, ~ ! @ # $ % ^ & * 중 3종류 조합</span
        >
        <input
          type="password"
          placeholder="Password"
          @keydown.enter.prevent="nextInput"
          @blur="checkPasswordForm"
          v-model="formData.joinPw"
        />
        <span class="error-msg">{{ errors.joinPw }}</span>

        <!-- Password Check -->
        <input
          type="password"
          placeholder="Password Check"
          @keydown.enter.prevent="telInput"
          @blur="checkPasswordCheck"
          v-model="formData.joinPwChk"
        />
        <span class="error-msg">{{ errors.joinPwChk }}</span>

        <!-- Tel -->
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
        <span class="error-msg">{{ errors.joinTel }}</span>
        <span class="error-msg" style="text-align:center;">{{ errors.response }}</span>
        <button @click.prevent="onSubmitForm">join</button>
        <router-link :to="{ name: 'login', query: { role: 'customer' } }" tag="span" class="caption"
          >Already has Account</router-link
        >
      </form>
    </div>

    <div class="overlay-container overlay-left" v-if="role === 'partner'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>Welcome Back!</h1>
          <p>Hi there</p>
          <router-link
            :to="{ name: 'join', query: { role: 'customer' } }"
            tag="button"
            class="ghost"
          >
            join As customer</router-link
          >
        </div>
      </div>
    </div>

    <div class="overlay-container overlay-right" v-else-if="role === 'customer'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>Welcome Back!</h1>
          <p>Hi there</p>
          <router-link :to="{ name: 'join', query: { role: 'partner' } }" tag="button" class="ghost"
            >join As partner</router-link
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
import axios from "axios";
import router from "../routes/routes";

faLibrary.add(faFacebookF, faGooglePlusG, faComment, faMinus);

export default {
  router,
  data() {
    return {
      formData: {
        role: this.role,
        joinId: "",
        joinPw: "",
        joinPwChk: "",
        joinTel: {
          first: "",
          second: "",
          third: "",
        },
        joinAddress: {
          address: "",
          addressNum: "",
          addressDetail: "",
        },
      },
      errors: {
        joinId: "",
        joinPw: "",
        joinPwChk: "",
        joinTel: "",
        joinAddress: "",
        response: "",
      },
      popup: null,
      loading: false,
      post: null,
      error: null,
    };
  },
  props: ["role"],
  components: { FontAwesomeIcon },
  methods: {
    goPopup() {
      const routeData = this.$router.resolve({ name: "address" });
      this.popup = window.open(
        routeData.href,
        "pop",
        "width=570, height=420,scrollbars=yes,resizable=yes",
      );
    },
    telInput() {
      event.target.nextElementSibling.nextElementSibling.children[0].focus();
    },
    nextInput() {
      event.target.nextElementSibling.nextElementSibling.focus();
    },
    nextPwInput() {
      event.target.nextElementSibling.nextElementSibling.nextElementSibling.focus();
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
    setValue() {
      this.formData.joinAddress.address = document.getElementById("location").value;
      this.formData.joinAddress.addressNum = document.getElementById("location_num").value;
    },
    checkRequired() {
      for (const item in this.formData) {
        try {
          if (!this.formData[item]) {
            throw "빈 칸을 모두 채워주세요";
          } else {
            throw "";
          }
        } catch (error) {
          this.errors[item] = error;
        }
      }

      for (const item in this.formData.joinTel) {
        try {
          if (!this.formData.joinTel[item]) {
            throw "빈 칸을 모두 채워주세요";
          }
        } catch (error) {
          this.errors.joinTel = error;
        }
      }

      if (this.formData.role === "partner") {
        for (const item in this.formData.joinAddress) {
          try {
            if (!this.formData.joinAddress[item]) {
              throw "빈 칸을 모두 채워주세요";
            }
          } catch (error) {
            this.errors.joinAddress = error;
          }
        }
      }
    },
    checkId() {
      const pattern_joinId = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

      try {
        if (!pattern_joinId.test(this.formData.joinId)) {
          throw "이메일 형식을 확인해주세요";
        } else {
          this.errors.joinId = "";
        }
      } catch (error) {
        this.errors.joinId = error;
      }
    },
    checkPasswordForm() {
      // const pattern_joinPw = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d~!@#$%^&*]{8,}$/;
      // const pw = !/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,}$/;

      // try {
      //   if (!pattern_joinPw.test(this.formData.joinPw)) throw "비밀번호 형식을 확인해주세요";
      //   else this.errors.joinPw = "";
      // } catch (error) {
      //   this.errors.joinPw = error;
      // }

      const num = this.formData.joinPw.search(/[0-9]/g);
      const smallEng = this.formData.joinPw.search(/[a-z]/g);
      const bigEng = this.formData.joinPw.search(/[A-Z]/g);
      const spe = this.formData.joinPw.search(/[~!@@#$%^&*]/gi);

      try {
        if (this.formData.joinPw.length < 8) {
          throw "8자리 이상으로 입력해주세요";
        } else if (this.formData.joinPw.search(/\s/) != -1) {
          throw "비밀번호는 공백없이 입력해주세요";
        } else if (num + smallEng + bigEng + spe < -1) {
          throw "영문 소문자, 대문자, 숫자, 특수문자 중 3가지 이상을 혼합해주세요";
        } else {
          this.errors.joinPw = "";
        }
      } catch (error) {
        this.errors.joinPw = error;
      }
    },
    checkPasswordCheck() {
      try {
        if (this.formData.joinPw !== this.formData.joinPwChk) {
          throw "비밀번호가 위와 다릅니다";
        } else {
          this.errors.joinPwChk = "";
        }
      } catch (error) {
        this.errors.joinPwChk = error;
      }
    },
    validate() {
      this.checkRequired();
      this.checkId();
      this.checkPasswordForm();
      this.checkPasswordCheck();
    },
    async onSubmitForm(e) {
      e.preventDefault();
      this.validate();

      if (
        !this.errors.joinId &&
        !this.errors.joinPw &&
        !this.errors.joinPwChk &&
        !this.errors.joinTel &&
        !this.errors.joinAddress
      ) {
        // path
        const path = "http://localhost:5000/join/" + this.formData.role;

        const payload = {
          role: this.formData.role,
          id: this.formData.joinId,
          pw: this.formData.joinPw,
          tel:
            this.formData.joinTel.first +
            this.formData.joinTel.second +
            this.formData.joinTel.third,
          address: this.formData.joinAddress.address,
          address_num: this.formData.joinAddress.addressNum,
          address_detail: this.formData.joinAddress.addressDetail,
        };

        let sendJoinForm = axios.create({
          baseURL: path,
        });

        sendJoinForm.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
        sendJoinForm.defaults.headers.common["Content-Type"] = "application/json;charset=utf-8";

        await sendJoinForm
          .post(path, payload)
          .then((res) => {
            if (res.data.result == "fail") {
              throw res.data.message;
            } else {
              router.push({ name: "login", query: { role: this.formData.role } });
            }
          })
          .catch((error) => {
            this.errors.response = error;
          });
      }
    },
    async openKakaoLogin() {
      const REST_API_KEY = "210e6ee868e2837c5e6d0805cc037348";
      const REDIRECT_URI = "http://localhost:8080";
      const path = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}`;

      // let kakaoLogin = axios.create({ baseURL: path });
      // const config = {
      //   headers: {
      //     "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
      //   },
      // };

      // kakaoLogin.defaults.headers.common["access-control-allow-origin"] = "*";
      // kakaoLogin.defaults.headers.common["Access-Control-Allow-Headers"] = "*";
      // kakaoLogin.defaults.headers.common["Content-Type"] = "application/x-www-form-urlencoded";
      // kakaoLogin.defaults.params = {
      //   response_type: "code",
      //   client_id: REST_API_KEY,
      //   redirect_uri: REDIRECT_URI,
      // };

      // kakaoLogin.get(path, config).then((res) => console.log("res :>> ", res));

      window.open(path, "pop", "width=570, height=420,scrollbars=yes,resizable=yes");
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
      min-height: 840px;
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

          .error-msg {
            color: $error-msg;
            user-select: none;
            width: 100%;
            text-align: left;
            padding-left: 1rem;
            height: 0.8rem;
          }

          .password-hint {
            color: map-get($map: $theme, $key: "text-light");
            user-select: none;
            height: 0.8rem;
          }

          input {
            background-color: map-get($map: $theme, $key: "background");
            color: map-get($map: $theme, $key: "text");
            border: none;
            padding: 1rem 1.4rem;
            margin: 0.3rem 0;
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
            margin: 0.3rem 0;
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

          .address-input-wrapper {
            display: flex;
            width: 100%;

            input {
              cursor: pointer;
              border: none;
              padding: 1rem 1.4rem;
              width: 100%;
              margin-right: 1rem;
              box-sizing: border-box;
              background-color: map-get($map: $theme, $key: "content-blocked");
            }
            .address-btn {
              border-radius: 10px;
              cursor: pointer;
              font-weight: normal;
              padding: 0.5rem;
              box-sizing: border-box;
              white-space: nowrap;
              margin: 0.5rem;

              &:hover {
                transform: scale(1.05);
                transition: all 0.2s ease-in-out;
              }
            }
          }

          .address-detail-input-wrapper {
            display: flex;
            width: 100%;

            #location_num {
              width: 20%;
              margin-right: 0.5rem;
              padding: 0.5rem;
              box-sizing: border-box;
              text-align: center;
              cursor: default;
              user-select: none;
              -webkit-user-select: none;
              -ms-user-select: none;
              white-space: nowrap;
              background-color: map-get($map: $theme, $key: "content-blocked");
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
