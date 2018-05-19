//@ sourceURL=scheduler.js
/**
 * 定时器模块js:包括主界面、修改定时器界面
 */
var CM_Scheduler = function () {
    //定时任务主页面属性开始
    var schedulerTableId = "schedulerTable";
    var $schedulerTable=$("#schedulerTable");
    var tableAjaxUrl="scheduler/page/list";
    var schedulerTableTableColum=[{checkbox: true}
                        ,{title: 'id',field: 'id',align: 'center',valign: 'middle',visible:false}
                        ,{title: '任务名称',field: 'jobName',align: 'center',valign: 'middle'}
                        ,{title: '任务状态',field: 'isStart',align: 'center',valign: 'middle'}
                        ,{title: 'cron表达式',field: 'cron',align: 'center',valign: 'middle'}
                        ,{title: '创建人',field: 'createBy',align: 'center',valign: 'middle'}
                        ,{title: '创建时间',field: 'createDate',align: 'center',valign: 'middle'}
                        ,{title: '修改人',field: 'updateBy',align: 'center',formatter:function (value,row,index){
                            if(null==value || ""==value){
                                return "-";
                            }
                            return value;
                        }}
                        ,{title: '修改日期',field: 'gender',align: 'center',valign: 'middle',formatter:function (value,row,index) {
                                if(null==value || ""==value){
                                    return "-";
                                }
                                return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                            }}
                        ];
    var $startSchedulers=$("#startSchedulers");
    var $stopSchedulers=$("#stopSchedulers");
    var $updateSchedulerView=$("#updateSchedulerView");
    //定时任务主页面属性结束
    //定时任务修改页面属性
    var $updateSchedulerForm =$("#schedulerUpdateForm");
    var updateSchedulerForm ="schedulerUpdateForm";
    var $updateSchedulerBtn = $("#updateSchedulerBtn");
    var updateAjaxUrl = CM_Components.getContextPath()+"/scheduler";
    /**
     * 定时器主页面初始化定时器表格数据
     */
    var initTable=function () {
        CM_Components.initBootStrapTable($schedulerTable,tableAjaxUrl,schedulerTableTableColum);
    }
    /**
     * 定时器主页面按钮绑定事件:启动、修改、停止
     */
    var schedulerMainEventHandler=function () {

        //绑定修改定时任务弹出页面的按钮事件
        $updateSchedulerView.on("click",function () {
           var rows =  CM_Components.getTableSelections($schedulerTable);
            if(rows.length==0){
                CM_Components.bootstrapSweetAlert("","请选择一个定时任务","error");
                return;
            }
            if(rows.length>=2){
                CM_Components.bootstrapSweetAlert("","不能选择多个定时任务","error");
                return;
            }
            //查看的基定时器本信息
            CM_Components.layerOpen("配置定时器",'900px','450px',"scheduler/update/view/"+rows[0].id);
        });
        
        //绑定停止定时器事件---------可以和下面的时间绑定合成一个,后续优化
        $stopSchedulers.on("click",function () {
            var rows =  CM_Components.getTableSelections($schedulerTable);
            if(rows.length==0){
                CM_Components.bootstrapSweetAlert("","请选择一个定时器","error");
                return false;
            }
            if(rows.length==1){
                var isStart = rows[0].isStart;
                if(isStart=="关闭"){
                    CM_Components.bootstrapSweetAlert("","此任务已经关闭","error");
                    return false;
                }
            }
            if(rows.length > 1){
                var isStartDataAry=[];
                $.each(rows, function(i, obj) {
                    isStartDataAry.push(obj.isStart);
                });
                var flag = $.inArray("开启", isStartDataAry);
                if(flag==-1){
                    CM_Components.bootstrapSweetAlert("","所选择的任务已经全部关闭了","error");
                    return false;
                }
            }
            var idDataAry=[];
            $.each(rows, function(i, obj) {
                if(obj.isStart=="开启"){
                    idDataAry.push(obj.id);
                }
            });
            ajaxChangeJobStatus(idDataAry,"scheduler/status/0");
        });


        //绑定开启定时器事件
        $startSchedulers.on("click",function () {
            var rows =  CM_Components.getTableSelections($schedulerTable);
            if(rows.length==0){
                CM_Components.bootstrapSweetAlert("","请选择一个定时器","error");
                return false;
            }
            if(rows.length==1){
                var isStart = rows[0].isStart;
                if(isStart=="开启"){
                    CM_Components.bootstrapSweetAlert("","此任务已经开启","error");
                    return false;
                }
            }
            if(rows.length > 1){
                var isStartDataAry=[];
                $.each(rows, function(i, obj) {
                    isStartDataAry.push(obj.isStart);
                });
                var flag = $.inArray("关闭", isStartDataAry);
                if(flag==-1){
                    CM_Components.bootstrapSweetAlert("","所选择的任务已经全部开启了","error");
                    return;
                }
            }
            var idDataAry=[];
            $.each(rows, function(i, obj) {
                if(obj.isStart=="关闭"){
                    idDataAry.push(obj.id);
                }
            });
            ajaxChangeJobStatus(idDataAry,"scheduler/status/1");
        });
    }
   
    var ajaxChangeJobStatus = function(idDataAry,url){
        $.ajax({
            type:"POST",
            url:url,
            contentType: "application/json",//加入contentType,后端需要用requestBody接受参数,此时的参数不在request里面了
            data: JSON.stringify(idDataAry),
            dataType: "json",
            success:function(responseJson){
                if(responseJson.success==true){//返回true
                    CM_Components.bootstrapSweetAlert("",responseJson.msg,"success");
                    CM_Components.refreshTable($schedulerTable);
                }
                if(responseJson.success==false){//返回false
                    CM_Components.bootstrapSweetAlert("",responseJson.msg,"error");
                }
            },
            beforeSend:function(XMLHttpRequest){
                //请求之前方法增强处理 ,显示遮罩层
                App.blockUI({target: '.page-content',animate: true});
            },
            complete:function(XMLHttpRequest, textStatus){
                //请求结束方法增强处理  ,隐藏遮罩层
                App.unblockUI('.page-content');
            },
            error:function (XMLHttpRequest, textStatus) {
                CM_Components.bootstrapSweetAlert("","请联系管理员!","error");
            }
        });
    }

    /**
     * 定时任务修改页面验证表单
     */
    var initUserUpdateBootStrapValidate = function () {
        $updateSchedulerForm.bootstrapValidator({
//          trigger: 'blur',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                jobName: {
                    validators: {
                        notEmpty: {
                            message: '任务名称不能为空'
                        }
                    }
                },
                jobClass:{
                    validators: {
                        notEmpty: {
                            message: '类不能为空'
                        }
                    }
                },
                cron:{
                    validators: {
                        notEmpty: {
                            message: 'cron表达式不能为空'
                        }
                    }
                }
            }
        }).on("success.form.bv",function(e){
            CM_Components.ajaxFormSumbitTable(updateAjaxUrl,updateSchedulerForm,schedulerTableId);
        });
    }
    
    var schedulerUpdateEnventHandler = function () {
        $updateSchedulerBtn.on("click",function () {
            $updateSchedulerForm.submit();
        });
    }
    return{
        //初始化主界面
        initSchedulerMain:function () {
            initTable();
            schedulerMainEventHandler();
        },
        initSchedulerUpdate:function () {
            initUserUpdateBootStrapValidate();
            schedulerUpdateEnventHandler();
        }
    }
}();
