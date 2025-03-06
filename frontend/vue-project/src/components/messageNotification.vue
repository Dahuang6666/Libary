<template>
  <div class="message-notification">
    <!-- 标题 -->
    <h2>借书进度</h2>

    <!-- 切换借书状态表格 -->
    <el-table :data="borrowProgress" border stripe style="width: 100%" v-loading="loading">
      <!-- ID列 -->
      <el-table-column prop="id" label="ID" width="80" align="center" />
      <!-- 书名列 -->
      <el-table-column prop="bookName" label="书名" min-width="200" align="center" />
      <!-- 借书人列 -->
      <el-table-column prop="userName" label="借书人" min-width="150" align="center" />
      <!-- 申请时间列 -->
      <el-table-column prop="requestTime" label="申请时间" min-width="180" align="center" />
      <!-- 借书状态列 -->
      <el-table-column prop="status" label="状态" min-width="120" align="center">
        <template #default="{ row }">
          <!-- 使用标签显示状态，颜色不同 -->
          <el-tag :type="getStatusTag(row.status)">
            {{ getStatusText(row.status) }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>

    <!-- 反馈信息 -->
    <el-message :show-close="true" :center="true" v-if="errorMessage" type="error">{{ errorMessage }}</el-message>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "messageNotification",
  data() {
    return {
      username: this.$route.query.username, // 获取传递的用户名
      borrowProgress: [], // 存储借书进度数据
      loading: false, // 控制加载动画
      errorMessage: '', // 错误信息
    };
  },
  methods: {
    async fetchBorrowProgress() {
      // 开始加载
      this.loading = true;
      this.errorMessage = ''; // 清空错误信息
      try {
        const response = await axios.get("http://localhost:8080/user/borrow", {
          params: {
            userName: this.username,
          },
        });
        // 假设返回的数据在 data 字段中
        if (response.data.data) {
          this.borrowProgress = response.data.data;
        } else {
          this.errorMessage = "未能成功获取借书数据，请稍后重试";
        }
      } catch (error) {
        console.error("获取借书进度失败:", error);
        this.errorMessage = "获取借书数据失败，请稍后重试";
      } finally {
        // 完成加载
        this.loading = false;
      }
    },
    // 根据状态获取显示的文字
    getStatusText(status) {
      if (status === 0) return "审核中";
      if (status === 1) return "同意";
      return "拒绝";
    },
    // 根据状态获取标签颜色
    getStatusTag(status) {
      if (status === 0) return "warning";
      if (status === 1) return "success";
      return "danger";
    },
  },
  mounted() {
    this.fetchBorrowProgress(); // 组件加载时获取借书进度数据
  },
};
</script>

<style scoped>
.message-notification {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

/* 错误信息显示 */
.el-message {
  margin-top: 20px;
}

.el-table .cell {
  text-align: center;
}

.el-tag {
  font-size: 14px;
}
</style>
