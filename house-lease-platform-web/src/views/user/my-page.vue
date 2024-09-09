<template>
  <el-card class="my-space-card" shadow="hover" v-loading="isLoading">
    <el-form :model="mySpaceData" label-width="100px">
      <el-form-item label="用户名">
        <el-input v-model="mySpaceData.username" readonly></el-input>
      </el-form-item>

      <el-form-item label="手机号">
        <el-input v-model="mySpaceData.phone" readonly></el-input>
      </el-form-item>

      <el-form-item label="邮箱">
        <el-input v-model="mySpaceData.email" readonly></el-input>
      </el-form-item>

      <el-form-item label="年龄">
        <el-input v-model="mySpaceData.age" readonly></el-input>
      </el-form-item>

      <el-form-item label="性别">
        <el-input v-model="mySpaceData.sex" readonly></el-input>
      </el-form-item>

      <el-form-item label="身份证">
        <el-input v-model="mySpaceData.idCard" readonly></el-input>
      </el-form-item>

      <el-form-item label="权限">
        <el-input v-model="mySpaceData.role" readonly></el-input>
      </el-form-item>

      <el-form-item label="上次登录时间">
        <el-input v-model="mySpaceData.lastLoginTime" readonly></el-input>
      </el-form-item>

      <el-form-item label="注册时间">
        <el-input v-model="mySpaceData.createTime" readonly></el-input>
      </el-form-item>


      <!-- 这里是头像占位，可以后续加上 -->
      <!-- <el-form-item label="头像">
        <el-image :src="mySpaceData.userImg" alt="用户头像" v-if="mySpaceData.userImg" />
      </el-form-item> -->

    </el-form>
  </el-card>

</template>

<script setup>
import { userApi } from "../../api/user-api.js";
import { onMounted, ref } from "vue";
let isLoading = ref(false);

const mySpaceData = ref({
  userId: null,
  username: "",
  phone: "",
  email: "",
  age: null,
  sex: null,
  idCard: "",
  role: "",
  rentalRequest: null,
  published: null,
  userImg: null,
  lastLoginTime: "",
  createTime: "",
  updateTime: ""
});

const viewSpace = async () => {
  isLoading.value = true;
  try {
    const detail = await userApi.mySpace();
    mySpaceData.value = {
      ...detail,
      role: detail.role === "admin" ? "管理员" : "会员",
      sex: detail.sex === 0 ? "男" : detail.sex === 1 ? "女" : "其他"
    };
  } catch (error) {
    console.error("An error occurred while fetching data:", error);
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  viewSpace();
});
</script>

<style scoped>
.my-space-card {
  max-width: 600px;
  margin: 20px auto;
}
</style>
