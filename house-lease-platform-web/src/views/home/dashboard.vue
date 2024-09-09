<template>
  <el-row :gutter="20" v-loading.fullscreen.lock="isLoading">
    <el-col :span="18">
      <el-carousel height="80vh" style="border-radius: 3px">
        <el-carousel-item v-for="(image, index) in images" :key="index">
          <img :src="image" alt="Carousel Image" style="width: 100%; height: 100%; object-fit: cover" />
        </el-carousel-item>
      </el-carousel>
    </el-col>
    <el-col :span="6">
      <div class="stats-cards">
        <el-card shadow="hover" class="stat-card">
          <div class="card-content">
            <el-icon :size="40"><User/></el-icon>
            <div>
              <p>总注册用户</p>
              <h2>{{ userCount }}</h2>
            </div>
          </div>
        </el-card>
        <el-card shadow="hover" class="stat-card">
          <div class="card-content">
            <el-icon :size="40"><House/></el-icon>
            <div>
              <p>总房源条数</p>
              <h2>{{ sourceCount }}</h2>
            </div>
          </div>
        </el-card>
        <el-card shadow="hover" class="stat-card">
          <div class="card-content">
            <el-icon :size="40"><Document/></el-icon>
            <div>
              <p>总求租条数</p>
              <h2>{{ requestCount }}</h2>
            </div>
          </div>
        </el-card>
        <el-card shadow="hover" class="stat-card">
          <div class="card-content">
            <el-icon :size="40"><Upload/></el-icon>
            <div>
              <p>我的房源</p>
              <h2>{{ mySourceCount }}</h2>
            </div>
          </div>
        </el-card>
        <el-card shadow="hover" class="stat-card">
          <div class="card-content">
            <el-icon :size="40"><Folder/></el-icon>
            <div>
              <p>我的求租</p>
              <h2>{{ myRequestCount }}</h2>
            </div>
          </div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { userApi } from "../../api/user-api.js";
import { ElMessage } from "element-plus";
import {Document, Folder, House, Upload, User} from "@element-plus/icons-vue";
let userCount = ref(0);
let sourceCount = ref(0);
let mySourceCount = ref(0);
let requestCount = ref(0);
let myRequestCount = ref(0);
let isLoading = ref(false);

onMounted(async () => {
  await getData();
});

const images = ref([
  'https://big.justeasy.cn/effectwater/202101/20210103115313_5ff13fa9e0236.jpg',
  'https://big.justeasy.cn/effectwater/202001/20200116015746_5e1f529a9c480.jpg',
  'https://img1n.soufunimg.com/zxb/201609/05/596/662d69b43f607529626f83b939da3a09.jpeg'
]);


const getData = async () => {
  isLoading.value = true;
  try {
    userCount.value = await userApi.userCount();
    sourceCount.value = await userApi.sourceCount();
    mySourceCount.value = await userApi.mySourceCount();
    requestCount.value = await userApi.requestCount();
    myRequestCount.value = await userApi.myRequestCount();
  } catch (error) {
    ElMessage.error("数据获取失败");
    console.error("Failed to fetch data", error);
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
.stats-cards {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
}

.card-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.card-content p {
  margin: 0;
  font-size: 16px;
  color: #909399;
}

.card-content h2 {
  margin: 0;
  font-size: 24px;
  color: #333;
}
</style>
