/**
 * 刘春晓
 * blog.html外部js代码
 */

var editor;
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
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-blue' data-icon='plus' onclick='addblogInfo();'>添加</button>";
                }
                if (per[z] == "编辑") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-green' data-icon='edit' onclick='editblogInfo();'>编辑</button>";
                }
                if (per[z] == "删除") {
                    toolBarItem += "<button type='button' style='border-right:none' class='btn-red' data-icon='trash-o' onclick='deleteblogInfo();'>删除</button>";
                }
                if (per[z] == "审核") {
                    toolBarItem += "<button type='button' class='btn-orange' data-icon='check' onclick='auditblogInfo();'>审核</button>";
                }
                if (per[z] == "查看") {
                    status = false;
                }
            }

            //判断用户是否有增加等看权限，如果没有则隐藏工具条
            if (toolBarItem == "") {
                toolBar = false;
            }
            toolBarItem += "<button type='button' class='btn-default' data-icon='refresh' onclick='refreshBlog();'>刷新</button>";
            //生成datagrid表格
            $('#blogInfo').datagrid({
                height: '100%',
                tableWidth: '100%',
                gridTitle: "<i class='" + data[1].menuIcon + " sidebar-nav-link-logo'></i>" + data[1].menuName + "" +
                "<i class='am-icon-angle-double-right sidebar-nav-link-logo'></i>" +
                "<i class='" + data[0].menuIcon + " sidebar-nav-link-logo'></i>" + data[0].menuName + "",
                showToolbar: toolBar,
                toolbarCustom: toolBarItem,
                local: 'remote',
                dataUrl: 'blog/getAllBlogByAreaId.do',
                columns: [
                    {
                        name: 'area', label: '所属区域', align: 'center', width: width * 0.2,
                        render: function (value, data) {
                            return value.areaName;
                        }
                    },
                    {
                        name: 'grid', label: '所属网格', align: 'center', width: width * 0.2,
                        render: function (value, data) {
                            return value.gridName;
                        }
                    },
                    {name: 'blogName', label: '日志名称', align: 'center', width: width * 0.1},
                    {
                        name: 'gridStaff', label: '网格员', align: 'center', width: width * 0.1,
                        render: function (value, data) {
                            return value.gridStaffName;
                        }
                    },
                    {
                        name: 'blogType', label: '日志类型', align: 'center', width: width * 0.16,
                        render: function (value, data) {
                            var str = "";
                            if (value != "" && value != null) {
                                var blogType = data.blogType.split(",");
                                var length = blogType.length;
                                for (var i = 0; i < length; i++) {
                                    if (blogType[i] == 1) {
                                        str += "巡查,";
                                    }
                                    if (blogType[i] == 2) {
                                        str += "宣传,";
                                    }
                                    if (blogType[i] == 3) {
                                        str += "走访,";
                                    }
                                    if (blogType[i] == 4) {
                                        str += "处理,";
                                    }
                                }
                                str = str.substring(0, str.length - 1);
                            } else {
                                str += "无";
                            }
                            return str;
                        }
                    },
                    {
                        name: 'editBlogDate', label: '编辑时间', align: 'center', width: width * 0.12,
                        render: function (value, data) {
                            return convertDate(value);
                        }
                    },
                    {
                        name: ' ', label: '操作', align: 'center', hide: status, width: width * 0.12,
                        render: function (value, data) {
                            return "<button type='button' class='btn-default' data-icon='search' onclick='getOneBlogDetail(" + data.blogId + ");'>详细信息</button>";
                            ;
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
        }
    });
});

