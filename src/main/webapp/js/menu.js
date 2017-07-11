/**
 * author 李宁财
 * content 菜单管理javaScript
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
	    	for(var z=0;z<per.length;z++){
	    		if(per[z]=="添加"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addMenuInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editMenuInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteMenuInfo();'>删除</button>";
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshMenu();'>刷新</button>";
	    	
	    	//生成datagrid表格
	    	$.CurrentNavtab.find('#menuInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    linenumberAll: true,
	    	    showCheckboxcol:true,
	    	    hScrollbar:false,
	    	    filterThead: false,
	    	    columnMenu: false,
	    	    dataUrl: 'menu/getAllMenuByMenuId.do',
	    	    columns: [
	    	        {name:'menuIcon',label:'Icon',align:'center',width:width*0.06,
	    	        	 render:function(value, data){
							if(value==''){
								return "<i class='am-icon-th-large'></i>";
							}else{
	    	        		 	return "<i class='"+value+"'></i>";
							}
						}
	    	        },
	    	        {name:'menuId',label:'ID',align:'center',width:width*0.08},
	    	        {name:'menuName',label:'菜单名称',align:'center',width:width*0.18},
	    	        {name:'menuUri',label:'菜单地址',align:'center',width:width*0.18},
	    	        {name:'parentMenuId',label:'菜单级别',align:'center',width:width*0.15,
	    	        	render:function(value, data){
	    	        		if(value != "" && value != null){
	    	        			return "<span style='color:red'>一级菜单</span>";
	    	        		}else{
	    	        			return "<span style='color:green'>二级菜单</span>";
	    	        		}
	    	        	}
	    	        },
	    	        {name:'permissions',label:'<center>菜单功能</center>',align:'left',width:width*0.35,
	    	        	render:function(value, data){
	    	        		if(value.length>0){
	    	        			 var perBtn="";
	    			    	     for(var i=0;i<value.length;i++){
	    			    	    	 perBtn+="　<input type='checkbox' name='checkbox' data-toggle='icheck' data-label='"+value[i].perName+"' checked disabled>";
	    			    	   	 }
	    		    	         return perBtn;
	    	        		}
	    	        	}
	    	        },
	    	    ],
	    	    paging:{pageSize:60, selectPageSize:'30,90', pageCurrent:1, showPagenum:5, totalRow:0},
	    	    isTree:'menuName',
	    	    treeOptions:{
	    	        keys: {
	    	            key       : 'menuId',        
	    	            parentKey : 'parentMenuId',  
	    	            isExpand  : 'isExpand'   
	    	        },
	    	        simpleData  : true,          
	    	        add         : true,
	    	    }
	    	});
	    }
	});	
	
	//获取所有人口基本属性生成复选框
	BJUI.ajax('doajax', {
	    url: 'menu/getAllPermission.do',
	    type:'GET',
	    okalert:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var str="";
	    	for(var i=0;i<data.length;i++){
	    		str+="<input type='checkbox' name='permissions[0].menuPer["+i+"].perId' id='perId"+data[i].perId+"' value='"+data[i].perId+"' " +
	    				"data-toggle='icheck' data-label='"+data[i].perName+"'/>&ensp;";
	    	}
	    	$("#perIcheck").html(str);
	    }
	});
});
 
 
 /**
  * 增加菜单信息方法
  */
 function addMenuInfo(){
 	BJUI.dialog({
 		id:'addMenu',
 	    target:$.CurrentNavtab.find("#addMenu"),
 	    title:'添加菜单',
 	    width:480,
 	    height:370,
 	    mask:true,
 	    resizable:false,
 	    onClose:function(){
 	    	$.CurrentNavtab.find('#menuInfo').datagrid('selectedRows',false);
 	    }
 	});
 	$.CurrentDialog.find("#menuIcon").attr("novalidate", "novalidate");
 	getAllMainMenu();
 	saveMenuInfo();
 }

 /**
  * 编辑菜单信息方法
  */
 function editMenuInfo(){
 	var row=$.CurrentNavtab.find('#menuInfo').data('selectedDatas');
 	if(typeof(row)=="undefined" || row.length!=1){
 		BJUI.alertmsg('info', '请选择一条记录进行编辑！');
 	}else{
 		BJUI.dialog({
 			id:'addMenu',
 		    target:$.CurrentNavtab.find("#addMenu"),
 		    title:'修改菜单',
 		    width:480,
 	 	    height:370,
 	 	    mask:true,
 	 	    resizable:false,
 		    onClose:function(){
 		    	$.CurrentNavtab.find('#menuInfo').datagrid('selectedRows',false);
 		    }
 		});
 		$.CurrentDialog.find("#menuBtn > span").text("修改");
 		$.ajax({
 			type : "get",
 			url : "menu/getOneMenuInfo.do?"+new Date(),
 			data : {"menuId" : row[0].menuId},
 			dataType : "json",
 			cache : false,
 			success : function(data) {
 				getAllMainMenu();
 				var permissions=data.permissions;
 				$.CurrentDialog.find('#menuId').val(data.menuId);
 				$.CurrentDialog.find('#menuName').val(data.menuName);
		    	$.CurrentDialog.find("#menuIcon").css("width","300px");
		    	$.CurrentDialog.find("#menuIcon").val(data.menuIcon);
		        $.CurrentDialog.find("#icon").html("　<i class='"+data.menuIcon+"'></i>");
 				if(data.parentMenuId==null){
 					$.CurrentDialog.find('#menu_1').iCheck('check');
					$.CurrentDialog.find('#parentMenuId').prop('disabled', true);
		    	    $.CurrentDialog.find('#parentMenuId').selectpicker('refresh');
		    	    $.CurrentDialog.find('#menuUri').attr('disabled','disabled');
		    	    $.CurrentDialog.find('input[id^=perId]').attr('disabled','disabled');
				}else{
	    	    	var size=permissions.length;
	    	    	if(size>0){
						for(var i=0;i<size;i++){
							$.CurrentDialog.find('#perId'+permissions[i].perId+'').iCheck('check');
						}
					}
		    	    $.CurrentDialog.find('#menu_2').iCheck('check');
	 				$.CurrentDialog.find('#menuUri').val(data.menuUri);
	 				$.CurrentDialog.find('#parentMenuId').selectpicker('val',data.parentMenuId);
					$.CurrentDialog.find('#parentMenuId').prop('disabled', false);
					$.CurrentDialog.find('#parentMenuId').selectpicker('refresh');
		    	    $.CurrentDialog.find('#menuUri').removeAttr('disabled');
		    	    $.CurrentDialog.find('input[id^=perId]').removeAttr('disabled');
		    	    $.CurrentDialog.find('#menu_2').on('ifChecked', function(event){ 	
		    	    	if(size>0){
							for(var i=0;i<size;i++){
								$.CurrentDialog.find("#perId"+permissions[i].perId+"").iCheck('check');
							}
						}
			    	    $.CurrentDialog.find('#menu_2').iCheck('check');
		 				$.CurrentDialog.find('#menuUri').val(data.menuUri);
		 				$.CurrentDialog.find('#parentMenuId').selectpicker('val',data.parentMenuId);
						$.CurrentDialog.find('#parentMenuId').prop('disabled', false);
						$.CurrentDialog.find('#parentMenuId').selectpicker('refresh');
			    	    $.CurrentDialog.find('#menuUri').removeAttr('disabled');
			    	    $.CurrentDialog.find('input[id^=perId]').removeAttr('disabled');
		    	    });
				}
 			}
 		});
 		saveMenuInfo();
 	}
 }

 /**
  * 删除菜单信息方法
  */
 function deleteMenuInfo(){
 	var row=$.CurrentNavtab.find('#menuInfo').data('selectedDatas');
 	var list_row = new Array();
 	var nameStr="";
 	if(typeof(row)=="undefined"||row.length==0){
 		BJUI.alertmsg('info', '请选择你要删除的记录！');
 	}else{
 		for ( var i = 0; i < row.length; i++) {
 			list_row[i]=row[i].menuId;
 			nameStr+=row[i].menuName+",";
 		}
 		nameStr=nameStr.substring(0, nameStr.length-1);
 		BJUI.alertmsg('confirm', "你确定要删除菜单<span style='color:orange'>"+nameStr+"</span>吗？", {
 		 	okCall: function() {
 		 		$.ajax({
 					type : "post",
 					url : "menu/deleteMenuByIds.do?"+new Date(),
 					data : {"menuIds" : list_row},
 					dataType : "json",
 					cache : false,
 					success : function(data) {
 						if (data == "0") {
 							BJUI.alertmsg('error', '删除失败！');
 						} else {
 							$.CurrentNavtab.find('#menuInfo').datagrid('refresh', true);
 							BJUI.alertmsg('ok', "成功删除菜单<span style='color:green'>"+nameStr+"</span>！", {
 								 displayPosition:'middlecenter'
 							});
 						}
 					}
 				});
 		    }
 		});
 	}
 }


 /**
  * 添加和编辑时保存用户信息方法
  */
 function saveMenuInfo(){
 	$.CurrentDialog.find("#menuBtn").click(function() {
		$.CurrentDialog.find("#menuIcon").removeAttr("novalidate");
 		var nameStr="";
 		// 验证表单数据是否合法
 		if ($.CurrentDialog.find("#addMenuInfo").isValid()) {
 			if ($.CurrentDialog.find("#menuBtn > span").text().trim()=="提交") {
 				nameStr+=$.CurrentDialog.find('#menuName').val();
 				$.ajax({
 					type : "get",
 					url : "menu/addNewMenu.do?"+new Date(),
 					data : $.CurrentDialog.find("#addMenuInfo").serialize(),
 					dataType : "json",
 					cache : false,
 					success : function(data) {
 						if (data == "1") {
 							BJUI.dialog('closeCurrent');
 							$.CurrentNavtab.find('#menuInfo').datagrid('refresh', true);
 							BJUI.alertmsg('ok', "成功添加菜单<span style='color:green'>"+nameStr+"</span>！", {
 								 displayPosition:'middlecenter'
 							});
 						} else {
 							BJUI.alertmsg('error', '添加失败！');
 						}
 					}
 				});
 			} else {
 				nameStr+=$.CurrentDialog.find('#menuName').val();
 				$.ajax({
 					type : "post",
 					url : "menu/alterMenuInfo.do?"+new Date(),
 					data : $.CurrentDialog.find("#addMenuInfo").serialize(),
 					dataType : "text",
 					cache : false,
 					success : function(data) {
 						if (data == "1") {
 							BJUI.dialog('closeCurrent');
 							$.CurrentNavtab.find('#menuInfo').datagrid('refresh', false);
 							BJUI.alertmsg('ok', "成功修改菜单<span style='color:green'>"+nameStr+"</span>！", {
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
 					$('#addMenuInfo').validator('cleanUp');
 				}
 			});
 		}
 	});
 }


function getAllMainMenu(){
	BJUI.ajax('doajax', {
	    url: 'menu/getAllMainMenu.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var menuStr="<option value=''>请选择父级菜单</option>";
	    	for(var i=0;i<data.length;i++){
	    		menuStr+="<option value='" + data[i].menuId + "'>" + data[i].menuName + "</option>";
	    	}
	    	$.CurrentDialog.find("#parentMenuId").html(menuStr);
	    	$.CurrentDialog.find('#parentMenuId').selectpicker('refresh');
	    	$.CurrentDialog.find('#menu_1').on('ifChecked', function(event){ 
	    		$.CurrentDialog.find('#parentMenuId').prop('disabled', true);
	    	    $.CurrentDialog.find('#parentMenuId').selectpicker('refresh');
	    	    $.CurrentDialog.find('#menuUri').attr('disabled','disabled');
	    	    $.CurrentDialog.find('input[id^=perId]').attr('disabled','disabled');
	    	    //禁用验证
	    	    $.CurrentDialog.find("#parentMenuId").attr("novalidate", "novalidate");
	    	    $.CurrentDialog.find("#menuUri").attr("novalidate", "novalidate");
	    	    //将文本框内容清空
	    	    $.CurrentDialog.find('#menuUri').val('');
 				$.CurrentDialog.find('#parentMenuId').selectpicker('val','');
 				$.CurrentDialog.find('input[id^=perId]').iCheck('uncheck');
				$.CurrentDialog.find('#menu_1').iCheck('check');
				$.CurrentDialog.find('#menuUriDiv').removeClass('required');
				$.CurrentDialog.find('#menuUri').attr('placeholder','');
	        }); 
	    	$.CurrentDialog.find('#menu_2').on('ifChecked', function(event){ 
	    		$.CurrentDialog.find('#parentMenuId').prop('disabled', false);
	    	    $.CurrentDialog.find('#parentMenuId').selectpicker('refresh');
	    	    $.CurrentDialog.find('#menuUri').removeAttr('disabled');
	    	    $.CurrentDialog.find('input[id^=perId]').removeAttr('disabled');
	    	    $.CurrentDialog.find('#menuUriDiv').addClass('required');
	    	    $.CurrentDialog.find('#menuUri').attr('placeholder','必填项');
	    	    //启用验证
	    	    $.CurrentDialog.find("#parentMenuId").removeAttr("novalidate");
	    	    $.CurrentDialog.find("#menuUri").removeAttr("novalidate");
	        }); 
	    }
	});
}

//FindGrid JS API 调用
function getAllIcon(obj) {
    BJUI.findgrid({
        empty:false,
        include: 'menuIcon:iconName',
        dialogOptions: {id:'iconData',title:'图标库'},
        gridOptions: {
            local: 'local',
            linenumberAll:true,
//    	    showCheckboxcol:true,
            dataUrl: 'menu/getAllIcon.do',
            columns: [
                {name:'iconName', label:'图标',align:'center',width:50,
   	        	 render:function(value, data){
						return "<i class='"+value+"'></i>";
					}
                },
                {name:'iconName', label:'名称',align:'center',width:100},
                {name:'iconType', label:'类别',align:'center',width:30,type:'select',
                	items:[{'网页':'网页'},{'手势':'手势'},{'运输':'运输'},{'性别':'性别'},
                	       {'文件':'文件'},{'表单':'表单'},{'支付':'支付'},{'图表':'图表'},
                	       {'货币':'货币'},{'视频':'视频'},{'标志':'标志'},{'辅助功能':'辅助功能'},
                	       {'文本编辑':'文本编辑'},{'指示方向':'指示方向'}]
                }
            ],
            paging:{pageSize:30, selectPageSize:'60,90', pageCurrent:1, showPagenum:5, totalRow:0},
        },
        afterSelect:function(data){
        	$("#menuIcon").attr("novalidate", "novalidate");
        	$("#menuIcon").css("width","300px");
        	$("#icon").html("　<i class='"+data.iconName+ "'></i>");
        }
    });
}


//刷新页面
 function refreshMenu(){
 	$.CurrentNavtab.find('#menuInfo').datagrid('refresh', false);
 	$.CurrentNavtab.find('#menuInfo').datagrid('selectedRows',false);
 }
