/**
 * Created by Олег on 16.11.2016.
 */
var up = function (comment_id) {
    var counter = $('#counter' + comment_id);
    $.ajax({
        'url': '/ajax-comment-rate',
        'data': {
            'q': true,
            'id': comment_id
        },
        'method': 'get',
        'success': function (obj) {
            counter.text(parseInt(counter.text(), 10) + 1);;
        }
    });
};
var down = function (comment_id) {
    var counter = $('#counter' + comment_id);
    $.ajax({
        'url': '/ajax-comment-rate',
        'data': {
            'q': false,
            'id': comment_id
        },
        'method': 'get',
        'success': function (obj) {
            counter.text(parseInt(counter.text(), 10) - 1);
        }
    });
};