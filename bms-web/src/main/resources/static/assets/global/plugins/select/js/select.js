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

            var $reqUrl = CM_Components.getContextAll($that.attr("data-url"));
            if (params) {
                $reqUrl = $reqUrl + "?" + $value + "=" + params;
            }

            $.ajax({
                url: $reqUrl,
                type: "GET",
                success: function (result) {
//                                console.table(result.dataList);
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