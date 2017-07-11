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
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addWorkInfo();'>添加</button>";
                }
                if (per[z] == "编辑") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editWorkInfo();'>编辑</button>";
                }
                if (per[z] == "删除") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteWorkInfo();'>删除</button>";
                }
                if (per[z] == "查看") {
                    status = false;
                }
            }

            //判断用户是否有增加等看权限，如果没有则隐藏工具条
            if (toolBarItem == "") {
                toolBar = false;
            }

            toolBarItem += "<button type='button' class='btn-default' data-icon='refresh' onclick='refreshWork();'>刷新</button>";
            //生成datagrid表格
            $.CurrentNavtab.find('#attendanceInfo').datagrid({
                height: '100%',
                tableWidth: '100%',
                gridTitle: "<i class='" + data[1].menuIcon + " sidebar-nav-link-logo'></i>" + data[1].menuName + "" +
                "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
                "<i class='" + data[0].menuIcon + " sidebar-nav-link-logo'></i>" + data[0].menuName + "",
                showToolbar: toolBar,
                toolbarCustom: toolBarItem,
                local: 'remote',
                dataUrl: 'work/getAllAttendanceLogByAreaId.do',
                columns: [
                    {
                        name: 'gridStaff', label: '网格员', align: 'center', width: width * 0.1,
                        render: function (value, data) {
                            return value.gridStaffName;
                        }
                    },
                    {name: 'recordDate', label: '日期', align: 'center', width: width * 0.1},
                    {
                        name: 'start_time',
                        label: '上班时间',
                        align: 'center',
                        type: 'date',
                        pattern: 'HH:mm:ss',
                        width: width * 0.13
                    },
                    {name: 'start_site', label: '打卡地点', align: 'center', width: width * 0.18},
                    {
                        name: 'end_time',
                        label: '下班时间',
                        align: 'center',
                        type: 'date',
                        pattern: 'yyyy-MM-dd HH:mm:ss',
                        width: width * 0.13
                    },
                    {name: 'end_site', label: '签退地点', align: 'center', width: width * 0.18},
                    {name: 'start_memo', label: '备注', align: 'center', width: width * 0.18},
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

/**
 * 删除用户信息方法
 */
function deleteWorkInfo() {
    var attendance = $.CurrentNavtab.find("#attendanceInfo").data('selectedDatas');
    var delId = "";
    var nameStr = "";
    if (typeof(attendance) == "undefined" || attendance.length == 0) {
        BJUI.alertmsg('info', '请选择需要删除的道路');
    } else {
        for (var i = 0; i < attendance.length; i++) {
            delId += attendance[i].attendance_id + ",";
            nameStr += attendance[i].recordDate + ",";
        }
        nameStr = nameStr.substring(0, nameStr.length - 1);
        BJUI.alertmsg("confirm",  "你确定要删除<span style='color:orange'>"+nameStr+"</span>考勤记录吗？", {
            okCall: function () {
                $.ajax({
                    type: "post",
                    url: "work/deleteWorkByIds.do?" + new Date().getTime(),
                    data: {"delId": delId},
                    dataType: "text",
                    success: function (data) {
                        if (data == 0) {
                            BJUI.alertmsg('error', '删除失败');
                        } else {
                            $.CurrentNavtab.find('#attendanceInfo').datagrid('refresh', true);
                            BJUI.alertmsg('ok', "成功删除<span style='color:orange'>"+nameStr+"</span>考勤记录吗？", {
                                displayPosition: 'middlecenter'
                            });
                        }
                    }
                });
            }
        });
    }
}

//刷新页面
function refreshWork() {
    $.CurrentNavtab.find('#attendanceInfo').datagrid('refresh', false);
    $.CurrentNavtab.find('#attendanceInfo').datagrid('selectedRows', false);
}
