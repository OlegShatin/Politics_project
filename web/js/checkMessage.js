/**
 * Created by Олег on 23.11.2016.
 */
function checkMessage(messageId) {
    messageId = messageId || 'message_text';
    var textfield = $('#'+messageId);
    if ($.trim(textfield.val()) === '') {
        //add notification about empty text
        textfield.parent().addClass("has-error");
        return false;
    } else {
        return true;
    }
};
var removeHasError = function (messageId){
    messageId = messageId || 'message_text';
    var textfield = $('#'+messageId);
    textfield.parent().removeClass("has-error");
};