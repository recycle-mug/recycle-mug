<template>
  <div class="card">
    <nav>
      <div class="icon-wrapper" style="float:left;" @click="closeThisModal()">
        <font-awesome-icon :icon="['fas', 'times']" style="width:100%;"></font-awesome-icon>
      </div>
      <span>Close This Page</span>
      <div class="icon-wrapper hover-change" style="float:left;" @click="deleteThis()">
        <font-awesome-icon :icon="['far', 'trash-alt']" style="width:100%;"></font-awesome-icon>
      </div>
    </nav>

    <div class="cup-info">
      <div class="image-container">
        <div class="image-wrapper">
          <img :src="'data:image/jpeg;base64,' + cupInfo.image" alt="" />
        </div>
      </div>

      <div class="description">
        <h2 class="des-name">{{ cupInfo.name }}</h2>
        <h1 class="des-price">{{ addComma(cupInfo.price) }} 원</h1>
        <h1 class="des-price">{{ addComma(cupInfo.stockQuantity) }} 개</h1>
      </div>
    </div>

    <div class="cup-analysis" style="display:flex; justify-content:center; align-items:center">
      아래 공간에 이 컵에 대한 분석자료가 들어갈 예정
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { faTrashAlt } from "@fortawesome/fontawesome-free-regular";

faLibrary.add(faTimes, faTrashAlt);

export default {
  data() {
    return {
      cupInfo: {
        name: "",
        image: "",
        price: "",
        stockQuantity: "",
      },
    };
  },
  components: { FontAwesomeIcon },
  props: ["cupId"],
  methods: {
    getCupInfo() {
      const path = "/backend/cup/" + this.cupId;
      let cupInfo = axios.create();

      cupInfo
        .get(path)
        .then((res) => {
          this.cupInfo.name = res.data.name;
          this.cupInfo.image = res.data.image;
          this.cupInfo.price = res.data.price;
          this.cupInfo.stockQuantity = res.data.stockQuantity;
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
    },
    addComma(str) {
      let text = str.toString().replace(/[^0-9]/g, "");
      const result = text.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      return result;
    },
    closeThisModal() {
      this.$emit("closeModal");
    },
    deleteThis() {
      if (confirm("정말 이 컵을 삭제하시겠습니까?")) {
        const path = "/backend/cup/remove/" + this.cupId;
        let deleteRequest = axios.create();

        deleteRequest.defaults.headers["withCredentials"] = true;

        deleteRequest.delete(path).then((res) => {
          this.closeThisModal();
          window.location.reload();
        });
      }
    },
  },
  mounted() {
    this.getCupInfo();
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
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

      .cup-info {
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
            width: 100%;
            border-radius: 6px;
            overflow: hidden;

            img {
              width: 100%;
            }
          }
        }

        .description {
          padding: 1.5rem;
          float: left;
          width: 55%;
          border-left: 2px solid map-get($map: $theme, $key: "border");

          .des-name {
            color: map-get($map: $theme, $key: "text");
            font-weight: bold;
            font-size: 1.2rem;
            text-transform: uppercase;
          }

          .des-price {
            color: map-get($map: $theme, $key: "text");
            font-size: 1.2rem;
            padding-top: 1rem;
          }
        }
      }
    }
  }
}
</style>
