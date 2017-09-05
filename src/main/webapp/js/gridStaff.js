/**
 * author 刘春晓
 * content grid.html外部引用js
 */

var auditGS = false;
var width = Math.round($(window).width() * 0.5);

//获取所有areaId下的居委会
function showGridMenu() {
    var areaMenu = new Array();
    $.ajax({
        type: "get",
        url: "gridStaff/getAreaMenuById.do?" + new Date(),
        dataType: "json",
        cache: false,
        async: false,
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                areaMenu[i] = new area(data[i].areaCode, data[i].areaParentCode, data[i].areaName, data[i].areaId);
            }
        }
    });
    return areaMenu;
}

var toolBarItem = "";
var gridTitle = "";
var status = true;
var toolBar = true;
//显示所有该用户区域下的网格员
$(function () {
    //获取导航菜单权限功能
    $.ajax({
        type: 'get',
        url: 'menu/getMenuPerById.do?' + new Date(),
        data: {"menuName": $(".tabsMoreList li[class=active]").text()},
        dataType: 'json',
        cache: false,
        success: function (data) {

            var width = Math.round($(window).width() * 0.4);
            var pers = data[0].permissions;
            var per = [];
            for (var i = 0; i < pers.length; i++) {
                per[i] = pers[i].perName;
            }
            for (var z = 0; z < per.length; z++) {
                if (per[z] == "添加") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addgridStaffInfo();'>添加</button>";
                }
                if (per[z] == "编辑") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editgridStaffInfo();'>编辑</button>";
                }
                if (per[z] == "删除") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deletegridStaffInfo();'>删除</button>";
                }
                if (per[z] == "审核") {
                    toolBarItem += "<button type='button' class='btn-orange' data-icon='check' onclick='auditgridStaffInfo();'>审核</button>";
                    auditGS = true;
                }
                if (per[z] == "查看") {
                    status = false;
                }
            }
            //判断用户是否有增加等看权限，如果没有则隐藏工具条
            if (toolBarItem == "") {
                toolBar = false;
            }
            toolBarItem += "<button type='button' class='btn-default' data-icon='refresh' onclick='refreshGridStaff();'>刷新</button>";
            gridTitle += "<i class='" + data[1].menuIcon + " sidebar-nav-link-logo'></i>" + data[1].menuName + "" +
                "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
                "<i class='" + data[0].menuIcon + " sidebar-nav-link-logo'></i>" + data[0].menuName + "";
            $.CurrentNavtab.find('#gridStaffInfo').datagrid({
                height: '100%',
                tableWidth: '100%',
                gridTitle: gridTitle,
                showToolbar: toolBar,
                toolbarCustom: toolBarItem,
                postData: {"areaId": sessionStorage.userAreaId},
                local: 'remote',
                dataUrl: 'gridStaff/getAllGridStaffByAreaId.do',
                columns: [
                    {name: 'gridStaffName', label: '姓名', align: 'center', width: width * 0.1},
                    {
                        name: 'gridPost', label: '职务', align: 'center', width: width * 0.1,
                        render: function (value, data) {
                            return value.gridPostName;
                        }
                    },
                    {name: 'gridStaffSex', label: '性别', align: 'center', width: width * 0.08},
                    {name: 'gridStaffScope', label: '服务范围', align: 'center', width: width * 0.26},
                    {
                        name: 'gridStaffAudit', label: '状态', align: 'center', width: width * 0.1,
                        render: function (value, data) {
                            if (value == 0) {
                                return "<i class='am-icon-check-square-o' style='color:green'></i>";
                            } else {
                                return "<i class='am-icon-minus-square-o' style='color:red'></i>";
                            }
                        }
                    },
                    {
                        name: '', label: '操作', align: 'center', hide: false, width: width * 0.36,
                        render: function (value, data) {
                            var btnIcon = "user-plus";
                            var btnClass = "btn-blue";
                            var btnFont = "添加App账号";
                            if (data.appUser == 1) {
                                btnIcon = "edit";
                                btnClass = "btn-orange";
                                btnFont = "编辑App账号";
                            }
                            var str = "";
                            if (data.gridStaffAudit == 1) {
                                str += "&ensp;<button type='button' class='" + btnClass + "' data-icon='" + btnIcon + "' style='font-size:12px;' disabled='disabled'" +
                                    "onclick='addOneStaffAppStaff(" + JSON.stringify(data) + ");'>" + btnFont + "</button>" +
                                    "&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
                                    "onclick='auditGridStaffSingle(" + data.gridStaffId + ",\"" + data.gridStaffName + "\"," + data.gridStaffAudit + ");'>审核</button>";
                            } else {
                                str += "&ensp;<button type='button' class='" + btnClass + "' data-icon='" + btnIcon + "' style='font-size:12px;' " +
                                    "onclick='addOneStaffAppStaff(" + JSON.stringify(data) + ");'>" + btnFont + "</button>" +
                                    "&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
                            }
                            return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
                                "onclick='getOnegridDetail(" + data.gridStaffId + ");'>详细信息</button>" + str;
                        },
                    },
                ],
                paging: {pageSize: 30, selectPageSize: '10,20,40,50', pageCurrent: 1, showPagenum: 5, totalRow: 0},
                linenumberAll: true,
                showLinenumber: true,
                showCheckboxcol: 'lock',
                hScrollbar: false,
                columnMenu: false,
                filterThead: false
            });
//	    	forGridInfo(sessionStorage.userAreaId, null);
        }
    });
});

