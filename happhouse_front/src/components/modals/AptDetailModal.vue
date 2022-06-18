<template>
  <div>
    <div
      class="modal fade my-font2"
      id="aptModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title font-weight-normal" id="detailModalLabel">
              {{ $store.state.houses.house.aptName }}
            </h5>
            <button class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <div class="card">
              <img src="@/assets/img/houselist.jpg" />
            </div>
            <div class="card">
              <div class="table-responsive">
                <table class="text-center" style="width: 100%">
                  <tr class="m-3">
                    <td>
                      <h6 class="mb-0 text-s font-weight-bolder">이름</h6>
                    </td>
                    <td>
                      <p class="text-s mb-0">
                        {{ $store.state.houses.house.aptName }}
                      </p>
                    </td>
                  </tr>
                  <tr class="m-3">
                    <td>
                      <h6 class="mb-0 text-s font-weight-bolder">주소</h6>
                    </td>
                    <td>
                      <p class="text-s mb-0">
                        {{
                          $store.state.houses.house.sidoName +
                          " " +
                          $store.state.houses.house.gugunName +
                          " " +
                          $store.state.houses.house.dongName +
                          " " +
                          $store.state.houses.house.jibun
                        }}
                      </p>
                    </td>
                  </tr>
                  <tr class="m-3">
                    <td>
                      <h6 class="mb-0 text-s font-weight-bolder">건축연도</h6>
                    </td>
                    <td>
                      <p class="text-s mb-0">
                        {{ $store.state.houses.house.buildYear }}
                      </p>
                    </td>
                  </tr>
                  <tr class="m-3">
                    <td>
                      <h6 class="mb-0 text-s font-weight-bolder">최근거래금액</h6>
                    </td>
                    <td>
                      <p class="text-s mb-0">
                        {{
                          (parseInt($store.state.houses.house.recentPrice.replace(",", "")) * 10000)
                            | price
                        }}원
                        <!-- {{ $store.state.houses.house.recentPrice }} -->
                      </p>
                    </td>
                  </tr>
                </table>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn bg-gradient-warning"
              @click="intDelete"
              v-if="$store.state.user.isLogin && $store.state.houses.house.isInterest"
              style="background-color: #0d6efd; color: white"
            >
              관심 아파트 해제
            </button>
            <button
              type="button"
              class="btn bg-gradient-info"
              @click="intInsert"
              v-else-if="$store.state.user.isLogin && !$store.state.houses.house.isInterest"
              style="background-color: #0d6efd; color: white"
            >
              관심 아파트 지정
            </button>
            <button
              type="button"
              class="btn bg-gradient-secondary"
              data-bs-dismiss="modal"
              style="background-color: #0d6efd; color: white"
            >
              닫기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/common/axios.js";
import { Modal } from "bootstrap";

export default {
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    intInsert() {
      var params = {
        userSeq: this.$store.state.user.userSeq,
        houseNo: this.$store.state.houses.house.aptCode,
      };
      Modal.getOrCreateInstance(document.getElementById("aptModal")).hide();
      http
        .post("/interests", params)
        .then(({ data }) => {
          console.log("intInsertData : ");

          console.log(data);
          this.$store.state.houses.house.isInterest = true;

          console.log(this.$store.state.user.userSeq);
          this.$store.dispatch("getInterest", this.$store.state.user.userSeq);
        })
        .catch((error) => {
          console.error("intInsertError : ");
          console.error(error);
        });
    },
    intDelete() {
      Modal.getOrCreateInstance(document.getElementById("aptModal")).hide();
      http
        .delete("/interests", {
          data: {
            userSeq: this.$store.state.user.userSeq,
            houseNo: this.$store.state.houses.house.aptCode,
          },
        })
        .then(({ data }) => {
          console.log("intDeleteData : ");
          console.log(data);
          this.$store.state.houses.house.isInterest = false;
          this.$store.dispatch("getInterest", this.$store.state.user.userSeq);
        })
        .catch((error) => {
          console.error("intDeleteError : ");
          console.error(error);
        });
      this.$store.dispatch("getInterest", this.$store.state.user.userSeq);
      this.$store.dispatch("getInterestList", this.$store.state.user.userSeq);
      this.$store.dispatch("getInterestListMain", this.$store.state.user.userId);
      this.$router.go();
    },
  },
};
</script>

<style></style>
