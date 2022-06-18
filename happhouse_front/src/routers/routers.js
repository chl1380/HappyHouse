import Vue from "vue";
import VueRouter from "vue-router";

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(() => {
    return window.location.reload();
  });
};

Vue.use(VueRouter);

import Board from "@/components/Board.vue";
import Login from "@/components/Login.vue";
import User from "@/components/User.vue";
import Register from "@/components/Register.vue";
import Home from "@/components/Home.vue";
import store from "@/store/store";
import Notice from "@/components/Notice.vue";
import Apt from "@/components/Apt.vue";
import Interest from "@/components/Interest.vue";

export default new VueRouter({
  routes: [
    {
      path: "/",
      component: Home,
    },
    {
      name: "Board",
      path: "/board",
      component: Board,
      beforeEnter: (to, from, next) => {
        if (!store.state.user.isLogin) {
          next("/login");
        } else {
          return next();
        }
      },
    },
    {
      name: "Notice",
      path: "/notice",
      component: Notice,
    },
    {
      name: "Login",
      path: "/login",
      component: Login,
    },
    {
      name: "User,",
      path: "/user",
      component: User,
      beforeEnter: (to, from, next) => {
        if (!store.state.user.isLogin) {
          next("/login");
        } else {
          return next();
        }
      },
    },
    {
      name: "Register,",
      path: "/register",
      component: Register,
    },
    {
      name: "Home,",
      path: "/home",
      component: Home,
    },
    {
      name: "Apt,",
      path: "/apt",
      component: Apt,
    },
    {
      name: "Interest,",
      path: "/interest",
      component: Interest,
      beforeEnter: (to, from, next) => {
        if (!store.state.user.isLogin) {
          next("/login");
        } else {
          return next();
        }
      },
    },
  ],
});
