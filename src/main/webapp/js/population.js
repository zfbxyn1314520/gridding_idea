/**
 * 人口信息
 * author 李宁财
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
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addPopInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editPopInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deletePopInfo();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='auditPopInfo();'>审核</button>";
	    		}
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshPop();'>刷新</button>";
	    	//生成datagrid表格
	    	$.CurrentNavtab.find('#popInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'pop/getAllPopByAreaId.do',
	    	    columns: [
	    	        {name:'popName',label:'姓名',align:'center',width:width*0.1},
	    	        {name:'popSex',label:'性别',align:'center',width:width*0.08},
	    	        {name:'popCode',label:'身份证号',align:'center',width:width*0.12,
	    	        	render:function(value,data){
	    	        		var codeStr=/^(.{6})(.*)(.{4})$/.exec(value);
	    	        		if(codeStr != "" && codeStr != null){
	    	        			return codeStr[1]+"****"+codeStr[3];
	    	        		}
	    	        	}
	    	        },
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
		    	        	render:function(value, data){
		    	        		return value.gridStaffName;
		    	        	}
	    	        	},
	    	        {name:'popAudit',label:'状态',align:'center',width:width*0.08,
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
	    	        		if(data.popAudit==1){
	    	        			str+="&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
        						"onclick='auditPopSingle("+data.popId+",\""+data.popName+"\","+data.popAudit+");'>审核</button>";
	    	        		}else{
	    	        			str+="&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
	    	        		}
	    	        		return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
	    	        				"onclick='getOnePopDetail("+data.popId+");'>详细信息</button>"+str;
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
	
	//获取所有人口基本属性生成复选框
	BJUI.ajax('doajax', {
	    url: 'pop/getAllPopAttr.do',
	    type:'GET',
	    okalert:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var spaceStr="";
	    	var str="";
	    	var trStr="";
	    	var brStr="";
	    	var br=0;
	    	for(var i=0;i<data.length;i++){
	    		br++;
	    		var spaceNum=6-data[i].popAttrName.length;
	    		spaceStr+="&emsp;";
	    		for(var j=0;j<spaceNum;j++){
	    			spaceStr+="&emsp;";
	    		}
	    		if(br==6||br==12||br==18||br==24||br==30){
	    			brStr="<br>";
	    		}
	    		str+="<input type='checkbox' name='popAttrs["+i+"].popAttrId' " 
	    				+"id='popAttr"+data[i].popAttrId+"' " 
	    				+"value='"+data[i].popAttrId+"' data-toggle='icheck' "
	    				+"data-label='"+data[i].popAttrName+"' />"+spaceStr+brStr;
	    		trStr+="<input type='checkbox' id='tr_attr"+data[i].popAttrId+"' " 
						+"value='"+data[i].popAttrId+"' data-toggle='icheck' "
						+"data-label='"+data[i].popAttrName+"' disabled='disabled' />"+spaceStr+brStr;
	    		spaceStr="";
	    		brStr="";
	    	}
	    	$("#popAttrIcheck").html(str);
	    	$("#tr_pop_attr").after("<tr><td style='background-color:white;text-align: left;padding-left:20px;' colspan='6'>"+trStr+"</td></tr>");
	    }
	});
	
	//将人口流动信息栏的验证禁用
	$.CurrentNavtab.find("#flowTypeId").attr("novalidate", "novalidate");
	$.CurrentNavtab.find("#inflowSite").attr("novalidate", "novalidate");
	$.CurrentNavtab.find("#flowTime").attr("novalidate", "novalidate");
	$.CurrentNavtab.find("#outflowSite").attr("novalidate", "novalidate");
	$.CurrentNavtab.find("#flowCause").attr("novalidate", "novalidate");
});


/**
 * 增加人口信息方法
 */
