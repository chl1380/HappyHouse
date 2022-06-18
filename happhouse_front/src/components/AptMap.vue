<template>
  <div>
    <div id="wrapper" class="my-font2">
      <div class="map_wrap">
        <div id="map" style="height: 89vh; width: 100%"></div>
        <ul id="category">
          <li id="BK9" data-order="0" @click="onClickCategory('BK9')">
            <span class="category_bg bank">
              <img
                src="https://cdn-icons.flaticon.com/png/512/5695/premium/5695119.png?token=exp=1653480253~hmac=170f39514106a3080ccf71a7edc92062"
                alt="bank"
                width="30px"
                height="30px"
              />
            </span>
            은행
          </li>
          <li id="MT1" data-order="1" @click="onClickCategory('MT1')">
            <span class="category_bg mart">
              <img
                src="https://cdn-icons.flaticon.com/png/512/5695/premium/5695140.png?token=exp=1653479995~hmac=ebbe88f40feab77b212ba55ec05123fd"
                alt="bank"
                width="30px"
                height="30px"
              />
            </span>
            마트
          </li>
          <li id="PM9" data-order="2" @click="onClickCategory('PM9')">
            <span class="category_bg pharmacy">
              <img
                src="https://cdn-icons.flaticon.com/png/512/5695/premium/5695684.png?token=exp=1653480371~hmac=082efd5eb3d69087aeb3b5855b6d2daa"
                alt="bapharmacynk"
                width="30px"
                height="30px"
              />
            </span>
            약국
          </li>
          <li id="OL7" data-order="3" @click="onClickCategory('OL7')">
            <span class="category_bg oil">
              <img
                src="https://cdn-icons.flaticon.com/png/512/5695/premium/5695643.png?token=exp=1653479995~hmac=3dd802d2a058ba915f94b9ed4af458a7"
                alt="oil"
                width="30px"
                height="30px"
              />
            </span>
            주유소
          </li>
          <li id="CE7" data-order="4" @click="onClickCategory('CE7')">
            <span class="category_bg cafe"
              ><img
                src="https://cdn-icons.flaticon.com/png/512/5695/premium/5695680.png?token=exp=1653480084~hmac=e7efe3ca5bc427e9701226eabdaf80b0"
                alt="cafe"
                width="30px"
                height="30px"
              />
            </span>
            카페
          </li>
          <li id="CS2" data-order="5" @click="onClickCategory('CS2')">
            <span class="category_bg store"
              ><img
                src="https://cdn-icons.flaticon.com/png/512/5693/premium/5693840.png?token=exp=1653479995~hmac=ec676724238bf16e158d16f409836dfb"
                alt="store"
                width="30px"
                height="30px"
              />
            </span>
            편의점
          </li>
        </ul>
      </div>
      <div class="card" id="list">
        <div class="table-responsive">
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
                @click="showDetailModal(house)"
                style="cursor: pointer"
              >
                <td class="text-s text-center">{{ house.aptCode }}</td>
                <td class="text-s text-center">{{ house.aptName }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <detail-modal></detail-modal>
    <a href="#" class="scroll-top btn-hover">
      <i class="lni lni-chevron-up"></i>
    </a>
    <br />
  </div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import DetailModal from "./modals/AptDetailModal.vue";
import { Modal } from "bootstrap";
export default {
  components: {
    DetailModal,
  },
  data() {
    return {
      kakao: window.kakao,
      map: null,
      markers: [],
      customOverlays: [],
      customOverlay: null,
      detailModal: null,
      ps: null,
      currCategory: "",
      placeOverlay: null,
      contentNode: null,
      placeMarkers: [],
    };
  },
  created() {
    this.$store.commit("CLEAR_SIDO_LIST");
    this.$store.commit("CLEAR_GUGUN_LIST");
    this.$store.commit("CLEAR_DONG_LIST");
    this.$store.commit("CLEAR_HOUSE_LIST");
    this.$store.dispatch("getSido");
    this.$store.dispatch("getInterest", this.$store.state.user.userSeq);
    this.$store.dispatch("getInterestList", this.$store.state.user.userSeq);
    console.log("created");
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
    this.detailModal = new Modal(document.getElementById("aptModal"));
  },
  // Err
  updated() {
    this.displayMarkers(this.$store.state.houses.houselist);
  },
  methods: {
    initMap() {
      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(35.160494, 129.0573258),
        level: 3,
      };

      this.map = new kakao.maps.Map(container, options);
      this.ps = new kakao.maps.services.Places(this.map);
      this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
      this.contentNode = document.createElement("div");
      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
      this.contentNode.className = "placeinfo_wrap";
      // 지도에 idle 이벤트를 등록합니다
      kakao.maps.event.addListener(this.map, "idle", this.searchPlaces);

      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(this.contentNode, "mousedown", kakao.maps.event.preventMap);
      this.addEventHandle(this.contentNode, "touchstart", kakao.maps.event.preventMap);

      // 커스텀 오버레이 컨텐츠를 설정합니다
      this.placeOverlay.setContent(this.contentNode);
    },
    // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },

    // 카테고리 검색을 요청하는 함수입니다
    searchPlaces() {
      if (!this.currCategory) {
        return;
      }

      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removePlaceMarker();

      this.ps.categorySearch(this.currCategory, this.placesSearchCB, {
        useMapBounds: true,
      });
    },
    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.displayPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
        // this.$alertify.error("검색 결과가 존재하지 않습니다.");
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
        // this.$alertify.error("에러가 발생했습니다.");
      }
    },
    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var order = document.getElementById(this.currCategory).getAttribute("data-order");

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addPlaceMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        var $this = this;
        (function (marker, place) {
          kakao.maps.event.addListener(marker, "click", function () {
            $this.displayPlaceInfo(place);
          });
        })(marker, places[i]);

        kakao.maps.event.addListener($this.map, "click", function () {
          $this.placeOverlay.setMap(null);
        });
      }
    },
    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    //     addPlaceMarker(position, order) {
    //       // var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다

    //       // var imageSrc = [
    //       //   "https://us.123rf.com/450wm/kall1st0/kall1st01606/kall1st0160600055/58955597-%EA%B2%80%EC%A0%95%EC%83%89-%EB%B0%B0%EA%B2%BD%EC%97%90-%EC%82%AC%EC%A7%84-%EB%B3%B5%EA%B3%A0-%ED%9A%A8%EA%B3%BC-.jpg",
    //       //   "https://post-phinf.pstatic.net/MjAxNzAzMDNfMTgx/MDAxNDg4NTQ4ODQ5NDY2.UOsbePh_KuEZDLAqdyuneStYjLeK_bh6cwlwA49y6W8g.XGnvQ0zz7Ig3MpNG7gTRyQTlZOmj6gGzQPM9a0Y-Lpsg.JPEG/K-002.jpg",
    //       // ];
    // var imageSrc = "https://us.123rf.com/450wm/kall1st0/kall1st01606/kall1st0160600055/58955597-%EA%B2%80%EC%A0%95%EC%83%89-%EB%B0%B0%EA%B2%BD%EC%97%90-%EC%82%AC%EC%A7%84-%EB%B3%B5%EA%B3%A0-%ED%9A%A8%EA%B3%BC-.jpg",
    //       // var imageSrc = "@/assets/img/black.jpg",
    //       imageSize = new kakao.maps.Size(27, 28)), // 마커 이미지의 크기
    //         // imgOptions = {
    //         //   spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
    //         //   // spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
    //         //   // offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
    //         // },
    //         (markerImage = new kakao.maps.MarkerImage(imageSrc[order], imageSize)),
    //         (marker = new kakao.maps.Marker({
    //           position: position, // 마커의 위치
    //           image: markerImage,
    //         }));

    //       marker.setMap(this.map); // 지도 위에 마커를 표출합니다
    //       this.placeMarkers.push(marker); // 배열에 생성된 마커를 추가합니다

    //       return marker;

    // addPlaceMarker(position, order) {
    //   // var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
    //   var imageSrc = [
    //     "https://us.123rf.com/450wm/kall1st0/kall1st01606/kall1st0160600055/58955597-%EA%B2%80%EC%A0%95%EC%83%89-%EB%B0%B0%EA%B2%BD%EC%97%90-%EC%82%AC%EC%A7%84-%EB%B3%B5%EA%B3%A0-%ED%9A%A8%EA%B3%BC-.jpg",
    //     "https://post-phinf.pstatic.net/MjAxNzAzMDNfMTgx/MDAxNDg4NTQ4ODQ5NDY2.UOsbePh_KuEZDLAqdyuneStYjLeK_bh6cwlwA49y6W8g.XGnvQ0zz7Ig3MpNG7gTRyQTlZOmj6gGzQPM9a0Y-Lpsg.JPEG/K-002.jpg",
    //   ];
    //   (imageSize = new kakao.maps.Size(27, 28)), // 마커 이미지의 크기
    //     (imgOptions = {
    //       // spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
    //       // spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
    //       offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
    //     }),
    //     (markerImage = new kakao.maps.MarkerImage(imageSrc[order], imageSize, imgOptions)),
    //     (marker = new kakao.maps.Marker({
    //       position: position, // 마커의 위치
    //       image: markerImage,
    //     }));

    //   marker.setMap(this.map); // 지도 위에 마커를 표출합니다
    //   this.placeMarkers.push(marker); // 배열에 생성된 마커를 추가합니다

    //   return marker;
    // },

    addPlaceMarker(position, order) {
      // var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
      var imageSrc = [
          "https://cdn-icons.flaticon.com/png/512/5695/premium/5695119.png?token=exp=1653480253~hmac=170f39514106a3080ccf71a7edc92062",
          "https://cdn-icons.flaticon.com/png/512/5695/premium/5695140.png?token=exp=1653479995~hmac=ebbe88f40feab77b212ba55ec05123fd",
          "https://cdn-icons.flaticon.com/png/512/5695/premium/5695684.png?token=exp=1653480371~hmac=082efd5eb3d69087aeb3b5855b6d2daa",
          "https://cdn-icons.flaticon.com/png/512/5695/premium/5695643.png?token=exp=1653479995~hmac=3dd802d2a058ba915f94b9ed4af458a7",
          "https://cdn-icons.flaticon.com/png/512/5695/premium/5695680.png?token=exp=1653480084~hmac=e7efe3ca5bc427e9701226eabdaf80b0",
          "https://cdn-icons.flaticon.com/png/512/5693/premium/5693840.png?token=exp=1653479995~hmac=ec676724238bf16e158d16f409836dfb",
        ],
        // var imageSrc = [

        // ],
        imageSize = new kakao.maps.Size(45, 45), // 마커 이미지의 크기
        imgOptions = {
          // spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
          // spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc[order], imageSize, imgOptions),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.placeMarkers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removePlaceMarker() {
      for (var i = 0; i < this.placeMarkers.length; i++) {
        this.placeMarkers[i].setMap(null);
      }
      this.placeMarkers = [];
    },
    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
    displayPlaceInfo(place) {
      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ")</span>";
      } else {
        content += '    <span title="' + place.address_name + '">' + place.address_name + "</span>";
      }

      content +=
        '    <span class="tel">' + place.phone + "</span>" + "</div>" + '<div class="after"></div>';

      this.contentNode.innerHTML = content;
      this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.map);
    },
    // 카테고리를 클릭했을 때 호출되는 함수입니다
    onClickCategory(id) {
      var el = document.getElementById(id);
      // var id = el.id,
      var className = el.className;

      this.placeOverlay.setMap(null);
      console.log("onclickCategory : " + " " + id + " " + className);

      if (className === "on") {
        this.currCategory = "";
        this.changeCategoryClass();
        this.removePlaceMarker();
      } else {
        this.currCategory = id;
        this.changeCategoryClass(el);
        this.searchPlaces();
      }
    },
    // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
    changeCategoryClass(el) {
      var category = document.getElementById("category"),
        children = category.children,
        i;

      for (i = 0; i < children.length; i++) {
        children[i].className = "";
      }

      if (el) {
        el.className = "on";
      }
    },
    removeOverlay() {
      for (var i = 0; i < this.customOverlays.length; i++) {
        this.customOverlays[i].setMap(null);
      }
      this.customOverlays = [];
    },
    closeOverlay() {
      console.log("close");
      this.customOverlay.setMap(null);
    },
    displayInfowindow(marker, title, place) {
      console.log(title);
      var content = `
      <div class="overlaybox">
        <div class="boxtitle row">
          <div class="col-8">${title}</div>
            <ul>
              <li class="up">
                <span class="title">건축년도</span>
                <span class="count">${place.buildYear}</span>
              </li>
              <li>
                <span class="title">주소</span>
                <span class="count">${place.sidoName} ${place.gugunName} ${place.dongName} ${place.jibun}</span>
              </li>
              <li>
                <span class="title">최신거래금액</span>
                <span class="count">${place.recentPrice}</span>
              </li>
            </ul>
          </div>
        </div>
      `;
      var position = new kakao.maps.LatLng(
        marker.getPosition().getLat() + 0.00033,
        marker.getPosition().getLng() - 0.00003
      );
      this.customOverlay = new kakao.maps.CustomOverlay({
        position: position,
        content: content,
        xAnchor: 0.3,
        yAnchor: 0.91,
      });
      this.customOverlay.setMap(this.map);
      this.customOverlays.push(this.customOverlay);
    },
    // Err
    displayMarkers(places) {
      var fragment = document.createDocumentFragment();
      var bounds = new kakao.maps.LatLngBounds();
      // var listStr = "";

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();
      for (var i = 0; i < places.length; i++) {
        var placePosition = new kakao.maps.LatLng(places[i].lat, places[i].lng);
        var marker = this.addMarker(placePosition, i);
        var itemEl = this.getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);
        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다

        var $this = this;
        (function (marker, title, code, place) {
          kakao.maps.event.addListener(marker, "click", function () {
            $this.removeOverlay();
            $this.displayInfowindow(marker, title, place);
            console.log(title + " " + code + " " + place);
          });

          kakao.maps.event.addListener($this.map, "click", function () {
            $this.removeOverlay();
          });

          itemEl.onmouseover = function () {
            this.displayInfowindow(marker, title);
          };

          itemEl.onmouseout = function () {
            this.customOverlay.setMap(null);
          };
        })(marker, places[i].aptName, places[i].aptCode, places[i]);
        fragment.appendChild(itemEl);
      }
      // 마커를 생성하고 지도에 표시합니다

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      this.map.setBounds(bounds);
    },
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    addMarker(position, idx) {
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    getListItem(index, place) {
      var el = document.createElement("li");
      var itemStr = `
         <span class="markerbg marker_${index + 1}></span>
         <div class="info"><h5>${place.aptName}</h5> <button>관심등록</button>
         <span>${place.sidoName} ${place.gugunName} ${place.dongName} ${place.jibun}</span>
      `;
      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },
    showDetailModal(house) {
      this.$store.commit("SET_DETAIL_HOUSE", house);
      this.$store.state.houses.house.isInterest = false;
      this.$store.commit("SET_INTEREST");
      this.detailModal.show();
      console.log(this.$store.state.houses.house.isInterest);
    },
  },
};
</script>

