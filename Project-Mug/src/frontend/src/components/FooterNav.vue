<template>
  <footer class="footer">
    <div class="text-wrapper">
      <span class="footer-text">copyright © 2021</span>
      <span class="footer-text">| support@wecup.com |</span>
    </div>
  </footer>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    margin: 0;
    user-select: none;

    .footer {
      padding: 0.8rem 2.3rem;
      min-height: 8rem;
      box-sizing: border-box;
      width: 100%;
      margin: auto;
      box-sizing: border-box;
      margin-top: 8rem;
      position: absolute;
      bottom: 0;
      background-color: map-get($map: $theme, $key: "content-background");
      color: map-get($map: $theme, $key: "text-light");
      font-size: 0.8rem;
      user-select: none;

      .text-wrapper {
        max-width: 1140px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        margin: auto;

        .footer-text {
          text-align: center;
          box-sizing: border-box;
          margin: 0.2rem;
        }
      }
    }
  }
}
</style>
