<template>
  <section class="card">
    <div class="image-section">
      <input type="file" style="display:none" name="" id="imageBtn" @change="uploadImage" />
      <div
        class="image-wrapper"
        :class="{ changed: imgChanged, error: imgErr }"
        @click="chooseImage"
      >
        <img :src="imgSrc" v-if="imgSrc.length > 0" alt="" />
        <img
          :src="'data:image/jpeg;base64,' + this.userInfo.profilePicture"
          v-else-if="userInfo.profilePicture"
          alt=""
        />
        <div class="image-overlay"></div>
      </div>
      <button class="image-save-btn" v-if="imgChanged" @click="saveImage">저장하기</button>
    </div>
    <div class="form-wrapper" v-if="writingMode">
      <h1>내 정보 수정하기</h1>
      <div class="row" :class="{ changed: changed.nickname }">
        <div class="category">닉네임</div>
        <input
          type="text"
          v-model="editForm.nickname"
          @input="editForm.nickname = $event.target.value"
          placeholder="새 닉네임"
          maxlength="20"
          @keydown.enter.prevent="nextInput"
          @keyup="checkNickname()"
          @change="checkChanged"
        /><span class="error-msg">{{ errors.nickname }}</span>
      </div>

      <div class="row" :class="{ changed: changed.password }">
        <div class="category">새 비밀번호</div>
        <input
          type="password"
          v-model="editForm.password"
          @keydown.enter.prevent="nextInput"
          @blur="checkPassword"
          placeholder="새 비밀번호"
          @change="checkChanged"
        /><span class="error-msg">{{ errors.password }}</span>
      </div>

      <div class="row" :class="{ changed: changed.passwordCheck }">
        <div class="category">새 비밀번호 확인</div>
        <input
          type="password"
          v-model="editForm.passwordCheck"
          @keydown.enter.prevent="telInput"
          @blur="checkPasswordCheck"
          placeholder="새 비밀번호 확인"
          @change="checkChanged"
        /><span class="error-msg">{{ errors.passwordCheck }}</span>
      </div>

      <div class="row" :class="{ changed: changed.phoneNumber }">
        <div class="category">tel</div>
        <div class="num-input-wrapper">
          <input
            type="number"
            name="tel_first"
            class="num-input"
            v-model="tel.first"
            placeholder="010"
            @keydown.enter.prevent="nextTelInput()"
            @keyup="limitNumber"
            @change="checkChanged"
          />
          <div class="num-input-connector">
            <font-awesome-icon :icon="['fas', 'minus']"></font-awesome-icon>
          </div>
          <input
            type="number"
            name="tel_second"
            class="num-input"
            v-model="tel.second"
            placeholder="0000"
            @keydown.enter.prevent="nextTelInput()"
            @keyup="limitNumber"
            @change="checkChanged"
          />
          <div class="num-input-connector">
            <font-awesome-icon :icon="['fas', 'minus']"></font-awesome-icon>
          </div>
          <input
            type="number"
            name="tel_third"
            class="num-input"
            v-model="tel.third"
            placeholder="0000"
            @keydown.enter.prevent="userRole === 'customer' ? onSubmit() : goPopup()"
            @keyup="limitNumber"
            @change="checkChanged"
          />
        </div>
      </div>

      <div class="row" v-if="userInfo.address" :class="{ changed: changed.address }">
        <div class="category">주소</div>
        <input
          type="text"
          id="location"
          placeholder="매장 위치"
          readonly
          @click.prevent="goPopup"
          class="address-input"
          v-model="editForm.address"
          @input="checkChanged"
        />
      </div>

      <div class="row" v-if="userInfo.businessName" :class="{ changed: changed.businessName }">
        <div class="category">매장 명</div>
        <input
          type="text"
          id="location_name"
          placeholder="카페 이름"
          v-model="editForm.businessName"
          @keydown.enter.prevent="onSubmitForm"
          readonly
          @input="checkChanged"
        />
        <input type="hidden" v-model="editForm.latitude" id="location_lat" />
        <input type="hidden" v-model="editForm.longitude" id="location_lng" />
      </div>

      <div class="btn-row">
        <button class="undo-btn" @click.prevent="routerHistoryGo">뒤로가기</button>
        <button @click.prevent="onSubmit()">수정완료</button>
      </div>
    </div>
    <div class="wrapper" v-else>
      <div class="role-row" :class="userRole === 'partner' ? ['partner'] : ['customer']">
        <div class="value">{{ userRole }}</div>
      </div>

      <div class="row" v-if="userInfo.businessName">
        <div class="category">가게 명</div>
        <div class="value">{{ userInfo.businessName }}</div>
      </div>

      <div class="row">
        <div class="category">아이디(이메일)</div>
        <div class="value">{{ userInfo.email }}</div>
      </div>

      <div class="row">
        <div class="category">닉네임</div>
        <div class="value">{{ userInfo.nickname }}</div>
      </div>

      <div class="row" v-if="userInfo.address">
        <div class="category">주소</div>
        <div class="value">{{ userInfo.address }}</div>
      </div>

      <div class="row">
        <div class="category">tel</div>
        <div class="value">{{ formatPhoneNumber(userInfo.phoneNumber) }}</div>
      </div>

      <div class="row">
        <div class="category">포인트</div>
        <div class="value">{{ userInfo.point }}</div>
      </div>

      <div class="btn-row">
        <button @click="activateWritingMode()">수정하기</button>
      </div>
    </div>
  </section>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faMinus } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faMinus);

