/**
 * Created by LouXin on 2015/6/9.
 */
function WinCall(options) {
    var defaults = {
        wintel_server_ip: '',
        wintel_server_port: '80',
        https: false,
        wintelapi_url: '',
        vcc_id: 0,
        vcc_code: '',
        phonetype: 'telephone',//telephone|softphone
        tel_server: '127.0.0.1',
        tel_server_port: '5090',
        busy_reasons: {},//置忙原因
        reconnect: true,
        reconnect_limit: 30,
        debug: false,
        event_listener: this.defaultEventListener
    };
    this.opts = $.extend({}, defaults, options);
    this.opts.sip_prefix = 'ss'+this.opts.vcc_id+'ss';
}

WinCall.prototype.socket = null;
WinCall.prototype.isConnect = false;
WinCall.prototype.wtPhone = null;
WinCall.prototype.sockeIsLogin = false;
/** 坐席的所有队列 */
WinCall.prototype.ag_ques = [];
/** 坐席的所有主叫号码 */
WinCall.prototype.ag_callers = [];
WinCall.prototype.agentPhone = null;
/** 坐席登陆电话密码 软电话使用 */
WinCall.prototype.pho_pwd = '';
/** 是否登录 */
WinCall.prototype.isLogin = false;
/** 是否在通话中 */
WinCall.prototype.isCalling = false;
/** 是否点击签出按钮 */
WinCall.prototype.isClickedBtnLogout = false;
/** 是否点击置忙按钮 */
WinCall.prototype.isClickedBtnBusy = false;
/** 通话过程中的参数 */
WinCall.prototype.params = {};
/** 技能组状态(默认为空闲) */
WinCall.prototype.QUEUESTATE = 1;
/** 当前的消息，用于保存返回的消息体分段返回时的信息 */
WinCall.prototype.currentMessage = '';
/** 当前登录坐席的信息，用于重登录 */
WinCall.prototype.loginInfo = {};
/** 呼叫记录 */
WinCall.prototype.callRecords = [];
WinCall.prototype.interval = null;
/** 消息返回 */
WinCall.prototype.returnType = {
    RETURN_ACTION: 1,//操作消息返回
    RETURN_CONNECT: 2,//保持连接消息返回
    RETURN_PHONE: 3,//电话状态消息返回 如呼叫中，振铃，挂机
    RETURN_AGENT: 4,//坐席状态消息返回 如系统置忙，系统置闲，占用等
    RETURN_QUEUE: 5,//系统排队状态
    RETURN_INITSTATE: 6,//签入后状态消息
    RETURN_IVRRESULT: 7,//IVR返回消息
    RETURN_UPDATE: 8,//坐席重载后的更新消息
    RETURN_ERROR: 10//返回和警告信息
};
/** 坐席操作的动作 */
WinCall.prototype.actions = {
    AGMT_INIT: 100,//坐席初始化
    AGMT_UNINIT: 1,//坐席反初始化
    AGMT_LOGIN: 2,//签入
    AGMT_LOGOUT: 3,//签出
    AGMT_BUSY: 4,//置忙
    AGMT_UNBUSY: 5,//置闲
    AGMT_QUERY: 6,//状态查询
    AGMT_KEEP: 7,//保持连接
    AGMT_ATIN: 9,//进入自动外呼
    AGMT_ATOUT: 10,//退出自动外呼
    AGMT_DIALAG: 21,//呼叫坐席
    AGMT_DIALOUT: 22,//呼叫外线
    AGMT_HANGUP: 23,//挂机
    AGMT_CONSULTAG: 24,//咨询内线
    AGMT_CONSULTOUT: 25,//咨询外线
    AGMT_CONSULTBACK: 26,//咨询接回
    AGMT_3WAY: 27,//三方
    AGMT_3WAYBACK: 28,//三方接回
    AGMT_TRANSFER: 29,//转接
    AGMT_CHANSPY: 30,//监听
    AGMT_INTERCEPT: 31,//拦截
    AGMT_BREAKIN: 32,//强插
    AGMT_HOLD: 33,//保持
    AGMT_UNHOLD: 34,//恢复
    AGMT_EVALUATE: 41,//转评价
    AGMT_DTMF: 42,//DTMF
    AGMT_TRANSIVR: 43,//转IVR
    AGMT_TRANSQUE: 44,//转技能组
    AGMT_BINDTRANS: 46,//盲转
    AGMT_TRANSVCCQUE: 47,//转技能组(可以跨企业转)
    AGMT_OLANSWER: 51//长接通应答
};
WinCall.prototype.que_states = {
    QUESTATE_LOGOFF: 0,//未登录
    QUESTATE_LOGON: 1,//队列已登录/空闲
    QUESTATE_BUSY: 2,//示忙
    QUESTATE_UNBUSY: 3,//忙碌
    QUESTATE_INUSE: 4,//占用中
    QUESTATE_WAIT: 5,//事后处理
    QUESTATE_OFFLINE: 7//离线(常接通模式未接通)
};
WinCall.prototype.agent_states = {
    AGSTRN_SYSBUSY: 2,//系统示忙
    AGSTRN_SYSUNBUSY: 3,//系统示闲
    AGSTRN_SYSOCCUPY: 4,//系统占用
    AGSTRN_SYSAFTDEAL: 5,//事后处理
    AGSTRN_OFFLINE: 7,//电话离线
    AGSTRN_OLUNBUSY: 11,//上线空闲
    AGSTRN_OLBUSY: 12//上线忙碌
};
WinCall.prototype.phone_states = {
    PHRN_HANGUP: 0,//挂机
    PHRN_CALLOUT: 4,//外呼中
    PHRN_RING: 5,//来电振铃
    PHRN_CONNECT: 6,//接通
    PHRN_OLRING: 7,//长接通模式来电
    PHRN_OLCONNECT: 8,//长接通接通
    PHRN_OLHANGUP: 9,//长接通断开
    PHRN_SELFCONNECT: 10,//己方接通
    PHRN_CONSULTOUT: 21,//咨询外呼中
    PHRN_CONSULTCONNECT: 22,//咨询接通
    PHRN_CONSULTBACK: 23,//咨询接回
    PHRN_CONSULTFAIL: 24,//咨询未呼通
    PHRN_3WAYSUCCESS: 25,//三方接通
    PHRN_3WAYBACK: 26,//三方接回
    PHRN_CHANSPYSUCCESS: 31,//监听成功
    PHRN_BREAKINSUCCESS: 32,//强插成功
    PHRN_TRANSFERSUCCESS: 33,//转接成功
    PHRN_INTERCEPTSUCCESS: 34,//拦截成功
    PHRN_TRANSFER: 35,//电话转接 被转接的作息收到此消息
    PHRN_OLDISCONNECT: 40,//常接通模式未接通
    PHRN_OTHERRING: 55//对方振铃
};
WinCall.prototype.error = {
    ERROR_LOGOFF: 4,//强制退出
    ERROR_UNBUSY: 5,//强制置闲
    ERROR_BUSY: 6//强制置忙
};
WinCall.prototype.allButtonActions = ['login', 'logout', 'dialinner', 'dialouter', 'hangup', 'answer', 'consultinner', 'consultouter', 'consultback', 'busy', 'unbusy', 'hold', 'restore', 'transfer', 'threeway', 'threewayback', 'chanspy', 'intercept', 'breakin', 'evaluate', 'enable_autocall', 'disable_autocall', 'transivr', 'transque', 'transvccque', 'blindtrans'];
/** 消息返回编码 */
WinCall.prototype.responseCode = {
    '002': '系统置忙',
    '003': '系统置闲',
    '004': '系统占用',
    '005': '事后处理',
    '007': '常接通电话离线',
    '011': '签入置闲',
    '012': '签入置忙',
    '024': '您已经被强制签出',
    '025': '您已经被强制置闲',
    '026': '您已经被强制置忙',
    '070': 'IVR返回',
    '080': '数据更新返回',
    '096': '断线重签入',
    '097': '连接失败',
    '098': 'flash未加载',
    '099': '断开socket',
    '100': '网络连接已断开，正在尝试重新连接，请稍后...',
    '101': '操作未完成，请刷新页面重新签入',
    '0000': '通话完成',
    '0400': '外呼中',
    '0500': '被呼叫来电',
    '0501': '队列分配来电',
    '0502': '外呼来电',
    '0503': '监听来电',
    '0504': '被咨询',
    '0505': '自动外呼来电',
    '0507': '常接通模式来电',
    '0508': '外线来电',
    '0600': '通话中',
    '0800': '常接通通话中',
    '0900': '常接通模式电话断开',
    '1000': '己方接通',
    '2100': '咨询外呼中',
    '2200': '咨询接通',
    '2300': '咨询接回',
    '2400': '咨询未呼通',
    '2500': '三方接通',
    '2600': '三方接回',
    '3100': '监听成功',
    '3200': '强插成功',
    '3300': '转接成功',
    '3400': '拦截成功',
    '3500': '系统重签入',
    '3600': '连接失败',
    '3700': 'flash未加载',
    '4000': '常接通模式未接通',
    '5500': '对方振铃',
    '20001': '软电话注册成功',
    '20002': '软电话重新注册',
    '20003': '软电话注册失败',
    '10010': '连接成功',
    '10011': '与服务器断开连接',
    '10012': '无法连接到服务器',
    '10013': '安全错误',
    '10014': '发送数据错误，服务器还没准备好',
    '10015': '网络异常',
    '10016': '网络异常，中间件断开',
    '001000': '初始化成功',
    '001001': '坐席已被初始化',
    '001002': '本socket已被初始化',
    '001003': '分机号不存在',
    '001004': '分机已有坐席使用',
    '001005': '队列不存在',
    '001006': '已达登录上限',
    '001007': '分机类型错误',
    '001100': '初始化参数为空',
    '001020': '企业错误(停用或过期等)',
    '001801': '连接数据库错误',
    '001804': '帐号错误',
    '001805': '找到多个坐席',
    '001806': '坐席密码错误',
    '001807': '分机查询错误',
    '001808': '分机不存在',
    '001809': '找到多个分机',
    '001810': '查询参数[vcccode]为空',
    '001811': '查询参数[agnum]为空',
    '001812': '查询参数[agpass]为空',
    '001813': '查询参数[agphone]为空',
    '001814': '编码设置错误',
    '001820': '不允许动态坐席签入',
    '001899': '其它初始化错误',
    '002000' : '签出成功',
    '002001' : '坐席尚未初始化',
    '002999' : '其它反初始化错误',
    '003000' : '签入成功',
    '003001' : '坐席尚未初始化',
    '003999' : '其它签入错误',
    '004000' : '签出成功',
    '004001' : '坐席尚未初始化',
    '004002' : '坐席尚未登录',
    '004003' : '连接断开自动签出',
    '004999' : '其它签出错误',
    '005000' : '置忙成功',
    '005001' : '坐席尚未初始化',
    '005002' : '坐席尚未签入',
    '005003' : '已处于置忙',
    '005004' : '不允许置忙',
    '005999' : '其它置忙错误',
    '006000' : '置闲成功',
    '006001' : '坐席尚未初始化',
    '006002' : '坐席尚未登录',
    '006003' : '已处于置闲',
    '006999' : '其它置闲错误',
    '007000' : '未登录',
    '007010' : '尚未初始化',
    '009000' : '进入自动',
    '009001' : '坐席尚未初始化',
    '009002' : '坐席尚未登录',
    '009003' : '项目错误(此项目非自动外呼项目或没有这个项目)',
    '009004' : '已进入自动外呼',
    '009999' : '其它进入自动外呼错误',
    '010000' : '退出自动',
    '010001' : '坐席尚未初始化',
    '010002' : '坐席尚未登录',
    '010003' : '项目错误(此项目非自动外呼项目或没有这个项目)',
    '010004' : '已退出自动外呼',
    '010999' : '其它退出自动外呼错误',
    '021000' : '呼叫坐席',
    '021001' : '主叫坐席错误',
    '021002' : '被叫坐席错误',
    '021003' : '队列错误',
    '021004' : '队列不允许呼叫',
    '021005' : '主叫状态不对',
    '021006' : '被叫状态不空闲',
    '021007' : '被叫坐席不属于本企业',
    '021008' : '主叫号码不对',
    '021011' : '没有外呼权限',
    '021020' : '企业错误(停用或过期等)',
    '021030' : '常接通模式不能呼叫坐席',
    '021451' : '消息格式错误',
    '021501' : '企业未启用',
    '021502' : '企业已过期或未开通',
    '021503' : '余额不足',
    '021504' : '外呼号码为黑名单',
    '021505' : '外呼坐席为空',
    '021999' : '其它呼叫坐席错误',
    '022000' : '呼叫外线',
    '022001' : '呼叫坐席错误',
    '022002' : '呼叫错误',
    '022003' : '队列错误',
    '022004' : '队列不允许呼叫',
    '022005' : '主叫状态不对',
    '022007' : '企业不匹配',
    '022008' : '主叫号码错误',
    '022009' : '常接通模式未接通',
    '022010' : '通道错误',
    '022011' : '没有外呼权限(坐席没有外呼的权限)',
    '022020' : '企业错误(停用或过期等)',
    '022451' : '消息格式错误',
    '022501' : '企业未启用',
    '022502' : '企业已过期或未开通',
    '022503' : '余额不足',
    '022504' : '外呼号码为黑名单',
    '022505' : '外呼号码为空',
    '022506' : '外呼号码为非数字',
    '022507' : '外呼时主叫号码为非数字',
    '022999' : '其它呼叫外线错误',
    '023000' : '挂机成功',
    '023001' : '坐席错误',
    '023002' : '不处于通话中',
    '023999' : '其它挂机错误',
    '024000' : '咨询成功',
    '024001' : '主叫坐席错误',
    '024002' : '咨询坐席错误',
    '024004' : '咨询坐席不空闲',
    '024005' : '不在通话中',
    '024006' : '通话类型不对',
    '024007' : '通话异常',
    '024009' : '已处于咨询或三方',
    '024451' : '消息格式错误',
    '024501' : '企业未启用',
    '024502' : '企业已过期或未开通',
    '024503' : '余额不足',
    '024504' : '咨询号码为黑名单',
    '024999' : '其它咨询坐席错误',
    '025000' : '咨询成功',
    '025001' : '主叫坐席错误',
    '025002' : '咨询坐席错误',
    '025004' : '咨询坐席不空闲',
    '025005' : '不在通话中',
    '025006' : '通话类型不对',
    '025007' : '通话异常',
    '025009' : '于咨询或三方',
    '025020' : '企业错误(停用或过期等)',
    '025451' : '消息格式错误',
    '025501' : '企业未启用',
    '025502' : '企业已过期或未开通',
    '025503' : '余额不足',
    '025504' : '咨询号码为黑名单',
    '025505' : '咨询号码为空',
    '025506' : '咨询号码为非数字',
    '025507' : '咨询时主叫号码为非数字',
    '025999' : '其它咨询外线错误',
    '026000' : '咨询接回',
    '026001' : '坐席错误',
    '026002' : '没有通话',
    '026003' : '通话异常',
    '026004' : '不在咨询三方中',
    '026005' : '咨询状态不对',
    '026999' : '其它咨询接回错误',
    '027000' : '三方成功',
    '027001' : '坐席错误',
    '027002' : '没有通话',
    '027003' : '通话异常',
    '027004' : '不在咨询中',
    '027005' : '咨询状态异常',
    '027999' : '其它三方错误',
    '028000' : '三方接回',
    '028001' : '坐席错误',
    '028002' : '没有通话',
    '028003' : '通话异常',
    '028004' : '不在咨询中',
    '028005' : '咨询状态异常',
    '028999' : '其它三方接回错误',
    '029000' : '转接成功',
    '029001' : '坐席错误',
    '029002' : '没有通话',
    '029003' : '通话异常',
    '029004' : '不在咨询中',
    '029005' : '询状态异常',
    '029006' : '不允许转接外线',
    '029999' : '其它转接错误',
    '030000' : '监听成功',
    '030001' : '监听坐席错误',
    '030002' : '被监听坐席错误',
    '030004' : '监听坐席状态不对',
    '030005' : '不在通话中',
    '030006' : '通话类型不对',
    '030007' : '通话异常',
    '030009' : '已被监听或强插',
    '030020' : '企业错误(停用或过期等)',
    '030030' : '常接通模式不能监听',
    '030999' : '其它监听错误',
    '031000' : '拦截成功',
    '031001' : '坐席错误',
    '031002' : '不在监听中',
    '031999' : '其它拦截错误',
    '032000' : '强插成功',
    '032001' : '坐席错误',
    '032003' : '不在监听或强插中',
    '032999' : '其它强插错误',
    '033000' : '保持成功',
    '033001' : '坐席错误',
    '033002' : '坐席不在通话中',
    '033003' : '尚未接通',
    '033004' : '已保持',
    '033005' : '没有权限',
    '033999' : '其他保持错误',
    '034000' : '恢复成功',
    '034999' : '其它恢复错误',
    '041000' : '满意度成功',
    '041001' : '坐席错误',
    '041002' : '坐席不在通话中',
    '041003' : '坐席不在通话中',
    '041004' : '通话异常',
    '041005' : '通话类型错误',
    '041006' : '通话状态不对',
    '041007' : '通道错误',
    '041999' : '其它满意度错误',
    '042000' : '握手信号',
    '043000' : '转IVR成功',
    '043001' : '坐席错误',
    '043002' : '坐席不在通话中',
    '043003' : '坐席不在通话中',
    '043004' : '通话异常',
    '043005' : '通话类型错误',
    '043006' : '通话状态不对',
    '043007' : '通道错误',
    '043999' : '其它转IVR错误',
    '044000' : '转技能组成功',
    '044001' : '坐席错误',
    '044002' : '坐席不在通话中',
    '044003' : '坐席不在通话中',
    '044004' : '通话异常',
    '044005' : '通话类型错误',
    '044006' : '通话状态不对',
    '044007' : '通道错误',
    '044008' : '技能组错误',
    '044999' : '其它转技能组错误',
    '047001' : '坐席错误',
    '047002' : '坐席不在通话中',
    '047003' : '坐席不在通话中',
    '047004' : '通话异常',
    '047005' : '通话类型错误',
    '047006' : '通话状态不对',
    '047007' : '通道错误',
    '047008' : '技能组错误',
    '047508' : '转技能组错误',
    '047999' : '其它转技能组错误',
    '046000' : '盲转成功',
    '046001' : '坐席不在通话中',
    '046002' : '坐席不在通话中',
    '046003' : '坐席不在通话中',
    '046005' : '通话类型不对',
    '046006' : '呼叫已结束或者呼叫状态不对',
    '046007' : '通道错误',
    '046099' : '被叫号码有误（太短，00开头）',
    '046999' : '其它盲转错误',
    '051000' : '常接通模',
    '051001' : '坐席错误',
    '051002' : '不处于通话中',
    '051003' : '非振铃状态',
    '051020' : '企业错误(停用或过期等)',
    '051030' : '常接通模式不允许',
    '051999' : '其它应答错误'
};
/** 初始化 */
WinCall.prototype.init = function () {
    var self = this;
    var schema = this.opts.https ? 'https' : 'http';
    var port = this.opts.https ? '443' : this.opts.wintel_server_port;
    this.socket = io.connect(schema+'://'+this.opts.wintel_server_ip+':'+port, {
        multiplex: false,
        reconnection: this.opts.reconnect,
        reconnectionAttempts: this.opts.reconnect_limit,
        reconnectionDelay: 2000,
        reconnectionDelayMax: 600000
    });
    this.socket.on('connect', function() {
        self.isConnect = true;
        self.log('connect');
        if (self.loginInfo.agentNum) {
            if (!self.interval) {
                self.interval = window.setInterval(function () {
                    self.fn_login(self.loginInfo.agentNum, self.loginInfo.agentPass,self.loginInfo.agentPhone,self.QUEUESTATE);
                }, 10000);
            }
        }
    });
    this.socket.on('toAgent', function(msg){//接收server消息
       self.response(msg);//处理消息
    });
    this.socket.on('error', function() {
        self.log('error');
    });
    this.socket.on('reconnecting', function(data) {
        self.log('reconnecting：'+data);
    });
    this.socket.on('reconnect_error', function(data) {
        self.log('reconnect_error：'+data);
    });
    this.socket.on('reconnect_failed', function() {
        self.log('reconnect_failed');
    });
    this.socket.on('disconnect', function() {
        self.isConnect = false;
        self.isLogin = false;
        self.log('disconnect');

        var response = self.msgResponce({
            MT: self.returnType.RETURN_ACTION,
            Retact: self.actions.AGMT_LOGOUT,
            Ret: 3,
            Retmsg: '',
            PhoSta: '',
            RingType: '',
            OAgId: '',
            AgSta: '',
            Code: ''
        });
        self.opts.event_listener(response);
    });
    /** 接收返回的消息 */
    this.socket.on('response', function(data) {
        self.response(data);
    });
};
/** 打印log */
WinCall.prototype.log = function (msg) {
    if (window.console && this.opts.debug) {
        console.log(msg);
    }
};
/** 发送消息 */
WinCall.prototype.send = function (message) {
    this.log(message);
    this.socket.emit('AgentCmd', message);
};
/** 断开和服务器的连接 */
WinCall.prototype.disconnect = function () {
    this.isConnect = false;
    //this.isClickedBtnLogout = true;
    this.isLogin = false;
    this.socket.disconnect();
    if(this.opts.phonetype == 'softphone') {
        this.close_softphone();
    }
};
/** 保持连接 握手信息 */
WinCall.prototype.agentKeepAlive = function () {
    WinCall.send({Action:this.actions.AGMT_KEEP});
};
/** 启动软电话 */
WinCall.prototype.start_softphone = function () {
    var self = this;
    try {
        self.wtPhone = new wtPhone({
            ip : 'localhost',
            port: '8086',
            wincall: self
        });

        self.wtPhone.init_event();
        self.wtPhone.init_cmd();
    } catch(e) {
        self.log(e);
        return false;
    }

    if (!self.wtPhone.isLogin) {
            //查看是否登陆成功；如果没有成功在在返回状态失败的是否重新注册；
            var socket_register_info = setInterval(function(){
            if (!self.wtPhone.isLogin) {
                self.log(self.wtPhone.isLogin);
                self.wtPhone.get_regstatus();
                self.wtPhone.login(self.opts.sip_prefix+self.agentPhone,self.pho_pwd,self.opts.tel_server,self.opts.tel_server_port);

            } else {
                //成功之后；
                clearInterval(socket_register_info);
                //登陆坐席；
                self.send({Action:self.actions.AGMT_LOGIN,State:self.QUEUESTATE,OnLine:self.OLMODE});
            }
        },5000)
    } else {
        self.send({Action:self.actions.AGMT_LOGIN,State:self.QUEUESTATE,OnLine:self.OLMODE});
    }
};
/** 关闭软电话 */
WinCall.prototype.close_softphone = function () {
    try{
        this.wtPhone.logout();//注销登陆
        this.wtPhone.isLogin = false;
        this.wtPhone.wsocket_event.close();
        this.wtPhone.wsocket_cmd.close();
        this.wtPhone.wsocket_event = null;
        this.wtPhone.wsocket_cmd = null;
    }
    catch(e){}
};
/** 坐席初始化 */
WinCall.prototype.agentInit = function (agentNum, agentPass, agentPhone) {
    this.send({Action: this.actions.AGMT_INIT, VccCode: this.opts.vcc_code, AgNum: agentNum, AgPass: agentPass, AgPhoneNum: agentPhone});
};
/** 坐席反初始化 */
WinCall.prototype.agentUnInit = function () {
    this.send({Action:this.actions.AGMT_UNINIT});
    this.close_softphone();
};
/** 坐席签入 */
WinCall.prototype.agentLogin = function () {
    if (this.opts.phonetype == 'softphone') {
        this.start_softphone();
    } else {
        this.send({Action:this.actions.AGMT_LOGIN,State:this.QUEUESTATE,OnLine:this.OLMODE});
    }
    this.isLogin = false;
};
/** 坐席签出 */
WinCall.prototype.agentLogout = function () {
    this.send({Action:this.actions.AGMT_LOGOUT});
};
/** 查询状态 */
WinCall.prototype.agentQuery = function () {
    if (!this.isLogin) {
        return false;
    }

    this.send({Action:this.actions.AGMT_QUERY});
};
/** 设置坐席的主教和队列 */
WinCall.prototype.set_ag_quecaller = function (jsonStr) {
    //var json = JSON.parse(jsonStr);
    var json = eval("("+jsonStr+")");
    this.ag_ques = json[0];
    this.ag_callers = json[1];
    this.pho_pwd = json[2];
};
/** 获取最近的通话记录 */
WinCall.prototype.fn_get_callrecord = function () {
    if (typeof localStorage == 'object') {
        if (localStorage.getItem('callRecords')) {
            return localStorage.getItem('callRecords').split(',');
        }
    }

    return this.callRecords;
};
WinCall.prototype.fn_set_callrecord = function (param) {
    if (!param) {
        return false;
    }

    var $length = this.callRecords.length;
    if ($length <= 0) {
        if (typeof localStorage == 'object') {
            if (localStorage.getItem('callRecords')) {
                this.callRecords = localStorage.getItem('callRecords').split(',');
                $length = this.callRecords.length;
            }
        }
    }

    for (var i=0; i<$length; i++) {
        if (param == this.callRecords[i]) {
            // 删除数组元素
            this.callRecords.splice(i, 1);
            this.callRecords.unshift(param);
            if (typeof localStorage == 'object') {
                localStorage.setItem('callRecords', this.callRecords.join(','));
            }

            return true;
        }
    }

    if ($length >= 10) {
        this.callRecords.pop();
        this.callRecords.unshift(param);
    } else {
        this.callRecords.unshift(param);
    }

    if (typeof localStorage == 'object') {
        localStorage.setItem('callRecords', this.callRecords.join(','));
    }

    return true;
};
/**
 * 以下函数为按钮点击事件函数
 */
