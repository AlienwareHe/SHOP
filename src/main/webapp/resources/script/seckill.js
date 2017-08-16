//存放主要交互逻辑js

//利用json模块化js
var seckill ={
    //封装秒杀相关ajax的url
    url:{
        now:function () {
            return '/seckill/time/now';
        },
        exposer:function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        excution:function (seckillId,md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execute';
        }
    },
    validate:function (phone) {
        if(phone&&phone.length==11&&!isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    //处理秒杀逻辑
    handleSeckill:function (seckillId,node) {
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>')
        $.post(seckill.url.exposer(seckillId),{},function (res) {
            if(res && res['success']){
                var exposer=res['data'];
                if(exposer['exposed']){
                    var killUrl=seckill.url.excution(seckillId,exposer['md5']);
                    $('#killBtn').one('click',function () {//绑定一次点击事件
                        //执行秒杀请求，首先禁用按钮
                        $(this).addClass('disabled');
                        //发送秒杀请求
                        $.post(killUrl,{},function (res) {
                            if( res && res['success']){
                                var killResult=res['data'];
                                var state=killResult['state'];
                                var stateinfo=killResult['stateInfo'];
                                node.html('<span class="label label-success">'+ stateinfo);
                            }
                        });
                    });
                    node.show();
                }else{
                    //未开启秒杀，因为客户端系统时间可能与服务端时间不一致
                    var now=exposer['now'];
                    var start=exposer['startTime'];
                    var end=exposer['endTime'];
                    //重新计算计时逻辑
                    seckill.countdown(seckillId,now,start,end);

                }
            }
        });
    },
    //计时交互
    countdown:function (nowTime,startTime,endTime,seckillId) {
        var seckillBox=$('#seckill-box');
        if(nowTime>endTime){
            //秒杀结束
            seckillBox.html("秒杀结束");
        }else if(nowTime<startTime){
            //秒杀尚未开始
            seckillBox.html("秒杀未开始");
            //利用countdown插件
            var killTime = new Date(startTime+1000);
            seckillBox.countdown(killTime,function (event) {
                var format = event.strftime('秒杀计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function () {//计时结束后触发事件
                //获取秒杀地址，控制显示逻辑，点击秒杀按钮执行秒杀
                seckill.handleSeckill(seckillId,seckillBox);
            });
        }else{
            //正在秒杀
            seckill.handleSeckill(seckillId,seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail:{
        //详情页初始化
        init:function (params) {
            //在cookie中查找手机号
            var phone = $.cookie('killPhone');

            //验证手机号
            if (!seckill.validate(phone)) {
                //弹出modal
                $('#killPhoneModal').modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//防止点击窗口周围关闭弹出层
                    keyboard: false//防止按键关闭
                });
                $('#killPhoneBtn').click(function () {
                    var killphoneKey = $('#killphoneKey').val();
                    if (seckill.validate(killphoneKey)) {
                        //将电话写入cookie中，并设置有效时间和路径
                        $.cookie('killPhone', killphoneKey, {expires: 7, path: '/seckill'});
                        window.location.reload();
                    } else {
                        $('#killphoneMessage').hide().html("<label class='label label-danger'>手机号错误!</label>").show(500);
                    }
                });
            }
            //已经登录，计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params.seckillId;
            $.get(seckill.url.now(),function (res) {//url处不直接写url地址是为了便于以后的维护和更改
                if(res && res['success']){
                    var nowTime=res['data'];
                    //判断时间逻辑
                    seckill.countdown(nowTime,startTime,endTime,seckillId);
                }
            });
        }
    }

    //详情页初始化
}