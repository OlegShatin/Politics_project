/**
 * Created by Олег on 23.11.2016.
 */
function checkField(fieldid) {

    var textfield = $("#"+fieldid);
    if (textfield.val() === '' || textfield.val() === undefined) {
        //add notification about empty text
        textfield.parent().addClass("has-error");
        return false;
    } else {
        return true;
    }
};
function checkMinLegth(fieldid, minimum) {
    var field = $("#"+fieldid);
    var regExp = new RegExp('^[0-9]+$');
    if (field.val().length < minimum || !regExp.test(field.val())) {
        field.parent().removeClass("has-success");
        field.parent().addClass("has-error");
        return false;
    } else {
        return true;
    }
};

var removeHasError = function (fieldid){
    var textfield = $("#"+fieldid);
    textfield.parent().removeClass("has-error");
};