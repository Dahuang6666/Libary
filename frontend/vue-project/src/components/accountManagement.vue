<template>
  <div class="account-management">
    <!-- 账号展示表格 -->
    <el-table :data="accounts" border stripe style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" align="center" />
      <el-table-column prop="username" label="用户名" min-width="150" align="center" />
      <el-table-column prop="email" label="邮箱" min-width="200" align="center" />
      <el-table-column prop="permissions" label="权限" min-width="120" align="center">
        <template #default="{ row }">
          <el-tag :type="getPermissionsTag(row.permissions)">
            {{ getPermissionsText(row.permissions) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template #default="{ row }">
          <div class="action-buttons">
            <el-button type="primary" size="small" @click="openUpdateDialog(row)">修改账户</el-button>
            <el-button type="danger" size="small" @click="handleDeleteAccount(row.id)">删除账户</el-button>
            <el-button type="warning" size="small" @click="handleResetPassword(row)">重置密码</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>


    <el-dialog v-model="updateDialogVisible" width="400px" @close="resetUpdateForm">
      <div class="dialog-content">
        <h3 class="dialog-title">修改账户</h3>
        <el-form label-position="left">
          <el-form-item label="用户名">
            <el-input v-model="currentAccount.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="currentAccount.email" placeholder="请输入邮箱" />
          </el-form-item>
          <el-form-item label="权限">
            <el-select v-model="currentAccount.permissions" placeholder="请选择权限">
              <el-option label="用户" :value="1" />
              <el-option label="管理员" :value="0" />
              <el-option label="超管" :value="6" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="updateDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleUpdateAccount">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      accounts: [], // 存储账号数据
      loading: false,
      updateDialogVisible: false, // 控制修改弹窗显示
      currentAccount: { // 当前修改的账户
        id: "",
        username: "",
        email: "",
        permissions: null,
      },
    };
  },
  methods: {
    // 获取账号列表
    async fetchAccounts() {
      this.loading = true;
      try {
        const response = await axios.get("http://localhost:8080/admin");
        this.accounts = response.data.data;
      } catch (error) {
        console.error("获取账号数据失败:", error);
      } finally {
        this.loading = false;
      }
    },

    // 获取权限文本
    getPermissionsText(permissions) {
      if (permissions === 0) return "管理员";
      if (permissions === 1) return "用户";
      if (permissions === 6) return "超管";
      return "未知";
    },

    // 获取权限标签样式
    getPermissionsTag(permissions) {
      if (permissions === 0) return "danger";
      if (permissions === 1) return "success";
      if (permissions === 6) return "warning";
      return "info";
    },

    // 打开修改账户弹窗
    openUpdateDialog(account) {
      this.currentAccount = { ...account }; // 复制当前账户数据
      this.updateDialogVisible = true;
    },

    // 重置密码
    async handleResetPassword(account) {
      try {
        const response = await axios.post("http://localhost:8080/admin/password", {
          id: account.id,
          permissions: account.permissions,
        });
        this.$message.success("密码已重置");
      } catch (error) {
        console.error("重置密码失败:", error);
        this.$message.error("密码重置失败");
      }
    },

    // 修改账户
    async handleUpdateAccount() {
      try {
        const response = await axios.put("http://localhost:8080/admin/update", this.currentAccount);
        this.$message.success("账户修改成功");
        this.updateDialogVisible = false;
        this.fetchAccounts(); // 刷新账户列表
      } catch (error) {
        console.error("修改账户失败:", error);
        this.$message.error("修改失败");
      }
    },

    // 删除账户
    async handleDeleteAccount(id) {
      try {
        await axios.delete(`http://localhost:8080/admin/deleteLogin/${id}`);
        this.$message.success("账户删除成功");
        this.fetchAccounts(); // 刷新账户列表
      } catch (error) {
        console.error("删除账户失败:", error);
        this.$message.error("删除失败");
      }
    },

    // 重置表单
    resetUpdateForm() {
      this.currentAccount = {id: "", username: "", email: "", permissions: null}; // 重置账户数据
    },
  },

  mounted() {
    this.fetchAccounts(); // 加载账号数据
  },
};
</script>

<style scoped>
.account-management {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
}

.dialog-content {
  padding: 10px 20px;
}

.dialog-title {
  text-align: center;
  margin-bottom: 20px;
}

.el-table .cell {
  text-align: center;
}

.dialog-footer {
  display: flex;
  justify-content: space-between;
  padding: 0 20px 15px;
  margin-top: -10px;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 10px; /* 按钮之间的间距 */
}

.action-buttons .el-button {
  margin: 0; /* 移除默认的 margin */
  flex: 1; /* 让按钮均匀分布 */
}

.el-button {
  width: 100%;
  margin-top: 10px;
}
</style>