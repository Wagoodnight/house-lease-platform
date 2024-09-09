<template>
  <div id="app" class="login-container">
    <el-card class="login-box" shadow="hover" style="z-index: 1">
      <el-form :model="loginForm" label-width="auto" :rules="rules" class="login-form">
        <h2 class="title">登录</h2>
        <el-form-item  prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item  prop="password">
          <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login" :loading="isLoading" class="login-button">登录</el-button>
          <el-button type="text" @click="goToRegister" class="register-button" style="margin-left: 0">没有账号？去注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-footer style="height: 3vh; opacity: 100%;text-align: center; font-size: small" class="el-footer">Copyright© 2024 My WebSites</el-footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { userApi } from '../../api/user-api.js'
import {router} from "../../router/index.js";
let isLoading = ref(false);

const loginForm = ref({
  username: null,
  password: null
})

const rules = ref({
  username: [
    {required: true, message: '用户名不能为空！', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '密码不能为空！', trigger: 'password'}
  ]
})

async function login() {
  isLoading.value = true;
  try {
    let loginResult = await userApi.login(loginForm.value);
    const token = loginResult.token;
    localStorage.setItem('authToken', token);
    isLoading.value = false;
    await router.push('/dashboard');
  } catch (error) {
    setTimeout(() => {
      if(error.code === '1005') {
        isLoading.value = false;
      }
    },5000)
  }

}

function goToRegister() {
  router.push('/register');
}

</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  z-index: 1;
}

.login-box {
  width: 300px;
  padding: 40px 30px;
  background-color: rgba(255, 255, 255, 0.9);
}

.title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.login-form {
  max-width: 250px;
  margin: auto;
}

.login-button {
  width: 100%;
  margin-top: 10px;
}

.register-button {
  margin-top: 15px;
  text-align: center;
  width: 100%;
  color: #409EFF;
}

.el-footer {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 3vh;
  text-align: center;
  font-size: small;
  opacity: 100%;
}
</style>
