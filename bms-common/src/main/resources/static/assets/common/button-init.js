!function ($) {

    var BmsButton = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.init();
        this.bandingEvent();
    }

    BmsButton.prototype.init = function () {
        this.$el.bootstrapTable = $("#" + this.options["bootstrapTable"]);

    }

    /**
     * tabTitle:打开tab的标题
     * bootstrapTable: bootstrapTable id
     * @type {{bootstrapTable: string, tabTitle: string}}
     */
    BmsButton.DEFAULTS = {
        "tabTitle": "编辑数据",
        "bootstrapTable": ""
    }

    BmsButton.prototype.bandingEvent = function () {
        var btn = this.$el;
        var $t = this;
        var options = this.options;

        this.$el.find("button").each(function () {
            var $that = $(this);
            $that.click(function () {
                var $this = $(this);
                var t = $this.attr("data-type");
                var reqUrl = $this.attr("data-url");
                switch (t) {
                    case "add":
                        // 新增
                        var tabId = "add" + new Date().getTime().toString();
                        CommonUtils.addTab(tabId, options["tabTitle"], CommonUtils.getContextAll(reqUrl) + "?id=" + tabId);
                        break;
                    case "edit":
                        // 修改
                        var rows = btn.bootstrapTable.bootstrapTable('getSelections');
                        if (rows.length == 0) {
                            layer.msg("请选择一行数据");
                            return;
                        }
                        if (rows.length >= 2) {
                            layer.msg("只能选择一行数据");
                            return;
                        }
                        var rowId = rows[0]["id"];
                        var tabUrl = CommonUtils.getContextAll(reqUrl) + "?id=" + rowId;
                        CommonUtils.addTab(rowId, options["tabTitle"], tabUrl);
                        break;
                    case "del":
                        $t.del(reqUrl);
                        break;
                    default:
                        console.error("未匹配到data-type");
                }
            });
        });
    }

    BmsButton.prototype.del = function (reqUrl) {
        var btn = this.$el;
        var rows = btn.bootstrapTable.bootstrapTable('getSelections');
        if (!rows || rows.length == 0) {
            layer.msg("请选择要删除的数据");
            return;
        }
        swal({
                title: "确定删除?",
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
                    var ids = [];
                    $.each(rows, function (i, obj) {
                        ids.push(obj.id);
                    });
                    $.ajax({
                        type: 'POST',
                        url: CommonUtils.getContextAll(reqUrl),
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        dataType: "json",
                        success: function (res) {
                            var msg=res["msg"];
                            CM_Components.layerMsg(msg);
                            if (res["success"]) {
                                btn.bootstrapTable.bootstrapTable('refresh');
                            } else {
                                console.error(msg);
                            }
                        },
                        error: function (response) {
                            CM_Components.layerMsg(response);
                        }
                    });
                }
            });
    }

    /**
     * 本函数主要用于绑定管理页面主页头部按钮事件
     * 使用方法请参考：function_main.html
     * @param options
     * @returns {BmsButton}
     */
    $.fn.buttonInit = function (options) {
        options = options || BmsButton.DEFAULTS;
        return new BmsButton(this, options);
    }


    var BmsFormButton = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.init();
        this.bandingEvent();
    }

    BmsFormButton.prototype.bandingEvent = function () {
        var options = this.options;
        var $this = this.$el;
        this.$el.find("a").each(function () {
            var $that = $(this);
            $that.click(function () {
                var t = $(this).attr("data-type");
                switch (t) {
                    case "reset":
                        CommonUtils.reset(options["searchForm"]);
                        break;
                    case "search":
                        $this.bootstrapTable.bootstrapTable('refresh', {
                            query: CommonUtils.getFormData(options["searchForm"])
                        });
                        break;
                    case "close":
                        CommonUtils.closeTab(options["tabId"]);
                        break;
                    case "submit":
                        var dt = CommonUtils.getFormData(options["editForm"]);
                        if(options["supplyParams"]){
                            dt=$.extend(dt,options["supplyParams"].apply($this));
                        }
                        $.ajax({
                            url: CommonUtils.getContextAll($(this).attr("data-url")),
                            type: "POST",
                            contentType: 'application/json',
                            data: JSON.stringify(dt),
                            success: function (res) {
                                var msg = res["msg"];
                                layer.msg(msg);
                                if (res["success"]) {
                                    CommonUtils.closeTab(options["tabId"]);
                                    $this.bootstrapTable.bootstrapTable('refresh');
                                } else {
                                    console.error(msg);
                                }
                            }
                        });
                        break;

                    default:
                        console.error("未匹配到data-type");
                }
            });
        });

    }

    BmsFormButton.prototype.init = function () {
        this.$el.bootstrapTable = $("#" + this.options["bootstrapTable"]);
        this.options["searchForm"] = this.options["searchForm"] || this.options["editForm"];
        if (this.options["bootstrapTableInit"]) {
            CM_Components.initBootStrapTable(this.$el.bootstrapTable, CommonUtils.getContextAll(this.options["searchUrl"]));
        }

    }


    /**
     * bootstrapTable: bootstrapTable id
     * bootstrapTableInit:是否初始化 bootstrapTable
     * searchForm:查询form id
     * searchUrl：查询路径 例：/function/query
     * editForm：编辑form
     * supplyParams：补充参数 此属性值为：json或函数（返回值为json）
     * tabId:tab id
     * @type {{bootstrapTable: string, searchForm: string, searchUrl: string,tabId:string}}
     */
    BmsFormButton.DEFAULTS = {
        "bootstrapTable": "",
        "bootstrapTableInit": false,
        "searchForm": "",
        "searchUrl": "",
        "editForm": "",
        "supplyParams": "",
        "tabId": ""
    }

    /**
     * 本函数主要用于（查询||编辑）form表单button按钮的绑定事件
     * 使用方法请参考：function_profile.html||function_main.html
     * @param options
     * @returns {BmsFormButton}
     */
    $.fn.formButtonInit = function (options) {
        options = options || BmsFormButton.DEFAULTS;
        return new BmsFormButton(this, options);
    }
}(jQuery);