//打开添加弹窗
function addblogInfo() {
    BJUI.dialog({
        id: 'addBlog',
        target: $.CurrentNavtab.find("#addBlog"),
        title: '添加工作日志',
        width: 900,
        height: 600,
        mask: true,
        resizable: false,
        onClose: function () {
            $.CurrentNavtab.find('#blogInfo').datagrid('selectedRows', false);
            editor.destroy();
        },
        onLoad: function ($dialog) {
            var areaId = sessionStorage.userAreaId;
            var zTree = $.fn.zTree.getZTreeObj("blog_areaNameList");
            var node = zTree.getNodeByParam("areaId", areaId);
            editor = new wangEditor('blogContent');
            wangEditor.config.printLog = false;
            editor.config.uploadImgUrl = 'blog/uploadFile.do';
            editor.config.uploadParams = {mainName: 'app/blog'};
            editor.config.uploadImgFileName = 'blogPic';
            editor.config.uploadHeaders = {'Accept': 'text/x-json'};
            editor.create();
            node.checked = true, zTree.updateNode(node);
            $('#blog_areaName').val(node.name), $('#blog_areaId').val(areaId);
            //字数统计
            editor.onchange = function () {
                $("#blogCount").text(this.$txt.text().length);
            };
            //字数统计
            $("#blogCount").text(editor.$txt.text().length);
            // 清空内容。
            $('#clearBlogBtn').click(function () {
                editor.$txt.html('<p><br></p>');
                return false;
            });
            $("#editBlogDate").change(function () {
                if ($(this).val() != "") {
                    $("#blogName").val($(this).val().substring(0, 4) + "-" + $(this).val().substring(5, 7) + "-" + $(this).val().substring(8, 10));
                    $("#blogName").attr("readonly", "readonly");
                } else {
                    $("#blogName").val("");
                    $("#blogName").removeAttr("readonly");
                }
            });
        }
    });
    getAllGridOfBlog(sessionStorage.userAreaId);
    saveBlogInfo();
}

//打开修改弹窗
function editblogInfo() {
    var blogId = $.CurrentNavtab.find("#blogInfo").data('selectedDatas');
    if (blogId == null || blogId.length != 1) {
        BJUI.alertmsg('info', '请选择一条需要编辑的日志');
    } else {
        BJUI.dialog({
            id: 'editBlog',
            target: $.CurrentNavtab.find("#addBlog"),
            title: '修改工作日志',
            width: 900,
            height: 600,
            mask: true,
            resizable: false,
            onClose: function () {
                $.CurrentNavtab.find('#blogInfo').datagrid('selectedRows', false);
            },
            onLoad: function ($dialog) {
                editor = new wangEditor('blogContent');
                wangEditor.config.printLog = false;
                editor.config.uploadImgUrl = 'blog/uploadFile.do';
                editor.config.uploadParams = {mainName: 'app/blog'};
                editor.config.uploadImgFileName = 'blogPic';
                editor.config.uploadHeaders = {'Accept': 'text/x-json'};
                editor.create();
                //字数统计
                editor.onchange = function () {
                    $("#blogCount").text(this.$txt.text().length);
                };
                $("#blogCount").text(editor.$txt.text().length);
                $('#clearBlogBtn').click(function () {
                    editor.$txt.html('<p><br></p>');
                    return false;
                });
            }
        });
        $.CurrentDialog.find("#blogBtn > span").text("修改");
        var upBLid = "";
        for (var i = 0; i < blogId.length; i++) {
            upBLid = blogId[i].blogId;
        }
        $.ajax({
            type: "post",
            url: "blog/getBlogById.do?" + new Date(),
            data: {"upBLid": upBLid},
            dataType: "json",
            success: function (data) {
                var zTree = $.fn.zTree.getZTreeObj("blog_areaNameList");
                var node = zTree.getNodeByParam("areaId", data.areaId);
                node.checked = true, zTree.updateNode(node);
                $('#blog_areaName').val(node.name), $('#blog_areaId').val(data.areaId);
                getAllGridOfBlog(data.areaId);
                $.CurrentDialog.find('#blog_areaName').val(node.name);
                $.CurrentDialog.find('#blog_areaId').val(data.areaId);
                $.CurrentDialog.find('#blog_gridId').selectp2wsicker('val', data.gridId);
                getGridStaffByGridIdOfBlog(data.gridId);
                $.CurrentDialog.find('#blog_gridStaffId').selectpicker('val', data.gridStaffId);
                $.CurrentDialog.find("#blogName").attr("value", data.blogName);
                $.CurrentDialog.find("#blogId").attr("value", data.blogId);
                $.CurrentDialog.find('#editBlogDate').val(convertDate(data.editBlogDate));
                $.CurrentDialog.find("#blogContent").text(editor.$txt.html(data.blogContent));
                var blogPic = data.blogPic;
                var pics =  blogPic.split(";");
                for(var i=0;i<pics.length;i++){
                    $.CurrentDialog.find("#blogContent").text(editor.$txt.append("<p><img style='max-width: 300px;max-height: 300px' src='http://localhost:8080/grid/"+pics[i]+"' /></p>"));
                }

                if (data.blogType != null) {
                    var blogType = data.blogType.split(",");
                    if (blogType.length > 0) {
                        for (var i = 0; i < blogType.length; i++) {
                            if (blogType[i] == 1) {
                                $.CurrentDialog.find('#blogType1').iCheck('check');
                            }
                            if (blogType[i] == 2) {
                                $.CurrentDialog.find('#blogType2').iCheck('check');
                            }
                            if (blogType[i] == 3) {
                                $.CurrentDialog.find('#blogType3').iCheck('check');
                            }
                            if (blogType[i] == 4) {
                                $.CurrentDialog.find('#blogType4').iCheck('check');
                            }
                        }
                    }
                }
            }
        });
        saveBlogInfo();
    }
}

