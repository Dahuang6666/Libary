// import { createApp } from 'vue'
// import ElementPlus from 'element-plus'
// import 'element-plus/dist/index.css'
// // 这里不再导入App.vue，因为我们以Login作为初始组件通过路由来切换到其他组件
// import router from './router/index.js'
// import Login from './components/Login.vue'
// import Home from './components/Home.vue'
// import Hello from "@/components/hello.vue";
// const app = createApp(Login)
// app.component('Hello', Hello);
// app.component('Home', Home);
// app.use(ElementPlus)
// app.use(router)
//
// app.mount('#app')
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router/index.js'
import App from './App.vue'
const app = createApp(App)
app.use(ElementPlus)
app.use(router)

app.mount('#app')
