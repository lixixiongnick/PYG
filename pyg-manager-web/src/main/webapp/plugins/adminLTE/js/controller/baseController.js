app.controller('baseController', function ($scope) {

    //重新加载列表 数据
    $scope.reloadList = function () {
        //切换页码
        $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
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
    $scope.delList = [];
    $scope.selected = function ($event, id) {
        if ($event.target.checked) {
            $scope.delList.push(id)
        } else {
            var idx = $scope.delList.indexOf(id);
            $scope.delList.splice(idx, 1);//删除

        }
    };
});