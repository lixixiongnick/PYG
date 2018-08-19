app.controller('LoginController' ,function($scope,LoginService){
            $scope.showLoginName=function () {
                LoginService.showLoginName().success(function (data) {
                    $scope.name=data;
                });
            }
});