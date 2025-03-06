<template>
  <div class="category-list">
    <!-- 分类表格 -->
    <el-table :data="categories" border stripe style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" align="center" />
      <el-table-column prop="name" label="分类名称" min-width="200" align="center" />
      <el-table-column label="操作" width="150" align="center">
        <template #default="{ row }">
          <el-button size="small" @click="handleDelete(row.id)" class="delete-button">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加分类 -->
    <div class="add-category">
      <el-input v-model="newCategory" placeholder="输入分类名称" class="small-input" clearable />
      <el-button type="primary" @click="addCategory" class="small-button">添加分类</el-button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      categories: [],
      newCategory: "",
      loading: false,
    };
  },
  methods: {
    async fetchCategories() {
      try {
        this.loading = true;
        const response = await axios.get("http://localhost:8080/admin/categories");
        this.categories = response.data.data;
      } catch (error) {
        console.error("获取分类失败:", error);
      } finally {
        this.loading = false;
      }
    },
    async addCategory() {
      if (!this.newCategory.trim()) {
        this.$message.warning("请输入分类名称");
        return;
      }
      try {
        const response = await axios.post("http://localhost:8080/admin/category", { name: this.newCategory });
        if (response.status === 200) {
          this.$message.success("添加成功");
          this.newCategory = "";
          this.fetchCategories();
        }
      } catch (error) {
        console.error("添加分类失败:", error);
        this.$message.error("添加失败");
      }
    },
    async handleDelete(categoryId) {
      try {
        const response = await axios.delete(`http://localhost:8080/admin/category/${categoryId}`);
        if (response.status === 200) {
          this.$message.success("删除成功");
          this.fetchCategories();
        }
      } catch (error) {
        console.error("删除失败:", error);
        this.$message.error("删除失败");
      }
    }
  },
  mounted() {
    this.fetchCategories();
  },
};
</script>

<style scoped>
.category-list {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
}

.add-category {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 20px;
}

.small-input {
  width: 200px;
  font-size: 14px;
}

.small-button {
  padding: 5px 10px;
  font-size: 14px;
  width: 300px;
  margin-left: 20px;
  margin-bottom: 20px;
}

.delete-button {
  background: #ea7970;
  border: 1px solid #070719;
  padding: 5px 10px;
  font-size: 14px;
}

.delete-button:hover {
  background: #fff;
  color: #ea7970;
}
</style>