<style>
#wrapper {
  position: relative;
}
#list {
  position: absolute;

  top: 10px;
  bottom: 10px;
  left: 10px;

  width: 400px;
  padding: 10px;

  z-index: 100;

  background-color: rgba(255, 255, 255, 0.9);

  overflow-y: scroll;
  overflow-x: scroll;
}
/* width */
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
.map_wrap,
.map_wrap > * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
}
#category {
  position: absolute;
  top: 10px;
  right: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: rgb(250, 115, 115);
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
/* #category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .bank {
  background-position: -10px 0;
  background-position-x: -7px;
}
#category li .mart {
  background-position: -10px -36px;
  background-position-x: -7px;
}
#category li .pharmacy {
  background-position: -10px -72px;
  background-position-x: -7px;
}
#category li .oil {
  background-position: -10px -108px;
  background-position-x: -7px;
}
#category li .cafe {
  background-position: -10px -144px;
  background-position-x: -7px;
}
#category li .store {
  background-position: -10px -180px;
  background-position-x: -7px;
} */
/* #category li.on .category_bg {
  background-position-x: -43px;
} */
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
.overlaybox {
  position: relative;
  width: 360px;
  height: 150px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/box_movie.png")
    no-repeat;
  padding: 15px 10px;
}
.overlaybox div,
ul {
  /* overflow: hidden; */
  margin: 0;
  padding: 0;
}
.overlaybox li {
  list-style: none;
}
.overlaybox .boxtitle {
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png")
    no-repeat right 120px center;
  margin-bottom: 8px;
}
.overlaybox .first {
  position: relative;
  width: 247px;
  height: 136px;
}
.first .text {
  color: #fff;
  font-weight: bold;
}
.first .triangle {
  position: absolute;
  width: 48px;
  height: 48px;
  top: 0;
  left: 0;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/triangle.png") no-repeat;
  padding: 6px;
  font-size: 18px;
}
.first .movietitle {
  position: absolute;
  width: 100%;
  bottom: 0;
  background: rgba(0, 0, 0, 0.4);
  padding: 7px 15px;
  font-size: 14px;
}
.overlaybox ul {
  width: 247px;
  margin-top: 8px;
}
.overlaybox li {
  position: relative;
  margin-bottom: 2px;
  background: #2b2d36;
  padding: 5px 10px;
  color: #aaabaf;
  line-height: 1;
}
.overlaybox li span {
  display: inline-block;
}
.overlaybox li .number {
  font-size: 16px;
  font-weight: bold;
}
.overlaybox li .title {
  font-size: 14px;
}
.overlaybox li .last {
  font-size: 12px;
  margin-right: 15px;
}
.overlaybox ul .arrow {
  position: absolute;
  margin-top: 8px;
  right: 25px;
  width: 5px;
  height: 3px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/updown.png") no-repeat;
}
.overlaybox li .up {
  background-position: 0 -40px;
}
.overlaybox li .down {
  background-position: 0 -60px;
}
.overlaybox li .count {
  position: absolute;
  margin-top: 3px;
  right: 15px;
  font-size: 10px;
}
.overlaybox li:hover {
  color: #fff;
  background: #d24545;
}
.overlaybox li:hover .up {
  background-position: 0 0px;
}
.overlaybox li:hover .down {
  background-position: 0 -20px;
}
</style>
