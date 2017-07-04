/**
 * author 李宁财
 * content 用户管理javaScript
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
	    	var status=true;
	    	var toolBar=true;
	    	for(var z=0;z<per.length;z++){
	    		if(per[z]=="添加"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addUserInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editUserInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteUserInfo();'>删除</button>";
	    		}
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshUser();'>刷新</button>";
	    	//生成datagrid表格
	    	$.CurrentNavtab.find('#userInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'user/getAllUserByAreaId.do',
	    	    columns: [
                    {name:'headIcon',label:'Icon',align:'center',width:width*0.05,
	    	        	render:function(value, data){
	    	        		var headIcon="";
	    	        		if(value==null||value==""){
	    	        			headIcon="images/user/default.png";
	    	        		}else{
	    	        			headIcon=value;
	    	        		}
	    	        		return "<img src='"+headIcon+"'class='tpl-user-panel-profile-picture' style='width:30px;height:30px;margin:0px;'/>";
	    	        	}
	    	        },
	    	        {name:'userName',label:'用户名',align:'center',width:width*0.1},
	    	        {name:'role',label:'角色',align:'center',width:width*0.1,
	    	        	render:function(value, data){
	    	        		return value.roleName;
	    	        	}
	    	        },
	    	        {name:'area',label:'所属区域',align:'center',width:width*0.21,
	    	        	render:function(value, data){
	    	        		return value.areaName;
	    	        	}
	    	        },
	    	        {name:'mobileTel',label:'联系电话',align:'center',width:width*0.1,
	    	        	render:function(value, data){
	                    	return value+"<br>"+data.officeTel;
	                    }
	    	        },
	    	        {name:'user_last_login',label:'最近登录',align:'center',type: 'date',pattern: 'yyyy-MM-dd HH:mm:ss',width:width*0.12
	    	        },
	    	        {name:'user_login_ip',label:'IP地址',align:'center',width:width*0.1},
	    	        {name:'user_enable',label:'状态',align:'center',width:width*0.06,
	    	        	render:function(value, data){
	    	        		if(value==1){
	    	        			return "<i class='am-icon-check-square-o' style='color:green'></i>";
	    	        		}else{
	    	        			return "<i class='am-icon-minus-square-o' style='color:red'></i>";
	    	        		}
	    	        	}
	    	        },
	    	        {name:'',label:'操作',align:'center',hide:status,width:width*0.16,
	    	        	render:function(value, data){
	    	        		var btnIcon="lock";
	    	        		var btnClass="btn-red";
	    	        		var btnFont="禁用";
	    	        		if(data.user_enable==0){
	    	        			btnIcon="unlock";
	    	        			btnClass="btn-blue";
	    	        			btnFont="启用";
	    	        		}
	    	        		var str="<button type='button' class='"+btnClass+"' data-icon='"+btnIcon+"' style='font-size:12px;'" +
	        						"onclick='changeUserStatus("+data.user_enable+","+data.userId+",\""+data.userName+"\");'>"+btnFont+"</button>";
	    	        		return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;'" +
	    	        				"onclick='getOneUserDetail("+data.userId+");'>详细信息</button>&ensp;"+str;
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
 * 增加用户信息方法
 */
function addUserInfo(){
	BJUI.dialog({
		id:'addUser',
	    target:$.CurrentNavtab.find("#addUser"),
	    title:'添加用户信息',
	    width:655,
	    height:340,
	    mask:true,
	    resizable:false,
	    onClose:function(){
	    	$.CurrentNavtab.find('#userInfo').datagrid('selectedRows',false);
	    },
	    onLoad:function($dialog){
			var that  = this;
			var width  = that.options.width > that.options.minW ? that.options.width : that.options.minW;
			var wW     = $(window).width(),iTop   = that.options.max ? 0 : (($(window).height() - 510) / 3);
			if (width > wW)  width  = wW;
			$("#headIcon").click(function(event){
				$dialog.height(510)
					.css({left:(wW - width) / 2, top:0, opacity:0.1})
					.animate({top:iTop > 0 ? iTop : 0, opacity:1})
					.find('> .dialogContent').height(510 - $('> .dialogHeader', $dialog).outerHeight());
				$dialog.find('> .dialogContent').css({height:(510- $dialog.find('> .dialogHeader').outerHeight())}); 
				$("#userUploadArea").removeClass("hide");
				$.CurrentDialog.find("#filePicker div:last").css({"left":"220px","width":"156px","height":"25px","bottom":"auto","right":"auto"});
				$.CurrentDialog.find("#filePicker2 div:last").css({"left":"10px","width":"94px","height":"27px","bottom":"auto","right":"auto"});
			});
		}
	});
	$.CurrentDialog.find("#areaName").attr("novalidate", "novalidate");
	getAllRoles();
	saveUserInfo();
	
}

