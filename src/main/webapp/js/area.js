/**
 * author 李宁财
 * content 区域管理javaScript
 */
$(function () {
    //获取导航菜单权限功能
    $.ajax({
        type: 'get',
        url: 'menu/getMenuPerById.do?' + new Date(),
        data: {"menuName": $(".tabsMoreList li[class=active]").text()},
        dataType: 'json',
        cache: false,
        success: function (data) {

            var width = Math.round($(window).width() * 0.5);
            var pers = data[0].permissions;
            var per = [];
            for (var i = 0; i < pers.length; i++) {
                per[i] = pers[i].perName;
            }
            var toolBarItem = "";
            var toolBar = true;
            for (var z = 0; z < per.length; z++) {
                if (per[z] == "添加") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addAreaInfo();'>添加</button>";
                }
                if (per[z] == "编辑") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editAreaInfo();'>编辑</button>";
                }
                if (per[z] == "删除") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteAreaInfo();'>删除</button>";
                }
            }

            //判断用户是否有增加等看权限，如果没有则隐藏工具条
            if (toolBarItem == "") {
                toolBar = false;
            }
            toolBarItem += "<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='importInfo();'>导入</button>";
            toolBarItem += "<button type='button' class='btn-default' data-icon='refresh' onclick='refreshArea();'>刷新</button>";

            //生成datagrid表格
            $.CurrentNavtab.find('#areaInfo').datagrid({
                height: '100%',
                tableWidth: '100%',
                gridTitle: "<i class='" + data[1].menuIcon + " sidebar-nav-link-logo'></i>" + data[1].menuName + "" +
                "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
                "<i class='" + data[0].menuIcon + " sidebar-nav-link-logo'></i>" + data[0].menuName + "",
                showToolbar: toolBar,
                toolbarCustom: toolBarItem,
                local: 'remote',
                dataUrl: 'area/getAllAreaByAreaId.do',
                columns: [
                    {name: 'areaCode', label: '行政区划代码', align: 'center', width: width * 0.3},
                    {name: 'areaName', label: '行政区划名称', align: 'center', width: width * 0.4},
                    {name: 'shortName', label: '行政区划简拼', align: 'center', width: width * 0.15},
                    {
                        name: 'areaLevel', label: '行政区划级别', align: 'center', width: width * 0.15,
                        render: function (value, data) {
                            switch (value) {
                                case 0:
                                    return "最高级别";
                                    break;
                                case 1:
                                    return "省级级别";
                                    break;
                                case 2:
                                    return "市级级别";
                                    break;
                                case 3:
                                    return "区县级别";
                                    break;
                                case 4:
                                    return "镇街级别";
                                    break;
                                case 5:
                                    return "村社级别";
                                    break;
                            }
                        }
                    }
                ],
                paging: {pageSize: 60, selectPageSize: '30,90,120', pageCurrent: 1, showPagenum: 5, totalRow: 0},
                linenumberAll: true,
                showLinenumber: true,
                showCheckboxcol: 'lock',
                hScrollbar: false,
                columnMenu: false,
                filterThead: false,
                isTree: 'areaName',
                treeOptions: {
                    keys: {
                        key: 'areaCode',
                        parentKey: 'areaParentCode',
                        level: 'areaLevel',
                        isExpand: 'isExpand'
                    },
                    simpleData: true,
                    add: false,
                }

            });
        }
    });
});

/**
 * 增加区域信息方法
 */
function addAreaInfo() {
    BJUI.dialog({
        id: 'addArea',
        target: $.CurrentNavtab.find("#addArea"),
        title: '添加行政区划',
        width: 475,
        height: 340,
        mask: true,
        resizable: false,
        onClose: function () {
            $.CurrentNavtab.find('#areaInfo').datagrid('selectedRows', false);
        }
    });
    $.CurrentDialog.find("#areaParentCode").attr("novalidate", "novalidate");
    saveAreaInfo();
}

/**
 * 添加和编辑时保存用户信息方法
 */
