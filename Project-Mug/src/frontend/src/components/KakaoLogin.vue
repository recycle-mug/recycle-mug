<template>
  <div></div>
</template>

<script>
export default {
  methods: {
    openKakaoLogin() {
      const REST_API_KEY = "210e6ee868e2837c5e6d0805cc037348";
      const REDIRECT_URI = "http://localhost:8080/getKakaoKey";
      const path = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}`;

      //   window.open(path, "pop", "width=570, height=420,scrollbars=yes,resizable=yes");
      window.location.href = path;
    },
    getKakaoInfo() {
      console.log("this.code :>> ", this.code);
    },
  },
  mounted() {
    this.openKakaoLogin();
  },
};
</script>

<style lang="scss" scoped></style>
