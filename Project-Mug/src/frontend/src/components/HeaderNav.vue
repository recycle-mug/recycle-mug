<template>
  <div class="header-container">
    <div class="header">
      <side-bar :isActive="isActive"></side-bar>
      <div class="content-wrapper">
        <div class="content-left">
          <div class="icon-wrapper">
            <div
              style="width:100%; cursor:pointer;"
              :class="isActive + ' menu-toggler'"
              @click="activeMenu"
            >
              <span></span>
            </div>
          </div>
        </div>

        <router-link :to="{ name: 'main' }" class="content-center">
          <h1>RecycleMug</h1>
        </router-link>

        <div class="content-right">
          <div v-if="isLogin">
            <div class="profile-btn" @click="activateDropdown()">
              <img :src="'data:image/jpeg;base64,' + profileImg" alt="" />
              <span>{{ username }}님 안녕하세요</span>
              <div class="icon-wrapper">
                <font-awesome-icon
                  :icon="['fas', 'user']"
                  style="width:100%; cursor:pointer;"
                ></font-awesome-icon>
              </div>
            </div>

            <ul class="sub-menu" v-if="dropDown">
              <router-link :to="{ name: 'profile' }" tag="li" class="menu-item">
                마이페이지
              </router-link>
              <li class="menu-item" @click="logout">로그아웃</li>
            </ul>
          </div>

          <router-link
            :to="{ name: 'login', query: { role: 'customer' } }"
            tag="span"
            class="login-btn"
            v-else
            >로그인 / 회원가입</router-link
          >
        </div>

        <div class="theme-picker-wrapper">
          <theme-picker></theme-picker>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ThemePicker from "./ThemePicker.vue";
import SideBar from "./SideBar";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faUser);

export default {
  data() {
    return {
      theme: this.getTheme,
      isActive: "",
      username: "익명",
      isLogin: false,
      dropDown: false,
      profileImg: "",
    };
  },
  components: {
    ThemePicker,
    SideBar,
    FontAwesomeIcon,
  },
  methods: {
    activeMenu() {
      if (this.isActive == "active") {
        this.isActive = "";
      } else {
        this.isActive = "active";
      }
    },
    logout() {
      this.$store
        .dispatch("LOGOUT")
        .then((window.location.href = "/"))
        .catch((err) => (this.errors.response = err));
    },
    getProfile() {
      this.$store.dispatch("GETPROFILE").then(() => {
        this.isLogin = true;
        this.username = this.getUser.nickname;
        this.profileImg = this.getUser.profilePicture;
      });
    },
    activateDropdown() {
      this.dropDown = !this.dropDown;
    },
  },
  computed: {
    getTheme() {
      return this.$store.state.theme;
    },
    getUser() {
      return this.$store.state.user;
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
    background-color: map-get($map: $theme, $key: "content-background");
    color: map-get($map: $theme, $key: "text");
    box-sizing: border-box;

    .header-container {
      background-color: map-get($map: $theme, $key: "content-background");
      color: map-get($map: $theme, $key: "text");
      box-sizing: border-box;
      width: 100%;
      margin-bottom: 3rem;

      .header {
        width: 100%;
        max-width: 1140px;
        margin-left: auto;
        margin-right: auto;
        position: relative;
        z-index: 10;
        padding: 0 2rem;

        .content-wrapper {
          box-sizing: border-box;
          display: flex;
          align-items: center;
          justify-content: center;
          padding: 2rem 0;

          .content-left {
            box-sizing: border-box;
            line-height: 1.33;
            color: map-get($map: $theme, $key: "text");
            margin: 0;
            width: 100%;
            align-items: center;
            display: flex;

            .icon-wrapper {
              width: 30px;
              padding: 20px;
              color: map-get($map: $theme, $key: "text");

              .menu-toggler {
                top: 20px;
                left: 20px;
                width: 16px;
                height: 16px;
                outline: none;
                display: flex;
                align-items: center;
                z-index: 999;

                span,
                span::before,
                span::after {
                  position: absolute;
                  content: "";
                  width: 16px;
                  height: 2.5px;
                  background: map-get($map: $theme, $key: "text");
                  border-radius: 20px;
                  transition: 500ms cubic-bezier(0.77, 0, 0.175, 1);
                }

                span::before {
                  top: -5px;
                }

                span::after {
                  top: 5px;
                }

                &.active > span {
                  background: transparent;
                }

                &.active > span::before,
                &.active > span::after {
                  top: 0px;
                }

                &.active > span::before {
                  transform: rotate(-225deg);
                }

                &.active > span::after {
                  transform: rotate(225deg);
                }
              }
            }
          }

          .content-center {
            box-sizing: border-box;
            line-height: 1.33;
            color: map-get($map: $theme, $key: "text");
            margin: 0;
            width: 100%;
            align-items: center;
            display: flex;
            justify-content: center;

            h1 {
              font-weight: bolder;
              font-size: 1.5rem;
              cursor: pointer;
              text-align: center;
              transition: 0.3s color linear;
            }
          }

          .content-right {
            box-sizing: border-box;
            line-height: 1.33;
            color: map-get($map: $theme, $key: "text");
            margin: 0;
            width: 100%;
            align-items: center;
            display: flex;
            justify-content: flex-end;
          }

          .login-btn {
            font-weight: bold;
            user-select: none;
            cursor: pointer;
            color: map-get($map: $theme, $key: "text-light");
            &::after {
              content: "";
              width: 0px;
              height: 0.125rem;
              display: block;
              background: map-get($map: $theme, $key: "text");
              transition: all 0.3s ease-in-out;
            }
            &:hover::after {
              font-weight: bolder;
              width: 100%;
            }
          }

          .profile-btn {
            font-weight: bold;
            user-select: none;
            cursor: pointer;
            color: map-get($map: $theme, $key: "text-light");
            display: flex;
            align-items: center;

            img {
              width: 35px;
              height: 35px;
              border-radius: 40%;
              border: 1px solid map-get($map: $theme, $key: "border");
              margin-right: 10px;
              overflow: hidden;
            }

            span {
              font-weight: bold;
              user-select: none;
              cursor: pointer;
              color: map-get($map: $theme, $key: "text-light");
              &::after {
                content: "";
                width: 0px;
                height: 0.125rem;
                display: block;
                background: map-get($map: $theme, $key: "text");
                transition: all 0.3s ease-in-out;
              }
              &:hover::after {
                font-weight: bolder;
                width: 100%;
              }
            }
          }

          ul {
            list-style: none;
            position: absolute;
            bottom: -80px;

            &:first-child {
              width: 100%;
            }

            &-item {
              display: flex;
            }

            li {
              padding: 1rem;
              cursor: pointer;
              background-color: map-get($map: $theme, $key: "content-background");
              border: 1px solid map-get($map: $theme, $key: "border");
              transition: all 0.2s ease;

              &:hover {
                font-weight: bold;
                background-color: map-get($map: $theme, $key: "background");
              }
            }
          }

          .theme-picker-wrapper {
            box-sizing: border-box;
            margin-left: 50px;
            display: flex;
            justify-content: center;
            align-items: center;
          }
        }
      }
    }

    .icon-wrapper {
      width: 30px;
      padding: 10px;
      color: map-get($map: $theme, $key: "text");
    }
  }
}
</style>
