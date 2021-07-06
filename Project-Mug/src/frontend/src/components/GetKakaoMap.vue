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
    },
    onAddScript() {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.onLoadMap);
      script.src =
        "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6e95b4c59c1a9d01927e2946ff3a4c88";
      document.head.appendChild(script);
    },
  },
  mounted() {
    window.kakao && window.kakao.maps ? this.onLoadMap() : this.onAddScript();
  },
  created() {
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
