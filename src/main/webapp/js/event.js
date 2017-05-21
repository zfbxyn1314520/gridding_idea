/**
 * 刘春晓
 * event.html外部js代码
 */
var editor;
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
	    	var status=false;
	    	var toolBar=true;
	    	for(var z=0;z<per.length;z++){
	    		if(per[z]=="添加"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addeventInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editeventInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteeventInfo();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='auditeventInfo();'>审核</button>";
	    		}
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshEvent();'>刷新</button>";
	    	//生成datagrid表格
	    	$('#eventInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'event/getAllEventByAreaId.do',
	    	    columns: [
	    	        {name:'area',label:'所属区域',align:'center',width:width*0.18,
	    	        	render:function(value, data){
	    	        		return value.areaName;
	    	        	}
	    	        },
	    	        {name:'grid',label:'所属网格',align:'center',width:width*0.18,
	    	        	render:function(value, data){
	    	        		return value.gridName;
	    	        	}
	    	        },
	    	        {name:'gridStaff',label:'网格员',align:'center',width:width*0.1,
	    	        	render:function(value,data){
	    	        		return value.gridStaffName;
	    	        	}
	    	        },
	    	        {name:'eventTitle',label:'标题',align:'center',width:width*0.18},
	    	        {name:'eventLevel',label:'事件等级',align:'center',width:width*0.09,
	    	        	render:function(value,data){
	    	        		return value.eventLevelName;
	    	        	}
	    	        },
	    	        {name:'eventType',label:'事件类型',align:'center',width:width*0.09,
	    	        	render:function(value, data){
	    	        		return value.eventTypeName;
	    	        	}
	    	        },
	    	        {name:'isImportant',label:'重点督办',align:'center',width:width*0.1,
	    	        	render:function(value, data){
	    	        		if(value==0){
	    	        			return "<i style='color:green'>非紧急</i>";
	    	        		}else{
	    	        			return "<i style='color:red'>紧急</i>";
	    	        		} 	
	    	        	}
	    	        },
	    	        {name:' ',label:'操作',align:'center',hide:status,width:width*0.15,
	    	        	render:function(value, data){
	    	        		if(data.isFinished==0 || data.isFinished==1){
	    	        			return "<button type='button' class='btn-orange' data-icon='clock-o' onclick='addEventLog("+data.eventId+");'>处理</button>";
	    	        		}else{
	    	        			return "<button type='button' class='btn-default' data-icon='search' onclick='getOneEventDetail("+data.eventId+");'>详细信息</button>";
	    	        		}
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

//打开处理弹窗
function addEventLog(e){
	BJUI.dialog({
		id:'eventDetail',
	    target:$.CurrentNavtab.find("#eventDetail"),
	    title:'处理事件',
	    width:700,
	    height:645,
	    mask:true,
	    resizable:false,
	    onClose:function(){
	    	$.CurrentNavtab.find('#eventInfo').datagrid('selectedRows',false);
	    },
//	    onLoad:function($dialog){
//	        if ($("#solveStatusId1").attr("checked")) {
//               $.CurrentDialog.find('#eventLogContent').attr('readOnly',true);
//            }
//	    }
	});
	getEventLogInfo(e);
	$.CurrentDialog.find("#el_eventId").attr("value",e);
	$.CurrentDialog.find("#addEventLog").removeClass("hide");
	$.CurrentDialog.find("#elBtn").removeClass("hide");
	$.CurrentDialog.find("#eventLogInfo").removeClass("hide");
	$("#solveStatusId2").click(function(){
	 	$.CurrentDialog.find('#eventLogContent').removeAttr('readOnly');
	});
	$.CurrentDialog.find("#eventLogBtn").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addEventLogInfo").isValid()){
			nameStr+=$.CurrentDialog.find("#l_eventTitle").val();
			$.ajax({
				type:"post",
				url:"event/saveEventLog.do?"+new Date(),
				data:$.CurrentDialog.find("#addEventLogInfo").serialize(),
				dataType:"json",
				cache : false,
				success:function(data){
					if(data=="1"){
						BJUI.dialog('closeCurrent');
						$.CurrentNavtab.find('#eventInfo').datagrid('refresh', true);
						BJUI.alertmsg('ok', "成功处理事件<span style='color:green'>"+nameStr+"<span>！", {
							 displayPosition:'middlecenter'
						});
					}else if(data=="3"){
						BJUI.alertmsg('info', '事件已处理完毕！');
					}else{
						BJUI.alertmsg('error', '处理失败');
					}
				}
			});
		}
	});
}


//打开增加弹窗
function addeventInfo(){
	BJUI.dialog({
		id:'addEvent',
	    target:$.CurrentNavtab.find("#addEvent"),
	    title:'添加事件',
	    width:905,
	    height:700,
	    mask:true,
	    resizable:false,
	    onClose:function(){
	    	$.CurrentNavtab.find('#eventInfo').datagrid('selectedRows',false);
	    },
	    onLoad:function($dialog){
	    	var areaId=sessionStorage.userAreaId;
	    	var zTree = $.fn.zTree.getZTreeObj("event_areaNameList");
			var node = zTree.getNodeByParam("areaId",areaId);
			editor = new wangEditor('eventContent');
		    wangEditor.config.printLog = false;
		    editor.config.uploadImgUrl = 'blog/uploadFile.do';
		    editor.config.uploadParams = { mainName: 'app/blog' };
		    editor.config.uploadImgFileName = 'blogPic'; 
		    editor.config.uploadHeaders = {  'Accept' : 'text/x-json' }; 
		    editor.create();
 		    node.checked = true,zTree.updateNode(node);
			$('#blog_areaName').val(node.name),$('#blog_areaId').val(areaId);
		    editor.onchange = function () {
		        $("#blogCount").text(this.$txt.text().length);
		    };
		    $("#eventCount").text(editor.$txt.text().length);
			$('#clearBlogBtn').click(function () {
		        editor.$txt.html('<p><br></p>');
		        return false;
		    });
			node.checked = true,zTree.updateNode(node);
			$('#event_areaName').val(node.name),$('#event_areaId').val(areaId);
		}
	});
	getAllGridOfEvent(sessionStorage.userAreaId);
	getAllEventLevelAndStatusAndType();
	saveEventInfo();
}

function editeventInfo(){
	var eventId=$.CurrentNavtab.find("#eventInfo").data('selectedDatas');
	if(eventId==null || eventId.length!=1){
		BJUI.alertmsg('info', '请选择一条需要编辑的事件');
	}else{
		BJUI.dialog({
			id:'editEvent',
			target:$.CurrentNavtab.find("#addEvent"),
			title:'修改事件',
			width:905,
		    height:700,
			mask:true,
			resizable:false,
			onClose:function(){
				$.CurrentNavtab.find('#eventInfo').datagrid('selectedRows',false);
			},
		    onLoad:function($dialog){
				editor = new wangEditor('eventContent');
			    wangEditor.config.printLog = false;
			    editor.config.uploadImgUrl = 'blog/uploadFile.do';
			    editor.config.uploadParams = { mainName: 'app/blog' };
			    editor.config.uploadImgFileName = 'blogPic'; 
			    editor.config.uploadHeaders = {  'Accept' : 'text/x-json' }; 
			    editor.create();
			    editor.onchange = function () {
			        $("#blogCount").text(this.$txt.text().length);
			    };
			    $("#eventCount").text(editor.$txt.text().length);
				$('#clearBlogBtn').click(function () {
			        editor.$txt.html('<p><br></p>');
			        return false;
			    });
			}
		});
		$.CurrentDialog.find("#eventBtn > span").text("修改");
		var upELid="";
		for(var i=0;i<eventId.length;i++){
			upELid=eventId[i].eventId;
		}
		$.ajax({
			type:"post",
			url:"event/getEventById.do?"+new Date(),
			data:{"upELid":upELid},
			dataType:"json",
			success:function(data){
				var zTree = $.fn.zTree.getZTreeObj("event_areaNameList");
				var node = zTree.getNodeByParam("areaId",data.event.areaId);
				node.checked = true,zTree.updateNode(node);
				$('#event_areaName').val(node.name),$('#event_areaId').val(data.event.areaId);
				getAllGridOfEvent(data.event.areaId);
				getAllEventLevelAndStatusAndType();
				$.CurrentDialog.find('#event_areaName').val(node.name);
				$.CurrentDialog.find('#event_areaId').val(data.event.areaId);
				$.CurrentDialog.find('#event_gridId').selectpicker('val',data.event.gridId);
				getGridStaffByGridIdOfEvent(data.event.gridId);
				$.CurrentDialog.find('#event_gridStaffId').selectpicker('val',data.event.gridStaffId);
				$.CurrentDialog.find('#eventLevelId').selectpicker('val',data.event.eventLevelId);
				$.CurrentDialog.find('#sourceTypeId').selectpicker('val',data.event.sourceTypeId);
				$.CurrentDialog.find('#eventTypeId').selectpicker('val',data.event.eventTypeId);
				$.CurrentDialog.find('#editEventDate').val(convertDate(data.event.editEventDate));
				$.CurrentDialog.find("#eventTitle").attr("value",data.event.eventTitle);
				$.CurrentDialog.find("#eventContent").text(editor.$txt.html(data.event.eventContent));
				$.CurrentDialog.find("#eventId").attr("value",data.event.eventId);
				if(data.event.isImportant==0){
					$.CurrentDialog.find('#isImportant').iCheck('check');
				}else{
					$.CurrentDialog.find('#isImportant1').iCheck('check');
				}
			}
		});
		saveEventInfo();
	}
}

function saveEventInfo(){
	$.CurrentDialog.find("#event_gridId").change(function(){
		if($(this).val()!=""){
			getGridStaffByGridIdOfEvent($(this).val());
		}else{
			$.CurrentDialog.find("#event_gridStaffId").html("<option value=''>请选择单元</option>");
	    	$.CurrentDialog.find('#event_gridStaffId').selectpicker('refresh');
		}
	});
	$.CurrentDialog.find("#eventBtn").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addEventInfo").isValid()){
			var eventPic="";
			var imgUrl="";
			var imgs = editor.$txt.find('img');
			var imgsLength=imgs.length;
			for(var i=0;i<imgsLength;i++){
				imgUrl=imgs[i].src;
				eventPic+=imgUrl.substring(imgUrl.indexOf("images/app"),imgUrl.length)+";";
			}
			$.CurrentDialog.find("#eventPic").val(eventPic);
			if($.CurrentDialog.find("#eventBtn > span").text().trim()=="提交"){
				nameStr+=$.CurrentDialog.find("#eventTitle").val();
				$.ajax({
					type:"post",
					url:"event/saveEvent.do?"+new Date(),
					data:$.CurrentDialog.find("#addEventInfo").serialize(),
					dataType:"json",
					cache : false,
					success:function(data){
						if(data=="1"){
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#eventInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加事件<span style='color:green'>"+nameStr+"<span>！", {
								 displayPosition:'middlecenter'
							});
						}else{
							BJUI.alertmsg('error', '添加失败');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#eventTitle").val();
				$.ajax({
					type:"post",
					url:"event/editEventById.do?"+new Date(),
					data:$.CurrentDialog.find("#addEventInfo").serialize(),
					dataType:"text",
					success:function(data){
						if(data==0){
							 BJUI.alertmsg('error', '修改失败');
						}else{
							 BJUI.alertmsg('ok', '成功修改事件'+nameStr+"!",{
								 displayPosition:'middlecenter'
								});
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#eventInfo').datagrid('refresh', true);
						}
					}
				});
			}
		}else{
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#addEventInfo').validator('cleanUp');
 				}
 			});
		}
	});
}

