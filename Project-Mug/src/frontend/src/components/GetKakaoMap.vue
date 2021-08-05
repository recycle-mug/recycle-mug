<template>
  <div class="container">
    <div v-if="locationErr">위치 정보를 찾을 수 없습니다.</div>
    <div v-if="gettingLocation">위치 정보 조회 중...</div>

    <div id="map" style="width:500px;height:500px;" class="map-container"></div>
    <div class="place-description" v-if="clicked">
      <div class="businessName" v-if="detailInfo.businessName">
        {{ detailInfo.businessName }}
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
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
    getPartnerInfo(partnerId, map, marker) {
      const path = `/backend/partner/${partnerId}`;

      let partnerInfo = axios.create();
      partnerInfo
        .get(path)
        .then((res) => {
          this.createCustomOverlay(res.data, map, marker);
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
    },
    createCustomOverlay(data, map, marker) {
      const iwContent = document.createElement("div");
      iwContent.className = "map-marker";
      iwContent.addEventListener("mouseleave", () => {
        overlay.setMap(null);
      });

      const contentTitle = document.createElement("div");
      contentTitle.className = "title";
      contentTitle.appendChild(document.createTextNode(data.businessName));
      contentTitle.onclick = () => {
        this.clicked = true;
        this.detailInfo = data;
      };

      const contentCloseBtn = document.createElement("div");
      contentCloseBtn.className = "close";
      contentCloseBtn.onclick = () => {
        overlay.setMap(null);
      };

      contentTitle.appendChild(contentCloseBtn);

      iwContent.appendChild(contentTitle);

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
          console.log("res.data :>> ", res.data);
          this.partnerLocations = res.data;
          this.onLoadMap();
        })
        .catch((err) => {
          console.log("err :>> ", err);
        });
    },
  },
  mounted() {
    // window.kakao && window.kakao.maps ? this.getPartners() : this.onAddScript();
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

        &:hover {
          box-shadow: $shadow;
          transition: all 0.3s ease-in-out;
        }
      }

      .place-description {
        margin: 0.5rem;
        box-sizing: border-box;
        box-shadow: $shadow-lighter;
        border-radius: 30px;
        overflow: hidden;
        transition: all 0.3s ease-in-out;
        width: 100%;
        max-width: 500px;
        background-color: map-get($map: $theme, $key: "content-background");

        &:hover {
          box-shadow: $shadow;
          transition: all 0.3s ease-in-out;
        }
      }
    }
  }
}
</style>
