/**
 * road.html页面的外部js代码
 * author 刘春晓
 */

var auditRoad=false;
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
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addroadInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editRoadInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteRoadInfo();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='auditRoadInfo();'>审核</button>";
	    			auditRoad=true;
	    		}
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshRoad();'>刷新</button>";
	    	
	    	//生成datagrid表格
	    	$('#roadInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'road/getAllRoadByAreaId.do',
	    	    columns: [
	    	        {name:'area',label:'所属区域',align:'center',width:width*0.16,
	    	        	render:function(value, data){
	    	        		return value.areaName;
	    	        	}
	    	        },
	    	        {name:'roadGrade',label:'道路等级',align:'center',width:width*0.08,
	    	        	render:function(value, data){
	    	        		return value.roadGradeName;
	    	        	}
	    	        },
	    	        {name:'roadName',label:'道路名称',align:'center',width:width*0.18},
	    	        {name:'roadLength',label:'长度',align:'center',width:width*0.1,
	    	        	render:function(value, data){
	    	        		return value+"米";
	    	        	}
	    	        },
	    	        {name:'roadWidth',label:'宽度',align:'center',width:width*0.1,
	    	        	render:function(value, data){
	    	        		return value+"米";
	    	        	}
	    	        },
	    	        {name:'laneNum',label:'车道数',align:'center',width:width*0.07},
	    	        {name:'singleLane',label:'单车道',align:'center',width:width*0.07,
	    	        	render:function(value,data){
	    	        		if(value==0){
	    	        			return "<span>否</span>";
	    	        		}else{
	    	        			return "<span>是</span>";
	    	        		}
	    	        	}
	    	        },       	     	      	        	        
	    	        {name:'roadAudit',label:'状态',align:'center',width:width*0.08,
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
	    	        		if(data.roadAudit==1){
	    	        			str+="&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
        						"onclick='auditRoadSingle("+data.roadId+",\""+data.roadName+"\","+data.roadAudit+");'>审核</button>";
	    	        		}else{
	    	        			str+="&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
	    	        		}
	    	        		return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
	        						"onclick='getOneRoadDetail("+data.roadId+");'>详细信息</button>"+str;
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


//查看
function getOneRoadDetail(e){
	BJUI.dialog({
        id:'roadDetail',
        target:$.CurrentNavtab.find("#roadDetail"),
        title:'查看道路',
        width:700,
        height:415,
        mask:true,
        resizable:false,
        onClose:function(){
	    	$.CurrentNavtab.find('#roadInfo').datagrid('selectedRows',false);
	    }
	});
	$.ajax({
		type:"post",
		url:"road/getOneRoadById.do?"+new Date(),
		data:{"roadId":e},
		dataType:"json",
		success:function(data){
			$.CurrentDialog.find("#r_Name").text(data.roadName);
			$.CurrentDialog.find("#ra_Name").text(data.area.areaName);
			$.CurrentDialog.find("#r_roadGradeName").text(data.roadGrade.roadGradeName);
			$.CurrentDialog.find("#r_Length").text(data.roadLength);
			$.CurrentDialog.find("#r_Width").text(data.roadWidth);
			$.CurrentDialog.find("#r_LaneNum").text(data.laneNum);
			$.CurrentDialog.find("#r_editRoadName").text(data.editRoadName);
			$.CurrentDialog.find("#r_editRoadDate").text(convertDate(data.editRoadDate));
			if(data.singleLane==1){
				$.CurrentDialog.find("#r_SingleLane").html("<span>是</span>");
			}else{
				$.CurrentDialog.find("#r_SingleLane").html("<span>否</span>");
			}
			if(data.roadAudit==0){
				$.CurrentDialog.find("#r_RoadAudit").html("<span style='color:green'>已审核</span>");
			}else{
				$.CurrentDialog.find("#r_RoadAudit").html("<span style='color:red'>审核中</span>");
			}
			if(data.roadPic!=null){
				var img = data.roadPic.split(",");
				var str = "<img alt='' src='"+img[0]+"' style='max-width: 125px;max-height: 85px;'>";
				if(img.length>1){
					var url=img[0].substring(0,img[0].lastIndexOf("/")+1);
					for(var i=1;i<img.length;i++){
						img[i]=url+img[i];
						str += "&ensp;<img alt='' src='"+img[i]+"' style='max-width: 125px;max-height: 85px;'>";
					}
				}
				$.CurrentDialog.find('#td_road_pic').html(str);
			}else{
				$.CurrentDialog.find('#td_road_pic').html("<center>暂无图片信息</center>");
			}
		}
	});
}

//打开修改弹窗
function editRoadInfo(){
	var roadId=$.CurrentNavtab.find("#roadInfo").data('selectedDatas');
	if(roadId==null || roadId.length!=1){
		BJUI.alertmsg('info', '请选择一条需要编辑的道路');
	}else{
		BJUI.dialog({
	        id:'addRoad',
	        target:$.CurrentNavtab.find("#addRoad"),
	        width:655,
		    height:500,
	        title:'修改道路信息',
	        mask:true,
	        resizable:false,
	        onClose:function(){
	        	$.CurrentNavtab.find('#roadInfo').datagrid('selectedRows',false);
	     	},
	     	onLoad:function($dialog){
				$("#roadUploadArea").removeClass("hide");
				$("#submitBtnr > span").text("修改");
			}
		});
		var upRid="";
		for(var i=0;i<roadId.length;i++){
			upRid=roadId[i].roadId;
		}
		$.ajax({
			type:"post",
			url:"road/getRoadById.do?"+new Date(),
			data:{"upRid":upRid},
			dataType:"json",
			success:function(data){
				getAllRoadGrade();
				var areaname=data.area.areaName;
				var zTree = $.fn.zTree.getZTreeObj("r_areaNameMenu");
				var node = zTree.getNodeByParam("areaId",data.area.areaId);
				areaname=areaname.substring(areaname.lastIndexOf(",")+1,areaname.length);
				node.checked = true; 
				zTree.updateNode(node);
				$.CurrentDialog.find('#r_areaName').val(areaname.trim());
				$.CurrentDialog.find('#r_areaId').val(data.area.areaId);
				$.CurrentDialog.find("#roadName").attr("value",data.roadName);
				$.CurrentDialog.find("#roadId").attr("value",data.roadId);
				$.CurrentDialog.find("#roadLength").attr("value",data.roadLength);
				$.CurrentDialog.find("#roadWidth").attr("value",data.roadWidth);
				$.CurrentDialog.find("#laneNum").attr("value",data.laneNum);
				if(data.singleLane==1){
					$.CurrentDialog.find('#singleLane1').iCheck('check');
				}else{
					$.CurrentDialog.find('#singleLane2').iCheck('check');
				}
				$.CurrentDialog.find('#roadGradeId').selectpicker('val',data.roadGradeId);
				$.CurrentDialog.find("#editRoadDate").attr("value",convertDate(data.editRoadDate));
				$.CurrentDialog.find('#roadPic').val(data.roadPic);
			}
		});
		saveRoadInfo();
	}
	
	
}

//删除
function deleteRoadInfo(){
	var roadId=$.CurrentNavtab.find("#roadInfo").data('selectedDatas');
	var deRid="";
	var nameStr="";
	if(typeof(roadId)=="undefined"||roadId.length==0){
		BJUI.alertmsg('info', '请选择需要删除的道路');
	}else{
		for(var i=0;i<roadId.length;i++){
			deRid+=roadId[i].roadId+",";
			nameStr+=roadId[i].roadName+",";
		}
		nameStr=nameStr.substring(0, nameStr.length-1);
		BJUI.alertmsg('confirm', '你确定要删除道路'+nameStr+'吗？', {
			okCall: function() {
				$.ajax({
					type:"post",
					url:"road/deleteRoad.do?"+new Date(),
					data:{"deRid" : deRid},
					dataType:"text",
					success:function(data){
						if(data==0){
							   BJUI.alertmsg('error', '删除失败');
						}else{
							$.CurrentNavtab.find('#roadInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', '成功删除道路'+nameStr+"!", {
								displayPosition:'middlecenter'
							});
						}
					}
				});
			}
		});
	}
	
}


//打开新增弹窗
function addroadInfo(){
	$.ajax({
		type:"post",
		url:"road/getAreaName.do?"+new Date(),
		dataType:"json",
		success:function(data){
			 BJUI.dialog({
			        id:'addRoad',
			        width: 655,
			        height: 330,
			        target:$("#addRoad"),
			        title:'新增道路',
			        mask:true,
			        resizable:false,
			        onClose:function(){
			        	$.CurrentNavtab.find('#roadInfo').datagrid('selectedRows',false);
			     	},
					onLoad:function($dialog){
						var that  = this;
						var width  = that.options.width > that.options.minW ? that.options.width : that.options.minW;
						var wW     = $(window).width(),iTop   = that.options.max ? 0 : (($(window).height() - 500) / 3);
						if (width > wW)  width  = wW;
						$("#roadPic").click(function(event){
							$dialog.height(500)
								.css({left:(wW - width) / 2, top:0, opacity:0.1})
								.animate({top:iTop > 0 ? iTop : 0, opacity:1})
								.find('> .dialogContent').height(500 - $('> .dialogHeader', $dialog).outerHeight());
							$dialog.find('> .dialogContent').css({height:(500- $dialog.find('> .dialogHeader').outerHeight())}); 
							$("#roadUploadArea").removeClass("hide");
							$.CurrentDialog.find("#filePicker div:last").css({"left":"220px","width":"156px","height":"25px","bottom":"auto","right":"auto"});
							$.CurrentDialog.find("#filePicker2 div:last").css({"left":"10px","width":"94px","height":"27px","bottom":"auto","right":"auto"});
						});
					}
			 });
			var areaName=data.areaName;
			var areaId=data.areaId;
			$.CurrentDialog.find("#r_areaName").attr("value",areaName);
			$.CurrentDialog.find("#r_areaId").attr("value",areaId);
			getAllRoadGrade();
			saveRoadInfo();
		}
	});
}



//表单验证
function saveRoadInfo(){
	$.CurrentDialog.find("#submitBtnr").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addRoadInfo").isValid()){
			if($.CurrentDialog.find("#submitBtnr > span").text().trim()=="提交"){
				nameStr+=$.CurrentDialog.find("#roadName").val();
				$.ajax({
					type:"post",
					url:"road/saveRoad.do?"+new Date(),
					data:$.CurrentDialog.find("#addRoadInfo").serialize(),
					dataType:"json",
					cache : false,
					success:function(data){
						if(data=="1"){
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#roadInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加道路<span style='color:green'>"+nameStr+"<span>！", {
								 displayPosition:'middlecenter'
							});
						}else{
							BJUI.alertmsg('error', '保存失败');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#roadName").val();
				$.ajax({
					type:"post",
					url:"road/editRoadById.do?"+new Date(),
					data:$.CurrentDialog.find("#addRoadInfo").serialize(),
					dataType:"text",
					success:function(data){
						if(data==0){
							 BJUI.alertmsg('error', '修改失败');
						}else{
							 BJUI.alertmsg('ok', '成功修改道路'+nameStr+"!",{
								 displayPosition:'middlecenter'
								});
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#roadInfo').datagrid('refresh', true);
						}
					}
				});
			}
		}else{
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#addRoadInfo').validator('cleanUp');
 				}
 			});
		}
	});
}

