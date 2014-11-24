/*------------------------------------------------------------------------------------
 * 
 * 
 * 
 * -----------------------------------------------------------------------------------*/
checkuser.factory('datatest',['$http','$filter',function ($http,$filter) {
	/*-----------------------------------------------
	 * variable of function return
	 * 
	 * ----------------------------------------------*/
	var tempvalue = {
			value : []
	};
	var tempdata = {
			data : []
	};
	var temptotal = {
			total : []
	};
	var formatData = {
			format : []
	}
	var monthData = [{'id':1,'month':'Jan'}, {'id':2,'month':'Fab'},{'id':3,'month':'Mar'},{'id':4,'month':'Apr'},{'id':5,'month':'May'},{'id':6,'month':'Jun'},
				          {'id':7,'month':'Jul'},{'id':8,'month':'Aug'},{'id':9,'month':'Sep'},{'id':10,'month':'Oct'},{'id':11,'month':'Nov'},{'id':12,'month':'Dec'}]
	var datagraph = {};
	var total = {} ;
	var label = {};
	var dailygraph = {};
	var doghnutgraph = {};
	var compareBargraph = {};
	var compareLinegraph = {};
	var transaction = {};
    var service = {};
    /*--------------------------------------------------------------
     * return value follow function this below
     * 
     * -------------------------------------------------------------*/
     return{
    	 /*--------------------------------
    	  * 
    	  * -------------------------------*/
    	 getData : function(){
    		 service =  JSON.parse(localStorage.getItem("service"));
    		 return service;
    	 },
    	 setData : function(input){
    		 localStorage.setItem("service",JSON.stringify(input));
    	 },
    	 clearData : function(){
    		 localStorage.clear();
    	 },
    	 /*--------------------------------
    	  * 
    	  * -------------------------------*/
    	 gettransaction : function(){
    		 transaction =  JSON.parse(localStorage.getItem("transaction"));
    		 console.log(transaction);
    		 return transaction;
    	 },
    	 settransaction : function(input){
    		 localStorage.setItem("transaction",JSON.stringify(input));
    		 
    	 },
    	 cleartransaction : function(){
    		 localStorage.clear();
    	 },
    	 
    	 
    	/*-----------------------------------------------------------
    	 * ***********collection of graph**************
    	 * 
    	 * -----------------------------------------------------------*/ 
    	 getDoghnutGraph : function(){
    		 doghnutgraph = JSON.parse(localStorage.getItem("doghnutgraph"));
    		 return doghnutgraph;
    	 },
    	 
    	 requireDoghnutData : function(url){
    		 $http.post(url).success(function(data,status){
    			 if(data){
    				 localStorage.setItem("doghnutgraph",JSON.stringify(data));
    			 }
    		 	}).error(function(data,status){
    			 
    		 })
    	 },
    	 
    	 ////------------------------------------------------------------//////////
    	 getCompareBarGraph : function(){
    		 compareDataBargraph = JSON.parse(localStorage.getItem("compareDataBargraph"));
    		 return compareDataBargraph;
    	 },
    	 requireCompareBarData : function(url){
    		 $http.post(url).success(function(data,status){
    			 if(data){
    				 localStorage.setItem("compareDataBargraph",JSON.stringify(data));
    				 //console.log(compareDataBargraph);
    			 }
    		 	}).error(function(data,status){
    			 
    		 })
    	 },
    	 
    	 //////---------------------------------------------------------------//////
    	 
    	 getCompareLineGraph : function(){
    		 compareLinegraph = JSON.parse(localStorage.getItem("compareLinegraph"));
    		 return compareLinegraph;
    	 },
    	 requireCompareData : function(url){
    		 $http.post(url).success(function(data,status){
    			 if(data){
    				 localStorage.setItem("compareLinegraph",JSON.stringify(data));
    			 }
    		 	}).error(function(data,status){
    			 
    		 })
    	 },
    	 
    	 //////---------------------------------------------//////
    	 getLinearGraph : function(){
    		 linegraph = JSON.parse(localStorage.getItem("linegraph"));
    		 return linegraph;
    	 },
    	 requireLinaerData : function(url){
    		 $http.post(url).success(function(data,status){
    			 if(data){
    				 localStorage.setItem("Lineargraph",JSON.stringify(data));
    			 }
    		 	}).error(function(data,status){
    			 
    		 })
    	 },
    	 
    	 ///////----------------------------------------------------///
    	 setarraylabel : function(){
    		 tempvalue.value = [];
    		 var temp = $filter('orderBy')(transaction.data.incomeoutlay,'savedate');
    		 for(var i in temp){
    			 var item = temp[i].savedate;
    			 var date = new Date(item);
    			 var day = date.getDate();
    			 tempvalue.value.push(day)
    			 
    		 }
    		 localStorage.setItem("label",JSON.stringify(tempvalue.value));
    		 label = JSON.parse(localStorage.getItem("label"));
    		 console.log(label);
    		 return label;
    		 
    	 },
    	//-//---------------------------------------------------------////	  
    
    	 setFormatgraph : function(){
    		 var graph = this.getCompareBarGraph();
    		 tempdata.data = [];
    		 temptotal.total = [];
    		 formatData.format = [];
    		 var refval = '';
    		 var useval = '';
    		 var month = '';
    			for(var i in graph.data.result){
    				//console.log(graph[i].month);
    				for(var j in monthData){
    					if(graph.data.result[i].month == monthData[j].id){
    						
    						month = monthData[j].month;
    						refval = graph.data.result[i].valueref;
    						useval = graph.data.result[i].valueuse;
    						formatData.format.push(month);
    						tempvalue.value.push(refval);
    						temptotal.total.push(useval);
    					}
    				}
    			}
    			localStorage.setItem("datagraph",JSON.stringify({'month':formatData.format,'valueref':tempvalue.value,'valueuse':temptotal.total}));
    			datagraph = JSON.parse(localStorage.getItem("datagraph"));
    			return datagraph;
    	 },
    	 ///---------------------------------------------------------------////
    	 setarraydata : function(){
    		 tempdata.data = [];
    		 temptotal.total = [];
    		 var totalincomeoutcome = 0;
    		 var income = 0;
    		 var outcome = 0;
    		 var temp = $filter('orderBy')(transaction.data.incomeoutlay,'savedate');
    		 for(var i in temp){
    			 var item = temp[i].amount;
    			 if(temp[i].typeofuse.type=="outcome"){
    				 outcome += item;
    				 item *= (-1);
    				 tempdata.data.push(item);
    			 }else{
    				 income += item;
    				 tempdata.data.push(item);
    			 } 
    			 totalincomeoutcome += item;
    			 console.log(item +' '+total);
    		 }
    		 temptotal.total.push({
    			 'income': income,
    			 'outcome': outcome,
    			 'total': totalincomeoutcome
    		 });
    		 localStorage.setItem("total",JSON.stringify(temptotal.total));
    		 localStorage.setItem("data",JSON.stringify(tempdata.data));
    		 data = JSON.parse(localStorage.getItem("data"));
    		 console.log(item);
    		 return data;
    	 },
    	 ////----------------------------------------------------------///
    	 getsumvalue : function(){
    		 total = JSON.parse(localStorage.getItem("total"));
    		 console.log(total);
    		 return total;
    	 }
    	 
     }
}]);
