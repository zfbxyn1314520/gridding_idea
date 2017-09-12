/**
 * 刘春晓
 * account.html外部js代码
 */

var auditAccount = false;
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
            var status = true;
            var toolBar = true;
            for (var z = 0; z < per.length; z++) {
                if (per[z] == "添加") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addaccountInfo();'>添加</button>";
                }
                if (per[z] == "编辑") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editaccountInfo();'>编辑</button>";
                }
                if (per[z] == "删除") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteaccountInfo();'>删除</button>";
                }
                if (per[z] == "审核") {
                    toolBarItem += "<button type='button' class='btn-orange' data-icon='check' onclick='auditaccountInfo();'>审核</button>";
                    auditAccount = true;
                }
                if (per[z] == "查看") {
                    status = false;
                }
            }

            //判断用户是否有增加等看权限，如果没有则隐藏工具条
            if (toolBarItem == "") {
                toolBar = false;
            }
            toolBarItem += "<button type='button' class='btn-default' data-icon='refresh' onclick='refreshAccount();'>刷新</button>";
            //生成datagrid表格
            $.CurrentNavtab.find('#accountInfo').datagrid({
                height: '100%',
                tableWidth: '100%',
                gridTitle: "<i class='" + data[1].menuIcon + " sidebar-nav-link-logo'></i>" + data[1].menuName + "" +
                "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
                "<i class='" + data[0].menuIcon + " sidebar-nav-link-logo'></i>" + data[0].menuName + "",
                showToolbar: toolBar,
                toolbarCustom: toolBarItem,
                local: 'remote',
                dataUrl: 'account/getAllAccountByHouseId.do',
                columns: [
                    {name: 'accountHolder', label: '户主', align: 'center', width: width * 0.1},
                    {name: 'memberCount', label: '人数', align: 'center', width: width * 0.06},
                    {
                        name: 'holderCard', label: '身份证号', align: 'center', width: width * 0.16,
                        render: function (value, data) {
                            var codeStr = /^(.{6})(.*)(.{4})$/.exec(value);
                            if (codeStr != null) {
                                return codeStr[1] + "****" + codeStr[3];
                            }
                        }
                    },
                    {name: 'holderTel', label: '户主电话', align: 'center', width: width * 0.1},
                    {
                        name: 'house', label: '户口地址', align: 'center', width: width * 0.26,
                        render: function (value, data) {
                            return value.houseSite;
                        }
                    },
                    {
                        name: 'accountAudit', label: '状态', align: 'center', width: width * 0.06,
                        render: function (value, data) {
                            if (value == 0) {
                                return "<i class='am-icon-check-square-o' style='color:green'></i>";
                            } else {
                                return "<i class='am-icon-minus-square-o' style='color:red'></i>";
                            }
                        }
                    },
                    {
                        name: ' ', label: '操作', align: 'center', hide: status, width: width * 0.26,
                        render: function (value, data) {
                            var str = "";
                            if (data.accountAudit == 1) {
                                str += "&ensp;<button type='button' class='btn-orange' data-icon='id-badge' disabled='disabled'" +
                                    "onclick='getPopData(" + data.accountId + ",\"" + data.accountHolder + "\"," + data.memberCount + ");'>家庭成员(" + data.memberCount + ")</button>" +
                                    "&ensp;<button type='button' class='btn-red' data-icon='history' style='font-size:12px;'" +
                                    "onclick='auditAccountSingle(" + data.accountId + ",\"" + data.accountHolder + "\"," + data.accountAudit + ");'>审核</button>";
                            } else {
                                str += "&ensp;<button type='button' class='btn-orange' data-icon='id-badge' " +
                                    "onclick='getPopData(" + data.accountId + ",\"" + data.accountHolder + "\"," + data.memberCount + ");'>家庭成员(" + data.memberCount + ")</button>" +
                                    "&ensp;<button type='button' class='btn-green' style='font-size:12px;');'>已通过</button>";
                            }

                            return "<button type='button' class='btn-default' data-icon='search' style='font-size:12px;' " +
                                "onclick='getOneaccountDetail(" + data.accountId + ");'>详细信息</button>" + str;

                            return "<button type='button' class='btn-orange' data-icon='id-badge' " +
                                "onclick='getPopData(" + data.accountId + ",\"" + data.accountHolder + "\"," + data.memberCount + ");'>家庭成员(" + data.memberCount + ")</button>";
                        },
                    },
                ],
                paging: {pageSize: 50, selectPageSize: '10,20,40,50', pageCurrent: 1, showPagenum: 5, totalRow: 0},
                linenumberAll: true,
                showLinenumber: true,
                showCheckboxcol: 'lock',
                hScrollbar: false,
                columnMenu: false,
                filterThead: false
            });
        }
    });
});


