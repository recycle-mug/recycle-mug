<template>
  <div class="card">
    <nav>
      <div class="icon-wrapper" style="float:left;" @click="closeThisModal()">
        <font-awesome-icon :icon="['fas', 'times']" style="width:100%;"></font-awesome-icon>
      </div>
      <span @click="closeThisModal()">Close This Page</span>
    </nav>

    <div class="card-body">
      <div class="qr-code-wrapper" v-if="customUri">
        <div class="qr-code">
          <vue-qrcode :value="customUri" style="width:250px; height:250px; object-fit:cover;" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VueQrcode from "vue-qrcode";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { faTrashAlt } from "@fortawesome/fontawesome-free-regular";

faLibrary.add(faTimes, faTrashAlt);

export default {
  data() {
    return {
      userId: null,
      customUri: null,
    };
  },
  components: { FontAwesomeIcon, VueQrcode },
  props: ["cupId"],
  methods: {
    closeThisModal() {
      this.$emit("closeModal");
    },
    getProfile() {
      this.$store.dispatch("GETPROFILE").then(() => {
        this.userId = this.$store.state.user.id;
        // this.customUri = `http://localhost:5000/customer/rent-cup?customerId=${this.userId}&partnerCupId=${this.cupId}`;
        this.customUri = `/backend/customer/rent-cup?customerId=${this.userId}&partnerCupId=${this.cupId}`;
      });
    },
  },
  mounted() {
    this.getProfile();
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .card {
      width: 100%;
      height: 100%;
      overflow: auto;

      nav {
        width: 100%;
        color: map-get($map: $theme, $key: "text-light");
        padding: 1rem;
        border-bottom: 2px solid map-get($map: $theme, $key: "border");
        display: flex;
        align-items: center;

        .icon-wrapper {
          width: 50px;
          align-items: center;
          cursor: pointer;
          padding: 10px;
          transition: all 0.3s ease;

          &:hover {
            transform: scale(1.2);
          }
        }

        .hover-change:hover {
          color: $error-msg;
          transform: scale(1.2);
        }

        span {
          user-select: none;
          width: 100%;
          cursor: pointer;
        }
      }

      .card-body {
        width: 100%;
        padding: 1rem;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        .qr-code-wrapper {
          width: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
        }
      }
    }
  }
}
</style>
