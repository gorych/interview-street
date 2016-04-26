var global = global || {};

(function () {

    /*Global ajax callback function*/
    global.ajaxCallback = function onAjaxCallback(data, textStatus, xhr) {
        if (xhr.status === "400" || xhr.status === "405") {
            Materialize.toast("Ошибка при выполнении операции", 2000);
            console.log(xhr.responseText);
        }

        if(xhr.status === "500") {
            console.log("Unknown error from server.")
        }
    };

    global.rewriteUrl = function (relativeUrl) {
        return global.contextPath + relativeUrl;
    };

    global.urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        return results[1] || 0;
    };

    global.scrollToElement = function (element) {
        if (!element || element.length < 1) {
            return;
        }
        var SPEED = 1100;

        var destination = $(element).offset().top - 15;
        $('body').animate({scrollTop: destination}, SPEED);
    };

    $("#hide-chip-btn").click(function () {
        $.get(global.rewriteUrl("/user/hide-chip"), null, global.ajaxCallback);
    });

}());
