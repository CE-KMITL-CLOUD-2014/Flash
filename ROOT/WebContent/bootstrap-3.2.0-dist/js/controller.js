//var chartjs = angular.module('chartjs',['tc.chartjs'])
checkuser.controller('Usercontroller', ['$scope','$http','$location','$timeout','datatest','$interval','$route', function ($scope,$http,$location,$timeout,datatest,$interval,$route) { 
	$scope.datas={};
	$scope.datadis= datatest.gettransaction();
	$scope.datauser = datatest.getData();//datatest.getData();//
	$scope.typedata = {};
	$scope.sumvalue = datatest.getsumvalue();
	console.log($scope.sumvalue);
	//$scope.transactionlist = datatest.gettransaction();

	$scope.selectItem = function(){
		$scope.datas.addgroup = $scope.datas.addtype.type;
		$scope.datas.addpriority = $scope.datas.addtype.priority;
	};
	$scope.promise;
	$scope.loadincomeoutlay = function(){
		$http.post('service/getincomeoutlay?username='+$scope.datauser.data.username
				+'&sessionId='+$scope.datauser.data.sessionId
				+'&startsavedate=null'
				+'&stopsavedate=null')
				.success(function(data,status){
					datatest.settransaction(data); 
					$scope.facth();
				})
				.error(function(data,status){
					alert(status);
				})
			};
	$scope.facth = function(){
		$scope.promise = $timeout(function(){
			$route.reload();
		},10);
	}
	
	$scope.stop = function(){
		
		alert("stop");
		$timeout.cancel($scope.promise);
	};
	
	//$scope.facth();
	
	$http.post('service/typeincomeoutlay?username='+$scope.datauser.data.username+'&sessionId='+$scope.datauser.data.sessionId)
	.success(function(data,status){
		$scope.typedata = data;
		
	})
	.error(function(data,status){
		alert("your not active");
	})

	$scope.addcontent = function(){
		$scope.show = true;
	}

	$scope.addtransaction = function(){
		if($scope.datas.addlist!=null&&$scope.datas.addamount!=null&&$scope.datas.adddate!=null&&$scope.datas.addgroup!=null){
			$http.post('service/addincomeoutlay?username='+$scope.datauser.data.username
					+'&sessionId='+$scope.datauser.data.sessionId
					+'&owner='+$scope.datauser.data.username
					+'&nameincomeoutlay='+$scope.datas.addlist
					+'&amount='+$scope.datas.addamount
					+'&savedate='+$scope.datas.adddate
					+'&comment=null'
					+'&nametype='+$scope.datas.addtype.name
					+'&type='+$scope.datas.addgroup
					+'&priority='+$scope.datas.addpriority)
					.success(function(data,status){
						if(data.status=='complete'){
							alert("add transaction successed!!!!");
							$scope.loadincomeoutlay();						
						}
						else{
							alert("error");
						}
					}).error(function(data,status){
						alert("not succesed");
					})
		}
		else
		{
			alert("value is null");
		}
	};
	
	/* delete transaction of list income-outlay*/
	$scope.transactionlist = {
			list:[]
	};
	$scope.deletetransaction = function(){
		alert($scope.transactionlist.list.length);
		for(var i =0 ;i<$scope.transactionlist.list.length;i++){
			console.log($scope.transactionlist.list[i]);
			$http.post('service/deleteincomeoutlay?username='+$scope.datauser.data.username
					+'&sessionId='+$scope.datauser.data.sessionId
					+'&owner='+$scope.transactionlist.list[i].owner
					+'&nameincomeoutlay='+$scope.transactionlist.list[i].nameincomeoutlay
					+'&savedate='+$scope.transactionlist.list[i].savedate)
					.success(function(data,status){
						alert("ssss");
						$scope.loadincomeoutlay();	
						//datatest.settransaction(data);
					}).error(function(data,status){
						alert("not succesed");
					});
		};
	};
	
	$scope.edittransaction = function(){
		$scope.datas.adddate = $scope.transactionlist.list.savedate;
		$scope.datas.addlist = $scope.transactionlist.list.nameincomeoutlay;
	}
	/* function logout of system */
	$scope.logout = function(){
		datatest.cleartransaction();
		$location.path('/index');
	}
	

}]);



checkuser.controller('profile', ['$scope','$http','datatest', function($scope,$http,datatest){
	$scope.datau = datatest.getData();
	$scope.editprofile = function(){
		$scope.template = "collettion/profile/editprofile.html";

	};

	$scope.editfile = function(){
		$http.post('bootstrap-3.2.0-dist/json/user.json').success(function(data,status){
			//$scope.datauser = statedata.setData(data);
			$scope.template = "collettion/profile/showprofile.html";
			alert(status);
		})
		.error(function(data,status){
			alert(status);
		});
	};

	$scope.cleardata = function(){
		datatest.clearData();
	};
}]);



