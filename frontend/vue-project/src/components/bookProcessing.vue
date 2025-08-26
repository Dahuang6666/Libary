<template>
  <div class="book-list">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
          v-model="searchName"
          placeholder="输入书名搜索"
          clearable
      />
      <el-input
          v-model="searchAuthor"
          placeholder="输入作者搜索"
          clearable
      />
      <el-input
          v-model="searchCategory"
          placeholder="输入分类搜索"
          clearable
      />
      <el-button :icon="Search" @click="fetchBooks">搜索</el-button>
    </div>

    <!-- 书籍表格 -->
    <el-table
        :data="books"
        border
        stripe
        style="width: 100%"
        v-loading="loading"
    >
      <el-table-column prop="id" label="ID" width="80" align="center" />
      <el-table-column prop="name" label="书名" min-width="200" align="center" />
      <el-table-column prop="author" label="作者" min-width="180" align="center" />
      <el-table-column prop="count" label="剩余数量" min-width="120" align="center">
        <template #default="{ row }">
          <el-tag :type="row.count > 0 ? 'success' : 'danger'">
            {{ row.count }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="categoryName" label="分类" min-width="160" align="center" />
      <el-table-column label="操作" width="200" align="center">
        <template #default="{ row }">
          <div class="action-buttons">
            <el-button
                class="xiugai"
                size="small"
                @click="handleEdit(row)"
            >
              修改
            </el-button>
            <el-button
                class="delete-button"
                size="small"
                @click="handleDelete(row.id)"
            >
              删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加书籍按钮 -->
    <div class="add-book-button">
      <el-button type="primary" @click="handleAddBook">添加书籍</el-button>
    </div>

    <!-- 修改书籍弹窗 -->
    <el-dialog
        v-model="dialogVisible"
        width="400px"
        @close="resetForm"
    >
      <div class="dialog-content">
        <h3 class="dialog-title">修改书籍</h3>
        <el-form label-position="left">
          <!-- 书名 -->
          <el-form-item label="书名" class="info-item">
            <el-input
                v-model="currentBook.name"
                placeholder="请输入书名"
            />
          </el-form-item>

          <!-- 作者 -->
          <el-form-item label="作者" class="info-item">
            <el-input
                v-model="currentBook.author"
                placeholder="请输入作者"
            />
          </el-form-item>

          <!-- 剩余数量 -->
          <el-form-item label="剩余数量" class="info-item">
            <el-input-number
                v-model="currentBook.count"
                :min="0"
                style="width: 100%;"
            />
          </el-form-item>

          <!-- 分类 -->
          <el-form-item label="分类" class="info-item">
            <!-- 分类选择下拉框 -->
            <el-select v-model="currentBook.categoryId" placeholder="请选择分类">
              <el-option
                  v-for="category in categories"
                  :key="category.id"
                  :label="category.name"
                  :value="category.id"
              />
            </el-select>
          </el-form-item>
        </el-form>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmEdit">确定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 添加书籍弹窗 -->
    <el-dialog
        v-model="addBookDialogVisible"
        width="400px"
        @close="resetForm"
    >
      <div class="dialog-content">
        <h3 class="dialog-title">添加书籍</h3>
        <el-form label-position="left">
          <!-- 书名 -->
          <el-form-item label="书名" class="info-item">
            <el-input
                v-model="newBook.name"
                placeholder="请输入书名"
            />
          </el-form-item>

          <!-- 作者 -->
          <el-form-item label="作者" class="info-item">
            <el-input
                v-model="newBook.author"
                placeholder="请输入作者"
            />
          </el-form-item>

          <!-- 剩余数量 -->
          <el-form-item label="剩余数量" class="info-item">
            <el-input-number
                v-model="newBook.count"
                :min="0"
                style="width: 100%;"
            />
          </el-form-item>

          <!-- 分类 -->
          <el-form-item label="分类" class="info-item">
            <el-select v-model="newBook.categoryId" placeholder="请选择分类">
              <el-option
                  v-for="category in categories"
                  :key="category.id"
                  :label="category.name"
                  :value="category.id"
              />
            </el-select>
          </el-form-item>
        </el-form>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addBookDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmAddBook">确定</el-button>
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
      categories: [], // 存储分类数据
      books: [],
      searchName: "",
      searchAuthor: "",
      searchCategory: "",
      loading: false,
      dialogVisible: false,
      addBookDialogVisible: false,
      currentBook: {
        categoryId: 0,
        name: '',
        author: '',
        count: '',
        id: '',
      }, // 存储当前编辑的书籍
      newBook: {
        categoryId: 0,
        name: '',
        author: '',
        count: '',
      }, // 存储新添加的书籍
    };
  },
  methods: {
    async fetchCategories() {
      try {
        const response = await axios.get("http://localhost:8080/admin/categories");
        this.categories = response.data.data; // 假设返回的数据在 data 字段中
      } catch (error) {
        console.error("获取分类失败:", error);
      }
    },
    // 获取书籍列表
    async fetchBooks() {
      try {
        this.loading = true;
        const response = await axios.get("http://localhost:8080/admin/search", {
          params: {
            name: this.searchName,
            author: this.searchAuthor,
            categoryName: this.searchCategory,
          },
        });
        this.books = response.data.data;
      } catch (error) {
        console.error("获取书籍失败:", error);
      } finally {
        this.loading = false;
      }
    },

    // 修改书籍信息
    handleEdit(row) {
      // 根据 categoryName 查找对应的 categoryId
      const category = this.categories.find(cat => cat.name === row.categoryName);
      const categoryId = category ? category.id : null;

      // 构造 currentBook，包含正确的 categoryId
      this.currentBook = {
        id: row.id,
        name: row.name,
        author: row.author,
        count: row.count,
        categoryId: categoryId,  // ✅ 正确的分类 ID
      };

      console.log('currentBook.categoryId:', this.currentBook.categoryId);
      this.dialogVisible = true; // 打开弹窗
    },

    // 确认修改
    async confirmEdit() {
      try {
        const response = await axios.put("http://localhost:8080/admin", this.currentBook);

        if (response.status === 200) {
          this.$message.success("修改成功");
          this.dialogVisible = false;
          this.fetchBooks(); // 刷新书籍列表
        }
      } catch (error) {
        console.error("修改失败:", error);
        this.$message.error("修改失败");
      }
    },

    // 删除书籍
    async handleDelete(bookId) {
      try {
        const response = await axios.delete(`http://localhost:8080/admin/delete/${bookId}`);
        if (response.status === 200) {
          this.$message.success("删除成功");
          this.fetchBooks(); // 刷新书籍列表
        }
      } catch (error) {
        console.error("删除失败:", error);
        this.$message.error("删除失败");
      }
    },

    // 添加书籍
    handleAddBook() {
      this.newBook = {categoryId: null, name: '', author: '', count: ''}; // 重置新书数据
      this.addBookDialogVisible = true; // 打开添加书籍弹窗
    },

    // 确认添加书籍
    async confirmAddBook() {
      try {
        const response = await axios.post("http://localhost:8080/admin", this.newBook);
        if (response.status === 200) {
          this.$message.success("添加成功");
          this.addBookDialogVisible = false;
          this.fetchBooks(); // 刷新书籍列表
        }
      } catch (error) {
        console.error("添加失败:", error);
        this.$message.error("添加失败");
      }
    },

    resetForm() {
      this.currentBook = {}; // 重置表单数据
      this.newBook = {}; // 重置新书数据
    }
  },

  mounted() {
    this.fetchCategories(); // 加载分类数据
    this.fetchBooks(); // 加载书籍数据
  },
};
</script>

