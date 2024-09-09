import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default () => {
  return defineConfig({
    plugins: [vue()],
    base: '/',
    server: {
      port: 8083,
      host: '0.0.0.0',
      open: true,
      proxy: {
        '/api': {
          target: 'http://127.0.0.1:8082/',
          changeOrigin: true
        },
      }
    },
    css: {
      preprocessorOptions: {
        scss: {

        }
      }
    },
    build: {
      minify: false, // 关闭代码压缩
      terserOptions: {
        format: {
          beautify: true, // 美化输出
          indent_level: 2, // 缩进级别
        },
      },
    },
  });
}
