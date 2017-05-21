/**
 * 刘春晓 accountPop.html外部js
 */

var auditPop=false;
$(function(){
	$.ajax({
		type:'get',
		url:'menu/getMenuPerById.do?'+new Date(),
		data:{"menuName":'人口信息'},
	    dataType:'json',
	    cache:false,
	    success:function(data){
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
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addPopInfoOfAccount();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editPopInfoOfAccount();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deletePopInfoOfAccount();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='auditPopInfoOfAccount();'>审核</button>";
	    			auditPop=true;
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
			$.CurrentNavtab.find('#accountPopInfo').datagrid({
				height: '100%',
			    tableWidth:'100%',
			    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
			    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
			    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
			    showToolbar: toolBar,
			    toolbarCustom: toolBarItem,
			    local: 'remote',
		//	    data:{"accountId":localStorage.accountId},
			    dataUrl:'account/getAccountPopByAccountId.do?accountId='+sessionStorage.accountId,
			    columns: [
			        {name:'popId',label:'ID',align:'center'},
			        {name:'popName',label:'姓名',align:'center'},
			        {name:'popSex',label:'性别',align:'center'},
			        {name:'popCode',label:'身份证号',align:'center',
	    	        	render:function(value,data){
	    	        		var codeStr=/^(.{6})(.*)(.{4})$/.exec(value);
	    	        		console.log(codeStr);
	    	        		if(codeStr!=null){
	    	        			return codeStr[1]+"****"+codeStr[3];
	    	        		}
	    	        	}
	    	        },
			        {name:'area',label:'所属区域',align:'center',
			        	render:function(value, data){
			        		return value.areaName;
			        	}
			        },
			        {name:'grid',label:'所属网格',align:'center',
			        	render:function(value, data){
			        		return value.gridName;
			        	}
			        	},
			        {name:'gridStaff',label:'网格员',align:'center',
		    	        	render:function(value, data){
		    	        		return value.gridStaffName;
		    	        	}
			        	},
			        {name:'popAudit',label:'审核状态',align:'center',
			        	items:[{"sex":true,"name":"已通过"},{"sex":false,"name":"审核中"}],
			        	itemattr:{value:'sex',label:'name'},
			        	render:function(value, data){
			        		var status="<a href='javascript:void(0);' onclick='auditAcPopSingle("+data.popId+",\""+data.popName+"\","+value+");'>";
			        		if(value==0){
			        			status+= "<i class='am-icon-check-square-o' style='color:green'></i>";
			        		}else{
			        			status+= "<i class='am-icon-minus-square-o' style='color:red'></i>";
			        		}	
			        		return status;
			        	}
			        },
			        {name:' ',label:'操作',align:'center',hide:status,
			        	render:function(value, data){	    	      
			        			return "<button type='button' class='btn-default' data-icon='search' onclick='getOneAccountPopDetail("+data.popId+");'>查看</button>";;
			        	},
			        },
			    ],
			    paging:{pageSize:30, selectPageSize:'10,20,40,50', pageCurrent:1, showPagenum:5, totalRow:0},
			    linenumberAll: true,
			    showCheckboxcol:true,
			    hScrollbar:false,
			    columnLock:false,
			    sortAll:true,
			    filterAll:true,
			    fieldSortable:true,
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
			    				+"id='AcPop_popAttr"+data[i].popAttrId+"' " 
			    				+"value='"+data[i].popAttrId+"' data-toggle='icheck' "
			    				+"data-label='"+data[i].popAttrName+"' />"+spaceStr+brStr;
			    		spaceStr="";
			    		brStr="";
			    	}
			    	$("#AcPop_popAttrIcheck").html(str);
			    }
			});
			
			//将人口流动信息栏的验证禁用
			$.CurrentNavtab.find("#AcPop_flowTypeId").attr("novalidate", "novalidate");
			$.CurrentNavtab.find("#AcPop_inflowSite").attr("novalidate", "novalidate");
			$.CurrentNavtab.find("#AcPop_flowTime").attr("novalidate", "novalidate");
			$.CurrentNavtab.find("#AcPop_outflowSite").attr("novalidate", "novalidate");
			$.CurrentNavtab.find("#AcPop_flowCause").attr("novalidate", "novalidate");
	    }
	});	
});


/**
 * 增加人口信息方法
 */
