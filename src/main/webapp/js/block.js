/**
 * block.html页面的外部js代码
 * author 刘春晓
 */

var auditBlock=false;
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
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addblockInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editblockInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteblockInfo();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='auditblockInfo();'>审核</button>";
	    			auditBlock=true;
	    		}
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshBlock();'>刷新</button>";
	    	//生成datagrid表格
	    	$.CurrentNavtab.find('#blockInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'block/getAllBlockByCourtId.do',
	    	    columns: [
	    	        {name:'court',label:'所属小区',align:'center',width:width*0.21,
	    	        	render:function(value, data){
	    	        		return value.courtName;
	    	        	}
	    	        },
	    	        {name:'blockName',label:'楼栋名称',align:'center',width:width*0.15},
	    	        {name:'unitCount',label:'总单元数',align:'center',width:width*0.12},
	    	        {name:'unitFloorCount',label:'总层数',align:'center',width:width*0.12},
	    	        {name:' ',label:'总户数',align:'center',width:width*0.12,
	    	        	render:function(value,data){
	    	        		var count=0;
	    	        		BJUI.ajax('doajax', {
	    	        		    url: 'house/getHouseCountById.do',
	    	        		    type:'GET',
	    	        		    data:{"blockId":data.blockId},
	    	        		    async : false,
	    	        		    okalert:false,
	    	        		    okCallback: function(json,options) {
	    	        		    	count=json.message;
	    	        		    }
	    	        		});
	    	        		return "<a href='javascript:void(0);' onclick='getHouseByBlockId("+data.blockId+");'>"+count+"</a>";
	    	        	}
	    	        },
	    	        {name:'blockAudit',label:'状态',align:'center',width:width*0.08,
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
	    	        		if(data.blockAudit==1){
	    	        			str+="&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
        						"onclick='auditBlockSingle("+data.blockId+",\""+data.blockName+"\","+data.blockAudit+");'>审核</button>";
	    	        		}else{
	    	        			str+="&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
	    	        		}
	    	        		return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
	        						"onclick='getOneblockDetail("+data.blockId+");'>详细信息</button>"+str;
	    	        	},
	    	        },
	    	    ],
	    	    paging:{pageSize:30, selectPageSize:'10,20,40,50', pageCurrent:1, showPagenum:5, totalRow:0},
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


//显示每个楼栋各个房屋基本信息
function getHouseByBlockId(e){
	$.ajax({
		type : "get",
		url : "house/getHouseByBlockId.do?"+new Date(),
		data : {"blockId":e},
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data!=null){
				BJUI.dialog({
					id:'blockSuHouse',
				    target:$.CurrentNavtab.find("#blockSuHouse"),
				    title:'房屋详情',
				    width:820,
				    height:500,
				    mask:true,
				    resizable:false,
				    onLoad:function(){
				    	$('#houseView').datagrid({
				    		height: '100%',
				    	    tableWidth:'98%',
				    	    local: 'local',
				    		filterThead: false,
				    	    paging:{pageSize:10, selectPageSize:'15,20,25', pageCurrent:1, showPagenum:5, totalRow:0},
					    	linenumberAll: true,
					    	hScrollbar:false,
					    	columnMenu: false,
							columns: [
								{name:'houseId',label:'ID',align:'center',width:50},
								{name:'houseNum',label:'门牌号',align:'center',},
								{name:'houseArea',label:'房屋面积',align:'center',
									render:function(value, data){
										return value+"平方米";
									}
								},
								{name:'houseAudit',label:'审核状态',align:'center',
									items:[{"sex":true,"name":"已通过"},{"sex":false,"name":"审核中"}],
									itemattr:{value:'sex',label:'name'},
									render:function(value, data){
										if(value==0){
				    	        			return "<i class='am-icon-check-square-o' style='color:green'></i>";
				    	        		}else{
				    	        			return "<i class='am-icon-minus-square-o' style='color:red'></i>";
				    	        		}	         		
									}
								}
						    ],
						    data: data
						});
				    }
				});
			}
		}
	});
}

//打开新增窗口
function addblockInfo(){
	BJUI.dialog({
        id:'addBlock',
        width: 655,
        height: 295,
        target:$("#addBlock"),
        title:'添加楼栋信息',
        mask:true,
        resizable:false,
        onClose:function(){
        	$.CurrentNavtab.find('#blockInfo').datagrid('selectedRows',false);
     	}
	});
	getAllCourtsOfBlock();
	getAllGridsOfBlock();
	saveBlockInfo();
}

