/**
 * author 李宁财
 */

var audit=false;
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
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addstoreInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editStoreInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteStoreInfo();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='auditStoreInfo();'>审核</button>";
	    			audit=true;
	    		}
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有查看权限，如果没有则隐藏"操作"列
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshStore();'>刷新</button>";
	    	
	    	//生成datagrid表格
	    	$.CurrentNavtab.find('#storeInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'store/getAllStoreByAreaId.do',
	    	    columns: [
	    	        {name:'area',label:'所属区域',align:'center',width:width*0.2,
	    	        	render:function(value, data){
	    	        		return value.areaName;
	    	        	}
	    	        },
	    	        {name:'storeName',label:'商铺名称',align:'center',width:width*0.3},
	    	        {name:'linlkMan',label:'商铺店主',align:'center',width:width*0.12},
	    	        {name:'storeArea',label:'商铺面积',align:'center',width:width*0.12,
	    	        	render:function(value, data){
	    	        		return value+"平方米";
	    	        	}
	    	        },
	    	        {name:'storeAudit',label:'状态',align:'center',width:width*0.1,
	    	        	render:function(value, data){
	    	        		if(value==0){
	    	        			return "<i class='am-icon-check-square-o' style='color:green'></i>";
	    	        		}else{
	    	        			return "<i class='am-icon-minus-square-o' style='color:red'></i>";
	    	        		}
	    	        	}
	    	        },
	    	        {name:'',label:'操作',align:'center',hide:status,width:width*0.16,
	    	        	render:function(value, data){	    	      
	    	        		var str="";
	    	        		if(data.storeAudit==1){
	    	        			str+="&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
        						"onclick='auditStoreSingle("+data.storeId+",\""+data.storeName+"\","+data.storeAudit+");'>审核</button>";
	    	        		}else{
	    	        			str+="&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
	    	        		}
	    	        		return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
	    	        				"onclick='getOneStoreDetail("+data.storeId+");'>详细信息</button>"+str;
	    	        	},
	    	        },
	    	    ],
	    	    paging:{pageSize:30, selectPageSize:'10,30,40,50', pageCurrent:1, showPagenum:5, totalRow:0},
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

//打开查看商铺弹窗
function getOneStoreDetail(e){
	BJUI.dialog({
        id:'storeDetail',
        target:$.CurrentNavtab.find("#storeDetail"),
        title:'查看商铺',
        width:700,
        height:475,
        mask:true,
        resizable:false,
        onClose:function(){
	    	$.CurrentNavtab.find('#storeInfo').datagrid('selectedRows',false);
	    }
	});
	$.ajax({
		type:"post",
		url:"store/getOneStoreById.do?"+new Date(),
		data:{"storeId":e},
		dataType:"json",
		success:function(data){
			$.CurrentDialog.find("#s_Name").text(data.storeName);
			$.CurrentDialog.find("#sa_Name").text(data.area.areaName);
			$.CurrentDialog.find("#s_storeScope").text(data.storeScope);
			$.CurrentDialog.find("#s_storeArea").text(data.storeArea+"平方米");
			$.CurrentDialog.find("#s_linlkMan").text(data.linlkMan);
			$.CurrentDialog.find("#s_storeTel").text(data.storeTel);
			$.CurrentDialog.find("#s_storeSite").text(data.storeSite);
			$.CurrentDialog.find("#s_editStoreName").text(data.editStoreName);
			$.CurrentDialog.find("#s_editStoreDate").text(convertDate(data.editStoreDate));
			if(data.storeAudit==0){
				$.CurrentDialog.find("#s_storeAudit").html("<span style='color:green'>已审核</span>");
			}else{
				$.CurrentDialog.find("#s_storeAudit").html("<span style='color:red'>审核中</span>");
			}
			if(data.storePic!=null){
				var img = data.storePic.split(",");
				var str = "<img alt='' src='"+img[0]+"' style='max-width: 125px;max-height: 85px;'>";
				if(img.length>1){
					var url=img[0].substring(0,img[0].lastIndexOf("/")+1);
					for(var i=1;i<img.length;i++){
						img[i]=url+img[i];
						str += "&ensp;<img alt='' src='"+img[i]+"' style='max-width: 125px;max-height: 85px;'>";
					}
				}
				$.CurrentDialog.find('#td_store_pic').html(str);
			}else{
				$.CurrentDialog.find('#td_store_pic').html("<center>暂无图片信息</center>");
			}
		}
	});
	
}

//打开增加商铺页面
function addstoreInfo(){
	$.ajax({
		type:"post",
		url:"store/getAreaName.do?"+new Date(),
		dataType:"json",
		success:function(data){
			var areaName=data.areaName;
			var areaId=data.areaId;
			BJUI.dialog({
				id:'addStore',
				target:$.CurrentNavtab.find("#addStore"),
				title:'添加商铺',
				width: 655,
				height: 390,
				mask:true,
				resizable:false,
				onClose:function(){
					$.CurrentNavtab.find('#storeInfo').datagrid('selectedRows',false);
				},
				onLoad:function($dialog){
					var that  = this;
					var width  = that.options.width > that.options.minW ? that.options.width : that.options.minW;
					var wW     = $(window).width(),iTop   = that.options.max ? 0 : (($(window).height() - 570) / 3);
					if (width > wW)  width  = wW;
					$("#storePic").click(function(event){
						$dialog.height(570)
							.css({left:(wW - width) / 2, top:0, opacity:0.1})
							.animate({top:iTop > 0 ? iTop : 0, opacity:1})
							.find('> .dialogContent').height(570 - $('> .dialogHeader', $dialog).outerHeight());
						$dialog.find('> .dialogContent').css({height:(570- $dialog.find('> .dialogHeader').outerHeight())}); 
						$("#storeUploadArea").removeClass("hide");
						$.CurrentDialog.find("#filePicker div:last").css({"left":"220px","width":"156px","height":"25px","bottom":"auto","right":"auto"});
						$.CurrentDialog.find("#filePicker2 div:last").css({"left":"10px","width":"94px","height":"27px","bottom":"auto","right":"auto"});
					});
				}
			});
			$.CurrentDialog.find("#store_areaName").attr("value",areaName);
			$.CurrentDialog.find("#store_areaId").attr("value",areaId);
			saveStoreInfo();
		}
	});
}


//打开修改商铺弹窗
function editStoreInfo(){
	var storeId=$.CurrentNavtab.find("#storeInfo").data('selectedDatas');
	if(storeId==null || storeId.length!=1){
		   BJUI.alertmsg('info', '请选择一条需要编辑的商铺');
	}else{
		BJUI.dialog({
	        id:'editStore',
	        target:$.CurrentNavtab.find("#addStore"),
	        title:'修改商铺',
	        width:655,
		    height:570,
		    mask:true,
		    resizable:false,
	        onClose:function(){
	        	$.CurrentNavtab.find('#storeInfo').datagrid('selectedRows',false);
	     	},
	     	onLoad:function($dialog){
				$("#storeUploadArea").removeClass("hide");
				$("#submitBtns > span").text("修改");
			}
		});
		var upSid="";
		for(var i=0;i<storeId.length;i++){
			upSid=storeId[i].storeId;
		}
		$.ajax({
			type:"post",
			url:"store/getStoreById.do?"+new Date(),
			data:{"upSid":upSid},
			dataType:"json",
			success:function(data){
				var areaname=data.area.areaName;
				var zTree = $.fn.zTree.getZTreeObj("store_areaNameMenu");
				var node = zTree.getNodeByParam("areaId",data.area.areaId);
				node.checked = true,zTree.updateNode(node);
				areaname=areaname.substring(areaname.lastIndexOf(",")+1,areaname.length);
				$.CurrentDialog.find('#s_areaName').val(areaname.trim());
				$.CurrentDialog.find('#s_areaId').val(data.area.areaId);
				$.CurrentDialog.find('#store_areaName').val(areaname.trim());
				$.CurrentDialog.find('#store_areaId').val(data.area.areaId);
				$.CurrentDialog.find("#storeId").attr("value",data.storeId);
				$.CurrentDialog.find("#storeName").attr("value",data.storeName);
				$.CurrentDialog.find("#storeScope").text(data.storeScope);
				$.CurrentDialog.find("#storeArea").attr("value",data.storeArea);
				$.CurrentDialog.find("#editStoreDate").val(convertDate(data.editStoreDate));
				$.CurrentDialog.find('#storePic').val(data.storePic);
				$.CurrentDialog.find("#linlkMan").attr("value",data.linlkMan);
				$.CurrentDialog.find("#storeTel").attr("value",data.storeTel);
				$.CurrentDialog.find("#storeSite").attr("value",data.storeSite);
			}
		});
		saveStoreInfo();
	}
}

//表单验证
function saveStoreInfo(){
	$.CurrentDialog.find("#submitBtns").click(function() {
		var nameStr="";
//		if (ajaxFileUpload()) {
		if($.CurrentDialog.find("#addStoreInfo").isValid()){
			//this.holdSubmit();
			if ($.CurrentDialog.find("#submitBtns > span").text().trim()=="提交") {
				nameStr+=$.CurrentDialog.find("#storeName").val();
				$.ajax({
					type:"post",
					url:"store/saveStore.do?"+new Date(),
					data:$.CurrentDialog.find("#addStoreInfo").serialize(),
					dataType:"json",
					cache : false,
					success:function(data){
						if (data == "1") {
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#storeInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加商鋪<span style='color:green'>"+nameStr+"<span>！", {
								 displayPosition:'middlecenter'
							});
						} else {
							BJUI.alertmsg('error', '添加失败！');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#storeName").val();
				$.ajax({
					type:"post",
					url:"store/editStoreById.do?"+new Date(),
					data:$.CurrentDialog.find("#addStoreInfo").serialize(),
					dataType:"text",
					success:function(data){
						if(data==0){
							 BJUI.alertmsg('ok', '保存失败');
						}else{
							 BJUI.alertmsg('ok', '成功修改商铺'+nameStr+'!', {
								 displayPosition:'middlecenter'
							 });
							 BJUI.dialog('closeCurrent');
							 $.CurrentNavtab.find('#storeInfo').datagrid('refresh', true);
						}
					}
				});
			}
		}else{
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#addStoreInfo').validator('cleanUp');
 				}
 			});
		}
//		} else {
//			BJUI.alertmsg('error', '图片上传失败！');
//		}
	});
}

