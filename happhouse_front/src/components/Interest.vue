<template>
  <div>
    <br />
    <br />
    <br />
    <br />
    <br />
    <div class="row my-font2">
      <!-- 관심 매물 -->
      <div class="col-4">
        <table class="table align-items-center mb-0">
          <thead>
            <tr>
              <th
                class="text-center text-uppercase text-secondary text-s font-weight-bolder opacity-7"
              >
                번호
              </th>
              <th
                class="text-center text-uppercase text-secondary text-s font-weight-bolder opacity-7"
              >
                아파트 이름
              </th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(house, index) in $store.state.houses.houselist"
              :key="index"
              @click="aptDetail(house)"
              style="cursor: pointer"
            >
              <td class="text-s text-center">{{ house.aptCode }}</td>
              <td class="text-s text-center">{{ house.aptName }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="col">
        <div class="row">
          <div class="col">
            <!-- map -->
            <!-- 지도를 표시할 div 입니다 -->
            <div id="map" style="width: 100%; height: 600px"></div>
          </div>
        </div>
        <table class="table align-items-center mb-0" v-if="isChecked">
          <thead>
            <tr>
              <th
                class="text-center text-uppercase text-secondary text-s font-weight-bolder opacity-7"
              >
                건설년도
              </th>
              <th
                class="text-center text-uppercase text-secondary text-s font-weight-bolder opacity-7"
              >
                거래가격
              </th>
              <th
                class="text-center text-uppercase text-secondary text-s font-weight-bolder opacity-7"
              >
                거래일자
              </th>
              <th
                class="text-center text-uppercase text-secondary text-s font-weight-bolder opacity-7"
              >
                평수
              </th>
              <th
                class="text-center text-uppercase text-secondary text-s font-weight-bolder opacity-7"
              >
                층수
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(housedeal, index) in houseDealList" :key="index">
              <td class="text-s text-center">{{ housedeal.buildYear }}</td>
              <td class="text-s text-center">{{ housedeal.dealAmount }}</td>
              <td class="text-s text-center">
                {{ housedeal.dealYear }}.{{ housedeal.dealMonth }}.{{ housedeal.dealDay }}
              </td>
              <td class="text-s text-center">{{ housedeal.area }}</td>
              <td class="text-s text-center">{{ housedeal.floor }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <a href="#" class="scroll-top btn-hover">
      <i class="lni lni-chevron-up"></i>
    </a>
    <br />
  </div>
</template>

<script>
export default {
  data() {
    return {
      map: null,
      detailModal: null,
      lat: 35.160494,
      lng: 129.0573258,
      markers: [],
      isChecked: false,
    };
  },
  created() {
    this.$store.dispatch("getInterest", this.$store.state.user.userSeq);
    this.$store.dispatch("getInterestList", this.$store.state.user.userSeq);
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
      this.$router.go();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=f007d231b738fb07ad65258951c02f45&libraries=services";
      document.head.appendChild(script);
    }
  },

  methods: {
    initMap() {
      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(this.lat, this.lng),
        level: 3,
      };
      //지도 객체를 등록합니다.
      this.map = new kakao.maps.Map(container, options);
    },
    setCenter(lat, lng) {
      // 이동할 위도 경도 위치를 생성합니다
      var moveLatLon = new kakao.maps.LatLng(lat, lng);

      // 지도 중심을 이동 시킵니다
      this.map.setCenter(moveLatLon);
    },

    //아파트들 마크 표시
    AptMarker(lat, lng) {
      //현재 표시되어있는 마커들 제거
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }
      this.markers = [];
      //마커를 뿌릴 위치를 저장(아파트 위치들)
      const position = new kakao.maps.LatLng(lat, lng);
      const imgSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      const imgSize = new kakao.maps.Size(24, 35);
      // 마커 이미지를 생성합니다
      const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);
      const marker = new kakao.maps.Marker({
        map: this.map, // 마커를 표시할 지도
        position: position, // 마커를 표시할 위치
        image: markerImage, // 마커 이미지
      });

      this.markers.push(marker);

      this.setCenter(lat, lng);
    },

    aptDetail(house) {
      this.isChecked = true;
      this.houseNo = house.aptCode;
      this.getHouseDealList();
      this.AptMarker(house.lat, house.lng);
    },
    getHouseDealList() {
      this.$store.dispatch("getHouseDealList");
    },
  },
  computed: {
    houseDealList() {
      return this.$store.getters.getHouseDealList;
    },
    houseNo: {
      get() {
        return this.$store.state.houses.houseNo;
      },
      set(houseNo) {
        this.$store.commit("SET_HOUSES_HOUSENO", houseNo);
      },
    },
  },
};
</script>

<style scoped></style>
