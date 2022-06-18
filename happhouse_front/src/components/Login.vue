<template>
  <div class="container my-font2">
    <br />
    <br />
    <br />
    <br />
    <br />
    <div class="row">
      <div class="col-xl-4 col-lg-5 col-md-7 d-flex flex-column ms-auto me-auto">
        <div class="card card-plain h-100">
          <div class="position-relative mt-n4 mx-3 z-index-2">
            <div
              class="shadow-secondary border-radius-lg py-3 pe-1"
              style="background-color: #0d6efd"
            >
              <h4 class="font-weight-bolder text-center mt-2 mb-0" style="color: white">Login</h4>
              <br />
            </div>
          </div>
          <div class="card-body">
            <form role="form" class="text-start">
              <div class="input-group input-group-outline my-3">
                <input
                  type="email"
                  class="form-control"
                  v-model="$store.state.user.userId"
                  placeholder="Id"
                />
              </div>
              <div class="input-group input-group-outline mb-3">
                <input
                  type="password"
                  class="form-control"
                  v-model="$store.state.user.userPassword"
                  placeholder="Password"
                  @keyup.enter="login"
                />
              </div>
              <div class="text-center">
                <button
                  type="button"
                  class="btn w-100 my-4 mb-2"
                  @click="login"
                  style="background-color: #0d6efd; color: white"
                >
                  로그인
                </button>
              </div>
              <p class="mt-4 text-sm text-center">
                계정이 없으신가요?
                <router-link
                  to="/register"
                  class="text-gradient font-weight-bold"
                  style="color: #0d6efd"
                  >회원 가입하기</router-link
                >
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
    <br />
  </div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";
export default {
  created() {
    this.$store.commit("SET_USER", {
      isLogin: false,
      userName: "",
      userSeq: 0,
      userEmail: "",
      userId: "",
      userPassword: "",
    });
  },
  methods: {
    login() {
      http
        .post("/login", {
          userId: this.$store.state.user.userId,
          userPassword: this.$store.state.user.userPassword,
        })
        .then(({ data }) => {
          console.log("LoginVue: data : ");
          console.log(data);

          this.$store.commit("SET_USER", {
            isLogin: true,
            userName: data.userName,
            userSeq: data.userSeq,
            userEmail: data.userEmail,
            userId: data.userId,
            userPassword: data.userPassword,
          });

          this.$alertify.alert("로그인", "로그인 성공");

          this.$router.push("/");
        })
        .catch((error) => {
          console.log(error.response);
          console.log("LoginVue: error : ");
          console.log(error);
          if (error.response.status == "404") {
            this.$alertify.error("아이디 또는 비밀번호를 확인하세요.");
          } else {
            this.$alertify.error("아이디 또는 비밀번호를 확인하세요.");
          }
        });
    },
  },
};
</script>

<style></style>
