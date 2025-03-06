<template>
  <div class="personal-center">
    <!-- 头像和用户名 -->
    <div class="avatar-section">
      <img :src="avatarUrl" alt="用户头像" class="avatar" />
      <h2>{{ username }}</h2>
      <p>{{ role }}</p>
      <el-upload
          class="avatar-uploader"
          action="http://localhost:8080/user/upload-avatar"
          :data="{ username: username }"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :show-file-list="false"
      >
        <el-button type="primary">上传头像</el-button>
      </el-upload>
    </div>

    <!-- 修改密码 -->
    <div class="password-section">
      <h3>修改密码和用户名</h3>
      <el-form :model="loginForm" label-width="100px">
        <el-form-item label="新用户名">
          <el-input
              v-model="loginForm.newUsername"
              type="text"
              placeholder="请输入新用户名"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input
              v-model="loginForm.newPassword"
              type="password"
              placeholder="请输入新密码"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="update">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ElMessage } from "element-plus";

export default {
  data() {
    return {
      id: "", // 用户的 ID
      username: this.$route.query.username, // 从路由获取用户名
      permissions: this.$route.query.permissions, // 从路由获取角色
      avatarUrl: "", // 头像 URL
      loginForm: {
        newUsername: "",
        newPassword: "", // 新密码
      },
    };
  },
  computed: {
    // 根据 permissions 计算角色
    role() {
      console.log("per$$$:"+this.permissions)
      return this.permissions === "1"
          ? "用户"
          : this.permissions === "0"
              ? "管理员"
              : "超管";
    },
  },
  created() {
    this.loadAvatar();
    this.getUserId(); // 调用方法根据用户名获取用户 ID
  },
  methods: {
    // 加载头像
    async loadAvatar() {
      try {
        const response = await axios.get("http://localhost:8080/user/avatar", {
          params: {
            username: this.username,
          },
        });
        this.avatarUrl = response.data.data; // 假设返回的 URL 在 data 字段中
      } catch (error) {
        ElMessage.error("加载头像失败");
      }
    },
    // 获取用户 ID
    async getUserId() {
      try {
        const response = await axios.get(`http://localhost:8080/login/${this.username}`);
          console.log(response.data.data);
          this.id = response.data.data; // 假设返回的数据中包含 id

      } catch (error) {
        ElMessage.error("请求失败，请重试");
      }
    },
    // 修改
    async update() {
        this.password = this.loginForm.newPassword;
        this.username = this.loginForm.newUsername;
      try {
        const response = await axios.post("http://localhost:8080/login/update", {
          id: this.id,
          username: this.username,
          password: this.password,
          permissions: this.permissions,
        });
        console.log(this.username);
        console.log(this.username);

        if (response.data.data==="True") {
          ElMessage.success("修改成功");
        } else {
          console.log("Data:"+response.data);
          ElMessage.error("修改失败");

        }
      } catch (error) {
        ElMessage.error("请求失败，请重试");
      }
    },
    // 头像上传成功
    handleAvatarSuccess(response) {
      if (response.data === "True") {
        ElMessage.success("头像上传成功");
        this.loadAvatar(); // 重新加载头像
      } else {
        ElMessage.error("头像上传失败");
      }
    },

    // 头像上传前的校验
    beforeAvatarUpload(file) {
      const isImage = file.type.startsWith("image/");
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        ElMessage.error("只能上传图片文件");
      }
      if (!isLt2M) {
        ElMessage.error("图片大小不能超过 2MB");
      }

      return isImage && isLt2M;
    },
  },
};
</script>


<style scoped>
.personal-center {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.avatar-section {
  text-align: center;
  margin-bottom: 20px;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}

.password-section {
  margin-top: 20px;
}

.avatar-uploader {
  margin-top: 10px;
}
</style>