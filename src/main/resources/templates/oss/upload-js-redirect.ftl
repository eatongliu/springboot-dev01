<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../common/base.ftl">
    <meta charset="utf-8" />
    <title>OSS上传-js直传</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="${ctx}/static/crypto1/crypto/crypto.js" ></script>
    <script src="${ctx}/static/crypto1/hmac/hmac-min.js" ></script>
    <script src="${ctx}/static/crypto1/sha1/sha1-min.js" ></script>
    <script src="${ctx}/static/js/base64.js" ></script>
    <script src="${ctx}/static/plupload-2.1.2/js/plupload.full.min.js" ></script>

    <style>
        pre {
            background: none;
            border: none;
        }
    </style>
    <script type="text/javascript">
        $(function () {


        });
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
                    <li class="active">OSS上传</li>
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
                        OSS上传
                        <small>
                            <i class="icon-double-angle-right"></i>
                            查看
                        </small>
                    </h1>
                </div><!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <form name=theform>
                            <input type="radio" name="myradio" value="local_name" checked=true/> 上传文件名字保持本地文件名字
                            <input type="radio" name="myradio" value="random_name" /> 上传文件名字是随机文件名
                            <br/>
                            上传到指定目录:<input type="text" id='dirname' placeholder="如果不填，默认是上传到根目录" size=50>
                        </form>

                        <h4>您所选择的文件列表：</h4>
                        <div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>

                        <br/>

                        <div id="container">
                            <a id="selectfiles" href="javascript:void(0);" class='btn'>选择文件</a>
                            <a id="postfiles" href="javascript:void(0);" class='btn'>开始上传</a>
                        </div>

                        <pre id="console"></pre>


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

<script src="${ctx}/static/js/upload.js" ></script>
</html>