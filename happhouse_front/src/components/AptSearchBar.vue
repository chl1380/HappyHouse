<template>
  <div class="row" style="padding-top: 20px">
    <div class="col-3" style="background-color: white"></div>
    <div class="col-2 pt-2" style="background-color: white">
      <span class="input-group input-group-static mb-4">
        <!-- <label for="sidoOption" class="ms-0">시.도</label> -->
        <select class="form-select" id="sidoOption" @change="gugunList">
          <option
            v-for="(sido, index) in $store.state.houses.sidos"
            :key="index"
            :value="sido.value"
          >
            {{ sido.text }}
          </option>
        </select>
      </span>
    </div>
    <div class="col-2 pt-2" style="background-color: white">
      <span class="input-group input-group-static mb-4">
        <!-- <label for="gugunOption" class="ms-0">구.군</label> -->
        <select class="form-select" id="gugunOption" @change="dongList">
          <option
            v-for="(gugun, index) in $store.state.houses.guguns"
            :key="index"
            :value="gugun.value"
          >
            {{ gugun.text }}
          </option>
        </select>
      </span>
    </div>
    <div class="col-2 pt-2" style="background-color: white">
      <span class="input-group input-group-static mb-4">
        <!-- <label for="dongOption" class="ms-0">동</label> -->
        <select class="form-select" id="dongOption" @change="searchApt">
          <option
            v-for="(dong, index) in $store.state.houses.dongs"
            :key="index"
            :value="dong.value"
          >
            {{ dong.text }}
          </option>
        </select>
      </span>
    </div>
    <div class="col-3" style="background-color: white"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      sidoCode: "",
      sidoName: "",
      gugunCode: "",
      gugunName: "",
      dongCode: "",
      dongName: "",
    };
  },
  created() {
    this.$store.commit("CLEAR_SIDO_LIST");
    this.$store.commit("CLEAR_GUGUN_LIST");
    this.$store.commit("CLEAR_DONG_LIST");
    this.$store.commit("CLEAR_HOUSE_LIST");
    this.$store.dispatch("getSido");
    console.log("created : ");
    console.log(this.$store.state.houses.sidos);
  },
  methods: {
    showSidoList() {
      console.log(this.$store.state.houses.sidos);
    },
    gugunList() {
      var selectedSido = document.getElementById("sidoOption");
      this.sidoCode = selectedSido.options[selectedSido.selectedIndex].value;
      this.sidoName = selectedSido.options[selectedSido.selectedIndex].text;
      this.$store.commit("CLEAR_GUGUN_LIST");
      this.gugunCode = null;
      if (this.sidoCode != null) this.$store.dispatch("getGugun", this.sidoCode);
    },
    dongList() {
      var selectedGugun = document.getElementById("gugunOption");
      this.gugunCode = selectedGugun.options[selectedGugun.selectedIndex].value;
      this.gugunName = selectedGugun.options[selectedGugun.selectedIndex].text;
      console.log("dongList : " + this.gugunName + " " + this.gugunCode);
      this.$store.commit("CLEAR_DONG_LIST");
      this.dongCode = null;
      if (this.gugunCode != null) this.$store.dispatch("getDong", this.gugunCode);
    },
    searchApt() {
      var selectedDong = document.getElementById("dongOption");
      this.dongCode = selectedDong.options[selectedDong.selectedIndex].value;
      this.dongName = selectedDong.options[selectedDong.selectedIndex].text;
      console.log("searchApt : " + this.dongCode + " " + this.dongName);
      if (this.dongName != null) this.$store.dispatch("getHouseList", this.dongName);
    },
  },
};
</script>

<style></style>
