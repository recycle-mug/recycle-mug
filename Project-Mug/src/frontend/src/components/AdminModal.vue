<template>
  <div>THIS IS ADMIN MODAL</div>
</template>

<script>
export default {
  data() {
    return {};
  },
  props: ["cupId"],
  methods: {
    getCupInfo() {},
  },
  mounted() {
    this.getCupInfo();
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .modal-wrapper {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
