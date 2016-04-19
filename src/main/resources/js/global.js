var global = global || {};

(function () {

    /*Global ajax callback function*/
    global.ajaxCallback = function onAjaxCallback(data, textStatus, xhr) {
        if (xhr.status === "400" || xhr.status === "405") {
            Materialize.toast("Ошибка при выполнении операции", 2000);
            console.log(xhr.responseText);
        }
    };

    global.rewriteUrl = function (relativeUrl) {
        return global.contextPath + relativeUrl;
    };

    global.urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        return results[1] || 0;
    };

    global.updateStars = function (that) {
        var starCol = $(that).parent().next().empty();
        var value = $(that).val();
        var length = parseInt(value);
        if (!value || (length < 3 || length > 10)) {
            $(that.val(3));
            length = 3;
        }

        var i = 0;
        for (; i < length; i++) {
            $(starCol).append("<i class='small material-icons red-text text-lighten-1'>star_rate</i>");
        }
    };

    $("#hide-chip-btn").click(function () {
        $.get(global.rewriteUrl("/user/hide-chip"), null, global.ajaxCallback);
    });

}());
