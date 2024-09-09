<template>
  <div>
    <el-button @click="show = !show" type="primary">筛选</el-button>
    <el-button type="primary" :loading="isLoading" :disabled="!show" @click="handleClick">查询</el-button>
    <el-button @click="formClear" :disabled="!show" type="primary">清空</el-button>
  </div>
  <div style="display:flex; flex-direction: column">
    <div style="margin-top: 20px">
      <el-collapse-transition>
        <div v-show="show">
          <el-form :model="postData" :inline="true" label-width="auto" style="max-width: 100%" class="form-inline">
            <el-row>
              <div>
                <el-form-item label="最低价格">
                  <el-input v-model="postData.minPrice" style="width: 220px"/>
                </el-form-item>
                <el-form-item label="最高价格">
                  <el-input v-model="postData.maxPrice" style="width: 220px"/>
                </el-form-item>
              </div>
              <el-form-item label="地点">
                <el-input v-model="postData.area"/>
              </el-form-item>
              <el-form-item label="房屋朝向" style="width: 200px">
                <el-select v-model="postData.houseFacing" placeholder="请选择朝向">
                  <el-option label="东" value="0"/>
                  <el-option label="西" value="1"/>
                  <el-option label="南" value="2"/>
                  <el-option label="北" value="3"/>
                  <el-option label="东南" value="4"/>
                  <el-option label="东北" value="5"/>
                  <el-option label="西南" value="6"/>
                  <el-option label="西北" value="7"/>
                </el-select>
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
              <div>
                <el-form-item label="户型">
                  <el-select v-model="postData.room" style="width: 80px" placeholder="室">
                    <el-option label="1室" value="1"/>
                    <el-option label="2室" value="2"/>
                    <el-option label="3室" value="3"/>
                    <el-option label="4室" value="4"/>
                    <el-option label="5室" value="5"/>
                    <el-option label="6室" value="6"/>
                    <el-option label="7室" value="7"/>
                    <el-option label="8室" value="8"/>
                    <el-option label="9室" value="9"/>
                    <el-option label="10室" value="10"/>
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-select v-model="postData.hall" style="width: 80px" placeholder="厅">
                    <el-option label="1厅" value="1"/>
                    <el-option label="2厅" value="2"/>
                    <el-option label="3厅" value="3"/>
                    <el-option label="4厅" value="4"/>
                    <el-option label="5厅" value="5"/>
                    <el-option label="6厅" value="6"/>
                    <el-option label="7厅" value="7"/>
                    <el-option label="8厅" value="8"/>
                    <el-option label="9厅" value="9"/>
                    <el-option label="10厅" value="10"/>
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-select v-model="postData.rest" style="width: 80px" placeholder="卫">
                    <el-option label="1卫" value="1"/>
                    <el-option label="2卫" value="2"/>
                    <el-option label="3卫" value="3"/>
                    <el-option label="4卫" value="4"/>
                    <el-option label="5卫" value="5"/>
                    <el-option label="6卫" value="6"/>
                    <el-option label="7卫" value="7"/>
                    <el-option label="8卫" value="8"/>
                    <el-option label="9卫" value="9"/>
                    <el-option label="10卫" value="10"/>
                  </el-select>
                </el-form-item>
              </div>
              <div>
                <el-form-item label="厨房">
                  <el-radio-group v-model="postData.isKitchen">
                    <el-radio value="1">有</el-radio>
                    <el-radio value="0">无</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="阳台">
                  <el-radio-group v-model="postData.isBalcony">
                    <el-radio value="1">有</el-radio>
                    <el-radio value="0">无</el-radio>
                  </el-radio-group>
                </el-form-item>
              </div>
            </el-row>
          </el-form>
        </div>
      </el-collapse-transition>
    </div>
    <el-table :data="tableData" v-loading="isLoading" style="width: 100%" :max-height="tableMaxHeight " ref="MyTable">
      <el-table-column prop="requestId" label="ID" :width="MyTableColumn.requestIdWidth"/>
      <el-table-column prop="location" label="地点" :width="MyTableColumn.locationWidth"/>
      <el-table-column prop="area" label="面积/m²" :width="MyTableColumn.areaWidth" sortable/>
      <el-table-column prop="layout" label="户型" :width="MyTableColumn.areaWidth"/>
      <el-table-column prop="houseFacing" label="朝向" :width="MyTableColumn.houseFacingWidth"/>
      <el-table-column prop="isKitchen" label="厨房" :width="MyTableColumn.requestIdWidth" sortable/>
      <el-table-column prop="isBalcony" label="阳台" :width="MyTableColumn.requestIdWidth" sortable/>
      <el-table-column prop="price" label="价格￥" :width="MyTableColumn.priceWidth" sortable/>
      <el-table-column fixed="right" label="操作" :min-width="MyTableColumn.rightWidth">
        <template #default="scope">
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="viewDetails(scope.row.requestId)"
          >
            详情
          </el-button>
          <el-button
              link
              type="primary"
              size="small"
              v-if="AutoLogin.isAdmin"
              @click="open(scope.row.requestId)"
              style="color: red"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        layout="sizes, prev, pager, next, jumper"
        :background=true
        :total=IPageRequest.total
        :page-sizes="[10, 15, 30, 50, 100]"
        :page-size=IPageRequest.size
        :current-page.sync=IPageRequest.current
        style="justify-content: center; margin: 5px"
        @size-change="handlePageSizeChange"
        @current-change="handleCurrentPageChange"
    />
  </div>

  <el-dialog v-model="viewDialog" title="详情" width="65%" center>
    <div style="margin: 10px; line-height: 2; font-weight: bold;" v-loading.fullscreen.lock="detailIsLoading">
      <el-text class="mx-1" type="info">面积&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ requestDetail.area }}<br></el-text>
      <el-text class="mx-1" type="info">户型&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ requestDetail.layout }}<br></el-text>
      <el-text class="mx-1" type="info">朝向&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ requestDetail.houseFacing }}<br></el-text>
      <el-text class="mx-1" type="info">厨房&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ requestDetail.isKitchen }}<br></el-text>
      <el-text class="mx-1" type="info">阳台&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ requestDetail.isBalcony }}<br></el-text>
      <el-text class="mx-1" type="info">价格&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ requestDetail.price }}<br></el-text>
      <el-text class="mx-1" type="info">地点&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ requestDetail.location }}<br></el-text>
      <el-text class="mx-1" type="info">备注&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ requestDetail.remark }}<br></el-text>
    </div>
  </el-dialog>