<style scoped>
.book-list {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
}

.search-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  gap: 10px;
}

.search-bar .el-button {
  margin-left: auto;
  margin-bottom: 22px;
}

.el-table :deep(.cell) {
  text-align: center;
}

/* 横向排列按钮 */
.action-buttons {
  display: flex;
  justify-content: space-evenly;
  gap: 10px;
}

.delete-button {
  background: #ea7970;
  border: 1px solid #070719;
}

.delete-button:hover {
  background: #fff;
  color: #ea7970;
}

.xiugai {
  background: #0e75d3;
  border: 1px solid #070719;
}

.xiugai:hover {
  background: #fff;
  color: #0e75d3;
}

/* 弹窗样式 */
.dialog-content {
  padding: 10px 20px;
}

.dialog-title {
  text-align: center;
  margin: 0 0 25px 0;
  font-size: 20px;
  color: #303133;
}

/* 信息项统一样式 */
.info-item {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
}

.info-label {
  font-size: 16px;
  color: #606266;
  width: 100px;
  text-align: right;
  margin-right: 15px;
}

.info-value {
  font-size: 16px;
  color: #303133;
  font-weight: 500;
  flex: 1;
}

.count-item :deep(.el-form-item__label) {
  font-size: 16px !important;
  color: #606266 !important;
  padding-right: 12px;
}

/* 按钮容器 */
.dialog-footer {
  display: flex;
  justify-content: space-between;
  padding: 0 20px 15px;
  margin-top: -10px;
}

.add-book-button {
  margin: 20px auto; /* 上下边距为20px，左右自动居中 */
  text-align: center;
  width: 300px;
}

</style>
