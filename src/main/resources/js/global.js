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

}());
