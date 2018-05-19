var ID_TYPE = {
    '0': '身份证',
    '1': '户口薄',
    '2': '护照',
    '3': '军官证',
    '4': '士兵证',
    '5': '港澳居民来往内地通行证',
    '6': '台湾同胞来往内地通行证',
    '7': '临时身份证',
    '8': '外国人居留证',
    '9': '警官证'
};

var HF_OPEN_STATUS = {
    '0': '未开户',
    '1': '已开户',
    '2': '处理中'
};

/**
 * 客户信息查询模块
 */
var CMMG_CUSTOMER = function () {
    // 客户主页面table
    var $cmmg_customer_Table = $("#cmmg_customer_Table");
    var tableAjaxUrl = "customer/page/list";

    var tableColum = [
        {title: 'id', field: 'id', align: 'center', valign: 'middle', visible: false}
        , {
            title: '手机号', field: 'mobile', align: 'center', valign: 'middle', formatter: function (value, row, index) {
                if (!value) {
                    return null;
                }
                if (value.length <= 4) {
                    return value;
                }
                return value.replace(/(\d{3})\d{4}/, "$1****");
            }
        }
        , {title: '用户名', field: 'loginName', align: 'center', valign: 'middle'}
        , {
            title: '证件号', field: 'idNo', align: 'center', valign: 'middle', formatter: function (value, row, index) {
                if (!value) {
                    return null;
                }
                if (value.length <= 4) {
                    return value;
                }
                var after = value.length > 4 ? (value.length - 4) : value.length;
                return value.substring(0, 4) + '****' + value.substring(after);
            }
        }
        , {
            title: '注册时间', field: 'createDate', align: 'center', 'formatter': function (value, row, index) {
                if (value) {
                    var dt = new Date(value);
                    var valDate;
                    var year = dt.getFullYear();
                    var month = dt.getMonth() + 1;
                    month = month < 10 ? ("0" + month) : month;
                    var day = dt.getDate();
                    day = day < 10 ? ("0" + day) : day;
                    var hour = dt.getHours();
                    hour = hour < 10 ? ("0" + hour) : hour;
                    var minut = dt.getMinutes();
                    minut = minut < 10 ? ("0" + minut) : minut;
                    var second = dt.getSeconds();
                    second = second < 10 ? ("0" + second) : second;
                    valDate = year + "-" + month + "-" + day + " " + hour + "-" + minut + "-" + second;
                    return valDate;

                }
                return value;
            }
        }
        , {title: '注册渠道', field: 'channelCode', align: 'center'}
        , {title: '渠道码', field: 'advertiser', align: 'center'}
        , {title: '绑卡数', field: 'bindNum', align: 'center'}
        , {
            title: '首次交易日期', field: 'firstDealTime', align: 'center', 'formatter': function (value, row, index) {
                if (value) {
                    var dt = new Date(value);
                    var valDate;
                    var year = dt.getFullYear();
                    var month = dt.getMonth() + 1;
                    month = month < 10 ? ("0" + month) : month;
                    var day = dt.getDate();
                    day = day < 10 ? ("0" + day) : day;
                    var hour = dt.getHours();
                    hour = hour < 10 ? ("0" + hour) : hour;
                    var minut = dt.getMinutes();
                    minut = minut < 10 ? ("0" + minut) : minut;
                    var second = dt.getSeconds();
                    second = second < 10 ? ("0" + second) : second;
                    valDate = year + "-" + month + "-" + day + " " + hour + "-" + minut + "-" + second;
                    return valDate;

                }
                return value;
            }
        }
        , {
            title: '操作', field: 'remarks', align: 'center', "formatter": function (value, row, index) {
                // var rowData = JSON.stringify(row).replace(/"/g, "'")
                var idNo = row.idNo == null ? 1 : row.idNo;
                var btn = '<a  class="btn btn-circle green btn-outline sbold" data-toggle="modal" ' +
                    'onclick="CMMG_CUSTOMER.showCustomerInfo(' + row.mobile + ')"><i class="fa fa-user"></i>&nbsp;基本信息</a>' +
                    '<a  class="btn btn-circle green btn-outline sbold" data-toggle="modal" ' +
                    'onclick="CMMG_CUSTOMER.showAssetsInfo(\'' + idNo + '\',\'' + row.userNo + '\')"><i class="fa fa fa-suitcase"></i>&nbsp;资产交易</a>';
                // type,idNo,userNo
                // 'base',user.idNo==null?1:user.idNo,user.userNo)
                return btn;
            }
        }
    ];
    //表单重置按钮
    var $cmmg_customer_resetSearcFormBtn = $("#cmmg_customer_resetSearcFormBtn");
    // 查询表单
    var $cmmg_customer_searchFormId = $("#cmmg_customer_searchFormId");
    // 搜索按钮
    var $cmmg_customer_searchBtn = $("#cmmg_customer_searchBtn");

    var mainEventHandler = function () {
        // 查询
        $cmmg_customer_searchBtn.on("click", function () {
            var paramData = {
                "mobile": $cmmg_customer_searchFormId.find('input[name=mobile]').val(),
                "loginName": $cmmg_customer_searchFormId.find('input[name=loginName]').val(),
                "idNo": $cmmg_customer_searchFormId.find('input[name=idNo]').val(),
                "userNo": $cmmg_customer_searchFormId.find('input[name=userNo]').val(),
                "createBeginTime": $cmmg_customer_searchFormId.find('input[name=createBeginTime]').val(),
                "createEndTime": $cmmg_customer_searchFormId.find('input[name=createEndTime]').val()
            };
            // 重新加载数据
            $cmmg_customer_Table.bootstrapTable(
                "refresh",
                {
                    url: tableAjaxUrl,
                    query: paramData
                }
            );
        });
        $cmmg_customer_resetSearcFormBtn.on('click', function () {
            CommonUtils.reset($cmmg_customer_searchFormId);
        });
    }

    function cusotmerInfo(mobile) {
        if (!mobile) {
            console.error("手机号【mobile】为空！");
            return;
        }
        var requestUrl = CM_Components.getContextAll("/customer/detailInfo?mobile=") + mobile;
        var customer;
        $.ajax({
            url: requestUrl,
            type: "GET",
            async: false,
            //contentType: 'application/json',
            // data: JSON.stringify(params),
            success: function (result) {
                customer = result;
            },
            error: function (xhr, status, error) {
                CM_Components.layerMsg("查询失败！");
            }
        });

        var myModal = $("#myModal")
        myModal.modal();
        myModal.find("table td[name=mobile]").html(customer.mobile);
        myModal.find("table td[name=idNo]").html(customer.idNo);
        myModal.find("table td[name=loginName]").html(customer.loginName);
        myModal.find("table td[name=userNo]").html(customer.userNo);
        myModal.find("table td[name=idType]").html(ID_TYPE[customer.idType]);
        myModal.find("table td[name=channelName]").html(customer.channelName);
        myModal.find("table td[name=channelCode]").html(customer.channelCode);
        myModal.find("table td[name=invUserMobile]").html(customer.invUserMobile);
        myModal.find("table td[name=invUserName]").html(customer.invUserName);
        // 版本号
        var appBuildVersion = customer.appBuildVersion;
        var currentVersion;
        if (appBuildVersion) {
            currentVersion = customer.currentVersion + "(" + customer.appBuildVersion + ")";
        } else {
            currentVersion = customer.currentVersion;
        }
        myModal.find("table td[name=currentVersion]").html(currentVersion);
        myModal.find("table td[name=advertiser]").html(customer.advertiser);
        var lockedState = customer.lockedState;
        if (lockedState == "1") {
            myModal.find("table td[name=lockedState]").html("锁定");
        } else {
            myModal.find("table td[name=lockedState]").html("未锁定");
        }

        // 是否开启恒丰存管户( 0 未开户 ， 1 已开户 ， 2 处理中)
        myModal.find("table td[name=isOpenHfAccount]").html(HF_OPEN_STATUS[customer.isOpenHfAccount]);
        var openHfAccountTime = customer.openHfAccountTime;
        if (openHfAccountTime) {
            openHfAccountTime = CMMG_CUSTOMER.dateFormat(openHfAccountTime);
        }
        myModal.find("table td[name=openHfAccountTime]").html(openHfAccountTime);
        myModal.find("table td[name=hfAccountno]").html(customer.hfAccountno);
        var hfAccountno = customer.hfAccountno;
        if (hfAccountno == "1") {
            hfAccountno = "已设置";
        } else {
            hfAccountno = "已设置";
        }
        myModal.find("table td[name=isSetHfPassword]").html(hfAccountno);
        var setHfPasswordTime = customer.setHfPasswordTime;
        if (setHfPasswordTime) {
            setHfPasswordTime = CMMG_CUSTOMER.dateFormat(setHfPasswordTime);
        }
        myModal.find("table td[name=setHfPasswordTime]").html(setHfPasswordTime);
    }

    /**
     * 参数合并
     */
    function paramsMerge(paramData) {
        paramData["idNo"] = $("#cmmg_customer_transaction_idNo").val();
        paramData["userNo"] = $("#cmmg_customer_transaction_userNo").val();
        return paramData;
    }

    function initCustomerAseets() {

        $("#cmmg_customer_transaction_idNo").val("640121198301131649");
        $("#cmmg_customer_transaction_userNo").val("201904301100111809");

        // 交易记录 table
        var $cmmg_customer_transaction_record_table = $("#cmmg_customer_transaction_record_table");
        var $cmmg_customer_transaction_form = $("#cmmg_customer_transaction_content form");
        $("#cmmg_customer_transaction_query_btn").on('click', function () {
            var paramData = CM_Components.getFormData("cmmg_customer_transaction_form");
            paramData = paramsMerge(paramData);
            $cmmg_customer_transaction_record_table.bootstrapTable("refresh", {
                url: CM_Components.getContextAll("/customer/findTransaction"),
                query: paramData
            });
        });

        // 合同查询
        $("#cmmp_customer_contract_search_btn").on("click", function () {
            var paramData = CM_Components.getFormData("cmmp_customer_contract_form");
            paramData = paramsMerge(paramData);
            console.info("paramData", paramData);

            $.ajax({
                url: CM_Components.getContextAll("/customer/countAssetAmt"),
                type: "POST",
                async: true,
                contentType: 'application/json',
                data: JSON.stringify(paramData),
                success: function (result) {
                    $("#cmmp_customer_contract_countAssetAmt").text(result);
                },
                error: function (xhr, status, error) {
                    CM_Components.layerMsg("查询失败！");
                }
            });


            $.ajax({
                url: CM_Components.getContextAll("/customer/getAppUserAccountByUserNo?userNo=" + paramData.userNo),
                type: "GET",
                async: true,
                //contentType: 'application/json',
                //data: JSON.stringify(paramData),
                success: function (result) {
                    console.info("user", result);
                    if (result) {
                        var moneyInfo = '恒丰总资产' + result.hfAssets + '元 = 可用余额' + result.availAssets + '元 （不可提现' + result.unwithdrawAssets + '元 + 可提现' + result.withdrawAssets + '元）' +
                            ' + 持有本金' + result.holdPrincipal + '元 + 冻结' + result.freezeAssets + '元';
                        $("#cmmg_customer_contract_hengfeng").text(moneyInfo);
                    }
                },
                error: function (xhr, status, error) {
                    CM_Components.layerMsg("查询失败！");
                }
            });

            $("#cmmg_customer_constract_info_table").bootstrapTable("refresh", {
                url: CM_Components.getContextAll("/customer/getContractList"),
                query: paramData
            });
        });


        // 充值记录 查询
        $("#cmmg_customer_recharge_record_form_search_btn").on("click", function () {
            var paramData = CM_Components.getFormData("cmmg_customer_recharge_record_form");
            paramData = paramsMerge(paramData);
            console.info(paramData);
            $("#cmmg_customer_recharge_record_table").bootstrapTable("refresh", {
                url: CM_Components.getContextAll("/rechargeRecord/findRechargeRecord"),
                query: paramData
            });
        });

        // 提现记录 查询
        $("#cmmg_customer_withdrawals_record_form_search_btn").on("click", function () {
            var paramData = CM_Components.getFormData("cmmg_customer_withdrawals_record_form");
            paramData = paramsMerge(paramData);
            console.info(paramData);
            $("#cmmg_customer_withdrawals_record_table").bootstrapTable("refresh", {
                url: CM_Components.getContextAll("/withdrawalsRecord/findWithdrawalsRecord"),
                query: paramData
            });
        });


        // 数据网格框架：bootstrap table
        $(".cmmg-common-table").bootstrapTable({
            // search: true,
            method: 'get',
            url: "",
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
            height: 350,
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
        initCustomerMain: function () {
            CM_Components.initBootStrapTable($cmmg_customer_Table, tableAjaxUrl, tableColum);
            mainEventHandler();
            initCustomerAseets();
        },
        showCustomerInfo: function (mobile) {
            cusotmerInfo(mobile);

        },
        showAssetsInfo: function (idNo, userNo) {
            $("#cmmg_customer_asset_info").modal();
            $("#cmmg_customer_transaction_idNo").val(idNo);
            $("#cmmg_customer_transaction_userNo").val(userNo);
            // 充值记录
            $("#cmmg_customer_recharge_record_table").bootstrapTable("removeAll");
            // 提现记录 查询
            $("#cmmg_customer_withdrawals_record_form_search_btn").bootstrapTable("removeAll");
            // 合同
            $("#cmmg_customer_constract_info_table").bootstrapTable("removeAll");
            // 交易记录
            $("#cmmg_customer_transaction_record_table").bootstrapTable("removeAll");
            // 默认显示交易记录
            $('#cmmg_customer_tabs_transaction').tab('show');

        },
        dateFormat: function (longTime, dateFormat) {
            var dt = new Date(longTime);
            var valDate;
            var year = dt.getFullYear();
            var month = dt.getMonth() + 1;
            month = month < 10 ? ("0" + month) : month;
            var day = dt.getDate();
            day = day < 10 ? ("0" + day) : day;
            var hour = dt.getHours();
            hour = hour < 10 ? ("0" + hour) : hour;
            var minut = dt.getMinutes();
            minut = minut < 10 ? ("0" + minut) : minut;
            var second = dt.getSeconds();
            second = second < 10 ? ("0" + second) : second;
            valDate = year + "-" + month + "-" + day + " " + hour + "-" + minut + "-" + second;
            return valDate;
        }
    }
}();
