<template>
  <div class="container">
    <div v-if="locationErr">위치 정보를 찾을 수 없습니다.</div>
    <div v-if="gettingLocation">위치 정보 조회 중...</div>

    <div id="map" style="width:500px;height:500px;" class="map-container"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      location: null,
      gettingLocation: false,
      locationErr: null,
      partnerLocations: [
        {
          name: "현지",
          lat: 36.61938880589755,
          lng: 127.44334824252485,
        },
        {
          name: "재서",
          lat: 36.612523961538564,
          lng: 127.5172328005922,
        },
      ],
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
    },
    addMarker(map) {
      this.partnerLocations.forEach((partnerLocation, index) => {
        const lat = partnerLocation.lat;
        const lng = partnerLocation.lng;

        let markerPosition = new kakao.maps.LatLng(lat, lng);
        const marker = new kakao.maps.Marker({
          position: markerPosition,
          clickable: true,
          title: partnerLocation.name,
        });

        marker.setMap(map);

        this.onHoverMarker(map, marker);
      });
    },
    onHoverMarker(map, marker) {
      console.log("marker :>> ", marker);
      const iwContent = '<div style="padding:1rem;user-select:none">' + marker.Fb + "</div>";

      const infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
      });

      kakao.maps.event.addListener(marker, "mouseover", function() {
        infowindow.open(map, marker);
      });

      kakao.maps.event.addListener(marker, "mouseout", function() {
        infowindow.close();
      });
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
  },
  mounted() {
    window.kakao && window.kakao.maps ? this.onLoadMap() : this.onAddScript();
  },
  created() {
    this.getCurrentLocation();
  },
};
</script>

<style lang="scss" scoped>
.container {
  max-width: 1140px;
  margin: auto;

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
}
</style>
