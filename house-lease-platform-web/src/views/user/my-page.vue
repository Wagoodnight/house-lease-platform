<template>
  <el-card class="my-space-card" shadow="hover">
    <el-form :model="mySpaceData" label-width="100px">

      <div class="avatar-container">
        <el-avatar :size="150" :src="mySpaceData.userImg"></el-avatar>
      </div>
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

    </el-form>
  </el-card>

</template>

<script setup>
import {inject, onMounted} from "vue";

// 使用 inject 获取数据和函数
const mySpaceData = inject('mySpaceData');
const viewSpace = inject('viewSpace');

onMounted(() => {
  viewSpace();
});
</script>

<style scoped>
.my-space-card {
  max-width: 600px;
  margin: 20px auto;
}

.avatar-container {
  display: flex;       /* 使用flex布局 */
  justify-content: center; /* 水平居中 */
  align-items: center; /* 如果需要垂直居中也可以加上这个属性 */
  height: 100%;       /* 确保父容器有高度 */
  width: 100%;        /* 确保父容器有宽度 */
  margin-bottom: 10px;
}
</style>