</template>

<script setup>
import {ref, onMounted, inject} from 'vue';
import {userApi} from '../../api/user-api.js';
import {ElMessage, ElMessageBox} from 'element-plus';
import {useResizeObserver} from "@vueuse/core";

//注入全局变量
const AutoLogin = inject('AutoLogin');

const isLoading = ref(false);
const detailIsLoading = ref(false);
const viewDialog = ref(false);
const MyTable = ref();
const detailId = ref(null);
let tableData = ref([]);
let tableMaxHeight = '73vh';
const show = ref(false);
let postData = ref({
  area: null,
  currentPage: 1,
  endTime: "",
  hall: null,
  houseFacing: null,
  isBalcony: null,
  isKitchen: null,
  location: "",
  maxPrice: null,
  minPrice: null,
  pageSize: 15,
  rest: null,
  room: null,
  startTime: ""
});

let MyTableColumn = ref({
  requestIdWidth: 80,
  locationWidth: 410,
  areaWidth: 110,
  houseFacingWidth: 80,
  priceWidth: 90,
  rightWidth: 100
})

const formClear = () => {
  postData.value = {
    area: null,
    currentPage: 1,
    endTime: "",
    hall: null,
    houseFacing: null,
    isBalcony: null,
    isKitchen: null,
    location: "",
    maxPrice: null,
    minPrice: null,
    pageSize: postData.value.pageSize,
    rest: null,
    room: null,
    startTime: ""
  }
  handleClick();
}

