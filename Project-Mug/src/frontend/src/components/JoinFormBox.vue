<template>
  <div class="container">
    <div class="form-container partner-form-container" v-if="role === 'partner'">
      <router-link
        :to="{ name: 'join', query: { role: 'customer' } }"
        tag="button"
        class="overlay-top"
      >
        👈 사용자 회원가입하러가기</router-link
      >

      <form action="#">
        <h1>파트너 회원가입하기</h1>
        <div class="social-container">
          <div class="social-error" v-if="socialIconClicked">아직 지원하지 않는 기능입니다.</div>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon
          ></a>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon
          ></a>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon
          ></a>
        </div>

        <span>👇 이메일로 회원가입하기 👇</span>

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
          >8자리 이상: 영어(대∙소문자), 숫자, 특수문자(~ ! @ # $ % ^ & *) 조합</span
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
          <span class="num-input-title">Tel</span>
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
            @click.prevent="goPopup"
            class="address-input"
            v-model="formData.joinAddress.address"
          />
          <button @click.prevent="goPopup" class="address-btn">매장찾기</button>
        </div>

        <div class="address-detail-input-wrapper">
          <input
            type="text"
            id="location_name"
            placeholder="카페 이름"
            v-model="formData.joinAddress.addressName"
          />

          <input
            type="hidden"
            v-model="formData.joinAddress.addressLat"
            id="location_lat"
            value=""
          />
          <input
            type="hidden"
            v-model="formData.joinAddress.addressLng"
            id="location_lng"
            value=""
          />
        </div>
        <span class="error-msg">{{ errors.joinAddress }}</span>

        <!-- Private Policy -->
        <div class="policy-wrapper">
          <h1 class="policy-title">개인정보 보호정책</h1>
          <textarea name="" id="" cols="30" rows="10" class="policy" readonly>
통신·방송의 시설기준과 신문의 기능을 보장하기 위하여 필요한 사항은 법률로 정한다. 대통령은 필요하다고 인정할 때에는 외교·국방·통일 기타 국가안위에 관한 중요정책을 국민투표에 붙일 수 있다. 누구든지 체포 또는 구속을 당한 때에는 적부의 심사를 법원에 청구할 권리를 가진다. 이 헌법시행 당시에 이 헌법에 의하여 새로 설치될 기관의 권한에 속하는 직무를 행하고 있는 기관은 이 헌법에 의하여 새로운 기관이 설치될 때까지 존속하며 그 직무를 행한다. 정당은 그 목적·조직과 활동이 민주적이어야 하며, 국민의 정치적 의사형성에 참여하는데 필요한 조직을 가져야 한다. 이 헌법은 1988년 2월 25일부터 시행한다. 다만, 이 헌법을 시행하기 위하여 필요한 법률의 제정·개정과 이 헌법에 의한 대통령 및 국회의원의 선거 기타 이 헌법시행에 관한 준비는 이 헌법시행 전에 할 수 있다.

모든 국민은 법률이 정하는 바에 의하여 공무담임권을 가진다. 법률안에 이의가 있을 때에는 대통령은 제1항의 기간내에 이의서를 붙여 국회로 환부하고, 그 재의를 요구할 수 있다. 국회의 폐회중에도 또한 같다. 국회의원의 선거구와 비례대표제 기타 선거에 관한 사항은 법률로 정한다. 국회의원은 현행범인인 경우를 제외하고는 회기중 국회의 동의없이 체포 또는 구금되지 아니한다. 국회는 국무총리 또는 국무위원의 해임을 대통령에게 건의할 수 있다. 국회는 국가의 예산안을 심의·확정한다. 대통령으로 선거될 수 있는 자는 국회의원의 피선거권이 있고 선거일 현재 40세에 달하여야 한다. 국민의 모든 자유와 권리는 국가안전보장·질서유지 또는 공공복리를 위하여 필요한 경우에 한하여 법률로써 제한할 수 있으며, 제한하는 경우에도 자유와 권리의 본질적인 내용을 침해할 수 없다.</textarea
          >
          <div class="policy-check-wrapper">
            <div class="policy-check">
              <input type="checkbox" name="" id="policy" v-model="formData.policyChk" />
              <label for="policy">위 개인정보 수집 · 이용에 동의합니다. (필수) </label>
            </div>
            <span class="error-msg">{{ errors.policyChk }}</span>
          </div>
        </div>

        <span class="error-msg" style="text-align:center;">{{ errors.response }}</span>
        <button @click.prevent="onSubmitForm">회원가입</button>
        <router-link :to="{ name: 'login' }" tag="span" class="caption"
          >계정이 이미 있어요</router-link
        >
      </form>
    </div>

    <div class="form-container customer-form-container" v-else-if="role === 'customer'">
      <router-link
        :to="{ name: 'join', query: { role: 'partner' } }"
        tag="button"
        class="overlay-top"
      >
        👉 파트너 회원가입하러가기</router-link
      >
      <form action="#">
        <h1>사용자 회원가입하기</h1>
        <div class="social-container">
          <div class="social-error" v-if="socialIconClicked">아직 지원하지 않는 기능입니다.</div>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon
          ></a>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon
          ></a>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon
          ></a>
        </div>

        <span>👇 이메일로 회원가입하기 👇</span>

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
          >8자리 이상: 영어(대∙소문자), 숫자, 특수문자(~ ! @ # $ % ^ & *) 조합</span
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
          <span class="num-input-title">Tel</span>
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
            @keyup="limitNumber"
          />
        </div>
        <span class="error-msg">{{ errors.joinTel }}</span>

        <!-- Private Policy -->
        <div class="policy-wrapper">
          <h1 class="policy-title">개인정보 보호정책</h1>
          <textarea name="" id="" cols="30" rows="10" class="policy" readonly>
통신·방송의 시설기준과 신문의 기능을 보장하기 위하여 필요한 사항은 법률로 정한다. 대통령은 필요하다고 인정할 때에는 외교·국방·통일 기타 국가안위에 관한 중요정책을 국민투표에 붙일 수 있다. 누구든지 체포 또는 구속을 당한 때에는 적부의 심사를 법원에 청구할 권리를 가진다. 이 헌법시행 당시에 이 헌법에 의하여 새로 설치될 기관의 권한에 속하는 직무를 행하고 있는 기관은 이 헌법에 의하여 새로운 기관이 설치될 때까지 존속하며 그 직무를 행한다. 정당은 그 목적·조직과 활동이 민주적이어야 하며, 국민의 정치적 의사형성에 참여하는데 필요한 조직을 가져야 한다. 이 헌법은 1988년 2월 25일부터 시행한다. 다만, 이 헌법을 시행하기 위하여 필요한 법률의 제정·개정과 이 헌법에 의한 대통령 및 국회의원의 선거 기타 이 헌법시행에 관한 준비는 이 헌법시행 전에 할 수 있다.

모든 국민은 법률이 정하는 바에 의하여 공무담임권을 가진다. 법률안에 이의가 있을 때에는 대통령은 제1항의 기간내에 이의서를 붙여 국회로 환부하고, 그 재의를 요구할 수 있다. 국회의 폐회중에도 또한 같다. 국회의원의 선거구와 비례대표제 기타 선거에 관한 사항은 법률로 정한다. 국회의원은 현행범인인 경우를 제외하고는 회기중 국회의 동의없이 체포 또는 구금되지 아니한다. 국회는 국무총리 또는 국무위원의 해임을 대통령에게 건의할 수 있다. 국회는 국가의 예산안을 심의·확정한다. 대통령으로 선거될 수 있는 자는 국회의원의 피선거권이 있고 선거일 현재 40세에 달하여야 한다. 국민의 모든 자유와 권리는 국가안전보장·질서유지 또는 공공복리를 위하여 필요한 경우에 한하여 법률로써 제한할 수 있으며, 제한하는 경우에도 자유와 권리의 본질적인 내용을 침해할 수 없다.</textarea
          >
          <div class="policy-check-wrapper">
            <div class="policy-check">
              <input type="checkbox" v-model="formData.policyChk" name="" id="policy" />
              <label for="policy">위 개인정보 수집 · 이용에 동의합니다. (필수) </label>
            </div>
            <span class="error-msg">{{ errors.policyChk }}</span>
          </div>
        </div>

        <span class="error-msg" style="text-align:center;">{{ errors.response }}</span>
        <button @click.prevent="onSubmitForm">회원가입</button>
        <router-link :to="{ name: 'login' }" tag="span" class="caption"
          >계정이 이미 있어요</router-link
        >
      </form>
    </div>

    <div class="overlay-container overlay-left" v-if="role === 'partner'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>반가워요!</h1>
          <p>혹시, 컵을 빌리러 오셨나요?</p>
          <router-link
            :to="{ name: 'join', query: { role: 'customer' } }"
            tag="button"
            class="ghost"
          >
            사용자 회원가입하기</router-link
          >
        </div>
      </div>
    </div>

    <div class="overlay-container overlay-right" v-else-if="role === 'customer'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>반가워요!</h1>
          <p>혹시, 제휴 카페 사장님이신가요?</p>
          <router-link :to="{ name: 'join', query: { role: 'partner' } }" tag="button" class="ghost"
            >파트너 회원가입하기</router-link
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
          addressName: "",
          addressLat: "0",
          addressLng: "0",
        },
        policyChk: false,
      },
      errors: {
        joinId: "",
        joinPw: "",
        joinPwChk: "",
        joinTel: "",
        joinAddress: "",
        policyChk: "",
        response: "",
      },
      popup: null,
      loading: false,
      post: null,
      error: null,
      socialIconClicked: false,
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
      this.formData.joinAddress.addressName = document.getElementById("location_name").value;
      this.formData.joinAddress.addressLat = document.getElementById("location_lat").value;
      this.formData.joinAddress.addressLng = document.getElementById("location_lng").value;

      console.log("this.formData.joinAddress :>> ", this.formData.joinAddress);
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
      const num = this.formData.joinPw.search(/[0-9]/g);
      const eng = this.formData.joinPw.search(/[a-z]/gi);
      const spe = this.formData.joinPw.search(/[~!@@#$%^&*]/gi);

      let cnt = 0;

      if (num == -1) cnt += 1;
      if (eng == -1) cnt += 1;
      if (spe == -1) cnt += 1;

      console.log("num :>> ", num);
      console.log("eng :>> ", eng);
      console.log("spe :>> ", spe);

      console.log("cnt :>> ", cnt);

      try {
        if (this.formData.joinPw.length < 8) {
          throw "8자리 이상으로 입력해주세요";
        } else if (this.formData.joinPw.search(/\s/) != -1) {
          throw "비밀번호는 공백없이 입력해주세요";
        } else if (cnt > 0) {
          throw "영어(대∙소문자), 숫자, 특수문자를 모두 혼합해주세요";
        } else if (this.formData.joinPw.search(/[()_+|<>?:{}/[\]/\\//"':;.,]/) != -1) {
          throw "정해진 특수문자 외 특수문자는 사용할 수 없습니다";
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
    checkCheckboxFilled() {
      try {
        console.log(this.formData.policyChk);
        if (!this.formData.policyChk) {
          throw "필수 동의 내용을 체크해주세요";
        } else {
          this.errors.policyChk = "";
        }
      } catch (error) {
        this.errors.policyChk = error;
      }
    },
    validate() {
      this.checkRequired();
      this.checkId();
      this.checkPasswordForm();
      this.checkPasswordCheck();
      this.checkCheckboxFilled();
    },
    async onSubmitForm(e) {
      e.preventDefault();
      this.validate();

      if (
        !this.errors.joinId &&
        !this.errors.joinPw &&
        !this.errors.joinPwChk &&
        !this.errors.joinTel &&
        !this.errors.joinAddress &&
        !this.errors.policyChk
      ) {
        // path
        const path = "/backend/join/" + this.formData.role;

        const payload = {
          email: this.formData.joinId,
          password: this.formData.joinPw,
          phoneNumber:
            this.formData.joinTel.first +
            this.formData.joinTel.second +
            this.formData.joinTel.third,
          address: this.formData.joinAddress.address,
          businessName: this.formData.joinAddress.addressName,
          latitude: this.formData.joinAddress.addressLat,
          longitude: this.formData.joinAddress.addressLng,
        };

        let sendJoinForm = axios.create();

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
            console.log("error :>> ", error);
            this.errors.response = "이미 사용중인 이메일입니다.";
          });
      }
    },
    onClickSocialIcon() {
      this.socialIconClicked = true;
    },
    async openKakaoLogin() {
      const routeData = this.$router.resolve({ name: "kakao" });
      this.popup = window.open(
        routeData.href,
        "pop",
        "width=570, height=420,scrollbars=yes,resizable=yes",
      );
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
    padding: auto 1rem;

    .container {
      background-color: map-get($map: $theme, $key: "content-background");
      border-radius: 10px;
      box-shadow: $shadow;
      position: relative;
      overflow: hidden;
      width: 1050px;
      max-width: 100%;
      min-height: 840px;
      margin: 1rem auto;

      .form-container {
        transition: all 0.6s ease-in-out;
        box-sizing: border-box;

        @media screen and (max-width: 1020px) {
          position: static;
        }

        .overlay-top {
          display: none;

          @media screen and (max-width: 1020px) {
            display: block;
            width: 100%;
            margin-top: 1rem;
            color: $main-color;
            font-size: 0.85rem;
            text-transform: uppercase;
            cursor: pointer;
            background-color: transparent;
            border: none;
            font-weight: bold;

            &:hover {
              text-decoration: underline;
            }
          }
        }

        form {
          background-color: map-get($map: $theme, $key: "content-background");
          display: flex;
          align-items: center;
          justify-content: center;
          flex-direction: column;
          padding: 2rem 3rem;
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

            .social-error {
              color: $error-msg;
              user-select: none;
              width: 100%;
              text-align: center;
              height: 1rem;
              font-size: 0.8rem;
              margin-bottom: 10px;
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
            align-items: center;
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

            .num-input-title {
              user-select: none;
              padding: 0.5rem;
              font-size: 0.8rem;
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

              &::placeholder {
                color: rgba(map-get($map: $theme, $key: "text-muted"), 0.5);
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

          .policy-wrapper {
            display: flex;
            flex-direction: column;
            width: 100%;
            margin: 1rem 0;

            h1 {
              text-align: left;
              font-size: 1rem;
              padding-left: 1rem;
              color: map-get($map: $theme, $key: "text");
            }

            textarea {
              width: 100%;
              max-height: 100px;
              background-color: map-get($map: $theme, $key: "background");
              padding: 1rem;
              resize: none;
              box-sizing: border-box;
              border-radius: 6px;
              border: 1px solid map-get($map: $theme, $key: "border");
              color: map-get($map: $theme, $key: "text-light");

              &:focus {
                outline: none;
                border: 2px solid $main-color;
                color: map-get($map: $theme, $key: "text");
              }
            }

            .policy-check-wrapper {
              display: flex;
              flex-direction: column;
              justify-content: end;
              width: 100%;
              box-sizing: border-box;
              margin-top: 10px;

              .policy-check {
                width: 100%;
                display: flex;
                justify-content: flex-end;
                align-items: center;
                cursor: pointer;

                input {
                  margin-right: 5px;
                  width: fit-content;
                }
                label {
                  font-size: 0.8rem;
                  white-space: nowrap;
                  &:hover {
                    font-weight: bold;
                    cursor: pointer;
                  }
                }
              }

              .error-msg {
                text-align: right;
                box-sizing: border-box;
              }
            }
          }
        }
      }

      .partner-form-container {
        float: right;
        width: 50%;
        opacity: 1;
        z-index: 1;

        @media screen and (max-width: 1020px) {
          width: 100%;
        }
      }

      .customer-form-container {
        float: left;
        width: 50%;
        z-index: 2;

        @media screen and (max-width: 1020px) {
          width: 100%;
        }
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

        @media screen and (max-width: 1020px) {
          display: none;
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
