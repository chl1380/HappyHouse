<template>
  <div>
    <nav-bar />
    <router-view></router-view>
    <footer-bar></footer-bar>
  </div>
</template>

<script>
import NavBar from "./components/NavBar.vue";
import FooterBar from "./components/FooterBar.vue";
export default {
  name: "App",
  components: {
    NavBar,
    FooterBar,
  },

  methods: {
    logout() {
      this.isLogin = false;
      this.userInfo = {};
    },
  },
};
</script>

<style>
@import url(../assets/css/animate.css);
@import url(../assets/css/bootstrap.min.css);
@import url(../assets/css/glightbox.min.css);
@import url(../assets/css/LineIcons.2.0.css);
@import url(../assets/css/main.css);
@import url(../assets/css/tiny-slider.css);

@import url("https://fonts.googleapis.com/css2?family=Black+And+White+Picture&family=Black+Han+Sans&family=Do+Hyeon&family=Gothic+A1&family=Jua&family=Nanum+Gothic&family=Noto+Sans+KR&family=Stylish&family=Sunflower:wght@300&display=swap");

.my-font {
  font-family: "Sunflower", sans-serif;
}

.my-font2 {
  font-family: "Noto Sans KR", sans-serif;
}
</style>
