checkuser.controller('Logincontroller',['$scope','$http','$location','statedata','datatest', function($scope,$http,$location,statedata,datatest){
	
	$scope.provinces = [];
	$scope.jobs=[];
	$scope.datauser = statedata.getData();
	$scope.datas = [];
	$scope.ulogin = {
			username:null,
			password:null
			};
	$scope.users = {
			username: null ,
			email:null,
			sex:null,
			password:null,
			confirmPassword:null,
			name : null,
			age: null,
			birthday :null,
			job:null,
			province:null
			};
	
	$http.get('service/province').success(function(data,status){
		$scope.provinces = data;
	}).error(function(data,status){
		alert(status);
	});
	
	
	$http.get('service/job').success(function(data,status){
		$scope.jobs = data;
	}).error(function(data,status){
		alert(status);
	});

	$scope.submitform = function (){
			$scope.template="collettion/signup1.html";
	};
	
	$scope.submitdetail = function (){
		$http.post('service/register?username='+$scope.users.username
				+'&email='+$scope.users.email
				+'&sex='+$scope.users.sex
				+'&password='+$scope.users.password
				+'&confirmPassword='+$scope.users.confirmPassword
				+'&name='+$scope.users.name
				+'&age='+$scope.users.age
				+'&birthday='+$scope.users.birthday
				+'&job='+$scope.users.job.name
				+'&province='+$scope.users.province.name
				)
		.success(function(data, status, headers, config){
			if(data.status=="complete"){
				$scope.datas = data;
				$location.path('/login'); 
				alert("121549516556456");
			}
			else{
				alert("please try again");
				$scope.template = 'collettion/intro.html';
			}
		})
		.error(function(data,status){
			alert(status);
		});
	};
	$scope.formlogin = function (){
		//$location.path('/user'); 
		$http.get('service/login?username='+$scope.ulogin.username+'&password='+$scope.ulogin.password)
		.success(function(data, status, headers, config){
			if(data.data != null){
				datatest.setData(data);
				statedata.setData(data);
				$scope.datauser= datatest.getData();
				//$location.path('/user'); 
				$http.post('service/getincomeoutlay?username='+$scope.datauser.data.username
						+'&sessionId='+$scope.datauser.data.sessionId
						+'&startsavedate=null'
						+'&stopsavedate=null')
						.success(function(data,status){
							datatest.settransaction(data);
							$location.path('/user'); 
						})
						.error(function(data,status){
							alert(status);
						});
			}else{
				alert("plase your username or your password");
			}
		
		})
		.error(function(data,status){
			alert(status);
		});
	};

	
}]);