useResizeObserver(MyTable, (entries) => {
  const entry = entries[0]
  const {width} = entry.contentRect
  if (width >= 1140) {
    MyTableColumn.value.requestIdWidth = Math.round(80 * width / 1140)
    MyTableColumn.value.locationWidth = Math.floor(410 * width / 1140)
    MyTableColumn.value.areaWidth = Math.round(110 * width / 1140)
    MyTableColumn.value.houseFacingWidth = Math.round(80 * width / 1140)
    MyTableColumn.value.priceWidth = Math.round(90 * width / 1140)
    MyTableColumn.value.rightWidth = Math.floor(100 * width / 1140)
  }
})

const handleClick = async () => {
  isLoading.value = true;
  try {
    await fetchTableData();
  } finally {
    isLoading.value = false;
  }
}

let delData = ref({
  requestId: ""
})

let IPageRequest = ref({
  total: 1,
  size: null,
  current: null,
  pages: null
});

const fetchTableData = async () => {
  try {
    const result = await userApi.requestPage(postData.value);
    IPageRequest.value.total = result.total;
    IPageRequest.value.size = result.size;
    IPageRequest.value.current = result.current;
    IPageRequest.value.pages = result.pages;
    tableData.value = result.records.map(record => ({
      ...record,
      layout: `${record.room}室${record.hall}厅${record.rest}卫`,
      isKitchen: record.isKitchen === 1 ? '是' : '否',
      isBalcony: record.isBalcony === 1 ? '是' : '否',
      houseFacing: switchHouseFacing(record.houseFacing)
    }));
  } catch (error) {
    console.error("An error occurred while fetching data:", error);
  }
};

const switchHouseFacing = (record) => {
  switch (record) {
    case 0 :
      return "东";
    case 1 :
      return "西";
    case 2 :
      return "南";
    case 3 :
      return "北";
    case 4 :
      return "东南";
    case 5 :
      return "东北";
    case 6 :
      return "西南";
    case 7 :
      return "西北";
    default:
      return "?"
  }
};

onMounted(() => {
  handleClick();
});

const requestDetail = ref({
  area: "",
  houseFacing: null,
  isBalcony: null,
  isKitchen: null,
  location: "",
  price: "",
  layout: "",
});

const viewDetails = async (index) => {
  console.log(index)
  viewDialog.value = true;
  detailId.value = index;
  requestDetail.value = {
    area: "",
    houseFacing: null,
    isBalcony: null,
    isKitchen: null,
    location: "",
    price: "",
    layout: "",
  }
  detailIsLoading.value = true;
  try {
    const detail = await userApi.requestDetail(index);
    requestDetail.value = {
      ...detail,
      layout: `${detail.room}室${detail.hall}厅${detail.rest}卫`,
      area: `${detail.area} m²`,
      price: `${detail.price} ￥/月`,
      isKitchen: detail.isKitchen === 1 ? '是' : '否',
      isBalcony: detail.isBalcony === 1 ? '是' : '否',
      houseFacing: switchHouseFacing(detail.houseFacing),
    };
  } catch (error) {
    console.error("An error occurred while fetching data:", error);
  } finally {
    detailIsLoading.value = false;
  }
};

const delRequest = async (index) => {
  delData.value.requestId = index;
  await userApi.delRequest(delData.value.requestId);
  await handleClick();
};

const open = (index) => {
  ElMessageBox.confirm(
      '确认删除这条记录？',
      'Warning',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(async () => {
        await delRequest(index);
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '删除取消',
        })
      })
};

function handlePageSizeChange(size) {
  postData.value.pageSize = size;
  postData.value.currentPage = 1;
  handleClick();
}

function handleCurrentPageChange(page) {
  postData.value.currentPage = page;
  handleClick();
}

</script>

<style scoped>

</style>
