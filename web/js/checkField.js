/**
 * Created by Олег on 23.11.2016.
 */
function checkField(fieldid) {

    var textfield = $("#"+fieldid);
    if ($.trim(textfield.val()) === '' || textfield.val() === undefined) {
        //add notification about empty text
        textfield.parent().addClass("has-error");
        return false;
    } else {
        return true;
    }
};



    function isValidDate(fieldid, userFormat) {
        var value = $("#"+fieldid).val();
        // Используем формат по умолчанию, если ничего не указано
        userFormat = userFormat || 'dd.mm.yyyy';

        // Находим разделитель исключая символы месяца, дня и года (в английском варианте - m, d, y)
        var delimiter = /[^mdy]/.exec(userFormat)[0];

        // Создаем массив из месяца, дня и года,
        // то есть мы знаем порядок формата
        var theFormat = userFormat.split(delimiter);

        // Создаем массив из даты пользователя
        var theDate = value.split(delimiter);

        function isDate(date, format) {
            var m, d, y, i = 0, len = format.length, f;
            for (i; i < len; i++) {
                f = format[i];
                if (/m/.test(f)) m = date[i];
                if (/d/.test(f)) d = date[i];
                if (/y/.test(f)) y = date[i];
            }
            return (
                m > 0 && m < 13 &&
                y && y.length === 4 && y < now().getYear - 17 && y > 1900 &&
                d > 0 &&
                    // Проверяем правильность дня месяца
                d <= (new Date(y, m, 0)).getDate()
            );
        }

        if (isDate(theDate, theFormat)){
            return true;
        } else {
            $("#"+fieldid).parent().addClass("has-error");
            return false;
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