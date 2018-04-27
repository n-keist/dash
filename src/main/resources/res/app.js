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