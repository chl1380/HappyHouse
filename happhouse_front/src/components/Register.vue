<template>
  <div class="container my-font2">
    <br />
    <br />
    <br />
    <br />
    <br />
    <div class="row">
      <div class="col-xl-4 col-md-7 flex-column ms-auto me-auto">
        <div class="card card-plain">
          <div class="card-header" style="background-color: #0d6efd">
            <h4 class="font-weight-bolder" style="color: white">회원가입</h4>
            <p class="mb-0" style="color: white">회원가입에 필요한 정보를 입력해주세요</p>
          </div>
          <div class="card-body">
            <form role="form">
              <div class="text-secondary font-weight-bold text-sm">이름</div>
              <div class="input-group input-group-outline mb-3">
                <input
                  type="name"
                  class="form-control"
                  v-model="userName"
                  placeholder="Name"
                  :class="{
                    'is-valid': isUserNameFocusAndValid,
                    'is-invalid': isUserNameFocusAndInvalid,
                  }"
                  @input="validateUserName"
                  @focus="isUserNameFocus = true"
                />
                <div class="valid-feedback">사용 가능 합니다.</div>
                <div class="invalid-feedback">올바른 이름을 입력해 주세요.</div>
              </div>
              <div class="text-secondary font-weight-bold text-sm">아이디</div>
              <div class="input-group input-group-outline mb-3">
                <input
                  type="text"
                  class="form-control"
                  v-model="userId"
                  placeholder="Id"
                  :class="{
                    'is-valid': isUserIdFocusAndValid,
                    'is-invalid': isUserIdFocusAndInvalid,
                  }"
                  @input="validateUserId"
                  @focus="isUserIdFocus = true"
                />
                <div class="valid-feedback">사용 가능 합니다.</div>
                <div class="invalid-feedback">올바른 아이디를 입력해 주세요.</div>
              </div>
              <div class="text-secondary font-weight-bold text-sm">이메일</div>
              <div class="input-group input-group-outline mb-3">
                <input
                  type="email"
                  class="form-control"
                  v-model="userEmail"
                  placeholder="Email"
                  :class="{
                    'is-valid': isUserEmailFocusAndValid,
                    'is-invalid': isUserEmailFocusAndInValid,
                  }"
                  @input="validateEmail"
                  @focus="isUserEmailFocus = true"
                />
                <div class="valid-feedback">사용 가능 합니다.</div>
                <div class="invalid-feedback">올바른 이메일을 입력해 주세요.</div>
              </div>
              <div class="text-secondary font-weight-bold text-sm">비밀번호</div>
              <div class="input-group input-group-outline mb-3">
                <input
                  type="password"
                  class="form-control"
                  v-model="userPassword"
                  placeholder="Password"
                  :class="{
                    'is-valid': isUserPasswordFocusAndValid,
                    'is-invalid': isUserPasswordFocusAndInvalid,
                  }"
                  @input="validatePassword"
                  @focus="isUserPasswordFocus = true"
                />
                <div class="valid-feedback">사용 가능 합니다.</div>
                <div class="invalid-feedback">
                  1개 이상의 특수문자, 대소문자 및 숫자를 포함하고 8자리 이상이여야 합니다.
                </div>
              </div>
              <div class="text-secondary font-weight-bold text-sm">비밀번호 확인</div>
              <div class="input-group input-group-outline mb-3">
                <input
                  type="password"
                  class="form-control"
                  v-model="userPassword2"
                  placeholder="Password Confirm"
                  :class="{
                    'is-valid': isUserPassword2FocusAndValid,
                    'is-invalid': isUserPassword2FocusAndInvalid,
                  }"
                  @input="validatePassword2"
                  @focus="isUserPassword2Focus = true"
                />
                <div class="valid-feedback">일치 합니다.</div>
                <div class="invalid-feedback">일치하지 않습니다.</div>
              </div>
              <div class="text-center">
                <button
                  @click="register"
                  type="button"
                  class="btn btn-lg bg-gradient btn-lg w-100 mt-4 mb-0"
                  style="background-color: #0d6efd; color: white"
                >
                  회원가입
                </button>
              </div>
            </form>
          </div>
          <div class="card-footer text-center pt-0 px-lg-2 px-1">
            <p class="mb-2 text-sm mx-auto">
              이미 계정이 있으신가요?
              <router-link to="/login" class="text-gradient font-weight-bold" style="color: #0d6efd"
                >로그인</router-link
              >
            </p>
          </div>
        </div>
      </div>
    </div>
    <br />
  </div>
