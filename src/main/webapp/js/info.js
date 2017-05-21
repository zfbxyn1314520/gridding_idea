/**
 * author lnc
 * belong to 信息管理
 */

var editor;
var infoData;
$(function(){
	
    editor = new wangEditor('infoContent');
    // 阻止输出log
    wangEditor.config.printLog = false;
    // 上传图片
    editor.config.uploadImgUrl = 'info/uploadFile.do';
    // 配置自定义参数
    editor.config.uploadParams = { mainName: 'info' };
    editor.config.uploadImgFileName = 'infoPic'; 
    // 设置 headers
    editor.config.uploadHeaders = {  'Accept' : 'text/x-json' }; 
    
//  editor.$editorContainer.css('z-index', 20);
    editor.create();
  
    $.CurrentNavtab.find("#info_areaName").attr("novalidate", "novalidate");
	$.CurrentNavtab.find("#infoEditor").val(sessionStorage.userName);
	getAllInfoType();
	getAllInfoByAreaId(sessionStorage.userAreaId);
	//预览与字数统计
    editor.onchange = function () {
        $("#info_content").html(this.$txt.html());
        $("#count").text(this.$txt.text().length);
    };
	//效果预览
    $("#info_content").html(editor.$txt.html());
    //字数统计
    $("#count").text(editor.$txt.text().length);
	// 清空内容。
	$('#clearBtn').click(function () {
        editor.$txt.html('<p><br></p>');
        return false;
    });
	// 导出内容。
	$("#exportBtn").click(function () {
    	if(editor.$txt.html()=='<p><br></p>'){
    		BJUI.alertmsg('info', '文档内容不能为空！');
    	}else{
    		// 生成Word文档HTML
            var html = "<html><head><xml><w:WordDocument><w:View>Print</w:View></xml><meta charset='utf-8' /></head><body>" + editor.$txt.html() + "</body></html>";
            // 生成一个包含文档的Blob对象
            var blob = new Blob([html], { type: "application/msword" });
            // 生成用于下载的链接
            var a = document.createElement("a");
            // 为链接指定Blob地址
            a.href = URL.createObjectURL(blob);
            // 指定文件名，只有Chrome和FireFox支持，Edge不支持该文件名
            a.download =$.CurrentNavtab.find('#info_areaName').val()+"("+$.CurrentNavtab.find("#infoTypeId option:selected").text()+")"+".doc";
            // 对于FireFox，要把链接放到body中，否则不能下载，Chrome和Edge不需要这么做
            document.body.appendChild(a);
            // 触发点击事件，进行下载
            a.click();
    	}
    	return false;
    });
	
	// 销毁编辑器
//	$('#infoBtnlll').click(function () {
//	    editor.destroy();
//	});
	
	//信息类别选中之后动态生成相应内容
	$.CurrentNavtab.find('#infoTypeId').change(function(){
		var infoTypeId=$.CurrentNavtab.find('#infoTypeId').selectpicker('val');
		var areaId=$.CurrentNavtab.find('#info_areaId').val();
		for(var i=0;i<infoData.length;i++ && infoData[i].infoTypeId==infoTypeId && infoData[i].area.areaId==areaId){
			if(infoData[i].infoTypeId==infoTypeId){
				$.CurrentNavtab.find('#infoId').val(infoData[i].infoId);
				$.CurrentNavtab.find('#infoEditor').val(infoData[i].infoEditor);
				$.CurrentNavtab.find('#infoEditDate').val(convertDate(infoData[i].infoEditDate));
				$.CurrentNavtab.find('#infoTitle').val(infoData[i].infoTitle);
				$.CurrentNavtab.find('#infoContent').text(editor.$txt.html(infoData[i].infoContent));
				$.CurrentNavtab.find("#infoBtn > span").text("修改");
				$("#info_content").html(editor.$txt.html());
			    $("#count").text(editor.$txt.text().length);
			    break;
			}else{
				$.CurrentNavtab.find('#infoEditDate').val("");
				$.CurrentNavtab.find('#infoTitle').val("");
				$.CurrentNavtab.find("#infoEditor").val(sessionStorage.userName);
				editor.$txt.html('<p><br></p>');
				$.CurrentNavtab.find("#infoBtn > span").text("添加");
			}
		}
	});
	
	$.CurrentNavtab.find("#infoBtn").click(function() {
		$.CurrentNavtab.find("#info_areaName").removeAttr("novalidate");
		// 验证表单数据是否合法
		if ($.CurrentNavtab.find("#infoEdit").isValid()) {
			if ($.CurrentNavtab.find("#infoBtn > span").text().trim()=="添加") {
				$.ajax({
					type : "post",
					url : "info/addNewInfo.do?"+new Date(),
					data : $.CurrentNavtab.find("#infoEdit").serialize(),
					dataType : "text",
					cache : false,
					success : function(data) {
						if (data == "0") {
							BJUI.alertmsg('error', '添加失败！');
						} else {
							BJUI.alertmsg('ok', "成功添加<span style='color:green'>"+data+"<span>的信息！", {
								 displayPosition:'middlecenter'
							});
						}
					}
				});
			} else {
				$.ajax({
					type : "post",
					url : "info/alterInfo.do?"+new Date(),
					data : $.CurrentNavtab.find("#infoEdit").serialize(),
					dataType : "text",
					cache : false,
					success : function(data) {
						if (data == "0") {
							BJUI.alertmsg('error', '修改失败！');
						} else {
							BJUI.alertmsg('ok', "成功修改<span style='color:green'>"+data+"<span>的信息！", {
								 displayPosition:'middlecenter'
							});
						}
					}
				});
			}
		} else {
			BJUI.alertmsg('warn', '验证未通过！',{
 				okCall:function(){
 					$('#infoEdit').validator('cleanUp');
 				}
 			});
		}
	});
});

