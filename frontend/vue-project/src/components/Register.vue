<template>
  <div class="register">
    <el-form class="register-form" @submit.prevent="handleRegister">
      <el-form-item>
        <el-input
            v-model="registerData.username"
            placeholder="输入用户名"
            clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input
            v-model="registerData.password"
            type="password"
            placeholder="输入密码"
            show-password
            clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input
            v-model="registerData.email"
            placeholder="输入邮箱"
            clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleRegister">注册</el-button>
      </el-form-item>
      <div class="login-link">
        <p>已有账号？<span @click="goToLogin" style="color: blue">立即登录</span></p>
      </div>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";
import { ElMessage } from "element-plus";
import router from "@/router/index.js";

export default {
  data() {
    return {
      registerData: {
        username: "",
        password: "",
        email: "",
      },
    };
  },
  methods: {
    validateForm() {
      // 表单验证
      if (!this.registerData.username) {
        ElMessage.error("用户名不能为空");
        return false;
      }
      if (!this.registerData.password) {
        ElMessage.error("密码不能为空");
        return false;
      }
      if (!this.registerData.email) {
        ElMessage.error("邮箱不能为空");
        return false;
      }
      if (!/^\w+@[a-z0-9]+\.[a-z]{2,4}$/i.test(this.registerData.email)) {
        ElMessage.error("邮箱格式不正确");
        return false;
      }
      return true;
    },

    async handleRegister() {
      if (!this.validateForm()) return;

      try {
        const response = await axios.post(
            "http://localhost:8080/login/register/user",
            this.registerData

        );
         console.log(response.data.data);
        if (response.data.data === "True") {
          ElMessage.success("注册成功");
          router.push("/"); // 跳转到登录页
        } else {
          ElMessage.error(response.data.message || "注册失败");
        }
      } catch (error) {
        console.error("注册请求失败:", error);
        ElMessage.error(
            error.response?.data?.message || "网络错误，请稍后重试"
        );
      }
    },

    goToLogin() {
      router.push("/");
    },
  },
};
</script>

<style>
.register {
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

.register-form {
  width: 450px;
  padding: 30px;
  background-color: rgba(255, 255, 255, 0.1); /* 完全透明 */
  border: 1px solid rgba(255, 255, 255, 0.5); /* 半透明边框 */
  border-radius: 20px; /* 更大的圆角 */
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1); /* 更柔和的阴影 */
  margin-left: 400px;
  margin-bottom: 60px;
}

.el-button {
  width: 100%;
  height: 40px;
  background-color: #6699cc;
  color: white;
  border: none;
  border-radius: 5px;
  transition: all 0.3s;
}

.el-button:hover {
  background-color: #5079a3;
}

.login-link {
  text-align: center;
  margin-top: 15px;
}

.login-link span {
  color: #6699cc;
  cursor: pointer;
  text-decoration: underline;
}

.login-link span:hover {
  color: #5079a3;
}

/* 输入框统一样式 */
.el-input__inner {
  height: 40px;
  border-radius: 4px;
}
</style>