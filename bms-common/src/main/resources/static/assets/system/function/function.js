/**
 * 资源管理(菜单、按钮等)模块js
 */
var BMS_Function = function () {


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



    var $functionTable = $("#bms-function-table");

    function initFunctionIcons() {

        function iconClick(className) {
            var iconId = "#" + $("#function_functionIconID").val();
            var $funIcon = $(iconId, parent.document);
            $funIcon.find("span").text(className);
            $funIcon.find("i").attr("class", className);
            $funIcon.find("input").val(className);
        }

        // 图标单击、双击事件
        $("div.function-icon").on({
            click: function () {
                console.log("click");
                var icon = $(this);
                icon.addClass("cmmp-function-icon-hover");
                var className = icon.find("i").attr("class");
                iconClick(className);
                var changeIcon = $("#cmmg_function_icon_ul").find("[isChange=true]");
                if (changeIcon) {
                    icon.attr("isChange", "false");
                    changeIcon.removeClass("cmmp-function-icon-hover");
                }
                icon.attr("isChange", "true");
            }, dblclick: function () {
                console.log("dblclick");
                var className = $(this).find("i").attr("class");
                iconClick(className);
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再执行关闭
            }
        });
    }

    return {
        initFunctionIcons: function () {
            initFunctionIcons();
        }
    }
}();