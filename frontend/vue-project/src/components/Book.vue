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
      <el-table-column prop="author" label="作者" width="150" align="center" />
      <el-table-column prop="count" label="剩余数量" width="120" align="center">
        <template #default="{ row }">
          <el-tag :type="row.count > 0? 'success' : 'danger'">
            {{ row.count }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="categoryName" label="分类" width="150" align="center" />
      <el-table-column label="操作" width="120" align="center">
        <template #default="{ row }">
          <el-button
              type="primary"
              size="small"
              @click="handleBorrow(row)"
              :disabled="row.count <= 0"
          >
            借书
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 借书弹窗 -->
    <el-dialog
        v-model="dialogVisible"
        width="400px"
        @close="resetForm"
    >
      <div class="dialog-content">
        <h3 class="dialog-title">借书单</h3>
        <el-form label-position="left">
          <!-- 书名 -->
          <el-form-item class="info-item">
            <span class="info-label">当前书名：</span>
            <span class="info-value">{{ currentBook.name }}</span>
          </el-form-item>

          <!-- 用户名 -->
          <el-form-item class="info-item">
            <span class="info-label">用户名：</span>
            <span class="info-value">{{ username }}</span>
          </el-form-item>

          <!-- 借书数量 -->
          <el-form-item
              label="借书数量"
              class="count-item"
              label-width="100px"
          >
            <el-input-number
                v-model="borrowCount"
                :min="1"
                :max="currentBook.count"
                style="width: 100%"
            />
          </el-form-item>
          <el-form-item
              label="备注"
              class="remark-item"
              label-width="100px"
          >
            <el-input
                v-model="message"
                type="textarea"
                :rows="3"
                placeholder="请输入备注信息"
                maxlength="100"
                show-word-limit
            />
          </el-form-item>
        </el-form>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmBorrow">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { Search } from '@element-plus/icons-vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';

// 获取传递的参数
const username = new URLSearchParams(window.location.search).get('username');

// 数据
const books = ref([]);
const searchName = ref('');
const searchAuthor = ref('');
const searchCategory = ref('');
const loading = ref(false);
const dialogVisible = ref(false);
const currentBook = ref({});
const borrowCount = ref(1);
const message = ref('');

const resetForm = () => {
  borrowCount.value = 1;
  message.value = '';  // 清空备注
};
// 搜索书籍
const fetchBooks = async () => {
  try {
    loading.value = true;
    const response = await axios.get('http://localhost:8080/user/search', {
      params: {
        name: searchName.value,
        author: searchAuthor.value,
        categoryName: searchCategory.value,
      }
    });
    books.value = response.data.data;
  } catch (error) {
    console.error('获取书籍失败:', error);
    ElMessage.error('加载数据失败');
  } finally {
    loading.value = false;
  }
};

// 借书操作
const handleBorrow = (book) => {
  console.log('借书按钮点击，当前书籍:', book);
  currentBook.value = book;
  borrowCount.value = 1;
  dialogVisible.value = true;
};

// 确定借书
// 确定借书
const confirmBorrow = async () => {
  try {
    if (borrowCount.value > currentBook.value.count) {
      ElMessage.error('借书数量不能超过剩余数量');
      return;
    }

    // 先执行减少库存的请求
    const reduceResponse = await axios.get('http://localhost:8080/user/reduceCount', {
      params: {
        id: currentBook.value.id,
        count: borrowCount.value
      }
    });

    if (reduceResponse.data.data === "True") {
      // 减少库存成功后再发送借书记录
      try {
        await axios.post('http://localhost:8080/user', {
          userName: username,
          bookName: currentBook.value.name,
          message: message.value
        });
      } catch (postError) {
        console.error('提交借书记录失败:', postError);
        ElMessage.warning('借书成功，但记录保存时遇到问题');
      }

      ElMessage.success(`成功借了 ${borrowCount.value} 本${currentBook.value.name}`);
      await fetchBooks();
    } else {
      ElMessage.error(reduceResponse.data.message || '借书失败');
    }
  } catch (error) {
    console.error('借书请求失败:', error);
    ElMessage.error('借书请求失败，请检查网络');
  } finally {
    dialogVisible.value = false;
  }
};

// 初始化加载
onMounted(() => {
  fetchBooks();
});
</script>

<style scoped>
.book-list {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
}

.search-bar {
  display: flex;
  align-items: center;
  gap: 10px;
}

.search-bar .el-button {
  margin-left: auto;
  margin-bottom: 22px;
}

.input-group {
  flex: 1;
  display: flex;
  gap: 10px;
  max-width: 800px;
}

.input-group .el-input {
  flex: 1;
  min-width: 120px;
}

.el-table :deep(.cell) {
  text-align: center;
}
.dialog-content {
  padding: 0 20px;
}

.dialog-title {
  text-align: center;
  margin: 0 0 20px 0;
  font-size: 18px;
  color: #303133;
}

.info-item {
  margin-bottom: 15px;
}

.info-label {
  font-size: 14px;
  color: #606266;
  margin-right: 10px;
}

.info-value {
  font-size: 14px;
  color: #303133;
  font-weight: 500;
}

.dialog-footer {
  display: flex;
  justify-content: space-between;
  padding: 0 20px 20px;
}

/* 调整表单元素间距 */
.el-form-item {
  margin-bottom: 15px;
}
</style>