/**
 * 编辑用户信息方法
 */
function editUserInfo(){
	var row=$.CurrentNavtab.find('#userInfo').data('selectedDatas');
	if(typeof(row)=="undefined" || row.length!=1){
		BJUI.alertmsg('info', '请选择一条记录进行编辑！');
	}else{
		BJUI.dialog({
			id:'addUser',
		    target:$.CurrentNavtab.find("#addUser"),
		    title:'编辑用户信息',
		    width:655,
		    height:510,
		    mask:true,
		    resizable:false,
		    onClose:function(){
		    	$.CurrentNavtab.find('#userInfo').datagrid('selectedRows',false);
		    },
	     	onLoad:function($dialog){
				$("#userUploadArea").removeClass("hide");
				$("#userBtn > span").text("修改");
			}
		});
		var userName=null;
		$.ajax({
			type : "post",
			url : "user/getOneUserInfo.do?"+new Date(),
			data : {"userId" : row[0].userId},
			dataType : "json",
			cache : false,
			success : function(data) {
				getAllRoles();
				userName=data.userName;
				var areaname=data.area.areaName;
				var zTree = $.fn.zTree.getZTreeObj("areaNameMenu");
				var node = zTree.getNodeByParam("areaId",data.areaId);
				$.CurrentDialog.find('#userId').val(data.userId);
				$.CurrentDialog.find('#userName').val(data.userName);
				$.CurrentDialog.find('#password').val("*********");
				$.CurrentDialog.find('#password').attr("disabled","disabled");
				$.CurrentDialog.find('#mobileTel').val(data.mobileTel);
				$.CurrentDialog.find('#officeTel').val(data.officeTel);
				$.CurrentDialog.find('#roleId').selectpicker('val',data.roleId);
				$.CurrentDialog.find('#headIcon').val(data.headIcon);
				$.CurrentDialog.find('#editUserDate').val(convertDate(data.editUserDate));
				areaname=areaname.substring(areaname.lastIndexOf(",")+1,areaname.length);
				node.checked = true,zTree.updateNode(node);
				$.CurrentDialog.find('#areaName').val(areaname.trim());
				$.CurrentDialog.find('#areaId').val(data.areaId);
				if(data.user_enable==1){
					$.CurrentDialog.find('#user_enable').iCheck('check');
				}else{
					$.CurrentDialog.find('#user_disable').iCheck('check');
				}
				if ($.CurrentDialog.find('#userName').val() == userName) {
					$.CurrentDialog.find('#addUserInfo').validator("setField", "userName", null);
					$.CurrentDialog.find('#addUserInfo').validator("setField", "userName", "用户名:required; username;");
				}
			}
		});
		
		$.CurrentDialog.find('#userName').change(function() {
			$.CurrentDialog.find('#addUserInfo').validator("setField", "userName", null);
			$.CurrentDialog.find('#addUserInfo').validator("setField", "userName", "用户名:required; username; remote[user/validateUsername.do, userName]");
			$.CurrentDialog.find('#userName').on('valid.field', function(e, result){

			});
		});
		saveUserInfo();
	}
}

/**
 * 删除用户信息方法
 */
