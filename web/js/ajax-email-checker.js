/**
 * Created by Олег on 25.11.2016.
 */
var checkEmail = function() {
    var email = $('#email');
    $.ajax({
        'url': '/ajax-email-check',
        'data': {
            'email': email.val()
        },
        'method': 'get',
        'success': function (obj) {
            if (obj.result) {
                $("#email_exists").remove();
                email.parent().removeClass("has-error");
                email.parent().addClass("has-success");
            } else {
                email.parent().removeClass("has-success");
                email.parent().addClass("has-error");
                if ($("#email_exists").length) {

                } else {
                    email.parent().append('\<label class="control-label" id ="email_exists" for="email">Этот email уже используется.</label>');
                }

            }
        }
    });
    if (email.parent().hasClass("has-error")){
        return false;
    } else {
        return true;
    }
};