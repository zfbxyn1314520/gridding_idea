/**
 * log.html页面的外部js代码
 * author 刘春晓
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
	    	var toolBar=true;
	    	for(var z=0;z<per.length;z++){
	    		if(per[z]=="删除"){
	    			toolBarItem+="<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteLogInfo();'>删除</button>";
	    		}
	    	}
	    	
	    	//判断用户是否有增加等看权限，如果没有则隐藏工具条
	    	if(toolBarItem==""){
	    		toolBar=false;
	    	}
	    	
	    	toolBarItem+="<button type='button' class='btn-default' data-icon='refresh' onclick='refreshLog();'>刷新</button>";
	    	
	    	//生成datagrid表格
	    	$('#logInfo').datagrid({
	    		height: '100%',
	    	    tableWidth:'100%',
	    	    gridTitle: "<i class='"+data[1].menuIcon+" sidebar-nav-link-logo'></i>"+data[1].menuName+"" +
	    	    		   "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
	    	    		   "<i class='"+data[0].menuIcon+" sidebar-nav-link-logo'></i>"+data[0].menuName+"",
	    	    showToolbar: toolBar,
	    	    toolbarCustom: toolBarItem,
	    	    local: 'remote',
	    	    dataUrl: 'log/getAllLogByUserId.do',
	    	    columns: [
	    	        {name:'user',label:'用户',align:'center',width:width*0.1,
	    	        	render:function(value,data){
	    	        		if(value != "" && value != null){
	    	        			return value.userName;
	    	        		}
	    	        	}
	    	        },
	    	        {name:'user',label:'角色',align:'center',width:width*0.1,
	    	        	render:function(value,data){
	    	        		if(value != "" && value != null){
	    	        			return value.role.roleName;
	    	        		}
	    	        	}
	    	        },
	    	        {name:'user',label:'所属区域',align:'center',width:width*0.2,
	    	        	render:function(value,data){
	    	        		if(value != "" && value != null){
	    	        			return value.area.areaName;
	    	        		}
	    	        	}
	    	        },
	    	        {name:'logMsg',label:'日志信息',align:'center',width:width*0.3},
	    	        {name:'logDate',label:'时间',align:'center',width:width*0.12},
	    	        {name:'logLevel',label:'日志等级',align:'center',width:width*0.08},
	    	        {name:'logIP',label:'Ip地址',align:'center',width:width*0.1}
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

//删除日志
function deleteLogInfo(){
	var logId=$.CurrentNavtab.find("#logInfo").data('selectedDatas');
	var deLid="";
	if(typeof(logId)=="undefined"||logId.length==0){
		BJUI.alertmsg('info', '请选择需要删除的日志');
	}else{
		for(var i=0;i<logId.length;i++){
			deLid+=logId[i].logId+",";
		}
		BJUI.alertmsg('confirm', '你确定要删除id为'+deLid+'的日志吗？', {
			okCall: function() {
				$.ajax({
					type:"post",
					url:"log/deleteLog.do?"+new Date(),
					data:{"deLid" : deLid},
					dataType:"text",
					success:function(data){
						if(data==0){
							   BJUI.alertmsg('error', '删除失败');
						}else{
							$.CurrentNavtab.find('#logInfo').datagrid('refresh', true);
							BJUI.alertmsg('ok', '成功删除日志'+deLid+"!", {
								displayPosition:'middlecenter'
							});
						}
					}
				});
			}
		});
	}
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


function refreshLog(){
	$.CurrentNavtab.find('#logInfo').datagrid('refresh', false);
}