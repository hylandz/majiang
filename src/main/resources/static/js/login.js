var username = null;
var pwd = null;
//var confirm_password = null;
var remember = null;
var imageCode = null;
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


    /**
     * 看不清,换一张
     */

    function changeImageCode(){
        const image_url = document.getElementById("img-captcha");
        image_url.attr("src","/code/image");
    }
});


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
};

/**
 * 切换为登录页面
 */
function doLoginNow() {
    //window.location.href='login.html';
    $(".regPhone").css("display", "none");
    $(".regConfirm").css("display", "none");
    $(".regProtocol").css("display", "none");
    $(".loginRemember").css("display", "block");
    $(".titleLogin").css("display", "block");
    $(".titleReg").css("display", "none");
    $("#btnBig").attr("class", "btn btn-primary btnLogin")
    $("#btnBig").text("登录");
    $('.bigBtnDown').css("display", "block")
}

/**
 * 切换为注册页面
 */
function doRegister() {
    //window.location.href='login.html';
    $(".regPhone").css("display", "block");
    $(".regConfirm").css("display", "block");
    $(".regProtocol").css("display", "block");
    $(".loginRemember").css("display", "none");
    $(".titleLogin").css("display", "none");
    $(".titleReg").css("display", "block");
    $("#btnBig").attr("class", "btn btn-primary btnRegister")
    $("#btnBig").text("注册");
    $('.bigBtnDown').css("display", "none")
}