function addPopInfoOfAccount(){
	BJUI.dialog({
		id:'addACPop',
	    target:$.CurrentNavtab.find("#addACPop"),
	    title:'添加人口信息',
	    width:950,
	    height:500,
	    mask:true,
	    resizable:false,
	    onClose:function(){
	    	$.CurrentNavtab.find('#accountPopInfo').datagrid('selectedRows',false);
	    },
	    onLoad:function($dialog){
	    	var areaId=sessionStorage.userAreaId;
	    	var zTree = $.fn.zTree.getZTreeObj("AcPop_areaNameMenu");
			var node = zTree.getNodeByParam("areaId",areaId);
			node.checked = true,zTree.updateNode(node);
			$('#AcPop_areaName').val(node.name),$('#AcPop_areaId').val(areaId);
//			$("#popIcon").click(function(){
//				$('html,body').animate({scrollTop:$('#popUploadArea').offset().top}, 800);
//			});
		}
	});
	$.CurrentDialog.find("#AcPop_accountHolder").attr("value",sessionStorage.accountHolder);
	$.CurrentDialog.find("#AcPop_accountId").attr("value",sessionStorage.accountId);
	getAllGridByAreaIdOfAccount(sessionStorage.userAreaId);
	getAllSubMenuOfAccount();
	valueChangeOfAccount();
	savePopInfoOfAccount();
}

/**
 * 编辑人口信息方法
 */
function editPopInfoOfAccount(){
	var row=$.CurrentNavtab.find('#accountPopInfo').data('selectedDatas');
	if(typeof(row)=="undefined" || row.length!=1){
		BJUI.alertmsg('info', '请选择一条记录进行编辑！');
	}else{
		BJUI.dialog({
			id:'editPop',
		    target:$.CurrentNavtab.find("#addACPop"),
		    title:'修改人口信息',
		    width:950,
		    height:500,
		    mask:true,
		    resizable:false,
		    onClose:function(){
		    	$.CurrentNavtab.find('#accountPopInfo').datagrid('selectedRows',false);
		    },
	     	onLoad:function($dialog){
				$("#AcPop_popBtn > span").text("修改");
			}
		});
		console.log(row);
		$.ajax({
			type : "post",
			url : "pop/getOnePopInfo.do?"+new Date(),
			data : {"popId" : row[0].popId},
			dataType : "json",
			cache : false,
			success : function(data) {
				var pop=data.population;
				var attrs=pop.popAttrs;
		    	var zTree = $.fn.zTree.getZTreeObj("AcPop_areaNameMenu");
				var node = zTree.getNodeByParam("areaId",pop.areaId);
				node.checked = true,zTree.updateNode(node);
				getAllGridByAreaIdOfAccount(pop.areaId);
				getAllSubMenuOfAccount();
				$.CurrentDialog.find('#AcPop_areaName').val(node.name);
				$.CurrentDialog.find('#AcPop_areaId').val(pop.areaId);
				$.CurrentDialog.find('#AcPop_gridId').selectpicker('val',pop.gridId);
				getAllGridStaffByGridIdOfAccount($.CurrentDialog.find('#AcPop_gridId').val());
				$.CurrentDialog.find('#AcPop_gridStaffId').selectpicker('val',pop.gridStaffId);
				$.CurrentDialog.find('#AcPop_householder').selectpicker('val',pop.householder);
				$.CurrentDialog.find('#AcPop_accountId').val(sessionStorage.accountId);
				$.CurrentDialog.find('#AcPop_relationId').selectpicker('val',pop.relationId);
				$.CurrentDialog.find('#AcPop_popName').val(pop.popName);
				$.CurrentDialog.find('#AcPop_popBirthday').val(convertDate(pop.popBirthday).substring(0, 12));
				$.CurrentDialog.find('#AcPop_popSex').selectpicker('val',pop.popSex);
				$.CurrentDialog.find('#AcPop_popInsured').selectpicker('val',pop.popInsured);
				$.CurrentDialog.find('#AcPop_isFlow').selectpicker('val',pop.isFlow);
				$.CurrentDialog.find('#AcPop_popNative').val(pop.popNative);
				$.CurrentDialog.find('#AcPop_marriageId').selectpicker('val',pop.marriageId);
				$.CurrentDialog.find('#AcPop_popJob').val(pop.popJob);
				$.CurrentDialog.find('#AcPop_nationId').selectpicker('val',pop.nationId);
				$.CurrentDialog.find('#AcPop_politicsId').selectpicker('val',pop.politicsId);
				$.CurrentDialog.find('#AcPop_levelId').selectpicker('val',pop.levelId);
				$.CurrentDialog.find('#AcPop_popCode').val(pop.popCode);
				$.CurrentDialog.find('#AcPop_popTel').val(pop.popTel);
				$.CurrentDialog.find('#AcPop_popId').val(pop.popId);
				$.CurrentDialog.find('#AcPop_editPopDate').val(convertDate(pop.editPopDate));
				$.CurrentDialog.find('#AcPop_popCompany').val(pop.popCompany);
				$.CurrentDialog.find('#AcPop_popJobSite').val(pop.popJobSite);
				$.CurrentDialog.find('#AcPop_popIcon').val(pop.popIcon);
				$.CurrentDialog.find('#AcPop_popNowSite').val(pop.popNowSite);
				$.CurrentDialog.find('#AcPop_popNativeSite').val(pop.popNativeSite);
				$.CurrentDialog.find('#AcPop_popCar').text(pop.popCar);
				$.CurrentDialog.find('#AcPop_popHobby').text(pop.popHobby);
				$.CurrentDialog.find('#AcPop_accountHolder').val(pop.account.accountHolder);
				var popBirthday=$.CurrentDialog.find('#AcPop_popBirthday').val();
				if(popBirthday!=''){
					var str=popBirthday.substring(0,4)+"/"+popBirthday.substring(5,7)+"/"+popBirthday.substring(8,10);   
					var age = (new Date()-new Date(str))/(1000*3600*365*24);
					$.CurrentDialog.find("#AcPop_popAge").val(parseInt(age));
				}
				if($.CurrentDialog.find('#AcPop_isFlow').selectpicker('val')==1){
					$.CurrentDialog.find("#AcPop_flowView").removeClass("hide");
					getAllFlowTypeOfAccount();
					$.CurrentDialog.find("#AcPop_flowTypeId").removeAttr("novalidate");
					$.CurrentDialog.find("#AcPop_inflowSite").removeAttr("novalidate");
					$.CurrentDialog.find("#AcPop_flowTime").removeAttr("novalidate");
					$.CurrentDialog.find("#AcPop_outflowSite").removeAttr("novalidate");
					$.CurrentDialog.find("#AcPop_flowCause").removeAttr("novalidate");
					$.CurrentDialog.find('#AcPop_flowTypeId').selectpicker('val',data.flowType.flowTypeId);
					$.CurrentDialog.find('#AcPop_flowId').val(data.flowId);
					$.CurrentDialog.find('#AcPop_inflowSite').val(data.inflowSite);
					$.CurrentDialog.find('#AcPop_flowTime').val(convertDate(data.flowTime).substring(0, 12));
					$.CurrentDialog.find('#AcPop_outflowSite').val(data.outflowSite);
					$.CurrentDialog.find('#AcPop_flowCause').val(data.flowCause);
				}else{
					$.CurrentDialog.find("#AcPop_flowView").addClass("hide");
					$.CurrentDialog.find("#AcPop_flowTypeId").attr("novalidate", "novalidate");
					$.CurrentDialog.find("#AcPop_inflowSite").attr("novalidate", "novalidate");
					$.CurrentDialog.find("#AcPop_flowTime").attr("novalidate", "novalidate");
					$.CurrentDialog.find("#AcPop_outflowSite").attr("novalidate", "novalidate");
					$.CurrentDialog.find("#AcPop_flowCause").attr("novalidate", "novalidate");
				}
				if(attrs.length>0){
					var size=attrs.length;
					for(var i=0;i<size;i++){
						$.CurrentDialog.find('#AcPop_popAttr'+attrs[i].popAttrId+'').iCheck('check');
					}
				}
			}
		});
		valueChangeOfAccount();
		savePopInfoOfAccount();
	}
}

