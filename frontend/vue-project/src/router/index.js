import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/components/Login.vue';
import Home from "@/components/Home.vue";
import Register from '@/components/Register.vue';
import sendEmail   from "@/components/sendEmail.vue";
import myCenter from "@/components/myCenter.vue"
import book from "@/components/Book.vue"
import bookProcessing from "@/components/bookProcessing.vue"
import bookClassification  from "@/components/bookClassification.vue";
import bookNotification  from "@/components/bookNotification.vue";
import accountManagement from "@/components/accountManagement.vue";
import messageNotification from "@/components/messageNotification.vue";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      component: Register
    },
    {
      path: '/forgot-password',
      component :sendEmail
    },
    {
      path: '/Home',
      name: 'Home',
      component: Home,
      children:[
        {
          path: 'myCenter',
          name: 'myCenter',
          component: myCenter,
          props: (route) => ({ username: route.query.username, permissions: route.query.permissions})

          },
        {
          path: 'book',
          name: 'book',
          component: book,
        },
        {
          path: 'bookProcessing',
          name: 'bookProcessing',
          component: bookProcessing,
        },
        {
          path: 'bookClassification',
          name: 'bookClassification',
          component:  bookClassification,
        },
        {
          path: 'accountManagement',
          name: 'accountManagement',
          component:  accountManagement,
        },
        {
          path: 'bookNotification',
          name: 'bookNotification',
          component:  bookNotification,
        },
        {
          path: 'messageNotification',
          name: 'messageNotification',
          component:  messageNotification,
        }
      ]
    }
  ]
})
export default router;