function getGridId(e) {
    var areaId = null;
    var gridId = null;
    if (e.id == null) {
        gridId = e.areaId;
    } else {
        areaId = e.areaId;
    }
    forGridInfo(areaId, gridId);

}

//生成数据表格
function forGridInfo(e, f) {
    //生成datagrid表格
    $.CurrentNavtab.find('#gridStaffInfo').datagrid("reload", {
        height: '100%',
        tableWidth: '100',
        gridTitle: gridTitle,
        showToolbar: toolBar,
        toolbarCustom: toolBarItem,
        postData: {"areaId": e, "gridId": f},
        local: 'remote',
        dataUrl: 'gridStaff/getAllGridStaffByAreaId.do',
        columns: [
            {name: 'gridStaffName', label: '姓名', align: 'center', width: width * 0.1},
            {
                name: 'gridPost', label: '职务', align: 'center', width: width * 0.1,
                render: function (value, data) {
                    return value.gridPostName;
                }
            },
            {name: 'gridStaffSex', label: '性别', align: 'center', width: width * 0.08},
            {name: 'gridStaffScope', label: '服务范围', align: 'center', width: width * 0.26},
            {
                name: 'gridStaffAudit', label: '状态', align: 'center', width: width * 0.1,
                render: function (value, data) {
                    if (value == 0) {
                        return "<i class='am-icon-check-square-o' style='color:green'></i>";
                    } else {
                        return "<i class='am-icon-minus-square-o' style='color:red'></i>";
                    }
                }
            },
            {
                name: '', label: '操作', align: 'center', hide: false, width: width * 0.36,
                render: function (value, data) {
                    var btnIcon = "user-plus";
                    var btnClass = "btn-blue";
                    var btnFont = "添加App账号";
                    if (data.appUser == 1) {
                        btnIcon = "edit";
                        btnClass = "btn-orange";
                        btnFont = "编辑App账号";
                    }
                    var str = "";
                    if (data.gridStaffAudit == 1) {
                        str += "&ensp;<button type='button' class='" + btnClass + "' data-icon='" + btnIcon + "' style='font-size:12px;' disabled='disabled'" +
                            "onclick='addOneStaffAppStaff(" + JSON.stringify(data) + ");'>" + btnFont + "</button>" +
                            "&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
                            "onclick='auditGridStaffSingle(" + data.gridStaffId + ",\"" + data.gridStaffName + "\"," + data.gridStaffAudit + ");'>审核</button>";
                    } else {
                        str += "&ensp;<button type='button' class='" + btnClass + "' data-icon='" + btnIcon + "' style='font-size:12px;' " +
                            "onclick='addOneStaffAppStaff(" + JSON.stringify(data) + ");'>" + btnFont + "</button>" +
                            "&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
                    }
                    return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
                        "onclick='getOnegridDetail(" + data.gridStaffId + ");'>详细信息</button>" + str;
                },
            }
        ],
        paging: {pageSize: 30, selectPageSize: '10,20,40,50', pageCurrent: 1, showPagenum: 5, totalRow: 0},
        linenumberAll: true,
        showLinenumber: true,
        showCheckboxcol: 'lock',
        hScrollbar: false,
        columnMenu: false,
        filterThead: false
    });
}

