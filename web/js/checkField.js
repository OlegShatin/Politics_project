/**
 * Created by Олег on 23.11.2016.
 */
function checkField(fieldid) {
    var textfield = $("#"+fieldid);
    if (textfield.val() === '') {
        //add notification about empty text
        textfield.parent().addClass("has-error");
        return false;
    } else {
        return true;
    }
};
var removeHasError = function (fieldid){
    var textfield = $("#"+fieldid);
    textfield.parent().removeClass("has-error");
};