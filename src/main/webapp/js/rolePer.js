/**
 * author 李宁财
 * content 分配权限javaScript
 */
 var rolePerData=null;
 $(function(){
	//获取用户权限信息
	 $.ajax({
			type:'get',
			url:'menu/getRolePerInfo.do?'+new Date(),
			data:{"roleId":localStorage.roleId},
		    dataType:'json',
		    cache:false,
		    success:function(data){
		    	rolePerData=data;
		    }
	 });
	 //生成datagrid表格
	 $.CurrentNavtab.find('#rolePerInfo').datagrid({
	 height: '100%',
	 tableWidth:'100',
	 gridTitle: "<i class='am-icon-cog sidebar-nav-link-logo'></i>系统设置<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	   		    "<i class='am-icon-github sidebar-nav-link-logo'></i>角色管理<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	   		    "<i class='am-icon-lock sidebar-nav-link-logo'></i>分配权限<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>"+localStorage.roleName+"",
	 showToolbar: true,
	 toolbarCustom: "<button type='button' style='border-right:none' class='btn-blue' data-icon='save' onclick='saveRolePer();'>保存</button>" +
         			"<button type='button' style='border-right:none' class='btn-orange' data-icon='undo' onclick='cancelRolePer();'>取消</button>" +
         			"<button type='button' style='border-right:none' class='btn-red' data-icon='ban' onclick='initRolePer();'>初始化</button>" +
         			"<button type='button' class='btn-green' data-icon='refresh' onclick='refreshrRolePer();'>刷新</button>",
     local: 'remote',
	 linenumberAll: true,
	 showCheckboxcol:true,
	 hScrollbar:false,
	 filterThead: false,
	 columnMenu: false,
	 dataUrl: 'menu/getAllMenuByMenuId.do',
	 columns: [
	     {name:'menuId',label:'ID',align:'center',width:120},
	     {name:'menuName',label:'菜单名称',align:'center'},
	     {name:'menuUri',label:'菜单地址',align:'center'},
	   	 {name:'parentMenuId',label:'菜单级别',align:'center',
	    	 render:function(value, data){
	    		 if(value==''){
	    			 return "<span style='color:red'>一级菜单</span>";
	    	     }else{
	    	         return "<span style='color:green'>二级菜单</span>";
	    	     }
	    	 }
	   	 },
	     {name:'permissions',label:'<center>功能权限</center>',align:'left',width:380,
	   		 render:function(value, data){
	   			 if(value.length>0 && data.parentMenuId!=null){
	   				 var perBtn="";
		    	     for(var i=0;i<value.length;i++){
		    	    	 perBtn+="　<input type='checkbox' name='perId"+data.parentMenuId+"' " +
	    	    	 		"value='"+value[i].menuPer[0].menuId+value[i].menuPer[0].perId+"' " +
	    	    	 		"data-toggle='icheck' data-label='"+value[i].perName+"' ";
		    	    	 if(rolePerData!=null){
		    	    		 perBtn+=concatCheck(data.menuId,value[i].menuPer[0].perId);
		    	    	 }else{
		    	    		 perBtn+=">";
		    	    	 }
		    	    	 
		    	   	 }
	    	         return perBtn;
	   			 }else if(data.parentMenuId==null){
	   				 if(data.menuName!='我的主页'){
	   					return "　<input type='checkbox' id='checkedAll"+data.menuId+"' name='checkedAll"+data.menuId+"' " +
	   							"data-toggle='icheck' data-label='<span style=\"color: red;\">全选</span>'>";
	   				 }
	    	     }else{
	    	    	 return null;
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
	    	add         : false,
	    }
	});
 });
 
 
function saveRolePer(){
	var pers=$('input[name^=perId][type=checkbox]');
	var perIds = new Array();
	for(var i=0;i<pers.length;i++){
		if(pers[i].checked==true){
			perIds.push(pers[i].value);
		}
	}
	if(perIds.length!=0){
		$.ajax({
			type : "post",
			url : "role/saveRolePer.do?"+new Date(),
			data : {"perIds" : perIds,"roleId":localStorage.roleId},
			dataType : "json",
			cache : false,
			success : function(data) {
				if (data == "0") {
					BJUI.alertmsg('error', '保存失败！');
				} else {
					BJUI.alertmsg('ok', "成功保存角色<span style='color:green'>"+localStorage.roleName+"</span>的权限信息！", {
						displayPosition:'middlecenter'
					});
					$.CurrentNavtab.find('#rolePerInfo').navtab('refresh','navTab001');
				}
			}
		});
	}else{
		BJUI.alertmsg('info', "请勾选<span style='color:green'>"+localStorage.roleName+"</span>的相关权限信息！");
	}
}

//初始化用户权限信息
function initRolePer(){
	$.ajax({
		type : "post",
		url : "role/initRolePer.do?"+new Date(),
		data : {"roleId":localStorage.roleId},
		dataType : "json",
		cache : false,
		success : function(data) {
			if (data == "0") {
				BJUI.alertmsg('error', '初始化失败！');
			} else {
				$.CurrentNavtab.find('#rolePerInfo').navtab('refresh','navTab001');
			}
		}
	});
}

//全选功能
function checkedAll(e){
	var pers=$('input[name=perId'+e+'][type=checkbox]');
//    if($(this).attr("checked")){
    	pers.iCheck('check');
//    }else{
//    	pers.iCheck('uncheck');
//    }	
}

//判断角色是否拥有该权限，有则选中
function concatCheck(menuId,perId){
	 var num=0;
	 for(var j=0;j<rolePerData.length;j++){
		 if(rolePerData[j].menuId==menuId  && rolePerData[j].perId==perId){
			 num++;
		 }
	 }
	 if(num>0){
	 	return "checked='checked'>";
	 }else{
	 	return ">";
	 }
}

 
//刷新页面
 function refreshrRolePer(){
 	$.CurrentNavtab.find('#rolePerInfo').datagrid('refresh', false);
 	$.CurrentNavtab.find('#rolePerInfo').datagrid('selectedRows',false);
 }
 
 //取消修改权限，关闭当前navTab
 function cancelRolePer(){
	 BJUI.navtab('closeCurrentTab', 'navTab001');
 }
 
