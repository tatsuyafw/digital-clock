// index.js

$(function() {
    $('#dateTimeUpdate').click(function() {
        $.ajax({
            url: '/time',
            dataType: 'json',
            success: function(data) {
                $('#dateTime').html(data["dateTime"]);
            },
            error: function(data) {
                console.log("error");
            }
        });
    });
});
