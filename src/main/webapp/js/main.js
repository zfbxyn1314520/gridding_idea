/**
 * main.html页面的外部js代码
 * author lnc
 */
$(function () {

    var themeName = $.cookie('bjui_theme') || 'blue';
    // var $tabs = $('#bjui-navtab > .tabsPageHeader > .tabsPageHeaderContent > .navtab-tab');
    var color = themeColor(themeName);
    $(".am-fr>ul>li>a:first-child").css("color", color);

    $('body').loading({
        loadingWidth: 300,
        title: '',
        name: 'loading',
        titleColor: '#fff',
        discColor: color,
        discription: '请稍后，见证奇迹的时刻马上揭晓......',
        direction: 'column',
        type: 'origin',
        originBg: color,
        originDivWidth: 40,
        originDivHeight: 40,
        originWidth: 6,
        originHeight: 6,
        smallLoading: false,
        loadingBg: 'rgba(0,0,0,0)',
        loadingMaskBg: 'rgba(22,22,22,0.2)'
    });

    document.onreadystatechange = function () {
        if (document.readyState == "complete") {
            removeLoading('loading');
        }
    }

    //生成main.html页面的左边导航菜单
    $.ajax({
        type: "get",
        url: "user/getUserPerMenu.do?" + (new Date()).getTime(),
        dataType: "json",
        cache: false,
        success: function (data) {
            var menu = "<ul class='sidebar-nav'>";
            var str = "";
            var num = 0;
            for (var i = 0; i < data.length; i++) {
                if (data[i].parentMenuId == null) {

                    menu += "<li class='sidebar-nav-link sidebar-nav-link-" + themeName + "'><a href='javascript:void(0);' ";
                    if (data[i].menuName == "我的主页")
                        menu += "onclick='switchTab()'";
                    menu += "class='sidebar-nav-sub-title";
                    if (num == 0)
                        menu += " active";
                    menu += "'><i class='" + data[i].menuIcon + " sidebar-nav-link-logo'></i>" + data[i].menuName;

                    for (var j = 0; j < data.length; j++) {
                        if (data[i].menuId == data[j].parentMenuId && data[j].menuUri != null) {
                            str += "<li class='sidebar-nav-link sidebar-nav-link-" + themeName + "'><a href='" + data[j].menuUri +
                                "' data-id='navTab" + data[j].menuId + "' data-title='" + data[j].menuName + "' data-toggle='navtab' " +
                                "class='sidebar-nav-sub-title'><span style='margin-left:10px;' class='" + data[j].menuIcon +
                                " sidebar-nav-link-logo'></span>&nbsp;" + data[j].menuName + "</a></li>";
                        }
                    }

                    if (str != "") {
                        menu += "<span class='am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico' style='position:absolute; left:170px;'>" +
                            "</span></a><ul class='sidebar-nav sidebar-nav-sub'>" + str + "</ul></li>";
                    } else {
                        menu += "</a></li>";
                    }
                    str = "";
                    num++;
                }
            }
            menu += "</ul>";
            $("#navTab").html(menu);

            //侧边菜单
            $('.sidebar-nav-sub-title').on('click', function () {
                $tabs = $('#bjui-navtab > .tabsPageHeader > .tabsPageHeaderContent > .navtab-tab');
                console.log($tabs.html());
                $('.sidebar-nav-sub-title').removeClass("active");
                if ($(this).parent().parent().attr("class").trim() == "sidebar-nav sidebar-nav-sub") {
                    $('.sidebar-nav-sub-title').removeClass("sub-active-" + themeName);
                    $(this).addClass("sub-active-" + themeName);
                    $(this).parent().parent().parent().children("a").addClass("active");
                } else {
                    $('.sidebar-nav-sub').not($(this).siblings('.sidebar-nav-sub')).slideUp(80);
                    $(this).parent().children("a").addClass("active");
                }
                $(this).siblings('.sidebar-nav-sub').slideToggle(80).end()
                    .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
            });

            $('#bjui-themes > li > a').on('click', function () {
                var $active = $("#navTab a[class='sidebar-nav-sub-title sub-active-" + themeName + "']");
                $('#navTab li').removeClass('sidebar-nav-link-' + themeName);
                $active.removeClass('sub-active-' + themeName)

                themeName = $(this).data('theme');
                $active.addClass('sub-active-' + themeName)
                $('#navTab li').toggleClass('sidebar-nav-link-' + themeName);
                $(".am-fr>ul>li>a:first-child").css("color", themeColor(themeName));
            });

            $('#bjui-navtab > .tabsPageHeader > .tabsPageHeaderContent > .navtab-tab > li').on('click', function () {
                // this.getMoreLi().removeClass('active').eq(iTabIndex).addClass('active')
                // console.log(this.getTabs());
            });
        }
    });

    //保存用户区域信息
    $.get("user/saveUserAreaSession.do?" + (new Date()).getTime());
    $.ajaxSetup({
        type: 'POST',
        complete: function (xhr, status) {
            var sessionStatus = xhr.getResponseHeader('sessionstatus');
            if (sessionStatus == 'timeout') {
                var top = getTopWinow();
                var href = window.location.href;
                var yes = confirm('由于您长时间没有操作, 请重新登录！');
                if (yes) {
                    top.location.href = href.substring(0, href.indexOf("/pop/main.jsp"));
                }
            }
        }
    });

    /**
     * 在页面中任何嵌套层次的窗口中获取顶层窗口
     * @return 当前页面的顶层窗口对象
     */
    function getTopWinow() {
        var p = window;
        while (p != p.parent) {
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

function switchTab() {
    BJUI.navtab('switchTab', 'main')
}

function themeColor(theme) {
    var color = "";
    switch (theme) {
        case "blue":
            color = "#0e90d2";
            break;
        case "purple":
            color = "#573e7e";
            break;
        case "green":
            color = "#008000";
            break;
    }
    return color;
}

//声明区域对象
function area(id, pId, name, areaId) {
    this.id = id;
    this.pId = pId;
    this.name = name;
    this.areaId = areaId;
}
