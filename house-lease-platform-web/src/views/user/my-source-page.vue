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
      <el-table-column prop="houseId" label="ID" :width="MyTableColumn.houseIdWidth"/>
      <el-table-column prop="location" label="地点" :width="MyTableColumn.locationWidth"/>
      <el-table-column prop="area" label="面积/m²" :width="MyTableColumn.areaWidth" sortable/>
      <el-table-column prop="layout" label="户型" :width="MyTableColumn.areaWidth"/>
      <el-table-column prop="houseFacing" label="朝向" :width="MyTableColumn.houseFacingWidth"/>
      <el-table-column prop="isKitchen" label="厨房" :width="MyTableColumn.houseIdWidth" sortable/>
      <el-table-column prop="isBalcony" label="阳台" :width="MyTableColumn.houseIdWidth" sortable/>
      <el-table-column prop="price" label="价格￥" :width="MyTableColumn.priceWidth" sortable/>
      <el-table-column fixed="right" label="操作" :min-width="MyTableColumn.rightWidth">
        <template #default="scope">
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="viewDetails(scope.row.houseId)"
          >
            详情
          </el-button>
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="viewEditorDetails(scope.row.houseId)"
          >
            编辑
          </el-button>
          <el-button
              link
              type="primary"
              size="small"
              @click="open(scope.row.houseId)"
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
      <el-text class="mx-1">{{ sourceDetail.area }}<br></el-text>
      <el-text class="mx-1" type="info">户型&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ sourceDetail.layout }}<br></el-text>
      <el-text class="mx-1" type="info">朝向&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ sourceDetail.houseFacing }}<br></el-text>
      <el-text class="mx-1" type="info">厨房&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ sourceDetail.isKitchen }}<br></el-text>
      <el-text class="mx-1" type="info">阳台&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ sourceDetail.isBalcony }}<br></el-text>
      <el-text class="mx-1" type="info">价格&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ sourceDetail.price }}<br></el-text>
      <el-text class="mx-1" type="info">地点&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ sourceDetail.location }}<br></el-text>
      <el-text class="mx-1" type="info">备注&nbsp;&nbsp;&nbsp;&nbsp;</el-text>
      <el-text class="mx-1">{{ sourceDetail.remark }}<br></el-text>
    </div>
    <el-carousel :interval="4000" type="card" height="250px">
      <el-carousel-item v-for="(item) in sourceDetail.houseBase64" :key="item.uid">
        <img :src="item.url" :alt="item.name" style="height: 100%; width: 100%; object-fit: cover;"/>
      </el-carousel-item>
    </el-carousel>
  </el-dialog>

  <el-dialog v-model="viewEdit" title="编辑" width="65%" center>
    <div style="display: flex; justify-content: center;" v-loading.fullscreen.lock="editorIsLoading">
      <el-form :model="updateData" :rules="rules" ref="ruleFormRef" :inline="true" label-width="auto" style="width: 735px "
               class="form-inline">
        <div>
          <el-form-item label="厨房" prop="isKitchen">
            <el-radio-group v-model.number="updateData.isKitchen">
              <el-radio :label="1">有</el-radio>
              <el-radio :label="0">无</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="阳台" prop="isBalcony">
            <el-radio-group v-model.number="updateData.isBalcony">
              <el-radio :label="1">有</el-radio>
              <el-radio :label="0">无</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
        <div>
          <el-form-item prop="room" style="margin-right: 2px" label="户型">
            <el-select v-model="updateData.room" style="width: 86px; " placeholder="室" prop="room">
              <el-option label="1室" :value="1"/>
              <el-option label="2室" :value="2"/>
              <el-option label="3室" :value="3"/>
              <el-option label="4室" :value="4"/>
              <el-option label="5室" :value="5"/>
              <el-option label="6室" :value="6"/>
              <el-option label="7室" :value="7"/>
              <el-option label="8室" :value="8"/>
              <el-option label="9室" :value="9"/>
              <el-option label="10室" :value="10"/>
            </el-select>
          </el-form-item>
          <el-form-item prop="hall" style="margin-right: 2px">
            <el-select v-model="updateData.hall" style="width: 86px; " placeholder="厅" prop="hall">
              <el-option label="1厅" :value="1"/>
              <el-option label="2厅" :value="2"/>
              <el-option label="3厅" :value="3"/>
              <el-option label="4厅" :value="4"/>
              <el-option label="5厅" :value="5"/>
              <el-option label="6厅" :value="6"/>
              <el-option label="7厅" :value="7"/>
              <el-option label="8厅" :value="8"/>
              <el-option label="9厅" :value="9"/>
              <el-option label="10厅" :value="10"/>
            </el-select>
          </el-form-item>
          <el-form-item prop="rest" style="margin-right: 13px">
            <el-select v-model="updateData.rest" style="width: 86px; " placeholder="卫" prop="rest">
              <el-option label="1卫" :value="1"/>
              <el-option label="2卫" :value="2"/>
              <el-option label="3卫" :value="3"/>
              <el-option label="4卫" :value="4"/>
              <el-option label="5卫" :value="5"/>
              <el-option label="6卫" :value="6"/>
              <el-option label="7卫" :value="7"/>
              <el-option label="8卫" :value="8"/>
              <el-option label="9卫" :value="9"/>
              <el-option label="10卫" :value="10"/>
            </el-select>
          </el-form-item>
          <el-form-item label="房屋朝向" style="width: 253px" prop="houseFacing">
            <el-select v-model="updateData.houseFacing" placeholder="请选择朝向">
              <el-option label="东" :value="0"/>
              <el-option label="西" :value="1"/>
              <el-option label="南" :value="2"/>
              <el-option label="北" :value="3"/>
              <el-option label="东南" :value="4"/>
              <el-option label="东北" :value="5"/>
              <el-option label="西南" :value="6"/>
              <el-option label="西北" :value="7"/>
            </el-select>
          </el-form-item>
        </div>
        <div>
          <el-form-item label="价格" prop="price" style="margin-right: 13px; width: 340px">
            <el-input v-model.number="updateData.price"/>
          </el-form-item>
          <el-form-item label="面积" prop="area" style="width: 341px">
            <el-input v-model.number="updateData.area"/>
          </el-form-item>
        </div>
        <div>
          <el-form-item label="地点" prop="location">
            <el-input v-model="updateData.location" style="width: 616px;" type="textarea" maxlength="40" show-word-limit
                      resize="none"/>
          </el-form-item>
        </div>
        <div>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="updateData.remark" style="width: 616px" type="textarea" maxlength="1000" show-word-limit/>
          </el-form-item>
        </div>
        <div style="min-height: 200px">
          <el-form-item label="上传图片" prop="pictures">
            <el-upload
                v-model:file-list="fileList"
                list-type="picture-card"
                :before-upload="handleBeforeUpload"
                multiple
                :on-preview="handlePictureCardPreview"
                :accept="'image/*'"
            >
              <div class="upload-content">
                <el-icon size="60" class="upload-icon">
                  <upload-filled/>
                </el-icon>
                <div class="upload-text">
                  <em style="color: #409eff">点击上传</em>
                </div>
              </div>
            </el-upload>
            <el-image-viewer
                v-if="viewerVisible"
                :url-list="srcList"
                :initial-index="initialIndex"
                @close="viewerVisible = false"
            />
          </el-form-item>
        </div>
        <el-button style="margin-left: 79px;margin-top: 30px" type="primary" @click="handleSubmit(ruleFormRef)">提交
        </el-button>
        <el-button style="margin-top: 30px" @click="resetForm(ruleFormRef)">清空</el-button>
      </el-form>
    </div>
  </el-dialog>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {userApi} from '../../api/user-api.js';
