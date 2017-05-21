/**
 * road.html页面的外部js代码
 * author 刘春晓
 */

var auditF=false;
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
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addfacilitiesInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editfacilitiesInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deletefacilitiesInfo();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='auditfacilitiesInfo();'>审核</button>";
	    			auditF=true;
	    		}
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshFacilities();'>刷新</button>";
	    	//生成datagrid表格
	    	$.CurrentNavtab.find('#facilitiesInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'facilities/getAllfacilitiesByAreaId.do',
	    	    columns: [
	    	        {name:'area',label:'所属区域',align:'center',width:width*0.18,
	    	        	render:function(value, data){
	    	        		return value.areaName;
	    	        	}
	    	        },
	    	        {name:'facilitiesType',label:'公共设施类别',align:'center',width:width*0.15,
	    	        	render:function(value, data){
	    	        		return value.facilitiesTypeName;
	    	        	}
	    	        },
	    	        {name:'facilitiesName',label:'公共设施名称',align:'center',width:width*0.12},
	    	        {name:'facilitiesSite',label:'公共设施地址',align:'center',width:width*0.3},
	    	        {name:'facilitiesAudit',label:'状态',align:'center',width:width*0.08,
	    	        	render:function(value, data){
	    	        		if(value==0){
	    	        			return "<i class='am-icon-check-square-o' style='color:green'></i>";
	    	        		}else{
	    	        			return "<i class='am-icon-minus-square-o' style='color:red'></i>";
	    	        		}   		
	    	        	}
	    	        },
	    	        {name:'',label:'操作',align:'center',hide:status,width:width*0.17,
	    	        	render:function(value, data){	    
	    	        		var str="";
	    	        		if(data.facilitiesAudit==1){
	    	        			str+="&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
        						"onclick='auditFacilitiesSingle("+data.facilitiesId+",\""+data.facilitiesName+"\","+data.facilitiesAudit+");'>审核</button>";
	    	        		}else{
	    	        			str+="&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
	    	        		}
	    	        		return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
	    	        				"onclick='getOnefacilitiesDetail("+data.facilitiesId+");'>详细信息</button>"+str;
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

//查看公共设施
function getOnefacilitiesDetail(e){
	 BJUI.dialog({
	        id:'facilitiesDetail',
	        target:$.CurrentNavtab.find("#facilitiesDetail"),
	        title:'查看公共设施详细信息',
	        height:380,
	        width:720,
	        mask:true,
	        resizable:false,
	        onClose:function(){
		    	$.CurrentNavtab.find('#facilitiesInfo').datagrid('selectedRows',false);
		    }
	 });
	$.ajax({
		type:"post",
		url:"facilities/getOneFacilitiesById.do?"+new Date(),
		data:{"facilitiesId":e},
		dataType:"json",
		success:function(data){
			$.CurrentDialog.find("#f_Name").text(data.facilitiesName);
			$.CurrentDialog.find("#ft_Name").text(data.facilitiesType.facilitiesTypeName);
			$.CurrentDialog.find("#fa_Name").text(data.area.areaName);
			$.CurrentDialog.find("#f_Site").text(data.facilitiesSite);
			$.CurrentDialog.find("#f_editFacName").text(data.editFacName);
			$.CurrentDialog.find("#f_editFacDate").text(convertDate(data.editFacDate));
			if(data.facilitiesAudit==0){
				$.CurrentDialog.find("#f_Audit").html("<span style='color:green'>已审核</span>");
			}else{
				$.CurrentDialog.find("#f_Audit").html("<span style='color:red'>审核中</span>");
			}
			if(data.facilitiesPic!=null){
				var img = data.facilitiesPic.split(",");
				var str = "<img alt='' src='"+img[0]+"' style='max-width: 125px;max-height: 85px;'>";
				if(img.length>1){
					var url=img[0].substring(0,img[0].lastIndexOf("/")+1);
					for(var i=1;i<img.length;i++){
						img[i]=url+img[i];
						str += "&ensp;<img alt='' src='"+img[i]+"' style='max-width: 125px;max-height: 85px;'>";
					}
				}
				$.CurrentDialog.find('#td_fac_pic').html(str);
			}else{
				$.CurrentDialog.find('#td_fac_pic').html("<center>暂无图片信息</center>");
			}
		}
	});
}

//图片审核
function auditFacilitiesSingle(e,f,g){
	if(auditF==true){
		if(g==0){
			return false;
		}else{
			var nameStr=f;
			var audFid=e;
			BJUI.alertmsg('confirm', '你确定要审核公共设施<span style="color:orange">'+nameStr+'</span>吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"facilities/updateAuditFacilities.do?"+new Date(),
						data:{"audFid" : audFid},
						dataType:"text",
						success:function(data){
							if(data==0){
								   BJUI.alertmsg('ok', '审核失败');
							}else{
								$.CurrentNavtab.find('#facilitiesInfo').datagrid('refresh', true);
							}
						}
					});
				}
			});
		}
	}
}

