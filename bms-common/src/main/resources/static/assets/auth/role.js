
var BmsRole = function () {

    var setting = {
        view: {
            showLine: true,
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "code",
                pIdKey: "parentCode"
            }
        },
        check: {
            enable: true,
            chkboxType: {"Y": "p", "N": "p"}
        }
    };

    function initTree(options) {

        if(!options || options.length==0){
            return;
        }

        options.forEach(function (r) {
            $.ajax({
                type: "GET",
                cache: false,
                url: CommonUtils.getContextAll(r["url"]),
                success: function (res) {
                    $.fn.zTree.init($("#"+r["treeId"]), setting, res);
                },
                error: function (errorMsg) {
                    BmsComponents.layerMsg(errorMsg);
                }
            });
        });
    }
    return {

        // options=[{"treeId":"id","url":"url"}]
        initAuthTree:function (options) {
            initTree(options);
        }
    }
}();
