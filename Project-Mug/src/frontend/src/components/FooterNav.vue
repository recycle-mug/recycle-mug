<template>
  <footer class="footer-wrapper">
    <div class="footer">
      <div class="text-wrapper">
        <span class="footer-text">copyright © 2021</span>
        <span class="footer-text">| support@wecup.com |</span>
      </div>
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

    .footer-wrapper {
      padding-top: 8rem;
      position: relative;
      background-color: map-get($map: $theme, $key: "background");
      box-sizing: border-box;
      width: 100%;
      margin: auto;
      user-select: none;
      color: map-get($map: $theme, $key: "text-light");
      font-size: 0.8rem;
      display: flex;
      flex-direction: column;
      justify-content: flex-end;

      .footer {
        box-sizing: border-box;
        background-color: map-get($map: $theme, $key: "content-background");
        padding: 0.8rem 2.3rem;
        min-height: 8rem;

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
}
</style>
