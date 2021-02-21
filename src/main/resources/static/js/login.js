let username = null;
let pwd = null;
let confirm_password = null;
let remember = null;
let imageCode = null;
let phone = null;
$(function () {

    $('[data-toggle="tooltip"]').tooltip();

    username = $('#username');
    pwd = $('#password');
    phone = $('#phone');
    remember = $("#rem");
    imageCode = $('#imageCode');
    confirm_password = $('#confirmPwd');
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
     * 确认密码
     */
    confirm_password.on("blur", function () {
        if (confirm_password.val() == null || confirm_password.val()!== pwd.val()) {
            layer.tips('二次密码不正确', '#confirmPwd');
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
    /**
     * 手机号码
     */
    phone.on("blur", function () {
        if (phone.val() == null || (phone.val()).length === 0) {
            layer.tips('手机号码不为空', '#verifyCode', {tips: 2});
            this.focus();
        }
    });


});

/**
 * 看不清,换一张
 */

function reloadCode(){
    $("#img-captcha").attr("src","/code/image?data=" + new Date().getTime());
}

/**
 * 登录
 */
function login() {
    $.ajax({
        url: 'login',
        data: {
            username: username.val(),
            password: pwd.val(),
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
        $.post("/user/register", {
            username: username.val(),
            password: pwd.val(),
            phone: phone.val(),
            imageCode: imageCode.val(),
        }, function (r) {
            if (r.code === 200){
                alert("注册成功")
                window.location.href="/login";
            }else {
                alert('error:' + r.message);
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

/**
 * 打开一个窗口
 * @param url 跳转窗口url
 * @param width 展示窗口宽度
 * @param height 展示窗口高度
 */
function openWindow(url, width, height) {
    width = width || 650;
    height = height || 400;
    const left = (window.screen.width - width) / 2;
    const top = (window.screen.height - height) / 2;
    window.open(url, "_blank", "toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, left=" + left + ", top=" + top + ", width=" + width + ", height=" + height);
}


/**
 * step1:获取Authorization Code
 */
function qqLogin() {
    const qqAppId = '101855625'; // 上面申请得到的appid
    const redirect_uri = 'http://192.168.1.105:8887/callbackToQ'; // 前面设置的回调地址
    const state = '2015354107'; // 防止CSRF攻击的随机参数，必传，登录成功之后会回传，最好后台自己生成然后校验合法性
    openWindow('https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id='+ qqAppId + '&redirect_uri='+ encodeURIComponent(redirect_uri) + '&state='+ state);
}


