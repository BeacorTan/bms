/**
 * 多级联动选择器
 * version:1.0.1
 */
(function () {
    $(".select-container").click(function () {
        var $that = $(this);
        var $input = $that.find("input");

        if ($that.attr("arrow") === "down") {
            $that.attr("arrow", "open");
        } else {
            $that.attr("arrow", "down");
            $that.next().remove();
            return;
        }

        var $scl = $("<div></div>");
        $scl.addClass("select-container-list");
        var $scl_model = $("<div class='select-container-model'></div>");
        $scl_model.on("click",function () {
            $that.attr("arrow", "down");
            $(this).parent().remove();
        });

        $scl_model.height($(window).height()+"px");
        $scl_model.width($(window).width()+"px");

        $scl.append($scl_model);
        var $sci = $("<div></div>");
        $sci.addClass("select-container-item");
        var $searchInput = $("<input type=\"text\" class=\"form-control\">");
        $searchInput.on("keypress", function (event) {
            // 回车
            if (event.which === 13) {
                $sci.find("ul").remove();
                bandingList($(this).val());
            }

        });
        $sci.append($searchInput);

        function bandingList(params) {
            var $u = $("<ul></ul>");
            var $l_1 = $("<li></li>");
            $l_1.attr("data-code", "");
            $l_1.html("全部");
            $l_1.on("click", function () {
                var $selectList = $(this).parents(".select-container-list");
                $selectList.siblings().find(".item-name").html($(this).html());
                $input.val($(this).attr("data-code"));
                $selectList.remove();
                $that.attr("arrow", "down");

            });
            $u.append($l_1);
            var $code = $that.attr("data-code");
            var $value = $that.attr("data-value");

            var $reqUrl = CommonUtils.getContextAll($that.attr("data-url"));
            if (params) {
                $reqUrl = $reqUrl + "?" + $value + "=" + params;
            }

            $.ajax({
                url: $reqUrl,
                type: "GET",
                success: function (result) {
                    var $dataList = result.dataList;
                    if ($dataList && $dataList.length > 0) {
                        for (let index of $dataList) {
                            var $l_2 = $("<li></li>");
                            $l_2.html(index[$value]);
                            $l_2.attr("data-code", index[$code]);
                            $l_2.on("click", function () {
                                var $selectList = $(this).parents(".select-container-list");
                                $selectList.siblings().find(".item-name").html($(this).html());
                                $selectList.remove();
                                $that.attr("arrow", "down");
                                $input.val($(this).attr("data-code"));
                            });

                            $u.append($l_2);
                            $sci.append($u);
                            $scl.append($sci);
                            $that.after($scl);
                        }
                    }
                }
            });
        }
        bandingList();

    });
})();