/** 坐席签入 */
WinCall.prototype.fn_login = function (agentNum, agentPass, agentPhone, queState, olMode) {
        if (this.isLogin) {
        window.clearInterval(this.interval);
        this.log('坐席已经签入');
        return false;
    }
    queState = parseInt(queState);
    queState = queState == 2 ? 2 : 1;
    this.QUEUESTATE = queState ? queState : this.QUEUESTATE;
    this.agentPhone = agentPhone ? agentPhone : this.agentPhone;
    this.OLMODE = olMode ? olMode : this.OLMODE;
    var self = this;
    if (agentNum && agentPass && agentPhone) {
        if (!this.isConnect) {
            this.init();
        }

        window.setTimeout(function () {
            self.loginInfo = {
                agentNum: agentNum,
                agentPass: agentPass,
                agentPhone: agentPhone,
                queState: queState
            };
        }, 3000);

        this.agentInit(agentNum, agentPass, agentPhone);
    } else {
        var response = this.msgResponce({
            MT: this.returnType.RETURN_ACTION,
            Retact: this.actions.AGMT_INIT,
            Ret: 100,
            Retmsg: '',
            PhoSta: '',
            RingType: '',
            OAgId: '',
            AgSta: '',
            Code: ''
        });
        this.opts.event_listener(response);
        return false;
    }
};
/** 坐席签出 */
WinCall.prototype.fn_logout = function () {
    if (!this.isLogin) {
        return false;
    }

    this.isClickedBtnLogout = true;
    this.agentUnInit();
};
/** 获取参数 */
WinCall.prototype.fn_getParam = function (name) {
    if (!this.isLogin) {
        return false;
    }

    if (!name) {
        return this.params;
    } else {
        return this.params[name] || '';
    }
};
/**
 * 呼叫内线
 * @param agentID  要呼叫的坐席ID
 * @param callerID 主叫号码
 * @returns {boolean}
 */
