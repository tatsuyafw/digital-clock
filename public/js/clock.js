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
    function runEffect() {
        $('#toggle-effect').toggle("blind", 500);
    }
    $('#toggle-effect').hide();
    $('#clock-setting-title').click(function() {
        runEffect();
        return false;
    });
    $('#timezone').change(function() {
        getTime();
        return false;
    });
    $('#fontcolor').change(function() {
        changeFontColor();
        return false;
    });
    setInterval(function() {
        getTime();
    }, 1000);
});