//删除商铺
function deleteStoreInfo(){
	var storeId=$.CurrentNavtab.find("#storeInfo").data('selectedDatas');
	var delSid="";
	var nameStr="";
	if(typeof(storeId)=="undefined"||storeId.length==0){
		BJUI.alertmsg('info', '请选择一条需要删除的商铺');
	}else{
		for(var i=0;i<storeId.length;i++){
			delSid+=storeId[i].storeId+",";
			nameStr+=storeId[i].storeName;
		}
		nameStr=nameStr.substring(0, nameStr.length-1);
		BJUI.alertmsg('confirm', '你确定要删除商铺'+nameStr+'吗？', {
			okCall: function() {
				$.ajax({
					type:"post",
					url:"store/deleteStore.do?"+new Date(),
					data:{"delSid" : delSid},
					dataType:"text",
					success:function(data){
						if(data==0){
							   BJUI.alertmsg('error', '删除失败');
						}else{
							$.CurrentNavtab.find('#storeInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', '成功删除商铺'+nameStr+"!", {
								displayPosition:'middlecenter'
							});
						}
					}
				});
			}
		});
	}
}

//点击图片审核
function auditStoreSingle(e,f,g){
	if(audit==true){
		if(g==0){
			return false;
		}else{
			var nameStr=f;
			var audSid=e;
			BJUI.alertmsg('confirm', '你确定要审核商铺<span style="color:orange">'+nameStr+'</span>吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"store/updateAuditStore.do?"+new Date(),
						data:{"audSid" : audSid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#storeInfo').datagrid('refresh', true);
							}
						}
					});
				}
			});
		}
	}
	
}