//打开增加页面
function addgridStaffInfo() {
    BJUI.dialog({
        id: 'addGridStaff',
        width: 755,
        height: 400,
        target: $("#addGridStaff"),
        title: '新增网格员',
        mask: true,
        resizable: false,
        onClose: function () {
            $.CurrentNavtab.find('#gridStaffInfo').datagrid('selectedRows', false);
        },
        onLoad: function ($dialog) {
            var that = this;
            var width = that.options.width > that.options.minW ? that.options.width : that.options.minW;
            var wW = $(window).width(), iTop = that.options.max ? 0 : (($(window).height() - 565) / 3);
            if (width > wW) width = wW;
            $("#gridStaffIcon").click(function (event) {
                $dialog.height(565)
                    .css({left: (wW - width) / 2, top: 0, opacity: 0.1})
                    .animate({top: iTop > 0 ? iTop : 0, opacity: 1})
                    .find('> .dialogContent').height(565 - $('> .dialogHeader', $dialog).outerHeight());
                $dialog.find('> .dialogContent').css({height: (565 - $dialog.find('> .dialogHeader').outerHeight())});
                $("#gridStaffUploadArea").removeClass("hide");
                $.CurrentDialog.find("#filePicker div:last").css({
                    "left": "268.5px",
                    "width": "156px",
                    "height": "25px",
                    "bottom": "auto",
                    "right": "auto"
                });
                $.CurrentDialog.find("#filePicker2 div:last").css({
                    "left": "10px",
                    "width": "94px",
                    "height": "27px",
                    "bottom": "auto",
                    "right": "auto"
                });
            });
        }
    });
    getGridPostNameOfGridStaff();
    saveGridStaffInfo();
}


//打开修改页面
function editgridStaffInfo() {
    var gridStaffId = $.CurrentNavtab.find("#gridStaffInfo").data('selectedDatas');
    if (gridStaffId == null || gridStaffId.length != 1) {
        BJUI.alertmsg('info', '请选择一条需要编辑的网格员信息');
    } else {
        BJUI.dialog({
            id: 'editGridStaff',
            target: $.CurrentNavtab.find("#addGridStaff"),
            width: 755,
            height: 565,
            mask: true,
            resizable: false,
            title: '修改网格员信息',
            onClose: function () {
                $.CurrentNavtab.find('#gridStaffInfo').datagrid('selectedRows', false);
            },
            onLoad: function ($dialog) {
                $("#gridStaffUploadArea").removeClass("hide");
                $("#submitBtnst > span").text("修改");
            }
        });
        var upGSid = "";
        for (var i = 0; i < gridStaffId.length; i++) {
            upGSid = gridStaffId[i].gridStaffId;
        }
        $.ajax({
            type: "post",
            url: "gridStaff/getGridStaffById.do?" + new Date(),
            data: {"upGSid": upGSid},
            dataType: "json",
            success: function (data) {
                //计算截至当前时间的年龄
                var str = data.gridStaffCard.substring(6, 10) + "/"
                    + data.gridStaffCard.substring(10, 12) + "/"
                    + data.gridStaffCard.substring(12, 14);
                var age = (new Date() - new Date(str)) / (1000 * 3600 * 365 * 24);
                getAreaByGridIdOfGrid(data.gridId);
                $.CurrentDialog.find("#gridStaffName").attr("value", data.gridStaffName);
                $.CurrentDialog.find("#gridStaffId").attr("value", data.gridStaffId);
                $.CurrentDialog.find("#gridStaff_gridId").selectpicker('val', data.gridId);
                if (data.gridStaffSex == "男") {
                    $.CurrentDialog.find('#gridStaffSex1').iCheck('check');
                } else {
                    $.CurrentDialog.find('#gridStaffSex2').iCheck('check');
                }
                getGridPostNameOfGridStaff();
                $.CurrentDialog.find("#gridPostId").selectpicker('val', data.gridPost.gridPostId);
                $.CurrentDialog.find("#gridStaffAge").val(parseInt(age));
                $.CurrentDialog.find("#gridStaffCard").attr("value", data.gridStaffCard);
                $.CurrentDialog.find("#gridStaffTel").attr("value", data.gridStaffTel);
                $.CurrentDialog.find("#gridStaffScope").text(data.gridStaffScope);
            }
        });
        saveGridStaffInfo();
    }
}

//获取所属区域
function getAreaByGridIdOfGrid(e) {
    $.ajax({
        type: "post",
        url: "grid/getAreaByGridId.do?" + new Date(),
        data: {"gridId": e},
        dataType: "json",
        cache: false,
        async: false,
        success: function (data) {
            var zTree = $.fn.zTree.getZTreeObj("gridStaff_areaNameMenu");
            var node = zTree.getNodeByParam("areaId", data.areaId);
            node.checked = true, zTree.updateNode(node);
            $.CurrentDialog.find('#gridStaff_areaName').val(node.name);
            $.CurrentDialog.find('#gridStaff_areaId').val(data.areaId);
            getAllGridByAreaId(data.areaId);
        }
    });
}