!function ($) {

    var SelectLinkage = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        this.init();
    };

    SelectLinkage.prototype.init = function () {
        this.initOptions();
        this.bandingEvent();
        if (this.childrenId) {
            this.childrenSelect.selectLinkage({
                "url": this.options.url,
                "isTopLevel": false,
                "isChildren": true
            });
//                                $("#select-dept").selectLinkage({"url": "/dept/linkage", "isTopLevel": true});
        }
    }

    SelectLinkage.prototype.initOptions = function () {

        var $showLabel = this.$el.find("span.select-linkage-show");
        this.showLabel = $showLabel;

        var $childrenId = this.$el.attr("children-id");
        if ($childrenId) {
            this.childrenId = $childrenId;
            this.childrenSelect = $("#" + $childrenId);
        }

        var $selectInput = this.$el.find("input[type=hidden]");
        this.selectInput = $selectInput;
    }

    SelectLinkage.prototype.bandingEvent = function () {

        var $that = this;
        var $searchInput = $("<input class='form-control'>");

        $that.showLabel.click(function (e) {
            var $showLabel = $(this);
            $showLabel.hide();
            $showLabel.parent().prepend($searchInput);
            $searchInput.focus();
            $that.searchInput = $searchInput;
            $searchInput.val($showLabel.text());
            $that.searchInput.on("keypress", function (event) {
                // 回车
                if (event.which === 13) {
                    $that.bandingData($(this).val());
                }
            });
            e.stopPropagation();//阻止事件冒泡
        });


        $that.$el.find("span.input-group-btn").click(function (e) {
            var $searchInput = this.searchInput;
            var params;
            if ($searchInput) {
                params = $searchInput.val();
            }
            $that.bandingData(params);
            e.stopPropagation();//阻止事件冒泡
        });
    }

    SelectLinkage.prototype.reset=function () {
        this.selectInput.val("");
        this.showLabel.text("");
    }

    SelectLinkage.prototype.bandingData = function (params) {

        var $that = this, $parentCode = $that.$el.data("parentCode");

        if ($that.options.isChildren === true && !$parentCode) {
            layer.msg("请选择上级组织");
            return;
        }

        if ($that.selectContainerList) {
            $that.selectContainerList.remove();
            $that.selectContainerList = undefined;
        }

        var $selectContainerList = $("<div></div>");
        $selectContainerList.addClass("select-container-list");
        var $selectContainerItem = $("<div></div>");
        $selectContainerItem.addClass("select-container-item");
        var $ul = $("<ul></ul>");

        var params = "?parentCode=" + ($parentCode ? $parentCode : "") + "&deptName=" + ( $that.searchInput ? $that.searchInput.val() : "");

        $.ajax({
            url: CommonUtils.getContextAll($that.options.url) + params,
            type: "GET",
            dataType: "json",
            success: function (result) {
                var data = result && result.dataList;
                getListData(data);
            }
        });

        function getListData(dt) {
            var $li;
            if (!dt || dt.length <= 0) {
                $li = $("<li>未匹配到数据</li>");
                $ul.append($li);
            } else {
                for (let index of dt) {
                    $li = $("<li></li>");
                    $li.attr("data-code", index["deptCode"]);
                    $li.html(index["deptName"]);
                    $li.click(function (e) {
                        $that.showLabel.show().prev().remove();
                        $that.searchInput = undefined;
                        $that.showLabel.text($(this).html());

                        var $currCode = $(this).attr("data-code");
                        $that.selectInput.val($currCode);
                        $selectContainerList.remove();
                        $that.selectContainerList = undefined;
                        if ($that.childrenSelect) {
                            $that.childrenSelect.selectLinkage("reset");
                            $that.childrenSelect.data("parentCode", $currCode);
                        }
                        e.stopPropagation();//阻止事件冒泡
                    });
                    $ul.append($li);
                }
            }
            $selectContainerItem.append($ul);
            $selectContainerList.append($selectContainerItem);
            $that.$el.after($selectContainerList);
            $that.selectContainerList = $selectContainerList
            //点击空白处，设置的弹框消失
            $(document).one("click", function (e) {
                if($selectContainerList){
                    $selectContainerList.remove();
                }
                console.log("document.click");
                e.stopPropagation();//阻止事件冒泡
            });
        }
    }

    var allowedMethods = [
        'getOptions',
        'getSelectData',
        'load', 'append', 'prepend', 'remove', 'removeAll',
        'insertRow', 'updateRow', 'updateCell', 'updateByUniqueId', 'removeByUniqueId',
        'getRowByUniqueId', 'showRow', 'hideRow', 'getRowsHidden',
        'mergeCells',
        'checkAll', 'uncheckAll',
        'check', 'uncheck',
        'checkBy', 'uncheckBy',
        'refresh',
        'reset',
        'resetWidth',
        'destroy',
        'showLoading', 'hideLoading',
        'showColumn', 'hideColumn', 'getHiddenColumns',
        'filterBy',
        'scrollTo',
        'getScrollPosition',
        'selectPage', 'prevPage', 'nextPage',
        'togglePagination',
        'toggleView',
        'refreshOptions',
        'resetSearch',
        'expandRow', 'collapseRow', 'expandAllRows', 'collapseAllRows'
    ];

    $.fn.selectLinkage = function (option) {
        var value, args = Array.prototype.slice.call(arguments, 1);
        this.each(function () {
            var $this = $(this), data = $this.data('select.linkage');
            // jQuery.extend() 函数用于将一个或多个对象的内容合并到目标对象。
            var options = $.extend({}, data, typeof option === 'object' && option);
            if (typeof option === 'string') {
                if ($.inArray(option, allowedMethods) < 0) {
                    throw new Error("Unknown method: " + option);
                }

                if (!data) {
                    return;
                }
                value = data[option].apply(data, args);
            }
            if (!data) {
                $this.data('select.linkage', (data = new SelectLinkage(this, options)));
            }
        });
        return typeof value === 'undefined' ? this : value;
    }
}(jQuery);