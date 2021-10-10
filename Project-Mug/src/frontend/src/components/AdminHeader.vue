<template>
  <div class="header-container">
    <div class="header" :class="{ active: !isScrollDown }">
      <div class="content-wrapper">
        <div class="content-left">
          <div class="icon-wrapper" v-if="windowWidth < 1020">
            <div
              style="width:100%; cursor:pointer;"
              :class="isActive + ' menu-toggler'"
              @click="activeMenu"
            >
              <span></span>
            </div>
          </div>
          <admin-sidebar v-if="isActive"></admin-sidebar>
          <router-link :to="{ name: 'admin' }" tag="span">admin / </router-link>
          <h1>{{ routeName }}</h1>
        </div>

        <div class="content-center"></div>

        <div class="content-right">
          <div tag="div" class="icon-wrapper">
            <font-awesome-icon
              :icon="['far', 'envelope']"
              style="width:100%; cursor:pointer;"
            ></font-awesome-icon>
            <span class="icon-alert" v-if="false"></span>
          </div>

          <div tag="div" class="icon-wrapper">
            <font-awesome-icon
              :icon="['far', 'bell']"
              style="width:100%; cursor:pointer;"
            ></font-awesome-icon>
            <span class="icon-alert" v-if="true"></span>
          </div>

          <div class="profile-wrapper">
            <div class="profile-caption">
              <p>Admin</p>
              <span class="login-btn">Admin</span>
            </div>
            <div tag="div" class="profile-pic">
              <img
                src="https://www.controlbooth.com/data/avatars/o/26/26513.jpg?1606457659"
                alt=""
              />
            </div>
          </div>
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
import AdminSidebar from "./AdminSidebar.vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faBell, faEnvelope } from "@fortawesome/fontawesome-free-regular";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faBell, faEnvelope);
export default {
  data() {
    return {
      theme: this.getTheme,
      isActive: "",
      windowWidth: window.innerWidth,
      isScrollDown: true,
      lastScroll: 0,
      routeName: "Dashboard",
    };
  },
  components: {
    ThemePicker,
    FontAwesomeIcon,
    AdminSidebar,
  },
  methods: {
    activeMenu() {
      if (this.isActive == "active") {
        this.isActive = "";
      } else {
        this.isActive = "active";
      }
    },
    onResize() {
      this.windowWidth = window.innerWidth;
    },
    onScroll(event) {
      if (event.target.scrollTop <= this.lastScroll) {
        this.isScrollDown = true;
      } else {
        this.isScrollDown = false;
      }

      this.lastScroll = event.target.scrollTop;
    },
  },
  computed: {
    getTheme() {
      return this.$store.state.theme;
    },
    getCurrentRouteName() {
      return this.$route.name;
    },
  },
  mounted() {
    this.$nextTick(() => {
      window.addEventListener("resize", this.onResize);
      document.querySelector(".page").addEventListener("scroll", this.onScroll);
    });

    const currentRouteName = this.getCurrentRouteName.slice(6);
    if (currentRouteName === "") {
      this.routeName = "Dashboard";
    } else if (currentRouteName === "cups") {
      this.routeName = "Cups";
    } else if (currentRouteName === "users") {
      this.routeName = "Users";
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
    * {
      box-sizing: border-box;
    }

    .header-container {
      background-color: map-get($map: $theme, $key: "background");
      color: map-get($map: $theme, $key: "text");
      box-sizing: border-box;
      width: 100%;
      margin-bottom: 3rem;
      padding: 0.25rem 0;
      display: flex;
      justify-content: center;
      align-items: center;

      .header {
        width: 100%;
        max-width: 1020px;
        margin-left: auto;
        margin-right: auto;
        position: relative;
        z-index: 10;
        padding: 0 2rem;
        transition: all 0.3s ease;

        &.active {
          background-color: map-get($map: $theme, $key: "content-background");
          z-index: 50;
          margin: 1rem auto;
          position: fixed;
          top: 0;
          border-radius: 6px;
          box-shadow: $shadow;
        }

        .content-wrapper {
          box-sizing: border-box;
          display: flex;
          align-items: center;
          justify-content: center;
          padding: 0.3rem 0;

          .content-left {
            box-sizing: border-box;
            line-height: 1.33;
            color: map-get($map: $theme, $key: "text");
            margin: 0;
            width: 100%;
            align-items: center;
            display: flex;
            user-select: none;

            .icon-wrapper {
              width: 30px;
              color: map-get($map: $theme, $key: "text");
              margin-right: 0.5rem;

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

            span {
              font-size: 1rem;
              color: map-get($map: $theme, $key: "text-light");
              font-weight: bold;
              margin-right: 10px;
              cursor: pointer;
            }

            h1 {
              font-weight: bolder;
              font-size: 1.2rem;
              text-align: center;
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

          .icon-wrapper {
            width: 40px;
            color: map-get($map: $theme, $key: "text-light");
            font-size: 1.15rem;
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;

            .icon-alert {
              background-color: $error-msg;
              width: 10px;
              height: 10px;
              border-radius: 10px;
              position: absolute;
              cursor: pointer;
              top: -5px;
              right: 5px;
              opacity: 0;
              animation: pulsate 1.5s ease-out;
              animation-iteration-count: infinite;

              @keyframes pulsate {
                0% {
                  -webkit-transform: scale(0.1, 0.1);
                  opacity: 0;
                }
                50% {
                  opacity: 1;
                }
                100% {
                  -webkit-transform: scale(1, 1);
                  opacity: 0;
                }
              }
            }
          }

          .profile-wrapper {
            display: flex;
            margin: 0 0.5rem;
            cursor: pointer;

            .profile-caption {
              display: flex;
              flex-direction: column;
              text-align: right;

              p {
                font-weight: bold;
                font-size: 1.05rem;
                letter-spacing: 0.05rem;
              }

              span {
                color: map-get($map: $theme, $key: "text-light");
                font-size: 0.7rem;
                user-select: none;
                letter-spacing: 0.1rem;
              }
            }

            .profile-pic {
              width: 40px;
              height: 40px;
              border-radius: 20px;
              overflow: hidden;
              margin-left: 8px;

              img {
                width: 100%;
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
  }
}
</style>
