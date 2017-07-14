/**
 * main.html页面的外部js代码
 * author 李宁财
 */
$(function () {
    //生成main.html页面的左边导航菜单
    $.ajax({
        type: "get",
        url: "user/getUserPerMenu.do?" + (new Date()).getTime(),
        dataType: "json",
        cache: false,
        success: function (data) {
            var menu = "<ul class='sidebar-nav'>";
            for (var i = 0; i < data.length; i++) {
                if (data[i].parentMenuId == null) {
                    if (data[i].menuName == "我的主页") {
                        menu += "<li class='sidebar-nav-link'><a href='javascript:void(0);' class='active'>" +
                            "<i class='" + data[i].menuIcon + " sidebar-nav-link-logo'></i>我的主页</a></li>";
                        continue;
                    } else {
                        menu += "<li class='sidebar-nav-link'><a href='javascript:void(0);' class='sidebar-nav-sub-title'>" +
                            "<i class='" + data[i].menuIcon + " sidebar-nav-link-logo'></i>" + data[i].menuName +
                            "<span class='am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico' style='position:absolute; left:170px;'></span></a>" +
                            "<ul class='sidebar-nav sidebar-nav-sub'>";
                    }
                    for (var j = 0; j < data.length; j++) {
                        if (data[i].menuId == data[j].parentMenuId && data[j].menuUri != null) {
                            menu += "<li class='sidebar-nav-link'><a href='" + data[j].menuUri + "' data-id='navTab" + data[j].menuId + "' " +
                                "data-title='" + data[j].menuName + "' data-toggle='navtab'>" +
                                "<span style='margin-left:10px;' class='" + data[j].menuIcon + " sidebar-nav-link-logo'></span>&nbsp;" + data[j].menuName + "</a></li>";
                        }
                    }
                    menu += "</ul></li>";
                }
            }
            menu += "</ul>";
            $("#navTab").html(menu);
            //侧边菜单
            $('.sidebar-nav-sub-title').on('click', function () {
                $(this).siblings('.sidebar-nav-sub').slideToggle(80).end()
                    .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
            });
        }
    });

    //保存用户区域信息
    $.get("user/saveUserAreaSession.do?" + (new Date()).getTime());

    $.ajaxSetup({
        type: 'POST',
        complete: function(xhr,status) {
            var sessionStatus = xhr.getResponseHeader('sessionstatus');
            if(sessionStatus == 'timeout') {
                var top = getTopWinow();
                var href = window.location.href;
                var yes = confirm('由于您长时间没有操作, 请重新登录！');
                if (yes) {
                    top.location.href=href.substring(0,href.indexOf("/pop/main.jsp"));
                }
            }
        }
    });

    /**
     * 在页面中任何嵌套层次的窗口中获取顶层窗口
     * @return 当前页面的顶层窗口对象
     */
    function getTopWinow(){
        var p = window;
        while(p != p.parent){
            p = p.parent;
        }
        return p;
    }
});


//单击事件
function S_NodeClick(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    zTree.checkNode(treeNode, !treeNode.checked, true, true);
    event.preventDefault();
}

/**
 * 区域下拉框ztree树
 * @returns {Array}
 */
function showAreaMenu() {
    var areaMenu = new Array();
    $.ajax({
        type: "get",
        url: "user/getAreaMenuById.do?" + (new Date()).getTime(),
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

//声明区域对象
function area(id, pId, name, areaId) {
    this.id = id;
    this.pId = pId;
    this.name = name;
    this.areaId = areaId;
}
