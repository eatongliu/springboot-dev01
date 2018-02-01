<a class="menu-toggler" id="menu-toggler" href="#">
    <span class="menu-text"></span>
</a>
<div class="sidebar" id="sidebar">
    <script type="text/javascript">
        try {
            ace.settings.check('sidebar', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <button class="btn btn-success">
                <i class="icon-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="icon-pencil"></i>
            </button>

            <button class="btn btn-warning">
                <i class="icon-group"></i>
            </button>

            <button class="btn btn-danger">
                <i class="icon-cogs"></i>
            </button>
        </div>

        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div><!-- #sidebar-shortcuts -->

    <ul class="nav nav-list" id="menu_ul">

    </ul><!-- /.nav-list -->
<#--<ul class="nav nav-list" id="menu-list">-->
<#--<@nr2kAuthTag url="/admin/index">-->
<#--<li class="">-->
<#--<a href="${ctx}/admin/index">-->
<#--<i class="icon-dashboard"></i>-->
<#--<span class="menu-text"> 控制台 </span>-->
<#--</a>-->
<#--</li>-->
<#--</@nr2kAuthTag>-->

<#--<@nr2kAuthTag url="/booklist">-->
<#--<li class="">-->
<#--<a href="${ctx}/booklist/">-->
<#--<i class="icon-check"></i>-->
<#--<span class="menu-text"> 校验书单 </span>-->
<#--</a>-->
<#--</li>-->
<#--</@nr2kAuthTag>-->

<#--</ul><!-- /.nav-list &ndash;&gt;-->

    <div class="sidebar-collapse" id="sidebar-collapse">
        <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
    </div>

    <script type="text/javascript">
        try {
            ace.settings.check('sidebar', 'collapsed')
        } catch (e) {
        }
    </script>
</div>

 <script type="text/javascript">
     $(function () {
         $.ajax({
             url: "${ctx}/sidebar",
             data: {},
             type: "get",
             dataType: "json",
             success: function (result) {
                 if (result.status === "SUCCESS") {
                     var currUrl = window.location.href;
                     var currUri = currUrl.substring(currUrl.indexOf('${ctx}'), currUrl.length);
                     var data = result.data
                     var collector = [];
                     data.map(function (one) {
                         var subMenu = one.subPermission;
                         var hasSubmenu = subMenu != null && subMenu.length > 0;

                         collector.push("<li>")
                         if (hasSubmenu) {
                             collector.push("<a href='javascript:void(0);' class='dropdown-toggle'>")
                         }else {
                             collector.push("<a href='${ctx}" + one.url +"'>")
                         }
                         var icon = one.icon || 'icon-file'
                         collector.push("<i class='" + icon + "'></i>")
                         collector.push("<span class='menu-text'>" + one.name + "</span>")
                         if (hasSubmenu)
                             collector.push("<b class='arrow icon-angle-down'></b>")
                         collector.push("</a>")
                         if (hasSubmenu)
                             collector.push(getSubmenu(subMenu));
                     })

                     var menuBox = $("#menu_ul");
                     menuBox.html(collector.join(''));
                     var currentLi = menuBox.find("a[href='" + currUri + "']").parent("li");
                     currentLi.addClass("active");
                     currentLi.parents("ul.submenu").show();
                     currentLi.parents("li").addClass("open");
                 } else {
                     alert(result.cause)
                 }
             },
             error: function () {
                 alert("网络请求异常！")
             }
         });
     });

     function getSubmenu(menus) {
         var collector = [];
         collector.push("<ul class='submenu'>");
         menus.map(function (one) {
             var subMenu = one.subPermission;
             var hasSubmenu = subMenu != null && subMenu.length > 0;

             collector.push("<li>")
             if (hasSubmenu) {
                 collector.push("<a href='javascript:void(0);' class='dropdown-toggle'>")
             }else {
                 collector.push("<a href='${ctx}" + one.url +"'>")
             }
             var icon = one.icon || 'icon-file'
             collector.push("<i class='" + icon + "'></i>")
             collector.push(one.name)
             if (hasSubmenu)
                 collector.push("<b class='arrow icon-angle-down'></b>")
             collector.push("</a>")
             if (hasSubmenu)
                 collector.push(getSubmenu(subMenu));
         })
         collector.push("</ul>")
         return collector.join('');
     }
 </script>