function saveAreaInfo() {

    $.CurrentDialog.find("#areaBtn").click(function () {
        if ($.CurrentDialog.find("#areaParentCode").val() == null || $.CurrentDialog.find("#areaParentCode").val() == '') {
            $.CurrentDialog.find("#areaParentCode").removeAttr("novalidate");
        }
        var nameStr = "";
// 		// 验证表单数据是否合法
        if ($.CurrentDialog.find("#addAreaInfo").isValid()) {
            if ($.CurrentDialog.find("#areaBtn > span").text().trim() == "提交") {
                nameStr += $.CurrentDialog.find('#areaName').val();
                $.ajax({
                    type: "get",
                    url: "area/addNewArea.do",
                    data: $.CurrentDialog.find("#addAreaInfo").serialize(),
                    dataType: "json",
                    cache: false,
                    success: function (data) {
                        if (data == "1") {
                            BJUI.dialog('closeCurrent');
                            $.CurrentNavtab.find('#menuInfo').datagrid('refresh', true);
                            BJUI.alertmsg('ok', "成功添加区域<span style='color:green'>" + nameStr + "</span>！", {
                                displayPosition: 'middlecenter'
                            });
                        } else {
                            BJUI.alertmsg('error', '添加失败！');
                        }
                    }
                });
            } else {
// 				nameStr+=$.CurrentDialog.find('#menuName').val();
// 				$.ajax({
// 					type : "post",
// 					url : "menu/alterMenuInfo.do",
// 					data : $.CurrentDialog.find("#addMenuInfo").serialize(),
// 					dataType : "text",
// 					cache : false,
// 					success : function(data) {
// 						if (data == "1") {
// 							BJUI.dialog('closeCurrent');
// 							$.CurrentNavtab.find('#menuInfo').datagrid('refresh', false);
// 							BJUI.alertmsg('ok', "成功修改菜单<span style='color:green'>"+nameStr+"</span>！", {
// 								 displayPosition:'middlecenter'
// 							});
// 						} else {
// 							BJUI.alertmsg('error', '修改失败！');
// 						}
// 					}
// 				});
            }
        } else {
            BJUI.alertmsg('warn', '验证未通过！', {
                okCall: function () {
                    $('#addAreaInfo').validator('cleanUp');
                }
            });
        }
    });
}

function getAllAreaName(obj) {
    BJUI.findgrid({
        empty: false,
        include: 'areaName:iconName',
        dialogOptions: {id: 'areaCodeData', title: '行政区划代码库'},
        gridOptions: {
            local: 'local',
            loadingmask: false,
//	    	    showCheckboxcol:true,
            dataUrl: 'area/getAllAreaName.do',
            columns: [
                {name: 'areaCodeNum', label: '区域代码', align: 'center', width: 100},
                {name: 'areaCodeName', label: '区域名称', align: 'center', width: 100}
            ],
            paging: {pageSize: 30, selectPageSize: '60,90', pageCurrent: 1, showPagenum: 5, totalRow: 0},
        },
        afterSelect: function (data) {
            $("#menuIcon").attr("novalidate", "novalidate");
        }
    });
}

function importInfo() {
    $.ajax({
        type: "get",
        url: "area/importInfo.do?" + new Date(),
        dataType: "json",
        cache: false,
        async: false,
        success: function (data) {

        }
    });
}
/**
 * 区域下拉框ztree树
 * @returns {Array}
 */
function getAllAreaMenu() {
    var areaMenu = new Array();
    $.ajax({
        type: "get",
        url: "area/getAllAreaMenu.do?" + new Date(),
        dataType: "json",
        cache: false,
        async: false,
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                areaMenu[i] = new area(data[i].areaCode, data[i].areaParentCode, data[i].areaName, data[i].areaId, data[i].areaLevel);
            }
        }
    });
    return areaMenu;
}

/**
 * ztree下拉选择
 * @param e
 * @param treeId
 * @param treeNode
 */
//选择事件
function Area_NodeCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getCheckedNodes(true);
    var ids = '', names = '';
    var areaId = '', areaLevel = '';

    for (var i = 0; i < nodes.length; i++) {
        ids += ',' + nodes[i].id;
        names += ',' + nodes[i].name;
        areaId += ',' + nodes[i].areaId;
        areaLevel += ',' + nodes[i].areaLevel;
    }
    if (ids.length > 0) {
        ids = ids.substr(1), names = names.substr(1);
        areaId = areaId.substr(1);
        areaLevel = areaLevel.substr(1);
    }
    var $from = $('#' + treeId).data('fromObj');
    if ($from && $from.length) {
        $from.val(ids);
    }
    $.CurrentDialog.find('#areaLevel').val(parseInt(areaLevel) + 1);
}


//声明区域对象
function area(id, pId, name, areaId, areaLevel) {
    this.id = id;
    this.pId = pId;
    this.name = name;
    this.areaId = areaId;
    this.areaLevel = areaLevel;
}
//刷新页面
function refreshArea(){
    $.CurrentNavtab.find('#areaInfo').datagrid('refresh', false);
    $.CurrentNavtab.find('#areaInfo').datagrid('selectedRows',false);
}
