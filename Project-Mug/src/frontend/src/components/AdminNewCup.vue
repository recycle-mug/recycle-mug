<template>
  <div class="form-wrapper">
    <form action="#">
      <div class="image-wrapper" @click="chooseImg">
        <input type="file" id="fileBtn" style="display:none" @change="addImgByClick" />
        <img :src="imgSrc" v-if="imgSrc.length > 0" />
        <div class="upload-img-icon" v-else>
          <div class="upload-icon">
            <font-awesome-icon :icon="['fas', 'upload']"></font-awesome-icon>
          </div>
          <span class="upload-text">Image Upload</span>
          <span class="error-msg">{{ imgErr }}</span>
        </div>
      </div>
      <div class="content-wrapper">
        <div class="content-row">
          <div class="row-title">Name</div>
          <input type="text" class="row-input" placeholder="Cup Name" v-model="name" />
        </div>

        <div class="content-row" style="display:flex; flex-direction:row;margin:0">
          <div class="content-row" style="margin-right:1.5rem">
            <div class="row-title">가격</div>
            <div class="input-wrapper" style="display:flex">
              <input
                type="text"
                class="row-input"
                placeholder="2,000"
                v-model="price"
                @keyup="setInputFilter"
                style="text-align:right"
                id="price"
              />
              <p>원</p>
            </div>
          </div>

          <div class="content-row">
            <div class="row-title">수량</div>
            <div class="input-wrapper" style="display:flex">
              <input
                type="text"
                class="row-input"
                placeholder="2,000"
                v-model="stockQuantity"
                @keyup="setInputFilter"
                style="text-align:right"
                id="stockQuantity"
              />
              <p>개</p>
            </div>
          </div>
        </div>

        <div class="content-row">
          <span class="error-msg">{{ errorMsg }}</span>
          <button type="submit" @click.prevent="onSubmit">컵 추가하기</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faUpload } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";
import axios from "axios";

faLibrary.add(faUpload);

export default {
  data() {
    return {
      writingMode: true,
      imgSrc: "",
      name: "",
      price: "",
      stockQuantity: "",
      formData: new FormData(),
      imgErr: "",
      errorMsg: "",
    };
  },
  components: { FontAwesomeIcon },
  methods: {
    TurnOnWritingMode() {
      this.writingMode = true;
    },
    TurnOffWritingMode() {
      this.writingMode = false;
    },
    chooseImg() {
      document.getElementById("fileBtn").click();
    },
    addImgByClick(e) {
      const files = e.target.files;
      this.formData = new FormData();
      this.formData.append("file", files[0]);

      try {
        if (files.length) {
          const file = files[0];
          const imgType = /image.*/;

          if (file.type.match(imgType)) {
            const reader = new FileReader();
            reader.onload = () => {
              this.imgSrc = reader.result;
            };
            reader.readAsDataURL(file);
          } else {
            throw "올바른 이미지 형식이 아닙니다";
          }
        }
      } catch (error) {
        console.log("error :>> ", error);
        this.imgErr = error;
        this.imgSrc = "";
      }
    },
    setInputFilter(e) {
      let inputText = e.target.value.replace(/[^0-9]/g, "");
      const result = inputText.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      e.target.value = result;
      e.target.id === "price" ? (this.price = result) : (this.stockQuantity = result);
    },
    checkRequired() {
      for (const item in this.formData) {
        try {
          if (!this.formData[item]) {
            throw "빈 칸을 모두 채워주세요";
          }
        } catch (error) {
          this.errorMsg = error;
        }
      }
    },
    validate() {
      this.checkRequired();
    },
    async onSubmit() {
      this.validate();

      const path = "/backend/cup/add";

      this.formData.append("price", this.price.replace(/[^0-9]/g, ""));
      this.formData.append("name", this.name);
      this.formData.append("stockQuantity", this.stockQuantity.replace(/[^0-9]/g, ""));

      let cupForm = axios.create();

      const config = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };

      await cupForm
        .post(path, this.formData, config)
        .then((res) => {
          this.errorMsg = "";
          console.log("res :>> ", res);
          this.$emit("modeSwitch");
        })
        .catch((err) => {
          this.errorMsg = err;
        });
    },
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, name)} {
    * {
      box-sizing: border-box;
    }

    .form-wrapper {
      width: 100%;
      display: flex;
      transition: all 0.3s ease;

      form {
        display: flex;
        padding: 1rem;
        width: 100%;

        .image-wrapper {
          width: 320px;
          height: fit-content;
          min-width: 320px;
          max-height: 420px;
          border-radius: 14px;
          background-color: map-get($map: $theme, $key: "background");
          cursor: pointer;
          display: flex;
          justify-content: center;
          align-items: center;
          overflow: hidden;
          margin: auto 2rem auto 0;

          &:hover {
            transition: all 0.3s ease;
            background-color: rgba($color: map-get($map: $theme, $key: "background"), $alpha: 0.5);
          }

          img {
            width: 100%;
            height: 100%;
            object-fit: cover;
          }

          .upload-img-icon {
            color: map-get($map: $theme, $key: "text-light");
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 420px;

            .upload-icon {
              font-size: 4rem;
              padding: 0.5rem;
            }
            .upload-text {
              font-size: 1rem;
            }
            .error-msg {
              color: $error-msg;
              transform: translateY(2rem);
              font-size: 0.8rem;
              height: 0.8rem;
              user-select: none;
            }
          }
        }

        .content-wrapper {
          width: 100%;
          padding: 1rem;

          .content-row {
            width: 100%;
            display: flex;
            flex-direction: column;
            margin: 1rem 0;

            .row-title {
              width: 100%;
              font-weight: bold;
              margin: 0.5rem;
            }

            .row-input {
              width: 100%;
              border-radius: 6px;
              border: 1px solid map-get($map: $theme, $key: "border");
              padding: 0.65rem 1rem;
              margin: 0.5rem;
              font-size: 1.2rem;
              background-color: map-get($map: $theme, $key: "background");
              color: map-get($map: $theme, $key: "text");

              &:focus {
                transition: all 0.3s ease;
                border: 1px solid $main-color;
                padding-left: 1.2rem;
                padding-right: 1.2rem;
                outline: none;
                box-shadow: $shadow-lighter;
              }
            }

            .input-wrapper p {
              padding: 0.65rem 0;
              margin: 0.5rem 0rem;
              user-select: none;
              font-size: 0.9rem;
              color: map-get($map: $theme, $key: "text-light");
              display: flex;
              justify-content: center;
              align-items: center;
            }

            button {
              width: 150px;
              padding: 1rem;
              margin: 2rem auto;
              cursor: pointer;
              border-radius: 6px;
              border: 1px solid map-get($map: $theme, $key: "border");
              color: map-get($map: $theme, $key: "text");
              background-color: map-get($map: $theme, $key: "border");
              box-shadow: $shadow-light;
              font-size: 1rem;

              &:hover,
              &:active {
                color: $white;
                background-color: $main-color;
                box-shadow: $shadow;
                transition: all 0.3s ease;
                transform: translateY(-2px);
              }
            }

            .error-msg {
              color: $error-msg;
              font-size: 0.8rem;
              text-align: center;
              height: 0.8rem;
              user-select: none;
            }
          }
        }
      }
    }
  }
}
</style>
