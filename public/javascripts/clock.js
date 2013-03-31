// index.js

$(function() {
    function getTime() {
        $.ajax({
            url: '/time',
            dataType: 'json',
            data: {
                timezone: $('#timezone option:selected').text()
            },
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
    $('#timezone').change(function() {
        getTime();
    });
    setInterval(function() {
        getTime();
    }, 1000);
});
