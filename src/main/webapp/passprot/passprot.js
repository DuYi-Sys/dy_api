function sendCode() {
    var email = $('#email').val().trim();
    console.log(email);
    $.ajax({
        type: "GET",
        url: "/sendSecurityCode?email="+email,
        dataType:"json",
        success: function (res) {
            alert(res.msg)
        }
    });
}
function confirmAlter() {
    var email = $('#email').val().trim();
    var password = $('#password').val().trim();
    var rePassword = $('#rePassword').val().trim();
    var code = $('#code').val().trim();
    $.ajax({
        type: "GET",
        url: "/changePassword?email="+ email + "&password=" + password + "&rePassword=" + rePassword + "&code=" + code,
        dataType:"json",
        success: function (res) {
            alert(res.msg);
        }
    });
}