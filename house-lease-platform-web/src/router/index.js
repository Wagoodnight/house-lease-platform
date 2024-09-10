import {createRouter, createWebHashHistory} from 'vue-router';

const routes = [
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/login/login.vue'),
        meta: {title: '登录'}
    },
    {
        path: '/register',
        component:  () => import('../views/login/registry.vue'),
        meta: {title: '注册'}
    },
    {
        path: '/',
        component: () => import('../layout/index.vue'),
        redirect: '/dashboard',
        meta: {title: '首页'},
        children: [
            {
                path: 'dashboard',
                component:  () => import('../views/home/dashboard.vue'),
                meta: {title: '主页'}
            },
            {
                path: 'sourcepage',
                component:  () => import('../views/HouseSource/page.vue'),
                meta: {title: '查找房源'}
            },
            {
                path: 'mysourcepage',
                component:  () => import('../views/user/my-source-page.vue'),
                meta: {title: '我发布的房源'}
            },
            {
                path: 'addsource',
                component:  () => import('../views/HouseSource/add.vue'),
                meta: {title: '发布房源'}
            },
            {
                path: 'requestpage',
                component:  () => import('../views/RentalRequest/page.vue'),
                meta: {title: '查找求租'}
            },
            {
                path: 'addrequest',
                component:  () => import('../views/RentalRequest/add.vue'),
                meta: {title: '发布求租'}
            },
            {
                path: 'myrequestpage',
                component:  () => import('../views/user/my-request-page.vue'),
                meta: {title: '我发布的求组'}
            },
            {
                path: 'myspace',
                component:  () => import('../views/user/my-page.vue'),
                meta: {title: '我的信息'}
            },
            {
                path: 'useradmin',
                component:  () => import('../views/admin/userAdmin.vue'),
                meta: {title: '注册用户管理'}
            },
            {
                path: 'syslog',
                component:  () => import('../views/admin/sysLog.vue'),
                meta: {title: '操作日志'}
            }
        ]
    },
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes
})

const whiteList = ['/login', '/register', '/reset', '/assets']

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('authToken');

    // 动态设置页面标题
    if (to.meta.title) {
        document.title = to.meta.title;
    }

    if (token) {
        if (to.path === '/login') {
            next({ path: '/' });
        } else {
            next();
        }
    } else {
        if (whiteList.includes(to.path)) {
            next();
        } else {
            next({ path: '/login' });
        }
    }
});


router.path = () => {
    return router.currentRoute.value.path;
}