</template>

<script>
import http from "@/common/axios.js";
export default {
  data() {
    return {
      userName: "",
      userId: "",
      userEmail: "",
      userPassword: "",
      userPassword2: "",

      // focus
      isUserNameFocus: false,
      isUserIdFocus: false,
      isUserEmailFocus: false,
      isUserPasswordFocus: false,
      isUserPassword2Focus: false,

      // validation
      isUserNameValid: false,
      isUserIdValid: false,
      isUserEmailValid: false,
      isUserPasswordValid: false,
      isUserPassword2Valid: false,
    };
  },
  computed: {
    isUserNameFocusAndValid() {
      return this.isUserNameFocus && this.isUserNameValid;
    },
    isUserNameFocusAndInvalid() {
      return this.isUserNameFocus && !this.isUserNameValid;
    },
    isUserIdFocusAndValid() {
      return this.isUserIdFocus && this.isUserIdValid;
    },
    isUserIdFocusAndInvalid() {
      return this.isUserIdFocus && !this.isUserIdValid;
    },
    isUserEmailFocusAndValid() {
      return this.isUserEmailFocus && this.isUserEmailValid;
    },
    isUserEmailFocusAndInValid() {
      return this.isUserEmailFocus && !this.isUserEmailValid;
    },
    isUserPasswordFocusAndValid() {
      return this.isUserPasswordFocus && this.isUserPasswordValid;
    },
    isUserPasswordFocusAndInvalid() {
      return this.isUserPasswordFocus && !this.isUserPasswordValid;
    },
    isUserPassword2FocusAndValid() {
      return this.isUserPassword2Focus && this.isUserPassword2Valid;
    },
    isUserPassword2FocusAndInvalid() {
      return this.isUserPassword2Focus && !this.isUserPassword2Valid;
    },
  },
  methods: {
    register() {
      if (
        !this.isUserNameValid ||
        !this.isUserIdValid ||
        !this.isUserEmailValid ||
        !this.isUserPasswordValid ||
        !this.isUserPassword2Valid
      ) {
        this.$alertify.alert("회원 가입 오류", "올바른 정보를 입력해 주세요");
        return;
      }
      http
        .post("/users", {
          userEmail: this.userEmail,
          userPassword: this.userPassword,
          userName: this.userName,
          userId: this.userId,
        })
        .then(({ data }) => {
          console.log("RegisterVue: data : ");
          console.log(data);
          this.$alertify.alert("회원 가입", "회원 가입 성공");

          this.$router.push("/login");
        })
        .catch((error) => {
          console.log("RegisterVue: error : ");
          console.log(error);
          if (error.response.status == "404") {
            this.$alertify.error("이메일 또는 비밀번호를 확인하세요.");
          } else {
            this.$alertify.error("Opps!! 서버에 문제가 발생했습니다.");
          }
        });
    },
    validateUserName() {
      this.isUserNameValid = this.userName.length > 0 ? true : false;
      console.log(this.isUserNameValid);
    },
    validateUserId() {
      this.isUserIdValid = this.userId.length > 0 ? true : false;
      console.log(this.isUserNameValid);
    },
    validateEmail() {
      // ^ 시작일치, $ 끝 일치
      // {2, 3} 2개 ~ 3개
      // * 0회 이상, + 1회 이상
      // [-_.] - 또는 _ 또는 .
      // ? 없거나 1회
      let regexp =
        /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      this.isUserEmailValid = regexp.test(this.userEmail) ? true : false;
      console.log(this.isUserEmailValid);
    },
    validatePassword() {
      let patternEngAtListOne = new RegExp(/[a-zA-Z]+/);
      let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/);
      let patternNumAtListOne = new RegExp(/[0-9]+/);

      this.isUserPasswordValid =
        patternEngAtListOne.test(this.userPassword) &&
        patternSpeAtListOne.test(this.userPassword) &&
        patternNumAtListOne.test(this.userPassword) &&
        this.userPassword.length >= 8
          ? true
          : false;
    },
    validatePassword2() {
      this.isUserPassword2Valid = this.userPassword == this.userPassword2 ? true : false;
    },
  },
};
</script>

<style></style>
