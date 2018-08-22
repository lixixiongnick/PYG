 //控制层 
app.controller('goodsController' ,function($scope,$controller,goodsService,itemCatService,typeTemplateService,UploadService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	// $scope.findPage=function(page,rows){
	// 	goodsService.findPage(page,rows).success(
	// 		function(response){
	// 			$scope.list=response.rows;
	// 			$scope.paginationConf.totalItems=response.total;//更新总记录数
	// 		}
	// 	);
	// };
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){
        $scope.entity.tbGoodsDesc.introduction=editor.html();
    goodsService.add($scope.entity).success(
			function(response){
				if(response.success){
					//重新查询
					//清空表格
					$scope.entity={};
                    editor.html('');//清空富文本编辑器
		        	alert("增加成功");//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	};

	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	};
	
	$scope.searchEntity={};//定义搜索对象
	//搜索
	//初始化

	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};
	$scope.selectItemCat1List=function (parentId) {
		itemCatService.findparentId(parentId).success(function (data) {
			$scope.itemCatList=data;
        })
    };
    $scope.entity={tbGoods:{},tbGoodsDesc:{itemImages:[],customAttributeItems:[],specificationItems:[]}};
	//#watch方法用于监控某个变量的值,当被监控的值发生变化,就自动执行相应的函数
    $scope.$watch("entity.tbGoods.category1Id",function (newvalue, oldvalue) {
		itemCatService.findparentId(newvalue).success(function (data) {
			$scope.itemCatList1=data;
        })
    });
	$scope.$watch("entity.tbGoods.category2Id",function (newvalue, oldvalue) {
		itemCatService.findparentId(newvalue).success(function (data) {
			$scope.itemCatList2=data;
        })
    });
	$scope.$watch("entity.tbGoods.category3Id",function (newvalue, oldvalue) {
		itemCatService.findOne(newvalue).success(function (data) {
			$scope.entity.tbGoods.typeTemplateId=data.typeId;
        })
    });
	$scope.$watch("entity.tbGoods.typeTemplateId",function (newvalue, oldvalue) {
		typeTemplateService.findOne(newvalue).success(function (data) {
			$scope.typeTemplate=data;
			$scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);
			$scope.entity.tbGoodsDesc.customAttributeItems=JSON.parse($scope.typeTemplate.customAttributeItems);
        });
		typeTemplateService.findspecList(newvalue).success(function (data) {
			$scope.optionList=data;
        })
    });
	$scope.uploadFile=function () {
		UploadService.uploadFile().success(function (data) {
			if (data.success){
                $scope.image_entity.url=data.message;
			}else {
				alert(data.message);
			}
        })
    };

    $scope.add_image_entity=function () {
		$scope.entity.tbGoodsDesc.itemImages.push($scope.image_entity);
    }
    $scope.uploadspecAttribute=function ($event, name, value) {
	var object = $scope.searchObjectBykey($scope.entity.tbGoodsDesc.specificationItems,"attributeName",name);
		if(object!=null){
			//选中事件
			if($event.target.checked){
				object.attributeValue.push(value);
			}else {
				//取消勾选
				object.attributeValue.splice(object.attributeValue.indexOf(value),1);
                //如果选项都取消了，将此条记录移除m
                if(object.attributeValue.length==0){
                    $scope.entity.tbGoodsDesc.specificationItems.splice(
                        $scope.entity.tbGoodsDesc.specificationItems.indexOf(object),1);
                }

            }
		}else {
			$scope.entity.tbGoodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]})
		}
    };
    //创建sku列表
	$scope.createItemList=function () {
		$scope.entity.tbItemList=[{spec:{},price:0,num:99999,status:1,isDefault:1}];//初始化sku行
		//获取选中规格选项
			var  item = $scope.entity.tbGoodsDesc.specificationItems;
			for(var i=0;i<item.length;i++){
				//抽取方法生成行
				$scope.entity.tbItemList=addColumn($scope.entity.tbItemList,item[i].attributeName,item[i].attributeValue);
			}
    };
    addColumn=function (itemList, Columnname, Columnvalue) {
		var newList=[];
		for(var i=0;i<itemList.length;i++){
			var oldRow=itemList[i];
			for(var j=0;j<Columnvalue.length;j++){
                var newRow= JSON.parse( JSON.stringify( oldRow )  );//深克隆
                newRow.spec[Columnname]=Columnvalue[j];
                newList.push(newRow);

            }
		}
		return newList;

    };
    $scope.status=['未审核','已审核','审核未通过','关闭'];
	//初始化catList
	$scope.catList=[];
	$scope.findAllItemcat=function () {
		itemCatService.findAll().success(function (data) {
			for (var i=0;i<data.length;i++){
				$scope.catList[data[i].id]=data[i].name;
			}
        })
    }
    //上下架
	$scope.updateMarketable=function (status) {
		goodsService.updateMarketable($scope.selectIds,status).success(function (data) {
			if (data.success){
				$scope.reloadList();
			}else {
				alert(data.message)
			}
        })
    };
    $scope.Marketable=['下架','上架'];



});
