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
    function changeFontColor() {
        var color = $('#fontcolor option:selected').attr("value");
        $('#dateTime').css("color", color);
    }
    $('#timezone').change(function() {
        getTime();
    });
    $('#fontcolor').change(function() {
        changeFontColor();
    });
    setInterval(function() {
        getTime();
    }, 1000);
});
