<template>
  <div>
    <div class="form-wrapper" v-if="writingMode">
      <div class="row">
        <div>닉네임 :</div>
        <div class="value">{{ userInfo.nickname }}</div>
        <input
          type="text"
          :value="userInfo.nickname"
          @input="editForm.nickname = $event.target.value"
        />
      </div>

      <div class="row">
        <div>새 비밀번호 :</div>
        <input type="password" v-model="editForm.password" />
      </div>

      <div class="row">
        <div>tel :</div>
        <div class="value">{{ userInfo.phoneNumber }}</div>
        <input
          type="text"
          :value="userInfo.phoneNumber.replace(/\D/g, '')"
          @input="editForm.phoneNumber = $event.target.value"
        />
      </div>

      <div class="row">
        <button @click.prevent="onSubmit()">수정완료</button>
      </div>
    </div>
    <div class="wrapper" v-else>
      <div class="row">
        <div class="value">{{ userRole }}</div>
      </div>

      <div class="row">
        <input type="file" style="display:none" name="" id="imageBtn" @change="uploadImage" />
        <div class="image-wrapper" :class="{ changed: imgChanged }" @click="chooseImage">
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

      <div class="row" v-if="userInfo.businessName">
        <div>가게 명 :</div>
        <div class="value">{{ userInfo.businessName }}</div>
      </div>

      <div class="row">
        <div>아이디(이메일) :</div>
        <div class="value">{{ userInfo.email }}</div>
      </div>

      <div class="row">
        <div>닉네임 :</div>
        <div class="value">{{ userInfo.nickname }}</div>
      </div>

      <div class="row">
        <div>tel :</div>
        <div class="value">{{ userInfo.phoneNumber }}</div>
      </div>

      <div class="row">
        <div>포인트 :</div>
        <div class="value">{{ userInfo.point }}</div>
      </div>

      <div class="row">
        <button @click="activateWritingMode()">수정하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      userInfo: {},
      editForm: {
        password: "",
        phoneNumber: "",
        nickname: "",
        address: "",
        registrationNumber: "",
        businessName: "",
        latitude: "",
        longitude: "",
      },
      imgSrc: "",
      imgFile: null,
      imgErr: "",
      imgChanged: false,
      writingMode: false,
    };
  },
  props: ["userRole", "userId", "user"],
  methods: {
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
            this.userInfo.phoneNumber = this.formatPhoneNumber(this.userInfo.phoneNumber);
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },
    formatPhoneNumber(str) {
      const cleaned = str.replace(/\D/g, "");
      const match = cleaned.match(/^(\d{3})(\d{4})(\d{4})$/);

      if (match) {
        return match[1] + " - " + match[2] + " - " + match[3];
      } else {
        return str;
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

      imageForm.post(path, imageFormData, config).then((res) => {
        this.imgChanged = false;
        console.log("res :>> ", res);
      });
    },
    onSubmit() {
      console.log("this.editForm :>> ", this.editForm);
      const path = `/backend/${this.userRole}/${this.userId}`;

      let editUser = axios.create();
      editUser.defaults.headers.common["Access-Control-Allow-Methods"] =
        "GET,POST,PUT,DELETE,OPTIONS,PATCH";

      editUser
        .post(path, this.editForm)
        .then((res) => {
          console.log("res.data :>> ", res.data);
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
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

    .row {
      display: flex;
      align-items: center;

      .value {
        margin: 1rem;
      }

      .image-wrapper {
        width: 300px;
        height: 300px;
        border: 1px solid map-get($map: $theme, $key: "border");
        border-radius: 25%;
        overflow: hidden;
        cursor: pointer;
        transition: all 0.2s ease;
        position: relative;

        &.changed {
          border: 5px solid $main-color;
        }

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }

        .image-overlay {
          opacity: 0;
          z-index: 999;
          width: 300px;
          height: 300px;
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
        margin-left: 1rem;
        transition: all 0.2s ease;
        position: relative;

        &::before {
          content: "이미지가 저장되지 않았습니다.";
          position: absolute;
          white-space: nowrap;
          top: -2rem;
          left: 0;
          color: map-get($map: $theme, $key: "text");
        }
        &:hover {
          transform: scale(1.05);
        }
      }
    }

    button {
      padding: 1rem;
      background-color: $main-color;
      border: none;
      border-radius: 6px;
      color: $white;
      cursor: pointer;
    }
  }
}
</style>
