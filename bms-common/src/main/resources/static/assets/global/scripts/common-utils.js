var CommonUtils = function () {

    /**
     * 根据传入form表单的id，组装json对象
     * 注意：此方法依赖jquery.js，使用示例：CommonUtils.getFormData("formID");
     * @param form form表单ID
     * @returns {{}}  按照form表单中name，组装成以name名为属性名的数据对象
     */
    var getFormData = function (form) {
        var formData = {};
        var name;
        var currentInput;
        var inputType;

        var list = $("#" + form + " input");
        for (var i = 0; i < list.length; i++) {
            currentInput = $(list[i]);
            name = currentInput.attr("name");
            if (!name) {
                continue;
            }
            inputType = currentInput.attr("type");
            // 复选框
            if (inputType == "checkbox") {
                if (!currentInput.is(":checked")) {
                    continue;
                }
                if (!formData[name]) {
                    formData[name] = [];
                }
                formData[name].push(currentInput.val());
                continue;
            }
            // 单选
            if (inputType == "radio" && !currentInput.is(":checked")) {
                continue;
            }
            formData[name] = currentInput.val();
        }

        // 下拉框
        var currDom;
        $("#" + form + " select").each(function () {
            currDom = $(this);
            formData[currDom.attr("name")] = currDom.val();
        });

        // 文本域
        $("#" + form + " textarea").each(function () {
            currDom = $(this);
            formData[currDom.attr("name")] = currDom.val();
        });

        return formData;
    }


    var getRequestParams = function (formId) {
        var params = getFormData(formId);
        var result = "?";
        for (var var1 in params) {
            if (params[var1] !== null && params[var1] !== '' && params[var1] !== 'undefined') {
                result += ( var1 + "=" + params[var1] + "&");
            }
        }
        if (result) {
            result = result.substring(0, result.length - 1)
        }
        return result;
    }

    /**
     * 重置表单
     * @param formID 表单ID
     */
    var formReset = function (formID) {
        var form = $("#" + formID);
        if (!form) {
            return;
        }
        var formInput = form.find("input[type=text]");
        if (formInput) {
            formInput.val("");
        }
        var formRadio = form.find("input[type=radio]");
        if (formRadio) {
            formRadio.attr("checked", false);
        }
        var formCheckbox = form.find("input[type=checkbox]");
        if (formCheckbox) {
            formCheckbox.attr("checked", false);
        }
        var formSelect = form.find("select");
        if (formSelect && formSelect.length > 0) {
            formSelect.each(function () {
                $(this).find("option")[0].selected = true;
            });
        }
        /*var formSelect = form.find("select option");
        if (formSelect && formSelect.length > 0) {
            formSelect.each(fun);
            formSelect[0].selected = true;
        }*/
        var formTextarea = form.find("textarea");
        if (formTextarea) {
            formTextarea.text("");
            formTextarea.val("");
        }
    }


    /**
     *
     * @param title  标题
     * @param tabUrl 页面加载路径
     */
    var addTab = function (id, title, tabUrl) {
        $.addtabs.add({
            'id': id,
            /*'title': "客户<span style='font-weight: bolder;color: #27a4b0;'>" + title + "</span>",*/
            'title': title,
            'url': tabUrl,
            'ajax': true
        });
    }


    var closeTab = function (id) {
        $.addtabs.close({"id": id});
    }

    var updateTab = function (bootstrapTable, tabId, reqUrl, tabTitle) {
        var rows = bootstrapTable.bootstrapTable('getSelections');
        if (!rows || rows.length == 0) {
            CM_Components.layerMsg("请选择您要编辑的数据");
            return;
        }
        if (rows.length > 1) {
            CM_Components.layerMsg("一次只能修改一行数据");
            return;
        }
        var $reqUrl = reqUrl + "?id=" + rows[0].id;
        CommonUtils.addTab(tabId, tabTitle, $reqUrl);
    }

    return {
        getFormData: function (formID) {
            return getFormData(formID);
        },
        addTab: function (id, title, tabUrl) {
            addTab(id, title, tabUrl);
        },
        closeTab: function (id) {
            closeTab("tab_" + id);
        },
        // 表单重置
        reset: function (formID) {
            formReset(formID);
        },
        getRequestParams: function (formId) {
            return getRequestParams(formId);
        },
        updateTab: function (bootstrapTable, tabId, reqUrl,tabTitle) {
            return updateTab(bootstrapTable, tabId, reqUrl,tabTitle);
        }

    };
}();