function addPopInfo(){
	BJUI.dialog({
		id:'addPop',
	    target:$.CurrentNavtab.find("#addPop"),
	    title:'添加人口信息',
	    width:950,
	    height:500,
	    mask:true,
	    resizable:false,
	    onClose:function(){
	    	$.CurrentNavtab.find('#popInfo').datagrid('selectedRows',false);
	    },
	    onLoad:function($dialog){
	    	var areaId=sessionStorage.userAreaId;
	    	var zTree = $.fn.zTree.getZTreeObj("pop_areaNameMenu");
			var node = zTree.getNodeByParam("areaId",areaId);
			node.checked = true,zTree.updateNode(node);
			$('#pop_areaName').val(node.name),$('#pop_areaId').val(areaId);
//			$("#popIcon").click(function(){
//				$('html,body').animate({scrollTop:$('#popUploadArea').offset().top}, 800);
//			});
		}
	});
	getAllGridAndCourt(sessionStorage.userAreaId);
	getAllSubMenuOfPop();
	valueChange();
	savePopInfo();
}

/**
 * 编辑人口信息方法
 */
function editPopInfo(){
	var row=$.CurrentNavtab.find('#popInfo').data('selectedDatas');
	if(typeof(row)=="undefined" || row.length!=1){
		BJUI.alertmsg('info', '请选择一条记录进行编辑！');
	}else{
		BJUI.dialog({
			id:'editPop',
		    target:$.CurrentNavtab.find("#addPop"),
		    title:'修改人口信息',
		    width:950,
		    height:500,
		    mask:true,
		    resizable:false,
		    onClose:function(){
		    	$.CurrentNavtab.find('#popInfo').datagrid('selectedRows',false);
		    },
	     	onLoad:function($dialog){
				$("#popBtn > span").text("修改");
			}
		});
		$.ajax({
			type : "post",
			url : "pop/getOnePopInfo.do?"+new Date(),
			data : {"popId" : row[0].popId},
			dataType : "json",
			cache : false,
			success : function(data) {
				var pop=data.population;
				var attrs=pop.popAttrs;
		    	var zTree = $.fn.zTree.getZTreeObj("pop_areaNameMenu");
				var node = zTree.getNodeByParam("areaId",pop.areaId);
				node.checked = true,zTree.updateNode(node);
				getAllGridAndCourt(pop.areaId);
				getAllSubMenuOfPop();
				$.CurrentDialog.find('#pop_areaName').val(node.name);
				$.CurrentDialog.find('#pop_areaId').val(pop.areaId);
				$.CurrentDialog.find('#pop_gridId').selectpicker('val',pop.gridId);
				getAllGridStaffByGridIdOfPop($.CurrentDialog.find('#pop_gridId').val());
				$.CurrentDialog.find('#pop_gridStaffId').selectpicker('val',pop.gridStaffId);
				$.CurrentDialog.find('#householder').selectpicker('val',pop.householder);
				$.CurrentDialog.find('#accountId').val(pop.account.accountId);
				$.CurrentDialog.find('#pop_accountHolder').val(pop.account.accountHolder);
				$.CurrentDialog.find('#relationId').selectpicker('val',pop.relationId);
				$.CurrentDialog.find('#popName').val(pop.popName);
				$.CurrentDialog.find('#popId').val(pop.popId);
				$.CurrentDialog.find('#popBirthday').val(convertDate(pop.popBirthday).substring(0, 12));
				$.CurrentDialog.find('#popSex').selectpicker('val',pop.popSex);
				$.CurrentDialog.find('#popInsured').selectpicker('val',pop.popInsured);
				$.CurrentDialog.find('#isFlow').selectpicker('val',pop.isFlow);
				$.CurrentDialog.find('#popNative').val(pop.popNative);
				$.CurrentDialog.find('#marriageId').selectpicker('val',pop.marriageId);
				$.CurrentDialog.find('#popJob').val(pop.popJob);
				$.CurrentDialog.find('#nationId').selectpicker('val',pop.nationId);
				$.CurrentDialog.find('#politicsId').selectpicker('val',pop.politicsId);
				$.CurrentDialog.find('#levelId').selectpicker('val',pop.levelId);
				$.CurrentDialog.find('#popCode').val(pop.popCode);
				$.CurrentDialog.find('#popTel').val(pop.popTel);
				$.CurrentDialog.find('#editPopDate').val(convertDate(pop.editPopDate));
				$.CurrentDialog.find('#popCompany').val(pop.popCompany);
				$.CurrentDialog.find('#popJobSite').val(pop.popJobSite);
				$.CurrentDialog.find('#popIcon').val(pop.popIcon);
				$.CurrentDialog.find('#popNowSite').val(pop.popNowSite);
				$.CurrentDialog.find('#popNativeSite').val(pop.popNativeSite);
				$.CurrentDialog.find('#popCar').text(pop.popCar);
				$.CurrentDialog.find('#popHobby').text(pop.popHobby);
				var popBirthday=$.CurrentDialog.find('#popBirthday').val();
				if(popBirthday!=''){
					var str=popBirthday.substring(0,4)+"/"+popBirthday.substring(5,7)+"/"+popBirthday.substring(8,10);   
					var age = (new Date()-new Date(str))/(1000*3600*365*24);
					$.CurrentDialog.find("#popAge").val(parseInt(age));
				}
				if($.CurrentDialog.find('#isFlow').selectpicker('val')==1){
					$.CurrentDialog.find("#flowView").removeClass("hide");
					getAllFlowTypeOfPop();
					$.CurrentDialog.find("#flowTypeId").removeAttr("novalidate");
					$.CurrentDialog.find("#inflowSite").removeAttr("novalidate");
					$.CurrentDialog.find("#flowTime").removeAttr("novalidate");
					$.CurrentDialog.find("#outflowSite").removeAttr("novalidate");
					$.CurrentDialog.find("#flowCause").removeAttr("novalidate");
					$.CurrentDialog.find('#flowTypeId').selectpicker('val',data.flowType.flowTypeId);
					$.CurrentDialog.find('#flowId').val(data.flowId);
					$.CurrentDialog.find('#inflowSite').val(data.inflowSite);
					$.CurrentDialog.find('#flowTime').val(convertDate(data.flowTime).substring(0, 12));
					$.CurrentDialog.find('#outflowSite').val(data.outflowSite);
					$.CurrentDialog.find('#flowCause').val(data.flowCause);
				}else{
					$.CurrentDialog.find("#flowView").addClass("hide");
					$.CurrentDialog.find("#flowTypeId").attr("novalidate", "novalidate");
					$.CurrentDialog.find("#inflowSite").attr("novalidate", "novalidate");
					$.CurrentDialog.find("#flowTime").attr("novalidate", "novalidate");
					$.CurrentDialog.find("#outflowSite").attr("novalidate", "novalidate");
					$.CurrentDialog.find("#flowCause").attr("novalidate", "novalidate");
				}
				if(attrs.length>0){
					var size=attrs.length;
					for(var i=0;i<size;i++){
						$.CurrentDialog.find('#popAttr'+attrs[i].popAttrId+'').iCheck('check');
					}
				}
			}
		});
		valueChange();
		savePopInfo();
	}
}