function deleteUserInfo(){
	var row=$.CurrentNavtab.find('#userInfo').data('selectedDatas');
	var nameStr="";
	var b=false;
	if(typeof(row)=="undefined"||row.length==0){
		BJUI.alertmsg('info', '请选择你要删除的记录！');
	}else{
		if(row.length > 1){
			BJUI.alertmsg('info', '请选择一条记录进行删除！');
		}else{
			if(row[0].role.roleLevel==6){
				b=true;
			}
			nameStr+=row[0].userName;
			BJUI.alertmsg('confirm', "你确定要删除用户<span style='color:orange'>"+nameStr+"</span>吗？", {
			 	okCall: function() {
			 		BJUI.ajax('doajax', {
			 			url : "user/deleteUserByIds.do?"+(new Date()).getTime(),
			 			data : {"userId" : row[0].userId,"isStaff":b,"userName":row[0].userName},
						cache : false,
					    okCallback: function(json, options) {
					    	$.CurrentNavtab.find('#userInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功删除用户<span style='color:green'>"+nameStr+"</span>！", {
								 displayPosition:'middlecenter'
							});
					    },
					    errCallback:function(json, options) {
					    	BJUI.alertmsg('error', '删除失败！');
					    }
					});
			    }
			});
		}
	}
}


/**
 * 查看用户信息方法
 */
function getOneUserDetail(e){
	BJUI.dialog({
		id:'userDetail',
	    target:$.CurrentNavtab.find("#userDetail"),
	    title:'用户详细信息',
	    width:565,
	    height:370,
	    mask:true,
	    resizable:false,
	    onClose:function(){
	    	$.CurrentNavtab.find('#userInfo').datagrid('selectedRows',false);
	    }
	});
	$.ajax({
		type : "get",
		url : "user/getOneUserInfo.do?"+new Date(),
		data : {"userId" : e},
		dataType : "json",
		cache : false,
		success : function(data) {
			$.CurrentDialog.find('#d_userName').text(data.userName);
			$.CurrentDialog.find('#d_password').text("*********");
			$.CurrentDialog.find('#d_area').text(data.area.areaName);
			$.CurrentDialog.find('#d_role').text(data.role.roleName);
			if(data.user_enable==1){
				$.CurrentDialog.find('#d_enable').html("<span style='color:green'>已启用</span>");
			}else{
				$.CurrentDialog.find('#d_enable').html("<span style='color:red'>已禁用</span>");
			}
			$.CurrentDialog.find('#d_mobileTel').text(data.mobileTel);
			$.CurrentDialog.find('#d_officeTel').text(data.officeTel);
			$.CurrentDialog.find('#d_user_last_login').text(convertDate(data.user_last_login));
			$.CurrentDialog.find('#d_user_login_ip').text(data.user_login_ip);
			$.CurrentDialog.find('#d_editTor').text(data.editUserName);
			$.CurrentDialog.find('#d_editTime').text(convertDate(data.editUserDate));
			if(data.headIcon!=null){
				//添加图片
				$.CurrentDialog.find('#d_headIcon img').attr("src",data.headIcon);
			}
			
		}
	});
}


/**
 * 添加和编辑时保存用户信息方法
 */