//表单验证
function saveGridStaffInfo() {
    $.CurrentDialog.find("#submitBtnst").click(function () {
        var nameStr = "";
        if ($.CurrentDialog.find("#addGridStaffInfo").isValid()) {
            if ($.CurrentDialog.find("#submitBtnst > span").text().trim() == "提交") {
                nameStr += $.CurrentDialog.find("#gridStaffName").val();
                $.ajax({
                    type: "post",
                    url: "gridStaff/saveGridStaff.do?" + new Date(),
                    data: $.CurrentDialog.find("#addGridStaffInfo").serialize(),
                    dataType: "json",
                    cache: false,
                    success: function (data) {
                        if (data == "1") {
                            BJUI.dialog('closeCurrent');
                            $.CurrentNavtab.find('#gridStaffInfo').datagrid('refresh', true);
                            BJUI.alertmsg('ok', "成功添加网格员<span style='color:green'>" + nameStr + "<span>！", {
                                displayPosition: 'middlecenter'
                            });
                        } else {
                            BJUI.alertmsg('error', '添加失败');
                        }
                    }
                });
            } else {
                nameStr += $.CurrentDialog.find("#gridStaffName").val();
                $.ajax({
                    type: "post",
                    url: "gridStaff/editGridStaffById.do?" + new Date(),
                    data: $.CurrentDialog.find("#addGridStaffInfo").serialize(),
                    dataType: "text",
                    success: function (data) {
                        if (data == 0) {
                            BJUI.alertmsg('error', '修改失败');
                        } else {
                            BJUI.alertmsg('ok', '成功修改网格员' + nameStr + "!", {
                                displayPosition: 'middlecenter'
                            });
                            BJUI.dialog('closeCurrent');
                            $.CurrentNavtab.find('#gridStaffInfo').datagrid('refresh', true);
                        }
                    }
                });
            }
        } else {
            BJUI.alertmsg('warn', '验证未通过！', {
                okCall: function () {
                    $('#addGridStaffInfo').validator('cleanUp');
                }
            });
        }
    });
}

//删除方法
function deletegridStaffInfo() {
    var gridStaffId = $.CurrentNavtab.find("#gridStaffInfo").data('selectedDatas');
    var deGsid = "";
    var nameStr = "";
    if (typeof(gridStaffId) == "undefined" || gridStaffId.length == 0) {
        BJUI.alertmsg('info', '请选择需要删除的网格员');
    } else {
        for (var i = 0; i < gridStaffId.length; i++) {
            deGsid += gridStaffId[i].gridStaffId;
            nameStr += gridStaffId[i].gridStaffName + ",";
        }
        var gridId = getGridIdByGridStaffId(deGsid);
        nameStr = nameStr.substring(0, nameStr.length - 1);
        BJUI.alertmsg('confirm', '你确定要删除网格员' + nameStr + '吗？', {
            okCall: function () {
                BJUI.dialog({
                    id: 'editBlockGridStaff',
                    target: $.CurrentNavtab.find("#editBlockGridStaff"),
                    width: 565,
                    height: 600,
                    mask: true,
                    resizable: false,
                    title: '配置' + nameStr + '管辖楼栋新网格员'
                });
                getBlockByGridStaffId(deGsid);
                getAllGridStaff(gridId, deGsid);
                saveBlockGridStaffInfo(deGsid);
//						$.ajax({
//							type:"post",
//							url:"gridStaff/deleteGridStaff.do",
//							data:{"deGsid" : deGsid},
//							dataType:"text",
//							success:function(data){
//								if(data==0){
//									   BJUI.alertmsg('error', '删除失败');
//								}else{
//									$.CurrentNavtab.find('#gridInfo').datagrid('refresh', true);
//									BJUI.alertmsg('ok', '成功删除网格员'+nameStr+"!", {
//										displayPosition:'middlecenter'
//									});
//								}
//							}
//						});
            }
        });
    }
}

//验证修改楼栋网格员表单
function saveBlockGridStaffInfo(e) {
    $.CurrentDialog.find("#submitBtnbst").click(function () {
        if ($.CurrentDialog.find("#editBlockGridStaffInfo").isValid()) {
            $.ajax({
                type: "post",
                url: "block/editBlockGridStaff.do?" + new Date(),
                data: {
                    "oldId": e,
                    "newBgId": $.CurrentDialog.find("#block_gridStaffId").val(),
                    "newPgId": $.CurrentDialog.find("#pop_gridStaffId").val()
                },
                dataType: "text",
                cache: false,
                success: function (data) {
                    if (data == '删除失败！') {
                        BJUI.alertmsg('error', '' + data + '');
                    } else {
                        $.CurrentNavtab.find('#gridStaffInfo').datagrid('refresh', true);
                        BJUI.alertmsg('ok', '' + data + '', {
                            displayPosition: 'middlecenter'
                        });
                        BJUI.dialog('closeCurrent');
                    }
                }
            });
        } else {
            BJUI.alertmsg('warn', '验证未通过！');
        }
    });
}