//查看
function getOneEventDetail(e){
	BJUI.dialog({
        id:'eventDetail',
        target:$.CurrentNavtab.find("#eventDetail"),
        title:'事件详细信息',
        width:700,
        height:645,
        mask:true,
        resizable:false,
        onClose:function(){
	    	$.CurrentNavtab.find('#eventInfo').datagrid('selectedRows',false);
	    }
	});
	getEventLogInfo(e);
}

//获取事件跟踪信息及相关操作
function getEventLogInfo(e) {
	$.ajax({
		type:"post",
		url:"event/getOneEventById.do?"+new Date(),
		data:{"eventId":e},
		dataType:"json",
		success:function(data){
			$.CurrentDialog.find("#te_eventTitle").text(data.eventTitle);
			$.CurrentDialog.find("#te_gridStaffId").text(data.gridStaff.gridStaffName);
			$.CurrentDialog.find("#te_areaName").text(data.area.areaName);
			$.CurrentDialog.find("#te_grid").text(data.grid.gridName);
			$.CurrentDialog.find("#te_eventLevelId").text(data.eventLevel.eventLevelName);
			$.CurrentDialog.find("#te_eventTypeId").text(data.eventType.eventTypeName);
			$.CurrentDialog.find("#te_sourceTypeId").text(data.sourceType.sourceTypeName);
			$.CurrentDialog.find("#te_eventContent").text(data.eventContent);
			if(data.isImportant==1){
				$.CurrentDialog.find("#te_isImportant").html("<span style='color:red'>紧急</span>");
			}else{
				$.CurrentDialog.find("#te_isImportant").html("<span style='color:green'>非紧急</span>");
			}
		}
	});
	$.ajax({
		type:"post",
		url:"event/getEventLogById.do?"+new Date(),
		data:{"eventId":e},
		dataType:"json",
		success:function(data){
			if(data.length>1){
				$.CurrentDialog.find("#te_editEventLogName").text(data[1].editEventLogName);
				$.CurrentDialog.find("#te_solveStatusName").text(data[1].solveStatus.solveStatusName);
				$.CurrentDialog.find("#te_editEventDate").text(convertDate(data[1].editEventLogDate));
			}else if(data[0].editEventLogName!=null){
				if(data[0].solveStatus.solveStatusId==1){
					$.CurrentDialog.find('#solveStatusId1').iCheck('check');
					$.CurrentDialog.find("#eventLogContent").text(data[0].eventLogContent);
					if ($("#solveStatusId1").attr("checked")) {
		            	$.CurrentDialog.find('#eventLogContent').attr('readOnly',true);
		            }
					$("#solveStatusId1").click(function(){
						$.CurrentDialog.find('#eventLogContent').attr('readOnly',true);
					});
				}
				$.CurrentDialog.find("#te_editEventLogName").text(data[0].editEventLogName);
				$.CurrentDialog.find("#te_solveStatusName").text(data[0].solveStatus.solveStatusName);
				$.CurrentDialog.find("#te_editEventDate").text(convertDate(data[0].editEventLogDate));
			}else{
				$.CurrentDialog.find("#te_editEventLogName").text("暂无人受理");
				$.CurrentDialog.find("#te_solveStatusName").text("未受理");
				$.CurrentDialog.find("#te_editEventDate").text(convertDate(data[0].event.editEventDate));
			}
			
			var str="";
			if(data[0].editEventLogName!=null){
				for(var i=0;i<data.length;i++){
					str+="<tr>";
				    str+="<td style='width: 100px;background: white;'>"+data[i].solveStatus.solveStatusName+"</td>"
				    	+"<td style='width: 150px;background: white;font-size:12px;'>"+convertDate(data[i].editEventLogDate)+"</td>"
				    	+"<td style='width: 440px;background: white;font-size:12px;'>"+data[i].eventLogContent+"</td>";
				    str+="</tr>";
				}
				$("#eventLogTr").after(str);
			}
		}
	});
}