function getPopData(id, name, count) {
    if (count > 0) {
        BJUI.navtab({
            id: 'navTab002',
            url: 'pop/accountPop.html',
            title: '家庭成员',
            fresh: true
        });
        sessionStorage.accountId = id;
        sessionStorage.accountHolder = name;
    } else {
        return false;
    }

}


function addaccountInfo() {
    BJUI.dialog({
        id: 'addAccount',
        width: 655,
        height: 610,
        target: $("#addAccount"),
        title: '添加户口信息',
        mask: true,
        resizable: false,
        onClose: function () {
            $.CurrentNavtab.find('#accountInfo').datagrid('selectedRows', false);
        }
    });
    getCourtNameOfAccount();
    getStatusNameOfAccount();
    getTypeNameOfAccount();
    saveAccountInfo();
}

//打开修改弹窗
function editaccountInfo() {
    var accountId = $.CurrentNavtab.find("#accountInfo").data('selectedDatas');
    if (accountId == null || accountId.length != 1) {
        BJUI.alertmsg('info', '请选择一条需要编辑的户口');
    } else {
        BJUI.dialog({
            id: 'editAccount',
            target: $.CurrentNavtab.find("#addAccount"),
            title: '修改户口信息',
            width: 655,
            height: 610,
            mask: true,
            resizable: false,
            onClose: function () {
                $.CurrentNavtab.find('#accountInfo').datagrid('selectedRows', false);
            }
        });
        $.CurrentDialog.find("#submitBtnac > span").text("修改");
        var upAid = "";
        for (var i = 0; i < accountId.length; i++) {
            upAid = accountId[i].accountId;
        }

        $.ajax({
            type: "post",
            url: "account/getAccountById.do?" + new Date().getTime(),
            data: {"upAid": upAid},
            dataType: "json",
            success: function (data) {
                getCourtNameOfAccount();
                getStatusNameOfAccount();
                getTypeNameOfAccount();
                $.CurrentDialog.find("#a_houseNum").attr("value", data.house.houseNum);
                $.CurrentDialog.find("#a_houseArea").attr("value", data.house.houseArea);
                $.CurrentDialog.find("#a_houseId").attr("value", data.house.houseId);
                $.CurrentDialog.find("#a_houseSite").attr("value", data.house.houseSite);
                $.CurrentDialog.find('#a_typeId').selectpicker('val', data.house.typeId);
                $.CurrentDialog.find('#a_statusId').selectpicker('val', data.house.statusId);
                $.CurrentDialog.find('#a_courtId').selectpicker('val', data.house.unit.block.court.courtId);
                getBlockNameOfAccount(data.house.unit.block.court.courtId);
                $.CurrentDialog.find('#a_blockId').selectpicker('val', data.house.unit.block.blockId);
                getUnitNameOfAccount(data.house.unit.block.blockId);
                $.CurrentDialog.find('#a_unitId').selectpicker('val', data.house.unit.unitId);
                $.CurrentDialog.find("#a_accountHolder").attr("value", data.accountHolder);
                $.CurrentDialog.find("#a_editHouseDate").attr("value", convertDate(data.house.editHouseDate));
                $.CurrentDialog.find("#a_accountSite").attr("value", data.accountSite);
                $.CurrentDialog.find("#a_memberCount").attr("value", data.memberCount);
                $.CurrentDialog.find("#a_holderTel").attr("value", data.holderTel);
                $.CurrentDialog.find("#a_holderCard").attr("value", data.holderCard);
            }
        });
        saveAccountInfo();
    }
}

