<template>
  <div>
    <div class="row">
      <div class="value">{{ userRole }}</div>
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
      <img :src="'data:image/jpeg;base64,' + this.userInfo.profilePicture" alt="" />
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      userInfo: {
        email: "",
        id: -1,
        nickname: "",
        profilePicture: "",
        phoneNumber: "",
        point: 0,
      },
    };
  },
  props: ["userRole", "userId"],
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

            console.log("res.data :>> ", res.data);
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },
    formatPhoneNumber(str) {
      const cleaned = str.replace(/\D/g, "");
      const match = cleaned.match(/^(\d{3})(\d{4})(\d{4})$/);

      console.log("cleaned :>> ", cleaned);
      console.log("match :>> ", match);

      if (match) {
        return match[1] + " - " + match[2] + " - " + match[3];
      } else {
        return str;
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

    .row {
      display: flex;
      align-items: center;

      .value {
        margin: 1rem;
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
