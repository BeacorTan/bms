/**
 * 组织架构管理模块js
 */
var Dep = function () {

    var initDeptMain = function () {

        var $deptTable = $("#bms-dept-table");

        CM_Components.initBootStrapTable($deptTable, CM_Components.getContextAll("/dept/json/list"));

        // 查询
        $("#bms-dept-search-btn").click(function () {
            $deptTable.bootstrapTable('refresh', {
                query: CommonUtils.getFormData("bms-dept-search-form")
            });
        });

        var $deptEditTabID = "dept_edit";
        var $reqUrl = CM_Components.getContextAll("/dept/profile");

        // 新增
        $("#bms-dept-add-btn").click(function () {
            CommonUtils.addTab($deptEditTabID, "新增部门",$reqUrl);
        });

        // 修改
        $("#bms-dept-update-btn").click(function () {
            // return updateTab(bootstrapTable, tabId, reqUrl,tabTitle);
            CommonUtils.updateTab($deptTable, $deptEditTabID, $reqUrl,"部门编辑");
        });

        // 删除
        $("#bms-dept-remove-btn").click(function () {
            CommonUtils.addTab("dept_edit", "部门编辑", CM_Components.getContextAll("/dept/profile"));
        });

    }

    var initDeptProfile = function () {
        var $editForm = $("#bms-dept-edit-form");
        $editForm.bootstrapValidator({
            fields: {
                depName: {
                    validators: {
                        notEmpty: {
                            message: '部门名称不能为空'
                        }
                    }
                },
                depCode: {
                    validators: {
                        notEmpty: {
                            message: '部门编码不能为空'
                        }
                    }
                },
                depType: {
                    validators: {
                        notEmpty: {
                            message: '部门类型不能为空'
                        }
                    }
                },
                treeSort: {
                    validators: {
                        notEmpty: {
                            message: '排序不能为空'
                        }
                    }
                }
            }
        }).on("success.form.bv", function (e) {
            swal({
                    title: "确定提交表单?",
                    text: "",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonClass: "btn-danger",
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    closeOnConfirm: true,
                    closeOnCancel: true
                },
                function (isConfirm) {
                    if (isConfirm) {

                        var params = CM_Components.getFormData("bms-dept-edit-form");
                        $.ajax({
                            type: "POST",
                            contentType: 'application/json',
                            cache: false,
                            url: CM_Components.getContextAll("/dept/edit"),
                            data: JSON.stringify(params),
                            success: function (responseJson) {
                                CM_Components.layerMsg("编辑成功");
                                CommonUtils.closeTab('dept_edit')
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                CM_Components.layerMsg("系统错误!!!请联系管理员");
                            }
                        });
                    }
                });
        });
        $("#bms-dept-edit-submit").click(function () {
            $editForm.submit();
        });
    }

    return {
        //初始主界面树
        initDeptMain: function () {
            initDeptMain();
        },
        //初始化部门修改页面
        initDeptProfile: function () {
            initDeptProfile();
        }
    }
}();
