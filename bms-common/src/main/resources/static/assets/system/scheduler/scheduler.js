//@ sourceURL=scheduler.js
/**
 * 定时器模块js:包括主界面、修改定时器界面
 */
var BmsScheduler = function () {


    /**
     * 定时器主页面按钮绑定事件:启动、修改、停止
     */
    var schedulerMainEventHandler=function (opt) {

        var $schedulerTable=opt["schedulerTable"];

        //绑定停止定时器事件---------可以和下面的时间绑定合成一个,后续优化
        opt["startBtn"].on("click",function (e) {

            var rows =  CM_Components.getTableSelections($schedulerTable);

            if(rows.length==0){
                layer.msg("请选择要操作的定时任务");
                return false;
            }

            var $that=$(this);
            var reqUrl=CommonUtils.getContextAll($that.attr("data-url"));
            var startType=reqUrl.substring(reqUrl.length-1);

            var idDataAry=[];
            $.each(rows, function(i, obj) {
                if(obj.start!=startType){
                    idDataAry.push(obj);
                }
            });
            if(idDataAry.length==0){
                if(startType==1){
                    layer.msg("此任务已启动");
                }else {
                    layer.msg("此任务已停止");
                }
                return ;
            }
            $.ajax({
                type:"POST",
                url:reqUrl,
                contentType: "application/json",
                data: JSON.stringify(idDataAry),
                dataType: "json",
                success:function(res){
                    layer.msg(res.msg);
                    if(res.success==true){
                        CM_Components.refreshTable($schedulerTable);
                    }
                },
                error:function (XMLHttpRequest, textStatus) {
                    layer.msg("操作失败！请联系管理员");
                    console.error(XMLHttpRequest);
                }
            });
            e.stopPropagation();//阻止事件冒泡
        });
    }



    return{
        //初始化主界面
        initStartBtn:function (opt) {
            schedulerMainEventHandler(opt);
        }
    }
}();
