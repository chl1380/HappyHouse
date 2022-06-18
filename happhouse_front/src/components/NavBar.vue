<template>
  <header class="header">
    <div class="navbar-area">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-12">
            <nav class="navbar navbar-expand-lg">
              <a class="navbar-brand logo">
                <router-link to="/"> <img id="logo" :src="logo" /> </router-link>
              </a>
              <div
                class="collapse navbar-collapse sub-menu-bar my-font"
                id="navbarSupportedContent"
              >
                <ul id="nav" class="navbar-nav">
                  <li class="nav-item">
                    <router-link to="/home" style="font-size: 20px; text-decoration: none"
                      ><strong>Home</strong></router-link
                    >
                  </li>
                  <li class="nav-item">
                    <a href="#" style="font-size: 20px; text-decoration: none"
                      ><strong>Apt</strong></a
                    >
                    <ul class="sub-menu">
                      <li>
                        <router-link to="/apt" style="text-decoration: none"
                          ><strong>아파트매물 검색</strong></router-link
                        >
                      </li>
                      <li>
                        <router-link to="/interest" style="text-decoration: none"
                          ><strong>관심매물 검색</strong></router-link
                        >
                      </li>
                    </ul>
                  </li>
                  <li class="nav-item">
                    <a href="#" style="font-size: 20px; text-decoration: none"
                      ><strong>Board</strong></a
                    >
                    <ul class="sub-menu">
                      <li>
                        <router-link to="/notice" style="text-decoration: none"
                          ><strong>공지사항</strong></router-link
                        >
                      </li>
                      <li>
                        <router-link to="/board" style="text-decoration: none"
                          ><strong>자유게시판</strong></router-link
                        >
                      </li>
                    </ul>
                  </li>
                  <li class="nav-item">
                    <router-link to="/user" style="font-size: 20px; text-decoration: none"
                      ><strong>MyPage</strong></router-link
                    >
                  </li>
                </ul>
              </div>
              <div
                v-show="this.$store.state.user.isLogin"
                style="width: 250px; margin-left: 25px; font-size: 17px"
                class="my-font2"
              >
                안녕하세요
                <strong>{{ this.$store.state.user.userName }}</strong
                >님
              </div>
              <div class="button">
                <a
                  v-show="$store.state.user.isLogin"
                  @click="Logout"
                  class="btn"
                  style="cursor: pointer font-size: 15px"
                  ><strong>Logout</strong></a
                >
                <router-link
                  v-show="!$store.state.user.isLogin"
                  to="/login"
                  class="login lni lni-lock-alt"
                  style="font-size: 15px; text-decoration: none"
                  ><strong>Login</strong></router-link
                >
                <router-link
                  v-show="!$store.state.user.isLogin"
                  to="/register"
                  class="btn"
                  style="font-size: 15px"
                  ><strong>Sign Up</strong></router-link
                >
              </div>
            </nav>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import logo from "@/assets/img/HappyHouse.png";

export default {
  data() {
    return {
      logo: logo,
      preUrl: "",
      pageIndex: 0,
    };
  },
  name: "NavBar",
  methods: {
    gotoUrl(toUrl) {
      if (this.preUrl === this.$route.path) {
        this.$router.push(toUrl + "?" + this.pageIndex++);
      }
      this.preUrl = this.$route.path;
    },
    Logout() {
      this.$alertify.confirm("로그아웃 하시겠습니까?", () => {
        this.$store.commit("SET_USER", {
          isLogin: false,
          userName: "",
          userSeq: 0,
          userEmail: "",
          userId: "",
          userPassword: "",
        });

        this.$router.push("/home");
      });
    },
  },
};
</script>

<style>
#logo {
  width: 75px;
}
</style>
