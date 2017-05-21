/**
 * 刘春晓
 * 小区信息外部js
 */

var auditCourt=false;
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
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addcourtInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editcourtInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deletecourtInfo();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='auditCourtInfo();'>审核</button>";
	    			auditCourt=true;
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
	    	
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshCourt();'>刷新</button>";
	    	
	    	//生成datagrid表格
	    	$.CurrentNavtab.find('#courtInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'court/getAllCourtByAreaId.do',
	    	    columns: [
	    	        {name:'courtName',label:'小区名称',align:'center',width:width*0.12},
	    	        {name:'area',label:'所属区域',align:'center',width:width*0.16,
	    	        	render:function(value, data){
	    	        		return value.areaName;
	    	        	}
	    	        },
	    	        {name:'propertyName',label:'物业公司',align:'center',width:width*0.12},
	    	        {name:'courtArea',label:'小区面积',align:'center',width:width*0.1,
	    	        	render:function(value, data){
	    	        		return value+"平方米";
	    	        	}
	    	        },
	    	        {name:'courtSite',label:'小区地址',align:'center',width:width*0.2},
	    	        {name:'blockCount',label:'楼栋数',align:'center',width:width*0.07,
	    	        	render:function(value,data){
	    	        		return "<a href='javascript:void(0);' onclick='getBlockByCourtId("+data.courtId+");'>"+value+"</a>";
	    	        	}
	    	        },
	    	        {name:'courtAudit',label:'状态',align:'center',width:width*0.08,
	    	        	items:[{"sex":true,"name":"已通过"},{"sex":false,"name":"审核中"}],
	    	        	itemattr:{value:'sex',label:'name'},
	    	        	render:function(value, data){
	    	        		if(value==0){
	    	        			return "<i class='am-icon-check-square-o' style='color:green'></i>";
	    	        		}else{
	    	        			return "<i class='am-icon-minus-square-o' style='color:red'></i>";
	    	        		}
	    	        	}
	    	        },
	    	        {name:'',label:'操作',align:'center',hide:status,width:width*0.15,
	    	        	render:function(value, data){
	    	        		var str="";
	    	        		if(data.courtAudit==1){
	    	        			str+="&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
        						"onclick='auditCourtSingle("+data.courtId+",\""+data.courtName+"\","+data.courtAudit+");'>审核</button>";
	    	        		}else{
	    	        			str+="&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
	    	        		}
	    	        		return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
	    	        				"onclick='getOneCourtDetail("+data.courtId+");'>详细信息</button>"+str;
	    	        	},
	    	        },
	    	    ],
	    	    paging:{pageSize:50, selectPageSize:'40,60,70,80', pageCurrent:1, showPagenum:5, totalRow:0},
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

//查看所属小区的楼栋基本信息
function getBlockByCourtId(e){
	$.ajax({
		type : "get",
		url : "block/getBlockByCourtId.do?"+new Date(),
		data : {"courtId":e},
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data!=null){
				BJUI.dialog({
					id:'courtSuBlock',
				    target:$.CurrentNavtab.find("#courtSuBlock"),
				    title:'楼栋详情',
				    width:800,
				    height:500,
				    mask:true,
				    resizable:false,
				    onLoad:function(){
				    	$('#blockView').datagrid({
				    		height: '100%',
				    	    tableWidth:'100',
				    	    local: 'local',
				    		filterThead: false,
				    	    paging:{pageSize:10, selectPageSize:'15,20,25', pageCurrent:1, showPagenum:5, totalRow:0},
					    	linenumberAll: true,
					    	hScrollbar:false,
					    	columnMenu: false,
							columns: [
								{name:'blockName',label:'楼栋名称',align:'center'},
								{name:'court',label:'所属小区',align:'center',
									render:function(value, data){
										return value.courtName;
									}
								},
								{name:'unitCount',label:'总单元数',align:'center',width:50},
								{name:' ',label:'总户数',align:'center',width:70,
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
										return count;
									}
								},
								{name:'blockAudit',label:'审核状态',align:'center',width:100,
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

//打开新增页面
function addcourtInfo(){
	$.ajax({
		type:"post",
		url:"court/getAreaName.do?"+new Date(),
		dataType:"json",
		success:function(data){
			var areaName=data.areaName;
			var areaId=data.areaId;
			BJUI.dialog({
				id:'addCourt',
				target:$.CurrentNavtab.find("#addCourt"),
				title:'添加小区信息',
				width: 655,
				height: 330,
				mask:true,
				resizable:false,
				onClose:function(){
					$.CurrentNavtab.find('#courtInfo').datagrid('selectedRows',false);
				},
				onLoad:function($dialog){
					var that  = this;
					var width  = that.options.width > that.options.minW ? that.options.width : that.options.minW;
					var wW     = $(window).width(),iTop   = that.options.max ? 0 : (($(window).height() - 500) / 3);
					if (width > wW)  width  = wW;
					$("#courtPic").click(function(event){
						$dialog.height(500)
							.css({left:(wW - width) / 2, top:0, opacity:0.1})
							.animate({top:iTop > 0 ? iTop : 0, opacity:1})
							.find('> .dialogContent').height(500 - $('> .dialogHeader', $dialog).outerHeight());
						$dialog.find('> .dialogContent').css({height:(500- $dialog.find('> .dialogHeader').outerHeight())}); 
						$("#courtUploadArea").removeClass("hide");
						$.CurrentDialog.find("#filePicker div:last").css({"left":"220px","width":"156px","height":"25px","bottom":"auto","right":"auto"});
						$.CurrentDialog.find("#filePicker2 div:last").css({"left":"10px","width":"94px","height":"27px","bottom":"auto","right":"auto"});
					});
				}
			});
			$.CurrentDialog.find("#co_areaName").attr("value",areaName);
			$.CurrentDialog.find("#co_areaId").attr("value",areaId);
			saveCourtInfo();
		}
	});
}

//打开修改页面
function editcourtInfo(){
	var courtId=$.CurrentNavtab.find("#courtInfo").data('selectedDatas');
	if(courtId==null || courtId.length!=1){
		   BJUI.alertmsg('info', '请选择一条需要编辑的小区');
	}else{
		BJUI.dialog({
	        id:'editCourt',
	        target:$.CurrentNavtab.find("#addCourt"),
	        title:'编辑小区信息',
	        width:655,
		    height:500,
		    mask:true,
		    resizable:false,
	        onClose:function(){
	        	$.CurrentNavtab.find('#courtInfo').datagrid('selectedRows',false);
	     	},
	     	onLoad:function($dialog){
				$("#courtUploadArea").removeClass("hide");
				$("#submitBtnco > span").text("修改");
			}
		});
		var upCid="";
		for(var i=0;i<courtId.length;i++){
			upCid=courtId[i].courtId;
		}
		$.ajax({
			type:"post",
			url:"court/getCourtById.do?"+new Date(),
			data:{"upCid":upCid},
			dataType:"json",
			success:function(data){
				var areaname=data.area.areaName;
				var zTree = $.fn.zTree.getZTreeObj("co_areaNameMenu");
				var node = zTree.getNodeByParam("areaId",data.area.areaId);
				areaname=areaname.substring(areaname.lastIndexOf(",")+1,areaname.length);
				node.checked = true; 
				zTree.updateNode(node);
				$.CurrentDialog.find('#co_areaName').val(areaname.trim());
				$.CurrentDialog.find('#co_areaId').val(data.area.areaId);
				$.CurrentDialog.find("#courtId").attr("value",data.courtId);
				$.CurrentDialog.find("#courtName").attr("value",data.courtName);
				$.CurrentDialog.find("#propertyName").attr("value",data.propertyName);
				$.CurrentDialog.find("#courtSite").attr("value",data.courtSite);
				$.CurrentDialog.find("#courtArea").attr("value",data.courtArea);
				$.CurrentDialog.find("#blockCount").attr("value",data.blockCount);
				$.CurrentDialog.find("#editCourtDate").val(convertDate(data.editCourtDate));
				$.CurrentDialog.find('#courtPic').val(data.courtPic);
			}
		});
		saveCourtInfo();
	}
}

//表单验证
function saveCourtInfo(){
	$.CurrentDialog.find("#submitBtnco").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addCourtInfo").isValid()){
			if($.CurrentDialog.find("#submitBtnco > span").text().trim()=="提交"){
				nameStr+=$.CurrentDialog.find("#courtName").val();
				$.ajax({
					type:"post",
					url:"court/saveCourt.do?"+new Date(),
					data:$.CurrentDialog.find("#addCourtInfo").serialize(),
					dataType:"json",
					cache : false,
					success:function(data){
						if (data == "1") {
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#courtInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加小区<span style='color:green'>"+nameStr+"<span>！", {
								 displayPosition:'middlecenter'
							});
						} else {
							BJUI.alertmsg('error', '添加失败！');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#courtName").val();
				$.ajax({
					type:"post",
					url:"court/editCourtById.do?"+new Date(),
					data:$.CurrentDialog.find("#addCourtInfo").serialize(),
					dataType:"text",
					success:function(data){
						if(data==0){
							 BJUI.alertmsg('ok', '保存失败');
						}else{
							 BJUI.alertmsg('ok', '成功修改小区'+nameStr+'!', {
								 displayPosition:'middlecenter'
							 });
							 BJUI.dialog('closeCurrent');
							 $.CurrentNavtab.find('#courtInfo').datagrid('refresh', true);
						}
					}
				});
			}
		}else{
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#addCourtInfo').validator('cleanUp');
 				}
 			});
		}
	});
}

//删除方法
function deletecourtInfo(){
	var courtId=$.CurrentNavtab.find("#courtInfo").data('selectedDatas');
	var delCid="";
	var nameStr="";
	if(typeof(courtId)=="undefined"||courtId.length==0){
		BJUI.alertmsg('info', '请选择一条需要删除的小区');
	}else{
		for(var i=0;i<courtId.length;i++){
			delCid+=courtId[i].courtId+",";
			nameStr+=courtId[i].courtName;
		}
		nameStr=nameStr.substring(0, nameStr.length-1);
		BJUI.alertmsg('confirm', '你确定要删除小区'+nameStr+'吗？', {
			okCall: function() {
				$.ajax({
					type:"post",
					url:"court/deleteCourt.do?"+new Date(),
					data:{"delCid" : delCid},
					dataType:"text",
					success:function(data){
						if(data==0){
							   BJUI.alertmsg('error', '删除失败');
						}else{
							$.CurrentNavtab.find('#courtInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', '成功删除小区'+nameStr+"!", {
								displayPosition:'middlecenter'
							});
						}
					}
				});
			}
		});
	}
}

//图片审核
function auditCourtSingle(e,f,g){
	if(auditCourt==true){
		if(g==0){
			return false;
		}else{
			var nameStr=f;
			var audCid=e;
			BJUI.alertmsg('confirm', '你确定要审核小区<span style="color:orange">'+nameStr+'</span>吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"court/updateAuditCourt.do?"+new Date(),
						data:{"audCid" : audCid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#courtInfo').datagrid('refresh', true);
							}
						}
					});
				}
			});
		}
	}
}

//审核方法
function auditCourtInfo(){
	var courtId=$.CurrentNavtab.find("#courtInfo").data('selectedDatas');
	var audCid="";
	var nameStr="";
	if(typeof(courtId)=="undefined"||courtId.length==0){
		BJUI.alertmsg('info', '请选择需要审核的小区');
	}else{
		for(var i=0;i<courtId.length;i++){
			if(courtId[i].courtAudit==0){
				continue;
			}else{
				audCid+=courtId[i].courtId+",";
				nameStr+=courtId[i].courtName+",";
			}
		}
		if(audCid.length>0){
			nameStr=nameStr.substring(0, nameStr.length-1);
			BJUI.alertmsg('confirm', '你确定要审核小区'+nameStr+'吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"court/updateAuditCourt.do?"+new Date(),
						data:{"audCid" : audCid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#courtInfo').datagrid('refresh', true);
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

//查看方法
function getOneCourtDetail(e){
	BJUI.dialog({
        id:'courtDetail',
        target:$.CurrentNavtab.find("#courtDetail"),
        title:'小区详细信息',
        width:700,
        height:415,
        mask:true,
        resizable:false,
        onClose:function(){
	    	$.CurrentNavtab.find('#courtInfo').datagrid('selectedRows',false);
	    }
	});
	$.ajax({
		type:"post",
		url:"court/getOneCourtById.do?"+new Date(),
		data:{"courtId":e},
		dataType:"json",
		success:function(data){
			$.CurrentDialog.find("#co_courtName").text(data.courtName);
			$.CurrentDialog.find("#co_areaName").text(data.area.areaName);
			$.CurrentDialog.find("#co_propertyName").text(data.propertyName);
			$.CurrentDialog.find("#co_courtSite").text(data.courtSite);
			$.CurrentDialog.find("#co_courtArea").text(data.courtArea+"㎡");
			$.CurrentDialog.find("#co_blockCount").text(data.blockCount);
			$.CurrentDialog.find("#co_editCourtName").text(data.editCourtName);
			$.CurrentDialog.find("#co_editCourtDate").text(convertDate(data.editCourtDate));
			if(data.courtAudit==0){
				$.CurrentDialog.find("#co_courtAudit").html("<span style='color:green'>已审核</span>");
			}else{
				$.CurrentDialog.find("#co_courtAudit").html("<span style='color:red'>审核中</span>");
			}
			if(data.courtPic!=null){
				var img = data.courtPic.split(",");
				var str = "<img alt='' src='"+img[0]+"' style='max-width: 125px;max-height: 85px;'>";
				if(img.length>1){
					var url=img[0].substring(0,img[0].lastIndexOf("/")+1);
					for(var i=1;i<img.length;i++){
						img[i]=url+img[i];
						str += "&ensp;<img alt='' src='"+img[i]+"' style='max-width: 125px;max-height: 85px;'>";
					}
				}
				$.CurrentDialog.find('#td_court_pic').html(str);
			}else{
				$.CurrentDialog.find('#td_court_pic').html("<center>暂无图片信息</center>");
			}
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
function Court_NodeCheck(e, treeId, treeNode) {
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
    	$("#co_areaId").val(areaId);
    };
}

//刷新页面
function refreshCourt(){
	$.CurrentNavtab.find('#courtInfo').datagrid('refresh', false);
}