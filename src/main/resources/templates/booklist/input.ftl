<!DOCTYPE html>
<html lang="en">
<head>
    <#--<#assign sec = JspTaglibs["/META-INF/security.tld"] />-->
    <meta charset="utf-8" />
    <title>校验书单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <#include "../common/base.ftl">
    <link href="${ctx}/static/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet"/>
    <script src="${ctx}/static/bootstrap-fileinput/js/fileinput.js"></script>
    <script src="${ctx}/static/bootstrap-fileinput/js/fileinput_locale_zh.js"></script>

    <style>
        body{
            min-width: 250px;
        }
        .kv-file-upload, .kv-upload-progress{
            display: none;
        }
        .export-btn {
            border: 1px solid green;
            border-radius: 4px;
            background: #fff;
            font-size: 30px;
            color: green!important;
        }
        .export-div {
            display: none;
            text-align: center;
            margin-top: 50px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var currentFileName;
            var fileinput = $("#excel-file").fileinput({
                language: "zh",
                theme: "fa",
                uploadUrl: '${ctx}/booklist/upload', //上传的地址
                allowedFileExtensions: ['xls', 'xlsx'],//接收的文件后缀
                enctype: 'multipart/form-data',
                validateInitialCount:true,
                previewFileIcon: "<i class='glyphicon glyphicon-file'></i>",
                uploadExtraData: function(previewId) {   //额外参数的关键点
                    var obj = {};
                    if (previewId) {
                        var specialName = $("#" + previewId).attr("name")
                        obj.specialName = specialName;
                    }
                    return obj;
                },
                slugCallback: function(filename) {
                    return filename.replace('(', '_').replace(']', '_');
                }
            });

            $("#excel-file").on("filebatchselected", function (event, data) {
                var uploadTime = new Date().getTime();
                for (var i = 0; i < data.length; i++) {
                    var file = data[i];
                    var name = file.name;
                    currentFileName = uploadTime + encodeURI(name);
                    console.log(currentFileName)
                    var currentFile = $("[id^='preview'][title='"+name+"']");
                    currentFile.attr("name", currentFileName);
                }
                $(".export-div").hide();
//                $('.kv-file-remove').click()
            });

            //导入文件上传完成之后的事件
            $("#excel-file").on("fileuploaded", function (event, data, previewId, index) {
//                $("#" + previewId + " .file-upload-indicator").append("aaa")
                $(".export-div").show();
            });

            $(".fileinput-remove-button,.fileinput-remove").on("click", function () {
                $(".export-div").hide();
            });
            $(".upload-box").on("click", ".kv-file-remove", function () {
                if ($(".file-preview-frame").length == 1) {
                    $(".export-div").hide();
                }
            })

            $(".export-btn").on("click", function () {
                var url = "${ctx}/booklist/export?specialName="+encodeURI(currentFileName);
                if (tryAccess(url, "get")) {
                    window.location.href = url;
                }else{
                    alert("系统繁忙，请稍后操作！");
                }
            })

        });

        function tryAccess(url, type) {
            var access = false;
            $.ajax({
                url: url,
                type: type,
                async: false,
                success: function (a) {
                    access = a != '';
                }
            });
            return access;
        }
    </script>
</head>

<body>
<#include "../common/header.ftl">
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <div class="main-container-inner">
		<#include "../common/sidebar.ftl">

        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="#">首页</a>
                    </li>
                    <li class="active">校验书单</li>
                </ul><!-- .breadcrumb -->

                <div class="nav-search" id="nav-search">
                    <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
                    </form>
                </div><!-- #nav-search -->
            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1>
                        校验书单
                        <small>
                            <i class="icon-double-angle-right"></i>
                            查看
                        </small>
                    </h1>
                </div><!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="root-box">
                            <br>
                            <div class="upload-box">
                                <input id="excel-file" type="file" name="file" class="file-loading">
                            </div>
                            <div class="export-div">
                                <button class="btn btn-default export-btn">导出校验结果</button>
                            </div>
                        </div>

                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->

    <#include "../common/setting.ftl">
    </div><!-- /.main-container-inner -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->
<#include "../common/footer.ftl">
</body>
</html>