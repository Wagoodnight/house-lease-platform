# 房租租赁系统（课程设计）

## 项目介绍

欢迎来到这个仓库！这个仓库是用于学校课程设计的提交。它包含了我们在课程中学习和实践的代码、文档和其他相关资料。

## 环境准备
- jdk 1.8以上，并且配置好环境变量
- node.js 14以上，推荐使用最新的LTS版本
- npm 6以上，推荐使用最新版本
- Apache Maven（本人使用的是3.8.6，未测试更低版本的兼容性）
- IntelliJ IDEA（可选，如果你可以手动打包Maven项目）
- MySQL

## 使用方法

### 配置数据库
1. **安装MySQL：**
   - 请确保你已经成功安装了MySQL数据库。你可以通过命令行运行 `mysql --version` 来检查是否安装成功。
2. **克隆项目并进入项目：**
   ```bash
   git clone https://github.com/Wagoodnight/house-lease-platform.git
   ```
   ```bash
   cd house-lease-platform
   ```
3. **创建数据库：**
   
   登录到MySQL服务器：
   ```bash
   mysql -u root -p
   ```
   输入密码后，你将进入MySQL命令行界面。
   在MySQL命令行界面中，创建一个新的数据库`housing_lease_db`：
   ```sql
   CREATE DATABASE housing_lease_db;
   ```
   ```bash
   quit
   ```
4. **导入SQL文件：**
   
   使用以下命令来导入SQL文件：
   
   ```bash
   mysql -u root -p housing_lease_db < housing_lease_db.sql
   ```
   输入MySQL root用户的密码。
   
	完成以上步骤后，你的数据库`housing_lease_db`应该已经创建好，并且包含了必要的表和数据结构。如果导入过程中遇到任何问题，请检查SQL文件的路径和内容是否正确，以及MySQL服务器的状态是否正常。

   
### 后端使用方法

1. **进入后端项目目录：**
   ```bash
   cd house-lease-platform-api
   ```
2. **编辑Application.yml文件：**

   编辑`src/main/resources/application.yml`文件，将`[Your Database Url]` `[Your password]` `[Your Account]`分别替换成你的MySQL数据库的地址，密码和账号，注意url需要带上端口号
3. **使用 Maven 构建项目：**
   ```bash
   mvn clean install
   ```

4. **在 IntelliJ IDEA 中导入项目：**
   - 打开 IntelliJ IDEA，导入 `house-lease-platform/house-lease-platform-api` 目录。
   - IDEA 会自动识别 Maven 项目，并导入所有依赖。

5. **运行项目：**
   - 在 IntelliJ IDEA 中，右键点击主类 `HouseLeasePlatformApplication`，选择“Run”以启动项目。
   
6. **如果你对Maven足够了解，也可以打包成jar包使用**
   
### 前端使用方法


1. **进入前端项目目录：**
   ```bash
   cd ../house-lease-platform-web
   ```

2. **安装依赖：**
   ```bash
   npm install
   ```
   如果遇到安装失败的情况，推荐先设置淘宝源
   ```bash
   npm config set registry https://registry.npmmirror.com/
   ```

3. **启动服务器：**
   ```bash
   npm run dev
   ```
   - 默认账号`admin`密码`123456`
   - 前端默认路径`http://localhost:8083/#/login`
   - 集成swagger3接口文档，通过`http://localhost:8082/api/doc.html#/`路径访问

## Tips

这个项目仅供学习和展示使用。目前该项目已经停止功能更新，如果你有任何建议或发现问题，可以提出 issue 或者联系我。


## 感谢

特别感谢以下项目对本项目的帮助和启发：

- [edu-manage](https://gitee.com/ligl756/edu-manage) - 该项目为我们提供了宝贵的参考和学习材料。


## 许可证

这个项目采用了 [GPL-3.0 许可证](LICENSE)。详细信息请查看 `LICENSE` 文件。