//审核公共设施
function auditfacilitiesInfo(){
	var facilitiesId=$.CurrentNavtab.find("#facilitiesInfo").data('selectedDatas');
	var audFid="";
	var nameStr="";
	if(typeof(facilitiesId)=="undefined"||facilitiesId.length==0){
		BJUI.alertmsg('info', '请选择需要审核的公共设施');
	}else{
		for(var i=0;i<facilitiesId.length;i++){
			if(facilitiesId[i].facilitiesAudit==0){
				continue;
			}else{
				audFid+=facilitiesId[i].facilitiesId+",";
				nameStr+=facilitiesId[i].facilitiesName+",";
			}
		}
		if(audFid.length>0){
			nameStr=nameStr.substring(0, nameStr.length-1);
			BJUI.alertmsg('confirm', '你确定要审核公共设施'+nameStr+'吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"facilities/updateAuditFacilities.do?"+new Date(),
						data:{"audFid" : audFid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('ok', '审核失败');
							}else{
								$.CurrentNavtab.find('#facilitiesInfo').datagrid('refresh', true);
								BJUI.alertmsg('ok', '成功审核公共设施'+nameStr+"!", {
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

//打开修改公共设施弹窗
function editfacilitiesInfo(){
	var facilitiesId=$.CurrentNavtab.find("#facilitiesInfo").data('selectedDatas');
	if(facilitiesId==null || facilitiesId.length!=1){
		BJUI.alertmsg('info', '请选择一条记录进行编辑！');
	}else{
		BJUI.dialog({
			id:'addFacilities',
		    target:$.CurrentNavtab.find("#addFacilities"),
		    title:'编辑公共设施',
		    width:730,
		    height:450,
		    mask:true,
		    resizable:false,
		    onClose:function(){
	        	$.CurrentNavtab.find('#facilitiesInfo').datagrid('selectedRows',false);
	     	},
	     	onLoad:function($dialog){
				$("#facilitiesUploadArea").removeClass("hide");
				$("#submitBtnf > span").text("修改");
			}
		});
		var upFid="";
		for(var i=0;i<facilitiesId.length;i++){
			upFid=facilitiesId[i].facilitiesId;
		}
		$.ajax({
			type:"post",
			url:"facilities/getFacilitiesById.do?"+new Date(),
			data:{"upFid":upFid},
			dataType:"json",
			success:function(data){
				getAllFacilitiesType();
				var areaname=data.area.areaName;
				var zTree = $.fn.zTree.getZTreeObj("f_areaNameMenu");
				var node = zTree.getNodeByParam("areaId",data.area.areaId);
				areaname=areaname.substring(areaname.lastIndexOf(",")+1,areaname.length);
				node.checked = true; 
				zTree.updateNode(node);
				$.CurrentDialog.find('#f_areaName').val(areaname.trim());
				$.CurrentDialog.find('#f_areaId').val(data.area.areaId);
				$.CurrentDialog.find('#facilitiesTypeId').selectpicker('val',data.facilitiesTypeId);
				$.CurrentDialog.find("#facilitiesName").attr("value",data.facilitiesName);
				$.CurrentDialog.find("#facilitiesId").attr("value",data.facilitiesId);
				$.CurrentDialog.find("#facilitiesSite").attr("value",data.facilitiesSite);
				$.CurrentDialog.find("#editFacDate").val(convertDate(data.editFacDate));
				$.CurrentDialog.find('#facilitiesPic').val(data.facilitiesPic);
			}
		});
		saveFacilitiesInfo();
	}
}


//打开新增公共设施弹窗
function addfacilitiesInfo(){
	$.ajax({
		type:"post",
		url:"facilities/getAreaName.do?"+new Date(),
		dataType:"json",
		success:function(data){
			BJUI.dialog({
		        id:'addfacilities',
		        width: 730,
		        height: 280,
		        target:$.CurrentNavtab.find("#addFacilities"),
		        title:'新增公共设施信息',
		        mask:true,
		        resizable:false,
		        onClose:function(){
		        	$.CurrentNavtab.find('#facilitiesInfo').datagrid('selectedRows',false);
		     	},
				onLoad:function($dialog){
					var that  = this;
					var width  = that.options.width > that.options.minW ? that.options.width : that.options.minW;
					var wW     = $(window).width(),iTop   = that.options.max ? 0 : (($(window).height() - 450) / 3);
					if (width > wW)  width  = wW;
					$("#facilitiesPic").click(function(event){
						$dialog.height(450)
							.css({left:(wW - width) / 2, top:0, opacity:0.1})
							.animate({top:iTop > 0 ? iTop : 0, opacity:1})
							.find('> .dialogContent').height(450 - $('> .dialogHeader', $dialog).outerHeight());
						$dialog.find('> .dialogContent').css({height:(450- $dialog.find('> .dialogHeader').outerHeight())}); 
						$("#facilitiesUploadArea").removeClass("hide");
						$.CurrentDialog.find("#filePicker div:last").css({"left":"257.5px","width":"156px","height":"25px","bottom":"auto","right":"auto"});
						$.CurrentDialog.find("#filePicker2 div:last").css({"left":"10px","width":"94px","height":"27px","bottom":"auto","right":"auto"});
					});
				}
			});
			var areaName=data.areaName;
			var areaId=data.areaId;
			$.CurrentDialog.find("#f_areaName").attr("value",areaName);
			$.CurrentDialog.find("#f_areaId").attr("value",areaId);
			getAllFacilitiesType();
			saveFacilitiesInfo();
		}
	});
	
}


//表单验证
function saveFacilitiesInfo(){
	$.CurrentDialog.find("#submitBtnf").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addFacilitiesInfo").isValid()){
			if ($.CurrentDialog.find("#submitBtnf > span").text().trim()=="提交") {
				nameStr+=$.CurrentDialog.find("#facilitiesName").val();
				$.ajax({
					type:"post",
					url:"facilities/saveFacilities.do?"+new Date(),
					data:$.CurrentDialog.find("#addFacilitiesInfo").serialize(),
					dataType:"json",
					cache:false,
					success:function(data){
						if(data=="1"){
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#facilitiesInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加公共设施<span style='color:green'>"+nameStr+"<span>！", {
								 displayPosition:'middlecenter'
							});
						}else{
							BJUI.alertmsg('error', '保存失败');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#facilitiesName").val();
				$.ajax({
					type:"post",
					url:"facilities/editfacilitiesById.do?"+new Date(),
					data:$.CurrentDialog.find("#addFacilitiesInfo").serialize(),
					dataType:"text",
					success:function(data){
						if(data==0){
							BJUI.alertmsg('error', '修改失败！');
						}else{
							 BJUI.alertmsg('ok', '成功修改公共设施'+nameStr+'!', {
								 displayPosition:'middlecenter'
								});
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#facilitiesInfo').datagrid('refresh', true);
						}
					}
				});
			}
		}else{
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#addFacilitiesInfo').validator('cleanUp');
 				}
 			});
		}
	});
	
}

//删除公共设施
function deletefacilitiesInfo(){
	var facilitiesId=$.CurrentNavtab.find("#facilitiesInfo").data('selectedDatas');
	var deFid="";
	var nameStr="";
	if(typeof(facilitiesId)=="undefined"||facilitiesId.length==0){
		BJUI.alertmsg('ok', '请选择需要删除的公共设施');
	}else{
		for(var i=0;i<facilitiesId.length;i++){
			deFid+=facilitiesId[i].facilitiesId+",";
			nameStr+=facilitiesId[i].facilitiesName+",";
		}
		nameStr=nameStr.substring(0, nameStr.length-1);
		BJUI.alertmsg('confirm', '你确定要删除公共设施'+nameStr+'吗？', {
			okCall: function() {
				$.ajax({
					type:"post",
					url:"facilities/deleteFacilities.do?"+new Date(),
					data:{"deFid" : deFid},
					dataType:"text",
					success:function(data){
						if(data==0){
							   BJUI.alertmsg('error', '删除失败');
						}else{
							$.CurrentNavtab.find('#facilitiesInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', '成功删除公共设施'+nameStr+'！', {
								 displayPosition:'middlecenter'
							});
						}
					}
				});
			}
		});
	}
}

//获取所有公共设施类别
function getAllFacilitiesType(){
	BJUI.ajax('doajax', {
	    url: 'facilities/getAllFacilitiesType.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var facilitiesTypeStr="";
	    	for(var i=0;i<data.length;i++){
	    		facilitiesTypeStr+="<option value='" + data[i].facilitiesTypeId + "'>" + data[i].facilitiesTypeName + "</option>";
	    	}
	    	$.CurrentDialog.find("#facilitiesTypeId").html(facilitiesTypeStr);
	    	$.CurrentDialog.find('#facilitiesTypeId').selectpicker('refresh');
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
function Facilities_NodeCheck(e, treeId, treeNode) {
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
    	$("#f_areaId").val(areaId);
    };
}

function refreshFacilities(){
	$.CurrentNavtab.find('#facilitiesInfo').datagrid('refresh', false);
	$.CurrentNavtab.find('#facilitiesInfo').datagrid('selectedRows',false);
}