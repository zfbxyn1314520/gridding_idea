/**
 * road.html页面的外部js代码
 * author 刘春晓
 */

var auditCom=false;
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
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addcompanyInfo();'>添加</button>";
	    		}
	    		if(per[z]=="编辑"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editcompanyInfo();'>编辑</button>";
	    		}
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deletecompanyInfo();'>删除</button>";
	    		}
	    		if(per[z]=="审核"){
	    			toolBarItem+="<button type='button' class='btn-orange' data-icon='check' onclick='auditcompanyInfo();'>审核</button>";
	    			auditCom=true;
	    		}
	    		if(per[z]=="查看"){
	    			status=false;
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshCompany();'>刷新</button>";
	    	//生成datagrid表格
	    	$('#companyInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'company/getAllCompanyByAreaId.do',
	    	    columns: [
	    	        {name:'area',label:'所属区域',align:'center',width:width*0.18,
	    	        	render:function(value, data){
	    	        		return value.areaName;
	    	        	}
	    	        },
	    	        {name:'companyName',label:'企业名称',align:'center',width:width*0.26},
	    	        {name:'companyNature',label:'企业性质',align:'center',width:width*0.12,
	    	        	render:function(value, data){
	    	        		return value.natureName;
	    	        	}
	    	        },
	    	        {name:'companyTrade',label:'所处行业',align:'center',width:width*0.12,
	    	        	render:function(value, data){
	    	        		return value.tradeName;
	    	        	}
	    	        },
	    	        {name:'companyScale',label:'企业规模',align:'center',width:width*0.1,
	    	        	render:function(value, data){
	    	        		return value.scaleName;
	    	        	}
	    	        },
	    	        {name:'companyAudit',label:'状态',align:'center',width:width*0.08,
	    	        	render:function(value, data){
	    	        		if(value==0){
	    	        			return "<i class='am-icon-check-square-o' style='color:green'></i>";
	    	        		}else{
	    	        			return "<i class='am-icon-minus-square-o' style='color:red'></i>";
	    	        		}     		
	    	        	}
	    	        },
	    	        {name:' ',label:'操作',align:'center',hide:status,width:width*0.16,
	    	        	render:function(value, data){
	    	        		var str="";
	    	        		if(data.companyAudit==1){
	    	        			str+="&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
        						"onclick='auditComSingle("+data.companyId+",\""+data.companyName+"\","+data.companyAudit+");'>审核</button>";
	    	        		}else{
	    	        			str+="&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
	    	        		}
	    	        		return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
	    	        				"onclick='getOnecompanyDetail("+data.companyId+");'>详细信息</button>"+str;
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

//打开增加页面
function addcompanyInfo(){
	$.ajax({
		type:"post",
		url:"company/getAreaName.do?"+new Date(),
		dataType:"json",
		success:function(data){
			 BJUI.dialog({
			        id:'addCompany',
			        width: 755,
			        height: 500,
			        target:$("#addCompany"),
			        title:'新增企业',
			        mask:true,
			        resizable:false,
			        onClose:function(){
			        	$.CurrentNavtab.find('#companyInfo').datagrid('selectedRows',false);
			     	},
					onLoad:function($dialog){
						var that  = this;
						var width  = that.options.width > that.options.minW ? that.options.width : that.options.minW;
						var wW     = $(window).width(),iTop   = that.options.max ? 0 : (($(window).height() - 680) / 3);
						if (width > wW)  width  = wW;
						$("#companyPic").click(function(event){
							$dialog.height(680)
								.css({left:(wW - width) / 2, top:0, opacity:0.1})
								.animate({top:iTop > 0 ? iTop : 0, opacity:1})
								.find('> .dialogContent').height(680 - $('> .dialogHeader', $dialog).outerHeight());
							$dialog.find('> .dialogContent').css({height:(680- $dialog.find('> .dialogHeader').outerHeight())}); 
							$("#companyUploadArea").removeClass("hide");
							$.CurrentDialog.find("#filePicker div:last").css({"left":"271px","width":"156px","height":"25px","bottom":"auto","right":"auto"});
							$.CurrentDialog.find("#filePicker2 div:last").css({"left":"10px","width":"94px","height":"27px","bottom":"auto","right":"auto"});
						});
					}
			 });
			var areaName=data.areaName;
			var areaId=data.areaId;
			$.CurrentDialog.find("#c_areaName").attr("value",areaName);
			$.CurrentDialog.find("#c_areaId").attr("value",areaId);
			getCompanyNature();
			getAllCompanyScale();
			getAllCompanyTrade();
			saveCompanyInfo();
			
		}
	});
}


//打开修改弹窗
function editcompanyInfo(){
	var companyId=$.CurrentNavtab.find("#companyInfo").data('selectedDatas');
	if(companyId==null || companyId.length!=1){
		BJUI.alertmsg('info', '请选择一条需要编辑的道路');
	}else{
		BJUI.dialog({
	        id:'addCompany',
	        target:$.CurrentNavtab.find("#addCompany"),
	        width:755,
		    height:700,
	        title:'修改企业信息',
	        mask:true,
	        resizable:false,
	        onClose:function(){
	        	$.CurrentNavtab.find('#companyInfo').datagrid('selectedRows',false);
	     	},
	     	onLoad:function($dialog){
				$("#companyUploadArea").removeClass("hide");
				$("#submitBtnc > span").text("修改");
			}
		});
		var upCid="";
		for(var i=0;i<companyId.length;i++){
			upCid=companyId[i].companyId;
		}
		$.ajax({
			type:"post",
			url:"company/getCompanyById.do?"+new Date(),
			data:{"upCid":upCid},
			dataType:"json",
			success:function(data){
				getCompanyNature();
				getAllCompanyScale();
				getAllCompanyTrade();
				var areaname=data.area.areaName;
				var zTree = $.fn.zTree.getZTreeObj("c_areaNameMenu");
				var node = zTree.getNodeByParam("areaId",data.area.areaId);
				areaname=areaname.substring(areaname.lastIndexOf(",")+1,areaname.length);
				node.checked = true; 
				zTree.updateNode(node);
				$.CurrentDialog.find('#c_areaName').val(areaname.trim());
				$.CurrentDialog.find('#c_areaId').val(data.area.areaId);
				$.CurrentDialog.find("#companyName").attr("value",data.companyName);
				$.CurrentDialog.find("#companyId").attr("value",data.companyId);
				$.CurrentDialog.find("#companyMan").attr("value",data.companyMan);
				$.CurrentDialog.find("#companyTel").attr("value",data.companyTel);
				$.CurrentDialog.find("#companyPic").attr("value",data.companyPic);
//				var sel = document.getElementById('#natureId');
//			    for(var x=0;x<sel.options.length;x++){
//			        if(sel.options[x].value==data.natureId){
//			            sel.options[x].selected=true;
//			            break;
//			        }
//			    }
				$.CurrentDialog.find('#natureId').selectpicker('val',data.natureId);
				$.CurrentDialog.find('#scaleId').selectpicker('val',data.scaleId);
				$.CurrentDialog.find('#tradeId').selectpicker('val',data.tradeId);
				$.CurrentDialog.find("#editComDate").val(convertDate(data.editComDate));
				$.CurrentDialog.find("#companySite").attr("value",data.companySite);
				$.CurrentDialog.find("#creditCode").attr("value",data.creditCode);
				$.CurrentDialog.find("#registerOffice").attr("value",data.registerOffice);
				$.CurrentDialog.find("#companyIntro").text(data.companyIntro);
			}
		});
		saveCompanyInfo();
	}
}

//表单验证
function saveCompanyInfo(){
	$.CurrentDialog.find("#submitBtnc").click(function() {
		var nameStr="";
		if($.CurrentDialog.find("#addCompanyInfo").isValid()){
			if($.CurrentDialog.find("#submitBtnc > span").text().trim()=="提交"){
				nameStr+=$.CurrentDialog.find("#companyName").val();
				$.ajax({
					type:"post",
					url:"company/saveCompany.do?"+new Date(),
					data:$.CurrentDialog.find("#addCompanyInfo").serialize(),
					dataType:"json",
					cache : false,
					success:function(data){
						if(data=="1"){
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#companyInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', "成功添加企业<span style='color:green'>"+nameStr+"<span>！", {
								 displayPosition:'middlecenter'
							});
						}else{
							BJUI.alertmsg('error', '保存失败');
						}
					}
				});
			}else{
				nameStr+=$.CurrentDialog.find("#companyName").val();
				$.ajax({
					type:"post",
					url:"company/editCompanyById.do?"+new Date(),
					data:$.CurrentDialog.find("#addCompanyInfo").serialize(),
					dataType:"text",
					success:function(data){
						if(data==0){
							 BJUI.alertmsg('error', '修改失败');
						}else{
							 BJUI.alertmsg('ok', '成功修改企业'+nameStr+"!",{
								 displayPosition:'middlecenter'
								});
							BJUI.dialog('closeCurrent');
							$.CurrentNavtab.find('#companyInfo').datagrid('refresh', true);
						}
					}
				});
			}
		}else{
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#addCompanyInfo').validator('cleanUp');
 				}
 			});
		}
	});
}

