/**
 * 客户信息查询模块
 */
var CMMG_INVEST = function () {

    // 查询表单
    var $cmmg_invest_searchFormId = $("#cmmg_invest_searchFormId");
    // 搜索按钮
    var $cmmg_invest_searchBtn = $("#cmmg_invest_searchBtn");

    var mainEventHandler = function () {
        // 查询
        $cmmg_invest_searchBtn.on("click", function () {
            var paramData = CM_Components.getFormData("cmmg_invest_searchFormId");
            $.ajax({
                url: CM_Components.getContextAll("/investStat/getInvestAmt"),
                type: "POST",
                async: true,
                contentType: 'application/json',
                data: JSON.stringify(paramData),
                success: function (result) {
                    if (result) {
                        $("#cmmp_invest_user_countAmt").text(result.userCountAmt);
                        $("#cmmp_invest_contract_countAssetAmt").text(result.investAssetAmt);
                        $("#cmmp_invest_user_perAmt").text(result.investUserPerAmt);
                        $("#cmmp_invest_sale_perAmt").text(result.investSalePerAmt);
                    }
                },
                error: function (xhr, status, error) {
                    CM_Components.layerMsg("查询失败！");
                }
            });

            $("#cmmg_invest_info_table").bootstrapTable("refresh", {
                url: CM_Components.getContextAll("/investStat/page/list"),
                query: paramData
            });
        });

        // 数据网格框架：bootstrap table
        $(".cmmg-invest-table").bootstrapTable({
            method: 'get',
            url: "",
            cache: false,
            dataType: "json",
            striped: false,	 //使表格带有条纹
            sortable: true,
            pagination: true, //在表格底部显示分页工具栏
            pageSize: 50,
            pageNumber: 1,
            pageList: [50, 100],
            idField: "id",  //标识哪个字段为id主键
            showToggle: false,   //名片格式
            cardView: false,//设置为True时显示名片（card）布局
            singleSelect: false,//复选框只能选择一条记录
            search: false,//是否显示右上角的搜索框
            clickToSelect: true,//点击行即可选中单选/复选框
            sidePagination: "server",//表格分页的位置
            queryParamsType: "",
            strictSearch: true,
            showColumns: false,     //是否显示所有的列
            showRefresh: false,     //是否显示刷新按钮
            minimumCountColumns: 2,    //最少允许的列数
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
            }
        });
    }


    return {
        //初始化用户管理的主页面:用户列表
        initInvestMain: function () {
            mainEventHandler();
        }

    }
}();