function deletePopInfo(){
	var row=$.CurrentNavtab.find('#popInfo').data('selectedDatas');
	if(typeof(row)=="undefined" || row.length!=1){
		BJUI.alertmsg('info', '请选择一条记录进行编辑！');
	}else{
		BJUI.alertmsg('confirm', "你确定要删除<span style='color:orange'>"+row[0].popName+"</span>的相关信息吗？", {
		 	okCall: function() {
		 		$.ajax({
					type : "post",
					url : "pop/deletePopInfoByIds.do?"+new Date(),
					data : {"popId" : row[0].popId},
					dataType : "json",
					cache : false,
					success : function(data) {
						if (data == "0") {
							BJUI.alertmsg('error', '删除失败！');
						} else {
							$.CurrentNavtab.find('#popInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功删除<span style='color:green'>"+row[0].popName+"</span>"+"相关信息！", {
								 displayPosition:'middlecenter'
							});
						}
					}
				});
		    }
		});
	}
}

function auditPopInfo(){
	var row=$.CurrentNavtab.find("#popInfo").data('selectedDatas');
	var list_row = new Array();
	var nameStr="";
	if(typeof(row)=="undefined"||row.length==0){
		BJUI.alertmsg('error', '请选择需要审核的人口！');
	}else{
		for ( var i = 0; i < row.length; i++) {
			list_row[i]=row[i].popId;
			nameStr+=row[i].popName+",";
		}
		nameStr=nameStr.substring(0, nameStr.length-1);
		BJUI.alertmsg('confirm', "你确定要审核通过<span style='color:orange'>"+nameStr+"</span>相关信息吗？", {
			okCall: function() {
				$.ajax({
					type:"post",
					url:"pop/alterOnePopAuditStatusInfo.do?"+new Date(),
					data:{"popIds" : list_row},
					dataType:"text",
					success:function(data){
						if(data==0){
							BJUI.alertmsg('error', '审核失败');
						}else{
							$.CurrentNavtab.find('#popInfo').datagrid('refresh', true);
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

function auditPopSingle(e,f,g){
	if(g==0){
		return false;
	}else{
		var nameStr=f;
		var list_row = new Array();
			list_row[0]=e;
		BJUI.alertmsg('confirm', '你确定要审核人员<span style="color:orange">'+nameStr+'</span>吗？', {
			okCall: function() {
				$.ajax({
					type:"post",
					url:"pop/alterOnePopAuditStatusInfo.do?"+new Date(),
					data:{"popIds" : list_row},
					dataType:"text",
					success:function(data){
						if(data==0){
							BJUI.alertmsg('error', '审核失败');
						}else{
							$.CurrentNavtab.find('#popInfo').datagrid('refresh', true);
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


function getOnePopDetail(e){
	BJUI.dialog({
		id:'popDetail',
	    target:$.CurrentNavtab.find("#popDetail"),
	    title:'人口详细信息',
	    width:950,
	    height:500,
	    mask:true,
	    resizable:false,
	    onClose:function(){
	    	$.CurrentNavtab.find('#popInfo').datagrid('selectedRows',false);
	    }
	});
	$.ajax({
		type : "get",
		url : "pop/getOnePopDetailInfoResult.do?"+new Date(),
		data : {"popId" : e},
		dataType : "json",
		cache : false,
		success : function(data) {
			var pop=data.population;
			$.CurrentDialog.find('#td_area_name').text(pop.area.areaName);
			$.CurrentDialog.find('#td_grid_name').text(pop.grid.gridName);
			$.CurrentDialog.find('#td_grid_stff_name').text(pop.gridStaff.gridStaffName);
			$.CurrentDialog.find('#td_pop_name').text(pop.popName);
			$.CurrentDialog.find('#td_pop_birthday').text(convertDate(pop.popBirthday).substring(0, 12));
			$.CurrentDialog.find('#td_pop_sex').text(pop.popSex);
			$.CurrentDialog.find('#td_account_name').text(pop.account.accountHolder);
			$.CurrentDialog.find('#td_account_relation').text(pop.holderRelation.relationName);
			$.CurrentDialog.find('#td_pop_native').text(pop.popNative);
			$.CurrentDialog.find('#td_pop_marriage').text(pop.marriageStatus.marriageName);
			$.CurrentDialog.find('#td_pop_level').text(pop.cultureLevel.levelName);
			$.CurrentDialog.find('#td_pop_nation').text(pop.popNation.nationName);
			$.CurrentDialog.find('#td_pop_politics').text(pop.politics.politicsName);
			$.CurrentDialog.find('#td_pop_job').text(pop.popJob);
			$.CurrentDialog.find('#td_pop_code').text(pop.popCode);
			$.CurrentDialog.find('#td_pop_tel').text(pop.popTel);
			$.CurrentDialog.find('#td_pop_job_name').text(pop.popCompany);
			$.CurrentDialog.find('#td_pop_editor').text(pop.editPopName);
			$.CurrentDialog.find('#td_pop_editDate').text(convertDate(pop.editPopDate));
			$.CurrentDialog.find('#td_pop_now_site').text(pop.popNowSite);
			$.CurrentDialog.find('#td_pop_job_site').text(pop.popJobSite);
			$.CurrentDialog.find('#td_pop_native_site').text(pop.popNativeSite);
			$.CurrentDialog.find('#td_pop_car').text(pop.popCar);
			$.CurrentDialog.find('#td_pop_hobby').text(pop.popHobby);
			var popBirthday=$.CurrentDialog.find('#td_pop_birthday').text();
			if(popBirthday!=''){
				var str=popBirthday.substring(0,4)+"/"+popBirthday.substring(5,7)+"/"+popBirthday.substring(8,10);   
				var age = (new Date()-new Date(str))/(1000*3600*365*24);
				$.CurrentDialog.find('#td_pop_age').text(parseInt(age)+"岁");
			}
			var insuredStr="否";
			if(pop.popInsured==1){
				insuredStr="是";
			}
			$.CurrentDialog.find('#td_pop_insured').text(insuredStr);
			var isflowStr="否";
			if(pop.isFlow==1){
				isflowStr="是";
				$.CurrentDialog.find('#tr_pop_flow')
					.after("<tr><td>流动类型</td><td>"+data.flowType.flowTypeName+"</td>" +
							"<td>流出地址</td><td colspan='3'>"+data.outflowSite+"</td></tr>" +
							"<tr><td>流动时间</td><td>"+convertDate(data.flowTime).substring(0, 12)+"</td>" +
									"<td>流入地址</td><td colspan='3'>"+data.inflowSite+"</td></tr>" +
							"<tr><td>流动原因</td><td colspan='5'>"+data.flowCause+"</td></tr>");
			}else{
				$.CurrentDialog.find('#tr_pop_flow')
					.after("<tr><td colspan='6' style='background-color:white;'>暂无信息</td></tr>");
			}
			$.CurrentDialog.find('#td_pop_isflow').text(isflowStr);
			var isholderStr="否";
			if(pop.householder==1){
				isholderStr="是";
			}
			$.CurrentDialog.find('#td_house_holder').text(isholderStr);
			var auditStr="<span style='color:red;'>未审核</span>";
			if(pop.popAudit==1){
				auditStr="<span style='color:grren;'>已通过</span>";
			}
			$.CurrentDialog.find('#td_pop_audit').html(auditStr);
			var size=pop.popAttrs.length;
			if(size>0){
				for(var i=0;i<size;i++){
					$.CurrentDialog.find('#tr_attr'+pop.popAttrs[i].popAttrId+'').iCheck('check');
				}
			}
			if(pop.popIcon!=""){
				var img = pop.popIcon.split(",");
				var str = "<img alt='' src='"+img[0]+"' style='max-width: 150px;max-height: 150px;'>";
				if(img.length>1){
					var url=img[0].substring(0,img[0].lastIndexOf("/")+1);
					for(var i=1;i<img.length;i++){
						img[i]=url+img[i];
						str += "&ensp;<img alt='' src='"+img[i]+"' style='max-width: 150px;max-height: 150px;'>";
					}
				}
				$.CurrentDialog.find('#td_pop_pic').html(str);
			}else{
				$.CurrentDialog.find('#td_pop_pic').html("<center>暂无图片信息</center>");
			}
		}
	});
}

//监听下拉文本框值改变事件
function valueChange(){
	$.CurrentDialog.find("#pop_gridId").change(function(){
		if($(this).val()!=""){
			getAllGridStaffByGridIdOfPop($(this).val());
		}else{
			$.CurrentDialog.find("#pop_gridStaffId").html("<option value=''>请选择网格员</option>");
	    	$.CurrentDialog.find('#pop_gridStaffId').selectpicker('refresh');
		}
	});
	$.CurrentDialog.find("#pop_courtId").change(function(){
		if($(this).val()!=""){
			getAllBlocksByCourtIdOfPop($(this).val());
		}else{
			$.CurrentDialog.find("#pop_unitId").html("<option value=''>请选择单元</option>");
			$.CurrentDialog.find("#pop_blockId").html("<option value=''>请选择楼栋</option>");
	    	$.CurrentDialog.find('#pop_unitId').selectpicker('refresh');
	    	$.CurrentDialog.find('#pop_blockId').selectpicker('refresh');
		}
	});
	$.CurrentDialog.find("#pop_blockId").change(function(){
		if($(this).val()!=""){
			getAllUnitsByBlockIdOfPop($(this).val());
		}else{
			$.CurrentDialog.find("#pop_unitId").html("<option value=''>请选择单元</option>");
	    	$.CurrentDialog.find('#pop_unitId').selectpicker('refresh');
		}
	});
	$.CurrentDialog.find("#isFlow").change(function(){
		if($(this).val()==1){
			$.CurrentDialog.find("#flowView").removeClass("hide");
			getAllFlowTypeOfPop();
			$.CurrentDialog.find("#flowTypeId").removeAttr("novalidate");
			$.CurrentDialog.find("#inflowSite").removeAttr("novalidate");
			$.CurrentDialog.find("#flowTime").removeAttr("novalidate");
			$.CurrentDialog.find("#outflowSite").removeAttr("novalidate");
			$.CurrentDialog.find("#flowCause").removeAttr("novalidate");
		}else{
			$.CurrentDialog.find("#flowView").addClass("hide");
			$.CurrentDialog.find("#flowTypeId").attr("novalidate", "novalidate");
			$.CurrentDialog.find("#inflowSite").attr("novalidate", "novalidate");
			$.CurrentDialog.find("#flowTime").attr("novalidate", "novalidate");
			$.CurrentDialog.find("#outflowSite").attr("novalidate", "novalidate");
			$.CurrentDialog.find("#flowCause").attr("novalidate", "novalidate");
		}
	});
	$.CurrentDialog.find("#householder").change(function(){
		if($("#householder").val()==1){
			if($("#pop_accountHolder").val()!=""){
				$("#popName").val($("#pop_accountHolder").val());
			}
		}else{
			$("#popName").val("");
		}
	});
	$.CurrentDialog.find("#popBirthday").change(function(){
		var str=$(this).val().substring(0,4)+"/"+$(this).val().substring(5,7)+"/"
				+$(this).val().substring(8,10);   
		var age = (new Date()-new Date(str))/(1000*3600*365*24);
		$.CurrentDialog.find("#popAge").val(parseInt(age));
	});
}


//保存人口编辑信息
function savePopInfo(){
	$.CurrentDialog.find("#popBtn").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addPopInfo").isValid()){
			if($.CurrentDialog.find("#popBtn > span").text().trim()=="提交"){
				nameStr+=$.CurrentDialog.find("#popName").val();
				$.ajax({
					type:"post",
					url:"pop/addOnePopInfo.do?"+new Date(),
					data:$.CurrentDialog.find("#addPopInfo").serialize(),
					dataType:"json",
					cache : false,
					success:function(data){
						if(data=="1"){
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#popInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加<span style='color:green'>"+nameStr+"<span>相关信息！", {
								 displayPosition:'middlecenter'
							});
						}else{
							BJUI.alertmsg('error', '添加人口信息失败！');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#popName").val();
				$.ajax({
					type:"post",
					url:"pop/editOnePopInfoById.do?"+new Date(),
					data:$.CurrentDialog.find("#addPopInfo").serialize(),
					dataType:"json",
					success:function(data){
						if(data=="0"){
							BJUI.alertmsg('error', "修改<span style='color:orange'>"+nameStr+"<span>相关信息失败！");
						}else{
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#popInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功修改<span style='color:green'>"+nameStr+"<span>相关信息！", {
								 displayPosition:'middlecenter'
							});
							if(data.message!=""){
								BJUI.alertmsg('info', data.message);
							}
							
						}
					}
				});
			}
		}else{
			BJUI.alertmsg('warn', '验证未通过！',{
				okCall:function(){
					$('#addPopInfo').validator('cleanUp');
				}
			});
		}
	});
}


/**
 * ztree下拉选择
 * 
 * @param e
 * @param treeId
 * @param treeNode
 */
//选择事件
function Pop_NodeCheck(e, treeId, treeNode) {
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
    	$("#pop_areaId").val(areaId);
    };
    getAllGridAndCourt($.CurrentDialog.find("#pop_areaId").val());
}

function getAllGridAndCourt(e){
	//获取选中区域下所有网格生成下拉菜单
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
	    	$.CurrentDialog.find("#pop_gridId").html(gridStr);
	    	$.CurrentDialog.find('#pop_gridId').selectpicker('refresh');
	    }
	 });
	//获取选中区域下所有小区生成下拉菜单
	BJUI.ajax('doajax', {
	    url: 'court/getAllCourtsByAreaId.do',
	    type:'get',
	    data:{"areaId":e},
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var courtStr="<option value=''>请选择小区名称</option>";
	    	for(var i=0;i<data.length;i++){
	    		courtStr+="<option value='" + data[i].courtId + "'>" + data[i].courtName + "</option>";
	    	}
	    	$.CurrentDialog.find("#pop_courtId").html(courtStr);
	    	$.CurrentDialog.find('#pop_courtId').selectpicker('refresh');
	    }
	});
	
}

//获取选中网格下所有网格员生成下拉菜单
function getAllGridStaffByGridIdOfPop(e){
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
	    	$.CurrentDialog.find("#pop_gridStaffId").html(gridStaffStr);
	    	$.CurrentDialog.find('#pop_gridStaffId').selectpicker('refresh');
	    }
	});
}


//获取所有楼栋
function getAllBlocksByCourtIdOfPop(e){
	BJUI.ajax('doajax', {
	    url: 'house/getBlockName.do',
	    type:'GET',
	    data:{"courtId":e},
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var blockStr="<option value=''>请选择楼栋</option>";
	    	for(var i=0;i<data.length;i++){
	    		blockStr+="<option value='" + data[i].blockId + "'>" + data[i].blockName + "</option>";
	    	}
	    	$.CurrentDialog.find("#pop_blockId").html(blockStr);
	    	$.CurrentDialog.find('#pop_blockId').selectpicker('refresh');
	    }
	});
}

//获取所有楼栋单元
function getAllUnitsByBlockIdOfPop(e){
	BJUI.ajax('doajax', {
	    url: 'house/getUnitName.do',
	    type:'GET',
	    data:{"blockId":e},
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var unitStr="<option value=''>请选择单元</option>";
	    	for(var i=0;i<data.length;i++){
	    		unitStr+="<option value='" + data[i].unitId + "'>" + data[i].unitName + "</option>";
	    	}
	    	$.CurrentDialog.find("#pop_unitId").html(unitStr);
	    	$.CurrentDialog.find('#pop_unitId').selectpicker('refresh');
	    }
	});
}

//获取所有下拉菜单选项值
function getAllSubMenuOfPop(){
	BJUI.ajax('doajax', {
	    url: 'pop/getAllHolderRelation.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var str="";
	    	for(var i=0;i<data.length;i++){
	    		str+="<option value='" + data[i].relationId + "'>" + data[i].relationName + "</option>";
	    	}
	    	$.CurrentDialog.find("#relationId").html(str);
	    	$.CurrentDialog.find('#relationId').selectpicker('refresh');
	    }
	});
	BJUI.ajax('doajax', {
	    url: 'pop/getAllMarriageStatus.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var str="";
	    	for(var i=0;i<data.length;i++){
	    		str+="<option value='" + data[i].marriageId + "'>" + data[i].marriageName + "</option>";
	    	}
	    	$.CurrentDialog.find("#marriageId").html(str);
	    	$.CurrentDialog.find('#marriageId').selectpicker('refresh');
	    }
	});
	BJUI.ajax('doajax', {
	    url: 'pop/getAllPopNation.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var str="";
	    	for(var i=0;i<data.length;i++){
	    		str+="<option value='" + data[i].nationId + "'>" + data[i].nationName + "</option>";
	    	}
	    	$.CurrentDialog.find("#nationId").html(str);
	    	$.CurrentDialog.find('#nationId').selectpicker('refresh');
	    }
	});
	BJUI.ajax('doajax', {
	    url: 'pop/getAllPolitics.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var str="";
	    	for(var i=0;i<data.length;i++){
	    		str+="<option value='" + data[i].politicsId + "'>" + data[i].politicsName + "</option>";
	    	}
	    	$.CurrentDialog.find("#politicsId").html(str);
	    	$.CurrentDialog.find('#politicsId').selectpicker('refresh');
	    }
	});
	
	BJUI.ajax('doajax', {
	    url: 'pop/getAllCultureLevel.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var str="";
	    	for(var i=0;i<data.length;i++){
	    		str+="<option value='" + data[i].levelId + "'>" + data[i].levelName + "</option>";
	    	}
	    	$.CurrentDialog.find("#levelId").html(str);
	    	$.CurrentDialog.find('#levelId').selectpicker('refresh');
	    }
	});
}

