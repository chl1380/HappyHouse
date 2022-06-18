<template>
  <div class="modal my-font2" tabindex="-1" id="detailModal">
    <div class="modal-dialog modal-dialog-centered modal-xl">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">
            <strong>게시판 NO.{{ $store.state.board.boardId }}</strong>
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <table class="table">
            <tbody>
              <tr>
                <td colspan="4"><strong>제목</strong> : {{ $store.state.board.title }}</td>
              </tr>
              <tr>
                <td><strong>작성자</strong> {{ $store.state.board.userName }}</td>
                <td></td>
                <td class="d-flex justify-content-end">
                  <strong>작성일시</strong>&nbsp;{{ $store.state.board.regDt }}&nbsp;&nbsp;&nbsp;
                  <strong>조회수</strong>&nbsp;{{ $store.state.board.readCount }}
                </td>
              </tr>
              <tr>
                <td height="180"><strong>내용</strong></td>
                <td colspan="3">
                  <div
                    v-html="$store.state.board.content"
                    style="word-break: break-all; white-space: normal"
                  ></div>
                </td>
              </tr>
              <!-- New for FileUpload -->
              <tr>
                <td colspan="4"><strong>첨부파일</strong></td>
              </tr>
              <tr>
                <td colspan="4">
                  <div
                    style="height: 25px"
                    v-for="(file, index) in $store.state.board.fileList"
                    :key="index"
                  >
                    <span class="fileName">{{ file.fileName }}</span>
                    <a
                      type="button"
                      class="btn btn-sm fas fa-download btn-default btn-xs mt-3"
                      v-bind:href="urllink + file.fileUrl"
                      download
                    ></a>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="modal-footer">
          <button
            v-show="$store.state.board.sameUser"
            @click="changeToUpdate"
            class="btn btn-sm"
            data-dismiss="modal"
            type="button"
            style="background-color: #0d6efd; color: white"
          >
            글 수정하기
          </button>
          <button
            v-show="$store.state.board.sameUser"
            @click="changeToDelete"
            class="btn btn-sm btn-outline"
            data-dismiss="modal"
            type="button"
            style="background-color: #0d6efd; color: white"
          >
            글 삭제하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DetailModal",
  data() {
    return {
      urllink: "http://localhost:8080/",
    };
  },

  methods: {
    changeToUpdate() {
      this.$emit("call-parent-change-to-update");
    },
    changeToDelete() {
      this.$emit("call-parent-change-to-delete");
    },
  },
};
</script>

<style>
::-webkit-scrollbar {
  width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
  background: #f1f1f1;
}

/* Handle */
::-webkit-scrollbar-thumb {
  background: #888;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
