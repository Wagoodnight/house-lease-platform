import request from '../utils/request'

export const userApi = {
    login(data) {
        return request({
            url: `/user/login`,
            method: 'post',
            data: data
        })
    },
    registry(data){
        return request({
            url: `/user/registry`,
            method: 'post',
            data: data
        })
    },
    delUser(data) {
        return request({
            url: `/user/del-user?userId=${data}`,
            method: 'post'
        })
    },
    sourcePage(data) {
        return request({
            url: `/source/page`,
            method: 'post',
            data: data
        })
    },
    mySourcePage(data) {
        return request({
            url: `/source/mySourcePage`,
            method: 'post',
            data: data
        })
    },
    delSource(data) {
        return request({
            url: `/source/delete?houseId=${data}`,
            method: 'post'
        })
    },
    sourceDetail(data) {
        return request({
            url: `/source/details?houseId=${data}`,
            method: 'post'
        })
    },
    addSource(data) {
        return request({
            url: `/source/add`,
            method: 'post',
            data: data
        })
    },
    updateSource(data){
        return request({
            url: `/source/update`,
            method: 'post',
            data: data
        })
    },
    updateRequest(data){
        return request({
            url: `/request/update`,
            method: 'post',
            data: data
        })
    },
    requestPage(data) {
        return request({
            url: `/request/page`,
            method: 'post',
            data: data
        })
    },
    myRequestPage(data) {
        return request({
            url: `/request/myrequestpage`,
            method: 'post',
            data: data
        })
    },
    addRequest(data) {
        return request({
            url: `/request/add`,
            method: 'post',
            data: data
        })
    },
    requestDetail(data) {
        return request({
            url: `/request/details?requestId=${data}`,
            method: 'post'
        })
    },
    mySpace() {
        return request({
            url: `/user/space`,
            method: 'get'
        })
    },
    delRequest(data) {
        return request({
            url: `/request/delete?requestId=${data}`,
            method: 'post'
        })
    },
    updateUser(data) {
        return request({
            url: `/user/update-user`,
            method: 'post',
            data: data
        })
    },
    testToken() {
        return request({
            url: `/user/testToken`,
            method: 'get'
        })
    },
    userCount() {
        return request({
            url: `/user/count`,
            method: 'get'
        })
    },
    sourceCount() {
        return request({
            url: `/source/count`,
            method: 'get'
        })
    },
    mySourceCount() {
        return request({
            url: `/source/mycount`,
            method: 'post'
        })
    },
    requestCount() {
        return request({
            url: `/request/count`,
            method: 'get'
        })
    },
    myRequestCount() {
        return request({
            url: `/request/mycount`,
            method: 'post'
        })
    },
    userPage(data) {
        return request({
            url: `/user/page`,
            method: 'post',
            data: data
        })
    },
    userDetail(data) {
        return request({
            url: `/user/detail?userId=${data}`,
            method: 'post'
        })
    },
    sysLogPage(data) {
        return request({
            url: `/logs/page`,
            method: 'post',
            data: data
        })
    },
}