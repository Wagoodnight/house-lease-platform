<template>
  <el-table :data="tableData" v-loading="isLoading" style="width: 100%" :max-height="tableMaxHeight" ref="MyTable">
    <el-table-column prop="username" label="用户名" :width="MyTableColumn.usernameWidth"/>
    <el-table-column prop="sex" label="性别" :width="MyTableColumn.sexWidth"/>
    <el-table-column prop="age" label="年龄" :width="MyTableColumn.ageWidth" sortable/>
    <el-table-column prop="role" label="权限" :width="MyTableColumn.ageWidth"/>
    <el-table-column prop="published" label="房源" :width="MyTableColumn.ageWidth"/>
    <el-table-column prop="rentalRequest" label="求购" :width="MyTableColumn.ageWidth"/>
    <el-table-column prop="email" label="邮箱" :width="MyTableColumn.emailWidth"/>
    <el-table-column prop="phone" label="电话" :width="MyTableColumn.emailWidth"/>
    <el-table-column fixed="right" label="操作" :min-width="MyTableColumn.rightWidth">
      <template #default="scope">
        <el-button
            link
            type="primary"
            size="small"
            @click.prevent="viewDetails(scope.row.userId)"
        >
          详情
        </el-button>
        <el-button
            link
            type="primary"
            size="small"
            @click.prevent="viewEditorDetails(scope.row.userId)"
        >
          编辑
        </el-button>
        <el-button
            link
            type="primary"
            size="small"
            @click="deleteUser(scope.row.userId)"
            style="color: red"
        >
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-dialog v-model="viewDialog" title="详情" width="65%" center>
    <div style="margin: 10px; line-height: 2; font-weight: bold;" v-loading.fullscreen.lock="detailIsLoading">
      <el-text class="mx-1" type="info">用户名&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.username }}<br></el-text>
      <el-text class="mx-1" type="info">年龄&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.age }}<br></el-text>
      <el-text class="mx-1" type="info">性别&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.sex }}<br></el-text>
      <el-text class="mx-1" type="info">电话&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.phone }}<br></el-text>
      <el-text class="mx-1" type="info">权限&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.role }}<br></el-text>
      <el-text class="mx-1" type="info">房源&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.published }}<br></el-text>
      <el-text class="mx-1" type="info">求租&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.rentalRequest }}<br></el-text>
      <el-text class="mx-1" type="info">身份证&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.idCard }}<br></el-text>
      <el-text class="mx-1" type="info">用户ID&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.userId }}<br></el-text>
      <el-text class="mx-1" type="info">登陆时间&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.lastLoginTime }}<br></el-text>
      <el-text class="mx-1" type="info">创建时间&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.createTime }}<br></el-text>
      <el-text class="mx-1" type="info">更新时间&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ UserDetail.updateTime }}<br></el-text>
    </div>
  </el-dialog>

  <el-dialog v-model="viewEdit" title="编辑" width="65%" center>
    <div style="display: flex; justify-content: center;" v-loading.fullscreen.lock="editorIsLoading">
      <el-form :model="UpdateData" ref="ruleFormRef" :inline="true" label-width="auto" style="width: 735px "
               class="form-inline">
        <div>
          <el-form-item label="房源" prop="published">
            <el-radio-group v-model.number="UpdateData.published">
              <el-radio :label="1">允许</el-radio>
              <el-radio :label="0">拒绝</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="求租" prop="rentalRequest">
            <el-radio-group v-model.number="UpdateData.rentalRequest">
              <el-radio :label="1">允许</el-radio>
              <el-radio :label="0">拒绝</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="权限" prop="role">
            <el-select v-model="UpdateData.role" style="width: 100px" placeholder="请选择权限">
              <el-option label="游客" value="anonymous"/>
              <el-option label="会员" value="user"/>
              <el-option label="管理员" value="admin"/>
            </el-select>
          </el-form-item>
        </div>
        <el-button style="margin-left: 79px;margin-top: 30px" type="primary" @click="handleSubmit(ruleFormRef)">提交</el-button>
      </el-form>
    </div>

  </el-dialog>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {userApi} from "../../api/user-api.js";
import {useResizeObserver} from "@vueuse/core";

const MyTable = ref();
let viewEdit = ref(false);
let isLoading = ref(false);
let viewDialog = ref(false);
let detailIsLoading = ref(false);
let editorIsLoading = ref(false);
let tableData = ref([]);
let tableMaxHeight = '73vh';
const ruleFormRef = ref();

let postData = ref({
  age: null,
  currentPage: 1,
  email: "",
  endTime: "",
  idCard: "",
  pageSize: 15,
  phone: "",
  published: null,
  rentalRequest: null,
  role: null,
  sex: null,
  startTime: "",
  userId: null,
  username: ""
})

