<template>
  <div style="display: flex; justify-content: center;">
    <el-form :model="addData" :rules="rules" ref="ruleFormRef" :inline="true" label-width="auto" style="width: 735px">
      <div>
        <el-form-item label="厨房" prop="isKitchen">
          <el-radio-group v-model="addData.isKitchen">
            <el-radio value="1">有</el-radio>
            <el-radio value="0">无</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="阳台" prop="isBalcony">
          <el-radio-group v-model="addData.isBalcony">
            <el-radio value="1">有</el-radio>
            <el-radio value="0">无</el-radio>
          </el-radio-group>
        </el-form-item>
      </div>
      <div>
        <el-form-item prop="room" style="margin-right: 2px" label="户型">
          <el-select v-model="addData.room" style="width: 86px; " placeholder="室" prop="room">
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
        <el-form-item prop="hall" style="margin-right: 2px">
          <el-select v-model="addData.hall" style="width: 86px; " placeholder="厅" prop="hall">
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
        <el-form-item prop="rest" style="margin-right: 13px">
          <el-select v-model="addData.rest" style="width: 86px; " placeholder="卫" prop="rest">
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
        <el-form-item label="房屋朝向" style="width: 253px" prop="houseFacing">
          <el-select v-model="addData.houseFacing" placeholder="请选择朝向">
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
      </div>
      <div>
        <el-form-item label="价格" prop="price" style="margin-right: 13px; width: 340px">
          <el-input v-model.number="addData.price"/>
        </el-form-item>
        <el-form-item label="面积" prop="area" style="width: 341px">
          <el-input v-model.number="addData.area"/>
        </el-form-item>
      </div>
      <div>
        <el-form-item label="地点" prop="location">
          <el-input v-model="addData.location" style="width: 616px;" type="textarea" maxlength="40" show-word-limit
                    resize="none"/>
        </el-form-item>
      </div>
      <div>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="addData.remark" style="width: 616px" type="textarea" maxlength="1000" show-word-limit/>
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
</template>

<script setup>
import {ref} from 'vue';
import {UploadFilled} from '@element-plus/icons-vue';
import {ElMessage, ElMessageBox} from "element-plus";
import {userApi} from "../../api/user-api.js";

let viewerVisible = ref(false)
const initialIndex = ref(0)
const ruleFormRef = ref();
let srcList = ref([]);
const addData = ref({
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

const fileList = ref([]);

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
            addData.value.houseBase64 = JSON.stringify(fileList.value);
            await userApi.addSource(addData.value);
            formEl.resetFields()
            fileList.value = [];
            ElMessage({
              type: 'success',
              message: '提交成功',
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

</script>

<style scoped>

.upload-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.upload-icon {
  margin-bottom: 10px;
}

.upload-text {
  font-size: 16px;
}
</style>