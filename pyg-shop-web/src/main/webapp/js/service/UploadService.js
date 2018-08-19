//定义模块函数
app.service('UploadService',function ($http) {
    this.uploadFile=function() {
        var formData = new FormData();
        formData.append("file", file.files[0]);
        return $http({
            method: 'POST',
            url: "../upload",
            data: formData,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        })
    }
    });
