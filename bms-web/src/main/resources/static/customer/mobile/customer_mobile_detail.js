// 电销客户查询
//

var CMMG_CUTOMER_MOBILE_DETAIL = function () {

    // 数据网格
    function initTable(id, url,queryParams) {
        var $table = $("#" + id);
        // 数据网格框架：bootstrap table
        $table.bootstrapTable({
            // search: true,
            method: 'get',
            // url: url,
            cache: false,
            dataType: "json",
            striped: false,	 //使表格带有条纹
            sortable: true,
            pagination: true, //在表格底部显示分页工具栏
            pageSize: 10,
            pageNumber: 1,
            pageList: [10, 15, 20],
            idField: "id",  //标识哪个字段为id主键
            showToggle: false,   //名片格式
            cardView: false,//设置为True时显示名片（card）布局
            singleSelect: false,//复选框只能选择一条记录
            search: false,//是否显示右上角的搜索框
            clickToSelect: true,//点击行即可选中单选/复选框
            sidePagination: "server",//表格分页的位置
            // height: 350,
            queryParamsType: "",
            strictSearch: true,
            showColumns: false,     //是否显示所有的列
            showRefresh: false,     //是否显示刷新按钮
            minimumCountColumns: 2,    //最少允许的列数
            responseHandler: function (res) { //请求返回结果后的回调函数
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

        if(queryParams){
            $table.bootstrapTable("refresh", {
                url: url,
                query: queryParams
            });
        }
    }

    function initQueryBtn(queryBtnID, formId, tableID, url) {
        var $queryBtn = $("#" + queryBtnID);
        $queryBtn.on('click', function () {
            var queryParams = CM_Components.getFormData(formId);
            $("#" + tableID).bootstrapTable("refresh", {
                url: url,
                query: queryParams
            });
        });
    }

    return {
        initTable: function (id, url,queryParams) {
            initTable(id, url,queryParams);
        },
        initQueryBtn: function (queryBtnID, formId, tableID, url) {
            initQueryBtn(queryBtnID, formId, tableID, url);
        }
    };
}();