function getAllFlowTypeOfPop(){
	BJUI.ajax('doajax', {
	    url: 'pop/getAllFlowType.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var str="";
	    	for(var i=0;i<data.length;i++){
	    		str+="<option value='" + data[i].flowTypeId + "'>" + data[i].flowTypeName + "</option>";
	    	}
	    	$.CurrentDialog.find("#flowTypeId").html(str);
	    	$.CurrentDialog.find('#flowTypeId').selectpicker('refresh');
	    }
	});
}

//FindGrid JS API 调用
function getAllAccount(obj) {
	var unitId=$.CurrentDialog.find("#pop_unitId").val();
	var findGrid_title=$.CurrentDialog.find("#pop_courtId option:selected").text()
						+$.CurrentDialog.find("#pop_blockId option:selected").text()
						+$.CurrentDialog.find("#pop_unitId option:selected").text();
	if(unitId!=""){
		BJUI.findgrid({
	        empty:false,
	        include: 'accountId:accountId,pop_accountHolder:accountHolder',
	        dialogOptions: {id:'unitAccount',title:findGrid_title+'户主信息',width:700},
	        gridOptions: {
	            local: 'local',
	            linenumberAll:true,
	    	    showCheckboxcol:true,
	    	    hScrollbar:false,
	    	    columnLock:false,
	    	    sortAll:true,
	    	    filterAll:true,
	            postData:{"unitId":unitId},
	            dataUrl: 'house/getAllAccountByUnitId.do',
	            columns: [
	                {name:'accountId', label:'ID',align:'center',width:30},
	                {name:'house', label:'门牌号',align:'center',width:60,
	   	        	 render:function(value, data){
							return value.houseNum;
						}
	                },
	                {name:'accountHolder', label:'户主姓名',align:'center',width:50},
	                {name:'holderTel', label:'户主电话',align:'center',width:80},
	                {name:'holderCard', label:'身份证号',align:'center',width:100}
	            ],
	            paging:{pageSize:30, selectPageSize:'60,90', pageCurrent:1, showPagenum:5, totalRow:0},
	        },
	        afterSelect:function(data){
	        	if($("#householder").val()==1){
	    			$("#popName").val(data.accountHolder);
	    		}
	        }
	    });
	}else{
		$.CurrentDialog.find("#pop_accountHolder").attr("novalidate", "novalidate");
		BJUI.alertmsg('warn', '请选择新增人员所处楼栋单元信息！');
	}
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

function refreshPop(){
	$.CurrentNavtab.find('#popInfo').datagrid('refresh', false);
}