WinCall.prototype.fn_dialinner = function (agentID, callerID) {
    if (!this.isLogin) {
        return false;
    }
    if (this.isCalling) {
        return false;
    }

    if (!agentID) {
        var response = this.msgResponce({
            MT: this.returnType.RETURN_ACTION,
            Retact: this.actions.AGMT_DIALAG,
            Ret: 505,
            Retmsg: '',
            PhoSta: '',
            RingType: '',
            OAgId: '',
            AgSta: '',
            Code: ''
        });
        this.opts.event_listener(response);
        return false;
    }

    //如果 当前有通话状态 返回错误
    if (
        this.QUEUESTATE != this.que_states.QUESTATE_BUSY &&
        this.QUEUESTATE != this.que_states.QUESTATE_UNBUSY &&
        this.QUEUESTATE != this.que_states.QUESTATE_LOGON
    ) {
        if (this.QUEUESTATE == this.que_states.QUESTATE_WAIT) {
            this.fn_unbusy();
        } else {
            return false;
        }
    }

    agentID = parseInt(agentID) || 0;
    this.send({Action:this.actions.AGMT_DIALAG,AgID:agentID,CallerID:callerID});
};
/**
 * 呼叫外线
 * @param called   被叫号码
 * @param callerID 主叫号码
 * @param groupID  呼出所属技能组ID
 * @param outChan  0-9,默认不写为0
 * @param proID    项目ID
 * @param taskID   任务ID
 */
WinCall.prototype.fn_dialouter = function (called, callerID, groupID, outChan, proID, taskID) {
    if (!this.isLogin) {
        return false;
    }
    if (this.isCalling) {
        return false;
    }
    var response = {};
    if (!called) {
        response = this.msgResponce({
            MT: this.returnType.RETURN_ACTION,
            Retact: this.actions.AGMT_DIALOUT,
            Ret: 505,
            Retmsg: '',
            PhoSta: '',
            RingType: '',
            OAgId: '',
            AgSta: '',
            Code: ''
        });
        this.opts.event_listener(response);
        return false;
    }
    called = $.trim(called);
    called = called.replace(' ', '');
    called = called.replace('-', '');
    called = called.replace('+', '');
    if (isNaN(called)) {
        response = this.msgResponce({
            MT: this.returnType.RETURN_ACTION,
            Retact: this.actions.AGMT_DIALOUT,
            Ret: 506,
            Retmsg: '',
            PhoSta: '',
            RingType: '',
            OAgId: '',
            AgSta: '',
            Code: ''
        });
        this.opts.event_listener(response);
        return false;
    }
    callerID = $.trim(callerID);
    callerID = callerID.replace(' ', '');
    callerID = callerID.replace('-', '');
    callerID = callerID.replace('+', '');
    if (isNaN(callerID)) {
        response = this.msgResponce({
            MT: this.returnType.RETURN_ACTION,
            Retact: this.actions.AGMT_DIALOUT,
            Ret: 507,
            Retmsg: '',
            PhoSta: '',
            RingType: '',
            OAgId: '',
            AgSta: '',
            Code: ''
        });
        this.opts.event_listener(response);
        return false;
    }

    //如果 当前有通话状态 返回错误
    if (
        this.QUEUESTATE != this.que_states.QUESTATE_BUSY &&
        this.QUEUESTATE != this.que_states.QUESTATE_UNBUSY &&
        this.QUEUESTATE != this.que_states.QUESTATE_LOGON
    ) {
        if (this.QUEUESTATE == this.que_states.QUESTATE_WAIT) {
            this.fn_unbusy();
        } else {
            return false;
        }
    }

    groupID = parseInt(groupID) || 0;
    proID = parseInt(proID) || 0;
    taskID = parseInt(taskID) || 0;
    outChan = parseInt(outChan) || 0;

    this.fn_set_callrecord(called);

    // 处理外呼号码的格式
    this.send({Action:this.actions.AGMT_DIALOUT,Called:called,CallerID:callerID,GroupID:groupID,OutChan:outChan,ProID:proID,TaskID:taskID});
};
/**
 * 挂断
 * @returns {boolean}
 */
