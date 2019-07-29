import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Index from './views/Index'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    // {
    //   path: '/',
    //   name: 'home',
    //   component: Home
    // },
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    },
    {
      path: '/login/success',
      name: 'LoginSuccess',
      component: () => import(/* webpackChunkName: "LoginSuccess" */ './views/LoginSuccess.vue')
    },
    {
      path: '/auth/login/:appName',
      name: 'authLogin',
      component: () => import(/* webpackChunkName: "authLogin" */ './views/Login.vue')
    }
  ]
})
