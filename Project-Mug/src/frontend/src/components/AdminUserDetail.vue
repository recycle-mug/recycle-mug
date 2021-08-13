<template>
  <div class="card">
    <nav>
      <div class="icon-wrapper" style="float:left;" @click="closeThisModal()">
        <font-awesome-icon :icon="['fas', 'times']" style="width:100%;"></font-awesome-icon>
      </div>
      <span @click="closeThisModal()" style="cursor:pointer">Close This Page</span>
    </nav>

    <div class="user-info">
      <div class="image-container">
        <div class="image-wrapper">
          <img :src="'data:image/jpeg;base64,' + user.profilePicture" alt="" />
        </div>
      </div>

      <div class="description" v-if="role === 'partner'">
        <div class="role-row partner">
          <div class="value">{{ role }}</div>
        </div>
        <div class="row">
          <div class="category">가게 이름</div>
          <h2 class="value" v-if="user.businessName">{{ user.businessName }}</h2>
        </div>

        <div class="row">
          <div class="category">이메일</div>
          <h1 class="value" v-if="user.email">{{ user.email }}</h1>
        </div>

        <div class="row">
          <div class="category">번호</div>
          <h1 class="value" v-if="user.phoneNumber">{{ formatPhoneNumber(user.phoneNumber) }}</h1>
        </div>

        <div class="row">
          <div class="category">주소</div>
          <h1 class="value" v-if="user.address">{{ user.address }}</h1>
        </div>

        <div class="row">
          <div class="category">포인트</div>
          <h1 class="value point" v-if="user.point">
            {{ addComma(user.point) }}
            <div class="icon-wrapper">
              <font-awesome-icon
                :icon="['fab', 'product-hunt']"
                style="width:100%;"
              ></font-awesome-icon>
            </div>
          </h1>
        </div>
      </div>

      <div class="description" v-if="role === 'customer'">
        <div class="role-row customer">
          <div class="value">{{ role }}</div>
        </div>

        <div class="row">
          <div class="category">닉네임</div>
          <h2 class="value" v-if="user.nickname">{{ user.nickname }}</h2>
        </div>

        <div class="row">
          <div class="category">이메일</div>
          <h1 class="value" v-if="user.email">{{ user.email }}</h1>
        </div>

        <div class="row">
          <div class="category">번호</div>
          <h1 class="value" v-if="user.phoneNumber">{{ formatPhoneNumber(user.phoneNumber) }}</h1>
        </div>

        <div class="row">
          <div class="category">포인트</div>
          <h1 class="value point" v-if="user.point">
            {{ addComma(user.point) }}
            <div class="icon-wrapper">
              <font-awesome-icon
                :icon="['fab', 'product-hunt']"
                style="width:100%;"
              ></font-awesome-icon>
            </div>
          </h1>
        </div>

        <div class="state-row" v-if="user.customerState === 'USE'">
          <div class="state">
            <font-awesome-icon :icon="['fas', 'coffee']"></font-awesome-icon>
            <span>컵 사용 중</span>
          </div>
        </div>

        <div class="state-row" v-else-if="user.customerState === 'OVERDUE'">
          <div class="state overdue">
            <font-awesome-icon :icon="['fas', 'coffee']"></font-awesome-icon>
            <span>컵 연체 중</span>
          </div>
        </div>
      </div>
    </div>

    <div
      class="user-analysis"
      style="display:flex; justify-content:center; align-items:center"
    ></div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";
import { faTimes, faCoffee } from "@fortawesome/free-solid-svg-icons";
import { faProductHunt } from "@fortawesome/free-brands-svg-icons";
import { faTrashAlt } from "@fortawesome/fontawesome-free-regular";

faLibrary.add(faTimes, faCoffee, faTrashAlt, faProductHunt);

export default {
  data() {
    return {};
  },
  components: { FontAwesomeIcon },
  props: ["user", "role"],
  methods: {
    addComma(str) {
      let text = str.toString().replace(/[^0-9]/g, "");
      const result = text.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      return result;
    },
    formatPhoneNumber(str) {
      if (str) {
        const cleaned = str.replace(/\D/g, "");
        const match = cleaned.match(/^(\d{3})(\d{4})(\d{4})$/);

        if (match) {
          return match[1] + " - " + match[2] + " - " + match[3];
        } else {
          return str;
        }
      }
    },
    closeThisModal() {
      this.$emit("closeModal");
    },
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .role-row {
      width: 100%;
      user-select: none;
      float: right;
      padding-bottom: 1rem;

      .value {
        width: fit-content;
        padding: 5px 10px;
        border-radius: 12px;
        font-weight: bold;
        color: map-get($map: $theme, $key: "text");
      }

      &.partner .value {
        background-color: rgba($main-color, 0.5);
        border: 2px solid $main-color;
      }

      &.customer .value {
        background-color: rgba($sub-color, 0.5);
        border: 2px solid $sub-color;
      }
    }

    .card {
      width: 100%;
      height: 100%;
      overflow: auto;

      nav {
        width: 100%;
        color: map-get($map: $theme, $key: "text-light");
        padding: 1rem;
        border-bottom: 2px solid map-get($map: $theme, $key: "border");
        display: flex;
        align-items: center;

        .icon-wrapper {
          width: 50px;
          align-items: center;
          cursor: pointer;
          padding: 10px;
          transition: all 0.3s ease;

          &:hover {
            transform: scale(1.2);
          }
        }

        .hover-change:hover {
          color: $error-msg;
          transform: scale(1.2);
        }

        span {
          user-select: none;
          width: 100%;
        }
      }

      .user-info {
        display: flex;
        border-bottom: 2px solid map-get($map: $theme, $key: "border");

        .image-container {
          padding: 1.5rem;
          width: 45%;
          float: left;
          display: flex;
          justify-content: center;
          align-items: center;

          .image-wrapper {
            width: 300px;
            height: 300px;
            border: 1px solid map-get($map: $theme, $key: "border");
            border-radius: 20%;
            overflow: hidden;
            display: flex;
            justify-content: center;
            align-items: center;

            img {
              width: 100%;
              height: 100%;
              object-fit: cover;
              -webkit-user-drag: none;
            }
          }
        }

        .description {
          padding: 1.5rem;
          float: left;
          width: 55%;

          .row {
            color: map-get($map: $theme, $key: "text");
            display: grid;
            align-items: center;
            width: 100%;
            grid-template-columns: 0.3fr 1fr;
            position: relative;

            .category {
              text-align: right;
              padding: 1rem 1rem 1rem 0;
              font-size: 0.9rem;
            }

            .value {
              padding: 1rem;
              font-weight: bold;
              text-align: right;

              &.point {
                display: flex;
                justify-content: flex-end;

                .icon-wrapper {
                  margin-left: 5px;
                  color: $sub-color;
                }
              }
            }
          }

          .state-row {
            color: map-get($map: $theme, $key: "text");
            display: flex;
            justify-content: flex-end;
            align-items: center;
            width: 100%;
            position: relative;
            padding: 1rem 1rem 1rem 0;

            .state {
              padding: 1rem;
              border: 1px solid map-get($map: $theme, $key: "border");
              border-radius: 6px;
              background-color: map-get($map: $theme, $key: "background");
              display: flex;
              justify-content: center;
              position: relative;
              transition: all 0.2s ease;
              user-select: none;

              &.overdue {
                background-color: $error-msg;
                color: $white;
              }

              &:hover {
                transform: scale(1.1);
              }

              span {
                margin-left: 10px;
              }
            }
          }
        }
      }
    }
  }
}
</style>
