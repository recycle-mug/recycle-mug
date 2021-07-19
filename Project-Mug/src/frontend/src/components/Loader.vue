<template>
  <div class="loader-wrapper">
    <div class="loader">
      <span class="dot"></span>
      <div class="dots">
        <span></span>
        <span></span>
        <span></span>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
.loader-wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

  .loader {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 142px;
    height: 40px;
    margin: -20px 0 0 -71px;
    background: white;
    filter: contrast(20);

    .dot {
      position: absolute;
      width: 16px;
      height: 16px;
      top: 12px;
      left: 15px;
      filter: blur(4px);
      background: #000;
      border-radius: 50%;
      transform: translateX(0);
      animation: dot 2.8s infinite;
    }

    .dots {
      transform: translateX(0);
      margin-top: 12px;
      margin-left: 31px;
      animation: dots 2.8s infinite;

      span {
        display: block;
        float: left;
        width: 16px;
        height: 16px;
        margin-left: 10px;
        filter: blur(4px);
        background: #000;
        border-radius: 50%;
      }
    }

    @keyframes dot {
      50% {
        transform: translateX(96px);
      }
    }
    @keyframes dots {
      50% {
        transform: translateX(-31px);
      }
    }
  }
}
</style>
