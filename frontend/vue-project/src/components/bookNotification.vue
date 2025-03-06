<template>
  <div class="borrow-list">
    <!-- 切换选项 -->
    <div class="tab-buttons">
      <el-button :type="activeTab === 'undisposed' ? 'primary' : 'default'" @click="setTab('undisposed')">
        未处理
      </el-button>
      <el-button :type="activeTab === 'disposed' ? 'primary' : 'default'" @click="setTab('disposed')">
        已处理
      </el-button>
    </div>

    <!-- 借书请求表格 -->
    <el-table :data="borrowRequests" border stripe style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" align="center" />
      <el-table-column prop="userName" label="借书人" min-width="150" align="center" />
      <el-table-column prop="bookName" label="书名" min-width="200" align="center" />
      <el-table-column prop="requestTime" label="申请时间" min-width="180" align="center" />
      <el-table-column prop="status" label="状态" min-width="120" align="center">
        <template #default="{ row }">
          <el-tag :type="getStatusTag(row.status)">{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center" v-if="activeTab === 'undisposed'">
        <template #default="{ row }">
          <div class="action-buttons">
            <el-button type="success" size="small" @click="handleUpdateStatus(row.id, 1)">同意</el-button>
            <el-button type="danger" size="small" @click="handleUpdateStatus(row.id, 2)">拒绝</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      activeTab: "undisposed",
      borrowRequests: [],
      loading: false,
    };
  },
  methods: {
    setTab(tab) {
      this.activeTab = tab;
      this.fetchBorrowRequests();
    },
    async fetchBorrowRequests() {
      this.loading = true;
      const url = this.activeTab === "undisposed"
          ? "http://localhost:8080/admin/borrow/undisposed"
          : "http://localhost:8080/admin/borrow/dispose";
      try {
        const response = await axios.get(url);
        this.borrowRequests = response.data.data;
      } catch (error) {
        console.error("获取借书请求失败:", error);
      } finally {
        this.loading = false;
      }
    },
    async handleUpdateStatus(id, status) {
      try {
        await axios.post("http://localhost:8080/admin/borrow", { id, status });
        this.$message.success("操作成功");
        this.fetchBorrowRequests();
      } catch (error) {
        console.error("更新借书请求状态失败:", error);
        this.$message.error("操作失败");
      }
    },
    getStatusText(status) {
      return status === 0 ? "审核中" : status === 1 ? "同意" : "拒绝";
    },
    getStatusTag(status) {
      return status === 0 ? "warning" : status === 1 ? "success" : "danger";
    }
  },
  mounted() {
    this.fetchBorrowRequests();
  },
};
</script>

<style scoped>
.borrow-list {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
}

.tab-buttons {
  display: flex;
  justify-content: flex-start;
  gap: 10px;
  margin-bottom: 20px;
}
/* 确保按钮横向排列并适当留白 */
.action-buttons {
  display: flex;
  justify-content: center; /* 居中 */
  gap: 10px; /* 按钮间距 */
}
</style>