/**
 * 获取用户所在区域的所有信息
 */
function getAllInfoByAreaId(e){
	$.ajax({
		type : "get",
		url : "info/getAllInfoByAreaId.do?"+new Date(),
		data : {"areaId":e},
		dataType : "json",
		cache : false,
		success : function(data) {
			infoData=data;
			var zTree = $.fn.zTree.getZTreeObj("areaNameList");
			var node = zTree.getNodeByParam("areaId",e);
			node.checked = true; 
			zTree.updateNode(node);
			$.CurrentNavtab.find('#info_areaName').val(node.name);
			$.CurrentNavtab.find('#info_areaId').val(e);
			if (data.length > 0) {
				$.CurrentNavtab.find('#infoId').val(data[0].infoId);
				$.CurrentNavtab.find('#infoTypeId').selectpicker('val',data[0].infoTypeId);
				$.CurrentNavtab.find('#infoEditor').val(data[0].infoEditor);
				$.CurrentNavtab.find('#infoEditDate').val(convertDate(data[0].infoEditDate));
				$.CurrentNavtab.find('#infoTitle').val(data[0].infoTitle);
				$.CurrentNavtab.find('#infoContent').text(editor.$txt.html(data[0].infoContent));
				$.CurrentNavtab.find("#infoBtn > span").text("修改");
				$("#info_content").html(editor.$txt.html());
			    $("#count").text(editor.$txt.text().length);
			}else{
				$.CurrentNavtab.find('#infoEditDate').val("");
				$.CurrentNavtab.find('#infoTitle').val("");
				$.CurrentNavtab.find("#infoEditor").val(sessionStorage.userName);
				editor.$txt.html('<p><br></p>');
				$.CurrentNavtab.find("#infoBtn > span").text("添加");
			}
		}
	});
}


/**
 * 获取所有信息类别
 */
function getAllInfoType(){
	$.ajax({
		type:"get",
		url:"info/getAllInfoType.do?"+new Date(),
		dataType:"json",
		success:function(data){
			var options="";
	    	for(var i=0;i<data.length;i++){
	    		options+="<option value='" + data[i].infoTypeId + "'>" + data[i].infoTypeName + "</option>";
	    	}
			$.CurrentNavtab.find('#infoTypeId').html(options);
			$.CurrentNavtab.find('#infoTypeId').selectpicker('refresh');
		}
	});
}

function preview() {
	BJUI.dialog({
		id:'infoView',
	    target:$.CurrentNavtab.find("#infoView"),
	    title:'信息预览',
	    width:800,
	    height:500,
	    mask:true,
	    resizable:false,
	    onLoad:function(){
	    	$("#main_head h4").text($.CurrentNavtab.find("#infoTitle").val());
	    	$("#sub_head_areaName").text($.CurrentNavtab.find("#info_areaName").val());
	    	$("#sub_head_type").text($.CurrentNavtab.find("#infoTypeId option:selected").text());
	    	$("#sub_head_editor").text($.CurrentNavtab.find("#infoEditor").val());
	    	$("#sub_head_date").text($.CurrentNavtab.find("#infoEditDate").val());
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


/**
 * ztree下拉选择
 * @param e
 * @param treeId
 * @param treeNode
 */
//选择事件
function Info_NodeCheck(e, treeId, treeNode) {
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
    	$("#info_areaId").val(areaId);
    };
	getAllInfoByAreaId($.CurrentNavtab.find("#info_areaId").val());
}
