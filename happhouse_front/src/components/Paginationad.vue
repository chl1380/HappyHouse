<template>
  <nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
      <li v-if="prev" class="page-item">
        <a
          class="page-link"
          aria-label="Previous"
          @click="paginationChanged(startPageIndex - 1)"
          style="background-color: #c3bbac; border-color: #c3bbac; cursor: pointer"
        >
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <li
        v-for="index in endPageIndex - startPageIndex + 1"
        :key="index"
        v-bind:class="{
          active: startPageIndex + index - 1 == $store.state.boardad.currentPageIndex,
        }"
        class="page-item"
      >
        <a
          @click="paginationChanged(startPageIndex + index - 1)"
          class="page-link"
          style="background-color: #0d6efd; border-color: #0d6efd; cursor: pointer"
          >{{ startPageIndex + index - 1 }}</a
        >
      </li>
      <li v-if="next" class="page-item">
        <a
          class="page-link"
          aria-label="Next"
          @click="paginationChanged(endPageIndex + 1)"
          style="background-color: #c3bbac; border-color: #c3bbac; cursor: pointer"
        >
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "Pagination",

  data() {
    return {};
  },
  computed: {
    pageCount: function () {
      return this.$store.getters.getPageCountad;
    },
    startPageIndex: function () {
      return this.$store.getters.getStartPageIndexad;
    },
    endPageIndex: function () {
      return this.$store.getters.getEndPageIndexad;
    },
    prev: function () {
      return this.$store.getters.getPrevad;
    },
    next: function () {
      return this.$store.getters.getNextad;
    },
  },
  methods: {
    paginationChanged(pageIndex) {
      console.log("paginationVue : paginationChanged : pageIndex : " + pageIndex);
      this.$emit("call-parent", pageIndex);
    },
  },
  created() {
    this.paginationChanged(1);
  },
};
</script>