//审核
function auditStoreInfo(){
	var storeId=$.CurrentNavtab.find("#storeInfo").data('selectedDatas');
	var audSid="";
	var nameStr="";
	if(typeof(storeId)=="undefined"||storeId.length==0){
		BJUI.alertmsg('info', '请选择需要审核的商铺');
	}else{
		for(var i=0;i<storeId.length;i++){
			if(storeId[i].storeAudit==0){
				continue;
			}else{
				audSid+=storeId[i].storeId+",";
				nameStr+=storeId[i].storeName+",";
			}
		}
		if(audSid.length>0){
			nameStr=nameStr.substring(0, nameStr.length-1);
			BJUI.alertmsg('confirm', '你确定要审核商铺'+nameStr+'吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"store/updateAuditStore.do?"+new Date(),
						data:{"audSid" : audSid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#storeInfo').datagrid('refresh', true);
								BJUI.alertmsg('ok', '成功审核商铺'+nameStr+"!", {
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
	$.CurrentDialog.find("#storePic").val(url);
	return b;
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
function Store_NodeCheck(e, treeId, treeNode) {
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
    	$("#store_areaId").val(areaId);
    };
}

//刷新页面
function refreshStore(){
	$.CurrentNavtab.find('#storeInfo').datagrid('refresh', false);
	$.CurrentNavtab.find('#storeInfo').datagrid('selectedRows',false);
}

