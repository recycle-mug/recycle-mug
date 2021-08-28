<template>
  <div class="admin-sidebar">
    <ul>
      <li class="first-li">
        <div class="icon-wrapper">
          <img src="../assets/logo/logo.png" alt="" />
        </div>
        <span>WeCup</span>
      </li>

      <router-link :to="{ name: 'admin' }" tag="li">
        <div class="icon-wrapper">
          <font-awesome-icon :icon="['fas', 'clipboard-list']"></font-awesome-icon>
        </div>
        <span>Dashboard</span>
      </router-link>

      <router-link :to="{ name: 'admin-cups' }" tag="li">
        <div class="icon-wrapper">
          <font-awesome-icon :icon="['fas', 'coffee']"></font-awesome-icon>
        </div>
        <span>Cups</span>
      </router-link>

      <router-link :to="{ name: 'admin-users' }" tag="li">
        <div class="icon-wrapper">
          <font-awesome-icon :icon="['fas', 'users']"></font-awesome-icon>
        </div>
        <span>Users</span>
      </router-link>

      <li>
        <div class="icon-wrapper">
          <font-awesome-icon :icon="['fas', 'chart-line']"></font-awesome-icon>
        </div>
        <span>Charts</span>
      </li>
    </ul>
    <ul class="content-bottom">
      <li>
        <div class="icon-wrapper">
          <font-awesome-icon :icon="['fas', 'cog']"></font-awesome-icon>
        </div>
        <span>Setting</span>
      </li>
    </ul>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";
import {
  faClipboardList,
  faCoffee,
  faUsers,
  faChartLine,
  faCog,
} from "@fortawesome/free-solid-svg-icons";

faLibrary.add(faClipboardList, faCoffee, faUsers, faChartLine, faCog);

export default {
  components: { FontAwesomeIcon },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme,"name")} {
    * {
      box-sizing: border-box;
    }

    @media screen and (max-width: 1019px) {
      .admin-sidebar {
        background-color: map-get($map: $theme, $key: "table");
        border: 1px solid map-get($map: $theme, $key: "border");
        border-radius: 6px;
        position: absolute;
        width: 100%;
        box-sizing: border-box;
        top: 55px;
        left: 0;
        box-shadow: $shadow-light;

        &::before {
          content: "";
          position: absolute;
          top: 0px;
          left: 40px;
          border: 10px solid transparent;
          border-bottom-color: map-get($map: $theme, $key: "table");
          border-top: 0;
          margin-left: -10px;
          margin-top: -10px;
          z-index: 51;
        }

        &::after {
          content: "";
          position: absolute;
          top: 0px;
          left: 40px;
          border: 11px solid transparent;
          border-bottom-color: map-get($map: $theme, $key: "border");
          border-top: 0;
          margin-left: -11px;
          margin-top: -11px;
        }

        ul {
          display: flex;
          justify-content: space-evenly;
          list-style: none;

          .first-li {
            display: none;
          }

          li {
            height: 100%;
            padding: 1rem;
            cursor: pointer;
            &:hover {
              font-weight: bold;
              color: $main-color;
            }

            div {
              display: none;
            }
          }
        }

        .content-bottom {
          display: none;
        }
      }
    }
    @media screen and (min-width: 1020px) {
      .admin-sidebar {
        background-color: map-get($map: $theme, $key: "content-background");
        position: fixed;
        top: 0;
        left: 0;
        height: 100vh;
        width: 80px;
        border-right: 1px solid map-get($map: $theme, $key: "border");
        z-index: 99;
        display: flex;
        flex-direction: column;
        align-items: center;
        transition: all 0.3s ease;

        &:hover {
          width: 360px;
        }

        &:hover ul li span {
          display: block;
        }

        ul li span {
          display: none;
        }

        ul {
          width: 100%;
          list-style: none;
          display: flex;
          flex-direction: column;

          li {
            display: flex;
            height: 80px;
            align-items: center;
            cursor: pointer;

            &:not(.first-li):hover {
              div {
                border-right: 5px solid $main-color;
                box-shadow: $shadow;
                color: $main-color;
              }
              span {
                font-weight: bold;
                color: $main-color;
              }
            }

            div {
              width: 80px;
              height: 80px;
              display: flex;
              justify-content: center;
              align-items: center;
              font-size: 1.6rem;
              position: fixed;
              color: map-get($map: $theme, $key: "text-light");
            }

            span {
              position: fixed;
              left: 100px;
              letter-spacing: 0.1rem;
            }
          }

          .first-li {
            user-select: none;
            cursor: default;
            margin-bottom: 1rem;
            div img {
              width: 80px;
              height: 80px;
              padding: 1rem;
            }
            span {
              font-size: 1.2rem;
              font-weight: bolder;
              color: map-get($map: $theme, $key: "text");
              letter-spacing: 0.2rem;
            }
          }
        }

        .content-bottom {
          position: absolute;
          bottom: 0;
        }
      }
    }
  }
}
</style>
