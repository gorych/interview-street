;(function () {

    $("#clipboard-btn").click(function () {
        new Clipboard("#clipboard-btn");
    });

    new Clipboard('#copy-button');

    $(".question-btn").each(function () {
        showStaggered(this)
    });

    //region Listeners functions

    function showStaggered(btn) {
        $(btn).click(function () {
            /*Remove all stag if exist*/
            $(".row.staggered").remove();

            var $stag = $("<div class='row staggered center'></div>");

            var $header = $("<ul><li><h5>Выберите тип добавляемого вопроса</h5></li></ul>")
            var $body = $("<div class='center'></div>");

            var $item = $("<ul class='staggered-item left-align'></ul>");

            addSubItem($item, 1, "Одиночный выбор");
            addSubItem($item, 2, "Множественный выбор");
            addSubItem($item, 3, "Текстовый ответ");
            addSubItem($item, 4, "Рейтинг");

            $body.append($item);
            $stag.append($header).append($body);

            $(btn).after($stag);

            Materialize.showStaggeredList($stag);
        });

        function addSubItem(item, subId, subTitle) {
            $(item).append("" +
                "<li><input name='question-type' type='radio' id='" + subId + "'/>" +
                "<label for='" + subId + "'>" + subTitle + "</label></li>")
        }
    }

}());