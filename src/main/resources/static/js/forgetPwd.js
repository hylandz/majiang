/**
 * 收件人邮箱检验
 */
function emailCheck(obj) {
    const regex = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{1,4}){1,3}$/;
    if (obj.val() == null || obj.val().length == 0) {
        obj.parent().attr("class", "col-sm-3 has-error");
        layer.msg('邮箱不为空', {
            icon: 5,
            anim: 6
        });
        obj.focus();
        return false;

    } else if (!regex.test(obj.val())) {
        obj.parent().attr("class", "col-sm-3 has-error");
        layer.msg('邮箱格式不正确', {
            icon: 5,
            anim: 6
        });
        obj.focus();
        return false;
    } else {
        obj.parent().attr("class", "col-sm-3");
        return true;
    }
}

/**
 * 倒计时
 */
function invokeShowTime(obj) {

    var countdown = 60;
    showTime(obj);

    /**
     * 倒计时展示
     * @param obj
     */
    function showTime(obj) {
        //倒计时结束
        if (countdown == 0) {
            $(obj).attr("disabled", false);
            $(obj).text("获取验证码");
            countdown = 60;
            return;
        } else {
            //倒计时每秒显示
            $(obj).attr("disabled", true);
            $(obj).text(countdown + "s 后重新发送");
            countdown--;
        }
        //循环
        setTimeout(function () {
            showTime(obj)
        }, 1000);

    }
}

/**
 * 获取验证码
 */
function getCode(obj) {
    var receiveMail = $("#receiveMail");
    var check = emailCheck(receiveMail);

    if (check) {
        //layer.msg("邮件已发送,请及时查收");
        //invokeShowTime(obj);

        $.ajax({
            url:'getCode',
            data: {
                receiveMail: receiveMail.val()
            },
            dataType: 'json', //服务器返回json格式数据
            type: 'post', //HTTP请求类型
            //timeout: 60000, //超时时间设置为10秒；
            beforeSend: function () {
                layer.msg('loading.....', {icon: 16, shade: 0.01});
            },
            success: function (data) {
                if (data.code == 200) {
                    layer.closeAll('loading');
                    //layer.msg(JSON.stringify(data));
                    layer.msg("邮件已发送,请及时查收");
                    invokeShowTime(obj);

                } else {
                    //layer.msg(JSON.stringify(data));
                    layer.msg("邮件发送失败");
                }
            },
            error: function () {
                layer.closeAll('loading');
                layer.msg("请求失败");

            }
        });
    }
}


/**
 * 验证码验证
 */
function verifyEmailCode() {
    var emailCode = $("#emailCode");
    var check = codeCheck(emailCode);
    if (check) {
        $.ajax({
            url:'emailAuth',
            data: {
                emailCode: emailCode.val()
            },
            dataType: 'json', //服务器返回json格式数据
            type: 'post', //HTTP请求类型
            timeout: 10000, //超时时间设置为10秒；
            success: function (response) {
                if (response.code == 200) {
                    layer.msg(JSON.stringify(response));

                } else {
                    layer.msg(JSON.stringify(response));
                    emailCode.val('');
                }

            },
            error: function () {
                layer.msg('请求失败');
                emailCode.val('');
            }
        });
    }
}


/**
 *
 * 验证码检验
 */
function codeCheck(obj) {

    if (obj.val() == null || obj.val().length == 0) {
        obj.parent().attr("class", "col-sm-3 has-error");
        layer.msg('验证码不为空', {
            icon: 5,
            anim: 6
        });
        obj.focus();
        return false;
    } else {
        obj.parent().attr("class", "col-sm-3");
        return true;
    }
}
