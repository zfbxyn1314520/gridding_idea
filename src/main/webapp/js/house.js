/**
 * house.html页面的外部js代码
 * author 刘春晓
 */
var auditHouse=false;
$(function(){
	//获取导航菜单权限功能
	$.ajax({
		type:'get',
		url:'menu/getMenuPerById.do?'+new Date(),
		data:{"menuName":$(".tabsMoreList li[class=active]").text()},
	    dataType:'json',
	    cache:false,
	    success:function(data){
	    	
	    	var width = Math.round($(window).width()*0.5);
	    	var pers=data[0].permissions;
	    	var per=[];
	    	for(var i=0;i<pers.length;i++){
	    		per[i]=pers[i].perName;
	    	}
	    	var toolBarItem="";
	    	var status=true;
	    	var toolBar=true;
	    	for(var z=0;z<per.length;z++){
	    		if(per[z]=="添加"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addhouseInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='edithouseInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deletehouseInfo();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='audithouseInfo();'>审核</button>";
	    			auditHouse=true;
	    		}
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshHouse();'>刷新</button>";
	    	//生成datagrid表格
	    	$.CurrentNavtab.find('#houseInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'house/getAllHouseByUnitId.do',
	    	    columns: [
	    	        {name:'unit',label:'小区',align:'center',width:width*0.24,
						render:function(value, data){
							if(value!=""){
								return value.block.court.courtName;
							}
						}
					},
	    	        
	    	        {name:'houseNum',label:'门牌号',align:'center',width:width*0.12},
	    	        {name:'houseHolder',label:'户主',align:'center',width:width*0.12,
	    	        	render:function(value,data){
	    	        		return "<a href='javascript:void(0);' onclick='getAccountByHouseId("+data.houseId+");'>"+value+"</a>";
	    	        	}
	    	        },
	    	        {name:'houseArea',label:'房屋面积',align:'center',width:width*0.12,
	    	        	render:function(value, data){
	    	        		return value+"平方米";
	    	        	}
	    	        },
	    	        {name:'houseStatus',label:'房屋状态',align:'center',width:width*0.12,
	    	        	render:function(value, data){
	    	        		if(value!=null){
	    	        			return value.statusName;
	    	        		}
	    	        	}
	    	        },
	    	        {name:'houseAudit',label:'状态',align:'center',width:width*0.08,
	    	        	render:function(value, data){
	    	        		if(value==0){
	    	        			return "<i class='am-icon-check-square-o' style='color:green'></i>";
	    	        		}else{
	    	        			return "<i class='am-icon-minus-square-o' style='color:red'></i>";
	    	        		}		
	    	        	}
	    	        },
	    	        {name:'',label:'操作',align:'center',hide:status,width:width*0.2,
	    	        	render:function(value, data){	   
	    	        		var str="";
	    	        		if(data.houseAudit==1){
	    	        			str+="&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
        						"onclick='auditHouseSingle("+data.houseId+",\""+data.houseName+"\","+data.houseAudit+");'>审核</button>";
	    	        		}else{
	    	        			str+="&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
	    	        		}
	    	        		return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
	    	        				"onclick='getOnehouseDetail("+data.houseId+");'>详细信息</button>"+str;
	    	        	},
	    	        },
	    	    ],
	    	    paging:{pageSize:50, selectPageSize:'10,20,40,50', pageCurrent:1, showPagenum:5, totalRow:0},
	    	    linenumberAll: true,
	    	    showLinenumber:true,
	    	    showCheckboxcol:'lock',
	    	    hScrollbar:false,
	    	    columnMenu: false,
	    	    filterThead: false
	    	});
	    }
	});	
});


//获取户主详细信息
function getAccountByHouseId(e){
	BJUI.dialog({
        id:'houseSuAccount',
        target:$.CurrentNavtab.find("#houseSuAccount"),
        title:'户口信息',
        width:565,
        height:200,
        mask:true,
        resizable:false,
        onClose:function(){
	    	$.CurrentNavtab.find('#houseInfo').datagrid('selectedRows',false);
	    }
	});
	$.ajax({
		type:"post",
		url:"house/getAccountByHouseId.do?"+new Date(),
		data:{"houseId":e},
		dataType:"json",
		success:function(data){
			$.CurrentDialog.find("#hAccountHoulder").text(data.accountHolder);
			$.CurrentDialog.find("#hAccountSite").text(data.accountSite);
			$.CurrentDialog.find("#hMemberCount").text(data.memberCount);
			$.CurrentDialog.find("#hHolderTel").text(data.holderTel);
			$.CurrentDialog.find("#hHolderCard").text(data.holderCard);
		}
	});
	
}

