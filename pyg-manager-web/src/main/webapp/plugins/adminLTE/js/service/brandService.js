//定义模块函数
app.service('brandService',function ($http) {
    //查询全部
    this.findAll=function () {
        return  $http.get('../brand/findAll')
    };
    //分页查询
    this.findPage=function (pageNum, pageSize) {
        return	$http.get('../brand/findPage/'+pageNum+'/'+pageSize);
    };
    //根据ID查询
    this.findById=function (id) {
        return $http.get("../brand/findById/"+id)
    };
    //保存与修改操作
    this.save=function (entity) {
        return $http.post("../brand/save/",entity);
    };
    this.updata=function (entity) {
        return   $http.post("../brand/updata/",entity);
    };
    //删除操作
    this.del=function (ids) {
        return	$http.get('../brand/del/'+ids);
    }
    this.findbrandList=function () {
        return $http.get('../brand/findbrandList');
    }
});