//表单验证
function saveBlogInfo() {
    $.CurrentDialog.find("#blog_gridId").change(function () {
        if ($(this).val() != "") {
            getGridStaffByGridIdOfBlog($(this).val());
        } else {
            $.CurrentDialog.find("#blog_gridStaffId").html("<option value=''>请选择单元</option>");
            $.CurrentDialog.find('#blog_gridStaffId').selectpicker('refresh');
        }
    });
    $.CurrentDialog.find("#blogBtn").click(function () {
        var nameStr = "";
        if ($.CurrentDialog.find("#addBlogInfo").isValid()) {
            var str = "";
            var blogPic = "";
            var imgUrl = "";
            var blogType = $.CurrentDialog.find('input[type=checkbox][id^=blogType]');
            var imgs = editor.$txt.find('img');
            var imgsLength = imgs.length;
            var typeLength = blogType.length;
            for (var i = 0; i < typeLength; i++) {
                //如果多选框被选中，则把值累计到str中
                if (blogType[i].checked == true) {
                    str += blogType[i].value + ",";
                }
            }
            for (var i = 0; i < imgsLength; i++) {
                imgUrl = imgs[i].src;
                blogPic += imgUrl.substring(imgUrl.indexOf("images/app"), imgUrl.length) + ";";
            }
            str = str.substring(0, str.length - 1);
            $.CurrentDialog.find("#blogType").val(str);
            $.CurrentDialog.find("#blogPic").val(blogPic);
            if ($.CurrentDialog.find("#blogBtn > span").text().trim() == "提交") {
                nameStr += $.CurrentDialog.find("#blockName").val();
                $.ajax({
                    type: "post",
                    url: "blog/saveBlog.do?" + new Date(),
                    data: $.CurrentDialog.find("#addBlogInfo").serialize(),
                    dataType: "json",
                    cache: false,
                    success: function (data) {
                        if (data == "1") {
                            BJUI.dialog('closeCurrent');
                            $.CurrentNavtab.find('#blogInfo').datagrid('refresh', true);
                            BJUI.alertmsg('ok', "成功添加日志<span style='color:green'>" + nameStr + "<span>！", {
                                displayPosition: 'middlecenter'
                            });
                        } else {
                            BJUI.alertmsg('error', '添加失败');
                        }
                    }
                });
            } else {
                nameStr += $.CurrentDialog.find("#blockName").val();
                $.ajax({
                    type: "post",
                    url: "blog/editBlogById.do?" + new Date(),
                    data: $.CurrentDialog.find("#addBlogInfo").serialize(),
                    dataType: "text",
                    success: function (data) {
                        if (data == 0) {
                            BJUI.alertmsg('error', '修改失败');
                        } else {
                            BJUI.alertmsg('ok', '成功修改工作日志' + nameStr + "!", {
                                displayPosition: 'middlecenter'
                            });
                            BJUI.dialog('closeCurrent');
                            $.CurrentNavtab.find('#blogInfo').datagrid('refresh', true);
                        }
                    }
                });
            }
        } else {
            BJUI.alertmsg('warn', '验证未通过！', {
                okCall: function () {
                    $('#addBlogInfo').validator('cleanUp');
                }
            });
        }
    });

}

