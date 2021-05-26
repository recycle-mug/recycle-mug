<template>
  <GoogleMapLoader
    class="map-container"
    :mapConfig="mapConfig"
    apiKey="AIzaSyAnUJ1BD9yCOYF0i_Qt4ctxXRAg32xrShI"
  >
    <!-- // insert your google maps api key to render styled map -->
    <!-- <template slot-scope="{ google, map }">
      <GoogleMapMarker
        v-for="marker in markers"
        :key="marker.id"
        :marker="marker"
        :google="google"
        :map="map"
      />
    </template> -->
  </GoogleMapLoader>
</template>

<script>
import GoogleMapLoader from "./GoogleMapLoader";

import { mapSettings } from "../constants/mapSettings";

export default {
  components: {
    GoogleMapLoader,
  },

  data() {
    return {
      markers: [
        {
          id: "a",
          position: { lat: 36.619, lng: 127.443 },
        },
        {
          id: "b",
          position: { lat: 36.626, lng: 127.457 },
        },
      ],
    };
  },

  computed: {
    mapConfig() {
      return {
        ...mapSettings,
        center: this.markers[1].position,
      };
    },
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");

    .map-container {
      margin: 0.5rem;
      box-sizing: border-box;
      box-shadow: $shadow-lighter;
      border-radius: 30px;
      overflow: hidden;
      transition: all 0.3s ease-in-out;

      &:hover {
        box-shadow: $shadow;
        transition: all 0.3s ease-in-out;
      }
    }
  }
}
</style>
