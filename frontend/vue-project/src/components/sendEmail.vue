<template>
  <div class="forgot-password">
    <el-form class="forgot-password-form" @submit.prevent="handleSubmit">
      <!-- 用户名输入 -->
      <el-form-item>
        <el-input
            v-model="formData.username"
            placeholder="输入用户名"
            clearable
        ></el-input>
      </el-form-item>

      <!-- 发送验证码按钮 -->
      <el-form-item>
        <el-button type="primary" @click="sendVerificationCode" :disabled="isCodeSent">
          {{ isCodeSent ? "验证码已发送" : "发送验证码" }}
        </el-button>
      </el-form-item>

      <!-- 验证码输入 -->
      <el-form-item>
        <el-input
            v-model="formData.code"
            placeholder="输入验证码"
            clearable
        ></el-input>
      </el-form-item>

      <!-- 新密码输入 -->
      <el-form-item>
        <el-input
            v-model="formData.newPassword"
            type="password"
            placeholder="输入新密码"
            show-password
            clearable
        ></el-input>
      </el-form-item>

      <!-- 确认按钮 -->
      <el-form-item>
        <el-button type="success" @click="handleSubmit">确定</el-button>
      </el-form-item>

      <!-- 返回登录页面链接 -->
      <div class="login-link">
        <p>想起密码了？<span @click="goToLogin" style="color: blue">立即登录</span></p>
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
      formData: {
        username: "", // 用户名
        code: "", // 验证码
        newPassword: "", // 新密码
      },
      isCodeSent: false, // 是否已发送验证码
    };
  },
  methods: {
    // 表单验证
    validateForm() {
      if (!this.formData.username) {
        ElMessage.error("用户名不能为空");
        return false;
      }
      if (!this.formData.code) {
        ElMessage.error("验证码不能为空");
        return false;
      }
      if (!this.formData.newPassword) {
        ElMessage.error("新密码不能为空");
        return false;
      }
      return true;
    },

    // 发送验证码
    async sendVerificationCode() {
      if (!this.formData.username) {
        ElMessage.error("请输入用户名");
        return;
      }

      try {
        // 调用后端接口发送验证码
        const response = await axios.get("http://localhost:8080/email/send", {
          params: { // 使用params字段传递查询参数
            username: this.formData.username
          }
        });

        if (response.data.data === "True") {
          ElMessage.success("验证码已发送至您的邮箱");
          this.isCodeSent = true; // 标记验证码已发送
        } else {
          ElMessage.error("验证码发送失败");
        }
      } catch (error) {
        console.error("发送验证码失败:", error);
        ElMessage.error("验证码发送失败，请稍后重试");
      }
    },

    // 提交表单（验证验证码并修改密码）
    async handleSubmit() {
      if (!this.validateForm()) return;

      try {
        // 调用后端接口验证验证码
        const verifyResponse = await axios.post("http://localhost:8080/email/verify", {
          username: this.formData.username,
          code: this.formData.code,
        }, {
          headers: {
            'Content-Type': 'application/json' // 确保请求头正确
          }
        });
        console.log(verifyResponse.data);
        if (verifyResponse.data.data === "True") {
          // 验证码验证成功，调用更新密码接口
          const updateResponse = await axios.post("http://localhost:8080/login/update", {
            username: this.formData.username,
            password:  this.formData.newPassword,
          });
          console.log("88888888888888888888888888888");
          console.log(updateResponse.data.data);

          if (updateResponse.data.data === "True") {
            ElMessage.success("密码修改成功");
            router.push("/"); // 跳转到登录页面
          } else {
            ElMessage.error("密码修改失败");
          }
        } else {
          ElMessage.error("验证码错误或已失效");
        }
      } catch (error) {
        console.error("密码修改失败:", error);
        ElMessage.error("密码修改失败，请稍后重试");
      }
    },

    // 跳转到登录页面
    goToLogin() {
      router.push("/");
    },
  },
};
</script>

<style>
.forgot-password {
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

.forgot-password-form {
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