//图片审核
function auditRoadSingle(e,f,g){
	if(auditRoad==true){
		if(g==0){
			return false;
		}else{
			var nameStr=f;
			var audRid=e;
			BJUI.alertmsg('confirm', '你确定要审核道路<span style="color:orange">'+nameStr+'</span>吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"road/updateAuditRoad.do?"+new Date(),
						data:{"audRid" : audRid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#roadInfo').datagrid('refresh', true);
							}
						}
					});
				}
			});
		}
	}
	
	
}

//审核方法
function auditRoadInfo(){
	var roadId=$.CurrentNavtab.find("#roadInfo").data('selectedDatas');
	var audRid="";
	var nameStr="";
	if(typeof(roadId)=="undefined"||roadId.length==0){
		BJUI.alertmsg('error', '请选择需要审核的道路');
	}else{
		for(var i=0;i<roadId.length;i++){
			if(roadId[i].roadAudit==0){
				continue;
			}else{
				audRid+=roadId[i].roadId+",";
				nameStr+=roadId[i].roadName+",";
			}
		}
		if(audRid.length>0){
			nameStr=nameStr.substring(0, nameStr.length-1);
			BJUI.alertmsg('confirm', '你确定要审核道路'+nameStr+'吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"road/updateAuditRoad.do?"+new Date(),
						data:{"audRid" : audRid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#roadInfo').datagrid('refresh', true);
								BJUI.alertmsg('ok', '成功审核'+nameStr+"!", {
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

//获取所有道路等级
function getAllRoadGrade(){
	BJUI.ajax('doajax', {
	    url: 'road/getAllRoadGrade.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var roadGradeStr="";
	    	for(var i=0;i<data.length;i++){
	    		roadGradeStr+="<option value='" + data[i].roadGradeId + "'>" + data[i].roadGradeName + "</option>";
	    	}
	    	$.CurrentDialog.find("#roadGradeId").html(roadGradeStr);
	    	$.CurrentDialog.find('#roadGradeId').selectpicker('refresh');
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
function Road_NodeCheck(e, treeId, treeNode) {
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
    	$("#r_areaId").val(areaId);
    };
}

//刷新页面
function refreshRoad(){
	$.CurrentNavtab.find('#roadInfo').datagrid('refresh', false);
	$.CurrentNavtab.find('#roadInfo').datagrid('selectedRows',false);
}