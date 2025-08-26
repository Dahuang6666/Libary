<template>
  <div class="common-layout">
    <el-container class="containers">
      <el-header class="headers">
        <div class="title">
         图书管理系统
        </div>
        <div style="flex: 1"></div>
        <div class="welcome">
          <el-dropdown>
<span class="el-dropdown-link">
        欢迎：{{ permissions === '1' ? '用户' :permissions === '0'?'管理员':"超管"}}登录
<el-icon class="el-icon--right">
<arrow-down/>
</el-icon>
</span>
<template #dropdown>
   <el-dropdown-menu>
  <el-dropdown-item>修改密码</el-dropdown-item>
  <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
  </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside class="aside">
          <el-menu
              active-text-color="#090909"
              background-color="#ffffff"
              class="el-menu-vertical-demo"
              default-active="1"
              text-color="#000"
              @open="handleOpen"
              @close="handleClose"
              router
          >
            <el-menu-item @click="bookShow">
              <el-icon><View/></el-icon>
              <span>图书展示</span>
            </el-menu-item>
              <el-menu-item v-if="permissions==='6'||permissions==='0'" @click="bookProcessing">
                <el-icon><EditPen /></el-icon>
                <span>图书处理</span>
              </el-menu-item>
            <el-menu-item  v-if="permissions==='6'||permissions==='0'" @click="bookClassification">
              <el-icon><EditPen /></el-icon>
              <span>分类修改</span>
            </el-menu-item>
            <el-menu-item  v-if="permissions==='6'||permissions==='0'" @click="bookNotification">
              <el-icon><ChatDotRound /></el-icon>
              <span>借书通知</span>
            </el-menu-item>
            <el-menu-item   v-if="permissions==='1'" @click="messagesNotification">
              <el-icon><ChatDotRound /></el-icon>
              <span>借书进度</span>
            </el-menu-item>
            <el-menu-item v-if="permissions==='6'" @click="accountManagement">
              <el-icon><ChatDotRound /></el-icon>
              <span>账号管理</span>
            </el-menu-item>
            <el-sub-menu>
              <template #title>
                <el-icon><setting/></el-icon>
                <span>系统设置</span>
              </template>
              <el-menu-item @click="goToMyCenter" >
                <span >个人信息</span>
              </el-menu-item>
              <el-menu-item @click="dahuang">
                <span  >关于</span>
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import { Plus, ChatDotRound, EditPen } from '@element-plus/icons-vue';
export default {
  components: {
    Plus,
    ChatDotRound,
    EditPen// 注册图标组件
  },
  data(){
    return{
      username: this.$route.query.username,
      permissions: this.$route.query.permissions
    }
  },
  methods:{
    logout(){
      this.$router.push('/')
    },
    bookShow(){
      this.$router.push({
        path: '/Home/book',
        query: {
          username: this.username,
        },
      });
    },
    goToMyCenter() {
      console.log("username:", this.username); // 打印 username
      console.log("permissions:", this.permissions); // 打印 permissions
      this.$router.push({
        path: '/Home/myCenter',
        query: {
          username: this.username,
          permissions: this.permissions
        },
      });
    },
    bookProcessing(){
      this.$router.push({
        path: '/Home/bookprocessing',
      });
    },
    bookClassification(){
      this.$router.push({
        path: '/Home/bookClassification',
      });
    },
    bookNotification(){
      this.$router.push({
        path: '/Home/bookNotification',
      });
    },
    accountManagement(){
      this.$router.push({
        path: '/Home/accountManagement',
      })
    },
    messagesNotification(){
      this.$router.push({
        path: '/Home/messageNotification',
        query: {
          username: this.username,
        },
      })
    }
  }
}
</script>
<style scoped>
.containers{
  height: 100vh;
}
.headers{
  height: 80px;
  background-color: rgb(1, 34, 67);
  color:#fff;
  font-size:24px;
  font-weight:bold;
  line-height:60px;
  display: flex;
}
.el-menu--horizontal {
  --el-menu-horizontal-height: 100px;
}
.title{
  display: flex;
  width: 600px;
  font-size: 35px;
  color: white;
  align-items: center;
}
.welcome{
  display: flex;
  width: 400px;
  color: white;
  align-items: center;
  justify-content: right;
  margin-right: 30px;
}
.el-dropdown{
  color: white;
  cursor: pointer;}
.aside{
  width: 220px;
  border-right: 1px solid #ccc;
}
</style>
<script setup>
import {
  ArrowDown,
  Setting,
  User,
  View
} from "@element-plus/icons-vue";
import messageNotification from "@/components/messageNotification.vue";

function dahuang() {
  window.alert("什么也没有呢！");
}
</script>