<template>
  <div :class="getTheme" style="width:100%;height:100%;box-sizing:border-box">
    <div class="container">
      <form id="form">
        <div class="input-form">
          <input type="text" v-model="query" ref="input" />
          <button @click.prevent="searchApi">주소 검색</button>
        </div>
        <div class="input-form-error">{{ queryError }}</div>
        <div
          class="address-card"
          v-for="searchResult in searchData"
          :key="searchResult.index"
          @click="setData(searchResult)"
        >
          <div class="card-container">
            <!-- <div class="card-content">
              <div class="address-name">
                <div class="new-address" v-if="searchResult.road_address">
                  {{ searchResult.road_address.address_name }}
                </div>
                <div class="old-address" v-if="searchResult.address">
                  {{ searchResult.address.address_name }}
                </div>
              </div>
              <div class="address-num" v-if="searchResult.road_address">
                {{ searchResult.road_address.zone_no }}
              </div>
            </div> -->
            <div class="card-content">
              <div class="address-name">
                <div class="new-address" v-if="searchResult.place_name">
                  {{ searchResult.place_name }}
                </div>
                <div class="old-address" v-if="searchResult.road_address_name">
                  {{ searchResult.road_address_name }}
                </div>
                <div class="old-address" v-if="searchResult.address_name">
                  {{ searchResult.address_name }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      query: "",
      queryError: "",
      searchData: [],
      addressRoad: "",
      addressNum: "",
    };
  },
  methods: {
    async searchApi() {
      const apiKey = "210e6ee868e2837c5e6d0805cc037348";
      // const url = "https://dapi.kakao.com/v2/local/search/address.json";
      const url = "https://dapi.kakao.com/v2/local/search/keyword.json";

      let kakaoApi = axios.create();

      kakaoApi.defaults.headers.common["Authorization"] = `KakaoAK ${apiKey}`;
      kakaoApi.defaults.headers.common["Content-Type"] = "application/x-www-form-urlencoded";
      kakaoApi.defaults.params = { query: this.query, category_group_code: "CE7" };

      await kakaoApi
        .get(url)
        .then((res) => {
          this.searchData = res.data.documents;
          console.log("this.searchData :>> ", this.searchData);
          // searchData[0]: {
          // address_name:"충북 청주시 흥덕구 가경동 1416-2"
          // category_group_code:"CE7"
          // category_group_name:"카페"
          // category_name:"음식점 > 카페 > 커피전문점 > 스타벅스"
          // distance : ""
          // id:"17127076"
          // phone: "1522-3232"
          // place_name:"스타벅스 청주터미널점"
          // place_url:"http://place.map.kakao.com/17127076"
          // road_address_name:"충북 청주시 흥덕구 풍산로 15"
          // x:"127.432432911263"
          // y:"36.6263666883092"
          // }
        })
        .catch((apierr) => {
          console.log("apierr :>> ", apierr);
        });
    },
    setData(e) {
      console.log("e :>> ", e);
      this.addressRoad = e.road_address_name;
      this.addressNum = e.phone;

      let location = window.opener.document.getElementById("location");
      location.value = this.addressRoad;
      location.dispatchEvent(new Event("input"));

      let location_num = window.opener.document.getElementById("location_num");
      location_num.value = this.addressNum;
      location_num.dispatchEvent(new Event("input"));

      window.close();
    },
    inputFocus() {
      this.$refs.input.focus();
      console.log("this.$refs.input :>> ", this.$refs.input);
    },
  },
  computed: {
    getTheme() {
      return this.$store.state.theme;
    },
  },
  mounted() {
    this.inputFocus();
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");

    .container {
      padding: 2rem;
      height: 100%;
      width: 100%;
      box-sizing: border-box;
      overflow-y: auto;

      form {
        width: 100%;
        height: 100%;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        align-items: center;

        .input-form {
          display: flex;
          box-sizing: border-box;
          margin: 1rem;
          width: 100%;

          input {
            background-color: map-get($map: $theme, $key: "content-background");
            color: map-get($map: $theme, $key: "text");
            border: none;
            padding: 1rem;
            width: 100%;
            box-sizing: border-box;
            box-shadow: $shadow-lighter;
            outline-color: $sub-color;
            margin: 0.3rem;

            &:focus {
              outline-color: $sub-color;
              box-shadow: $shadow;
              transition: all 0.2s ease-in-out;
              border: none;
            }
          }
          button {
            background-color: transparent;
            color: $main-color;
            border-radius: 20px;
            font-weight: bold;
            border: 1px solid $main-color;
            padding: 0.5rem 1rem;
            cursor: pointer;
            width: 20%;
            box-shadow: $shadow-lighter;
            margin-left: 1rem;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
            line-height: 1rem;
            height: 4rem;

            &:hover {
              box-shadow: $shadow;
              background-color: $main-color;
              border: $white;
              color: $white;
              transition: all 0.2s ease-in-out;
            }
          }
        }

        .input-form-error {
          color: $error-msg;
          font-size: 0.8rem;
          user-select: none;
          margin-bottom: 1rem;
          letter-spacing: 0.12rem;
        }

        .address-card {
          box-sizing: border-box;
          background-color: map-get($map: $theme, $key: "content-background");
          margin: 0.5rem;
          width: 97%;
          border-radius: 1rem;
          overflow: hidden;
          min-height: 6rem;
          cursor: pointer;
          box-shadow: $shadow-lighter;

          &:hover {
            box-shadow: $shadow;
            transition: all 0.2s ease-in;
            width: 100%;
          }

          .card-container {
            border-left: 0.5rem solid $main-color;
            height: 100%;
            padding: 1rem 0.5rem;
            box-sizing: border-box;
            user-select: none;

            .card-content {
              box-sizing: border-box;
              padding-left: 1rem;
              display: flex;
              justify-content: space-between;
              height: 100%;

              .address-name {
                box-sizing: border-box;
                display: flex;
                flex-direction: column;
                justify-content: center;

                .new-address {
                  box-sizing: border-box;
                  margin: 0.5rem 0;
                  font-weight: bold;
                  font-size: 1.1rem;
                  color: map-get($map: $theme, $key: "text");
                }

                .old-address {
                  box-sizing: border-box;
                  margin: 0.5rem 0;
                  font-weight: normal;
                  font-size: 1rem;
                  color: map-get($map: $theme, $key: "text-light");
                }
              }

              .address-num {
                padding-right: 1rem;
                display: flex;
                justify-content: center;
                align-items: center;
              }
            }
          }
        }
      }
    }
  }
}
</style>
