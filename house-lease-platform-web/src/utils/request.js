import axios from 'axios'
import {ElMessage} from "element-plus";

const service = axios.create({
    baseURL: import.meta.env.VITE_APP_URL_API,
    timeout: 20000
})

// 请求拦截器
service.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('authToken');
        if (token) {
            config.headers['Authorization'] = token;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

service.interceptors.response.use(
    (response) => {
        const res = response.data;
        const code = res.code;
        if(code === '200') {
            return Promise.resolve(res.data);
        } else if(code === '1000') {
            localStorage.clear();
            location.replace('/#/login');
            return Promise.reject(response);
        } else if(code === '403') {
            ElMessage.error(`访问未被授权，错误代码：${code}`)
        } else if(code === '0') {
            ElMessage.error(`操作失败，错误代码：${code}`)
        } else if(code === '404') {
            ElMessage.error(`请求的资源不存在，错误代码：${code}`)
        } else if(code === '405') {
            ElMessage.error(`不支持的请求，错误代码：${code}`)
        } else if(code === '500') {
            ElMessage.error(`服务器错误，错误代码：${code}`)
            ElMessage.error(res.message)
        } else if(code === '1001' || code === '1003') {
            ElMessage.error(`添加错误，错误代码：${code}`)
        } else if(code === '1002' || code === '1004') {
            ElMessage.error(`删除错误，错误代码：${code}`)
        } else if(code === '1005') {
            ElMessage.error(`用户不存在或密码错误，错误代码：${code}`)
            return Promise.reject(res);
        } else {
            alert(data.message);
            return Promise.reject(response);
        }
    }
)
export default service;

