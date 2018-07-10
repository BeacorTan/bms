//@ sourceURL=user.js
/**
 * 用户模块js:包括用户主界面、用户增加修改界面
 */
var CM_User = function () {
    //用户主页面属性开始
    var userTableId = "userTable";
    var $userTable = $("#userTable");
    var tableAjaxUrl = "user/page/list";
    var userTableColum = [{checkbox: true}
        , {title: 'id', field: 'id', align: 'center', valign: 'middle', visible: false}
        , {title: '登录账号', field: 'loginName', align: 'center', valign: 'middle'}
        , {title: '姓名', field: 'name', align: 'center', valign: 'middle'}
        , {title: '电话', field: 'mobile', align: 'center'}
        , {title: '手机号', field: 'phone', align: 'center'}
        , {title: '邮箱', field: 'email', align: 'center'}];
    var $deleteUsers = $("#deleteUsers");
    //用户管理页面头部表单搜索按钮
    var $user_searchUsers = $("#user_searchUsers");

    //用户修改页面属性开始
    var $updateUserInfo = $("#updateUserInfo");//修改用户信息按钮
    var $resetUserInfo = $("#resetUserInfo");//取消用户信息按钮
    var updateUserFormId = "updateUserForm";//个人信息表单
    var $updateUserForm = $("#updateUserForm");
    var $updateUserBtn = $("#updateUserBtn");
    var $inputDom = $("#imgUpload");//图片input dom元素
    var uploadAjaxUrl = CM_Components.getContextPath() + "/fastdfs/upload/photo";//上传图片地址
    var updateUserPwdFormId = "updateUserPwdForm";
    var $updateUserPwdForm = $("#updateUserPwdForm");//修改密码表单
    var $updateUserPwdBtn = $("#updateUserPwdBtn");//修改密码按钮
    //用户修改页面属性结束

    var $addUserForm = $("#addUserForm");
    //用户增加页面属性结束

    //部门管理模块-用户列表开始
    var $depUserTable = $("#depUserTable");
    //部门管理模块-用户列表结束


    var $searchUserFormId = $("#searchUserFormId");

    /**
     * 用户修改页面事件绑定
     */
    var userProfileEventHandler = function () {

        $updateUserInfo.on("click", function () {

        });
    }


    /**
     * 用户增加页面事件绑定
     */
    var userAddEventHandler = function () {
        $("#addUserBtn").on("click", function () {
            var formData = CommonUtils.getFormData("addUserForm");
            if (formData && formData.id) {
                $('#addUserForm').bootstrapValidator('removeField', 'loginName');
            }
            $addUserForm.submit();
        });
    }

    /**
     * 取消
     */
    var $cpmg_user_select_role = $("#cpmg_user_select_role");
    var selectRoleEventHandler = function () {
        var isOpen = false;
        $cpmg_user_select_role.on("click", function () {
            if (isOpen) {
                $("#cpmg_user_role_list").addClass("hidden-select_role");
                isOpen = false;
            } else {
                $("#cpmg_user_role_list").removeClass("hidden-select_role");
                isOpen = true;
            }
        });
    }
    /**
     * 用户增加页面验证表单
     */
    var initUserAddBootStrapValidate = function (addAjaxUrl) {
        $addUserForm.bootstrapValidator({
//          trigger: 'blur',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: '员工姓名不能为空'
                        }
                    }
                },
                channelCode: {
                    validators: {
                        notEmpty: {
                            message: '分机号不能为空'
                        }
                    }
                },
                deptName: {
                    validators: {
                        notEmpty: {
                            message: '部门不能为空'
                        }
                    }
                },
                positionName: {
                    validators: {
                        notEmpty: {
                            message: '职位不能为空'
                        }
                    }
                },
                loginName: {
                    validators: {
                        notEmpty: {
                            message: '登录账号不能为空'
                        },
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            url: CM_Components.getContextPath() + '/user/uniqueness/loginName',//验证地址
                            message: '账号已存在',//提示消息
                            delay: 2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                            type: 'POST'//请求方式
                        }
                    }
                },
                companyName: {
                    validators: {
                        notEmpty: {
                            message: '公司不能为空'
                        }
                    }
                }
            }
        }).on("success.form.bv", function (e) {
            console.info("edit user", e)
            var params = CommonUtils.getFormData("addUserForm");
            var roleCodes = [];
            var selectRoles = $("#cpmg-user-role-table").bootstrapTable("getSelections");
            for (var i = 0; i < selectRoles.length; i++) {
                roleCodes.push(selectRoles[i].roleCode);
            }
            params["roleCodes"] = roleCodes;

            var reqUrl;
            if (params && params.id) {
                reqUrl = "/user/update";
            } else {
                reqUrl = "/user/add";
            }

            $.ajax({
                url: CommonUtils.getContextAll(reqUrl),
                type: "POST",
                contentType: 'application/json',
                data: JSON.stringify(params),
                success: function (result) {
                    CM_Components.layerMsg("编辑成功");
                    CommonUtils.closeTab('addUserTab');
                },
                error: function (errorMsg) {
                    console.error(errorMsg);
                    CM_Components.layerMsg("编辑失败");
                }
            });
            CM_Components.refreshTable($userTable);
        });
        ;
    }

    /**
     * 用户修改页面验证表单
     */
    var initUserUpdateBootStrapValidate = function (updateAjaxUrl) {
        $updateUserForm.bootstrapValidator({
//          trigger: 'blur',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                userCode: {
                    validators: {
                        notEmpty: {
                            message: '员工编码不能为空'
                        }
                    }
                },
                userName: {
                    validators: {
                        notEmpty: {
                            message: '员工名称不能为空'
                        }
                    }
                }
            }
        }).on("success.form.bv", function (e) {
            CM_Components.ajaxFormSumbitTable(updateAjaxUrl, updateUserFormId, userTableId);
            CM_Components.refreshTable($userTable);
        });
    }

    /**
     * 用户修改密码页面验证表单
     */
    var initUserPwsUpdateBootStrapValidate = function (updateAjaxUrl) {
        $updateUserPwdForm.bootstrapValidator({
//          trigger: 'blur',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                loginPwd: {
                    validators: {
                        notEmpty: {
                            message: '当前不能为空'
                        }
                    }
                },
                currentPwd: {
                    validators: {
                        notEmpty: {
                            message: '新密码不能为空'
                        }
                    }
                },
                loginRepPwd: {
                    validators: {
                        notEmpty: {
                            message: '新重复密码不能为空'
                        },
                        identical: {//相同
                            field: 'loginPwd', //需要进行比较的input name值
                            message: '两次密码不一致'
                        }
                    }
                }
            }
        }).on("success.form.bv", function (e) {
            CM_Components.ajaxFormSumbitTable(updateAjaxUrl, updateUserPwdFormId, userTableId);
        });
    }

    /**
     * 用户增加页面事件绑定
     */
    var userUpdateEventHandler = function () {
        $updateUserBtn.on("click", function () {
            $updateUserForm.submit();
        });

        $updateUserPwdBtn.on("click", function () {
            var params = CommonUtils.getFormData("updateUserPwdForm");
            $.ajax({
                url: CommonUtils.getContextAll("/user/updatePwd"),
                type: "POST",
                //contentType: 'application/json',
                // data: JSON.stringify(params),
                data: params,
                // dataType: "json",
                success: function (result) {
                    CM_Components.layerMsg("修改成功");
                    CM_Components.layerClose();
                },
                error: function (xhr, status, error) {
                    CM_Components.layerMsg("修改失败");
                }
            });
            // $updateUserPwdForm.submit();
        });
    }

    /**
     * 上传头像
     * @param $inputDom
     * @param uploadAjaxUrl
     */
    var initBootStrapFileInput = function () {
        $inputDom
            .fileinput({
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
                    (parent.CM_Components).bootstrapSweetAlert("", res.msg, "success");
                }
                else {
                    CM_Components.bootstrapSweetAlert("", res.msg, "error");
                }
            })
    }

    var initDepUserTable = function (deptCode) {
        CM_Components.initBootStrapTable($depUserTable, tableAjaxUrl + "?deptCode=" + deptCode, userTableColum);
    }


    return {
        //初始化用户管理的主页面:用户列表
        initUserMain: function () {
            //initTable();
        },
        //初始化用户管理的修改页面:role_profile.html
        initUserProfile: function (updateAjaxUrl) {
            initUserUpdateBootStrapValidate(updateAjaxUrl);
            userUpdateEventHandler();
            initUserPwsUpdateBootStrapValidate(updateAjaxUrl);

        },
        //初始化用户增加页面
        initUserAdd: function (addAjaxUrl) {
            initUserAddBootStrapValidate(addAjaxUrl);
            userAddEventHandler();
            selectRoleEventHandler();
        },
        //初始化上传图片插件
        initUserUploadPhoto: function () {
            initBootStrapFileInput();
        },
        //初始化部门管理模块下的用户列表
        initDepUserTable: function (depCode) {
            initDepUserTable(depCode);
        }
    }
}();
