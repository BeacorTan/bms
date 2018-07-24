//@ sourceURL=sessio.js
/**
 * 会话管理模块js
 */
var CM_Session = function () {
    var $sessionTable=$("#sessionTable");
    var tableAjaxUrl="session/page/list";
    var sessionTableColum=[{checkbox: true}
                        ,{title: '会话id',field: 'id',align: 'center',valign: 'middle'}
                        ,{title: '用户',field: 'loginName',align: 'center',valign: 'middle',formatter:function (value,row,index) {
                            if(value=="guest"){return "游客";}else{return value}
                         }}
                        ,{title: 'ip地址',field: 'ip',align: 'center',valign: 'middle'}
                        ,{title: '最后一次请求时间',field: 'lastAccessTime',align: 'center',valign: 'middle',formatter:function (value,row,index) {
                            if(null==value || ""==value){
                                return "-";
                            }
                            return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                        }}
                        ];

    /**
     * 会话主页面初始化用户表格数据
     */
    var initTable=function () {
        BmsComponents.initBootStrapTable($sessionTable,tableAjaxUrl,sessionTableColum);
    }


    return{
        //初始化会话管理的主页面:会话列表
        initSessionMain:function () {
            initTable();
        }
    }
}();
