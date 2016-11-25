/**
 * Created by Олег on 23.11.2016.
 */
function checkMessage() {
    var textfield = $("#message_text");
    if ($.trim(textfield.val()) === '') {
        //add notification about empty text
        textfield.parent().addClass("has-error");
        return false;
    } else {
        return true;
    }
};
var removeHasError = function (){
    var textfield = $("#message_text");
    textfield.parent().removeClass("has-error");
};