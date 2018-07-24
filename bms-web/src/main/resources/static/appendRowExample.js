/**
 * bootstrap添加行-example
 */
jQuery(document).ready(function () {

    var $borrowerImportForm = $("#authms-borrower-import-form");

    // 头部按钮
    $("#bms-borrower-btn").find("button").on("click", function () {
        var $that = $(this);
        var $buttonType = $that.attr("data-type");
        switch ($buttonType) {
            case "import":
                if ($borrowerImportForm.data("show")) {
                    $borrowerImportForm.hide();
                    $borrowerImportForm.data("show", false);
                } else {
                    $borrowerImportForm.show();
                    $borrowerImportForm.data("show", true);
                }
                break;
            default:
                console.error("未匹配到type");
                break;
        }
    });

    $borrowerImportForm.find("button").click(function () {
        var fileForm = $borrowerImportForm.find("[type=file]");
        var fileObj = fileForm.get(0).files[0];
        if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
            fileForm.focus();
            BmsComponents.layerMsg("请选择您要提交的文件");
            return;
        }
        if (!fileObj["name"].endsWith(".xls") && !fileObj["name"].endsWith(".xlsx")) {
            BmsComponents.layerMsg("文件格式错误，请选择excel表格文件");
            return;
        }
        var formFile = new FormData();
        formFile.append("file", fileObj); //加入文件对象
        $.ajax({
            url: CommonUtils.getContextAll($borrowerImportForm.attr("action")),
            data: formFile,
            type: "Post",
            dataType: "json",
            cache: false,//上传文件无需缓存
            processData: false,//用于对data参数进行序列化处理 这里必须false
            contentType: false, //必须
            success: function (res) {
                BmsComponents.layerMsg(res["msg"]);
                if (res["success"]) {
                    fileForm.val("");
                } else {
                    console.error(res["msg"]);
                }
            },
            error: function (errorMsg) {
                fileForm.val("");
                BmsComponents.layerMsg("导入失败！");
                console.error(errorMsg);
            }
        });
    });

    var $borrowerTable = $("#bms-borrower-table");

    $borrowerTable.bootstrapTable({
        method: 'get',
        url: CommonUtils.getContextAll('/borrower/page/list'),
        cache: false,
        dataType: "json",
        striped: true,
        sortable: true,
        pagination: true,	//在表格底部显示分页工具栏
        pageSize: 10,
        pageNumber: 1,
        pageList: [10, 15, 20],
        idField: "id",  //标识哪个字段为id主键
        showToggle: false,   //名片格式
        cardView: false,//设置为True时显示名片（card）布局
        singleSelect: false,//复选框只能选择一条记录
        search: false,//是否显示右上角的搜索框
        // clickToSelect: true,//点击行即可选中单选/复选框
        sidePagination: "server",//表格分页的位置
        queryParamsType: "", //参数格式,发送标准的RESTFul类型的参数请求
        strictSearch: true,
        showColumns: false,     //是否显示所有的列
        showRefresh: false,     //是否显示刷新按钮
        minimumCountColumns: 2,    //最少允许的列数
        checkbox: true,
        responseHandler: function (res) {
            return {
                "rows": res.dataList,
                "total": res.total
            };
        },
        silent: true,  //刷新事件必须设置
        formatNoMatches: function () {  //没有匹配的结果
            return '无符合条件的记录';
        },
        rowStyle: function (row, index) {
            return {classes: "cursorHand"}
        },
        onClickRow: addRow
    });

    var $appendRow;

    function addRow(row, $element) {

        if (!$appendRow) {
            $appendRow = {};
        }
        var id = "borrower-" + row["id"];
        var flag = false;
        for (var r in $appendRow) {
            if (r == id) {
                if ($appendRow[r] == "show") {
                    $("#" + r).hide();
                    $appendRow[r] = "hide";
                } else {
                    $("#" + id).show();
                    $appendRow[r] = "show";
                }
                flag = true;
            } else {
                if ($appendRow[r] == "show") {
                    $("#" + r).hide();
                    $appendRow[r] = "hide";
                }

            }
        }
        if (flag) {
            console.log("已存在该【" + id + "】的数据");
            return;
        }


        var content = [];
        content.push("<tr id='{0}' class='borrower-append-row'>".format(id));

        content.push("<td colspan='2'>");
        content.push("<p><label>证件地址：</label>{0}</p>".format(row["idCardAddress"] || ""));
        content.push("<p><label>联系地址：</label>{0}</p>".format(row["address"] || ""));
        content.push("</td>");

        content.push("<td colspan='2'>");
        content.push("<p><label>手机：</label>{0}</p>".format(row["mobile"] || ""));
        content.push("<p><label>邮箱：</label>{0}</p>".format(row["email"] || ""));
        content.push("<p><label>合同金额：</label>{0}</p>".format(row["contractAmt"] || ""));
        content.push("<p><label>咨询费率：</label>{0}</p>".format(row["consultationRate"] || ""));
        content.push("</td>");


        content.push("<td>");
        var applyDate = row["applyDate"] || "";
        if (applyDate) {
            applyDate = new Date(applyDate).format("yyyy-MM-dd");
        }
        var debtTransferDate = row["debtTransferDate"] || "";
        if (debtTransferDate) {
            debtTransferDate = new Date(debtTransferDate).format("yyyy-MM-dd");
        }
        content.push("<p><label>借款日期：</label>{0}</p>".format(applyDate));
        content.push("<p><label>债权转让日：</label>{0}</p>".format(debtTransferDate));
        content.push("</td>");


        content.push("<td  colspan='5'>");

        content.push("<p><label>电子签章：</label>{0}；".format(isComplete(row["isDzqz"])));
        content.push("<label class='borrower-label-left'>借款协议：</label>{0}；".format(isComplete(row["isJkxy"])));
        content.push("<label class='borrower-label-left'>补充协议：</label>{0}；".format(isComplete(row["isJkbcxy"])));
        content.push("</p>");

        content.push("<p>");
        content.push("<label>借款咨询服务协议：</label>{0}；".format(isComplete(row["isJkzxfwxy"])));
        content.push("<label class='borrower-label-left'>转款凭证：</label>{0}；".format(isComplete(row["isZkpz"])));
        content.push("<label class='borrower-label-left'>签章授权：</label>{0}；".format(isComplete(row["isQzsqs"])));
        content.push("</p>");

        content.push("<p>");
        content.push("<label>服务费说明：</label>{0}；".format(isComplete(row["isFwfsm"])));
        content.push("<label class='borrower-label-left'>催收记录：</label>{0}；".format(isComplete(row["isCsjl"])));
        content.push("<label class='borrower-label-left'>还款记录：</label>{0}；".format(isComplete(row["isHkjl"])));
        content.push("</p>");

        content.push("<p>");
        content.push("<label>身份证：</label>{0}；".format(isComplete(row["isSfz"])));
        content.push("<label class='borrower-label-left'>短信：</label>{0}；".format(isComplete(row["isSms"])));
        content.push("</p>");


        content.push("</td>");

        content.push("</tr>");
        $element.after(content.join(""));
        $appendRow[id] = "show";
    }

    function isComplete(v) {
        if (v == 1) {
            return "<span class='complete'>完成</span>";
        } else {
            return "<span class='no-complete'>未完成</span>";
        }
    }


    $("#bms-borrower-search-btn").click(function () {
        $borrowerTable.bootstrapTable("refresh", {
            query: CommonUtils.getFormData("bms-borrower-search-form")
        });
        $appendRow=undefined;
    });
});