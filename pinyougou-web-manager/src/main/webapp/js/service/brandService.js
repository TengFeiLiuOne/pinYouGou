// 定义服务层:
app.service("brandService",function($http){
	this.findAll = function(){
		return $http.get("../brand/findAll.do");
	}
	
	this.findPage = function(page,rows){
		return $http.get("../brand/findByPage.do?pageNo="+page+"&pageSize="+rows);
	}
	
	this.add = function(entity){
		return $http.post("../brand/add.do",entity);
	}
	
	this.update=function(entity){
		return $http.post("../brand/update.do",entity);
	}
	
	this.findOne=function(id){
		return $http.get("../brand/findOne.do?id="+id);
	}
	
	this.dele = function(ids){
		return $http.get("../brand/delete.do?ids="+ids);
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../brand/searchByPage.do?pageNo="+page+"&pageSize="+rows,searchEntity);
	}
	
	this.selectOptionList = function(){
		return $http.get("../brand/selectOptionList.do");
	}
	
	//像数据库中添加品牌
	this.addBrands = function (brandList) {
		return $http.post("../brand/addBrands.do",brandList);
    }
});