//打开修改窗口
function editblockInfo(){
	var blockId=$.CurrentNavtab.find("#blockInfo").data('selectedDatas');
	if(blockId==null || blockId.length!=1){
		BJUI.alertmsg('info', '请选择一条需要编辑的道路');
	}else{
		BJUI.dialog({
	        id:'addBlock',
	        target:$.CurrentNavtab.find("#addBlock"),
	        width:655,
		    height:295,
	        title:'修改楼栋信息',
	        mask:true,
	        resizable:false,
	        onClose:function(){
	        	$.CurrentNavtab.find('#blockInfo').datagrid('selectedRows',false);
	     	}
		});
		$.CurrentDialog.find("#submitBtnb > span").text("修改");
		var upBid="";
		for(var i=0;i<blockId.length;i++){
			upBid=blockId[i].blockId;
		}
		$.ajax({
			type:"post",
			url:"block/getBlockById.do?"+new Date(),
			data:{"upBid":upBid},
			dataType:"json",
			success:function(data){
				getAllCourtsOfBlock();
				getAllGridsOfBlock();
				$.CurrentDialog.find("#blockName").attr("value",data.blockName);
				$.CurrentDialog.find("#blockId").attr("value",data.blockId);
				$.CurrentDialog.find("#unitCount").attr("value",data.unitCount);
				$.CurrentDialog.find('#courtId').selectpicker('val',data.court.courtId);
				$.CurrentDialog.find('#gridId').selectpicker('val',data.gridId);
				$.CurrentDialog.find("#blockAdmintor").attr("value",data.blockAdmintor);
				$.CurrentDialog.find("#blockAdmintorTel").attr("value",data.blockAdmintorTel);
				$.CurrentDialog.find("#floorCount").attr("value",data.unitFloorCount);
				getAllGridStaffsOfBlock(data.gridId);
				$.CurrentDialog.find('#gridStaffId').selectpicker('val',data.gridStaffId);
			}
		});
		saveBlockInfo();
	}
}

//表单验证
function saveBlockInfo(){
	$("#gridId").change(function(){
		if($(this).val()!=""){
			getAllGridStaffsOfBlock($(this).val());
		}else{
			$.CurrentDialog.find("#gridStaffId").html("<option value=''>请选择网格员</option>");
	    	$.CurrentDialog.find('#gridStaffId').selectpicker('refresh');
		}
		
	});
	$.CurrentDialog.find("#submitBtnb").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addBlockInfo").isValid()){
			if($.CurrentDialog.find("#submitBtnb > span").text().trim()=="提交"){
				nameStr+=$.CurrentDialog.find("#blockName").val();
				$.ajax({
					type:"post",
					url:"block/saveBlock.do?"+new Date(),
					data:$.CurrentDialog.find("#addBlockInfo").serialize(),
					dataType:"json",
					cache : false,
					success:function(data){
						if(data=="1"){
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#blockInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加楼栋<span style='color:green'>"+nameStr+"<span>！", {
								 displayPosition:'middlecenter'
							});
						}else{
							BJUI.alertmsg('error', '添加失败');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#blockName").val();
				$.ajax({
					type:"post",
					url:"block/editBlockById.do?"+new Date(),
					data:$.CurrentDialog.find("#addBlockInfo").serialize(),
					dataType:"text",
					success:function(data){
						if(data==0){
							 BJUI.alertmsg('error', '修改失败');
						}else{
							 BJUI.alertmsg('ok', '成功修改楼栋'+nameStr+"!",{
								 displayPosition:'middlecenter'
								});
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#blockInfo').datagrid('refresh', true);
						}
					}
				});
			}
		}else{
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#addBlockInfo').validator('cleanUp');
 				}
 			});
		}
	});
}

//图片审核
function auditBlockSingle(e,f,g){
	if(auditBlock==true){
		if(g==0){
			return false;
		}else{
			var nameStr=f;
			var audBid=e;
			BJUI.alertmsg('confirm', '你确定要审核楼栋<span style="color:orange">'+nameStr+'</span>吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"block/updateAuditBlock.do?"+new Date(),
						data:{"audBid" : audBid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#blockInfo').datagrid('refresh', true);
							}
						}
					});
				}
			});
		}
	}
}

