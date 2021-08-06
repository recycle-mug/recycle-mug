<template>
  <div class="container">
    <div v-if="locationErr">위치 정보를 찾을 수 없습니다.</div>
    <div v-if="gettingLocation">위치 정보 조회 중...</div>

    <div id="map" style="width:500px;height:500px;" class="map-container"></div>
    <div class="place-description" v-if="clicked">
      <div class="close-btn" @click="onCloseDetail">
        <font-awesome-icon
          :icon="['fas', 'times']"
          style="width:100%; cursor:pointer;"
        ></font-awesome-icon>
      </div>
      <div class="image-content" v-if="detailInfo.profilePicture">
        <img :src="'data:image/jpeg;base64,' + detailInfo.profilePicture" alt="" />
      </div>
      <div class="content-body">
        <div class="title">
          <div class="business-name" v-if="detailInfo.businessName">
            {{ detailInfo.businessName }}
          </div>
          <div class="address" v-if="detailInfo.address">
            {{ detailInfo.address }}
          </div>
        </div>
        <div class="partner-cups-wrapper">
          <carousel
            :mouse-drag="true"
            :auto-play="false"
            class="partner-cups"
            :paginationEnabled="false"
            :perPageCustom="[]"
          >
            <slide class="partner-cup" v-for="(cup, index) in detailInfo.partnerCups" :key="index">
              <div class="item-wrapper">
                <div class="item-title">{{ cup.name }}</div>
                <div class="item-image">
                  <img :src="'data:image/jpeg;base64,' + cup.image" alt="" />
                </div>
                <div class="item-description">
                  <div class="description-row">
                    <p>가격</p>
                    <div class="row-num">
                      <span>{{ addComma(cup.price) }}</span>
                      <span class="unit">(원)</span>
                    </div>
                  </div>
                  <div class="description-row">
                    <p>수량</p>
                    <div class="row-num">
                      <span>{{ addComma(cup.stockQuantity) }}</span>
                      <span class="unit">(개)</span>
                    </div>
                  </div>
                  <div class="btn-row">
                    <button>주문하기</button>
                  </div>
                </div>
              </div>
            </slide>
          </carousel>
        </div>
      </div>
    </div>
    <div class="place-list" v-else>
      <div
        class="address-card"
        v-for="partner in partnerLocations"
        :key="partner.index"
        @click="getPartnerInfoByClick(partner.userId)"
      >
        <div class="card-container">
          <div class="card-content">
            <div class="partner">
              <div class="business-name" v-if="partner.businessName">
                {{ partner.businessName }}
              </div>
              <div class="address" v-if="partner.address">
                {{ partner.address }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

import { Carousel, Slide } from "vue-carousel";

import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";

faLibrary.add(faTimes);

export default {
  data() {
    return {
      location: null,
      gettingLocation: false,
      locationErr: null,
      partnerLocations: [], // address, businessName, latitude, longitude, phoneNumber, userId
      detailInfo: null,
      clicked: false,
    };
  },
  components: { Carousel, Slide, FontAwesomeIcon },
  methods: {
    onLoadMap() {
      const container = document.getElementById("map");
      let options;
      if (this.location) {
        options = {
          center: new kakao.maps.LatLng(
            this.location.coords.latitude,
            this.location.coords.longitude,
          ),
          level: 3,
        };
      } else {
        options = {
          center: new kakao.maps.LatLng(33.450701, 126.570667),
          level: 3,
        };
      }

      const map = new kakao.maps.Map(container, options);

      this.addMarker(map);
    },
    onAddScript() {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.onLoadMap);
      script.src =
        "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6e95b4c59c1a9d01927e2946ff3a4c88";
      document.head.appendChild(script);

      this.getPartners();
    },
    addMarker(map) {
      this.partnerLocations.forEach((partnerLocation, index) => {
        const lat = partnerLocation.latitude;
        const lng = partnerLocation.longitude;

        let markerPosition = new kakao.maps.LatLng(lat, lng);
        const marker = new kakao.maps.Marker({
          position: markerPosition,
          clickable: true,
          title: partnerLocation.userId,
        });

        marker.setMap(map);

        kakao.maps.event.addListener(marker, "mouseover", () => {
          this.getPartnerInfo(marker.Fb, map, marker);
        });
      });
    },
    getPartnerInfoByClick(partnerId) {
      const path = `/backend/partner/map/${partnerId}`;

      let partnerInfo = axios.create();
      partnerInfo
        .get(path)
        .then((res) => {
          this.onClickPartner(res.data.mapPartnerInfoDto);
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
    },
    getPartnerInfo(partnerId, map, marker) {
      const path = `/backend/partner/map/${partnerId}`;

      let partnerInfo = axios.create();
      partnerInfo
        .get(path)
        .then((res) => {
          this.createCustomOverlay(res.data.mapPartnerInfoDto, map, marker);
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
    },
    onCloseDetail() {
      this.clicked = false;
      this.detailInfo = null;
    },
    onClickPartner(partner) {
      this.clicked = true;
      this.detailInfo = partner;
      console.log("partner :>> ", partner);
    },
    createCustomOverlay(data, map, marker) {
      const iwContent = document.createElement("div");
      iwContent.className = "map-marker";
      iwContent.addEventListener("mouseleave", () => {
        overlay.setMap(null);
      });

      iwContent.addEventListener("click", () => {
        this.onClickPartner(data);
      });

      const contentImage = document.createElement("div");
      contentImage.className = "image-wrapper";

      const image = document.createElement("img");
      image.src = "data:image/jpeg;base64," + data.profilePicture;
      contentImage.appendChild(image);

      const contentBody = document.createElement("div");
      contentBody.className = "content";

      const contentTitle = document.createElement("div");
      contentTitle.className = "title";
      const contentTitleText = document.createElement("span");
      contentTitleText.className = "title-content";
      contentTitleText.appendChild(document.createTextNode(data.businessName));
      contentTitle.appendChild(contentTitleText);
      contentTitle.onclick = () => {
        this.onClickPartner(data);
      };

      const contentCloseBtn = document.createElement("div");
      contentCloseBtn.className = "close-btn-container";
      contentCloseBtn.onclick = () => {
        overlay.setMap(null);
      };
      const closeBtn = document.createElement("div");
      closeBtn.className = "close-icon";
      closeBtn.appendChild(document.createElement("span"));
      closeBtn.appendChild(document.createElement("span"));
      closeBtn.appendChild(document.createElement("span"));
      contentCloseBtn.appendChild(closeBtn);

      contentBody.appendChild(contentTitle);

      iwContent.appendChild(contentCloseBtn);
      iwContent.appendChild(contentImage);
      iwContent.appendChild(contentBody);

      const overlay = new kakao.maps.CustomOverlay({
        content: iwContent,
        position: marker.getPosition(),
        xAnchor: 0.5,
        yAnchor: 1,
      });

      overlay.setMap(map);
    },
    getCurrentLocation() {
      if (!("geolocation" in navigator)) {
        this.locationErr = "위치 정보 권한이 없습니다";
        return;
      }

      this.gettingLocation = true;
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          this.gettingLocation = false;
          this.location = pos;
          this.onLoadMap();
        },
        (err) => {
          this.gettingLocation = false;
          this.locationErr = err.message;
        },
      );
    },
    getPartners() {
      const path = "/backend/user/normal/find-all-partners";

      let getPartnersLocation = axios.create();
      getPartnersLocation
        .get(path)
        .then((res) => {
          this.partnerLocations = res.data;
          this.onLoadMap();
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
  },
  mounted() {
    this.onAddScript();
  },
  created() {
    this.getCurrentLocation();
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .container {
      max-width: 1140px;
      margin: auto;
      display: flex;

      .map-container {
        margin: 0.5rem;
        box-sizing: border-box;
        box-shadow: $shadow-lighter;
        border-radius: 30px;
        overflow: hidden;
        transition: all 0.3s ease-in-out;
        width: 100%;
        height: 100%;
        border: 1px solid map-get($map: $theme, $key: "border");

        &:hover {
          box-shadow: $shadow;
          transition: all 0.3s ease-in-out;
        }
      }

      .place-description {
        margin: 0.5rem;
        box-sizing: border-box;
        box-shadow: $shadow-lighter;
        border-radius: 12px;
        transition: all 0.3s ease-in-out;
        width: 100%;
        max-width: 500px;
        height: 500px;
        background-color: map-get($map: $theme, $key: "content-background");
        border: 1px solid map-get($map: $theme, $key: "border");
        position: relative;
        overflow-y: overlay;

        &:hover {
          box-shadow: $shadow;
          transition: all 0.3s ease-in-out;
        }

        &::-webkit-scrollbar {
          width: 5px;
        }

        &::-webkit-scrollbar-thumb {
          background-color: rgba($color: map-get($theme, "text"), $alpha: 0.5);
          border-radius: 6px;
          transition: all 0.2s ease;

          &:hover,
          &:active {
            background-color: rgba($color: map-get($theme, "text"), $alpha: 1);
          }
        }

        .close-btn {
          position: absolute;
          top: 10px;
          right: 10px;
          width: 30px;
          height: 30px;
          font-size: 20px;
          color: $black;
          z-index: 10;
          display: flex;
          justify-content: center;
          align-items: center;
          border-radius: 6px;
          background-color: rgba(white, 0.2);
          transition: background-color 0.2s ease;
          cursor: pointer;

          &:hover,
          &:active {
            background-color: rgba(white, 0.7);
            color: black;
          }
        }

        .image-content {
          width: 100%;
          height: 150px;
          display: flex;
          justify-content: center;
          align-items: center;
          position: relative;
          overflow: hidden;
          transition: all 0.2s linear;

          &:hover {
            height: 250px;
          }

          img {
            width: 100%;
            object-fit: cover;
          }
        }

        .content-body {
          padding: 1rem;
          display: flex;
          flex-direction: column;
          position: relative;
          background-color: map-get($map: $theme, $key: "content-background");
          top: -20px;
          border-radius: 12px 12px 0 0;

          .title {
            padding-bottom: 1rem;
            padding-top: 0.5rem;
            border-bottom: 2px solid map-get($map: $theme, $key: "border");
            margin-bottom: 1rem;

            .business-name {
              font-size: 1.2rem;
              font-weight: bolder;
              color: map-get($map: $theme, $key: "text");
              margin-bottom: 10px;
              white-space: nowrap;
              width: 100%;
              overflow: hidden;
              text-overflow: ellipsis;
              line-height: 1.5rem;
            }

            .address {
              font-size: 0.9rem;
              font-weight: bolder;
              color: map-get($map: $theme, $key: "text-muted");
              white-space: nowrap;
              width: 100%;
              overflow: hidden;
              text-overflow: ellipsis;
              line-height: 1rem;
            }
          }

          .partner-cups-wrapper {
            .partner-cups {
              .partner-cup {
                .item-wrapper {
                  position: relative;
                  display: flex;
                  flex-direction: column;
                  width: 180px;
                  height: 240px;
                  border-radius: 14px;
                  background-color: map-get($map: $theme, $key: "background");
                  padding: 18px;
                  cursor: pointer;
                  margin-right: 1rem;

                  &:hover {
                    transition: all 0.2s ease;
                    transform: scale(0.95);
                    box-shadow: $shadow;
                  }

                  .item-title {
                    font-size: 1.2rem;
                    font-weight: bold;
                  }

                  .item-image {
                    width: 100%;
                    height: 150px;
                    margin: 15px 0;
                    border-radius: 6px;
                    overflow: hidden;

                    img {
                      object-fit: contain;
                      width: 100%;
                      height: 100%;
                    }
                  }

                  .item-description {
                    display: flex;
                    flex-direction: column;
                    width: 100%;

                    .description-row {
                      width: 100%;
                      display: flex;
                      justify-content: space-between;
                      letter-spacing: 0.15rem;
                      color: map-get($map: $theme, $key: "text-light");
                      margin: 5px 0;
                      font-size: 0.9rem;

                      .row-num {
                        span {
                          color: map-get($map: $theme, $key: "text");
                          font-weight: bold;
                        }

                        .unit {
                          font-size: 0.7rem;
                          font-weight: lighter;
                          letter-spacing: 0;
                          color: map-get($map: $theme, $key: "text-light");
                        }
                      }
                    }

                    .btn-row {
                      display: flex;
                      justify-content: center;
                      align-items: center;
                      margin-top: 0.5rem;

                      button {
                        cursor: pointer;
                        background-color: $main-color;
                        border-radius: 6px;
                        border: none;
                        padding: 0.3rem 0.5rem;
                        color: $white;
                        font-size: 0.85rem;
                        transition: all 0.2s ease;

                        &:active,
                        &:hover {
                          transform: scale(1.1);
                        }

                        &:active {
                          background-color: darken($main-color, 10%);
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

      .place-list {
        margin: 0.5rem;
        box-sizing: border-box;
        border-radius: 12px;
        overflow-y: overlay;
        transition: all 0.3s ease-in-out;
        width: 100%;
        max-width: 500px;
        height: 500px;

        &::-webkit-scrollbar {
          width: 5px;
        }

        &::-webkit-scrollbar-thumb {
          background-color: rgba($color: map-get($theme, "text"), $alpha: 0.5);
          border-radius: 6px;
          transition: all 0.2s ease;

          &:hover,
          &:active {
            background-color: rgba($color: map-get($theme, "text"), $alpha: 1);
          }
        }

        .address-card {
          box-sizing: border-box;
          background-color: rgba(map-get($map: $theme, $key: "content-background"), 0.8);
          border: 1px solid map-get($map: $theme, $key: "border");
          margin: 0.5rem;
          width: 95%;
          border-radius: 1rem;
          overflow: hidden;
          min-height: 6rem;
          cursor: pointer;
          box-shadow: $shadow-lighter;

          &:hover {
            box-shadow: $shadow;
            transition: all 0.2s ease-in;
            background-color: rgba(map-get($map: $theme, $key: "content-background"), 1);
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

              .partner {
                box-sizing: border-box;
                display: flex;
                flex-direction: column;
                justify-content: center;

                .business-name {
                  box-sizing: border-box;
                  margin: 0.5rem 0;
                  font-weight: bold;
                  font-size: 1.1rem;
                  color: map-get($map: $theme, $key: "text");
                }

                .address {
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