//获取该网格员管辖的所有楼栋信息
function getBlockByGridStaffId(e) {
    $.ajax({
        type: "post",
        url: "block/getBlockByGridStaffId.do?" + new Date(),
        data: {"gridStaffId": e},
        dataType: "json",
        success: function (data) {
            var data1 = eval(data);
            var str = "";
            for (var i = 0; i < data1.length; i++) {
                str += data1[i].blockName + ",";
            }
            $.CurrentDialog.find("#block_gridStaff").text(str);
        }
    });
}

//获取删除后的所有网格员
function getAllGridStaff(e, f) {
    BJUI.ajax('doajax', {
        url: 'gridStaff/getAllGridStaff.do',
        type: 'GET',
        data: {"gridId": e},
        okalert: false,
        async: false,
        okCallback: function (json, options) {
            var data = eval(json);
            var bgStr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                if (data[i].gridStaffId != f) {
                    bgStr += "<option value='" + data[i].gridStaffId + "'>" + data[i].gridStaffName + "</option>";
                }
            }
            $.CurrentDialog.find("#block_gridStaffId").html(bgStr);
            $.CurrentDialog.find('#block_gridStaffId').selectpicker('refresh');
            $.CurrentDialog.find("#pop_gridStaffId").html(bgStr);
            $.CurrentDialog.find('#pop_gridStaffId').selectpicker('refresh');
        }
    });
}

//获取所选中行的gridId
function getGridIdByGridStaffId(e) {
    var gridId = "";
    $.ajax({
        type: "post",
        url: "gridStaff/getGridIdByGridStaffId.do?" + new Date(),
        data: {"gridStaffId": e},
        dataType: "json",
        cache: false,
        async: false,
        success: function (data) {
            gridId += data;
        }
    });
    return gridId;
}

//图片审核
function auditGridStaffSingle(e, f, g) {
    if (auditGS == true) {
        if (g == 0) {
            return false;
        } else {
            var nameStr = f;
            var audGsid = e;
            BJUI.alertmsg('confirm', '你确定要审核网格员<span style="color:orange">' + nameStr + '</span>吗？', {
                okCall: function () {
                    $.ajax({
                        type: "post",
                        url: "gridStaff/updateAuditGridStaff.do?" + new Date(),
                        data: {"audGsid": audGsid},
                        dataType: "text",
                        success: function (data) {
                            if (data == 0) {
                                BJUI.alertmsg('error', '审核失败');
                            } else {
                                $.CurrentNavtab.find('#gridStaffInfo').datagrid('refresh', true);
                            }
                        }
                    });
                }
            });
        }
    }
}

