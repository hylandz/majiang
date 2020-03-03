let username = null;
let pwd = null;
//var confirm_password = null;
let remember = null;
let imageCode = null;
//var phone = null;
$(function () {

    $('[data-toggle="tooltip"]').tooltip();

    username = $('#username');
    pwd = $('#password');
    remember = $("#rem");
    imageCode = $('#imageCode');

    /**
     * 检验用户名
     */
    username.on("click", function () {
        layer.tips('用户名为您登录的账号', '#username');
    }).on("blur", function () {
        if (username.val() == null || username.val().length === 0) {
            layer.tips('用户名不能为空', '#username');
            this.focus();
        }
    });


    /**
     * 校验密码
     */
    pwd.on("click", function () {
        layer.tips('数字和字母组合,至少6位数', '#password');
    }).on("blur", function () {
        if (pwd.val() == null || pwd.val().length <= 5) {
            layer.tips('密码格式不正确', '#password');
            this.focus();
        }
    });


    /**
     * 验证码校验
     */
    imageCode.on("blur", function () {
        if (imageCode.val() == null || (imageCode.val()).length === 0) {
            layer.tips('验证码不为空', '#verifyCode', {tips: 2});
            this.focus();
        }
    });


});

/**
 * 看不清,换一张
 */

function changeImageCode(){
    const image_url = document.getElementById("img-captcha");
    image_url.attr("src","/code/image");
}

/**
 * 登录
 */
function login() {
    $.ajax('login', {
        data: {
            username: username.val(),
            password: pwd.val(),
            imageCode: imageCode.val(),
            rememberMe: remember.is(':checked')
        },
        dataType: 'json',//服务器返回json格式数据
        type: 'post',//HTTP请求类型
        timeout: 10000,//超时时间设置为10秒；
        success: function (response) {
            if (response.code === 200) {
                window.open("/", "_self");
            } else {
                layer.msg(response.message, function () {

                });
            }
        },
        error: function () {
            layer.msg('请求服务失败', function () {

            });
        }
    });
}

/**
 * 用户注册
 */
function registerUser() {
        $.post("/user/register", JSON.stringify({
            username: username.val(),
            password: pwd.val(),
            imageCode: imageCode.val(),
        }), function (r,s) {
            if (s === 'success'){
                alert("注册成功")
                window.location.href="/login";
            }else {
                alert('error:' + s);
            }
        });
}

/**
 * 登录页面,注册页面切换
 */
function doOptions(obj) {
    const  attr = $(obj).attr("class");
    if (attr.indexOf('btnLog') !== -1){
        window.location.href='/login';
    }else {
        window.location.href='/register';
    }
}

