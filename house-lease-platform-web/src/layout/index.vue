<template>
  <div class="common-layout">
    <el-watermark :content="[mySpaceData.userId, mySpaceData.username]" :z-index="1000" font-size="14px">
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
          <el-header style="height: 6vh; opacity: 100%; background: #f9f9f9; display: flex; justify-content: flex-end; align-items: center;">
            <el-popconfirm
                confirm-button-text="Yes"
                cancel-button-text="No"
                :icon="InfoFilled"
                title="是否退出登录？"
                @confirm="logout"
            >
              <template #reference>
                <el-avatar :size="40" :src="mySpaceData.userImg" style="cursor: pointer;"/>
              </template>
            </el-popconfirm>
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
    </el-watermark>
  </div>
</template>

<script setup>
import {Edit, House, InfoFilled, Search} from '@element-plus/icons-vue';
import {router} from "../router/index.js";
import {onMounted, provide, ref} from "vue";
import {userApi} from "../api/user-api.js";

const AutoLogin = ref({
  isAdmin: false,
  isUser: false,
  published: false,
  rentalRequest: false
});

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

  await viewSpace();
});


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
  try {
    const detail = await userApi.mySpace();
    mySpaceData.value = {
      ...detail,
      role: detail.role === "admin" ? "管理员" : "会员",
      sex: detail.sex === 0 ? "男" : detail.sex === 1 ? "女" : "其他",
      userImg: detail.userImg === null ? "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" : detail.userImg
    };
  } catch (error) {
    console.error("An error occurred while fetching data:", error);
  } finally {
  }
};

provide('mySpaceData', mySpaceData);
provide('viewSpace', viewSpace);

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