WinCall.prototype.fn_hangup = function () {
    if (!this.isLogin) {
        return false;
    }

    this.send({Action:this.actions.AGMT_HANGUP});
};
/**
 * 接听
 * @returns {boolean}
 */
WinCall.prototype.fn_answer = function () {
    if (!this.isLogin) {
        return false;
    }

    // 软电话接听
    if (this.opts.phonetype == 'softphone') {
        this.wtPhone.wt_answer();
    }
};
/**
 * 咨询内线
 * @param {int} agentID 坐席ID
 * @returns {boolean}
 */
WinCall.prototype.fn_consultinner = function (agentID) {
    if (!this.isLogin) {
        return false;
    }
    if (!this.isCalling) {
        return false;
    }

    agentID = parseInt(agentID) || 0;
    this.send({Action:this.actions.AGMT_CONSULTAG,AgID:agentID});
};
/**
 * 咨询外线
 * @param called
 * @param caller
 * @returns {boolean}
 */
WinCall.prototype.fn_consultouter = function (called,caller) {
    if (!this.isLogin) {
        return false;
    }
    if (!this.isCalling) {
        return false;
    }

    var response = {};
    if (!called) {
        response = this.msgResponce({
            MT: this.returnType.RETURN_ACTION,
            Retact: this.actions.AGMT_DIALOUT,
            Ret: 505,
            Retmsg: '',
            PhoSta: '',
            RingType: '',
            OAgId: '',
            AgSta: '',
            Code: ''
        });
        this.opts.event_listener(response);
        return false;
    }
    called = $.trim(called);
    called = called.replace(' ', '');
    called = called.replace('-', '');
    called = called.replace('+', '');
    if (isNaN(called)) {
        response = this.msgResponce({
            MT: this.returnType.RETURN_ACTION,
            Retact: this.actions.AGMT_DIALOUT,
            Ret: 506,
            Retmsg: '',
            PhoSta: '',
            RingType: '',
            OAgId: '',
            AgSta: '',
            Code: ''
        });
        this.opts.event_listener(response);
        return false;
    }
    caller = $.trim(caller);
    caller = caller.replace(' ', '');
    caller = caller.replace('-', '');
    caller = caller.replace('+', '');
    if (isNaN(caller)) {
        response = this.msgResponce({
            MT: this.returnType.RETURN_ACTION,
            Retact: this.actions.AGMT_DIALOUT,
            Ret: 507,
            Retmsg: '',
            PhoSta: '',
            RingType: '',
            OAgId: '',
            AgSta: '',
            Code: ''
        });
        this.opts.event_listener(response);
        return false;
    }

    this.fn_set_callrecord(called);

    // 处理外呼号码的格式
    this.send({Action:this.actions.AGMT_CONSULTOUT,Called:called,Caller:caller});
};
/**
 * 咨询接回
 * @returns {boolean}
 */
WinCall.prototype.fn_consultback = function () {
    if (!this.isLogin) {
        return false;
    }
    if (!this.isCalling) {
        return false;
    }

    this.send({Action:this.actions.AGMT_CONSULTBACK});
};
/**
 * 置忙
 * @param {int} reason 置忙原因
 * @returns {boolean}
 */
WinCall.prototype.fn_busy = function (reason) {
    if (!this.isLogin) {
        return false;
    }
    if (this.isCalling) {
        return false;
    }
    if (typeof(reason) == "undefined") {
        reason = 1;
    } else {
        reason = parseInt(reason) || 0;
    }
    this.isClickedBtnBusy = true;
    this.log({Action:this.actions.AGMT_BUSY,Reason:reason});

    this.send({Action:this.actions.AGMT_BUSY,Reason:reason});
};
/**
 * 置闲
 * @returns {boolean}
 */
WinCall.prototype.fn_unbusy = function () {
    if (!this.isLogin) {
        return false;
    }
    if (this.isCalling) {
        return false;
    }
    this.isClickedBtnBusy = false;
    this.send({Action:this.actions.AGMT_UNBUSY});
};
/**
 * 保持
 * @returns {boolean}
 */
WinCall.prototype.fn_hold = function () {
    if (!this.isLogin) {
        return false;
    }
    if (!this.isCalling) {
        return false;
    }

    this.send({Action:this.actions.AGMT_HOLD});
};
/**
 * 恢复
 * @returns {boolean}
 */
WinCall.prototype.fn_restore = function () {
    if (!this.isLogin) {
        return false;
    }
    if (!this.isCalling) {
        return false;
    }

    this.send({Action:this.actions.AGMT_UNHOLD});
};
/**
 * 转接
 * @returns {boolean}
 */
WinCall.prototype.fn_transfer = function () {
    if (!this.isLogin) {
        return false;
    }
    if (!this.isCalling) {
        return false;
    }

    this.send({Action:this.actions.AGMT_TRANSFER});
};
/**
 * 三方通话
 * @returns {boolean}
 */
WinCall.prototype.fn_3way = function () {
    if (!this.isLogin) {
        return false;
    }
    if (!this.isCalling) {
        return false;
    }

    this.send({Action:this.actions.AGMT_3WAY});
};
/**
 * 三方接回
 * @returns {boolean}
 */
WinCall.prototype.fn_3wayback = function () {
    if (!this.isLogin) {
        return false;
    }
    if (!this.isCalling) {
        return false;
    }

    this.send({Action:this.actions.AGMT_3WAYBACK});
};
/**
 * 监听
 * @param {int} agentID
 * @returns {boolean}
 */
WinCall.prototype.fn_chanspy = function (agentID) {
    if (!this.isLogin) {
        return false;
    }

    agentID = parseInt(agentID) || 0;
    this.send({Action:this.actions.AGMT_CHANSPY,AgID:agentID});
};
/**
 * 拦截
 * @returns {boolean}
 */
WinCall.prototype.fn_intercept = function () {
    if (!this.isLogin) {
        return false;
    }

    this.send({Action:this.actions.AGMT_INTERCEPT});
};
/**
 * 强插
 * @returns {boolean}
 */
WinCall.prototype.fn_breakin = function () {
    if (!this.isLogin) {
        return false;
    }

    this.send({Action:this.actions.AGMT_BREAKIN});
};
/**
 * 转评价
 * @returns {boolean}
 */
WinCall.prototype.fn_evaluate = function () {
    if (!this.isLogin) {
        return false;
    }
    if (!this.isCalling) {
        return false;
    }

    this.send({Action:this.actions.AGMT_EVALUATE});
};
/**
 * 发送DTMF按键
 * @param num 按键
 * @returns {boolean}
 */
WinCall.prototype.fn_dtmf = function (num) {
    if (!this.isLogin) {
        return false;
    }
    if (!this.isCalling) {
        return false;
    }

    this.send({Action:this.actions.AGMT_DTMF,Dtmf:num});
};
/**
 * 进入自动外呼
 * @param {int} proID
 * @returns {boolean}
 */
WinCall.prototype.fn_autocallin = function (proID) {
    if (!this.isLogin) {
        return false;
    }

    proID = parseInt(proID) || 0;
    this.send({Action:this.actions.AGMT_ATIN,ProID:proID});
};
/**
 * 退出自动外呼
 * @returns {boolean}
 */
WinCall.prototype.fn_autocallout = function () {
    if (!this.isLogin) {
        return false;
    }

    this.send({Action:this.actions.AGMT_ATOUT});
};
/**
 * 转IVR
 * @returns {boolean}
 */
WinCall.prototype.fn_transivr = function () {
    if (!this.isLogin) {
        return false;
    }

    this.send({Action:this.actions.AGMT_TRANSIVR,IvrID:0,IvrParm:"IVR"});
};
/**
 * 转技能组
 * @param {int} queId
 * @returns {boolean}
 */
WinCall.prototype.fn_transque = function (queId) {
    if (!this.isLogin) {
        return false;
    }

    this.send({Action:this.actions.AGMT_TRANSQUE,TransType:1,TransParam1:queId});
};
/**
 * 转企业技能组
 * @param {int} queId
 * @param {int} vccId
 * @returns {boolean}
 */
WinCall.prototype.fn_transvccque = function (queId, vccId) {
    if (!this.isLogin) {
        return false;
    }

    queId = parseInt(queId);
    vccId = parseInt(vccId) || 0;
    this.send({Action:this.actions.AGMT_TRANSVCCQUE,TransVcc:vccId,TransQue:queId});
};
/**
 * 盲转
 * @param called 被叫
 * @param caller 主叫
 * @returns {boolean}
 */
WinCall.prototype.fn_bindtrans = function (called, caller) {
    if (!this.isLogin) {
        return false;
    }

    this.send({Action:this.actions.AGMT_BINDTRANS,Called:called,Caller:caller});
};
/**
 * 返回坐席所属的技能组
 * @returns {Array}
 */
WinCall.prototype.fn_get_que = function () {
    return this.ag_ques;
};
/**
 * 获取主叫
 * @returns {Array}
 */
WinCall.prototype.fn_get_caller = function () {
    return this.ag_callers;
};
/** 接收返回消息 */
WinCall.prototype.response = function (data) {
    this.log(data);
    data = $.trim(data);
    this.currentMessage = data;
    if (this.currentMessage.substr(0, 1) != '{' || this.currentMessage.substr(-1, 1) != '}') {
        return;
    }

    var self = this;
    var temp = this.currentMessage.split('\r\n');
    this.currentMessage = '';
    $.each(temp, function(n, objstr) {
        var  obj_responce = JSON.parse(objstr);
        //电话状态缓存起来
        if(3 == obj_responce.MT || 7 == obj_responce.MT) {
            $.each(obj_responce, function(property,value) {
                self.params[property] = value;
            });
        }
        var _responce = self.msgResponce(obj_responce);
        self.opts.event_listener(_responce);
    });
};

