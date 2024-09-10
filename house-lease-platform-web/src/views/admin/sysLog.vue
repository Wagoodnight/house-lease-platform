<template>
  <div>
    <el-button @click="show = !show" type="primary">筛选</el-button>
    <el-button type="primary" :loading="isLoading" :disabled="!show" @click="handleClick">查询</el-button>
    <el-button @click="formClear" :disabled="!show" type="primary">清空</el-button>
  </div>
  <div style="display: flex; flex-direction: column">
    <div style="margin-top: 20px">
      <el-collapse-transition>
        <div v-show="show">
          <el-form :model="postData" :inline="true" label-width="auto" style="max-width: 100%" class="form-inline">
            <el-row>
              <el-form-item label="请求接口">
                <el-input v-model="postData.operateName" style="width: 220px" placeholder="请输入请求接口"/>
              </el-form-item>
              <el-form-item label="用户ID">
                <el-input v-model="postData.userId" style="width: 220px" placeholder="请输入用户ID"/>
              </el-form-item>
                <div>
                  <el-form-item label="开始时间">
                    <el-date-picker
                        v-model="postData.startTime"
                        value-format="YYYY-MM-DD"
                        type="date"
                        placeholder="Pick a date"
                        style="width: 220px"
                    />
                  </el-form-item>
                  <el-form-item label="结束时间">
                    <el-date-picker
                        value-format="YYYY-MM-DD"
                        type="date"
                        v-model="postData.endTime"
                        placeholder="Pick a date"
                        style="width: 220px"
                    />
                  </el-form-item>
                </div>
            </el-row>
          </el-form>
        </div>
      </el-collapse-transition>
    </div>
  </div>
  <el-table :data="tableData" v-loading="isLoading" style="width: 100%" :max-height="tableMaxHeight" ref="MyTable">
    <el-table-column prop="logId" label="日志ID" :width="MyTableColumn.logIdWidth" sortable/>
    <el-table-column prop="userId" label="用户ID" :width="MyTableColumn.userIdWidth"/>
    <el-table-column prop="operateName" label="请求接口" :width="MyTableColumn.userIdWidth"/>
    <el-table-column prop="parameter" label="请求参数" :width="MyTableColumn.parameterWidth" show-overflow-tooltip/>
    <el-table-column prop="result" label="请求结果" :width="MyTableColumn.logIdWidth"/>
  </el-table>
  <el-pagination
      layout="sizes, prev, pager, next, jumper"
      :background=true
      :total=IPageRequest.total
      :page-sizes="[20, 30, 50, 100]"
      :page-size=IPageRequest.size
      :current-page.sync=IPageRequest.current
      style="justify-content: center; margin: 5px"
      @size-change="handlePageSizeChange"
      @current-change="handleCurrentPageChange"
  />

</template>

<script setup>
import {onMounted,ref} from "vue";
import {userApi} from "../../api/user-api.js";
import {ElMessage} from "element-plus";
import {useResizeObserver} from "@vueuse/core";

let show = ref(false);
const MyTable = ref();
let tableMaxHeight = '73vh';
let isLoading = ref(false);
let tableData = ref([]);
let postData = ref({
  currentPage: 1,
  operateName: "",
  pageSize: 20,
  result: "200",
  userId: null
});

let IPageRequest = ref({
  total: 1,
  size: null,
  current: null,
  pages: null,
  startTime: null,
  endTime: null
});

onMounted(() => {
  handleClick();
})

let MyTableColumn = ref({
  logIdWidth: 100,
  userIdWidth: 150,
  parameterWidth: 700
})

useResizeObserver(MyTable, (entries) => {
  const entry = entries[0]
  const {width} = entry.contentRect
  console.log(width);
  if(width >= 1200) {
    MyTableColumn.value.logIdWidth = Math.round(100 * width / 1200)
    MyTableColumn.value.userIdWidth = Math.floor(150 * width / 1200)
    MyTableColumn.value.parameterWidth = Math.round(700 * width / 1200)
  }
})

const handleClick = async () => {
  isLoading.value = true;
  try {
    await fetchTableData();
  } catch (error) {
    ElMessage.error("获取数据失败")
  } finally {
    isLoading.value = false;
  }
}

const fetchTableData = async () => {
  try {
    const result = await userApi.sysLogPage(postData.value);
    IPageRequest.value.total = result.total;
    IPageRequest.value.size = result.size;
    IPageRequest.value.current = result.current;
    IPageRequest.value.pages = result.pages;
    tableData.value = result.records.map(record => ({
      ...record,
    }))
  } catch (error) {
    ElMessage.error("获取数据失败");
    console.error("An error occurred while fetching data:", error)
  }
}

function handlePageSizeChange(size) {
  postData.value.pageSize = size;
  postData.value.currentPage = 1;
  handleClick();
}

function handleCurrentPageChange(page) {
  postData.value.currentPage = page;
  handleClick();
}

const formClear = () => {
  postData.value = {
    currentPage: 1,
    operateName: "",
    pageSize: postData.value.pageSize,
    result: "200",
    userId: null,
    startTime: null,
    endTime: null
  }
  handleClick();
}

</script>

<style scoped>

.el-table-column .cell {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

</style>