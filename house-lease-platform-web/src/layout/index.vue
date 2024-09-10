<template>
  <div class="common-layout">
    <el-container style="height: 100vh">
      <el-aside width="200px" style="opacity: 100%; background-color: #545c64">
        <el-scrollbar>
          <el-menu :default-openeds="['1', '2', '3', '4']" @select="menuSelect" active-text-color="#ffd04b" background-color="#545c64" text-color="#fff">
            <el-menu-item index="0">
              <el-icon><House/></el-icon>主页
            </el-menu-item>
            <el-sub-menu index="1" v-if="AutoLogin.published">
              <template #title>租房</template>
              <el-menu-item index="1-1">
                <el-icon><Search /></el-icon>查找房源
              </el-menu-item>
              <el-menu-item index="1-2">
                <el-icon><Edit /></el-icon>发布房源
              </el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="2" v-if="AutoLogin.rentalRequest">
              <template #title>求购</template>
              <el-menu-item index="2-1">
                <el-icon><Search /></el-icon>查找求购
              </el-menu-item>
              <el-menu-item index="2-2">
                <el-icon><Edit /></el-icon>发布求购
              </el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="3" v-if="AutoLogin.isUser">
              <template #title>我的</template>
              <el-menu-item index="3-1">我的信息</el-menu-item>
              <el-menu-item index="3-2">我的房源</el-menu-item>
              <el-menu-item index="3-3">我的求购</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="4" v-if="AutoLogin.isAdmin">
              <template #title>管理</template>
              <el-menu-item index="4-1">已注册用户</el-menu-item>
              <el-menu-item index="4-2">操作日志</el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-scrollbar>
      </el-aside>
      <el-container>
        <el-header style="height: 6vh; opacity: 100%; background: #F9F9F9; display: flex; justify-content: flex-end; align-items: center;">
          <el-button type="text" @click="logout">退出登录</el-button>
        </el-header>
        <div style="background: #F9F9F9;height: 94vh">
          <el-scrollbar>
            <el-container>
              <el-main style="opacity: 100%; background: #F9F9F9; min-height: 91vh">
                <router-view></router-view>
              </el-main>
              <el-footer style="height: 3vh; opacity: 100%; background: #f9f9f9; text-align: center; font-size: small">Copyright© 2024 My WebSites</el-footer>
            </el-container>
          </el-scrollbar>
        </div>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {Edit, House, Search} from '@element-plus/icons-vue';
import {router} from "../router/index.js";
import {onMounted, provide, ref} from "vue";
import {userApi} from "../api/user-api.js";

const AutoLogin = ref({
  isAdmin: false,
  isUser: false,
  published: false,
  rentalRequest: false
});

//提供这个变量给全局
provide('AutoLogin', AutoLogin);

onMounted(async () => {
  try {
    const detail = await userApi.testToken();
    AutoLogin.value = {
      isAdmin: detail.role === "admin",
      isUser: detail.role === "user" ? true : detail.role === "admin",
      published: detail.published === 1,
      rentalRequest: detail.rentalRequest === 1
    };
  } catch (error) {
    console.error('Failed to fetch data', error);
  }
  //关闭粒子渲染节省资源
  window.stopParticleRendering();
});

function menuSelect(index) {
  if(index === '1-1') {
    router.push('/sourcepage')
  } else if (index === '1-2') {
    router.push('/addsource')
  } else if (index === '2-1') {
    router.push('/requestpage')
  } else if (index === '2-2') {
    router.push('/addrequest')
  } else if (index === '3-1') {
    router.push('/myspace')
  } else if (index === '3-2') {
    router.push('/mysourcepage')
  } else if (index === '3-3') {
    router.push('/myrequestpage')
  } else if (index === '4-1') {
    router.push('/useradmin')
  } else if (index === '0') {
    router.push('/dashboard')
  } else if (index === '4-2') {
    router.push('/syslog')
  }
}

function logout() {
  localStorage.removeItem('authToken');
  router.push('/login').then(() => {
    window.location.reload();
  });
}
</script>

<style scoped>

</style>