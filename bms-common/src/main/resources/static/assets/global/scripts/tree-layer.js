!function ($) {

    var TreeLayer = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.init();
        this.bandingEvent();
    }


    TreeLayer.prototype.init = function () {
        this.options.id = this.$el.attr("id");
        this.showInput=this.$el.find("input[type='text']");
        this.hiddenInput=this.$el.find("input[type='hidden']");
    }

    // TreeLayer.prototype.setData = function () {
    //     this.options.id = this.$el.attr("id");
    // }


    TreeLayer.prototype.bandingEvent = function () {
        var t = this;
        var treeId=t.options["id"]+Math.floor(Math.random()*(10));

        t.$el.click(function () {
            var that = $(this);
            var $reqUrl = CommonUtils.getContextAll("/common/tree?treeUrl={0}&id={1}").format(t.options["treeUrl"], t.options["id"]);
            layer.open({
                type: 2,
                skin: "bms-parent-layer",
                title: "选择公司",
                area: ['300px', '400px'],
                fix: false, //不固定
                shadeClose: false,
                shade: 0.5,
                id: treeId,
                scrollbar: false,
                btn: ["确定", "取消"],
                content: $reqUrl,
                success: function (layero, index) {
                    var loginPage;
                    // js获取ifram中的元素
                    loginPage = window.frames["layui-layer-iframe" + index].document.getElementById("cpmg-login");

                    // jq获取iframe中的元素
                    // loginPage=$("#layui-layer-iframe"+index).contents().find("#cpmg-login");
                    // 如果会话失效，重定向只登陆页面
                    if (loginPage) {
                        window.location = CommonUtils.getContextAll("/login");
                    }
                },
                yes: function (index, layero) {
                    var treeNode = $("#"+treeId).find("iframe")[0].contentWindow.selectNode();
                    if (treeNode) {
                        t.showInput.val(treeNode["name"]);
                        t.hiddenInput.val(treeNode["code"]);
                    }
                    layer.close(index);
                }
            });
        });
    }

    $.fn.treeLayer = function (options) {
        return new TreeLayer(this, options);
        // return typeof value === 'undefined' ? this : value;
    }
}(jQuery);