//表单验证
function saveAccountInfo() {
    $("#a_courtId").change(function () {
        if ($(this).val() != "") {
            getBlockNameOfAccount($(this).val());
        } else {
            $.CurrentDialog.find("#a_unitId").html("<option value=''>请选择单元</option>");
            $.CurrentDialog.find("#a_blockId").html("<option value=''>请选择楼栋</option>");
            $.CurrentDialog.find('#a_unitId').selectpicker('refresh');
            $.CurrentDialog.find('#a_blockId').selectpicker('refresh');
        }
    });
    $("#a_blockId").change(function () {
        if ($(this).val() != "") {
            getUnitNameOfAccount($(this).val());
        } else {
            $.CurrentDialog.find("#a_unitId").html("<option value=''>请选择单元</option>");
            $.CurrentDialog.find('#a_unitId').selectpicker('refresh');
        }

    });
    $("#a_houseNum").blur(function () {
        var option = $.CurrentDialog.find("#a_courtId option:selected").text() + "" + $.CurrentDialog.find("#a_blockId option:selected").text() + "" + $.CurrentDialog.find("#a_unitId option:selected").text() + "" + $.CurrentDialog.find("#a_houseNum").val();
        $.CurrentDialog.find("#a_houseSite").attr("value", option);
    });
    $.CurrentDialog.find("#submitBtnac").click(function () {
        var nameStr = "";
        if ($.CurrentDialog.find("#addAccountInfo").isValid()) {
            if ($.CurrentDialog.find("#submitBtnac > span").text().trim() == "提交") {
                nameStr += $.CurrentDialog.find("#a_accountHolder").val();

                BJUI.ajax('doajax', {
                    url: 'house/saveHouse.do?' + (new Date()).getTime(),
                    type: 'POST',
                    form: $.CurrentDialog.find("#addAccountInfo"),
                    data: $.CurrentDialog.find("#addAccountInfo").serialize(),
                    cache: false,
                    okalert: false,
                    callback: function (json) {
                        if (json.statusCode() == 200) {
                            BJUI.dialog('closeCurrent');
                            $.CurrentNavtab.find('#accountInfo').datagrid('refresh', true);
                            BJUI.alertmsg('ok', "成功添加户口<span style='color:green'>" + nameStr + "<span>！", {
                                displayPosition: 'middlecenter'
                            });
                        } else {
                            BJUI.alertmsg('error', json.message);
                        }
                    }
                });
            } else {
                nameStr += $.CurrentDialog.find("#a_accountHolder").val();
                BJUI.ajax('ajaxform', {
                    url: 'house/editHouseById.do?' + (new Date()).getTime(),
                    form: $.CurrentDialog.find("#addAccountInfo"),
                    type: 'POST',
                    data: $.CurrentDialog.find("#addAccountInfo").serialize(),
                    cache: false,
                    okalert: false,
                    callback: function (json) {
                        if (json.statusCode == 200) {
                            BJUI.alertmsg('ok', '成功修改户口' + nameStr + "!", {
                                displayPosition: 'middlecenter'
                            });
                            BJUI.dialog('closeCurrent');
                            $.CurrentNavtab.find('#accountInfo').datagrid('refresh', true);
                        } else {
                            BJUI.alertmsg('error', json.message);
                        }
                    }
                });
            }
        } else {
            BJUI.alertmsg('warn', '验证未通过！', {
                okCall: function () {
                    $('#addAccountInfo').validator('cleanUp');
                }
            });
        }
    });
}