let IPageRequest = ref({
  total: 1,
  size: null,
  current: null,
  pages: null
});

let MyTableColumn = ref({
  usernameWidth: 140,
  ageWidth: 90,
  emailWidth: 220,
  sexWidth: 60,
  rightWidth: 140
})

const UserDetail = ref({
  age: null,
  createTime: "",
  email: "",
  idCard: "",
  lastLoginTime: "",
  phone: "",
  published: null,
  rentalRequest: null,
  role: "",
  sex: null,
  updateTime: "",
  userId: null,
  userImg: "",
  username: ""
})

const UpdateData = ref({
  published: null,
  rentalRequest: null,
  role: "",
  userId: null,
})

onMounted(() => {
  handleClick();
})

useResizeObserver(MyTable, (entries) => {
  const entry = entries[0]
  const {width} = entry.contentRect
  if(width >= 1440) {
    MyTableColumn.value.usernameWidth = Math.round(140 * width / 1140)
    MyTableColumn.value.ageWidth = Math.floor(90 * width / 1140)
    MyTableColumn.value.emailWidth = Math.round(220 * width / 1140)
    MyTableColumn.value.sexWidth = Math.floor(60 * width / 1140)
    MyTableColumn.value.rightWidth = Math.round(140 * width / 1140)
  }
})

const viewDetails = async (index) => {
  viewDialog.value = true;
  detailIsLoading.value = true;
  UserDetail.value = {
    age: null,
    createTime: "",
    email: "",
    idCard: "",
    lastLoginTime: "",
    phone: "",
    published: null,
    rentalRequest: null,
    role: "",
    sex: null,
    updateTime: "",
    userId: null,
    userImg: "",
    username: ""
  };
  try {
    const detail = await userApi.userDetail(index);
    UserDetail.value = {
      ...detail,
      role: detail.role === "admin" ? "管理员" : detail.role === "user" ? "会员" : "游客",
      published: detail.published === 1 ? '是' : '否',
      rentalRequest: detail.rentalRequest === 1 ? '是' : '否',
      sex: detail.sex === 0 ? "男" : detail.sex === 1 ? "女" : "其他"
    }
  } catch (error) {
    ElMessage.error("获取信息失败")
    console.error("An error occurred while fetching data:", error);
  } finally {
    detailIsLoading.value = false;
  }
};

const viewEditorDetails = async (index) => {
  viewEdit.value = true;
  editorIsLoading.value = true;
  UpdateData.value = {
    published: null,
    rentalRequest: null,
    role: "",
    userId: null,
  }
  try {
    const detail = await userApi.userDetail(index);
    UpdateData.value = {
      published: detail.published,
      rentalRequest: detail.rentalRequest,
      role: detail.role,
      userId: detail.userId
    }
  } catch (error) {
    ElMessage.error("获取信息失败")
    console.error("An error occurred while fetching data:", error);
  } finally {
    editorIsLoading.value = false;
  }
}

const deleteUser = async (index) => {
  ElMessageBox.confirm(
      '确认删除？',
      'Warning',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(async () => {
        await userApi.delUser(index);
        await handleClick();
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除',
        })
      })
}

const handleSubmit = async (formEl) => {
  if (!formEl) return;
  formEl.validate(async valid => {
    if(valid) {
      ElMessageBox.confirm(
          '确认提交？',
          'Warning',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(async () => {
            await userApi.updateUser(UpdateData.value);
            formEl.resetFields()
            await handleClick()
            viewEdit.value = false;
            ElMessage({
              type: 'success',
              message: '更新成功',
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '取消提交',
            })
          })
    } else {
      dataEmpty();
      return false;
    }
  })
}

const dataEmpty = () => {
  ElMessage({
    showClose: true,
    message: '请完善表单！',
    type: 'error',
    duration: 10000
  })
}

const fetchTableData = async () => {
  try {
    const result = await userApi.userPage(postData.value);
    IPageRequest.value.total = result.total;
    IPageRequest.value.size = result.size;
    IPageRequest.value.current = result.current;
    IPageRequest.value.pages = result.pages;
    tableData.value = result.records.map(record => ({
      ...record,
      userId: String(record.userId), // 确保 userId 以字符串处理
      role: record.role === "admin" ? "管理员" : record.role === "user" ? "会员" : "游客",
      published: record.published === 1 ? '是' : '否',
      rentalRequest: record.rentalRequest === 1 ? '是' : '否',
      sex: record.sex === 0 ? "男" : record.sex === 1 ? "女" : "其他"
    }));
  } catch (error) {
    ElMessage.error("获取数据失败");
    console.error("An error occurred while fetching data:", error);
  }
};

const handleClick = async () => {
  isLoading.value = true;
  try {
    await fetchTableData();
  } finally {
    isLoading.value = false;
  }
};

</script>

<style scoped>

</style>