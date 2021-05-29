<template>
  <div class="toggle-btn">
    <input
      class="toggle-input"
      name="toggle-input"
      type="checkbox"
      @change="onChangeTheme"
      v-model="checked"
    />
    <span></span>
  </div>
</template>

<script>
export default {
  data() {
    return {
      theme: this.getTheme,
      checked: "",
    };
  },
  methods: {
    onChangeTheme() {
      const currentTheme = this.$store.state.theme;
      if (currentTheme === "theme-light") {
        this.$store.commit("SETSTYLE", "theme-dark");
        this.themeMode = this.$store.state.theme;
      } else if (currentTheme === "theme-dark") {
        this.$store.commit("SETSTYLE", "theme-light");
        this.themeMode = this.$store.state.theme;
      }
    },
    toggleSwitch() {
      if (this.theme === "theme-light") {
        console.log("this.checked :>> ", this.checked);
        this.checked = false;
      } else {
        this.checked = true;
      }
    },
  },
  computed: {
    getTheme() {
      return this.$store.state.theme;
    },
  },
  mounted() {
    this.theme = this.getTheme;
    this.toggleSwitch();
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");

    .toggle-btn {
      position: absolute;
      width: 50px;
      height: 24px;
      border-radius: 25px;
      overflow: hidden;
      opacity: 1;
      background-color: map-get($map: $theme, $key: "background");
      box-shadow: $shadow;
      transition: 0.2s ease background-color, 0.2s ease opacity;

      input[type="checkbox"] {
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        margin: 0;
        cursor: pointer;
        opacity: 0;
        z-index: 2;
      }

      input[type="checkbox"]:checked + span {
        background-color: map-get($map: $theme, $key: "background");
      }

      input[type="checkbox"]:active + span {
        opacity: 0.5;
        background-color: map-get($map: $theme, $key: "background");
      }

      input[type="checkbox"]:checked + span::before {
        background-color: map-get($map: $theme, $key: "background");
        transform: translate(20px, -1px);
      }

      input[type="checkbox"]:checked + span::after {
        background-color: $sub-color;
        transform: translate(29px, 0px);
      }

      span::before,
      span::after {
        content: "";
        position: absolute;
        top: 2px;
        left: -2px;
        width: 21px;
        height: 21px;
        border-radius: 50%;
        transition: 0.5s ease transform, 0.2s ease background-color;
      }

      span::before {
        background-color: map-get($map: $theme, $key: "background");
        transform: translate(-21px, 0px);
        z-index: 1;
      }

      span::after {
        background-color: $sub-color;
        transform: translate(4px, 0px);
        z-index: 0;
      }
    }
  }
}
</style>
