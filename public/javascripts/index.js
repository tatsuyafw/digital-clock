// index.js

$(function() {
    function getTime() {
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
    }
    $('#dateTimeUpdate').click(function() {
        getTime();
    });
    setInterval(function() {
        getTime();
    }, 1000);
});
