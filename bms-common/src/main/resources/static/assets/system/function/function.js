//@ sourceURL=function.js
/**
 * 资源管理(菜单、按钮等)模块js
 */
var BMS_Function = function () {

    // var b=function(){a.call(this); return $.extend(this,{});}
    var $searchForm = $("#bms-function-search-form");


    //
    // var initFunctionAddBootStrapValidate = function () {
    //     $addFunctionForm.bootstrapValidator({
    //         fields: {
    //             funName: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '资源名称不能为空'
    //                     }
    //                 }
    //             },
    //             funCode: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '资源编码不能为空'
    //                     }
    //                 }
    //             },
    //             type: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '资源类型不能为空'
    //                     }
    //                 }
    //             },
    //             url: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '资源地址不能为空'
    //                     }
    //                 }
    //             },
    //             icon: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '资源图标不能为空'
    //                     }
    //                 }
    //             },
    //             orderNumber: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '排序不能为空'
    //                     }
    //                 }
    //             }
    //         }
    //     });
    // }
    //
    // var functionAjax = function (ajaxUrl, data, method) {
    //     $.ajax({
    //         type: method,
    //         cache: false,
    //         url: ajaxUrl,
    //         data: data,
    //         success: function (responseJson) {
    //             if (responseJson.success == true) {//返回true
    //                 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    //                 parent.layer.close(index);
    //                 CM_Components.layerMsg(responseJson.msg);
    //                 if (method == "POST") {
    //                     (parent.CM_Function).addFunctionNode(responseJson.obj);//调用添加方法
    //                 }
    //             }
    //             if (responseJson.success == false) {//返回false
    //                 CM_Components.layerMsg(responseJson.msg);
    //             }
    //         },
    //         error: function (XMLHttpRequest, textStatus, errorThrown) {
    //             CM_Components.layerMsg("系统错误!!!");
    //         }
    //     });
    // }

    // var initFunctionUpdateBootStrapValidate = function () {
    //     $updateFunctionForm.bootstrapValidator({
    //         fields: {
    //             funName: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '资源名称不能为空'
    //                     }
    //                 }
    //             },
    //             funCode: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '资源编码不能为空'
    //                     }
    //                 }
    //             },
    //             type: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '资源类型不能为空'
    //                     }
    //                 }
    //             },
    //             url: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '资源地址不能为空'
    //                     }
    //                 }
    //             },
    //             icon: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '资源图标不能为空'
    //                     }
    //                 }
    //             },
    //             orderNumber: {
    //                 validators: {
    //                     notEmpty: {
    //                         message: '排序不能为空'
    //                     }
    //                 }
    //             }
    //         }
    //     }).on("success.form.bv", function (e) {
    //         swal({
    //                 title: "确定提交表单?",
    //                 text: "",
    //                 type: "warning",
    //                 showCancelButton: true,
    //                 confirmButtonClass: "btn-danger",
    //                 confirmButtonText: "确定",
    //                 cancelButtonText: "取消",
    //                 closeOnConfirm: false,
    //                 closeOnCancel: false
    //             },
    //             function (isConfirm) {
    //                 if (isConfirm) {
    //                     functionAjax(updateFunctionAjaxUrl, $updateFunctionForm.serialize(), "PUT");
    //                 } else {
    //                     swal("", "已经取消了当前操作", "error");
    //                 }
    //             });
    //     });
    // }


    function init(btnId) {
        initTable();
        initButton(btnId);
    }

    var $functionTable = $("#bms-function-table");

    function initTable() {
        CM_Components.initBootStrapTable($functionTable, CM_Components.getContextAll("/function/query"));
    }

    function initButton(btnId) {

        $("#bms-function-search-btn").click(function () {
            $functionTable.bootstrapTable()
            $functionTable.bootstrapTable('refresh', {
                query: CommonUtils.getFormData("bms-function-search-form")
            });
        });

        $("#" + btnId).find("button").each(function () {
            var $that=$(this);
            $that.click(function () {
                CommonUtils.addTab("editFunction","编辑菜单",CM_Components.getContextAll($that.attr("data-url")));
            });

        });
    }
    return {
        init: function (btnId) {
            init(btnId);
        }
    }
}();