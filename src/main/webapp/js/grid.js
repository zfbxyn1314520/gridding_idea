/**
 * author 刘春晓
 * content grid.html外部引用js
 */
var auditGrid=false;
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
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addgridInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editgridInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deletegridInfo();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='auditGridInfo();'>审核</button>";
	    			auditGrid=true;
	    		}
	    		//判断用户是否有查看权限，如果没有则隐藏"操作"列
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshGrid();>刷新</button>";
	    	
	    	//生成datagrid表格
	    	$.CurrentNavtab.find('#gridInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'grid/getAllGridByAreaId.do',
	    	    columns: [
	    	        {name:'gridName',label:'网格名称',align:'center',width:width*0.17},
	    	        {name:'area',label:'所属区域',align:'center',width:width*0.17,
	    	        	render:function(value, data){
	    	        		return value.areaName;
	    	        	}
	    	        },
	    	        {name:'gridScope',label:'服务范围',align:'center',width:width*0.3},
	    	        {name:'gridStaffCount',label:'网格员人数',align:'center',width:width*0.09,
	    	        	render:function(value,data){
	    	        		return "<a href='javascript:void(0);' onclick='getGridStaffByGridId("+data.gridId+");'>"+value+"</a>";
	    	        	}
	    	        },
	    	        {name:'gridAudit',label:'状态',align:'center',width:width*0.09,
	    	        	render:function(value, data){
	    	        		if(value==0){
	    	        			return "<i class='am-icon-check-square-o' style='color:green'></i>";
	    	        		}else{
	    	        			return "<i class='am-icon-minus-square-o' style='color:red'></i>";
	    	        		}
	    	        	}
	    	        },
	    	        {name:'',label:'操作',align:'center',hide:status,width:width*0.18,
	    	        	render:function(value, data){	    
	    	        		var str="";
	    	        		if(data.gridAudit==1){
	    	        			str+="&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
        						"onclick='auditGridSingle("+data.gridId+",\""+data.gridName+"\","+data.gridAudit+");'>审核</button>";
	    	        		}else{
	    	        			str+="&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
	    	        		}
	    	        		return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
	    	        				"onclick='getOneGridDetail("+data.gridId+");'>详细信息</button>"+str;
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


//网格员基本信息弹窗
function getGridStaffByGridId(e){
	$.ajax({
		type : "get",
		url : "gridStaff/getGridStaffByGridId.do?"+new Date(),
		data : {"gridId":e},
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data!=null){
				BJUI.dialog({
					id:'gridSuGridStaff',
				    target:$.CurrentNavtab.find("#gridSuGridStaff"),
				    title:'网格员详情',
				    width:800,
				    height:500,
				    mask:true,
				    resizable:false,
				    onLoad:function(){
				    	$('#gridStaffView').datagrid({
				    		height: '100%',
				    	    tableWidth:'100%',
				    	    local: 'local',
				    		filterThead: false,
				    	    paging:{pageSize:10, selectPageSize:'15,20,25', pageCurrent:1, showPagenum:5, totalRow:0},
					    	linenumberAll: true,
					    	hScrollbar:false,
					    	columnMenu: false,
					    	 columns: [
						    	        {name:'gridStaffId',label:'ID',align:'center'},
						    	        {name:'gridStaffName',label:'姓名',align:'center'},
						    	        {name:'gridPost',label:'职务',align:'center',
						    	        	render:function(value, data){
						    	        		return value.gridPostName;
						    	        	}
						    	        },
						    	        {name:'gridStaffSex',label:'性别',align:'center'},
						    	        {name:'gridStaffScope',label:'服务范围',align:'center'},
						    	        {name:'gridStaffAudit',label:'审核状态',align:'center',
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

//打开添加弹窗
function addgridInfo(){
	$.ajax({
		type:"post",
		url:"grid/getAreaName.do?"+new Date(),
		dataType:"json",
		success:function(data){
			var areaName=data.areaName;
			var areaId=data.areaId;
			BJUI.dialog({
				id:'addGrid',
				target:$.CurrentNavtab.find("#addGrid"),
				title:'新增网格',
				width: 375,
				height: 400,
				mask:true,
				resizable:false,
				onClose:function(){
					$.CurrentNavtab.find('#gridInfo').datagrid('selectedRows',false);
				}
			});
			$.CurrentDialog.find("#gr_areaName").attr("value",areaName);
			$.CurrentDialog.find("#gr_areaId").attr("value",areaId);
			saveGridInfo();
		}
	});
}

//打开修改页面
function editgridInfo(){
	var gridId=$.CurrentNavtab.find("#gridInfo").data('selectedDatas');
	if(gridId==null || gridId.length!=1){
		   BJUI.alertmsg('info', '请选择一条需要编辑的网格');
	}else{
		BJUI.dialog({
	        id:'editGrid',
	        target:$.CurrentNavtab.find("#addGrid"),
	        title:'修改网格',
	        width:375,
		    height:400,
		    mask:true,
		    resizable:false,
	        onClose:function(){
	        	$.CurrentNavtab.find('#gridInfo').datagrid('selectedRows',false);
	     	}
		});
		$.CurrentDialog.find("#submitBtngr > span").text("修改");
		var upGrid="";
		for(var i=0;i<gridId.length;i++){
			upGrid=gridId[i].gridId;
		}
		$.ajax({
			type:"post",
			url:"grid/getGridById.do?"+new Date(),
			data:{"upGrid":upGrid},
			dataType:"json",
			success:function(data){
				var areaname=data.area.areaName;
				var zTree = $.fn.zTree.getZTreeObj("gr_areaNameMenu");
				var node = zTree.getNodeByParam("areaId",data.area.areaId);
				areaname=areaname.substring(areaname.lastIndexOf(",")+1,areaname.length);
				node.checked = true; 
				zTree.updateNode(node);
				$.CurrentDialog.find('#gr_areaName').val(areaname.trim());
				$.CurrentDialog.find('#gr_areaId').val(data.area.areaId);
				$.CurrentDialog.find("#gr_gridName").attr("value",data.gridName);
				$.CurrentDialog.find("#gr_gridId").attr("value",data.gridId);
				$.CurrentDialog.find("#gr_gridScope").text(data.gridScope);
				$.CurrentDialog.find("#editGridDate").val(convertDate(data.editGridDate));
				$.CurrentDialog.find("#gr_gridAdmitor").val(data.gridAdmitor);
			}
		});
		saveGridInfo();
	}
}


//表单验证
function saveGridInfo(){
	$.CurrentDialog.find("#submitBtngr").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addGridInfo").isValid()){
			if($.CurrentDialog.find("#submitBtngr > span").text().trim()=="提交"){
				nameStr+=$.CurrentDialog.find("#gr_gridName").val();
				$.ajax({
					type:"post",
					url:"grid/saveGrid.do?"+new Date(),
					data:$.CurrentDialog.find("#addGridInfo").serialize(),
					dataType:"json",
					cache : false,
					success:function(data){
						if (data == "1") {
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#gridInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加网格<span style='color:green'>"+nameStr+"<span>！", {
								 displayPosition:'middlecenter'
							});
						} else {
							BJUI.alertmsg('error', '添加失败！');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#gr_gridName").val();
				$.ajax({
					type:"post",
					url:"grid/editGridById.do?"+new Date(),
					data:$.CurrentDialog.find("#addGridInfo").serialize(),
					dataType:"text",
					success:function(data){
						if(data==0){
							 BJUI.alertmsg('ok', '保存失败');
						}else{
							 BJUI.alertmsg('ok', '成功修改网格'+nameStr+'!', {
								 displayPosition:'middlecenter'
							 });
							 BJUI.dialog('closeCurrent');
							 $.CurrentNavtab.find('#gridInfo').datagrid('refresh', true);
						}
					}
				});
			}
		}else{
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#addGridInfo').validator('cleanUp');
 				}
 			});
		}
	});
}


//图片审核
function auditGridSingle(e,f,g){
	if(auditGrid==true){
		if(g==0){
			return false;
		}else{
			var nameStr=f;
			var audGrid=e;
			BJUI.alertmsg('confirm', '你确定要审核网格<span style="color:orange">'+nameStr+'</span>吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"grid/updateAuditGrid.do?"+new Date(),
						data:{"audGrid" : audGrid},
						dataType:"text",
						success:function(data){
							if(data==0){
								   BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#gridInfo').datagrid('refresh', true);
							}
						}
					});
				}
			});
		}
	}
}

//审核
function auditGridInfo(){
	var gridId=$.CurrentNavtab.find("#gridInfo").data('selectedDatas');
	var audGrid="";
	var nameStr="";
	if(typeof(gridId)=="undefined"||gridId.length==0){
		BJUI.alertmsg('info', '请选择需要审核的网格');
	}else{
		for(var i=0;i<gridId.length;i++){
			if(gridId[i].gridAudit==0){
				continue;
			}else{
				audGrid+=gridId[i].gridId+",";
				nameStr+=gridId[i].gridName+",";
			}
		}
		if(audGrid.length>0){
			nameStr=nameStr.substring(0, nameStr.length-1);
			BJUI.alertmsg('confirm', '你确定要审核网格'+nameStr+'吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"grid/updateAuditGrid.do?"+new Date(),
						data:{"audGrid" : audGrid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#gridInfo').datagrid('refresh', true);
								BJUI.alertmsg('ok', '成功审核小区'+nameStr+"!", {
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

//查看
function getOneGridDetail(e){
	BJUI.dialog({
        id:'gridDetail',
        target:$.CurrentNavtab.find("#gridDetail"),
        title:'查看网格',
        width:700,
        height:310,
        mask:true,
        resizable:false,
        onClose:function(){
	    	$.CurrentNavtab.find('#gridInfo').datagrid('selectedRows',false);
	    }
	});
	$.ajax({
		type:"post",
		url:"grid/getOneGridById.do?"+new Date(),
		data:{"gridId":e},
		dataType:"json",
		success:function(data){
			$.CurrentDialog.find("#sg_areaName").text(data.area.areaName);
			$.CurrentDialog.find("#sg_gridName").text(data.gridName);
			$.CurrentDialog.find("#sg_gridAdmitor").text(data.gridAdmitor);
			$.CurrentDialog.find("#sg_gridStaffCount").text(data.gridStaffCount+"人");
			$.CurrentDialog.find("#sg_gridScope").text(data.gridScope);
			$.CurrentDialog.find("#sg_editGridName").text(data.editGridName);
			$.CurrentDialog.find("#sg_editGridDate").text(convertDate(data.editGridDate));
			if(data.gridAudit==0){
				$.CurrentDialog.find("#sg_gridAudit").html("<span style='color:green'>已审核</span>");
			}else{
				$.CurrentDialog.find("#sg_gridAudit").html("<span style='color:red'>审核中</span>");
			}
		}
	});
}

//删除
function deletegridInfo(){
	var gridId=$.CurrentNavtab.find("#gridInfo").data('selectedDatas');
	var deGrid="";
	var nameStr="";
	if(typeof(gridId)=="undefined"||gridId.length==0){
		BJUI.alertmsg('info', '请选择需要删除的网格');
	}else{
		for(var i=0;i<gridId.length;i++){
			deGrid+=gridId[i].gridId;
			nameStr+=gridId[i].gridName+",";
		}
		nameStr=nameStr.substring(0, nameStr.length-1);
		BJUI.alertmsg('confirm', '你确定要删除网格'+nameStr+'吗？', {
			okCall: function() {
				BJUI.dialog({
			        id:'editBlockGrid',
			        target:$.CurrentNavtab.find("#editBlockGrid"),
			        width:565,
				    height:600,
				    mask:true,
				    resizable:false,
			        title:"配置新网格"
				});
				getAllGrid(deGrid);
				saveBSGridInfo(deGrid);
			}
		});
	}
}

//验证重新配置表单
function saveBSGridInfo(e){
	$.CurrentDialog.find("#submitBtngrt").click(function() {
		if($.CurrentDialog.find("#editBlockGridInfo").isValid()){
			$.ajax({
				type:"post",
				url:"block/editBlockGrid.do?"+new Date(),
				data:{"oldId":e,"newGBId":$.CurrentDialog.find("#block_gridId").val(),"newSGId":$.CurrentDialog.find("#gridStaff_gridId").val(),"newPGId":$.CurrentDialog.find("#pop_gridId").val()},
				dataType:"text",
				cache : false,
				success:function(data){
					if(data=='删除失败！'){
						BJUI.alertmsg('error', ""+data+"");
					}else{
						$.CurrentNavtab.find('#gridInfo').datagrid('refresh', true);
						BJUI.alertmsg('ok', ''+data+'',{
							 displayPosition:'middlecenter'
							});
						BJUI.dialog('closeCurrent');
					}
				}
			});
		}else{
			BJUI.alertmsg('warn', '验证未通过！');
		}
	});
}

//获取除删除网格外所有网格
function getAllGrid(e){
	BJUI.ajax('doajax', {
	    url: 'grid/getAllGrid.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var grStr="<option value=''>请选择</option>";
	    	for(var i=0;i<data.length;i++){
	    		if(data[i].gridId!=e){
	    			grStr+="<option value='" + data[i].gridId + "'>" + data[i].gridName + "</option>";
	    		}
	    	}
	    	$.CurrentDialog.find("#block_gridId").html(grStr);
	    	$.CurrentDialog.find('#block_gridId').selectpicker('refresh');
	    	$.CurrentDialog.find("#gridStaff_gridId").html(grStr);
	    	$.CurrentDialog.find('#gridStaff_gridId').selectpicker('refresh');
	    	$.CurrentDialog.find("#pop_gridId").html(grStr);
	    	$.CurrentDialog.find('#pop_gridId').selectpicker('refresh');
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

/**
 * ztree下拉选择
 * @param e
 * @param treeId
 * @param treeNode
 */
//选择事件
function Grid_NodeCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getCheckedNodes(true);
    var ids = '', names = '';
    var areaId='';
    for (var i = 0; i < nodes.length; i++) {
        ids   += ','+ nodes[i].id;
        names += ','+ nodes[i].name;
        areaId+=','+ nodes[i].areaId;
    }
    if (ids.length > 0) {
        ids = ids.substr(1), names = names.substr(1);areaId=areaId.substr(1);
    }
    var $from = $('#'+ treeId).data('fromObj');
    if ($from && $from.length) {
    	$from.val(names);
    	$("#gr_areaId").val(areaId);
    };
}

function refreshGrid(){
	$.CurrentNavtab.find('#gridInfo').datagrid('refresh', false);
}