//打开增加页面
function addhouseInfo(){
	BJUI.dialog({
        id:'addHouse',
        width: 655,
        height: 610,
        target:$("#addHouse"),
        title:'添加房屋信息',
        mask:true,
        resizable:false,
        onClose:function(){
        	$.CurrentNavtab.find('#houseInfo').datagrid('selectedRows',false);
     	}
	});
	getCourtOfCourt();
	getStatusName();
	getTypeName();
	saveHouseInfo();
}

//打开修改页面
function edithouseInfo(){
	var houseId=$.CurrentNavtab.find("#houseInfo").data('selectedDatas');
	if(houseId==null || houseId.length!=1){
		   BJUI.alertmsg('info', '请选择一条需要编辑的房屋');
	}else{
		BJUI.dialog({
	        id:'editHouse',
	        target:$.CurrentNavtab.find("#addHouse"),
	        title:'修改房屋信息',
	        width:655,
		    height:610,
		    mask:true,
		    resizable:false,
	        onClose:function(){
	        	$.CurrentNavtab.find('#houseInfo').datagrid('selectedRows',false);
	     	}
		});
		$.CurrentDialog.find("#submitBtnh > span").text("修改");
		var upHid="";
		for(var i=0;i<houseId.length;i++){
			upHid=houseId[i].houseId;
		}
		$.ajax({
			type:"post",
			url:"house/getHouseById.do?"+new Date(),
			data:{"upHid":upHid},
			dataType:"json",
			cache : false,
			success:function(data){
				getCourtOfCourt();
				getStatusName();
				getTypeName();
				$.CurrentDialog.find("#houseNum").attr("value",data.house.houseNum);
				$.CurrentDialog.find("#houseArea").attr("value",data.house.houseArea);
				$.CurrentDialog.find("#houseId").attr("value",data.house.houseId);
				$.CurrentDialog.find("#houseSite").attr("value",data.house.houseSite);
				$.CurrentDialog.find('#typeId').selectpicker('val',data.house.houseType.typeId);
				$.CurrentDialog.find('#statusId').selectpicker('val',data.house.houseStatus.statusId);
				$.CurrentDialog.find('#h_courtId').selectpicker('val',data.house.unit.block.court.courtId);
				getBlockOfCourt(data.house.unit.block.court.courtId);
				$.CurrentDialog.find('#h_blockId').selectpicker('val',data.house.unit.block.blockId);
				getUnitOfCourt(data.house.unit.block.blockId);
				$.CurrentDialog.find('#unitId').selectpicker('val',data.house.unit.unitId);
				$.CurrentDialog.find("#h_accountHolder").attr("value",data.accountHolder);
				$.CurrentDialog.find("#editHouseDate").attr("value",convertDate(data.house.editHouseDate));
				$.CurrentDialog.find("#h_accountSite").attr("value",data.accountSite);
				$.CurrentDialog.find("#h_memberCount").attr("value",data.memberCount);
				$.CurrentDialog.find("#h_holderTel").attr("value",data.holderTel);
				$.CurrentDialog.find("#h_holderCard").attr("value",data.holderCard);
			}
		});
		saveHouseInfo();
	}
}

//表单验证
function saveHouseInfo(){
	$("#h_courtId").change(function(){
		if($(this).val()!=""){
			getBlockOfCourt($(this).val());
		}else{
			$.CurrentDialog.find("#unitId").html("<option value=''>请选择单元</option>");
			$.CurrentDialog.find("#h_blockId").html("<option value=''>请选择楼栋</option>");
	    	$.CurrentDialog.find('#unitId').selectpicker('refresh');
	    	$.CurrentDialog.find('#h_blockId').selectpicker('refresh');
		}
	});
	$("#h_blockId").change(function(){
		if($(this).val()!=""){
			getUnitOfCourt($(this).val());
		}else{
			$.CurrentDialog.find("#unitId").html("<option value=''>请选择单元</option>");
	    	$.CurrentDialog.find('#unitId').selectpicker('refresh');
		}
		
	});
	$("#houseNum").blur(function(){
		var option=$.CurrentDialog.find("#h_courtId option:selected").text()+""+$.CurrentDialog.find("#h_blockId option:selected").text()+""+$.CurrentDialog.find("#unitId option:selected").text()+""+$.CurrentDialog.find("#houseNum").val();
		$.CurrentDialog.find("#houseSite").attr("value",option);
	});
	$.CurrentDialog.find("#submitBtnh").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addHouseInfo").isValid()){
			if($.CurrentDialog.find("#submitBtnh > span").text().trim()=="提交"){
				nameStr+=$.CurrentDialog.find("#houseNum").val();
				$.ajax({
					type:"post",
					url:"house/saveHouse.do?"+new Date(),
					data:$.CurrentDialog.find("#addHouseInfo").serialize(),
					dataType:"json",
					cache : false,
					success:function(data){
						if(data=="1"){
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#houseInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加房屋<span style='color:green'>"+nameStr+"<span>！", {
								 displayPosition:'middlecenter'
							});
						}else{
							BJUI.alertmsg('error', '添加失败');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#houseNum").val();
				$.ajax({
					type:"post",
					url:"house/editHouseById.do?"+new Date(),
					data:$.CurrentDialog.find("#addHouseInfo").serialize(),
					dataType:"text",
					success:function(data){
						if(data==0){
							 BJUI.alertmsg('error', '修改失败');
						}else{
							 BJUI.alertmsg('ok', '成功修改房屋'+nameStr+"!",{
								 displayPosition:'middlecenter'
								});
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#houseInfo').datagrid('refresh', true);
						}
					}
				});
			}
		}else{
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#addHouseInfo').validator('cleanUp');
 				}
 			});
		}
	});
}

