import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import http from "@/common/axios.js";
import router from "@/routers/routers.js";
import createPersistedState from "vuex-persistedstate";

export default new Vuex.Store({
  plugins: [createPersistedState()],

  state: {
    // 유저 정보 저장 ------------------------------------
    user: {
      isLogin: false,
      userSeq: 0,
      userName: "",
      userId: "",
      userPassword: "",
      userEmail: "",
    },

    // 게시판 글 정보 저장----------------------------------
    board: {
      list: [],
      limit: 10,
      offset: 0,
      searchWord: "",

      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,
      totalListItemCount: 0,

      boardId: 0,
      title: "",
      content: "",
      userName: "",
      regDt: {},
      readCount: 0,
      fileList: [],
      sameUser: false,
    },

    // 공지사항 글 정보 저장--------------------------
    boardad: {
      list: [],
      limit: 10,
      offset: 0,
      searchWord: "",

      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,
      totalListItemCount: 0,

      boardId: 0,
      title: "",
      content: "",
      userName: "",
      regDt: {},
      readCount: 0,
      fileList: [],
      sameUser: false,
    },

    // 하우스 정보 저장 ----------------------------
    houses: {
      house: {
        aptCode: 0,
        aptName: "",
        dongCode: "",
        dongName: "",
        sidoCode: "",
        sidoName: "",
        gugunCode: "",
        gugunName: "",
        buildYear: "",
        jibun: "",
        lat: "",
        lng: "",
        img: "",
        recentPrice: "",
        isInterest: false,
      },
      sidos: [{ value: null, text: "시/도" }],
      guguns: [{ value: null, text: "구/군" }],
      dongs: [{ value: null, text: "동" }],
      houselist: [],
      houseInterestlist: [],
      maininterestlist: [],
      houseDealList: [],
      houseNo: "",
    },
  },
  // state 상태를 변경하는 유일한 방법
  mutations: {
    // 유저 정보 가져와서 설정하는 부분 --------------------------------------
    SET_USER(state, payload) {
      state.user.isLogin = payload.isLogin;
      state.user.userName = payload.userName;
      state.user.userSeq = payload.userSeq;
      state.user.userEmail = payload.userEmail;
      state.user.userId = payload.userId;
      state.user.userPassword = payload.userPassword;
    },

    //아파트 정보 가져와서 설정하는 부분 ---------------------------------
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.houses.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.houses.guguns.push({
          value: gugun.gugunCode,
          text: gugun.gugunName,
        });
        console.log("SET_GUGUN_LIST");
        console.log(state.houses.guguns);
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.houses.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    CLEAR_SIDO_LIST: (state) => {
      state.houses.sidos = [{ value: null, text: "시/도" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.houses.guguns = [{ value: null, text: "구/군" }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.houses.dongs = [{ value: null, text: "동" }];
    },
    CLEAR_HOUSE_LIST: (state) => {
      state.houses.houselist = [];
    },
    SET_HOUSE_LIST: (state, houses) => {
      state.houses.houselist = houses;
    },
    SET_DETAIL_HOUSE(state, payload) {
      state.houses.house.aptCode = payload.aptCode;
      state.houses.house.aptName = payload.aptName;
      state.houses.house.buildYear = payload.buildYear;
      state.houses.house.dongCode = payload.dongCode;
      state.houses.house.dongName = payload.dongName;
      state.houses.house.jibun = payload.jibun;
      state.houses.house.lat = payload.lat;
      state.houses.house.lng = payload.lng;
      state.houses.house.sidoName = payload.sidoName;
      state.houses.house.gugunName = payload.gugunName;
      state.houses.house.recentPrice = payload.recentPrice;
    },
    SET_INTEREST(state) {
      state.houses.houseInterestlist.forEach((int) => {
        if (state.houses.house.aptCode == int) {
          state.houses.house.isInterest = true;
        }
      });
    },
    SET_INTEREST_LIST(state, ints) {
      console.log("SET_INTEREST_LIST : ");
      console.log(ints);
      state.houses.houseInterestlist = ints;
    },

    // 게시판 정보 가져와서 설정하는 부분 ----------------------------------
    SET_BOARD_LIST(state, list) {
      state.board.list = list;
    },

    SET_BOARD_TOTAL_LIST_ITEM_COUNT(state, count) {
      state.board.totalListItemCount = count;
    },

    SET_BOARD_MOVE_PAGE(state, pageIndex) {
      state.board.offset = (pageIndex - 1) * state.board.listRowCount;
      state.board.currentPageIndex = pageIndex;
    },

    SET_BOARD_DETAIL(state, payload) {
      state.board.boardId = payload.boardId;
      state.board.title = payload.title;
      state.board.content = payload.content;
      state.board.userName = payload.userName;
      state.board.regDt = payload.regDt;
      state.board.fileList = payload.fileList;
      state.board.sameUser = payload.sameUser;
      state.board.readCount = payload.readCount;
    },

    // 공지사항 정보 가져와서 설정하는 부분 ----------------------------------
    SET_BOARD_LISTAD(state, list) {
      state.boardad.list = list;
    },

    SET_BOARD_TOTAL_LIST_ITEM_COUNTAD(state, count) {
      state.boardad.totalListItemCount = count;
    },

    SET_BOARD_MOVE_PAGEAD(state, pageIndex) {
      state.boardad.offset = (pageIndex - 1) * state.board.listRowCount;
      state.boardad.currentPageIndex = pageIndex;
    },

    SET_BOARD_DETAILAD(state, payload) {
      state.boardad.boardId = payload.boardId;
      state.boardad.title = payload.title;
      state.boardad.content = payload.content;
      state.boardad.userName = payload.userName;
      state.boardad.regDt = payload.regDt;
      state.boardad.fileList = payload.fileList;
      state.boardad.sameUser = payload.sameUser;
      state.boardad.readCount = payload.readCount;
    },
    SET_BOARD_MAIN_LISTAD_DEFALUT(state, limit) {
      state.boardad.limit = limit;
      state.boardad.offset = 0;
      state.boardad.searchWord = "";
    },

    SET_HOUSES_HOUSEDEALLIST(state, payload) {
      state.houses.houseDealList = payload;
    },
    SET_HOUSES_HOUSENO(state, payload) {
      state.houses.houseNo = payload;
    },
  },
  // for async method
  actions: {
    async getHouseDealList(context) {
      try {
        let { data } = await http.get("/housedeal/" + this.state.houses.houseNo);
        console.log(data);
        context.commit("SET_HOUSES_HOUSEDEALLIST", data);
      } catch (error) {
        console.errer(error);
      }
    },

    // 게시판 글 가저오는 부분 ---------------------------------------------------
    boardList(context) {
      http
        .get("/boards", {
          params: {
            limit: this.state.board.limit,
            offset: this.state.board.offset,
            searchWord: this.state.board.searchWord,
          },
        })
        .then(({ data }) => {
          console.log("게시판 정보 : ");
          console.log(data);
          context.commit("SET_BOARD_LIST", data.list);
          context.commit("SET_BOARD_TOTAL_LIST_ITEM_COUNT", data.count);
        });
    },

    // 공지사항 글 가져오는 부분 --------------------------------------------------
    boardListad(context) {
      http
        .get("/notices", {
          params: {
            limit: this.state.boardad.limit,
            offset: this.state.boardad.offset,
            searchWord: this.state.boardad.searchWord,
          },
        })
        .then(({ data }) => {
          console.log("공지사항 정보 : ");
          console.log(data);
          context.commit("SET_BOARD_LISTAD", data.list);
          context.commit("SET_BOARD_TOTAL_LIST_ITEM_COUNTAD", data.count);
        });
    },

    // 하우스 관련 가져오는 부분 -----------------------------
    getSido: ({ commit }) => {
      http
        .get("/map/sido")
        .then(({ data }) => {
          console.log("getSidoData : ");
          console.log(data);
          commit("SET_SIDO_LIST", data);
        })
        .catch((error) => {
          console.error("getSidoError : ");
          console.error(error);
        });
    },
    getGugun: ({ commit }, sidoCode) => {
      http
        .get("/map/gugun/" + sidoCode)
        .then(({ data }) => {
          console.log("getGugunData : ");
          console.log(data);
          commit("SET_GUGUN_LIST", data);
        })
        .catch((error) => {
          console.error("getGugunError : ");
          console.error(error);
        });
    },
    getDong: ({ commit }, gugunCode) => {
      http
        .get("/map/dong/" + gugunCode)
        .then(({ data }) => {
          console.log("getDongData : ");
          console.log(data);
          commit("SET_DONG_LIST", data);
        })
        .catch((error) => {
          console.error("getDongError : ");
          console.error(error);
        });
    },
    getHouseList: ({ commit }, dongCode) => {
      http
        .get("/map/apt/" + dongCode)
        .then(({ data }) => {
          console.log("getHouseListData : ");
          console.log(data);
          commit("SET_HOUSE_LIST", data);
        })
        .catch((error) => {
          console.error("getHouseListError : ");
          console.error(error);
        });
    },
    getHouses: ({ commit }) => {
      http
        .get("/map/apt/")
        .then(({ data }) => {
          console.log("getHousesData : ");
          console.log(data);
          commit("SET_HOUSE_LIST", data);
        })
        .catch((error) => {
          console.error("getHousesError : ");
          console.error(error);
        });
    },
    detailHouse: ({ commit }, house) => {
      commit("SET_DETAIL_HOUSE", house);
    },
    getInterest: ({ commit }, userSeq) => {
      http
        .get("/interests/" + userSeq)
        .then(({ data }) => {
          console.log("getInterestData : ");
          console.log(data);
          commit("SET_INTEREST_LIST", data);
        })
        .catch((error) => {
          console.error("getInterestError : ");
          console.error(error);
        });
    },

    getInterestList: ({ commit }, userSeq) => {
      http
        .get("/interestslist/" + userSeq)
        .then(({ data }) => {
          console.log("getInterestListData : ");
          console.log(data);
          if (data.result == "login") {
          } else {
            commit("SET_HOUSE_LIST", data);
          }
        })
        .catch((error) => {
          console.error("getInterestListError : ");
          console.error(error);
        });
    },
  },
  getters: {
    isLogin: function (state) {
      return state.user.isLogin;
    },

    getAptList: function (state) {
      return state.houses.houselist;
    },

    // 게시판 정보 가져가는 부분 ------------------------------------------------------
    getBoardList: function (state) {
      return state.board.list;
    },

    getPageCount: function (state) {
      return Math.ceil(state.board.totalListItemCount / state.board.listRowCount);
    },
    getStartPageIndex: function (state) {
      if (state.board.currentPageIndex % state.board.pageLinkCount == 0) {
        return (
          (state.board.currentPageIndex / state.board.pageLinkCount - 1) *
            state.board.pageLinkCount +
          1
        );
      } else {
        return (
          Math.floor(state.board.currentPageIndex / state.board.pageLinkCount) *
            state.board.pageLinkCount +
          1
        );
      }
    },
    getEndPageIndex: function (state, getters) {
      let ret = 0;
      if (state.board.currentPageIndex % state.board.pageLinkCount == 0) {
        ret =
          (state.board.currentPageIndex / state.board.pageLinkCount - 1) *
            state.board.pageLinkCount +
          state.board.pageLinkCount;
      } else {
        ret =
          Math.floor(state.board.currentPageIndex / state.board.pageLinkCount) *
            state.board.pageLinkCount +
          state.board.pageLinkCount;
      }
      return ret > getters.getPageCount ? getters.getPageCount : ret;
    },
    getPrev: function (state) {
      if (state.board.currentPageIndex <= state.board.pageLinkCount) {
        return false;
      } else {
        return true;
      }
    },
    getNext: function (state, getters) {
      if (
        Math.floor(getters.getPageCount / state.board.pageLinkCount) * state.board.pageLinkCount <
        state.board.currentPageIndex
      ) {
        return false;
      } else {
        return true;
      }
    },

    // 공지사항 정보 가져가는 부분 ------------------------------------------------------
    getBoardListad: function (state) {
      return state.boardad.list;
    },

    getPageCountad: function (state) {
      return Math.ceil(state.boardad.totalListItemCount / state.boardad.listRowCount);
    },
    getStartPageIndexad: function (state) {
      if (state.boardad.currentPageIndex % state.boardad.pageLinkCount == 0) {
        return (
          (state.boardad.currentPageIndex / state.boardad.pageLinkCount - 1) *
            state.boardad.pageLinkCount +
          1
        );
      } else {
        return (
          Math.floor(state.boardad.currentPageIndex / state.boardad.pageLinkCount) *
            state.boardad.pageLinkCount +
          1
        );
      }
    },
    getEndPageIndexad: function (state, getters) {
      let ret = 0;
      if (state.boardad.currentPageIndex % state.boardad.pageLinkCount == 0) {
        ret =
          (state.boardad.currentPageIndex / state.boardad.pageLinkCount - 1) *
            state.boardad.pageLinkCount +
          state.boardad.pageLinkCount;
      } else {
        ret =
          Math.floor(state.boardad.currentPageIndex / state.boardad.pageLinkCount) *
            state.boardad.pageLinkCount +
          state.boardad.pageLinkCount;
      }
      return ret > getters.getPageCountad ? getters.getPageCountad : ret;
    },
    getPrevad: function (state) {
      if (state.boardad.currentPageIndex <= state.boardad.pageLinkCount) {
        return false;
      } else {
        return true;
      }
    },
    getNextad: function (state, getters) {
      if (
        Math.floor(getters.getPageCountad / state.boardad.pageLinkCount) *
          state.boardad.pageLinkCount <
        state.boardad.currentPageIndex
      ) {
        return false;
      } else {
        return true;
      }
    },
    getHouseDealList(state) {
      return state.houses.houseDealList;
    },
    getHouseNo(state) {
      return state.houses.houseNo;
    },
  },
});