//删除方法
function deletecompanyInfo(){
	var companyId=$.CurrentNavtab.find("#companyInfo").data('selectedDatas');
	var deCid="";
	var nameStr="";
	if(typeof(companyId)=="undefined"||companyId.length==0){
		BJUI.alertmsg('info', '请选择需要删除的企业');
	}else{
		for(var i=0;i<companyId.length;i++){
			deCid+=companyId[i].companyId+",";
			nameStr+=companyId[i].companyName+",";
		}
		nameStr=nameStr.substring(0, nameStr.length-1);
		BJUI.alertmsg('confirm', '你确定要删除企业'+nameStr+'吗？', {
			okCall: function() {
				$.ajax({
					type:"post",
					url:"company/deleteCompany.do?"+new Date(),
					data:{"deCid" : deCid},
					dataType:"text",
					success:function(data){
						if(data==0){
							   BJUI.alertmsg('ok', '删除失败');
						}else{
							$.CurrentNavtab.find('#companyInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', '成功删除企业'+nameStr+"!", {
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
function auditComSingle(e,f,g){
	if(auditCom==true){
		if(g==0){
			return false;
		}else{
			var audCid=e;
			var nameStr=f;
			BJUI.alertmsg('confirm', '你确定要审核企业<span style="color:orange">'+nameStr+'</span>吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"company/updateAuditCompany.do?"+new Date(),
						data:{"audCid" : audCid},
						dataType:"text",
						success:function(data){
							if(data==0){
								   BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#companyInfo').datagrid('refresh', true);
							}
						}
					});
				}
			});
		}
	}
}

//审核方法
function auditcompanyInfo(){
	var companyId=$.CurrentNavtab.find("#companyInfo").data('selectedDatas');
	var audCid="";
	var nameStr="";
	if(typeof(companyId)=="undefined"||companyId.length==0){
		BJUI.alertmsg('info', '请选择需要审核的企业');
	}else{
		for(var i=0;i<companyId.length;i++){
			if(companyId[i].companyAudit==0){
				continue;
			}else{
				audCid+=companyId[i].companyId+",";
				nameStr+=companyId[i].companyName+",";
			}
		}
		if(audCid.length>0){
			nameStr=nameStr.substring(0, nameStr.length-1);
			BJUI.alertmsg('confirm', '你确定要审核企业'+nameStr+'吗？', {
				okCall: function() {
					$.ajax({
						type:"post",
						url:"company/updateAuditCompany.do?"+new Date(),
						data:{"audCid" : audCid},
						dataType:"text",
						success:function(data){
							if(data==0){
								BJUI.alertmsg('error', '审核失败');
							}else{
								$.CurrentNavtab.find('#companyInfo').datagrid('refresh', true);
								BJUI.alertmsg('ok', '成功审核企业'+nameStr+"!", {
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

//查看
function getOnecompanyDetail(e){
	BJUI.dialog({
        id:'companyDetail',
        target:$.CurrentNavtab.find("#companyDetail"),
        title:'企业详细信息',
        width:910,
        height:560,
        mask:true,
        resizable:false,
        onClose:function(){
	    	$.CurrentNavtab.find('#companyInfo').datagrid('selectedRows',false);
	    }
	});
	$.ajax({
		type:"post",
		url:"company/getOneCompanyById.do?"+new Date(),
		data:{"companyId":e},
		dataType:"json",
		success:function(data){
			$.CurrentDialog.find("#c_Name").text(data.companyName);
			$.CurrentDialog.find("#ca_Name").text(data.area.areaName);
			$.CurrentDialog.find("#c_companyMan").text(data.companyMan);
			$.CurrentDialog.find("#c_companyTel").text(data.companyTel);
			$.CurrentDialog.find("#c_registerOffice").text(data.registerOffice);
			$.CurrentDialog.find("#c_natureName").text(data.companyNature.natureName);
			$.CurrentDialog.find("#c_scaleName").text(data.companyScale.scaleName);
			$.CurrentDialog.find("#c_tradeName").text(data.companyTrade.tradeName);
			$.CurrentDialog.find("#c_companySite").text(data.companySite);
			$.CurrentDialog.find("#c_creditCode").text(data.creditCode);
			$.CurrentDialog.find("#c_registerOffice").text(data.registerOffice);
			$.CurrentDialog.find("#c_companyIntro").text(data.companyIntro);
			$.CurrentDialog.find("#c_editComName").text(data.editComName);
			$.CurrentDialog.find("#c_editComDate").text(convertDate(data.editComDate));
			if(data.companyAudit==0){
				$.CurrentDialog.find("#c_companyAudit").html("<span style='color:green'>已审核</span>");
			}else{
				$.CurrentDialog.find("#c_companyAudit").html("<span style='color:red'>审核中</span>");
			}
			if(data.companyPic!=""){
				var img = data.companyPic.split(",");
				var str = "<img alt='' src='"+img[0]+"' style='max-width: 125px;max-height: 85px;'>";
				if(img.length>1){
					var url=img[0].substring(0,img[0].lastIndexOf("/")+1);
					for(var i=1;i<img.length;i++){
						img[i]=url+img[i];
						str += "&ensp;<img alt='' src='"+img[i]+"' style='max-width: 125px;max-height: 85px;'>";
					}
				}
				$.CurrentDialog.find('#td_com_pic').html(str);
			}else{
				$.CurrentDialog.find('#td_com_pic').html("<center>暂无图片信息</center>");
			}
		}
	});
}

//获取所有企业性质
function getCompanyNature(){
	BJUI.ajax('doajax', {
	    url: 'company/getAllCompanyNature.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var natureStr="";
	    	for(var i=0;i<data.length;i++){
	    		natureStr+="<option value='" + data[i].natureId + "'>" + data[i].natureName + "</option>";
	    	}
	    	$.CurrentDialog.find("#natureId").html(natureStr);
	    	$.CurrentDialog.find('#natureId').selectpicker('refresh');
	    }
	 });
}

//获取所有企业规模
function getAllCompanyScale(){
	BJUI.ajax('doajax', {
	    url: 'company/getAllCompanyScale.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var scaleStr="";
	    	for(var i=0;i<data.length;i++){
	    		scaleStr+="<option value='" + data[i].scaleId + "'>" + data[i].scaleName + "</option>";
	    	}
	    	$.CurrentDialog.find("#scaleId").html(scaleStr);
	    	$.CurrentDialog.find('#scaleId').selectpicker('refresh');
	    }
	 });
}

//获取企业行业
function getAllCompanyTrade(){
	BJUI.ajax('doajax', {
	    url: 'company/getAllCompanyTrade.do',
	    type:'GET',
	    okalert:false,
	    async:false,
	    okCallback: function(json, options) {
	    	var data = eval(json);
	    	var tradeStr="";
	    	for(var i=0;i<data.length;i++){
	    		tradeStr+="<option value='" + data[i].tradeId + "'>" + data[i].tradeName + "</option>";
	    	}
	    	$.CurrentDialog.find("#tradeId").html(tradeStr);
	    	$.CurrentDialog.find('#tradeId').selectpicker('refresh');
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
function Company_NodeCheck(e, treeId, treeNode) {
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
    	$("#c_areaId").val(areaId);
    };
}

//刷新
function refreshCompany(){
	$.CurrentNavtab.find('#companyInfo').datagrid('refresh', true);
	$.CurrentNavtab.find('#companyInfo').datagrid('selectedRows',false);
}