//审核
function auditgridStaffInfo() {
    var gridStaffId = $.CurrentNavtab.find("#gridStaffInfo").data('selectedDatas');
    var audGsid = "";
    var nameStr = "";
    if (typeof(gridStaffId) == "undefined" || gridStaffId.length == 0) {
        BJUI.alertmsg('info', '请选择需要审核的网格员');
    } else {
        for (var i = 0; i < gridStaffId.length; i++) {
            if (gridStaffId[i].gridStaffAudit == 0) {
                continue;
            } else {
                audGsid += gridStaffId[i].gridStaffId + ",";
                nameStr += gridStaffId[i].gridStaffName + ",";
            }
        }
        if (audGsid.length > 0) {
            nameStr = nameStr.substring(0, nameStr.length - 1);
            BJUI.alertmsg('confirm', '你确定要审核网格员' + nameStr + '吗？', {
                okCall: function () {
                    $.ajax({
                        type: "post",
                        url: "gridStaff/updateAuditGridStaff.do?" + new Date(),
                        data: {"audGsid": audGsid},
                        dataType: "text",
                        success: function (data) {
                            if (data == 0) {
                                BJUI.alertmsg('error', '审核失败');
                            } else {
                                $.CurrentNavtab.find('#gridStaffInfo').datagrid('refresh', true);
                                BJUI.alertmsg('ok', '成功审核网格员' + nameStr + "!", {
                                    displayPosition: 'middlecenter'
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
function getOnegridDetail(e) {
    BJUI.dialog({
        id: 'staffDetail',
        target: $.CurrentNavtab.find("#staffDetail"),
        title: '查看网格员',
        width: 700,
        height: 450,
        mask: true,
        resizable: false,
        onClose: function () {
            $.CurrentNavtab.find('#gridStaffInfo').datagrid('selectedRows', false);
        }
    });
    $.ajax({
        type: "post",
        url: "gridStaff/getOneGridStaffById.do?" + new Date(),
        data: {"gridStaffId": e},
        dataType: "json",
        success: function (data) {
            var str = data.gridStaffCard.substring(6, 10) + "/"
                + data.gridStaffCard.substring(10, 12) + "/"
                + data.gridStaffCard.substring(12, 14);
            var age = (new Date() - new Date(str)) / (1000 * 3600 * 365 * 24);
            $.CurrentDialog.find("#gs_gridStaffName").text(data.gridStaffName);
            $.CurrentDialog.find("#gs_gridStaffSex").text(data.gridStaffSex);
            $.CurrentDialog.find("#gs_gridPostName").text(data.gridPost.gridPostName);
            $.CurrentDialog.find("#gs_gridStaffAge").text(parseInt(age));
            $.CurrentDialog.find("#gs_gridStaffCard").text(data.gridStaffCard);
            $.CurrentDialog.find("#gs_gridStaffTel").text(data.gridStaffTel);
            $.CurrentDialog.find("#gs_gridStaffScope").text(data.gridStaffScope);
            $.CurrentDialog.find("#gs_gridName").text(data.grid.gridName);
            $.CurrentDialog.find("#gs_editStaffName").text(data.editStaffName);
            $.CurrentDialog.find("#gs_editStaffDate").text(convertDate(data.editStaffDate));
            if (data.gridStaffAudit == 0) {
                $.CurrentDialog.find("#gs_gridStaffAudit").html("<span style='color:green'>已审核</span>");
            } else {
                $.CurrentDialog.find("#gs_gridStaffAudit").html("<span style='color:red'>审核中</span>");
            }
            if (data.gridStaffIcon != null) {
                var img = data.gridStaffIcon.split(",");
                var str = "<img alt='' src='" + img[0] + "' style='max-width: 125px;max-height: 85px;'>";
                if (img.length > 1) {
                    var url = img[0].substring(0, img[0].lastIndexOf("/") + 1);
                    for (var i = 1; i < img.length; i++) {
                        img[i] = url + img[i];
                        str += "&ensp;<img alt='' src='" + img[i] + "' style='max-width: 125px;max-height: 85px;'>";
                    }
                }
                $.CurrentDialog.find('#td_staff_pic').html(str);
            } else {
                $.CurrentDialog.find('#td_staff_pic').html("<center>暂无图片信息</center>");
            }
        }
    });
}

//分配app账户
function addOneStaffAppStaff(e) {
    var title = "分配";
    var btnFont = "提交";
    var userId = null;
    var gridStaffId = e.gridStaffId;
    var staffName = e.gridStaffName;
    var userName = e.gridStaffTel;
    var realName = e.gridStaffName;
    var password = e.gridStaffCard;
    password = password.substring(password.length - 6);
    var areaName = e.grid.area.areaName;
    var areaId = e.grid.area.areaId;
    var roleId = null;
    var mobileTel = e.gridStaffTel;
    var editUserDate = convertDate((new Date()).valueOf());
    var gridPostId = e.gridPostId;
    var gridPostName = e.gridPostName;

	if(gridPostName=="网格员"){
	}
	// var count=$("#app_roleId").options.length;
	// console.log(count)
	// for(var i=0;i<count;i++){
	// 	if($("#app_roleId").get(0).options[i].text == "网格员"){
	// 		$("#app_roleId").get(0).options[i].selected = true;
	// 		break;
	// 	}
	// }

    if (e.appUser == 1) {
        title = "修改";
        btnFont = "修改";
        $.ajax({
            type: "post",
            url: "user/getAppUserInfo.do?" + (new Date()).getTime(),
            data: {"gridStaffId": gridStaffId},
            dataType: "json",
            cache: false,
            success: function (data) {
                userId = data.userId;
                userName = data.userName;
                realName = data.realName;
                password = data.password;
                areaName = data.area.areaName;
                areaId = data.area.areaId;
                roleId = data.roleId;
                mobileTel = data.mobileTel;
                editUserDate = convertDate(data.editUserDate);
            }
        });
    }
    BJUI.dialog({
        id: 'addStaffUser',
        url: 'pop/edit_user_dialog.html',
        title: title + staffName + 'App账户',
        width: 755,
        height: 400,
        mask: true,
        resizable: false,
        onClose: function () {
            $.CurrentNavtab.find('#gridStaffInfo').datagrid('selectedRows', false);
        },
        onLoad: function ($dialog) {
            getAllRolesOfApp();
            var zTree = $.fn.zTree.getZTreeObj("app_areaNameMenu");
            var node = zTree.getNodeByParam("areaId", areaId);
            node.checked = true, zTree.updateNode(node);
            $.CurrentDialog.find("#appUserBtn > span").text(btnFont);
            $.CurrentDialog.find("#app_userName").val(userName);
            $.CurrentDialog.find("#app_realName").val(realName);
            $.CurrentDialog.find("#app_userId").val(userId);
            $.CurrentDialog.find("#app_gridStaffId").val(gridStaffId);
            $.CurrentDialog.find("#app_password").val(password);
            $.CurrentDialog.find("#app_confirmPwd").val(password);
            $.CurrentDialog.find("#app_areaName").val(areaName);
            $.CurrentDialog.find("#app_areaId").val(areaId);
            $.CurrentDialog.find("#app_roleId").selectpicker('val', roleId);
            $.CurrentDialog.find("#app_mobileTel").val(mobileTel);
            $.CurrentDialog.find("#app_editUserDate").val(editUserDate);
            $.CurrentDialog.find("#app_areaName").attr("novalidate", "novalidate");

            if (e.appUser == 1) {
                $.CurrentDialog.find("#app_password").attr({"readonly": "readonly", "placeholder": "双击可修改原密码"});
                $.CurrentDialog.find("#app_confirmPwd").attr({"readonly": "readonly", "placeholder": "可选填"});
                $.CurrentDialog.find("#app_password").attr("novalidate", "novalidate");
                $.CurrentDialog.find("#app_confirmPwd").attr("novalidate", "novalidate");

                $.CurrentDialog.find("#app_password").dblclick(function () {
                    $.CurrentDialog.find("#app_password").removeAttr("readonly");
                    $.CurrentDialog.find("#app_confirmPwd").removeAttr("readonly");
                    $.CurrentDialog.find("#app_password").removeAttr("novalidate");
                    $.CurrentDialog.find("#app_confirmPwd").removeAttr("novalidate");
                });
            }

            if ($.CurrentDialog.find('#app_userName').val() != null) {
                $.CurrentDialog.find('#addStaffUserInfo').validator("setField", "userName", null);
                $.CurrentDialog.find('#addStaffUserInfo').validator("setField", "userName", "用户名:required; username;");
            }
            $.CurrentDialog.find('#app_userName').change(function () {
                $.CurrentDialog.find('#addStaffUserInfo').validator("setField", "userName", null);
                $.CurrentDialog.find('#addStaffUserInfo').validator("setField", "userName", "用户名:required; username; remote[user/validateUsername.do, userName]");
            });

            $.CurrentDialog.find("#appUserBtn").click(function () {
                if ($.CurrentDialog.find("#app_areaName").val() == null || $.CurrentDialog.find("#app_areaName").val() == '') {
                    $.CurrentDialog.find("#app_areaName").removeAttr("novalidate");
                }
                var nameStr = "";
                // 验证表单数据是否合法
                if ($.CurrentDialog.find("#addStaffUserInfo").isValid()) {
                    if ($.CurrentDialog.find("#appUserBtn > span").text().trim() == "提交") {
                        nameStr += $.CurrentDialog.find('#app_userName').val();
                        BJUI.ajax('doajax', {
                            url: "user/addNewAppUser.do?" + (new Date()).getTime(),
                            data: $.CurrentDialog.find("#addStaffUserInfo").serialize(),
                            cache: false,
                            okCallback: function (json, options) {
                                BJUI.dialog('closeCurrent');
                                $.CurrentNavtab.find('#gridStaffInfo').datagrid('refresh', false);
                                BJUI.alertmsg('ok', "成功添加App账户<span style='color:green'>" + nameStr + "</span>！", {
                                    displayPosition: 'middlecenter'
                                });
                            },
                            errCallback: function (json, options) {
                                BJUI.alertmsg('error', '添加失败！');
                            }
                        });
                    } else {
                        nameStr += $.CurrentDialog.find('#app_userName').val();
                        BJUI.ajax('doajax', {
                            url: "user/alterAppUserInfo.do?" + (new Date()).getTime(),
                            data: $.CurrentDialog.find("#addStaffUserInfo").serialize(),
                            cache: false,
                            okCallback: function (json, options) {
                                BJUI.dialog('closeCurrent');
                                $.CurrentNavtab.find('#gridStaffInfo').datagrid('refresh', false);
                                BJUI.alertmsg('ok', "成功修改App用户<span style='color:green'>" + nameStr + "</span>！", {
                                    displayPosition: 'middlecenter'
                                });
                            },
                            errCallback: function (json, options) {
                                BJUI.alertmsg('error', '修改失败！');
                            }
                        });
                    }
                } else {
                    BJUI.alertmsg('warn', '验证未通过！', {
                        okCall: function () {
                            $('#addUserInfo').validator('cleanUp');
                        }
                    });
                }
            });
        }
    });
}


/**
 * 获取所有角色
 */
function getAllRolesOfApp() {
    $.ajax({
        type: "get",
        url: "role/getAllRoles.do?" + new Date(),
        dataType: "json",
        async: false,
        success: function (data) {
            var options = "";
            for (var i = 0; i < data.length; i++) {
                if (data[i].roleName == '其他' || data[i].roleLevel < 6) {
                    continue;
                } else {
                    options += "<option value='" + data[i].roleId + "'>" + data[i].roleName + "</option>";
                }
            }
            $('#app_roleId').html(options);
            $('#app_roleId').selectpicker('refresh');
        }
    });
}

//获取所有网格职务
function getGridPostNameOfGridStaff() {
    BJUI.ajax('doajax', {
        url: 'gridStaff/getGridPostName.do',
        type: 'GET',
        okalert: false,
        async: false,
        okCallback: function (json, options) {
            var data = eval(json);
            var postStr = "";
            for (var i = 0; i < data.length; i++) {
                postStr += "<option value='" + data[i].gridPostId + "'>" + data[i].gridPostName + "</option>";
            }
            $.CurrentDialog.find("#gridPostId").html(postStr);
            $.CurrentDialog.find("#gridPostId").selectpicker('refresh');
        }
    });
}

function convertDate(value) {
    var year = "";
    var month = "";
    var day = "";
    var hour = "";
    var minute = "";
    var second = "";
    var currentDate = "";
    if (typeof (value) == 'object') {
        year = value.year + 1900;
        month = value.month + 1;
        day = value.date;
        hour = value.hours;
        minute = value.minutes;
        second = value.seconds;
    } else {
        // 时间戳
        var time = new Date(value);
        year = time.getYear() + 1900;
        month = time.getMonth() + 1;
        day = time.getDate();
        hour = time.getHours();
        minute = time.getMinutes();
        second = time.getSeconds();
    }
    currentDate = year + "年";
    if (month >= 10) {
        currentDate = currentDate + month + "月";
    } else {
        currentDate = currentDate + "0" + month + "月";
    }
    if (day >= 10) {
        currentDate = currentDate + day + "日";
    } else {
        currentDate = currentDate + "0" + day + "日";
    }
    if (hour >= 10) {
        currentDate = currentDate + " " + hour;
    } else {
        currentDate = currentDate + " 0" + hour;
    }
    if (minute >= 10) {
        currentDate = currentDate + ":" + minute;
    } else {
        currentDate = currentDate + ":0" + minute;
    }
    if (second >= 10) {
        currentDate = currentDate + ":" + second;
    } else {
        currentDate = currentDate + ":0" + second;
    }
    return currentDate;
}

/**
 * ztree下拉选择
 *
 * @param e
 * @param treeId
 * @param treeNode
 */
//选择事件
function gridStaff_NodeCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getCheckedNodes(true);
    var ids = '', names = '';
    var areaId = '';
    for (var i = 0; i < nodes.length; i++) {
        ids += ',' + nodes[i].id;
        names += ',' + nodes[i].name;
        areaId += ',' + nodes[i].areaId;
    }
    if (ids.length > 0) {
        ids = ids.substr(1), names = names.substr(1);
        areaId = areaId.substr(1);
    }
    var $from = $('#' + treeId).data('fromObj');
    if ($from && $from.length) {
        $from.val(names);
        $("#gridStaff_areaId").val(areaId);
    }
    ;
    if ($.CurrentDialog.find("#gridStaff_areaId").val() != "") {
        getAllGridByAreaId($.CurrentDialog.find("#gridStaff_areaId").val());
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
function appUser_NodeCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getCheckedNodes(true);
    var ids = '', names = '';
    var areaId = '';
    for (var i = 0; i < nodes.length; i++) {
        ids += ',' + nodes[i].id;
        names += ',' + nodes[i].name;
        areaId += ',' + nodes[i].areaId;
    }
    if (ids.length > 0) {
        ids = ids.substr(1), names = names.substr(1);
        areaId = areaId.substr(1);
    }
    var $from = $('#' + treeId).data('fromObj');
    if ($from && $from.length) {
        $from.val(names);
        $("#app_areaId").val(areaId);
    }
    ;
}

//获取该区域下所有网格显示到下拉
function getAllGridByAreaId(e) {
    BJUI.ajax('doajax', {
        url: 'gridStaff/getAllGridByAreaId.do',
        type: 'GET',
        data: {"areaId": e},
        okalert: false,
        async: false,
        okCallback: function (json, options) {
            var data = eval(json);
            var gridStr = "";
            for (var i = 0; i < data.length; i++) {
                gridStr += "<option value='" + data[i].gridId + "'>" + data[i].gridName + "</option>";
            }
            $.CurrentDialog.find("#gridStaff_gridId").html(gridStr);
            $.CurrentDialog.find('#gridStaff_gridId').selectpicker('refresh');
        }
    });
}

function refreshGridStaff() {
    $.CurrentNavtab.find('#gridStaffInfo').datagrid('refresh', false);
}
