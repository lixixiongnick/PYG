app.controller('specificationController', function ($scope,$controller, specificationService) {
    //控制器继承
    //继承baseController控制器，且把父控制器$scope传递子控制器的$scope
    $controller("baseController",{$scope:$scope});
    //定义函数
    //向服务器发送ajax请求
    $scope.findAll = function () {
        specificationService.findAll().success(function (data) {
            $scope.list = data;
        });
    };


    $scope.findPage = function (pageNum, pageSize) {
        specificationService.findPage(pageNum, pageSize).success(function (data) {
            $scope.list = data.rows;
            $scope.paginationConf.totalItems = data.total;
        })
    };
    $scope.save = function () {
        var objService = null;
        if ($scope.entity.tbSpecification.id != null) {
            objService = specificationService.updata($scope.entity);
        } else {
            objService = specificationService.add($scope.entity);
        }

        objService.success(function (data) {
            if (data.success) {
                $scope.reloadList();
            } else {
                alert("保存失败")
            }
        })
    };
    $scope.findById = function (id) {
        specificationService.findById(id).success(function (data) {
            $scope.entity = data;
        })
    };

    $scope.del = function () {
        specificationService.del($scope.delList).success(function (data) {
            if (data.success) {
                $scope.delList = [];
                $scope.reloadList();
            } else {
                alert("删除失败")
            }
        })
    };
    $scope.addTableRows=function () {
        $scope.entity.tbSpecificationOptionList.push({});
    };
    $scope.delTableRows=function (index) {
            $scope.entity.tbSpecificationOptionList.splice(index,1);
    };

});