/**
 * 表单验证
 */
function savePopInfoOfAccount(){
	$.CurrentDialog.find("#AcPop_popBtn").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addAcPopInfo").isValid()){
			if($.CurrentDialog.find("#AcPop_popBtn > span").text().trim()=="提交"){
				nameStr+=$.CurrentDialog.find("#AcPop_popName").val();
				$.ajax({
					type:"post",
					url:"pop/addOnePopInfo.do?"+new Date(),
					data:$.CurrentDialog.find("#addAcPopInfo").serialize(),
					dataType:"json",
					cache : false,
					success:function(data){
						if(data=="1"){
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#accountPopInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加<span style='color:green'>"+nameStr+"<span>相关信息！", {
								 displayPosition:'middlecenter'
							});
						}else{
							BJUI.alertmsg('error', '添加人口信息失败！');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#AcPop_popName").val();
				$.ajax({
					type:"post",
					url:"pop/editOnePopInfoById.do?"+new Date(),
					data:$.CurrentDialog.find("#addAcPopInfo").serialize(),
					dataType:"json",
					success:function(data){
						if(data=="0"){
							BJUI.alertmsg('error', "修改<span style='color:orange'>"+nameStr+"<span>相关信息失败！");
						}else{
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#accountPopInfo').datagrid('refresh', true);
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
					$('#addAcPopInfo').validator('cleanUp');
				}
			});
		}
	});
}

/**
 * 图片审核
 */
function auditAcPopSingle(e,f,g){
	if(auditPop==true){
		if(g==0){
			return false;
		}else{
			var list_row = new Array();
			var nameStr=f;
			list_row[0]=e;
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
								$.CurrentNavtab.find('#accountPopInfo').datagrid('refresh', true);
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

function auditPopInfoOfAccount(){
	var row=$.CurrentNavtab.find("#accountPopInfo").data('selectedDatas');
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
							$.CurrentNavtab.find('#accountPopInfo').datagrid('refresh', true);
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

function deletePopInfoOfAccount(){
	var row=$.CurrentNavtab.find('#accountPopInfo').data('selectedDatas');
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
							$.CurrentNavtab.find('#accountPopInfo').datagrid('refresh', true);
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

function getOneAccountPopDetail(e){
	BJUI.dialog({
		id:'selectPopAccount',
	    target:$.CurrentNavtab.find("#selectPopAccount"),
	    title:'人口详细信息',
	    width:950,
	    height:500,
	    mask:true,
	    resizable:false,
	    onClose:function(){
	    	$.CurrentNavtab.find('#accountPopInfo').datagrid('selectedRows',false);
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
			$.CurrentDialog.find('#Ac_area_name').text(pop.area.areaName);
			$.CurrentDialog.find('#Ac_grid_name').text(pop.grid.gridName);
			$.CurrentDialog.find('#Ac_grid_stff_name').text(pop.gridStaff.gridStaffName);
			$.CurrentDialog.find('#Ac_pop_name').text(pop.popName);
			$.CurrentDialog.find('#Ac_pop_birthday').text(convertDate(pop.popBirthday).substring(0, 12));
			$.CurrentDialog.find('#Ac_pop_sex').text(pop.popSex);
			$.CurrentDialog.find('#Ac_account_name').text(pop.account.accountHolder);
			$.CurrentDialog.find('#Ac_account_relation').text(pop.holderRelation.relationName);
			$.CurrentDialog.find('#Ac_pop_native').text(pop.popNative);
			$.CurrentDialog.find('#Ac_pop_marriage').text(pop.marriageStatus.marriageName);
			$.CurrentDialog.find('#Ac_pop_level').text(pop.cultureLevel.levelName);
			$.CurrentDialog.find('#Ac_pop_nation').text(pop.popNation.nationName);
			$.CurrentDialog.find('#Ac_pop_politics').text(pop.politics.politicsName);
			$.CurrentDialog.find('#Ac_pop_job').text(pop.popJob);
			$.CurrentDialog.find('#Ac_pop_code').text(pop.popCode);
			$.CurrentDialog.find('#Ac_pop_tel').text(pop.popTel);
			$.CurrentDialog.find('#Ac_pop_job_name').text(pop.popCompany);
			$.CurrentDialog.find('#Ac_pop_editor').text(pop.editPopName);
			$.CurrentDialog.find('#Ac_pop_editDate').text(convertDate(pop.editPopDate));
			$.CurrentDialog.find('#Ac_pop_now_site').text(pop.popNowSite);
			$.CurrentDialog.find('#Ac_pop_job_site').text(pop.popJobSite);
			$.CurrentDialog.find('#Ac_pop_native_site').text(pop.popNativeSite);
			$.CurrentDialog.find('#Ac_pop_car').text(pop.popCar);
			$.CurrentDialog.find('#Ac_pop_hobby').text(pop.popHobby);
			var popBirthday=$.CurrentDialog.find('#Ac_pop_birthday').text();
			if(popBirthday!=''){
				var str=popBirthday.substring(0,4)+"/"+popBirthday.substring(5,7)+"/"+popBirthday.substring(8,10);   
				var age = (new Date()-new Date(str))/(1000*3600*365*24);
				$.CurrentDialog.find('#Ac_pop_age').text(age+"岁");
			}
			var insuredStr="否";
			if(pop.popInsured==1){
				insuredStr="是";
			}
			$.CurrentDialog.find('#Ac_pop_insured').text(insuredStr);
			var isflowStr="否";
			if(pop.isFlow==1){
				isflowStr="是";
			}
			$.CurrentDialog.find('#Ac_pop_isflow').text(isflowStr);
			var isholderStr="否";
			if(pop.householder==1){
				isholderStr="是";
			}
			$.CurrentDialog.find('#Ac_house_holder').text(isholderStr);
			var auditStr="<span style='color:red;'>未审核</span>";
			if(pop.popAudit==1){
				auditStr="<span style='color:grren;'>已通过</span>";
			}
			$.CurrentDialog.find('#Ac_pop_audit').html(auditStr);
			
		}
	});
}

function getAllGridByAreaIdOfAccount(e){
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
	    	$.CurrentDialog.find("#AcPop_gridId").html(gridStr);
	    	$.CurrentDialog.find('#AcPop_gridId').selectpicker('refresh');
	    }
	 });
	
}

//获取所有下拉菜单选项值
function getAllSubMenuOfAccount(){
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
	    	$.CurrentDialog.find("#AcPop_relationId").html(str);
	    	$.CurrentDialog.find('#AcPop_relationId').selectpicker('refresh');
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
	    	$.CurrentDialog.find("#AcPop_marriageId").html(str);
	    	$.CurrentDialog.find('#AcPop_marriageId').selectpicker('refresh');
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
	    	$.CurrentDialog.find("#AcPop_nationId").html(str);
	    	$.CurrentDialog.find('#AcPop_nationId').selectpicker('refresh');
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
	    	$.CurrentDialog.find("#AcPop_politicsId").html(str);
	    	$.CurrentDialog.find('#AcPop_politicsId').selectpicker('refresh');
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
	    	$.CurrentDialog.find("#AcPop_levelId").html(str);
	    	$.CurrentDialog.find('#AcPop_levelId').selectpicker('refresh');
	    }
	});
}