import {UploadFilled} from '@element-plus/icons-vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import {useResizeObserver} from "@vueuse/core";

const isLoading = ref(false);
const detailIsLoading = ref(false);
const viewDialog = ref(false);
const viewEdit = ref(false);
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

const updateData = ref({
  houseId: "",
  location: "",
  area: null,
  room: null,
  hall: null,
  rest: null,
  isKitchen: null,
  isBalcony: null,
  houseFacing: null,
  houseBase64: [],
  price: null,
  remark: ""
});

let MyTableColumn = ref({
  houseIdWidth: 80,
  locationWidth: 370,
  areaWidth: 110,
  houseFacingWidth: 80,
  priceWidth: 90,
  rightWidth: 140
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
    MyTableColumn.value.houseIdWidth = Math.round(80 * width / 1140)
    MyTableColumn.value.locationWidth = Math.floor(370 * width / 1140)
    MyTableColumn.value.areaWidth = Math.round(110 * width / 1140)
    MyTableColumn.value.houseFacingWidth = Math.round(80 * width / 1140)
    MyTableColumn.value.priceWidth = Math.round(90 * width / 1140)
    MyTableColumn.value.rightWidth = Math.floor(140 * width / 1140)
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
  houseId: ""
})

let IPageRequest = ref({
  total: 1,
  size: null,
  current: null,
  pages: null
});

