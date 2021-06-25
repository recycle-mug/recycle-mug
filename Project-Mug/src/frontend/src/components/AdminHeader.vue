<template>
  <div class="header-container">
    <div class="header">
      <side-bar :isActive="isActive"></side-bar>
      <div class="content-wrapper">
        <div class="content-left">
          <h1>Dashboard</h1>
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
              <p>John Doe</p>
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
import SideBar from "./SideBar";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faBell, faEnvelope } from "@fortawesome/fontawesome-free-regular";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faBell, faEnvelope);
export default {
  data() {
    return {
      theme: this.getTheme,
      isActive: "",
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
  },
  computed: {
    getTheme() {
      return this.$store.state.theme;
    },
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
      background-color: transparent;
      color: map-get($map: $theme, $key: "text");
      box-sizing: border-box;
      width: 100%;
      margin-bottom: 3rem;
      padding: 0.3rem 0;

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
          padding: 0.3rem 0;

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
              margin-right: 1rem;

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

            h1 {
              font-weight: bolder;
              font-size: 1.2rem;
              text-align: center;
              transition: 0.3s color linear;
              user-select: none;
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
