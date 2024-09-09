<template>
  <el-container style="height: 100vh; justify-content: center; align-items: center;">
    <el-card class="register-card" shadow="hover">
      <h2 class="title">注册</h2>
      <el-form
          :model="form"
          :rules="rules"
          ref="registerForm"
          label-width="auto"
          label-position="left"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
              v-model="form.password"
              type="password"
              placeholder="请输入密码"
          />
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
              v-model="form.confirmPassword"
              type="password"
              placeholder="请确认密码"
          />
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>

        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>

        <el-form-item label="身份证" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="0" :max="150" />
        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option label="男" :value="0" />
            <el-option label="女" :value="1" />
            <el-option label="不愿透露" :value="2" />
          </el-select>
        </el-form-item>


      </el-form>
      <div style="display: flex; justify-content: center; margin-bottom: 5px;">
        <el-button type="primary" @click="onSubmit">注册</el-button>
        <el-button @click="onReset">重置</el-button>
      </div>
      <div style="display: flex; justify-content: center;">
        <el-button type="text" @click="goToLogin">返回登录</el-button>
      </div>

    </el-card>
    <el-footer style="height: 3vh; opacity: 100%;text-align: center; font-size: small" class="el-footer">Copyright© 2024 My WebSites</el-footer>

  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus';
import { userApi } from '../../api/user-api.js';
import { router } from "../../router/index.js";

// 定义表单数据
const form = ref({
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  phone: '',
  idCard: '',
  age: null,
  sex: null,
});

// 定义表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 15, message: '用户名长度在3到15个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: (rule, value, callback) => {
        if (value !== form.value.password) {
          callback(new Error('两次输入的密码不一致'));
        } else {
          callback();
        }
      },
      trigger: 'blur'
    },
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] },
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' },
  ],
  idCard: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { pattern: /^\d{15}|\d{18}$/, message: '请输入正确的身份证号', trigger: 'blur' },
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'change' },
    { type: 'number', min: 0, max: 150, message: '年龄在0到150之间', trigger: 'change' },
  ],
  sex: [
    { required: true, message: '请选择性别', trigger: 'change' },
  ],
};

const registerForm = ref(null);

const onSubmit = () => {
  registerForm.value.validate((valid) => {
    if (valid) {
      const submitData = { ...form.value };
      userApi.registry(submitData)
          .then(() => {
            ElMessage.success('注册成功！3秒后自动跳转');
            router.push('/login').then(() => {
              setTimeout(() => {
                window.location.reload(); // 强制刷新
              }, 3000); // 等待3秒
            });
          })
          .catch(error => {
            ElMessage.error('注册失败，请重试。');
            console.error(error);
          });
    } else {
      ElMessage.warning('请完善表单信息');
      return false;
    }
  });
};

const onReset = () => {
  registerForm.value.resetFields();
};

const goToLogin = () => {
  router.push('/login').then(() => {
      //window.location.reload();
  });
};
</script>

<style scoped>
.register-card {
  width: 400px;
  padding: 20px;
}

.title {
  text-align: center;
  margin-bottom: 20px;
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