//查看
function getOneaccountDetail(e) {
    BJUI.dialog({
        id: 'selectAccount',
        target: $.CurrentNavtab.find("#selectAccount"),
        title: '户口信息',
        width: 565,
        height: 300,
        mask: true,
        resizable: false,
        onClose: function () {
            $.CurrentNavtab.find('#accountInfo').datagrid('selectedRows', false);
        }
    });
    $.ajax({
        type: "post",
        url: "account/getAccountByAccountId.do?" + new Date(),
        data: {"accountId": e},
        dataType: "json",
        success: function (data) {
            var house = data.house;
            $.CurrentDialog.find("#a-houseNum").text(house.houseNum);
            $.CurrentDialog.find("#a-courtName").text(house.unit.block.court.courtName);
            $.CurrentDialog.find("#a-blockName").text(house.unit.block.blockName);
            $.CurrentDialog.find("#a-unitName").text(house.unit.unitName);
            $.CurrentDialog.find("#a-houseArea").text(house.houseArea);
            $.CurrentDialog.find("#a-typeName").text(house.houseType.typeName);
            $.CurrentDialog.find("#a-statusName").text(house.houseStatus.statusName);
            $.CurrentDialog.find("#a-houseSite").text(house.houseSite);
            $.CurrentDialog.find("#a_AccountHoulder").text(data.accountHolder);
            $.CurrentDialog.find("#a_AccountSite").text(data.accountSite);
            $.CurrentDialog.find("#a_MemberCount").text(data.memberCount);
            $.CurrentDialog.find("#a_HolderTel").text(data.holderTel);
            if (data.accountAudit == 0) {
                $.CurrentDialog.find("#a_accountAudit").html("<span style='color:green'>已审核</span>");
            } else {
                $.CurrentDialog.find("#a_accountAudit").html("<span style='color:red'>审核中</span>");
            }
            $.CurrentDialog.find("#a_editAccountName").text(data.editAccountName);
            $.CurrentDialog.find("#a_editAccountDate").text(convertDate(data.editAccountDate));
            $.CurrentDialog.find("#a_HolderCard").text(data.holderCard);
        }
    });
}

//获取所有小区
function getCourtNameOfAccount() {
    BJUI.ajax('doajax', {
        url: 'block/getCourtName.do',
        type: 'GET',
        okalert: false,
        async: false,
        okCallback: function (json, options) {
            var data = eval(json);
            var courtStr = "<option value=''>请选择小区名称</option>";
            for (var i = 0; i < data.length; i++) {
                courtStr += "<option value='" + data[i].courtId + "'>" + data[i].courtName + "</option>";
            }
            $.CurrentDialog.find("#a_courtId").html(courtStr);
            $.CurrentDialog.find('#a_courtId').selectpicker('refresh');
        }
    });
}


//获取所有楼栋
function getBlockNameOfAccount(e) {
    BJUI.ajax('doajax', {
        url: 'house/getBlockName.do',
        type: 'GET',
        data: {"courtId": e},
        okalert: false,
        async: false,
        okCallback: function (json, options) {
            var data = eval(json);
            var blockStr = "<option value=''>请选择楼栋</option>";
            for (var i = 0; i < data.length; i++) {
                blockStr += "<option value='" + data[i].blockId + "'>" + data[i].blockName + "</option>";
            }
            $.CurrentDialog.find("#a_blockId").html(blockStr);
            $.CurrentDialog.find('#a_blockId').selectpicker('refresh');
        }
    });
}

//获取所有楼栋单元
function getUnitNameOfAccount(e) {
    BJUI.ajax('doajax', {
        url: 'house/getUnitName.do',
        type: 'GET',
        data: {"blockId": e},
        okalert: false,
        async: false,
        okCallback: function (json, options) {
            var data = eval(json);
            var unitStr = "<option value=''>请选择单元</option>";
            for (var i = 0; i < data.length; i++) {
                unitStr += "<option value='" + data[i].unitId + "'>" + data[i].unitName + "</option>";
            }
            $.CurrentDialog.find("#a_unitId").html(unitStr);
            $.CurrentDialog.find('#a_unitId').selectpicker('refresh');
        }
    });
}


