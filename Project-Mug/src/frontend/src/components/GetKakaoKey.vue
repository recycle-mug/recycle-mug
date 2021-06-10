<template>
  <div>{{ this.authCode }}</div>
</template>

<script>
export default {
  props: ["code"],
  data() {
    return {
      authCode: this.code,
    };
  },
  methods: {
    getParams() {
      this.authCode = this.$route.query.code;
    },
  },
  mounted() {
    this.getParams();
  },
};
</script>

<style lang="scss" scoped></style>
