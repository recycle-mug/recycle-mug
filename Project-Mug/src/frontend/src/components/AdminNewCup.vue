<template>
  <div class="form-wrapper">
    <form>
      <div class="image-wrapper" @click="chooseImg">
        <input type="file" id="fileBtn" style="display:none" @change="addImgByClick" />
        <img :src="imgSrc" v-if="imgSrc.length > 0" />
        <div class="upload-img-icon" v-else>
          <div class="upload-icon">
            <font-awesome-icon :icon="['fas', 'upload']"></font-awesome-icon>
          </div>
          <span class="upload-text">Image Upload</span>
          <span class="error-msg">{{ errorMsg }}</span>
        </div>
      </div>
      <div class="content-wrapper">
        <div class="content-row">
          <div class="row-title">Name</div>
          <input type="text" class="row-input" placeholder="Cup Name" />
        </div>

        <div class="content-row" style="display:flex; flex-direction:row;margin:0">
          <div class="content-row" style="margin-right:1.5rem">
            <div class="row-title">가격</div>
            <div class="input-wrapper" style="display:flex">
              <input type="text" class="row-input" placeholder="2,000" style="text-align:right" />
              <p>원</p>
            </div>
          </div>

          <div class="content-row">
            <div class="row-title">수량</div>
            <div class="input-wrapper" style="display:flex">
              <input type="text" class="row-input" placeholder="2,000" style="text-align:right" />
              <p>개</p>
            </div>
          </div>
        </div>

        <div class="content-row">
          <button type="submit">컵 추가하기</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faUpload } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faUpload);

export default {
  data() {
    return {
      imgSrc: "",
      errorMsg: "",
    };
  },
  components: { FontAwesomeIcon },
  methods: {
    chooseImg() {
      document.getElementById("fileBtn").click();
    },
    addImgByClick(e) {
      const files = e.target.files;
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
            console.log("this.imgSrc :>> ", this.imgSrc);
          } else {
            throw "올바른 이미지 형식이 아닙니다";
          }
        } else {
          throw "파일을 찾을 수 없습니다";
        }
      } catch (error) {
        console.log("error :>> ", error);
        this.errorMsg = error;
        this.imgSrc = "";
      }
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
          }
        }
      }
    }
  }
}
</style>
