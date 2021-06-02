<template>
  <div id="map" style="width:500px;height:500px;" class="map-container"></div>
</template>

<script>
export default {
  methods: {
    onLoadMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      const map = new kakao.maps.Map(container, options);

      console.log("map :>> ", map);
    },
    onAddScript() {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.onLoadMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6e95b4c59c1a9d01927e2946ff3a4c88";
      document.head.appendChild(script);

      console.log("script :>> ", script);
    },
  },
  mounted() {
    window.kakao && window.kakao.maps ? this.onLoadMap() : this.onAddScript();
  },
};
</script>

<style lang="scss" scoped>
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
</style>
