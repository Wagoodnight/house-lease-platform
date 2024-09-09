import { createApp } from 'vue'
import 'element-plus/dist/index.css'
import App from './App.vue'
import ElementPlus from 'element-plus'
import {router} from "./router/index.js";
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import './global.css';

let app = createApp(App);
app.use(ElementPlus, { locale: zhCn });
app.use(router);
app.mount('#app')
