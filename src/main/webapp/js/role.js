/**
 * 
 * author 李宁财
 * content 角色管理javaScript
 */
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
	    	var toolBar=true;
	    	var status=true;
	    	for(var z=0;z<per.length;z++){
	    		if(per[z]=="添加"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addRoleInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editRoleInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteRoleInfo();'>删除</button>";
	    		}
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshRole();'>刷新</button>";
	    	
	    	//生成datagrid表格
	    	$.CurrentNavtab.find('#roleInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'role/getAllRoleByAreaId.do',
	    	    columns: [
	    	        {name:'roleName',label:'角色名称',align:'center',width:width*0.1},
	    	        {name:'roleLevel',label:'角色级别',align:'center',width:width*0.1,
	    	        	render:function(value, data){
	    	        		switch(value){
	    	        			case 0:
	    	        				  return "最高权限";
	    	        				  break;
	    	        			case 1:
		    	        			  return "省级权限";
		    	        			  break;
	    	        			case 2:
		    	        			  return "市级权限";
		    	        			  break;
	    	        			case 3:
		    	        			  return "区县权限";
		    	        			  break;
	    	        			case 4:
		    	        			  return "镇街权限";
		    	        			  break;
	    	        			case 5:
		    	        			  return "村社权限";
		    	        			  break;
	    	        			case 6:
		    	        			  return "网格权限";
		    	        			  break;
	    	        		}
	    	        	}
	    	        },
	    	        {name:'editRoleName',label:'编&ensp;辑&ensp;人',align:'center',width:width*0.1},
	    	        {name:'editRoleDate',label:'编辑时间',align:'center',type: 'date',pattern: 'yyyy-MM-dd HH:mm:ss',width:width*0.12,
	                    render:function(value, data){
	                    	return convertDate(value);
	                    }
	    	        },
	    	        {name:'roleMemo',label:'备　　注',align:'center',width:width*0.3},
	    	        {name:'userCount',label:'权限人数',align:'center',width:width*0.08,
	    	        	render:function(value, data){
	    	        		return "<a href='javascript:void(0);' onclick='getUsersByRoleId("+data.roleId+",\""+data.roleName+"\");'>"+value+"</a>";
	                    }
	    	        },
	    	        {name:'role_enable',label:'状态',align:'center',width:width*0.05,
	    	        	render:function(value, data){
	    	        		if(value==1){
	    	        			return "<i class='am-icon-check-square-o' style='color:green'></i>";
	    	        		}else{
	    	        			return "<i class='am-icon-minus-square-o' style='color:red'></i>";
	    	        		}		        		
	    	        	}
	    	        },
	    	        {name:'',label:'操作',align:'center',hide:status,width:width*0.15,
	    	        	render:function(value, data){
	    	        		var btnIcon="lock";
	    	        		var btnClass="btn-red";
	    	        		var btnFont="禁用";
	    	        		var str="";
	    	        		if(data.role_enable==0){
	    	        			btnIcon="unlock";
	    	        			btnClass="btn-blue";
	    	        			btnFont="启用";
	    	        			str+="<button type='button' class='btn-orange' data-icon='key' style='font-size:12px;' disabled='disabled'";
	    	        		}else{
	    	        			str+="<button type='button' class='btn-orange' data-icon='key' style='font-size:12px;'";
	    	        		}
	    	        		str+="onclick='assignPermission("+data.roleId+",\""+data.roleName+"\");'>授权</button>&ensp;";
	    	        		str+="<button type='button' class='"+btnClass+"' data-icon='"+btnIcon+"' style='font-size:12px;'" +
	        						"onclick='changeRoleStatus("+data.role_enable+","+data.roleId+",\""+data.roleName+"\");'>"+btnFont+"</button>";
	    	        		return  str;
	    	        	},
	    	        },
	    	    ],
	    	    paging:{pageSize:20, selectPageSize:'10,30,40,50', pageCurrent:1, showPagenum:5, totalRow:0},
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
 
/**
 * 增加角色信息方法
 */
function addRoleInfo(){
	BJUI.dialog({
		id:'addRole',
	    target:$.CurrentNavtab.find("#addRole"),
	    title:'添加角色',
	    width:325,
	    height:380,
	    mask:true,
	    resizable:false,
	    onClose:function(){
	    	$.CurrentNavtab.find('#roleInfo').datagrid('selectedRows',false);
	    }
	});
	saveRoleInfo();
}

/**
 * 编辑信息方法
 */
function editRoleInfo(){
	var row=$.CurrentNavtab.find('#roleInfo').data('selectedDatas');
	if(typeof(row)=="undefined" || row.length!=1){
		BJUI.alertmsg('info', '请选择一条记录进行编辑！');
	}else{
		BJUI.dialog({
			id:'addRole',
		    target:$.CurrentNavtab.find("#addRole"),
		    title:'修改角色',
		    width:325,
		    height:380,
		    mask:true,
		    resizable:false,
		    onClose:function(){
		    	$.CurrentNavtab.find('#roleInfo').datagrid('selectedRows',false);
		    }
		});
		$.CurrentDialog.find("#roleBtn > span").text("修改");
		$.ajax({
			type : "post",
			url : "role/getOneRoleInfo.do?"+new Date(),
			data : {"roleId" : row[0].roleId},
			dataType : "json",
			cache : false,
			success : function(data) {
				$.CurrentDialog.find('#roleId').val(data.roleId);
				$.CurrentDialog.find('#roleName').val(data.roleName);
				$.CurrentDialog.find('#roleLevel').selectpicker('val', data.roleLevel);//默认选中
				$.CurrentDialog.find('#roleMemo').text(data.roleMemo);
				$.CurrentDialog.find('#editRoleDate').val(convertDate(data.editRoleDate));
				if(data.role_enable==1){
					$.CurrentDialog.find('#role_enable').iCheck('check');
				}else{
					$.CurrentDialog.find('#role_disable').iCheck('check');
				}
			}
		});
		saveRoleInfo();
	}
}

/**
 * 删除角色信息方法
 */
function deleteRoleInfo(){
	var row=$.CurrentNavtab.find('#roleInfo').data('selectedDatas');
	var list_row = new Array();
	var nameStr="";
	var forbid="";
	if(typeof(row)=="undefined"||row.length==0){
		BJUI.alertmsg('info', '请选择你要删除的记录！');
	}else{
		for ( var i = 0; i < row.length; i++) {
			if(row[i].roleName=="系统管理员" || row[i].roleName=="其他"){
				forbid+=row[i].roleName;
				continue;
			}
			list_row[i]=row[i].roleId;
			nameStr+=row[i].roleName+",";
		}
		nameStr=nameStr.substring(0, nameStr.length-1);
		if(forbid!=''){
			BJUI.alertmsg('info','系统不允许删除角色(系统管理员、其他)！');
		}else{
			BJUI.alertmsg('confirm', "你确定要删除角色<span style='color:orange'>"+nameStr+"</span>吗？", {
			 	okCall: function() {
			 		$.ajax({
						type : "post",
						url : "role/deleteRoleByIds.do?"+new Date(),
						data : {"roleIds" : list_row},
						dataType : "json",
						cache : false,
						success : function(data) {
							if (data == "0") {
								BJUI.alertmsg('error', '删除失败！');
							} else {
								$.CurrentNavtab.find('#roleInfo').datagrid('refresh', true);
								BJUI.alertmsg('ok', "成功删除角色<span style='color:green'>"+nameStr+"</span>！", {
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

/**
 * 改变角色启用状态
 */
function changeRoleStatus(e,roleId,roleName){
	var role_enable,infoContent;
	if(e==1){
		infoContent="你确定要禁用<span style='color:orange;'>"+roleName+"</span>？";
		role_enable=0;
	}else{
		infoContent="你确定要启用<span style='color:orange;'>"+roleName+"</span>？";
		role_enable=1;
	}
	BJUI.alertmsg('confirm', infoContent, {
        okCall: function() {	        	
        	$.ajax({
				type : "post",
				url : "role/alterRoleUseStatus.do?" + (new Date()).getTime(),
				data : {"role_enable":role_enable,"roleId":roleId},
				cache : false,
				success : function(data) {
					$.CurrentNavtab.find('#roleInfo').datagrid('refresh', false);
				}
			});
        }
	});
}

/**
 * 分配角色权限方法
 */
function assignPermission (id,name){
	BJUI.navtab({
		id:'navTab001',
		url:'pop/rolePer.html',
		title:'分配权限',
		fresh:true
	});
	//本地存储
	localStorage.roleId=id;
	localStorage.roleName=name ;
}

/**
 * 获取某个角色下的所有用户方法
 */
function getUsersByRoleId(roleId,roleName){
	$.ajax({
		type : "get",
		url : "user/getUsersByRoleId.do?"+new Date(),
		data : {"roleId":roleId},
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data!=null){
				BJUI.dialog({
					id:'roleSubUser',
				    target:$.CurrentNavtab.find("#roleSubUser"),
				    title:roleName+'权限人数',
				    width:800,
				    height:500,
				    mask:true,
				    resizable:false,
				    onLoad:function(){
				    	var tableWidth='100%';
				    	if(data.length<8){
				    		tableWidth='98%';
				    	}
				    	$('#userView').datagrid({
				    		height: '100%',
				    	    tableWidth:tableWidth,
				    	    local: 'local',
				    		filterThead: false,
				    	    paging:{pageSize:10, selectPageSize:'15,20,25', pageCurrent:1, showPagenum:5, totalRow:0},
					    	linenumberAll: true,
					    	hScrollbar:false,
					    	columnMenu: false,
							columns: [
						    	{name:'userName',label:'用户名',align:'center',width:110,},
						    	{name:'area',label:'所属区域',align:'center',width:150,
						    	   	render:function(value, data){
						    		return value.areaName;
						        	}
						        },
						        {name:'mobileTel',label:'联系电话',align:'center',width:120,
						         	render:function(value, data){
						         		return value+"<br>"+data.officeTel;
						            }
						        },
				    	        {name:'editUserName',label:'编辑人',align:'center',width:110,},
				    	        {name:'editUserDate',label:'编辑时间',align:'center',width:120,type: 'date',pattern: 'yyyy-MM-dd HH:mm:ss',
						             render:function(value, data){
						            	 return convertDate(value);
						             }
						         },
						         {name:'user_enable',label:'状态',align:'center',width:60,
						        	render:function(value, data){
						           		if(value==1){
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


/**
 * 添加和编辑时保存角色信息方法
 */
function saveRoleInfo(){
	$.CurrentDialog.find("#roleBtn").click(function() {
		var nameStr="";
		// 验证表单数据是否合法
		if ($.CurrentDialog.find("#addRoleInfo").isValid()) {
			if ($.CurrentDialog.find("#roleBtn > span").text().trim()=="提交") {
				nameStr+=$.CurrentDialog.find('#roleName').val();
				$.ajax({
					type : "post",
					url : "role/addNewRole.do?"+new Date(),
					data : $.CurrentDialog.find("#addRoleInfo").serialize(),
					dataType : "json",
					cache : false,
					success : function(data) {
						if (data == "1") {
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#roleInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加角色<span style='color:green'>"+nameStr+"</span>！", {
								 displayPosition:'middlecenter'
							});
						} else {
							BJUI.alertmsg('error', '添加失败！');
						}
					}
				});
			} else {
				nameStr+=$.CurrentDialog.find('#roleName').val();
				$.ajax({
					type : "post",
					url : "role/alterRoleInfo.do?"+new Date(),
					data : $.CurrentDialog.find("#addRoleInfo").serialize(),
					dataType : "text",
					cache : false,
					success : function(data) {
						if (data == "1") {
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#roleInfo').datagrid('refresh', false);
							BJUI.alertmsg('ok', "成功修改角色<span style='color:green'>"+nameStr+"<span>！", {
								 displayPosition:'middlecenter'
							});
						} else {
							BJUI.alertmsg('error', '修改失败！');
						}
					}
				});
			}
		} else {
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#addRoleInfo').validator('cleanUp');
 				}
 			});
		}
	});
}
 

 /**
  * 转换日期方法
  */
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


 
//刷新角色DataGrid页面
 function refreshRole(){
 	$.CurrentNavtab.find('#roleInfo').datagrid('refresh', false);
 	$.CurrentNavtab.find('#roleInfo').datagrid('selectedRows',false);
 }