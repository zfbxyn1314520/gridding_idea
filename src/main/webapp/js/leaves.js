/**
 * Created by eollse on 2017/7/11.
 */
$(function () {
    //获取导航菜单权限功能
    $.ajax({
        type: 'get',
        url: 'menu/getMenuPerById.do?' + new Date().getTime(),
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
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addLeavesInfo();'>添加</button>";
                }
                if (per[z] == "编辑") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editLeavesInfo();'>编辑</button>";
                }
                if (per[z] == "删除") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteLeavesInfo();'>删除</button>";
                }
                if (per[z] == "查看") {
                    status = false;
                }
            }

            //判断用户是否有增加等看权限，如果没有则隐藏工具条
            if (toolBarItem == "") {
                toolBar = false;
            }

            toolBarItem += "<button type='button' class='btn-default' data-icon='refresh' onclick='refreshLeaves();'>刷新</button>";
            //生成datagrid表格
            $.CurrentNavtab.find('#leavesInfo').datagrid({
                height: '100%',
                tableWidth: '100%',
                gridTitle: "<i class='" + data[1].menuIcon + " sidebar-nav-link-logo'></i>" + data[1].menuName + "" +
                "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
                "<i class='" + data[0].menuIcon + " sidebar-nav-link-logo'></i>" + data[0].menuName + "",
                showToolbar: toolBar,
                toolbarCustom: toolBarItem,
                local: 'remote',
                dataUrl: 'leaves/getAllLeavesByAreaIds.do',
                columns: [
                    {
                        name: 'area', label: '所在区域', align: 'center', width: width * 0.13,
                        render: function (value, data) {
                            return value.areaName;
                        }
                    },
                    {
                        name: 'grid', label: '所在网格', align: 'center', width: width * 0.12,
                        render: function (value, data) {
                            return value.gridName;
                        }
                    }, {
                        name: 'gridStaff', label: '姓名', align: 'center', width: width * 0.08,
                        render: function (value, data) {
                            return value.gridStaffName;
                        }
                    }, {
                        name: 'leavesType', label: '请假类型', align: 'center', width: width * 0.08,
                        render: function (value, data) {
                            return value.leaves_type_name;
                        }
                    },
                    {
                        name: 'leaves_begin_time',
                        label: '开始时间',
                        align: 'center',
                        type: 'date',
                        pattern: 'HH:mm:ss',
                        width: width * 0.13
                    },
                    {
                        name: 'leaves_end_time',
                        label: '结束时间',
                        align: 'center',
                        type: 'date',
                        pattern: 'yyyy-MM-dd HH:mm:ss',
                        width: width * 0.13
                    },
                    {
                        name: '', label: '请假天数', align: 'center', width: width * 0.1,
                        render: function (value, data) {
                            if (data.leaves_begin_time != undefined)
                                return DateDiffNoWeekDay(data.leaves_begin_time, data.leaves_end_time);
                        }
                    },
                    {name: 'leaves_reason', label: '请假原因', align: 'center', width: width * 0.13},
                    {
                        name: '', label: '操作', align: 'center', hide: status, width: width * 0.1,
                        render: function (value, data) {
                            var str = "";
                            if (data.leavesStatus == 0) {
                                str += "&ensp;<button type='button' class='btn-green' data-icon='check-square-o' style='font-size:12px;'" +
                                    "onclick='auditStoreSingle(" + data.storeId + ",\"" + data.storeName + "\"," + data.storeAudit + ");'>批准</button>";
                            } else {
                                str += "&ensp;<button type='button' class='btn-red' style='font-size:12px;');'>批准</button>";
                            }
                            return str;
                        },
                    },
                ],
                paging: {pageSize: 20, selectPageSize: '10,30,40,50', pageCurrent: 1, showPagenum: 5, totalRow: 0},
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


function deleteLeavesInfo() {
    var leaves = $.CurrentNavtab.find("#leavesInfo").data('selectedDatas');
    var delId = "";
    var nameStr = "";
    if (typeof(leaves) == "undefined" || leaves.length == 0) {
        BJUI.alertmsg('info', '请选择需要删除的记录');
    } else {
        for (var i = 0; i < leaves.length; i++) {
            delId += leaves[i].leaves_id + ",";
            nameStr += leaves[i].gridStaff.gridStaffName + ",";
        }
        nameStr = nameStr.substring(0, nameStr.length - 1);
        BJUI.alertmsg("confirm", "你确定要删除<span style='color:orange'>" + nameStr + "</span>的请假申请吗？", {
            okCall: function () {
                $.ajax({
                    type: "post",
                    url: "leaves/deleteLeavesLogByIds.do?" + new Date().getTime(),
                    data: {"delId": delId},
                    dataType: "text",
                    success: function (data) {
                        if (data == 0) {
                            BJUI.alertmsg('error', '删除失败');
                        } else {
                            $.CurrentNavtab.find('#leavesInfo').datagrid('refresh', true);
                            BJUI.alertmsg('ok', "成功删除<span style='color:orange'>" + nameStr + "</span>考勤记录吗？", {
                                displayPosition: 'middlecenter'
                            });
                        }
                    }
                });
            }
        });
    }

}

/*根据起始时间和结束日期计算天数*/
function DateDiffNoWeekDay(sDate1, sDate2) {

    //日期格式为yyyy-mm-dd
    var oDate1 = StringToDate(sDate1);
    var oDate2 = StringToDate(sDate2);
    /*alert('取整-->'+parseInt((oDate2 - oDate1) / 1000 / 60 / 60 /24));
     alert('非取整-->'+(oDate2 - oDate1) / 1000 / 60 / 60 /24);*/
    if (parseInt((oDate2 - oDate1) / 1000 / 60 / 60 / 24) < (oDate2 - oDate1) / 1000 / 60 / 60 / 24) {
        var days = parseInt((oDate2 - oDate1) / 1000 / 60 / 60 / 24) + 1;
    }
    else {
        days = parseInt((oDate2 - oDate1) / 1000 / 60 / 60 / 24);
    }

    //var days = parseInt((oDate2 - oDate1) / 1000 / 60 / 60 /24);//获取总的天数
    var days1 = parseInt((oDate2 - oDate1) / 1000 / 60 / 60) % 8;  //获取余下的小时
    /*--减去不用上班的时间,即09-18之外的时间--*/
    if (days1 > 8) {
        days1 = 8;
    }
    var tempDate = oDate1;
    while (tempDate.getTime() <= oDate2.getTime()) {
        //tempDate = addDays(tempDate,2);//加一天
        //days>0表示超过1天，防止出现负数days
        if (checkWeekDay(DateToString(tempDate)) & days > 0) {
            //如果是周末,天数减1
            days--;
        }
        tempDate = addDays(tempDate, 2);//加一天
    }
    if (days == 1) {
        if (parseInt((oDate2 - oDate1) / 1000 / 60 / 60 / 24) == 0 & days1 == 0) {
            days = 1;
        }
        else {
            days = 0;
        }
        /*一天或半天加判断解决天数的问题*/
    }


    return days + "天" + days1 + "小时";
}

/*判断是否含有周末,如果是周末 返回true,没有返回false*/
function checkWeekDay(sDate) {
    arys = sDate.split('-');
    arys1 = arys[2].split(' ');
    arys2 = arys1[1].split(':');
    oDate = new Date(arys[0], parseInt(arys[1], 10) - 1, arys1[0], arys2[0], arys2[1], arys2[2]);
    day = oDate.getDay();//判断是否周末
    if (day == 0 || day == 6) {
        return true;
    }
    return false;
}
/*增加天数*/
function addDays(oDate, days) {
    if (days > 0) {
        days = days - 1;
    }
    if (days < 0) {
        days = days + 1;
    }
    var result = new Date(oDate.getTime() + (days * 24 * 60 * 60 * 1000));
    return result;
}
/*将字符串转换成日期*/
function StringToDate(sDate) {
    arys = sDate.split('-');
    arys1 = arys[2].split(' ');
    arys2 = arys1[1].split(':');
    if (arys2[0] > 18) {
        arys2[0] = 18;
        arys2[1] = 00;
        arys2[2] = 00;
    }
    if (arys2[0] < 10) {//9
        arys2[0] = 10;//9
        arys2[1] = 0;
        arys2[2] = 0;
    }
    var newDate = new Date(arys[0], parseInt(arys[1], 10) - 1, arys1[0], arys2[0], arys2[1], arys2[2]);
    return newDate;
}
/*为一部分月份及日期加前+0*/
function DateToString(oDate) {
    var month = oDate.getMonth() + 1;
    var day = oDate.getDate();
    var hour = oDate.getHours();
    var mi = oDate.getMinutes();
    var second = oDate.getSeconds();
    //如果月份小于10月则在前面加0
    if (month < 10) {
        month = "0" + month;
    }
    //如果日期小于10号则在前面加0
    if (day < 10) {
        day = "0" + day;
    }
    if (hour < 10) {
        //如果小于9点 设置为9点
        if (hour < 10) { //9
            hour = 10;//9
        }
        hour = "0" + hour;
    }
    //如果大于18点，让他等于18点
    if (hour > 18) {
        hour = 18;
    }
    if (mi < 10) {
        mi = "0" + mi;
    }
    if (second < 10) {
        second = "0" + second;
    }
    return oDate.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + mi + ":" + second;
}

//刷新页面
function refreshLeaves() {
    $.CurrentNavtab.find('#leavesInfo').datagrid('refresh', false);
    $.CurrentNavtab.find('#leavesInfo').datagrid('selectedRows', false);
}