//删除
function deleteeventInfo(){
	var eventId=$.CurrentNavtab.find("#eventInfo").data('selectedDatas');
	var deElid="";
	var nameStr="";
	if(typeof(eventId)=="undefined"||eventId.length==0){
		BJUI.alertmsg('ok', '请选择需要删除的日志');
	}else{
		for(var i=0;i<eventId.length;i++){
			deElid+=eventId[i].eventId+",";
			nameStr+=eventId[i].eventTitle+",";
		}
		nameStr=nameStr.substring(0, nameStr.length-1);
		BJUI.alertmsg('confirm', '你确定要删除事件'+nameStr+'吗？', {
			okCall: function() {
				$.ajax({
					type:"post",
					url:"event/deleteEvent.do?"+new Date(),
					data:{"deElid" : deElid,"nameStr":nameStr},
					dataType:"text",
					success:function(data){
						if(data==0){
							   BJUI.alertmsg('error', '删除失败');
						}else{
							$.CurrentNavtab.find('#eventInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', '成功删除事件'+nameStr+'！', {
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
 * ztree下拉选择
 * 
 * @param e
 * @param treeId
 * @param treeNode
 */
//选择事件
function Event_NodeCheck(e, treeId, treeNode) {
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
    	$("#event_areaId").val(areaId);
    };
    if($.CurrentDialog.find("#event_areaId").val()!=""){
    	getAllGridOfEvent($.CurrentDialog.find("#event_areaId").val());
    }
}

function getAllEventLevelAndStatusAndType(){
	BJUI.ajax('doajax', {
	    url: 'event/getAllEventLevel.do',
	    type:'get',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var eventLevelStr="<option value=''>请选择事件等级</option>";
	    	for(var i=0;i<data.length;i++){
	    		eventLevelStr+="<option value='" + data[i].eventLevelId + "'>" + data[i].eventLevelName + "</option>";
	    	}
	    	$.CurrentDialog.find("#eventLevelId").html(eventLevelStr);
	    	$.CurrentDialog.find('#eventLevelId').selectpicker('refresh');
	    }
	});
	BJUI.ajax('doajax', {
	    url: 'event/getAllType.do',
	    type:'get',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var solveStatusStr="<option value=''>请选择来源</option>";
	    	for(var i=0;i<data.length;i++){
	    		solveStatusStr+="<option value='" + data[i].sourceTypeId + "'>" + data[i].sourceTypeName + "</option>";
	    	}
	    	$.CurrentDialog.find("#sourceTypeId").html(solveStatusStr);
	    	$.CurrentDialog.find('#sourceTypeId').selectpicker('refresh');
	    }
	});
	BJUI.ajax('doajax', {
	    url: 'event/getAllStatus.do',
	    type:'get',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var solveStatusStr="<option value=''>请选择处理状态</option>";
	    	for(var i=0;i<data.length;i++){
	    		solveStatusStr+="<option value='" + data[i].solveStatusId + "'>" + data[i].solveStatusName + "</option>";
	    	}
	    	$.CurrentDialog.find("#solveStatusId").html(solveStatusStr);
	    	$.CurrentDialog.find('#solveStatusId').selectpicker('refresh');
	    }
	});
	BJUI.ajax('doajax', {
	    url: 'event/getAllEventType.do',
	    type:'get',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var eventTypeStr="<option value=''>请选择事件类型</option>";
	    	for(var i=0;i<data.length;i++){
	    		eventTypeStr+="<option value='" + data[i].eventTypeId + "'>" + data[i].eventTypeName + "</option>";
	    	}
	    	$.CurrentDialog.find("#eventTypeId").html(eventTypeStr);
	    	$.CurrentDialog.find('#eventTypeId').selectpicker('refresh');
	    }
	});
}

function getGridStaffByGridIdOfEvent(e){
	BJUI.ajax('doajax', {
	    url: 'gridStaff/getGridStaffByGridId.do',
	    type:'get',
	    data:{"gridId":e},
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var gridStaffStr="<option value=''>请选择网格员</option>";
	    	for(var i=0;i<data.length;i++){
	    		gridStaffStr+="<option value='" + data[i].gridStaffId + "'>" + data[i].gridStaffName + "</option>";
	    	}
	    	$.CurrentDialog.find("#event_gridStaffId").html(gridStaffStr);
	    	$.CurrentDialog.find('#event_gridStaffId').selectpicker('refresh');
	    }
	});
}

function getAllGridOfEvent(e){
	BJUI.ajax('doajax', {
	    url: 'gridStaff/getAllGridByAreaId.do',
	    type:'get',
	    data:{"areaId":e},
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var gridStr="<option value=''>请选择网格</option>";
	    	for(var i=0;i<data.length;i++){
	    		gridStr+="<option value='" + data[i].gridId + "'>" + data[i].gridName + "</option>";
	    	}
	    	$.CurrentDialog.find("#event_gridId").html(gridStr);
	    	$.CurrentDialog.find('#event_gridId').selectpicker('refresh');
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

function refreshEvent(){
	$.CurrentNavtab.find('#eventInfo').datagrid('refresh', true);
}