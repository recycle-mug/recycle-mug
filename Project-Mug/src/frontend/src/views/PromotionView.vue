<template>
  <div>
    <header>
      <div class="container">
        <div class="title_wrapper">
          <h1>We Cup</h1>
        </div>
      </div>
    </header>

    <section class="section section-1">
      <div class="promotion-title">
        <p>
          일회용컵을 대체할<br />
          <span class="emphasize">우리</span>의 다회용<span class="emphasize">컵</span>,
          <span class="emphasize">위컵</span>입니다.
        </p>
      </div>
    </section>

    <section class="section section-2">
      <div class="section-container">
        <img src="../assets/images/promotion-image-2.png" alt="" />
        <div class="promotion-content">
          <img src="../assets/images/promotion-image-3.png" alt="" />
          <p>
            길거리에 쌓인 일회용컵 쓰레기,<br />
            한 번 쓰이고 버려지는 플라스틱 테이크아웃 잔.<br />
            '이래도 계속 써야 하나?'<br />
            한번쯤은 고민해본 적 없으신가요?
          </p>
          <p>
            위컵(WECUP)은 다회용컵<br />
            공유 서비스를 통해<br />
            깨끗한 바다와 숲,<br />
            그리고 그 안에서 살아갈<br />
            다음 세대를 꿈꿉니다.<br />
            일회용컵에서 자유로운<br />
            세상을 위한 우리의 여정에<br />
            동참해 주세요.
          </p>
        </div>
      </div>
    </section>

    <footer>
      <router-link :to="{ name: 'login' }" tag="button">로그인하러 가기</router-link>
    </footer>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");

$background-color: #387ea0;
$background-color-darker: #2a6786;
$text-emphasize-color: #e5c144;

* {
  box-sizing: border-box;
  color: #f8fefc;
  font-family: "Jua", sans-serif;
}
header {
  height: 35vw;

  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    clip: rect(0, auto, auto, 0);
    overflow: hidden;
    position: absolute;
    height: 35vw;
    left: 0;
    width: 100%;
    background-image: url("../assets/images/promotion-image-1.png");
    background-size: 100% auto;
    background-position: top;
    background-attachment: fixed;

    .title_wrapper {
      position: fixed;
      display: block;
      margin: auto;
      width: 100%;

      h1 {
        text-align: center;
        font-size: 64px;
        text-transform: uppercase;
        text-align: center;
        font-weight: bolder;
        user-select: none;
        letter-spacing: 1vw;

        font-family: "Jua", sans-serif;
        color: $background-color;
        text-shadow: -1px -1px 0px white, 3px 3px 0px white, 6px 6px 0px $background-color-darker;
      }
    }
  }
}

.section {
  padding: 8rem 2rem;
  width: 100%;
  background-color: $background-color;

  .emphasize {
    color: $text-emphasize-color;
  }

  &-1 {
    .promotion-title {
      p {
        letter-spacing: 0.2rem;
        text-align: center;
        user-select: none;
      }

      @media screen and (max-width: 1019px) {
        p {
          font-size: 4vw;
        }
      }
      @media screen and (min-width: 1020px) {
        p {
          font-size: 2.5vw;
        }
      }
    }
  }

  &-2 {
    .section-container {
      display: flex;
      margin: auto;
      justify-content: center;
      align-items: center;
      max-width: 80vw;

      @media screen and (max-width: 1019px) {
        flex-direction: column;
        img {
          max-width: 50vw;
          max-height: 60vw;
        }
      }

      @media screen and (min-width: 1020px) {
        flex-direction: row;
        img {
          max-width: 30vw;
          max-height: 80vw;
          padding-right: 2rem;
        }
      }

      .promotion-content {
        display: flex;
        flex-direction: column;
        align-items: center;

        img {
          margin: 2rem auto;
          max-width: 30vw;
          -webkit-user-drag: none;
          user-select: none;
        }

        p {
          padding: 1rem 0;
          width: 100%;
          white-space: nowrap;
          letter-spacing: 0.1rem;
          font-family: "Jua", sans-serif;
        }

        @media screen and (max-width: 1019px) {
          p {
            font-size: 3vw;
            line-height: 4vw;
          }
        }
        @media screen and (min-width: 1020px) {
          p {
            font-size: 1.5vw;
            line-height: 2vw;
          }
        }
      }
    }
  }
}

footer {
  height: 15vw;
  background-color: $background-color;
  display: flex;
  justify-content: center;
  align-items: flex-start;

  button {
    border-radius: 20px;
    border: 1px solid $background-color-darker;
    background-color: $background-color-darker;
    color: white;
    font-size: 1.5rem;
    padding: 1rem 3rem;
    cursor: pointer;
    display: block;
    transition: all 0.3s ease-in-out;

    animation: pulsate 1.5s infinite;
    box-shadow: 0 0 7px 0 $background-color-darker;

    @keyframes pulsate {
      50% {
        box-shadow: 0 0 24px 0 $background-color-darker;
      }
    }

    &:hover {
      background-color: white;
      color: $background-color-darker;
      transform: scale(1.05);
    }
  }
}
</style>