//监听下拉文本框值改变事件
function valueChangeOfAccount(){
	$.CurrentDialog.find("#AcPop_gridId").change(function(){
		if($(this).val()!=""){
			getAllGridStaffByGridIdOfAccount($(this).val());
		}else{
			$.CurrentDialog.find("#AcPop_gridStaffId").html("<option value=''>请选择网格员</option>");
	    	$.CurrentDialog.find('#AcPop_gridStaffId').selectpicker('refresh');
		}
	});
	$.CurrentDialog.find("#AcPop_isFlow").change(function(){
		if($(this).val()==1){
			$.CurrentDialog.find("#AcPop_flowView").removeClass("hide");
			getAllFlowTypeOfAccount();
			$.CurrentDialog.find("#AcPop_flowTypeId").removeAttr("novalidate");
			$.CurrentDialog.find("#AcPop_inflowSite").removeAttr("novalidate");
			$.CurrentDialog.find("#AcPop_flowTime").removeAttr("novalidate");
			$.CurrentDialog.find("#AcPop_outflowSite").removeAttr("novalidate");
			$.CurrentDialog.find("#AcPop_flowCause").removeAttr("novalidate");
		}else{
			$.CurrentDialog.find("#AcPop_flowView").addClass("hide");
			$.CurrentDialog.find("#AcPop_flowTypeId").attr("novalidate", "novalidate");
			$.CurrentDialog.find("#AcPop_inflowSite").attr("novalidate", "novalidate");
			$.CurrentDialog.find("#AcPop_flowTime").attr("novalidate", "novalidate");
			$.CurrentDialog.find("#AcPop_outflowSite").attr("novalidate", "novalidate");
			$.CurrentDialog.find("#AcPop_flowCause").attr("novalidate", "novalidate");
		}
	});
	$.CurrentDialog.find("#AcPop_householder").change(function(){
		if($("#AcPop_householder").val()==1){
			if($("#AcPop_pop_accountHolder").val()!=""){
				$("#AcPop_popName").val($("#AcPop_accountHolder").val());
			}
		}else{
			$("#AcPop_popName").val("");
		}
	});
	$.CurrentDialog.find("#AcPop_popBirthday").change(function(){
		var str=$(this).val().substring(0,4)+"/"+$(this).val().substring(5,7)+"/"
				+$(this).val().substring(8,10);   
		var age = (new Date()-new Date(str))/(1000*3600*365*24);
		$.CurrentDialog.find("#AcPop_popAge").val(parseInt(age));
	});
}



//获取选中网格下所有网格员生成下拉菜单
function getAllGridStaffByGridIdOfAccount(e){
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
	    	$.CurrentDialog.find("#AcPop_gridStaffId").html(gridStaffStr);
	    	$.CurrentDialog.find('#AcPop_gridStaffId').selectpicker('refresh');
	    }
	});
}


function getAllFlowTypeOfAccount(){
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
	    	$.CurrentDialog.find("#AcPop_flowTypeId").html(str);
	    	$.CurrentDialog.find('#AcPop_flowTypeId').selectpicker('refresh');
	    }
	});
}