/**
 * 资源管理(菜单、按钮等)模块js
 */
var BmsFunction = function () {

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
                var icon = $(this);
                icon.addClass("bms-function-icon-hover");
                var className = icon.find("i").attr("class");
                iconClick(className);
                var changeIcon = $("#cmmg_function_icon_ul").find("[isChange=true]");
                if (changeIcon) {
                    icon.attr("isChange", "false");
                    changeIcon.removeClass("bms-function-icon-hover");
                }
                icon.attr("isChange", "true");
            }, dblclick: function () {
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