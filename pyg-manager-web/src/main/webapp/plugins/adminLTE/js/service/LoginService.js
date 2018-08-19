//定义模块函数
app.service('LoginService',function ($http) {
    //获取登录名
    this.showLoginName=function () {
        return  $http.get('../login/showLogin')
    };
});