/**
 * 处理消息返回
 *
 * @param obj_responce
 * @param {number} obj_responce.MT WinCall.return
 * @param {number} obj_responce.Retact 响应类型
 * @param {number} obj_responce.Ret 返回值
 * @param {string} obj_responce.Retmsg 返回消息
 * @param {string} obj_responce.PhoSta 分机状态
 * @param {string} obj_responce.RingType 分机状态
 * @param {string} obj_responce.OAgId
 * @param {string} obj_responce.AgSta
 * @param {string} obj_responce.AgStaParam
 * @param {string} obj_responce.Code
 * @param {number} obj_responce.Param
 * @param {string} obj_responce.Data
 * @param {string} obj_responce.IvrResult
 *
 * @returns {*}
 */
WinCall.prototype.msgResponce = function (obj_responce) {
    var self = this;
    var returnObject = {code:'',msg:'',type:'',agStatus:'',queStatus:'',enableActions:[],disableActions:[],data:""};
    switch (obj_responce.MT) {
        case self.returnType.RETURN_ACTION:
            switch(obj_responce.Retact) {
                case self.actions.AGMT_INIT://初始化
                    switch (obj_responce.Ret) {
                        case 0:// 初始化成功
                            self.set_ag_quecaller(obj_responce.Retmsg);
                            setTimeout(function (){self.agentLogin();}, 500);
                            self.agentIsStillLogin = false;
                            returnObject.code = '001000';
                            break;
                        case 1 : returnObject.code = '001001';self.agentIsStillLogin = true; break;   //坐席已被初始化
                        case 2 : returnObject.code = '001002'; break;   //本socket已被初始化
                        case 3 : returnObject.code = '001003'; break;   //分机号不存在
                        case 4 : returnObject.code = '001004'; break;   //分机已有坐席使用
                        case 5 : returnObject.code = '001005'; break;   //有队列不存在
                        case 6 : returnObject.code = '001006'; break;   //已达登录上限
                        case 7 : returnObject.code = '001007'; break;   //分机类型错误
                        case 20 : returnObject.code = '001020'; break;   //企业错误
                        case 100 : returnObject.code = '001100'; break;   //初始化参数为空
                        case 801 : returnObject.code = '001801'; break;   //连接数据库错误
                        case 802 : returnObject.code = '001802'; break;   //数据库名错误
                        case 803 : returnObject.code = '001803'; break;   //查询语句错误
                        case 804 : returnObject.code = '001804'; break;   //帐号错误
                        case 805 : returnObject.code = '001805'; break;   //找到多个坐席
                        case 806 : returnObject.code = '001806'; break;   //密码错误
                        case 807 : returnObject.code = '001807'; break;   //分机查询错误
                        case 808 : returnObject.code = '001808'; break;   //分机不存在
                        case 809 : returnObject.code = '001809'; break;   //找到多个分机
                        case 810 : returnObject.code = '001810'; break;   //查询参数[vcccode]为空
                        case 811 : returnObject.code = '001811'; break;   //查询参数[agnum]为空
                        case 812 : returnObject.code = '001812'; break;   //查询参数[agpass]为空
                        case 813 : returnObject.code = '001813'; break;   //查询参数[agphone]为空
                        case 814 : returnObject.code = '001814'; break;   //编码设置错误
                        case 820 : returnObject.code = '001820'; break;   //不允许动态坐席签入
                        default :
                            var retCode = parseInt(obj_responce.Ret);
                            if (retCode >= 700 && retCode < 800) {
                                returnObject.code = '[001'+retCode+']坐席初始化接口访问失败';
                            }
                            returnObject.code = '001'+obj_responce.Ret;
                            break;
                    }
                    break;
                case self.actions.AGMT_UNINIT://反初始化
                    switch (obj_responce.Ret) {
                        case 0 :
                            self.isLogin = false;
                            returnObject.code = '002000';
                            returnObject.type = 'logout_action';
                            returnObject.agStatus = '未签入';
                            returnObject.queStatus = '未签入';
                            returnObject.enableActions = ['login'];
                            returnObject.disableActions = self.allButtonActions;
                            break;// 成功
                        case 1 : returnObject.code = '002001'; break;// 坐席尚未初始化
                        default: returnObject.code = '002999'; break;//其它反初始化错误
                    }
                    break;
                case self.actions.AGMT_LOGIN://签入
                    switch (obj_responce.Ret) {
                        case 0 :
                        case 1 : self.isLogin = true;
                            returnObject.code = '003000';
                            returnObject.type = 'login_action';
                            returnObject.agStatus = '已签入';
                            if (self.QUEUESTATE == self.que_states.QUESTATE_LOGON) {
                                returnObject.queStatus = '空闲';
                                returnObject.enableActions = ['busy', 'enable_autocall', 'logout', 'dialinner', 'dialouter', 'chanspy'];
                                returnObject.disableActions = ['login'];
                            } else {
                                returnObject.queStatus = '忙碌';
                                returnObject.enableActions = ['unbusy', 'enable_autocall', 'logout', 'dialinner', 'dialouter', 'chanspy'];
                                returnObject.disableActions = ['login'];
                            }
                            window.clearInterval(self.interval);
                            break;//成功
                        case 2 : self.agentUnInit(); returnObject.code = '003001'; break;//坐席尚未初始化
                        default: self.agentUnInit(); returnObject.code = '003999'; break;//其它签入错误
                    }
                    break;
                case self.actions.AGMT_LOGOUT://签出
                    switch (obj_responce.Ret) {
                        case 0 :
                            self.QUEUESTATE = self.que_states.QUESTATE_LOGOFF;
                            self.agentUnInit();
                            self.agentIsStillLogin = false;
                            self.isLogin = false;
                            returnObject.code = '004000';//成功
                            returnObject.type = 'logout_action';
                            returnObject.agStatus = '未签入';
                            returnObject.queStatus = '未签入';
                            returnObject.enableActions = ['login'];
                            returnObject.disableActions = self.allButtonActions;
                            break;
                        case 1 : returnObject.code = '004001'; break;  //坐席尚未初始化
                        case 2 : returnObject.code = '004002'; break;  //坐席尚未登录
                        case 3 ://连接断开签出
                            self.isLogin = false;
                            returnObject.code = '004003';
                            returnObject.type = 'logout_action';
                            returnObject.agStatus = '未签入';
                            returnObject.queStatus = '未签入';
                            returnObject.enableActions = ['login'];
                            returnObject.disableActions = self.allButtonActions;
                            break;
                        default: returnObject.code = '004999'; break;  //其它签出错误
                    }
                    break;
                case self.actions.AGMT_BUSY://置忙
                    switch (obj_responce.Ret) {
                        case 3 : //已处于示忙
                        case 0 : self.QUEUESTATE = self.que_states.QUESTATE_BUSY;
                            returnObject.code = '005000';//成功
                            returnObject.type = 'busy_action';
                            returnObject.queStatus = '忙碌';
                            returnObject.enableActions = ['unbusy', 'dialinner', 'dialouter', 'enable_autocall'];
                            returnObject.disableActions = ['busy', 'hangup'];
                            break;
                        case 1 : returnObject.code = '005001';break;//坐席尚未初始化
                        case 2 : returnObject.code = '005002';break;//坐席尚未签入
                        case 4 : returnObject.code = '005004';break;//不允许示忙
                        default: returnObject.code = '005999';break;//其它置忙错误
                    }
                    break;
                case self.actions.AGMT_UNBUSY://置闲
                    switch (obj_responce.Ret) {
                        case 3 : self.QUEUESTATE = self.que_states.QUESTATE_UNBUSY;
                            returnObject.code = '006003';
                            break;//已处于示闲
                        case 0 : self.QUEUESTATE = self.que_states.QUESTATE_UNBUSY;
                            returnObject.code = '006000';//成功
                            returnObject.type = 'unbusy_action';
                            returnObject.queStatus = '空闲';
                            returnObject.enableActions = ['busy', 'dialinner', 'dialouter', 'enable_autocall', 'chanspy'];
                            returnObject.disableActions = ['unbusy', 'hangup'];
                            break;
                        case 1 : returnObject.code = '006001';break;//坐席尚未初始化
                        case 2 : returnObject.code = '006002';break;//坐席尚未登录
                        default: returnObject.code = '006999';break;//其它置闲错误
                    }
                    break;
                case self.actions.AGMT_QUERY://状态查询
                    switch(obj_responce.Ret) {
                        case 0 : returnObject.code = '007000';break;//未登录
                        case 1 : returnObject.code = '007001';break;//已登录（空闲）
                        case 2 : returnObject.code = '007002';break;//示忙
                        case 3 : returnObject.code = '007002';break;//示忙
                        case 4 : returnObject.code = '007004';break;//占用
                        case 5 : returnObject.code = '007005';break;//事后处理
                        case 10 :returnObject.code = '007010';break;//尚未初始化
                        default: returnObject.code = '007999';break;//其它坐席状态查询错误
                    }
                    break;
                case self.actions.AGMT_KEEP://保持连接
                    break;
                case self.actions.AGMT_ATIN://进入自动外呼
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '009000';
                            returnObject.type = 'enable_autocall_action';
                            returnObject.enableActions = ['disable_autocall'];
                            returnObject.disableActions = ['enable_autocall'];
                            break;//成功
                        case 1 : returnObject.code = '009001';break;//坐席尚未初始化
                        case 2 : returnObject.code = '009002';break;//坐席尚未登录
                        case 3 : returnObject.code = '009003';break;//项目错误(此项目非自动外呼项目或没有这个项目)
                        case 4 :
                            returnObject.type = 'enable_autocall_action';
                            returnObject.enableActions = ['disable_autocall'];
                            returnObject.disableActions = ['enable_autocall'];
                            returnObject.code = '009004';
                            break;//已进入
                        default: returnObject.code = '009999';break;//其它进入自动外呼错误
                    }
                    break;
                case self.actions.AGMT_ATOUT://退出自动外呼
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '010000';
                            returnObject.type = 'disable_autocall_action';
                            returnObject.enableActions = ['enable_autocall'];
                            returnObject.disableActions = ['disable_autocall'];
                            break;//成功
                        case 1 : returnObject.code = '010001';break;//坐席尚未初始化
                        case 2 : returnObject.code = '010002';break;//坐席尚未登录
                        case 3 : returnObject.code = '010003';break;//项目错误(此项目非自动外呼项目或没有这个项目)
                        case 4 :
                            returnObject.code = '010004';
                            returnObject.type = 'disable_autocall_action';
                            returnObject.enableActions = ['enable_autocall'];
                            returnObject.disableActions = ['disable_autocall'];
                            break;//已退出
                        default: returnObject.code = '010999';break; //其它退出自动外呼错误
                    }
                    break;
                case self.actions.AGMT_DIALAG://呼叫坐席
                    switch(obj_responce.Ret) {
                        case 0:
                            returnObject.code = '021000';
                            returnObject.type = 'callinner_action';
                            break;//呼叫成功
                        case 1 : returnObject.code = '021001';break;//主叫坐席错误
                        case 2 : returnObject.code = '021002';break;//被叫坐席错误
                        case 3 : returnObject.code = '021003';break;//队列错误
                        case 4 : returnObject.code = '021004';break;//队列不允许呼叫
                        case 5 : returnObject.code = '021005';break;//主叫状态不对
                        case 6 : returnObject.code = '021006';break;//被叫状态不空闲
                        case 7 : returnObject.code = '021007';break;//被叫坐席不属于本企业
                        case 8 : returnObject.code = '021008';break;//主叫号码不对
                        case 11 : returnObject.code = '021011';break;//没有外呼权限
                        case 20 : returnObject.code = '021020';break;//企业错误(停用或过期等)
                        case 30 : returnObject.code = '021030';break;//常接通模式不能呼叫坐席
                        case 451 :
                        case 452 : returnObject.code = '021451';break;//消息格式错误
                        case 501 : returnObject.code = '021501';break;//企业未启用
                        case 502 : returnObject.code = '021502';break;//企业已过期或未开通
                        case 503 : returnObject.code = '021503';break;//余额不足
                        case 504 : returnObject.code = '021504';break;//外呼黑名单
                        case 505 : returnObject.code = '021505';break;//外呼坐席为空
                        default: returnObject.code = '021999';break;//其它呼叫坐席错误
                    }
                    break;
                case self.actions.AGMT_DIALOUT://呼叫外线
                    switch(obj_responce.Ret) {
                        case 0:
                            returnObject.code = '022000';
                            returnObject.type = 'callouter_action';
                            break;//呼叫成功
                        case 1 : returnObject.code = '022001';break;//主叫坐席错误
                        case 2 : returnObject.code = '022002';break;//呼叫错误
                        case 3 : returnObject.code = '022003';break;//队列错误
                        case 4 : returnObject.code = '022004';break;//队列不允许呼叫
                        case 5 : returnObject.code = '022005';break;//主叫状态不对
                        case 6 : returnObject.code = '022006';break;//无
                        case 7 : returnObject.code = '022007';break;//企业不匹配
                        case 8 : returnObject.code = '022008';break;//主叫号码错误
                        case 9 : returnObject.code = '022009';break;//常接通模式未接通
                        case 10 : returnObject.code = '022010';break;//通道错误
                        case 11 : returnObject.code = '022011';break;//没有外呼权限(坐席没有外呼的权限)
                        case 20 : returnObject.code = '022020';break;//企业错误(停用或过期等)
                        case 451 :
                        case 452 : returnObject.code = '022451';break;//消息格式错误
                        case 501 : returnObject.code = '022501';break;//企业未启用
                        case 502 : returnObject.code = '022502';break;//企业已过期或未开通
                        case 503 : returnObject.code = '022503';break;//余额不足
                        case 504 : returnObject.code = '022504';break;//外呼黑名单
                        case 505 : returnObject.code = '022505';break;//外呼号码为空
                        case 506 : returnObject.code = '022506';break;//外呼号码格式不正确
                        case 507 : returnObject.code = '022507';break;//外呼时显示的主叫号码不正确
                        default: returnObject.code = '022999';break;//其它呼叫外线错误
                    }
                    break;
                case self.actions.AGMT_HANGUP://挂机
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '023000';
                            returnObject.type = 'hangup_action';
                            break;//挂机成功
                        case 1 : returnObject.code = '023001';break;//坐席错误
                        case 2 : returnObject.code = '023002';break;//不处于通话中
                        default: returnObject.code = '023999';break;//其它挂机错误
                    }
                    break;
                case self.actions.AGMT_CONSULTAG://咨询内线
                    switch(obj_responce.Ret) {
                        case 0:
                            returnObject.code = '024000';
                            returnObject.type = 'consultinner_action';
                            returnObject.enableActions = ['consultback'];
                            returnObject.disableActions = ['consultinner', 'consultouter', 'chanspy'];
                            break;//咨询成功
                        case 1 :returnObject.code = '024001';break;//主叫坐席错误
                        case 2 :
                        case 3 : returnObject.code = '024002';break;//咨询坐席错误
                        case 4 : returnObject.code = '024004';break;//咨询坐席不空闲
                        case 5 : returnObject.code = '024005';break;//不在通话中
                        case 6 : returnObject.code = '024006';break;// 通话类型不对
                        case 7 :
                        case 8 : returnObject.code = '024007';break;//通话异常
                        case 9 : returnObject.code = '024009';break;//已处于咨询或三方
                        case 451 :
                        case 452 : returnObject.code = '024451';break;//消息格式错误
                        case 501 : returnObject.code = '024501';break;//企业未启用
                        case 502 : returnObject.code = '024502';break;//企业已过期或未开通
                        case 503 : returnObject.code = '024503';break;//余额不足
                        case 504 : returnObject.code = '024504';break;//外呼黑名单
                        default: returnObject.code = '024999';break;//其它咨询坐席错误
                    }
                    break;
                case self.actions.AGMT_CONSULTOUT://咨询外线
                    switch(obj_responce.Ret) {
                        case 0:
                            returnObject.code = '025000';
                            returnObject.type = 'consultouter_action';
                            returnObject.enableActions = ['consultback'];
                            returnObject.disableActions = ['consultinner', 'consultouter', 'chanspy'];//咨询成功
                            break;
                        case 1 : returnObject.code = '025001';break;//主叫坐席错误
                        case 2 :
                        case 3 : returnObject.code = '025002';break;//咨询坐席错误
                        case 4 : returnObject.code = '025004';break;//咨询坐席不空闲
                        case 5 : returnObject.code = '025005';break;//不在通话中
                        case 6 : returnObject.code = '025006';break;//通话类型不对
                        case 7 :
                        case 8 : returnObject.code = '025007';break;//通话异常
                        case 9 : returnObject.code = '025009';break;//于咨询或三方
                        case 20 : returnObject.code = '025020';break;//企业错误
                        case 451 :
                        case 452 : returnObject.code = '025451';break;//消息格式错误
                        case 501 : returnObject.code = '025501';break;//企业未启用
                        case 502 : returnObject.code = '025502';break;//企业已过期或未开通
                        case 503 : returnObject.code = '025503';break;//余额不足
                        case 504 : returnObject.code = '025504';break;//外呼黑名单
                        case 505 : returnObject.code = '025505';break;//外呼号码为空
                        case 506 : returnObject.code = '025506';break;//外呼号码格式不正确
                        case 507 : returnObject.code = '025507';break;//外呼时显示的主叫号码不正确
                        default: returnObject.code = '025999';break;//其它咨询外线错误
                    }
                    break;
                case self.actions.AGMT_CONSULTBACK://咨询接回
                    switch(obj_responce.Ret) {
                        case 0:
                            returnObject.code = '026000';
                            returnObject.type = 'consultback_action';
                            returnObject.enableActions = ['consultback', 'transfer'];
                            returnObject.disableActions = ['transfer', 'consultback', 'threeway', 'chanspy'];
                            break;//操作成功
                        case 1 : returnObject.code = '026001';break;//坐席错误
                        case 2 : returnObject.code = '026002';break;//没有通话
                        case 3 : returnObject.code = '026003';break;//通话异常
                        case 4 : returnObject.code = '026004';break;//不在咨询三方中
                        case 5 : returnObject.code = '026005';break;//咨询状态不对
                        default: returnObject.code = '026999';break;//其它咨询接回错误
                    }
                    break;
                case self.actions.AGMT_3WAY://三方
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '027000';
                            returnObject.type = 'threeway_action';
                            returnObject.enableActions = ['threewayback'];
                            returnObject.disableActions = ['threeway', 'transfer', 'consultback', 'chanspy'];
                            break;//三方成功
                        case 1 : returnObject.code = '027001';break;//坐席错误
                        case 2 : returnObject.code = '027002';break;//没有通话
                        case 3 : returnObject.code = '027003';break;//通话异常
                        case 4 : returnObject.code = '027004';break;//不在咨询中
                        case 5 : returnObject.code = '027005';break;//咨询状态异常
                        default: returnObject.code = '027999';break;//其它三方错误
                    }
                    break;
                case self.actions.AGMT_3WAYBACK://三方接回
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '028000';
                            returnObject.type = 'threewayback_action';
                            returnObject.enableActions = ['consultinner', 'consultouter'];
                            returnObject.disableActions = ['threewayback', 'threeway', 'chanspy'];
                            break;//操作成功
                        case 1 : returnObject.code = '028001';break;//坐席错误
                        case 2 : returnObject.code = '028002';break;//没有通话
                        case 3 : returnObject.code = '028003';break;//通话异常
                        case 4 : returnObject.code = '028004';break;//不在咨询中
                        case 5 : returnObject.code = '028005';break;//咨询状态异常
                        default: returnObject.code = '028999';break;//其它三方接回错误
                    }
                    break;
                case self.actions.AGMT_TRANSFER://转接
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '029000';
                            returnObject.type = 'transfer_action';
                            break;//转接成功
                        case 1 : returnObject.code = '029001';break;//坐席错误
                        case 2 : returnObject.code = '029002';break;//没有通话
                        case 3 : returnObject.code = '029003';break;//通话异常
                        case 4 : returnObject.code = '029004';break;//不在咨询中
                        case 5 : returnObject.code = '029005';break;//咨询状态异常
                        case 6 : returnObject.code = '029006';break;//不允许转接外线
                        default: returnObject.code = '029999';break;//其它转接错误
                    }
                    break;
                case self.actions.AGMT_CHANSPY://监听
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '030000';
                            returnObject.type = 'chanspy_action';
                            returnObject.disableActions = ['chanspy'];
                            returnObject.enableActions = ['intercept', 'breakin'];
                            break;//监听成功
                        case 1 :
                        case 3 : returnObject.code = '030001';break;//监听坐席错误
                        case 2 : returnObject.code = '030002';break;//被监听坐席错误
                        case 4 : returnObject.code = '030004';break;//监听坐席状态不对
                        case 5 : returnObject.code = '030005';break;//不在通话中
                        case 6 : returnObject.code = '030006';break;//通话类型不对
                        case 7 :
                        case 8 : returnObject.code = '030007';break;//通话异常
                        case 9 : returnObject.code = '030009';break;//已被监听或强插
                        case 20 : returnObject.code = '030020';break;//企业错误
                        case 30 : returnObject.code = '030030';break;//常接通模式不能监听
                        default: returnObject.code = '030999';break;//其它监听错误
                    }
                    break;
                case self.actions.AGMT_INTERCEPT://拦截
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '031000';
                            returnObject.type = 'intercept_action';
                            returnObject.disableActions = ['chanspy', 'intercept', 'breakin'];
                            returnObject.enableActions = ['hangup'];
                            break;//拦截成功
                        case 1 : returnObject.code = '031001';break;//其它坐席错误
                        case 2 :
                        case 3 :
                        case 4 :
                        case 5 : returnObject.code = '031002';break;//不在监听中
                        default: returnObject.code = '031999';break;//其它拦截错误
                    }
                    break;
                case self.actions.AGMT_BREAKIN://强插
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '032000';
                            returnObject.type = 'breakin_action';
                            returnObject.disableActions = ['chanspy', 'breakin'];
                            returnObject.enableActions = ['intercept', 'hangup'];
                            break;//强插成功
                        case 1 :
                        case 2 : returnObject.code = '032001';break;//坐席错误
                        case 3 :
                        case 4 :
                        case 5 :
                        case 6 : returnObject.code = '032003';break;//不在监听或强插中
                        default: returnObject.code = '032999';break;//其它强插错误
                    }
                    break;
                case self.actions.AGMT_HOLD://保持
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '033000';
                            returnObject.type = 'hold_action';
                            returnObject.enableActions = ['restore'];
                            returnObject.disableActions = ['hold'];
                            break;//成功
                        case 1 : returnObject.code = '033001';break;//坐席错误
                        case 2 : returnObject.code = '033002';break;//坐席不在通话中
                        case 3 : returnObject.code = '033003';break;//尚未接通
                        case 4 : returnObject.code = '033004';break;//已保持
                        case 5 : returnObject.code = '033005';break;//没有权限
                        default: returnObject.code = '033999';break;//其它保持错误
                    }
                    break;
                case self.actions.AGMT_UNHOLD://恢复
                    switch(obj_responce.Ret) {
                        case 0 : returnObject.code = "034000";
                            returnObject.type = 'restore_action';
                            returnObject.enableActions = ['hold'];
                            returnObject.disableActions = ['restore'];
                            break;//恢复成功
                        default: returnObject.code = "034999";break;//其它恢复错误
                    }
                    break;
                case self.actions.AGMT_EVALUATE://转评价
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '041000';
                            returnObject.type = 'evaluate_action';
                            break;//转评价成功
                        case 1 : returnObject.code = '041001';break;//坐席错误
                        case 2 : returnObject.code = '041002';break;//坐席不在通话中
                        case 3 : returnObject.code = '041003';break;//坐席不在通话中
                        case 4 : returnObject.code = '041004';break;//通话异常
                        case 5 : returnObject.code = '041005';break;//通话类型错误
                        case 6 : returnObject.code = '041006';break;//通话状态不对
                        case 7 : returnObject.code = '041007';break;//通道错误
                        default: returnObject.code = '041999';break;//其它转评价错误
                    }
                    break;
                case self.actions.AGMT_TRANSIVR://转IVR
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '043000';
                            returnObject.type = 'ivr_action';
                            break;//转IVR成功
                        case 1 : returnObject.code = '043001';break;//坐席错误
                        case 2 : returnObject.code = '043002';break;//坐席不在通话中
                        case 3 : returnObject.code = '043003';break;//坐席不在通话中
                        case 4 : returnObject.code = '043004';break;//通话异常
                        case 5 : returnObject.code = '043005';break;//通话类型错误
                        case 6 : returnObject.code = '043006';break;//通话状态不对
                        case 7 : returnObject.code = '043007';break;//通道错误
                        default: returnObject.code = '043999';break;//其它转IVR错误
                    }
                    break;
                case self.actions.AGMT_TRANSQUE://转技能组
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '044000';
                            returnObject.type = 'queue_action';
                            break;//转技能组成功
                        case 1 : returnObject.code = '044001';break;//坐席错误
                        case 2 : returnObject.code = '044002';break;//坐席不在通话中
                        case 3 : returnObject.code = '044003';break;//坐席不在通话中
                        case 4 : returnObject.code = '044004';break;//通话异常
                        case 5 : returnObject.code = '044005';break;//通话类型错误
                        case 6 : returnObject.code = '044006';break;//通话状态不对
                        case 7 : returnObject.code = '044007';break;//通道错误
                        case 8 : returnObject.code = '044008';break;//技能组错误
                        default: returnObject.code = '044999';break;//其它转技能组错误
                    }
                    break;
                case self.actions.AGMT_TRANSVCCQUE://转企业技能组
                    switch(obj_responce.Ret) {
                        case 0 :
                            returnObject.code = '047000';
                            returnObject.type = 'queue_action';
                            break;//转技能组成功
                        case 1 : returnObject.code = '047001';break;//坐席错误
                        case 2 : returnObject.code = '047002';break;//坐席不在通话中
                        case 3 : returnObject.code = '047003';break;//坐席不在通话中
                        case 4 : returnObject.code = '047004';break;//通话异常
                        case 5 : returnObject.code = '047005';break;//通话类型错误
                        case 6 : returnObject.code = '047006';break;//通话状态不对
                        case 7 : returnObject.code = '047007';break;//通道错误
                        case 8 : returnObject.code = '047008';break;//技能组错误
                        case 508 : returnObject.code = '047508';break;//转技能组错误
                        default: returnObject.code = '047999';break;//其它转技能组错误
                    }
                    break;
                case self.actions.AGMT_OLANSWER://常接通模式应答
                    switch(obj_responce.Ret) {
                        case 0 : returnObject.code = '051000';break;//应答成功
                        case 1 : returnObject.code = '051001';break;//坐席错误
                        case 2 : returnObject.code = '051002';break;//不处于通话中
                        case 3 : returnObject.code = '051003';break;//非振铃状态
                        case 20 : returnObject.code = '051020';break;//企业错误
                        case 30 : returnObject.code = '051030';break;//常接通模式不允许
                        default: returnObject.code = '051999';break;//其它挂机错误
                    }
                    break;
                case self.actions.AGMT_BINDTRANS://盲转
                    switch(obj_responce.Ret) {
                        case 0 : returnObject.code = '046000';break;//盲转成功
                        case 1 : returnObject.code = '046001';break;
                        case 2 : returnObject.code = '046002';break;
                        case 3 : returnObject.code = '046003';break;//坐席不在通话中
                        case 5 : returnObject.code = '046005';break;//通话类型不对
                        case 6 : returnObject.code = '046006';break;//呼叫已结束或者呼叫状态不对
                        case 7 : returnObject.code = '046007';break;//通道错误
                        case 99 : returnObject.code = '046099';break;//通道错误
                        default: returnObject.code = '046999';break;//其它盲转错误
                    }
                    break;
                default :
                    returnObject.code = "UNKNOWN_FUNCTION"+obj_responce.Retact;
                    break;
            }
            break;
        case self.returnType.RETURN_CONNECT://保持连接消息返回
            self.agentKeepAlive();
            returnObject.code = '042000';
            break;
        case self.returnType.RETURN_PHONE://电话状态消息返回 如呼叫中,振铃,挂机
            switch(obj_responce.PhoSta) {
                case self.phone_states.PHRN_RING://来电振铃
                case self.phone_states.PHRN_OLRING://长接通模式来电
                    switch(obj_responce.RingType) {
                        case 0://被叫
                            if (obj_responce.OAgId == 0) {
                                //外线直接转坐席
                                returnObject.code = '0508';
                                returnObject.type = 'ring_outer_event';
                                returnObject.agStatus = '外线来电';
                                returnObject.enableActions = ['hangup'];
                            } else {
                                //坐席内线来电
                                returnObject.code = '0500';
                                returnObject.type = 'ring_inner_event';
                                returnObject.agStatus = '内线来电';
                                returnObject.enableActions = ['hangup'];
                            }
                            break;
                        case 1://队列分配
                            returnObject.code = '0501';
                            returnObject.type = 'ring_queue_event';
                            returnObject.agStatus = '技能组分配来电';
                            returnObject.enableActions = ['hangup'];
                            break;
                        case 2: //外呼时呼叫自己
                            returnObject.code = '0502';
                            returnObject.type = 'ring_outbound_event';
                            returnObject.agStatus = '外呼来电';
                            returnObject.enableActions = ['hangup'];
                            if (self.opts.phonetype == 'softphone') {
                                self.fn_answer();
                            }
                            break;
                        case 3://监听时呼叫自己
                            returnObject.code = '0503';
                            returnObject.type = 'ring_chanspy_event';
                            returnObject.agStatus = '监听来电';
                            returnObject.enableActions = ['hangup'];
                            if (self.opts.phonetype == 'softphone') {
                                self.fn_answer();
                            }
                            break;
                        case 4://被咨询
                            returnObject.code = '0504';
                            returnObject.type = 'ring_consult_event';
                            returnObject.agStatus = '咨询来电';
                            returnObject.enableActions = ['hangup'];
                            break;
                        case 5: //自动外呼来电
                            returnObject.code = '0505';
                            returnObject.type = 'ring_autocall_event';
                            returnObject.agStatus = '自动外呼来电';
                            returnObject.enableActions = ['hangup'];
                            break;
                        case 7: //常接通模式呼叫自己;
                            returnObject.code = '0507';
                            break;
                        default : returnObject.code = 'INCOMING_CALL'+obj_responce.RingType;//来电事件
                    }
                    returnObject.msg = self.responseCode[returnObject.code] || returnObject.code;
                    break;
                case self.phone_states.PHRN_OLCONNECT://长接通模式接通
                    self.isCalling = true;
                    returnObject.code = '0800';
                    break;
                case self.phone_states.PHRN_OLHANGUP://长接通断开
                    self.isCalling = false;
                    returnObject.code = '0900';
                    break;
                case self.phone_states.PHRN_OLDISCONNECT://长接通模式接通
                    self.isCalling = false;
                    returnObject.code = '4000';
                    break;
                case self.phone_states.PHRN_CONNECT://接通
                    self.isCalling = true;
                    returnObject.code = '0600';
                    returnObject.type = 'call_connected_event';
                    returnObject.agStatus = '通话中';
                    returnObject.enableActions = ['hangup', 'evaluate', 'consultinner', 'consultouter', 'hold', 'transque', 'transvccque', 'transivr', 'blindtrans', 'intercept', 'breakin'];
                    returnObject.disableActions = ['enable_autocall', 'chanspy'];
                    break;
                case self.phone_states.PHRN_CALLOUT://外呼中
                    returnObject.code = '0400';
                    returnObject.type = 'calling_event';
                    returnObject.agStatus = '呼叫中';
                    returnObject.enableActions = ['hangup'];
                    returnObject.disableActions = ['enable_autocall'];
                    break;
                case self.phone_states.PHRN_OTHERRING://对方振铃
                    returnObject.code = '5500';
                    break;
                case self.phone_states.PHRN_HANGUP ://通话完成
                    self.isCalling = false;
                    self.params  = {};
                    returnObject.code = '0000';
                    returnObject.type = 'call_done_event';
                    returnObject.agStatus = '通话完成';
                    returnObject.disableActions = ['consultinner', 'consultouter', 'hangup', 'evaluate', 'transfer', 'hold', 'restore', 'consultback', 'threeway', 'threewayback', 'transque', 'transvccque', 'transivr', 'blindtrans', 'intercept', 'breakin'];
                    returnObject.enableActions = ['enable_autocall', 'chanspy', 'busy', 'unbusy'];
                    break;
                case self.phone_states.PHRN_SELFCONNECT: // 己方接通
                    returnObject.code = '1000';
                    break;
                case self.phone_states.PHRN_CONSULTOUT: //咨询外呼中
                    returnObject.code = '2100';
                    break;
                case self.phone_states.PHRN_CONSULTCONNECT://咨询接通
                    returnObject.code = '2200';
                    returnObject.enableActions = ['threeway', 'transfer'];
                    returnObject.disableActions = ['consultinner', 'consultouter'];
                    break;
                case self.phone_states.PHRN_CONSULTBACK: //咨询接回
                    returnObject.code = '2300';
                    returnObject.enableActions = ['consultinner', 'consultouter'];
                    returnObject.disableActions = ['consultback', 'transfer', 'threeway'];
                    break;
                case self.phone_states.PHRN_CONSULTFAIL://咨询未呼通
                    returnObject.code = '2400';
                    returnObject.enableActions = ['consultinner', 'consultouter'];
                    returnObject.disableActions = ['consultback', 'transfer', 'threeway'];
                    break;
                case self.phone_states.PHRN_3WAYSUCCESS://三方接通
                    returnObject.code = '2500';
                    returnObject.enableActions = ['threewayback'];
                    returnObject.disableActions = ['consultback', 'transfer', 'threeway'];
                    break;
                case self.phone_states.PHRN_3WAYBACK://三方接回
                    returnObject.code = '2600';
                    returnObject.enableActions = ['consultinner', 'consultouter'];
                    returnObject.disableActions = ['threewayback'];
                    break;
                case self.phone_states.PHRN_CHANSPYSUCCESS://监听成功
                    returnObject.code = '3100';
                    break;
                case self.phone_states.PHRN_BREAKINSUCCESS://强插成功
                    returnObject.code = '3200';
                    break;
                case self.phone_states.PHRN_TRANSFERSUCCESS://转接成功
                    returnObject.code = '3300';
                    break;
                case self.phone_states.PHRN_INTERCEPTSUCCESS://拦截成功
                    returnObject.code = '3400';
                    break;
                case self.phone_states.PHRN_TRANSFER://电话转接 被转电话的坐席有此消息 转接成功
                    returnObject.code = '3500';
                    break;
                default: returnObject.code = 'UNKNOWN_PHONE_FUNCTION'+obj_responce.PhoSta;break; //其他电话事件
            }
            break;
        case self.returnType.RETURN_AGENT://坐席的状态 如系统示忙,系统示闲,占用等
            switch(obj_responce.AgSta) {
                case self.agent_states.AGSTRN_OLUNBUSY://签入置闲--常接通模式下电话接通恢复到之前的状态
                    self.QUEUESTATE = self.que_states.QUESTATE_UNBUSY;
                    returnObject.code = '011';
                    returnObject.queStatus = '空闲';
                    returnObject.enableActions = ['busy', 'dialinner', 'dialouter', 'enable_autocall'];
                    returnObject.disableActions = ['unbusy', 'hangup'];
                    break;
                case self.agent_states.AGSTRN_OLBUSY://签入置忙--常接通模式下电话接通恢复到之前的状态
                    self.QUEUESTATE = self.que_states.QUESTATE_BUSY;
                    returnObject.code = '012';
                    returnObject.queStatus = '忙碌';
                    returnObject.enableActions = ['unbusy', 'dialinner', 'dialouter', 'enable_autocall'];
                    returnObject.disableActions = ['busy', 'hangup'];
                    break;
                case self.agent_states.AGSTRN_OFFLINE://电话离线，常接通模式下电话未接通
                    self.QUEUESTATE = self.que_states.QUESTATE_OFFLINE;
                    returnObject.code = '007';
                    returnObject.queStatus = '电话离线';
                    returnObject.enableActions = ['login'];
                    returnObject.disableActions = '*';
                    break;
                case self.agent_states.AGSTRN_SYSBUSY ://系统置忙
                    self.QUEUESTATE = self.que_states.QUESTATE_BUSY;
                    returnObject.code = '002';
                    returnObject.type = 'system_busy_event';
                    returnObject.queStatus = '忙碌';
                    returnObject.enableActions = ['unbusy', 'dialinner', 'dialouter', 'enable_autocall'];
                    returnObject.disableActions = ['busy', 'hangup'];
                    // 设置具体的置忙原因到参数中
                    self.params['BusyReason'] = obj_responce.AgStaParam;
                    break;
                case self.agent_states.AGSTRN_SYSUNBUSY ://系统置闲
                    self.QUEUESTATE = self.que_states.QUESTATE_UNBUSY;
                    returnObject.code = '003';
                    returnObject.type = 'system_unbusy_event';
                    returnObject.queStatus = '空闲';
                    returnObject.enableActions = ['busy', 'dialinner', 'dialouter', 'enable_autocall', 'chanspy'];
                    returnObject.disableActions = ['unbusy', 'hangup', 'intercept', 'breakin'];
                    break;
                case self.agent_states.AGSTRN_SYSOCCUPY ://系统占用
                    self.QUEUESTATE = self.que_states.QUESTATE_INUSE;
                    returnObject.code = '004';
                    returnObject.queStatus = '系统占用';
                    returnObject.enableActions = ['hangup'];
                    returnObject.disableActions = ['unbusy', 'busy', 'dialinner', 'dialouter', 'chanspy', 'intercept', 'breakin'];
                    break;
                case self.agent_states.AGSTRN_SYSAFTDEAL ://事后处理
                    self.QUEUESTATE = self.que_states.QUESTATE_WAIT;
                    returnObject.code = '005';
                    returnObject.type = 'call_afterwards_event';
                    returnObject.queStatus = '事后处理';
                    returnObject.enableActions = ['unbusy', 'busy'];
                    // 设置事后处理时长到参数中
                    self.params['CallAfterwardsSecs'] = obj_responce.AgStaParam;
                    break;
                default: 	//其他队列事件
                    returnObject.code = 'UNKNOWN_QUE_FUNCTION'+obj_responce.AgSta;
            }
            break;
        case self.returnType.RETURN_QUEUE : //排队消息
            break;
        case self.returnType.RETURN_IVRRESULT: //IVR返回消息
            returnObject.code = '070';
            returnObject.type = 'ivr_return_event';
            returnObject.data = obj_responce.IvrResult;
            break;
        case self.returnType.RETURN_UPDATE: //重载后的数据更新
            var param = obj_responce.Param;
            var data = obj_responce.Data;
            returnObject.code = '080';
            switch (param) {
                case 1://技能组
                    returnObject.type = 'update_queue_event';
                    this.ag_ques = data;
                    break;
                case 2://主叫号码
                    returnObject.type = 'update_caller_event';
                    this.ag_callers = data;
                    break;
                default:
                    break;
            }
            break;
        case self.returnType.RETURN_ERROR : //系统错误告警
            switch(obj_responce.Code) {
                case self.error.ERROR_LOGOFF://强制签出
                    self.isLogin = false;
                    returnObject.code = '024';
                    returnObject.type = 'logout_action';
                    returnObject.agStatus = '未签入';
                    returnObject.queStatus = '未签入';
                    returnObject.enableActions = ['login'];
                    returnObject.disableActions = self.allButtonActions;
                    break;
                case self.error.ERROR_UNBUSY://强制置闲
                    self.fn_unbusy();
                    returnObject.code = '025';
                    break;
                case self.error.ERROR_BUSY://强制置忙
                    self.fn_busy(1);
                    returnObject.code = '026';
                    break;
                case 100:// 网络异常
                    returnObject.code = obj_responce.Code;
                    returnObject.agStatus = '未签入';
                    returnObject.queStatus = '未签入';
                    if (!self.interval) {
                        self.interval = window.setInterval(function () {
                            self.fn_login(self.loginInfo.agentNum, self.loginInfo.agentPass,self.loginInfo.agentPhone,self.QUEUESTATE);
                        }, 10000);
                    }
                    break;
                case 101:// 上一个操作未完成即进行下一个操作
                    returnObject.code = obj_responce.Code;
                    //returnObject.agStatus = '操作未完成';
                    //returnObject.queStatus = '操作未完成';
                    // window.setTimeout(function () {
                    //     self.fn_login(self.loginInfo.agentNum, self.loginInfo.agentPass,self.loginInfo.agentPhone);
                    // }, 30000);
                    break;
                default : returnObject.code = obj_responce.Code;
            }
            break;
        default : break;
    }
    returnObject.msg = self.responseCode[returnObject.code] || returnObject.code;

    return returnObject;
};
WinCall.prototype.defaultEventListener = function (responce) {
    this.log(responce);
};