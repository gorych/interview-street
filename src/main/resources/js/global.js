var global = global || {};

(function () {

    /*Global ajax callback function*/
    global.ajaxCallback = function onAjaxCallback(xhr) {
        if (xhr.status === "400" || xhr.status === "405") {
            Materialize.toast("Ошибка при выполнении операции", 2000);
            console.log(xhr.responseText);
        }
    };

    global.urlParam = function(name){
        var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        return results[1] || 0;
    };

}());
