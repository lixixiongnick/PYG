app.controller('baseController', function ($scope) {

    //重新加载列表 数据
    $scope.reloadList = function () {
        //切换页码

        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };
//分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();//重新加载
        }
    };
    $scope.selectIds = [];
    $scope.selected = function ($event, id) {
        if ($event.target.checked) {
            $scope.selectIds.push(id)
        } else {
            var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx, 1);//删除

        }
    };
    $scope.jsonTOstr=function (JsonStr, key) {
        //将json格式的字符串,转换为json数据
        var typeJson = JSON.parse(JsonStr);
        var value="";
        for (i=0;i<typeJson.length;i++){
            if(i>0){
                value +=",";
            }
            value += typeJson[i][key];
        }
        return value;
    };
    $scope.searchObjectBykey=function (list, key, keyValue) {
        //3
        for(var i=0;i<list.length;i++){
            console.log(list[i][key]+"___________1");
            console.log(keyValue+"___________2");
            if (list[i][key]==keyValue){
                return list[i];
            }
        }
        return null;
    }
});