//定义模块函数
app.service('specificationService',function ($http) {
    //查询全部
    this.findAll=function () {
        return  $http.get('../specification/findAll')
    };
    //分页查询
    this.findPage=function (pageNum, pageSize) {
        return	$http.get('../specification/findPage/'+pageNum+'/'+pageSize);
    };
    //根据ID查询
    this.findById=function (id) {
        return $http.get("../specification/findById/"+id)
    };
    //保存与修改操作
    this.add=function (entity) {
        return $http.post("../specification/save",entity);
    };
    this.updata=function (entity) {
        return   $http.post("../specification/updata",entity);
    };
    //删除操作
    this.del=function (ids) {
        return	$http.get('../specification/del/'+ids);
    };
    this.specIdsList=function () {
        return $http.get('../specification/specification')
    }
});