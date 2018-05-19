/**
 * 角色模块
 */
var CPMG_ROLE = function () {
    var $cpmg_role_Table = $("#cpmg_role_Table");
    var tableAjaxUrl = "role/page/list";
    var cpmg_role_TableColum = [{checkbox: true}
        , {title: 'id', field: 'id', align: 'center', valign: 'middle', visible: false}
        , {title: '角色名称', field: 'roleName', align: 'center', valign: 'middle'}
        , {title: '角色编码', field: 'roleCode', align: 'center', valign: 'middle'}
        , {title: '权限设置', field: 'gender', align: 'center', valign: 'middle'}
        , {title: '说明', field: 'remarks', align: 'center'}];

    var roleMainEventHandler = function () {
        $("#cpmg_role_addView").on("click", function () {
            // id, title, tabUrl
            CommonUtils.addTab("role_profile", "新增角色", CM_Components.getContextAll("/role/add/view/insert"))
        });
        // 查询
        $("#cpmg_role_searchBtn").on("click", function () {
            $cpmg_role_Table.bootstrapTable(
                "refresh",
                {
                    url: tableAjaxUrl,
                    query: CommonUtils.getFormData("cpmg_role_searchFormId")
                }
            );
        });

        //绑定修改用户弹出页面的按钮事件
        $("#cpmg_role_updateViewBtn").on("click", function () {
            var rows = CM_Components.getTableSelections($cpmg_role_Table);
            if (rows.length == 0) {
                CM_Components.bootstrapSweetAlert("", "请选择一个用户", "error");
                return;
            }
            if (rows.length >= 2) {
                CM_Components.bootstrapSweetAlert("", "不能选择多个用户", "error");
                return;
            }
            //查看用户的基本信息
            //CM_Components.layerOpen("配置角色", '800px', '400px', "role/add/view/" + rows[0].id);
            CommonUtils.addTab("role_profile", "编辑角色", CM_Components.getContextAll("/role/add/view/"+rows[0].id))
        });
        //绑定删除按钮事件
        $("#cpmg_role_deleteBtn").on("click", function () {
            CM_Components.ajaxDeleteTable("role/batch", $cpmg_role_Table);
        });
    }


    var roleAddBtnEventHandler = function () {
        $("#cpmg_role_addBtn").on("click", function () {

                var roleID = $("#cpmg_role_addForm").find('input[name=id]').val();
                // 功能菜单
                var treeObj = $.fn.zTree.getZTreeObj("cpmg_role_functionTree");
                var nodes = treeObj.getCheckedNodes(true);
                var functionCodes;
                if (nodes) {
                    functionCodes = [];
                    var functionCodeSize = nodes.length;
                    for (var i = 0; i < functionCodeSize; i++) {
                        functionCodes.push(nodes[i].functionCode);
                    }
                }

                // 数据权限
                var params = CommonUtils.getFormData("cpmg_role_addForm");
                params["functionCodes"] = functionCodes;
                // 数据权限
                var deptTree = $.fn.zTree.getZTreeObj("role_dept_Tree");
                var deptTreeNodes = deptTree.getCheckedNodes(true);
                var authData = [];
                if (deptTreeNodes) {
                    var deptTreeNodesSize = deptTreeNodes.length;
                    for (var i = 0; i < deptTreeNodesSize; i++) {
                        authData.push(deptTreeNodes[i]);
                    }
                }
                params["authData"] = authData;

                var url;
                if (roleID) {
                    // 修改
                    url = "/role/roleEdit";
                } else {
                    url = "/role/roleAdd";
                }

                $.ajax({
                    url: CM_Components.getContextAll(url),
                    type: "POST",
                    contentType: 'application/json',
                    data: JSON.stringify(params),
                    success: function (result) {
                        CM_Components.layerMsg("编辑成功");
                        CommonUtils.closeTab("role_profile");
                    }
                });
            }
        );
    }

    var initFunctionData = function (rolCode) {
        $.ajax({
            type: "GET",
            cache: false,
            url: CM_Components.getContextAll("/function/json/list?roleCode=" + rolCode),
            success: function (responseJson) {
                if (responseJson.success) {//成功
                    var functionData = responseJson.obj;
                    initFunctionTree(functionData);//初始化树列表
                } else {
                    CM_Components.layerMsg(responseJson.msg);
                }
            },
            error: function (errorMsg) {
                CM_Components.layerMsg(errorMsg);
            }
        });
    }
    var initFunctionTree = function (functionData) {
        var setting = {
            view: {
                showLine: true,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    functionCode: "functionCode"
                }
            },
            check: {
                enable: true,
                chkboxType: {"Y": "p", "N": "p"}
            }
        };
        $.fn.zTree.init($("#cpmg_role_functionTree"), setting, functionData);
    }

    return {
        //初始化用户管理的主页面:用户列表
        initRoleMain: function () {
            CM_Components.initBootStrapTable($cpmg_role_Table, tableAjaxUrl, cpmg_role_TableColum);
            roleMainEventHandler();
        },
        initRoleAdd: function (roleCode) {
            roleAddBtnEventHandler();
            initFunctionData(roleCode);
        }
    }
}
();