//审核方法
function auditblockInfo(){
	var blockId=$.CurrentNavtab.find("#blockInfo").data('selectedDatas');
	var audBid="";
	var nameStr="";
	if(typeof(blockId)=="undefined"||blockId.length==0){
		BJUI.alertmsg('info', '请选择需要审核的楼栋');
	}else{
		for(var i=0;i<blockId.length;i++){
			if(blockId[i].blockAudit==0){
				continue;
			}else{
				audBid+=blockId[i].blockId+",";
				nameStr+=blockId[i].blockName+",";
			}
		}
		if(audBid.length>0){
			
			nameStr=nameStr.substring(0, nameStr.length-1);
			BJUI.alertmsg('confirm', '你确定要审核楼栋'+nameStr+'吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"block/updateAuditBlock.do?"+new Date(),
						data:{"audBid" : audBid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#blockInfo').datagrid('refresh', true);
								BJUI.alertmsg('ok', '成功审核楼栋'+nameStr+"!", {
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

//查看楼栋详细信息
function getOneblockDetail(e){
	BJUI.dialog({
        id:'blockDetail',
        target:$.CurrentNavtab.find("#blockDetail"),
        title:'楼栋详细信息',
        width:700,
        height:270,
        mask:true,
        resizable:false,
        onClose:function(){
	    	$.CurrentNavtab.find('#blockInfo').datagrid('selectedRows',false);
	    }
	});
	$.ajax({
		type:"post",
		url:"block/getOneBlockById.do?"+new Date(),
		data:{"blockId":e},
		dataType:"json",
		success:function(data){
			$.CurrentDialog.find("#b_blockName").text(data.blockName);
			$.CurrentDialog.find("#b_courtName").text(data.court.courtName);
			$.CurrentDialog.find("#b_grid").text(data.grid.gridName);
			$.CurrentDialog.find("#b_gridStaff").text(data.gridStaff.gridStaffName);
			$.CurrentDialog.find("#b_unitCount").text(data.unitCount);
			$.CurrentDialog.find("#b_blockAdmintor").text(data.blockAdmintor);
			if(data.blockAudit==0){
				$.CurrentDialog.find("#b_blockAudit").html("<span style='color:green'>已审核</span>");
			}else{
				$.CurrentDialog.find("#b_blockAudit").html("<span style='color:red'>审核中</span>");
			}
			$.CurrentDialog.find("#b_blockAdmintorTel").text(data.blockAdmintorTel);
			$.CurrentDialog.find("#editBlockName").text(data.editBlockName);
			$.CurrentDialog.find("#editBlockDate").text(convertDate(data.editBlockDate));
		}
	});
}

//获取该用户下所有小区名称
function getAllCourtsOfBlock(){
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
	    	$.CurrentDialog.find("#courtId").html(courtStr);
	    	$.CurrentDialog.find('#courtId').selectpicker('refresh');
	    }
	 });
}

//获取该区域下所有网格员称
function getAllGridStaffsOfBlock(e){
	BJUI.ajax('doajax', {
	    url: 'block/getGridStaffName.do',
	    type:'GET',
	    data:{"gridId":e},
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var gridStaffStr="<option value=''>请选择网格员</option>";
	    	for(var i=0;i<data.length;i++){
	    		gridStaffStr+="<option value='" + data[i].gridStaffId + "'>" + data[i].gridStaffName + "</option>";
	    	}
	    	$.CurrentDialog.find("#gridStaffId").html(gridStaffStr);
	    	$.CurrentDialog.find('#gridStaffId').selectpicker('refresh');
	    }
	 });
}

//获取所有该网格下所有网格
function getAllGridsOfBlock(){
	BJUI.ajax('doajax', {
	    url: 'block/getGridName.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var gridStr="<option value=''>请选择网格名称</option>";
	    	for(var i=0;i<data.length;i++){
	    		gridStr+="<option value='" + data[i].gridId + "'>" + data[i].gridName + "</option>";
	    	}
	    	$.CurrentDialog.find("#gridId").html(gridStr);
	    	$.CurrentDialog.find('#gridId').selectpicker('refresh');
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
	
//刷新
function refreshBlock(){
	$.CurrentNavtab.find('#blockInfo').datagrid('refresh', false);
}