function saveUserInfo(){
	$.CurrentDialog.find("#userBtn").click(function() {
		if($.CurrentDialog.find("#areaName").val()==null || $.CurrentDialog.find("#areaName").val()==''){
			$.CurrentDialog.find("#areaName").removeAttr("novalidate");
	 	}
		var nameStr="";
		// 判断图片是否上传到服务器中
		if (ajaxFileUpload()) {
		// 验证表单数据是否合法
			if ($.CurrentDialog.find("#addUserInfo").isValid()) {
				if ($.CurrentDialog.find("#userBtn > span").text().trim()=="提交") {
					nameStr+=$.CurrentDialog.find('#userName').val();
					$.ajax({
						type : "post",
						url : "user/addNewUser.do?"+new Date(),
						data : $.CurrentDialog.find("#addUserInfo").serialize(),
						dataType : "json",
						cache : false,
						success : function(data) {
							if (data == "1") {
								BJUI.dialog('closeCurrent');
								$.CurrentNavtab.find('#userInfo').datagrid('refresh', true);
								BJUI.alertmsg('ok', "成功添加用户<span style='color:green'>"+nameStr+"<span>！", {
									 displayPosition:'middlecenter'
								});
							} else {
								BJUI.alertmsg('error', '添加失败！');
							}
						}
					});
				} else {
					nameStr+=$.CurrentDialog.find('#userName').val();
					$.ajax({
						type : "post",
						url : "user/alterUserInfo.do?"+new Date(),
						data : $.CurrentDialog.find("#addUserInfo").serialize(),
						dataType : "text",
						cache : false,
						success : function(data) {
							if (data == "1") {
								BJUI.dialog('closeCurrent');
								$.CurrentNavtab.find('#userInfo').datagrid('refresh', false);
								BJUI.alertmsg('ok', "成功修改用户<span style='color:green'>"+nameStr+"</span>！", {
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
						$('#addUserInfo').validator('cleanUp');
					}
				});
			}
		} else {
			BJUI.alertmsg('error', '图片上传失败！');
		}
	});
}

/**
 * 改变用户启用状态
 */
function changeUserStatus(e,userId,userName){
	var user_enable,infoContent;
	if(e==1){
		infoContent="你确定要禁用<span style='color:orange;'>"+userName+"</span>？";
		user_enable=0;
	}else{
		infoContent="你确定要启用<span style='color:orange;'>"+userName+"</span>？";
		user_enable=1;
	}
	BJUI.alertmsg('confirm', infoContent, {
        okCall: function() {	        	
        	$.ajax({
				type : "post",
				url : "user/alterAuditStatus.do?"+new Date(),
				data : {"user_enable":user_enable,"userId":userId},
				cache : false,
				success : function(data) {
					$.CurrentNavtab.find('#userInfo').datagrid('refresh', false);
				}
			});
        }
	});
}


/**
 * 获取所有角色
 */
function getAllRoles(){
	$.ajax({
		type:"get",
		url:"role/getAllRoles.do?"+new Date(),
		dataType:"json",
		async:false,
		success:function(data){
			var options="";
			for(var i=0;i<data.length;i++){
				if(data[i].roleName=='其他'){
					continue;
				}else{
					options+="<option value='"+data[i].roleId+"'>"+data[i].roleName+"</option>";
				}
			}
			$.CurrentDialog.find('#roleId').html(options);
			$.CurrentDialog.find('#roleId').selectpicker('refresh');
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

//上传图片
function ajaxFileUpload(){
	var b = true;
	var url_list = new Array();
	var num=$.CurrentDialog.find("ul[class=filelist] li").length;
	var uploaded;
	var state_complete;
	var url="";
	for(var i=0;i<num;i++){
		uploaded=$.CurrentDialog.find("ul[class=filelist] li[class=uploaded]");
		state_complete=$.CurrentDialog.find("ul[class=filelist] li[class=state-complete]");
		if(uploaded.length>0){
			for(var j=0;j<uploaded.length;j++){
				url_list.push(uploaded.eq(j).find("img").attr("src"));
			}
		}
		if(state_complete.length>0){
			for(var j=0;j<state_complete.length;j++){
				url_list.push(state_complete.eq(i).find("input[type=hidden][class=upload]").attr("value"));
			}
		}
	}
	for(var i=0;i<url_list.length;i++){
		if(i==0){
			url+=url_list[0];
		}else{
			url+=","+url_list[i].substring(url_list[i].lastIndexOf("/")+1,url_list[i].length);
		}
	}
	if(url==''||url==null){
		b=false;
	}
	$.CurrentDialog.find("#headIcon").val(url);
	return b;
}

/**
 * ztree下拉选择
 * @param e
 * @param treeId
 * @param treeNode
 */
//选择事件
function User_NodeCheck(e, treeId, treeNode) {
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
    	$("#areaId").val(areaId);
    };
}


//刷新页面
function refreshUser(){
	$.CurrentNavtab.find('#userInfo').datagrid('refresh', false);
	$.CurrentNavtab.find('#userInfo').datagrid('selectedRows',false);
}

