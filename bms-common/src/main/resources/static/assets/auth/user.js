var CM_User = function () {

    var $inputDom = $("#imgUpload");//图片input dom元素

    var uploadAjaxUrl = CommonUtils.getContextAll("/fastdfs/upload/photo");//上传图片地址
    //用户修改页面属性结束

    /**
     * 上传头像
     * @param $inputDom
     * @param uploadAjaxUrl
     */
    var initBootStrapFileInput = function () {
        $inputDom.fileinput({
            language: 'zh',
            uploadUrl: uploadAjaxUrl,
            autoReplace: true,
            maxFileCount: 1,
            maxFileSize: 2000,
            uploadAsync: true,
            enctype: 'multipart/form-data',
            dropZoneTitle: "可以拖拽头像到这里,快到碗里来!!!",
            allowedFileExtensions: ["jpg", "png", "gif"],
            browseClass: "btn green btn-outline sbold", //按钮样式
            removeClass: "btn red btn-outline sbold",
            uploadClass: "btn purple btn-outline sbold",
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
        })
            .on("fileuploaded", function (e, data) {
                var res = data.response;
                if (res.success == true) {
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                    (parent.BmsComponents).bootstrapSweetAlert("", res.msg, "success");
                }
                else {
                    BmsComponents.bootstrapSweetAlert("", res.msg, "error");
                }
            })
    }


    return {
        //初始化用户增加页面
        initUserAdd: function (addAjaxUrl) {
            initUserAddBootStrapValidate(addAjaxUrl);
            userAddEventHandler();
            selectRoleEventHandler();
        },
        //初始化上传图片插件
        initUserUploadPhoto: function () {
            initBootStrapFileInput();
        }
    }
}();
