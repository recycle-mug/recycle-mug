<template>
  <div class="container">
    <div class="form-container store-form-container" v-if="role === 'store'">
      <form action="#">
        <h1>Login as Store</h1>
        <div class="social-container">
          <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon></a>
        </div>

        <span>or use your email for registration</span>
        <input type="text" placeholder="Store ID" />
        <input type="password" placeholder="Store Password" />
        <button>Login</button>
        <router-link to="../join/store" tag="span" class="caption"
          >Create Store Account</router-link
        >
      </form>
    </div>

    <div class="form-container member-form-container" v-else-if="role === 'member'">
      <form action="#">
        <h1>Login as Member</h1>
        <div class="social-container">
          <a href="#"><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon></a>
          <a href="#"><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon></a>
        </div>

        <span>or use your email for registration</span>
        <input type="text" placeholder="ID" />
        <input type="password" placeholder="Password" />
        <button>Login</button>
        <router-link to="../join/member" tag="span" class="caption"
          >Create Member Account</router-link
        >
      </form>
    </div>

    <div class="overlay-container overlay-left" v-if="role === 'store'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>Welcome Back!</h1>
          <p>Hi there</p>
          <!-- <button class="ghost">Login As Member</button> -->
          <router-link to="../login/member" tag="button" class="ghost">
            Login As Member</router-link
          >
        </div>
      </div>
    </div>

    <div class="overlay-container overlay-right" v-else-if="role === 'member'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>Welcome Back!</h1>
          <p>Hi there</p>
          <!-- <button class="ghost">Login As Store</button> -->

          <router-link to="../login/store" tag="button" class="ghost">Login As Store</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faFacebookF, faGooglePlusG } from "@fortawesome/free-brands-svg-icons";
import { faComment } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faFacebookF, faGooglePlusG, faComment);

export default {
  data() {
    return {
      transitionName: "slide-left",
    };
  },
  props: ["role"],
  components: { FontAwesomeIcon },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    text-align: center;

    .container {
      background-color: map-get($map: $theme, $key: "content-background");
      border-radius: 10px;
      box-shadow: $shadow;
      position: relative;
      overflow: hidden;
      width: 768px;
      max-width: 100%;
      min-height: 480px;
      margin: auto;

      .form-container {
        position: absolute;
        top: 0;
        height: 100%;
        transition: all 0.6s ease-in-out;

        form {
          background-color: map-get($map: $theme, $key: "content-background");
          display: flex;
          align-items: center;
          justify-content: center;
          flex-direction: column;
          padding: 0 3rem;
          height: 100%;
          text-align: center;

          h1 {
            font-weight: bold;
            margin: 0.5rem;
            font-size: 1.5rem;
          }

          .social-container {
            margin: 0.7rem 0;
            a {
              color: $main-color;
              border: 1px solid $main-color;
              border-radius: 50%;
              display: inline-flex;
              justify-content: center;
              align-items: center;
              margin: 0 0.1rem;
              height: 40px;
              width: 40px;
            }
          }

          span {
            font-size: 0.7rem;
            margin-bottom: 0.5rem;
          }

          input {
            background-color: map-get($map: $theme, $key: "background");
            border: none;
            padding: 1rem 1.4rem;
            margin: 0.7rem 0;
            width: 100%;

            &:focus {
              outline-color: $sub-color;
              border: none;
            }
          }

          button {
            border-radius: 20px;
            margin-top: 1rem;
            border: 1px solid $main-color;
            background-color: $main-color;
            color: $white;
            border-radius: 20px;
            font-size: 1rem;
            font-weight: bold;
            padding: 1rem 3rem;
            text-transform: uppercase;

            &:active {
              transform: scale(0.95);
            }

            &:focus {
              outline: none;
            }
          }

          .caption {
            margin: 1rem;
            text-decoration: underline;
            cursor: pointer;
            font-size: 0.75rem;
          }
        }
      }

      .store-form-container {
        right: 0;
        width: 50%;
        opacity: 1;
        z-index: 1;
      }

      .member-form-container {
        left: 0;
        width: 50%;
        z-index: 2;
      }

      .overlay-container {
        position: absolute;
        top: 0;
        width: 50%;
        height: 100%;
        overflow: hidden;
        transform: translateX(0);
        transition: transform 0.6s ease-in-out;

        .overlay {
          background-color: $main-color;
          color: $white;
          position: relative;
          height: 100%;
          width: 100%;
          transform: translateX(0);
          transition: transform 0.6s ease-in-out;

          .overlay-panel {
            position: absolute;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            padding: 0 3rem;
            text-align: center;
            top: 0;
            height: 100%;
            width: 100%;
            transform: translateX(0);
            transition: transform 0.6s ease-in-out;
            box-sizing: border-box;

            h1 {
              font-weight: bold;
              margin: 0.5rem;
              font-size: 1.5rem;
            }

            p {
              letter-spacing: 0.1rem;
              margin: 1.5rem 0 2rem;
              line-height: 1.2rem;
            }

            button {
              border-radius: 20px;
              border: 1px solid $white;
              background-color: transparent;
              color: $white;
              border-radius: 20px;
              font-weight: bold;
              padding: 1rem 3rem;
              letter-spacing: 0.05rem;

              &:active {
                transform: scale(0.95);
              }

              &:focus {
                outline: none;
              }
            }
          }
        }
      }

      .overlay-left {
        left: 0;
      }

      .overlay-right {
        right: 0;
      }
    }
  }
}
</style>