import axios from "axios";

export default {
  data() {
    return {
      userInfo: {},
      editForm: {
        id: null,
        nickname: "",
        password: "",
        passwordCheck: "",
        phoneNumber: "",
        address: "",
        businessName: "",
        latitude: "",
        longitude: "",
      },
      imgSrc: "",
      imgFile: null,
      imgErr: "",
      imgChanged: false,
      writingMode: false,
      tel: {
        first: "",
        second: "",
        third: "",
      },
      errors: {
        nickname: "",
        password: "",
        passwordCheck: "",
        tel: "",
      },
      changed: {
        nickname: false,
        password: false,
        passwordCheck: false,
        phoneNumber: false,
        address: false,
        businessName: false,
      },
    };
  },
  props: ["userRole", "userId", "user"],
  components: { FontAwesomeIcon },
  methods: {
    nextInputOrSubmit() {},
    telInput() {
      event.target.parentElement.nextElementSibling.children[1].children[0].focus();
    },
    nextInput() {
      event.target.parentElement.nextElementSibling.children[1].focus();
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
        this.tel.first = event.target.value;
      }
      if ((event.target.name == "tel_second") & (event.target.value.length == 4)) {
        this.nextTelInput();
      }
      if ((event.target.name == "tel_second") & (event.target.value.length > 4)) {
        event.target.value = event.target.value.slice(0, 4);
        this.tel.second = event.target.value;
      }
      if ((event.target.name == "tel_third") & (event.target.value.length > 4)) {
        event.target.value = event.target.value.slice(0, 4);
        this.tel.third = event.target.value;
      }
    },
    checkNickname() {
      if (this.editForm.nickname) {
        const pattern_nickname = /[^0-9a-zA-Z가-힣]/gi;

        try {
          if (pattern_nickname.test(this.editForm.nickname)) {
            throw "닉네임 형식을 확인해주세요";
          } else {
            this.errors.nickname = "";
          }
        } catch (error) {
          this.errors.nickname = error;
        }
      } else {
        this.errors.nickname = "";
      }
    },
    checkPassword() {
      if (this.editForm.password) {
        const num = this.editForm.password.search(/[0-9]/g);
        const smallEng = this.editForm.password.search(/[a-z]/g);
        const bigEng = this.editForm.password.search(/[A-Z]/g);
        const spe = this.editForm.password.search(/[~!@@#$%^&*]/gi);

        let cnt = 0;

        if (num == -1) cnt += 1;
        if (smallEng == -1) cnt += 1;
        if (bigEng == -1) cnt += 1;
        if (spe == -1) cnt += 1;

        try {
          if (this.editForm.password.length < 8) {
            throw "8자리 이상으로 입력해주세요";
          } else if (this.editForm.password.search(/\s/) != -1) {
            throw "비밀번호는 공백없이 입력해주세요";
          } else if (cnt > 1) {
            throw "영문 소문자, 대문자, 숫자, 특수문자 중 3가지 이상을 혼합해주세요";
          } else if (this.editForm.password.search(/[()_+|<>?:{}/[\]/\\//"':;.,]/) != -1) {
            throw "정해진 특수문자 외 특수문자는 사용할 수 없습니다";
          } else {
            this.errors.password = "";
          }
        } catch (error) {
          this.errors.password = error;
        }
      } else {
        this.errors.password = "";
      }
    },
    checkPasswordCheck() {
      try {
        if (this.editForm.password !== this.editForm.passwordCheck) {
          throw "비밀번호가 위와 다릅니다";
        } else {
          this.errors.passwordCheck = "";
        }
      } catch (error) {
        this.errors.passwordCheck = error;
      }
    },
    checkChanged() {
      if (this.userInfo.nickname !== this.editForm.nickname) {
        this.changed.nickname = true;
      } else {
        this.changed.nickname = false;
      }

      if (this.editForm.password.length > 0) {
        this.changed.password = true;
      } else {
        this.changed.password = false;
      }

      if (this.editForm.passwordCheck.length > 0) {
        this.changed.passwordCheck = true;
      } else {
        this.changed.passwordCheck = false;
      }

      let tel = this.tel.first + this.tel.second + this.tel.third;
      if (this.userInfo.phoneNumber !== tel) {
        this.changed.phoneNumber = true;
      } else {
        this.changed.phoneNumber = false;
      }

      if (this.userRole === "partner") {
        if (this.userInfo.address !== this.editForm.address) {
          this.changed.address = true;
        } else {
          this.changed.address = false;
        }

        if (this.userInfo.businessName !== this.editForm.businessName) {
          this.changed.businessName = true;
        } else {
          this.changed.businessName = false;
        }
      }
    },
    goPopup() {
      const routeData = this.$router.resolve({ name: "address" });
      this.popup = window.open(
        routeData.href,
        "pop",
        "width=570, height=420,scrollbars=yes,resizable=yes",
      );
    },
    getProfile() {
      const path = `/backend/${this.userRole}/${this.userId}`;

      const { accessToken } = localStorage;
      if (!accessToken) {
        this.isLogin = false;
        this.username = "";
      } else {
        const getInfo = axios.create({ baseUrl: path });
        getInfo.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
        getInfo.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        getInfo.defaults.headers.common["Access-Control-Allow-Methods"] =
          "GET,POST,PUT,DELETE,OPTIONS";

        getInfo.defaults.headers.common["Content-Type"] =
          "application/x-www-form-urlencoded;charset=utf-8";

        getInfo
          .get(path)
          .then((res) => {
            this.userInfo = res.data;
            this.editForm.id = this.userInfo.id ? this.userInfo.id : "";
            this.editForm.nickname = this.userInfo.nickname ? this.userInfo.nickname : "";
            if (this.userInfo.phoneNumber) {
              this.editForm.phoneNumber = this.userInfo.phoneNumber;
              this.tel.first = this.userInfo.phoneNumber.slice(0, 3);
              this.tel.second = this.userInfo.phoneNumber.slice(3, 7);
              this.tel.third = this.userInfo.phoneNumber.slice(7, 11);
            }
            this.editForm.address = this.userInfo.address ? this.userInfo.address : "";
            this.editForm.businessName = this.userInfo.businessName
              ? this.userInfo.businessName
              : "";
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },
    formatPhoneNumber(str) {
      if (str) {
        const cleaned = str.replace(/\D/g, "");
        const match = cleaned.match(/^(\d{3})(\d{4})(\d{4})$/);

        if (match) {
          return match[1] + " - " + match[2] + " - " + match[3];
        } else {
          return str;
        }
      }
    },
    activateWritingMode() {
      this.writingMode = !this.writingMode;
    },
    uploadImage(e) {
      const files = e.target.files;
      this.imgFile = files[0];

      try {
        if (files.length) {
          const file = files[0];
          const imgType = /image.*/;

          if (file.type.match(imgType)) {
            const reader = new FileReader();
            reader.onload = () => {
              this.imgSrc = reader.result;
              this.imgChanged = true;
              this.imgErr = "";
            };
            reader.readAsDataURL(file);
          } else {
            throw "올바른 이미지 형식이 아닙니다";
          }
        }
      } catch (error) {
        console.log("error :>> ", error);
        this.imgErr = error;
        this.imgSrc = "";
        this.imgChanged = false;
      }
    },
    chooseImage() {
      document.getElementById("imageBtn").click();
    },
    saveImage() {
      const path = `/backend/user/${this.userRole}/profile-image`;
      let file = this.imgFile;
      const blob = file.slice(0, file.size, "image/jpeg");
      const newFileName = this.userInfo.email.split("@");
      const newFile = new File([blob], newFileName[0] + ".jpeg", { type: "image/jpeg" });

      let imageFormData = new FormData();
      imageFormData.append("file", newFile);
      imageFormData.append("userId", this.userId);

      let imageForm = axios.create();

      const config = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };

      imageForm.patch(path, imageFormData, config).then((res) => {
        this.imgChanged = false;
      });
    },
    onSubmit() {
      this.checkPasswordCheck();
      if (
        !this.errors.nickname &&
        !this.errors.password &&
        !this.errors.passwordCheck &&
        !this.errors.phoneNumber &&
        !this.errors.address &&
        !this.errors.businessName
      ) {
        const path = `/backend/${this.userRole}/${this.userId}`;

        let editUser = axios.create();

        let payload = {};

        if (this.changed.nickname === true) {
          payload.nickname = this.editForm.nickname;
        }
        if (this.changed.password === true && this.changed.passwordCheck) {
          payload.password = this.editForm.password;
        }
        if (this.changed.phoneNumber === true) {
          payload.phoneNumber = this.tel.first + this.tel.second + this.tel.third;
        }
        if (this.changed.address === true) {
          payload.address = this.editForm.address;
          payload.businessName = this.editForm.businessName;
          payload.latitude = this.editForm.latitude;
          payload.longitude = this.editForm.longitude;
        }

        editUser
          .patch(path, this.editForm)
          .then((res) => {
            this.writingMode = false;
            this.getProfile();
            this.$emit("makeToast", { status: "success", msg: "프로필 정보를 수정했습니다." });
          })
          .catch((err) => {
            console.log("err :>> ", err);
          });
      }
    },
    routerHistoryGo() {
      let isChanged = false;
      if (this.imgChanged === true) {
        isChanged = true;
      } else {
        for (let item in this.changed) {
          if (this.changed[item] === true) {
            isChanged = true;
            break;
          }
        }
      }

      if (isChanged) {
        if (confirm("변경사항을 저장하지 않고 나가시겠습니까?")) {
          this.writingMode = false;
        }
      } else {
        this.writingMode = false;
      }
    },
  },
  mounted() {
    if (localStorage.getItem("accessToken")) {
      this.getProfile();
    }
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .card {
      display: grid;
      margin: auto;
      max-width: 800px;
      width: 100%;
      background-color: map-get($map: $theme, $key: "content-background");
      box-shadow: $shadow-light;
      border-radius: 14px;
      padding: 2rem;
      grid-template-columns: 0.8fr 1fr;

      .image-section {
        position: relative;
        min-height: 350px;
        display: flex;
        align-items: center;
        justify-content: center;

        .image-wrapper {
          width: 350px;
          height: 350px;
          border: 1px solid map-get($map: $theme, $key: "border");
          border-radius: 25%;
          overflow: hidden;
          cursor: pointer;
          transition: all 0.2s ease;
          position: relative;
          left: -3rem;
          background-color: map-get($map: $theme, $key: "background");

          &.changed {
            border: 5px solid $main-color;
          }

          &.error {
            border: 5px solid $error-msg;

            &::after {
              content: "잘못된 이미지 형식입니다.";
              z-index: 999;
              width: 350px;
              height: 350px;
              background-color: rgba($color: #000000, $alpha: 0.15);
              position: absolute;
              top: 0;
              left: 0;
              display: flex;
              justify-content: center;
              align-items: center;
              font-weight: bold;
              font-size: 1.2rem;
              padding-top: 100px;
            }
          }

          img {
            width: 100%;
            height: 100%;
            object-fit: cover;
          }

          .image-overlay {
            opacity: 0;
            z-index: 999;
            width: 350px;
            height: 350px;
            background-color: rgba($color: #000000, $alpha: 0.15);
            position: absolute;
            top: 0;
            left: 0;
            transition: all 0.3s ease;
            display: flex;
            justify-content: center;
            align-items: center;

            &::after {
              content: "프로필 이미지 변경하기";
              padding: 1.5rem 0;
              background-color: rgba($color: #000000, $alpha: 0.4);
              width: 100%;
              display: flex;
              justify-content: center;
              align-items: center;
              font-size: 1.2rem;
              font-weight: bold;
              color: white;
            }
          }

          &:hover {
            box-shadow: $shadow-light;
            transform: scale(1.01);

            .image-overlay {
              opacity: 1;
            }
          }
        }

        .image-save-btn {
          transition: all 0.2s ease;
          position: absolute;
          height: 50px;
          top: calc(50% - 175px - 45px);
          left: 7%;
          width: 200px;
          border-radius: 6px 6px 0 0;

          &::before {
            content: "이미지가 저장되지 않았습니다.";
            position: absolute;
            white-space: nowrap;
            top: -1.5rem;
            left: 0;
            color: map-get($map: $theme, $key: "text");
            text-align: center;
            width: 100%;
          }
          &:hover {
            transform: scale(1.05);
          }
        }
      }
    }

    .row {
      display: grid;
      align-items: center;
      width: 100%;
      grid-template-columns: 0.5fr 1fr;
      margin-bottom: 1rem;
      position: relative;

      &.changed {
        input {
          border: 2px solid $main-color;
        }
      }

      .category {
        text-align: right;
        padding: 1rem 1rem 1rem 0;
        font-size: 0.9rem;
      }

      .value {
        padding: 1rem;
        font-weight: bold;
        font-size: 1.2rem;
        text-align: right;
      }

      input {
        background-color: map-get($map: $theme, $key: "background");
        color: map-get($map: $theme, $key: "text");
        border: none;
        padding: 1rem;
        margin: 0.3rem;
        width: 100%;
        font-size: 1rem;
        font-weight: bold;
        border: 1px solid rgba(map-get($map: $theme, $key: "border"), 0.3);
        border-radius: 6px;
        transition: border-color 0.2s ease-in-out;

        &::placeholder {
          color: map-get($map: $theme, $key: "text-muted");
        }

        &:focus {
          outline-color: $sub-color;
          border: none;
        }
      }

      .error-msg {
        position: absolute;
        right: 0;
        bottom: -0.5rem;
        font-size: 0.8rem;
        color: $error-msg;
      }

      .num-input-wrapper {
        display: flex;
        justify-content: space-between;
        border: none;
        width: 100%;
        margin: 0.3rem;

        .num-input-connector {
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 0.6rem;
          margin: 3px;
          color: $main-color;
        }

        .num-input {
          height: 43px;
          box-sizing: border-box;
          font-weight: 700;
          text-align: center;
          transition: border-color 0.2s ease-in-out;
          background-color: map-get($map: $theme, $key: "background");
          margin: 0;

          &::placeholder {
            color: map-get($map: $theme, $key: "text-muted");
          }

          &:focus {
            outline-color: $sub-color;
            border: none;
            box-sizing: border-box;
          }
        }
      }

      .address-input {
        cursor: pointer;
        background-color: map-get($map: $theme, $key: "content-blocked");
        user-select: none;
      }
    }

    .btn-row {
      padding-top: 1rem;
      width: 100%;
      justify-content: flex-end;
      display: flex;

      .undo-btn {
        margin-right: 1rem;
        background-color: map-get($map: $theme, $key: "background");
        border: 1px solid $main-color;
        color: map-get($map: $theme, $key: "text");
      }
    }

    button {
      padding: 1rem;
      background-color: $main-color;
      border: none;
      border-radius: 6px;
      color: $white;
      cursor: pointer;
      transition: all 0.2s ease;

      &:hover {
        box-shadow: $shadow-light;
        transform: scale(1.05);
      }
    }

    h1 {
      width: 100%;
      font-weight: bolder;
      padding-bottom: 1rem;
      font-size: 1.2rem;
    }

    .role-row {
      width: 100%;
      user-select: none;
      float: right;
      padding-bottom: 1rem;

      .value {
        width: fit-content;
        padding: 5px 10px;
        border-radius: 12px;
        font-weight: bold;
        color: map-get($map: $theme, $key: "text");
      }

      &.partner .value {
        background-color: rgba($main-color, 0.5);
        border: 2px solid $main-color;
      }

      &.customer .value {
        background-color: rgba($sub-color, 0.5);
        border: 2px solid $sub-color;
      }
    }
  }
}
</style>
