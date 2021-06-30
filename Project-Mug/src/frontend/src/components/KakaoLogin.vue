<template>
  <div>access token : {{ access_token }}</div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      REST_API_KEY: "210e6ee868e2837c5e6d0805cc037348",
      REDIRECT_URI: "http://localhost:8080/kakao",
      access_token: null,
    };
  },
  methods: {
    openKakaoLogin() {
      const path = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${this.REST_API_KEY}&redirect_uri=${this.REDIRECT_URI}`;

      window.location.href = path;
    },
    async getKakaoAccessToken(code) {
      const path = "/tokenApi/oauth/token";

      let getToken = axios.create({
        baseUrl: path,
      });

      getToken.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
      getToken.defaults.headers.common["Access-Control-Allow-Methods"] =
        "GET,POST,PUT,DELETE,OPTIONS";

      getToken.defaults.headers.common["Content-Type"] =
        "application/x-www-form-urlencoded;charset=utf-8";

      getToken.defaults.params = {
        grant_type: "authorization_code",
        client_id: this.REST_API_KEY,
        redirect_uri: this.REDIRECT_URI,
        code: code,
      };

      await getToken
        .post(path)
        .then((res) => {
          if (res.data.access_token) {
            this.access_token = res.data.access_token;
            this.getKakaoInfo();
          } else {
            throw "오류가 발생했습니다. 다시 시도해주세요";
            // alert("오류가 발생했습니다. 다시 시도해주세요");
            // const re_path = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${this.REST_API_KEY}&redirect_uri=${this.REDIRECT_URI}`;
            // window.location.href = re_path;
          }
        })
        .catch(() => {
          this.openKakaoLogin();
        });
    },
    async getKakaoInfo() {
      console.log("get kakao info");
      console.log("this.access_token :>> ", this.access_token);
      const path = "/infoApi/v2/user/me";

      let getInfo = axios.create({
        baseUrl: path,
      });

      getInfo.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
      getInfo.defaults.headers.common["Access-Control-Allow-Methods"] =
        "GET,POST,PUT,DELETE,OPTIONS";

      getInfo.defaults.headers.common["Content-Type"] =
        "application/x-www-form-urlencoded;charset=utf-8";

      getInfo.defaults.headers.common["Authorization"] = `Bearer ${this.access_token}`;

      await getInfo
        .post(path)
        .then((res) => {
          // console.log("res.data.kakao_account.email :>> ", res.data.kakao_account.email);
          console.log("res :>> ", res);
        })
        .catch((err) => {
          throw err;
        });
    },
  },
  mounted() {
    if (!this.$route.query.code) this.openKakaoLogin();
    else {
      this.getKakaoAccessToken(this.$route.query.code);
    }
  },
};
</script>

<style lang="scss" scoped></style>
