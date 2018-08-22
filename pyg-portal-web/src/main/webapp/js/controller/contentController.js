 //控制层 
app.controller('contentController' ,function($scope,$controller,contentService){

    //根据广告分类id查询列表
	//初始化$contentList
	$scope.contentList=[];
		$scope.findBycategoryId=function (categoryId) {
			contentService.findBycategoryId(categoryId).success(function (data) {
					$scope.contentList[categoryId]=data;
            })
        }
});	
