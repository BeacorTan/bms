// bootstrap table  点击行新增一行示例
jQuery(document).ready(function () {

    var $borrowerTable = $("#bms-borrower-table");

    $borrowerTable.bootstrapTable({
        method: 'get',
        url: CommonUtils.getContextAll('/**/page/list'),
        cache: false,
        dataType: "json",
        striped: true,
        sortable: true,
        pagination: true,
        pageSize: 10,
        pageNumber: 1,
        pageList: [10, 15, 20],
        idField: "id",
        showToggle: false,
        cardView: false,
        singleSelect: false,
        search: false,
        sidePagination: "server",
        queryParamsType: "",
        strictSearch: true,
        showColumns: false,
        showRefresh: false,
        minimumCountColumns: 2,
        checkbox: true,
        responseHandler: function (res) {
            return {
                "rows": res.dataList,
                "total": res.total
            };
        },
        silent: true,
        formatNoMatches: function () {
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
            //console.log("已存在该【" + id + "】的数据");
            return;
        }


        var content = [];
        content.push("<tr id='{0}' class='borrower-append-row'>".format(id));

        content.push("<td colspan='2'>");
        content.push("<p><label>****：</label>{0}</p>".format(row["idCardAddress"] || ""));
        content.push("<p><label>****：</label>{0}</p>".format(row["address"] || ""));
        content.push("</td>");

        content.push("<td colspan='2'>");
        content.push("<p><label>****：</label>{0}</p>".format(row["mobile"] || ""));
        content.push("<p><label>****：</label>{0}</p>".format(row["email"] || ""));
        content.push("<p><label>****：</label>{0}</p>".format(row["contractAmt"] || ""));
        content.push("<p><label>****：</label>{0}</p>".format(row["consultationRate"] || ""));
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
        content.push("<p><label>****：</label>{0}</p>".format(applyDate));
        content.push("<p><label>****：</label>{0}</p>".format(debtTransferDate));
        content.push("</td>");


        content.push("<td  colspan='5'>");

        content.push("<p><label>****：</label>{0}；".format(isComplete(row["isDzqz"])));
        content.push("<label class='borrower-label-left'>****：</label>{0}；".format(isComplete(row["isJkxy"])));
        content.push("<label class='borrower-label-left'>****：</label>{0}；".format(isComplete(row["isJkbcxy"])));
        content.push("</p>");

        content.push("<p>");
        content.push("<label>****：</label>{0}；".format(isComplete(row["isJkzxfwxy"])));
        content.push("<label class='borrower-label-left'>****：</label>{0}；".format(isComplete(row["isZkpz"])));
        content.push("<label class='borrower-label-left'>****：</label>{0}；".format(isComplete(row["isQzsqs"])));
        content.push("</p>");

        content.push("<p>");
        content.push("<label>****：</label>{0}；".format(isComplete(row["isFwfsm"])));
        content.push("<label class='borrower-label-left'>****：</label>{0}；".format(isComplete(row["isCsjl"])));
        content.push("<label class='borrower-label-left'>****：</label>{0}；".format(isComplete(row["isHkjl"])));
        content.push("</p>");

        content.push("<p>");
        content.push("<label>****：</label>{0}；".format(isComplete(row["isSfz"])));
        content.push("<label class='borrower-label-left'>****：</label>{0}；".format(isComplete(row["isSms"])));
        content.push("</p>");


        content.push("</td>");

        content.push("</tr>");
        $element.after(content.join(""));
        $appendRow[id] = "show";
    }

});