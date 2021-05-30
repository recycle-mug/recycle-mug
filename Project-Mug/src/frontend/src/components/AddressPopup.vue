<template>
  <div :class="getTheme" style="width:100%;height:100%;box-sizing:border-box">
    <div class="container">
      <form id="form">
        <div class="input-form">
          <input type="text" v-model="query" ref="input" />
          <button @click.prevent="queryCheck">주소 검색</button>
        </div>
        <div class="input-form-error">{{ queryError }}</div>
        <div
          class="address-card"
          v-for="searchResult in searchData"
          :key="searchResult.index"
          @click="setData(searchResult)"
        >
          <div class="card-container">
            <div class="card-content">
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
      address: "",
      addressNum: "",
      addressDetail: "",
    };
  },
  methods: {
    async searchApi() {
      const apiKey = "210e6ee868e2837c5e6d0805cc037348";
      // const url = "https://dapi.kakao.com/v2/local/search/address.json";
      const url = "https://dapi.kakao.com/v2/local/search/address.json";

      const params = new URLSearchParams();
      params.append("query", this.query);

      const config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          Accept: "application/json",
          Authorization: `KakaoAK ${apiKey}`,
        },
      };

      let kakaoApi = axios.create({
        baseURL: url,
      });

      kakaoApi.defaults.headers.common["Authorization"] = `KakaoAK ${apiKey}`;
      kakaoApi.defaults.headers.common["Content-Type"] = "application/x-www-form-urlencoded";
      kakaoApi.defaults.params = { page: 5, size: 30, query: this.query };

      await kakaoApi
        .get(url, params, config)
        .then((res) => {
          this.searchData = res.data.documents;
          console.log("this.searchData :>> ", this.searchData);
        })
        .catch((apierr) => {
          console.log("apierr :>> ", apierr);
        });
    },
    queryCheck() {
      const queryPattern = /^(([가-힣a-zA-Z0-9 ]+)\s{0,}([0-9-]+))$/;

      if (queryPattern.test(this.query)) {
        this.queryError = "";
        this.searchApi();
      } else {
        this.queryError = "OO동/OO로/OO길+(공백)+숫자로 입력해주세요";
        this.searchData = [];
      }
    },
    setData(e) {
      console.log("e :>> ", e);
      this.address = e.road_address.address_name;
      window.opener.document.getElementById("location").value = this.address;
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
          font-size: 0.5rem;
          user-select: none;
          margin-bottom: 1rem;
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
              }
            }
          }
        }
      }
    }
  }
}
</style>
