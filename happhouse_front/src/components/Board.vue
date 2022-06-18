<template>
  <div>
    <br />
    <br />
    <br />
    <br />
    <div class="page-header min-height-300 border-radius-xl mt-4 row my-font2">
      <div>
        <br />
        <br />
      </div>
      <div class="col-12">
        <div class="card my-4 container">
          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div
              class="shadow-secondary border-radius-lg pt-4 pb-3"
              style="background-color: #0d6efd"
            >
              <h5 class="font-weight-bolder text-white text-center ps-3">자유게시판</h5>
            </div>
          </div>

          <!-- <div class="ms-md-auto pe-md-3 d-flex align-items-center"> -->
          <div class="d-grid gap-2 d-md-flex pt-2 justify-content-md-end">
            <div>
              <input
                v-model="$store.state.board.searchWord"
                @keydown.enter="boardList"
                type="text"
                class="form-control shadow-secondary"
                placeholder="검색어 입력"
              />
            </div>
            <div>
              <button
                class="btn btn-icon btn-2"
                type="button"
                @click="boardList"
                style="background-color: #0d6efd"
              >
                <span class="text-white btn-inner--icon">검색</span>
              </button>
            </div>
            <div>
              <button
                class="btn btn-icon btn-2 shadow-secondary"
                type="button"
                @click="showInsertModal"
                style="background-color: #0d6efd"
                v-show="$store.state.user.isLogin"
              >
                <span class="text-white btn-inner--icon">글쓰기</span>
              </button>
            </div>
          </div>

          <div class="card-body px-0 pb-2 pt-0">
            <div class="p-0">
              <table class="table align-items-center mb-0 table-hover">
                <thead>
                  <tr>
                    <th
                      class="text-center text-uppercase text-secondary font-weight-bolder opacity-7"
                    >
                      #
                    </th>
                    <th
                      class="text-center text-uppercase text-secondary font-weight-bolder opacity-7"
                    >
                      제목
                    </th>
                    <th
                      class="text-center text-uppercase text-secondary font-weight-bolder opacity-7"
                    >
                      작성자
                    </th>
                    <th
                      class="text-center text-uppercase text-secondary font-weight-bolder opacity-7"
                    >
                      작성일시
                    </th>
                    <th
                      class="text-center text-uppercase text-secondary font-weight-bolder opacity-7"
                    >
                      조회수
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    style="cursor: pointer"
                    v-for="(board, index) in listGetters"
                    v-bind:key="index"
                    @click="boardDetail(board.boardId)"
                    class="align-middle text-center text-secondary font-weight-bold"
                  >
                    <td>{{ board.boardId }}</td>
                    <td>{{ board.title }}</td>
                    <td>{{ board.userName }}</td>
                    <td>
                      {{
                        makeDateStr(
                          board.regDt.date.year,
                          board.regDt.date.month,
                          board.regDt.date.day,
                          "."
                        )
                      }}
                    </td>
                    <td>{{ board.readCount }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <pagination v-on:call-parent="movePage"></pagination>
    </div>

    <insert-modal v-on:call-parent-insert="closeAfterInsert"></insert-modal>
    <!-- props 제거 -->
    <detail-modal
      v-on:call-parent-change-to-update="changeToUpdate"
      v-on:call-parent-change-to-delete="changeToDelete"
    ></detail-modal>
    <update-modal v-on:call-parent-update="closeAfterUpdate"></update-modal>
    <a href="#" class="scroll-top btn-hover">
      <i class="lni lni-chevron-up"></i>
    </a>
    <br />
  </div>
</template>

<script>
import InsertModal from "./modals/InsertModal.vue";
import DetailModal from "./modals/DetailModal.vue";
import UpdateModal from "./modals/UpdateModal.vue";
import Pagination from "./Pagination.vue";

import { Modal } from "bootstrap";

import http from "@/common/axios.js";
import util from "@/common/util.js";

// 삭제
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

export default {
  name: "Board",
  components: { InsertModal, DetailModal, UpdateModal, Pagination },
  data() {
    return {
      // modal
      insertModal: null,
      detailModal: null,
      updateModal: null,
    };
  },
  computed: {
    // getters 이용
    listGetters() {
      return this.$store.getters.getBoardList;
    },
  },
  methods: {
    boardList() {
      this.$store.dispatch("boardList");
    },

    // pagination
    movePage(pageIndex) {
      console.log("BoardMainVue : movePage : pageIndex : " + pageIndex);

      this.$store.commit("SET_BOARD_MOVE_PAGE", pageIndex);

      this.boardList();
    },

    // util
    makeDateStr: util.makeDateStr,

    // insert
    showInsertModal() {
      this.insertModal.show();
    },

    closeAfterInsert() {
      this.insertModal.hide();
      this.boardList();
    },

    // detail
    boardDetail(boardId) {
      http
        .get(
          "/boards/" + boardId // props variable
        )
        .then(({ data }) => {
          console.log("DetailModalVue: data : ");
          console.log(data);

          if (data.result == "login") {
            this.$router.push("/login");
          } else {
            this.$store.commit("SET_BOARD_DETAIL", {
              boardId: data.dto.boardId,
              title: data.dto.title,
              content: data.dto.content,
              userName: data.dto.userName,
              regDt: this.makeDateStr(
                data.dto.regDt.date.year,
                data.dto.regDt.date.month,
                data.dto.regDt.date.day,
                "."
              ),
              fileList: data.dto.fileList,
              sameUser: data.dto.sameUser,
              readCount: data.dto.readCount,
            });
            console.log(data.dto.sameUser);
            this.detailModal.show();
            this.boardList();
          }
        })
        .catch((error) => {
          console.log("DetailModalVue: error ");
          console.log(error);
        });
    },

    // update
    changeToUpdate() {
      this.detailModal.hide();
      this.updateModal.show();
    },

    closeAfterUpdate() {
      this.updateModal.hide();
      this.boardList();
    },

    // delete
    changeToDelete() {
      this.detailModal.hide();

      var $this = this;
      this.$alertify.confirm(
        "이 글을 삭제하시겠습니까?",
        function () {
          $this.boardDelete(); // $this 사용
        },
        function () {
          console.log("cancel");
        }
      );
    },
    boardDelete() {
      console.log("Delete BoardId: " + this.$store.state.board.boardId);
      http
        .delete("/boards/" + this.$store.state.board.boardId)
        .then(({ data }) => {
          console.log("BoardMainVue: data : ");
          console.log(data);
          if (data.result == "login") {
            this.$router.push("/login");
          } else {
            this.boardList();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created() {
    this.boardList();
  },
  mounted() {
    this.insertModal = new Modal(document.getElementById("insertModal"));
    this.detailModal = new Modal(document.getElementById("detailModal"));
    this.updateModal = new Modal(document.getElementById("updateModal"));
  },
};
</script>

<style scoped></style>
