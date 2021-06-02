<template>
  <div style="width:100%">
    <form>
      <input type="text" id="location" placeholder="주소" readonly @click.prevent="goPopup" />
      <button @click.prevent="goPopup">주소검색</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      popup: null,
      loading: false,
      post: null,
      error: null,
    };
  },

  methods: {
    goPopup() {
      const routeData = this.$router.resolve({ name: "address" });
      this.popup = window.open(
        routeData.href,
        "pop",
        "width=570, height=420,scrollbars=yes,resizable=yes",
      );
    },
  },
  created() {},
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    form {
      width: 100%;
      display: flex;
      input {
        background-color: map-get($map: $theme, $key: "background");
        color: map-get($map: $theme, $key: "text");
        border: none;
        padding: 1rem 1.4rem;
        width: 100%;
        margin-right: 1rem;
        cursor: pointer;

        &:focus {
          outline-color: $sub-color;
          border: none;
        }
      }

      button {
        border: 1px solid $main-color;
        border-radius: 10px;
        background-color: $main-color;
        color: $white;
        cursor: pointer;

        &:hover {
          transform: scale(1.05);
          transition: all 0.2s ease-in-out;
        }
      }
    }
  }
}
</style>
