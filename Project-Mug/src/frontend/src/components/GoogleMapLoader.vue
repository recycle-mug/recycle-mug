<template>
  <div>
    <div class="google-map" ref="googleMap"></div>
    <template v-if="Boolean(this.google) && Boolean(this.map)">
      <slot :google="google" :map="map" />
    </template>
  </div>
</template>

<script>
import GoogleMapsApiLoader from "google-maps-api-loader";
import axios from "axios";

export default {
  props: {
    mapConfig: Object,
    apiKey: String,
  },

  data() {
    return {
      google: null,
      map: null,
      lat: 36.619,
      lng: 127.443,
      type: "Restaurant",
      radius: 10,
    };
  },

  async mounted() {
    const googleMapApi = await GoogleMapsApiLoader({
      apiKey: this.apiKey,
    });
    this.google = googleMapApi;
    this.initializeMap();

    const placesSearchURL = `/api/maps/api/place/findplacefromtext/json?input=Museum%20of%20Contemporary%20Art%20Australia&inputtype=textquery&fields=photos,formatted_address,name,rating,opening_hours,geometry&key=${this.apiKey}`;
    axios.get(placesSearchURL).then((res) => {
      console.log("search :>> ", res);
    });

    const placesDetailURL = `/api/maps/api/place/details/json?ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name,rating,formatted_phone_number&key=${this.apiKey}`;

    axios.get(placesDetailURL).then((res) => {
      console.log("detail :>> ", res);
    });
  },

  methods: {
    initializeMap() {
      const mapContainer = this.$refs.googleMap;
      this.map = new this.google.maps.Map(mapContainer, this.mapConfig);

      // let infoWindow = new this.google.maps.InfoWindow({
      //   content: "Click",
      //   position: { lat: 36.619, lng: 127.443 },
      // });

      // infoWindow.open(this.map);
      // this.map.addListener("click", (mapsMouseEvent) => {
      //   infoWindow.close();
      //   infoWindow = new this.google.maps.InfoWindow({
      //     position: mapsMouseEvent.latLng,
      //   });

      //   infoWindow.setContent(JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2));
      //   console.log(JSON.stringify(mapsMouseEvent.latLng.toJSON()));

      //   infoWindow.open(this.map);
      // });
    },
  },
};
</script>

<style lang="scss" scoped>
.google-map {
  width: 100%;
  min-height: 100%;
}
</style>
