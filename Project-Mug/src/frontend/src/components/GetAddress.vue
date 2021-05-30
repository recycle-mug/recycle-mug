<template>
  <div>
    <form>
      <input id="inputYn" name="inputYn" />
      <button @click.prevent="goPopup">주소검색</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      popup: null,
      loading: false,
      post: null,
      error: null,
    };
  },

  methods: {
    goPopup() {
      const routeData = this.$router.resolve({ name: "address" });
      this.popup = window.open(
        routeData.href,
        "pop",
        "width=570, height=420,scrollbars=yes,resizable=yes",
      );
    },
    async fetchData() {
      // this.error = this.post = null;
      // this.loading = true;
      // const requestOptions = {
      //   method: "POST",
      //   mode: "cors",
      //   headers: { "Content-Type": "application/json" },
      // };
      // await fetch("https://www.juso.go.kr/addrlink/addrLinkUrl.do", requestOptions)
      //   .then((result) => {
      //     console.log("result :>> ", result);
      //   })
      //   .catch((err) => {
      //     console.log("err :>> ", err);
      //   });
      const url = "https://www.juso.go.kr/addrlink/addrLinkUrl.do";
      const options = {
        method: "POST",
        url: url,
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json;charset=UTF-8",
        },
      };

      const response = await axios(options)
        .then((res) => {
          console.log("res :>> ", res);
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
    },
  },
  created() {
    this.fetchData;
  },
};
</script>

<style lang="scss" scoped></style>
