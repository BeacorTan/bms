/**
 * 组织架构管理模块js
 */
var Dep = function () {
    //部门主页面
    var $depTree;//树定义
    var depId = "depTree";//树节点id
    var depDataAjaxUrl = "dep/json/list";//获取部门json数据地址
    var depData;//部门josn数据
    var depInfoAjaxHtml = "dep/info";//获取部门信息
    var rightContentClass = "todo-content";
    var deleteDepAjaxUrl = "dep/";
    //增加部门属性
    var $addDepForm = $("#depAddForm");//增加部门表单
    var addDepAjaxUrl = CM_Components.getContextPath() + "/dep";
    var $addDepBtn = $("#addDepBtn");//增加部门按钮
    //修改部门属性
    var $updateDepForm = $("#depUpdateForm");
    var updateDepAjaxUrl = CM_Components.getContextPath() + "/dep";
    var $updateDepBtn = $("#updateDepBtn");

    var initDepData = function () {
        $.ajax({
            type: "GET",
            cache: false,
            url: depDataAjaxUrl,
            success: function (responseJson) {
                if (responseJson.success) {//成功
                    depData = responseJson.obj;
                    initDepTree();//初始化树列表
                } else {
                    CM_Components.bootstrapSweetAlert("", responseJson.msg, "error");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                CM_Components.bootstrapSweetAlert("", "获取部门数据出错,系统错误!!!", "error");
            }
        });
    }

    var  initRefreshBtn=function(){
        $("cmmg_dept_refresh").click(function () {
            $depTree.refresh();
        });
    }

    var addHoverDom = function (treeId, treeNode) {

        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) btn.bind("click", function () {
            CM_Components.layerOpen("增加部门", '800px', '350px', "dep/add/view?depId=" + treeNode.id)
        });
    };
    var removeHoverDom = function (treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
    };

    var onZtreeClick = function (event, treeId, treeNode) {
        CM_Components.getPageData(depInfoAjaxHtml + "/" + treeNode.id, null, rightContentClass);
    }

    var onZtreeEditorClick = function (treeId, treeNode) {
        CM_Components.layerOpen("修改部门", '800px', '350px', "dep/update/view?depId=" + treeNode.id);
        return false;
    }
    var onZTreeRemoveClick = function (treeId, treeNode) {
        if (null != treeNode.children && treeNode.children.length > 0) {
            CM_Components.bootstrapSweetAlert("", "不能删除父节点", "error");
            return false;
        }
        swal({
                title: "确定删除 " + treeNode.depName + "?",
                text: treeNode.depName + " 下面的人员将会一起删除",
                type: "warning",
                showCancelButton: true,
                confirmButtonClass: "btn-danger",
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                // allowOutsideClick:true,
                closeOnConfirm: true,
                closeOnCancel: false
            },
            function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        type: "DELETE",
                        cache: false,
                        url: deleteDepAjaxUrl + "?id=" + treeNode.id,
                        success: function (responseJson) {
                            if (responseJson.success == true) {//返回true
                                $depTree.removeNode(treeNode);//动态删除该节点
                                CM_Components.layerMsg(responseJson.msg);
                            }
                            if (responseJson.success == false) {//返回false
                                CM_Components.layerMsg(responseJson.msg);
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            CM_Components.layerMsg("系统错误!!!");
                        }
                    });
                } else {
                    swal("", "已经取消了当前操作", "error");
                }
            });
        return false;
    }

    var initDepTree = function () {
        //初始化树的配置参数
        var setting = {
            view: {
                showLine: true,
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId",
                    rootPId: "1"
                },
                key: {
                    name: "depName"
                }
            },
            check: {
                enable: false,//在节点前显示checkbox
                chkboxType: {"Y": "", "N": ""}//父节点和字节不级联
            },
            edit: {
                enable: true
            },
            callback: {
                onClick: onZtreeClick,
                beforeEditName: onZtreeEditorClick,
                beforeRemove: onZTreeRemoveClick
            }
        };
        if (null != $depTree && "" != $depTree && undefined != $depTree) {//不为空

        } else {
            $depTree = $.fn.zTree.init($("#" + depId), setting, depData);
        }
        var allNodes = $depTree.getNodes();
        if (allNodes.length > 0) {//查询出来的是有数据的
            if (null != allNodes[0].children && allNodes[0].children.length > 0) $depTree.expandNode(allNodes[0]);//展开第一个节点
            CM_Components.getPageData(depInfoAjaxHtml + "/" + allNodes[0].id, null, rightContentClass); //点击第一个节点
        }
    }

    var initDepAddBootStrapValidate = function () {
        $addDepForm.bootstrapValidator({
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
                    closeOnCancel: false
                },
                function (isConfirm) {
                    if (isConfirm) {
                        depAjax(addDepAjaxUrl, $addDepForm.serialize(), "POST");
                    } else {
                        swal("", "已经取消了当前操作", "error");
                    }
                });
        });
    }

    var depAjax = function (ajaxUrl, data, method) {
        $.ajax({
            type: method,
            cache: false,
            url: ajaxUrl,
            data: data,
            success: function (responseJson) {
                if (responseJson.success == true) {//返回true
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                    CM_Components.layerMsg(responseJson.msg);
                    if (method == "POST") {
                        (parent.Dep).addDepNode(responseJson.obj);//调用添加方法
                    }
                }
                if (responseJson.success == false) {//返回false
                    CM_Components.layerMsg(responseJson.msg);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                CM_Components.layerMsg("系统错误!!!");
            }
        });
    }

    var initDepUpdateBootStrapValidate = function () {
        $updateDepForm.bootstrapValidator({
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
                    closeOnConfirm: false,
                    closeOnCancel: false
                },
                function (isConfirm) {
                    if (isConfirm) {
                        depAjax(updateDepAjaxUrl, $updateDepForm.serialize(), "PUT");

                    } else {
                        swal("", "已经取消了当前操作", "error");
                    }
                });
        });
    }
    /**
     * 增加页面调用父页面方法,给zTree动态添加数据
     * @param depNode
     */
    var addDepNode = function (depNode) {
        var pNode = $depTree.getNodeByParam("id", depNode.parentId, null);
        $depTree.addNodes(pNode, depNode);
    }
    /**
     * 修改页面调用父页面方法,给zTree动态修改数据
     * @param depNode
     */
    // var updateDepNode = function (depNode) {
    //     $depTree.updateNode(depNode);
    // }
    return {
        //初始主界面树
        initDepMain: function () {
            initDepData();
            initRefreshBtn();
        },
        //初始化部门增加页面
        initDepAdd: function () {
            initDepAddBootStrapValidate();
            $addDepBtn.on("click", function () {
                $addDepForm.submit();
            });
        },
        //初始化部门修改页面
        initDepUpdate: function () {
            initDepUpdateBootStrapValidate();
            $updateDepBtn.on("click", function () {
                $updateDepForm.submit();
            });
        },
        //增加页面调用父页面方法
        addDepNode: function (depNode) {
            addDepNode(depNode);
        }
        //修改页面调用父页面方法
        // updateDepNode:function (depNode) {
        //     updateDepNode(depNode);
        // }
    }
}();