//查看方法
function getOnehouseDetail(e){
	BJUI.dialog({
        id:'houseDetail',
        target:$.CurrentNavtab.find("#houseDetail"),
        title:'房屋详细信息',
        width:700,
        height:435,
        mask:true,
        resizable:false,
        onClose:function(){
	    	$.CurrentNavtab.find('#houseInfo').datagrid('selectedRows',false);
	    }
	});
	$.ajax({
		type:"post",
		url:"house/getOneHouseInfoById.do?"+new Date(),
		data:{"houseId":e},
		dataType:"json",
		success:function(data){
			var house=data.house;
			$.CurrentDialog.find("#h-houseNum").text(house.houseNum);
			$.CurrentDialog.find("#h-courtName").text(house.unit.block.court.courtName);
			$.CurrentDialog.find("#h-blockName").text(house.unit.block.blockName);
			$.CurrentDialog.find("#h-unitName").text(house.unit.unitName);
			$.CurrentDialog.find("#h-houseArea").text(house.houseArea);
			$.CurrentDialog.find("#h-typeName").text(house.houseType.typeName);
			$.CurrentDialog.find("#h-statusName").text(house.houseStatus.statusName);
			$.CurrentDialog.find("#h-houseSite").text(house.houseSite);
			$.CurrentDialog.find("#editHouseName").text(house.editHouseName);
			$.CurrentDialog.find("#h-houseHolder").text(house.houseHolder);
			$.CurrentDialog.find("#editHouseDate").text(convertDate(house.editHouseDate));
			
			$.CurrentDialog.find("#hAccountHoulder").text(data.accountHolder);
			$.CurrentDialog.find("#hAccountSite").text(data.accountSite);
			$.CurrentDialog.find("#hMemberCount").text(data.memberCount);
			$.CurrentDialog.find("#hHolderTel").text(data.holderTel);
			$.CurrentDialog.find("#hHolderCard").text(data.holderCard);
			if(house.houseAudit==0){
				$.CurrentDialog.find("#h-houseAudit").html("<span style='color:green'>已审核</span>");
			}else{
				$.CurrentDialog.find("#h-houseAudit").html("<span style='color:red'>审核中</span>");
			}
		}
	});
}

//图片审核
function auditHouseSingle(e,f,g){
	if(auditHouse==true){
		if(g==0){
			return false;
		}else{
			var nameStr=f;
			var audHid=e;
			BJUI.alertmsg('confirm', '你确定要审核房屋<span style="color:orange">'+nameStr+'</span>吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"house/updateAuditHouse.do?"+new Date(),
						data:{"audHid" : audHid},
						dataType:"text",
						success:function(data){
							if(data==0){
								   BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#houseInfo').datagrid('refresh', true);
							}
						}
					});
				}
			});
		}
		
	}else{
		return false;
	}
}

//审核方法
function audithouseInfo(){
	var houseId=$.CurrentNavtab.find("#houseInfo").data('selectedDatas');
	var audHid="";
	var nameStr="";
	if(typeof(houseId)=="undefined"||houseId.length==0){
		BJUI.alertmsg('info', '请选择需要审核的房屋');
	}else{
		for(var i=0;i<houseId.length;i++){
			if(houseId[i].houseAudit==0){
				continue;
			}else{
				audHid+=houseId[i].houseId+",";
				nameStr+=houseId[i].houseNum+",";
			}
		}
		if(audHid.length>0){
			nameStr=nameStr.substring(0, nameStr.length-1);
			BJUI.alertmsg('confirm', '你确定要审核房屋'+nameStr+'吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"house/updateAuditHouse.do?"+new Date(),
						data:{"audHid" : audHid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#houseInfo').datagrid('refresh', true);
								BJUI.alertmsg('ok', '成功审核房屋'+nameStr+"!", {
									displayPosition:'middlecenter'
								});
							}
						}
					});
				}
			});
		}
	}
}

