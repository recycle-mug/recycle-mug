<template>
  <div :class="getTheme">
    <header-nav></header-nav>
    <div class="content-body">
      <profile-form-box v-if="getUserInfo" :userRole="userRole" :userId="userId"></profile-form-box>
    </div>
  </div>
</template>

<script>
import HeaderNav from "../components/HeaderNav";
import ProfileFormBox from "../components/ProfileFormBox.vue";
import axios from "axios";

export default {
  data() {
    return {
      userRole: "",
      userId: "",
      getUserInfo: false,
    };
  },
  components: { HeaderNav, ProfileFormBox },
  computed: {
    getTheme() {
      return this.$store.state.theme;
    },
  },
  methods: {
    async getProfile() {
      const path = "/backend/profile";

      const { accessToken } = localStorage;
      if (!accessToken) {
        this.isLogin = false;
        this.userRole = "";
        this.userId = "";
      } else {
        const authUser = axios.create({ baseUrl: path });
        authUser.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
        authUser.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        authUser.defaults.headers.common["Access-Control-Allow-Methods"] =
          "GET,POST,PUT,DELETE,OPTIONS";

        authUser.defaults.headers.common["Content-Type"] =
          "application/x-www-form-urlencoded;charset=utf-8";

        await authUser
          .get(path)
          .then((res) => {
            if (res.data.error) {
              throw res.data.error;
            } else {
              this.userId = res.data.id;

              if (res.data.role === "ROLE_CUSTOMER") {
                this.userRole = "customer";
              } else if (res.data.role === "ROLE_PARTNER") {
                this.userRole = "partner";
              } else if (res.data.role === "ROLE_ADMIN") {
                this.userRole = "admin";
              }

              this.getUserInfo = true;
            }
          })
          .catch((error) => {
            console.error(error);
          });
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
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");
    width: 100%;
    height: 100%;

    .content-body {
      width: 100%;
      max-width: 1140px;
      margin: auto;
      box-sizing: border-box;
    }
  }
}
</style>
