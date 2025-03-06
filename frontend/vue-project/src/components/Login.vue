<template>
  <div class="login">
    <el-form class="login-form" @submit.prevent="handleLogin">
      <el-form-item>
        <el-input class="username" type="text" v-model="loginData.username" placeholder="输入用户名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input class="password" type="password" v-model="loginData.password" placeholder="输入密码"></el-input>
      </el-form-item>
      <el-form-item class="captcha-item">
        <el-input class="captcha-input" v-model="loginData.captcha" placeholder="输入验证码"></el-input>
        <img :src="captchaImage" @click="getCaptcha" alt="验证码" class="captcha-image" />
      </el-form-item>
      <el-form-item class="radio-item">
        <el-radio-group v-model="loginData.permissions" class="radio-group">
          <el-radio label="1" style="color: #070719">用户</el-radio>
          <el-radio label="0" style="color: #070719">管理员</el-radio>
          <el-radio label="6" style="color:  #070719;font-size: 90px">超管</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="success" round @click="handleLogin" class="denglu">登录</el-button>
      </el-form-item>
      <div class="bottom-links">
        <p @click="goToRegister" style="color: blue">没有账号？去注册</p>
        <p @click="goToForgotPassword"  style="color: blue">找回密码</p>
      </div>
    </el-form>
  </div>
</template>

<script>
import myaxios from "@/axios/myaxios.js";
import router from "@/router/index.js";
import { ElMessage } from "element-plus";
import axios from "axios";

export default {
  data() {
    return {
      loginData: {
        username: null,
        password: null,
        permissions: "",
        captcha:null// 单选，使用字符串而不是数组
      },
      captchaImage: "", // 验证码图片
      captchaId: "" // 验证码唯一标识
    };
  },
  created() {
    this.getCaptcha();// 初始化时加载验证码
  },
  methods: {
    async getCaptcha() {
      try {
        const response = await axios.get("http://localhost:8080/login/captcha");
        this.captchaImage = `data:image/png;base64,${response.data.captcha}`;
        this.captchaId = response.data.captchaId;
      } catch (error) {
        console.log("验证码返回数据:", this.response.data);
        ElMessage({
          message: "获取验证码失败！",
          type: "error", // 设置为 error 类型，显示红色警告框
          duration: 3000 // 3 秒后自动关闭
        });
      }
    },
    async handleLogin() {
      if (this.loginData.username == null || this.loginData.password == null) {
        ElMessage({
          message: "请输入用户名或密码",
          type: "error", // 设置为 error 类型，显示红色警告框
          duration: 3000 // 3 秒后自动关闭
        });
      } else {
        console.log("当前 permissions 值:", this.loginData.permissions); // 打印 permissions 的值

        // 验证验证码
        const verifyResponse = await myaxios.post("http://localhost:8080/login/verifyCaptcha", {
          captchaId: this.captchaId,
          captchaInput: this.loginData.captcha
        });

        if (!verifyResponse.data.success) {
          // 验证码错误或已失效
          ElMessage({
            message: "验证码不正确，请重新输入！",
            type: "error", // 设置为 error 类型，显示红色警告框
            duration: 3000 // 3 秒后自动关闭
          });
          this.getCaptcha(); // 刷新验证码
          return;
        }

        // 验证码验证成功，继续登录逻辑
        console.log("登录数据:", this.loginData);
        if (this.loginData.permissions) {
          // 调用登录接口
          const loginResponse = await myaxios.post("http://localhost:8080/login", this.loginData);
          console.log("登录响应:", loginResponse.data);
          if (loginResponse.data.data === "True") {
            router.push({
              path: "/Home",
              query: {
                username: this.loginData.username,
                permissions: this.loginData.permissions
              }})
           } else {
            ElMessage({
              message: "用户名或密码错误",
              type: "error", // 设置为 error 类型，显示红色警告框
              duration: 2000 // 2 秒后自动关闭
            });
          }
        } else {
          ElMessage({
            message: "请选择角色",
            type: "error", // 设置为 error 类型，显示红色警告框
            duration: 2000 // 2 秒后自动关闭
          });
        }
      }
    },
    goToRegister() {
      router.push("/register");
    },
    goToForgotPassword() {
      router.push("/forgot-password"); // 跳转到找回密码页面
    }
  }
};
</script>

<style>
.login {
  background-image: url("../assets/img/login.jpg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  height: 98vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-form {
  width: 430px;
  padding: 30px;
  background-color: rgba(255, 255, 255, 0.1); /* 完全透明 */
  border: 1px solid rgba(255, 255, 255, 0.5); /* 半透明边框 */
  border-radius: 20px; /* 更大的圆角 */
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1); /* 更柔和的阴影 */
  margin-left: 400px;
  margin-bottom: 55px;
}

.username,
.password,
.captcha-input {
  width: 100%;
  height: 40px;
  margin-top: 10px; /* 调整间距 */
  border-radius: 10px; /* 输入框圆角 */
  background-color: rgba(255, 255, 255, 0.1); /* 输入框半透明 */
  border: 1px solid rgba(255, 255, 255, 0.3); /* 输入框边框半透明 */
  color: #070719; /* 输入文字颜色 */
}

.username::placeholder,
.password::placeholder,
.captcha-input::placeholder {
  color: rgba(7, 7, 25, 0.7); /* 占位符文字颜色 */
}

.captcha-item {
  display: flex;
  align-items: center;
  margin-top: 10px; /* 调整间距 */
}

.captcha-input {
  flex: 1;
  margin-right: 10px;
  border-radius: 10px;
  margin-top: 2px;
}

.captcha-image {
  height: 40px;
  cursor: pointer;
  border: 1px solid rgba(255, 255, 255, 0.3); /* 验证码图片边框半透明 */
  border-radius: 10px; /* 验证码图片圆角 */
}

.radio-item {
  margin-top: 15px; /* 调整间距 */
}

.radio-group {
  display: flex;
  justify-content: space-around;
  margin-left: 100px;
}

.el-button {
  width: 100%;
  height: 40px;
  background-color: rgba(102, 153, 204, 0.8); /* 按钮半透明 */
  color: white;
  border: none;
  border-radius: 10px; /* 按钮圆角 */
  transition: background-color 0.3s ease;
  margin-top: 20px; /* 调整间距 */
}

.el-button:hover {
  background-color: rgba(80, 121, 163, 0.8); /* 更深的 hover 颜色 */
}

.bottom-links {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.bottom-links p {
  color: rgba(102, 153, 204, 0.8); /* 链接半透明 */
  cursor: pointer;
  margin: 0;
  font-size: 14px; /* 调整字体大小 */
}

.bottom-links p:hover {
  text-decoration: underline; /* hover 时显示下划线 */
}
</style>