/**
 * Created by Олег on 02.11.2016.
 */

function checker() {
    var pass = $("#password").val();
    var pass_rep = $("#repeated_password").val();

    if (pass !== pass_rep) {
        if ( !$("#repeated_password").parent().hasClass("has-error")) {
            $("#repeated_password").parent().append('\<label class="control-label" id ="passwords_not_matching_warning" for="input_login">Пароли не совпадают</label>');
            $("#repeated_password").parent().addClass("has-error");
        }
        return false;
    } else {

        $("#passwords_not_matching_warning").remove();
        $("#repeated_password").parent().removeClass("has-error");
        return true;
    }
};