const fetchTableData = async () => {
  try {
    const result = await userApi.mySourcePage(postData.value);
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

const sourceDetail = ref({
  area: "",
  houseFacing: null,
  isBalcony: null,
  isKitchen: null,
  location: "",
  price: "",
  layout: "",
  houseBase64: ""
});

const viewDetails = async (index) => {
  viewDialog.value = true;
  detailId.value = index;
  sourceDetail.value = {
    area: "",
    houseFacing: null,
    isBalcony: null,
    isKitchen: null,
    location: "",
    price: "",
    layout: "",
    houseBase64: ""
  }
  detailIsLoading.value = true;
  try {
    const detail = await userApi.sourceDetail(index);
    sourceDetail.value = {
      ...detail,
      layout: `${detail.room}室${detail.hall}厅${detail.rest}卫`,
      area: `${detail.area} m²`,
      price: `${detail.price} ￥/月`,
      isKitchen: detail.isKitchen === 1 ? '是' : '否',
      isBalcony: detail.isBalcony === 1 ? '是' : '否',
      houseFacing: switchHouseFacing(detail.houseFacing),
      houseBase64: JSON.parse(detail.houseBase64)
    };
  } catch (error) {
    console.error("An error occurred while fetching data:", error);
  } finally {
    detailIsLoading.value = false;
  }
};

const delSource = async (index) => {
  delData.value.houseId = index;
  await userApi.delSource(delData.value.houseId);
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
        await delSource(index);
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







//以下是编辑部分







let viewerVisible = ref(false)
const initialIndex = ref(0)
const ruleFormRef = ref();
let editorIsLoading = ref(false);
let srcList = ref([]);


const fileList = ref([]);


const viewEditorDetails = async (index) => {
  viewEdit.value = true;
  updateData.value = {
    houseId: "",
    location: "",
    area: null,
    room: null,
    hall: null,
    rest: null,
    isKitchen: null,
    isBalcony: null,
    houseFacing: null,
    houseBase64: [],
    price: null,
    remark: ""
  }
  editorIsLoading.value = true;
  try {
    const detail = await userApi.sourceDetail(index);
    updateData.value = {
      ...detail,
      houseBase64: JSON.parse(detail.houseBase64)
    };
    fileList.value = updateData.value.houseBase64.map(item => ({
      name: item.name,
      url: item.url
    }))
  } catch (error) {
    console.error("An error occurred while fetching data:", error);
  } finally {
    editorIsLoading.value = false;
  }
}

const validatePictures = (rule, value, callback) => {
  if (fileList.value.length === 0) {
    callback(new Error('请上传图片'));
  } else {
    callback();
  }
};

const handlePictureCardPreview = (file) => {
  srcList = fileList.value.map(file => file.url);
  initialIndex.value = fileList.value.findIndex(f => f.name === file.name);
  viewerVisible.value = true;
}

const rules = ref({
  isKitchen: [{required: true, message: '请选择厨房信息', trigger: 'change'}],
  isBalcony: [{required: true, message: '请选择阳台信息', trigger: 'change'}],
  room: [{required: true, message: '请选择室', trigger: 'change'}],
  hall: [{required: true, message: '请选择厅', trigger: 'change'}],
  rest: [{required: true, message: '请选择卫', trigger: 'change'}],
  houseFacing: [{required: true, message: '请选择房屋朝向', trigger: 'change'}],
  price: [
    {required: true, message: '请输入价格'},
    {type: 'number', message: '价格必须是数字'}
  ],
  area: [
    {required: true, message: '请输入面积'},
    {type: 'number', message: '面积必须是数字'}
  ],
  location: [{required: true, message: '请输入地点', trigger: 'blur'}],
  remark: [{message: '备注长度不能超过1000字', trigger: 'blur', max: 1000}],
  pictures: [{required: true, validator: validatePictures, trigger: 'change'}]
});

const outOfSize = () => {
  ElMessage({
    showClose: true,
    message: '超出10MB上传限制大小！',
    type: 'error',
    duration: 10000
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

const handleBeforeUpload = (file) => {
  const isLt20480kb = file.size / 1024 < 20480;
  if (!isLt20480kb) {
    outOfSize();
    return false;
  }
  const reader = new FileReader();
  reader.onload = (event) => {
    const base64Data = event.target.result;
    fileList.value.push({
      name: file.name,
      url: base64Data
    });
  };
  reader.readAsDataURL(file);
  return false;
};

const resetForm = async (formEl) => {
  if (!formEl) return;
  formEl.resetFields()
  fileList.value = [];
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
            updateData.value.houseBase64 = JSON.stringify(fileList.value);
            await userApi.updateSource(updateData.value);
            formEl.resetFields()
            fileList.value = [];
            await handleClick()
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

//
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  - /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//           佛祖保佑       永不宕机     永无BUG
//
//       佛曰:
//               写字楼里写字间，写字间里程序员；
//               程序人员写程序，又拿程序换酒钱。
//               酒醒只在网上坐，酒醉还来网下眠；
//               酒醉酒醒日复日，网上网下年复年。
//               但愿老死电脑间，不愿鞠躬老板前；
//               奔驰宝马贵者趣，公交自行程序员。
//               别人笑我忒疯癫，我笑自己命太贱；
//               不见满街漂亮妹，哪个归得程序员？
//
</script>

<style scoped>

</style>
