const WS_APP = "ws://localhost/stream";
var connection;

$(document).ready(function () {
    connection = new WebSocket(WS_APP);
    connection.onopen = function () { wsOpen() };
    connection.onerror = function (ev) { wsError(ev) };
    connection.onmessage = function (ev) { wsMessage(ev) }
});

function wsOpen() {
    console.log("INF: WebSocket Connection opened")
}

function wsError(error) {
    console.error("ERR: An error occoured while reading or sending from/to the WebSocket Service");
    console.error(error);
}

function wsMessage(event) {

}

$("#formRegister").submit(function() {
    $.ajax({
        url: $(this).attr("action"),
        method: "POST",
        data: $(this).serialize(),
        success: function(response) {
            try {
                var json = JSON.parse(response);
                var notification = $("#register-notification");
                notification.hide();
                notification.html('<div class="alert alert-' + json.type + '">' + json.message + '</div>');
                notification.fadeIn();
                setTimeout(function() {
                    notification.fadeOut();
                }, 1250);
            } catch(e) {}
        }
    });
});

$("#formLogin").submit(function() {
    $.ajax({
        url: $(this).attr("action"),
        method: "POST",
        data: $(this).serialize(),
        success: function(response) {
            try {
                var json = JSON.parse(response);
                var notification = $("#login-notification");
                notification.hide();
                notification.html('<div class="alert alert-' + json.type + '">' + json.message + '</div>');
                notification.fadeIn();
                setTimeout(function() {
                    notification.fadeOut();
                }, 1250);
            } catch(e) {}
        }
    });
});