//获取所有小区
function getCourtOfCourt(){
	BJUI.ajax('doajax', {
	    url: 'block/getCourtName.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var courtStr="<option value=''>请选择小区名称</option>";
	    	for(var i=0;i<data.length;i++){
	    		courtStr+="<option value='" + data[i].courtId + "'>" + data[i].courtName + "</option>";
	    	}
	    	$.CurrentDialog.find("#h_courtId").html(courtStr);
	    	$.CurrentDialog.find('#h_courtId').selectpicker('refresh');
	    }
	 });
}

//获取所有楼栋
function getBlockOfCourt(e){
	BJUI.ajax('doajax', {
	    url: 'house/getBlockName.do',
	    type:'GET',
	    data:{"courtId":e},
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var blockStr="<option value=''>请选择楼栋</option>";
	    	for(var i=0;i<data.length;i++){
	    		blockStr+="<option value='" + data[i].blockId + "'>" + data[i].blockName + "</option>";
	    	}
	    	$.CurrentDialog.find("#h_blockId").html(blockStr);
	    	$.CurrentDialog.find('#h_blockId').selectpicker('refresh');
	    }
	 });
}

//获取所有楼栋单元
function getUnitOfCourt(e){
	BJUI.ajax('doajax', {
	    url: 'house/getUnitName.do',
	    type:'GET',
	    data:{"blockId":e},
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var unitStr="<option value=''>请选择单元</option>";
	    	for(var i=0;i<data.length;i++){
	    		unitStr+="<option value='" + data[i].unitId + "'>" + data[i].unitName + "</option>";
	    	}
	    	$.CurrentDialog.find("#unitId").html(unitStr);
	    	$.CurrentDialog.find('#unitId').selectpicker('refresh');
	    }
	 });
}


//获取所有房屋状态
function getStatusName(){
	BJUI.ajax('doajax', {
	    url: 'house/getStatusName.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var statuStr="<option value=''>请选择房屋状态</option>";
	    	for(var i=0;i<data.length;i++){
	    		statuStr+="<option value='" + data[i].statusId + "'>" + data[i].statusName + "</option>";
	    	}
	    	$.CurrentDialog.find("#statusId").html(statuStr);
	    	$.CurrentDialog.find('#statusId').selectpicker('refresh');
	    }
	 });
}

//获取所有户型
function getTypeName(){
	BJUI.ajax('doajax', {
	    url: 'house/getTypeName.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var typeStr="<option value=''>请选择户型</option>";
	    	for(var i=0;i<data.length;i++){
	    		typeStr+="<option value='" + data[i].typeId + "'>" + data[i].typeName + "</option>";
	    	}
	    	$.CurrentDialog.find("#typeId").html(typeStr);
	    	$.CurrentDialog.find('#typeId').selectpicker('refresh');
	    }
	 });
}

function convertDate ( value ){
	 var year="";			var month="";
	 var day="";			var hour="";
	 var minute="";			var second="";
	 var currentDate = "" ;
	 if(typeof(value)=='object'){
	     year       = value . year+1900;
	     month      = value . month+ 1 ;
	     day        = value . date;
	     hour       = value . hours;
	     minute     = value . minutes;
	     second    = value.seconds;
	 }else{
		 //时间戳
		 var time = new Date(value);
		 year=time.getYear()+1900;
		 month=time.getMonth()+1;
		 day=time.getDate();
		 hour=time.getHours();
		 minute=time.getMinutes();
		 second=time.getSeconds();
	 }
	 currentDate = year + "年" ;
if ( month >= 10 ){
	 currentDate = currentDate + month + "月" ;
}else{
    currentDate = currentDate + "0" + month + "月" ;
}
if ( day >= 10 ){
    currentDate = currentDate + day +"日";
}else{
    currentDate = currentDate + "0" + day +"日";
}
if ( hour >= 10 ){
    currentDate = currentDate + " " + hour ;
}else{
    currentDate = currentDate + " 0" + hour ;
}
if ( minute >= 10 ){
    currentDate = currentDate + ":" + minute ;
}else{
    currentDate = currentDate + ":0" + minute ;
} 
if ( second >= 10 ){
    currentDate = currentDate + ":" + second ;
}else{
    currentDate = currentDate + ":0" + second ;
} 
return currentDate;
}

function refreshHouse(){
	$.CurrentNavtab.find('#houseInfo').datagrid('refresh', false);
}