//查看
function getOneBlogDetail(e) {
    BJUI.dialog({
        id: 'blogDetail',
        target: $.CurrentNavtab.find("#blogDetail"),
        title: '查看日志',
        width: 700,
        height: 600,
        mask: true,
        resizable: false,
        onClose: function () {
            $.CurrentNavtab.find('#blogInfo').datagrid('selectedRows', false);
        }
    });
    $.ajax({
        type: "post",
        url: "blog/getOneBlogById.do?" + new Date(),
        data: {"blogId": e},
        dataType: "json",
        success: function (data) {
            var str = "";
            if (data.blogType != null) {
                var blogType = data.blogType.split(",");
                for (var i = 0; i < blogType.length; i++) {
                    if (blogType[i] == 1) {
                        str += "巡查,";
                    }
                    if (blogType[i] == 2) {
                        str += "宣传,";
                    }
                    if (blogType[i] == 3) {
                        str += "走访,";
                    }
                    if (blogType[i] == 4) {
                        str += "处理,";
                    }
                }
                str = str.substring(0, str.length - 1);
            } else {
                str += "无";
            }
            $.CurrentDialog.find("#tb_blogName").text(data.blogName);
            $.CurrentDialog.find("#tb_gridStaff").text(data.gridStaff.gridStaffName);
            $.CurrentDialog.find("#tb_areaName").text(data.area.areaName);
            $.CurrentDialog.find("#tb_grid").text(data.grid.gridName);
            $.CurrentDialog.find("#tb_blogType").text(str);
            $.CurrentDialog.find("#tb_editBlogDate").text(convertDate(data.editBlogDate));
            $.CurrentDialog.find("#tb_blogContent").text(data.blogContent);
        }
    });
}

//删除
function deleteblogInfo() {
    var blogId = $.CurrentNavtab.find("#blogInfo").data('selectedDatas');
    var deBlid = "";
    var nameStr = "";
    if (typeof(blogId) == "undefined" || blogId.length == 0) {
        BJUI.alertmsg('ok', '请选择需要删除的日志');
    } else {
        for (var i = 0; i < blogId.length; i++) {
            deBlid += blogId[i].blogId + ",";
            nameStr += blogId[i].blogName + ",";
        }
        nameStr = nameStr.substring(0, nameStr.length - 1);
        BJUI.alertmsg('confirm', '你确定要删除日志' + nameStr + '吗？', {
            okCall: function () {
                $.ajax({
                    type: "post",
                    url: "blog/deleteBlog.do?" + new Date(),
                    data: {"deBlid": deBlid, "nameStr": nameStr},
                    dataType: "text",
                    success: function (data) {
                        if (data == 0) {
                            BJUI.alertmsg('error', '删除失败');
                        } else {
                            $.CurrentNavtab.find('#blogInfo').datagrid('refresh', true);
                            BJUI.alertmsg('ok', '成功删除日志' + nameStr + '！', {
                                displayPosition: 'middlecenter'
                            });
                        }
                    }
                });
            }
        });
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
function Blog_NodeCheck(e, treeId, treeNode) {
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
        $("#blog_areaId").val(areaId);
    }
    ;
    if ($.CurrentDialog.find("#blog_areaId").val() != "") {
        getAllGridOfBlog($.CurrentDialog.find("#blog_areaId").val());
    }
}

function getAllGridOfBlog(e) {
    BJUI.ajax('doajax', {
        url: 'gridStaff/getAllGridByAreaId.do',
        type: 'get',
        data: {"areaId": e},
        okalert: false,
        async: false,
        okCallback: function (json, options) {
            var data = eval(json);
            var gridStr = "<option value=''>请选择网格</option>";
            for (var i = 0; i < data.length; i++) {
                gridStr += "<option value='" + data[i].gridId + "'>" + data[i].gridName + "</option>";
            }
            $.CurrentDialog.find("#blog_gridId").html(gridStr);
            $.CurrentDialog.find('#blog_gridId').selectpicker('refresh');
        }
    });
}

function getGridStaffByGridIdOfBlog(e) {
    BJUI.ajax('doajax', {
        url: 'gridStaff/getGridStaffByGridId.do',
        type: 'get',
        data: {"gridId": e},
        okalert: false,
        async: false,
        okCallback: function (json, options) {
            var data = eval(json);
            var gridStaffStr = "<option value=''>请选择网格员</option>";
            for (var i = 0; i < data.length; i++) {
                gridStaffStr += "<option value='" + data[i].gridStaffId + "'>" + data[i].gridStaffName + "</option>";
            }
            $.CurrentDialog.find("#blog_gridStaffId").html(gridStaffStr);
            $.CurrentDialog.find('#blog_gridStaffId').selectpicker('refresh');
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

function refreshBlog() {
    $.CurrentNavtab.find('#blogInfo').datagrid('refresh', true);
}