//获取所有房屋状态
function getStatusNameOfAccount() {
    BJUI.ajax('doajax', {
        url: 'house/getStatusName.do',
        type: 'GET',
        okalert: false,
        async: false,
        okCallback: function (json, options) {
            var data = eval(json);
            var statuStr = "<option value=''>请选择房屋状态</option>";
            for (var i = 0; i < data.length; i++) {
                statuStr += "<option value='" + data[i].statusId + "'>" + data[i].statusName + "</option>";
            }
            $.CurrentDialog.find("#a_statusId").html(statuStr);
            $.CurrentDialog.find('#a_statusId').selectpicker('refresh');
        }
    });
}

//获取所有户型
function getTypeNameOfAccount() {
    BJUI.ajax('doajax', {
        url: 'house/getTypeName.do',
        type: 'GET',
        okalert: false,
        okCallback: function (json, options) {
            var data = eval(json);
            var typeStr = "<option value=''>请选择户型</option>";
            for (var i = 0; i < data.length; i++) {
                typeStr += "<option value='" + data[i].typeId + "'>" + data[i].typeName + "</option>";
            }
            $.CurrentDialog.find("#a_typeId").html(typeStr);
            $.CurrentDialog.find('#a_typeId').selectpicker('refresh');
        }
    });
}

//户口信息审核
function auditAccountSingle(e, f, g) {
    if (auditAccount == true) {
        if (g == 0) {
            return false;
        } else {
            var nameStr = f;
            var audAid = e;
            BJUI.alertmsg('confirm', '你确定要审核户口<span style="color:orange">' + nameStr + '</span>吗？', {
                okCall: function () {
                    $.ajax({
                        type: "post",
                        url: "account/updateAuditAccount.do?" + new Date(),
                        data: {"audAid": audAid},
                        dataType: "text",
                        success: function (data) {
                            if (data == 0) {
                                BJUI.alertmsg('error', '审核失败');
                            } else {
                                $.CurrentNavtab.find('#accountInfo').datagrid('refresh', true);
                                BJUI.alertmsg('ok', '成功审核户口' + nameStr + "!", {
                                    displayPosition: 'middlecenter'
                                });
                            }
                        }
                    });
                }
            });
        }

    } else {
        return false;
    }
}

//审核
function auditaccountInfo() {
    var accountId = $.CurrentNavtab.find("#accountInfo").data('selectedDatas');
    var audAid = "";
    var nameStr = "";
    if (typeof(accountId) == "undefined" || accountId.length == 0) {
        BJUI.alertmsg('info', '请选择需要审核的户口');
    } else {
        for (var i = 0; i < accountId.length; i++) {
            if (accountId[i].accountAudit == 0) {
                continue;
            } else {
                audAid += accountId[i].accountId + ",";
                nameStr += accountId[i].accountHolder + ",";
            }
        }
        if (audAid.length > 0) {
            nameStr = nameStr.substring(0, nameStr.length - 1);
            BJUI.alertmsg('confirm', '你确定要审核户口' + nameStr + '吗？', {
                okCall: function () {
                    $.ajax({
                        type: "post",
                        url: "account/updateAuditAccount.do?" + new Date(),
                        data: {"audAid": audAid},
                        dataType: "text",
                        success: function (data) {
                            if (data == 0) {
                                BJUI.alertmsg('error', '审核失败');
                            } else {
                                $.CurrentNavtab.find('#accountInfo').datagrid('refresh', true);
                                BJUI.alertmsg('ok', '成功审核户口' + nameStr + "!", {
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


function convertDate(value) {
    var year = "";
    var month = "";
    var day = "";
    var hour = "";
    var minute = "";
    var second = "";
    var currentDate = "";
    if (typeof(value) == 'object') {
        year = value.year + 1900;
        month = value.month + 1;
        day = value.date;
        hour = value.hours;
        minute = value.minutes;
        second = value.seconds;
    } else {
        //时间戳
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


function refreshAccount() {
    $.CurrentNavtab.find('#accountInfo').datagrid('refresh', false);
}