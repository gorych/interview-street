;(function () {

    var _pathname = window.location.pathname;
    var _hash = _pathname.split("/")[2];

    new Clipboard("#clipboard-btn");

    $(".question-btn").each(function () {
        showStaggered(this)
    });

    //region Listeners functions

    function showStaggered(btn) {
        $(btn).click(function () {
            /*Remove all stag if its exist*/
            $(".row.staggered").remove();

            var $stag = $("<div class='row staggered center'></div>");

            var $header = $("<ul><li><h5>Выберите тип добавляемого вопроса</h5></li></ul>")
            var $body = $("<div class='center'></div>");

            var $item = $("<ul class='staggered-item left-align'></ul>");

            addSubItem($item, 1, "Текстовый ответ");
            addSubItem($item, 2, "Одиночный выбор");
            addSubItem($item, 3, "Множественный выбор");
            addSubItem($item, 4, "Рейтинг");

            $body.append($item);
            $stag.append($header).append($body);

            $(btn).after($stag);

            $("input[name=answer-type]").each(function () {
                $(this).click(function () {
                    var answerTypeId = $(this).val();
                    buildQuestionForm(answerTypeId);
                });
            });

            Materialize.showStaggeredList($stag);
        });

        function addSubItem(item, typeId, subTitle) {
            var input = "<input name='answer-type' type='radio' id='" + typeId + "' value='" + typeId + "'/>";
            var label = "<label for='" + typeId + "'>" + subTitle + "</label>";
            var li = "<li></li>";

            $(item).append($(li).append(input).append(label));
        }
    }

    function buildQuestionForm(answerTypeId) {
        $.ajax({
            url: "/build-form",
            method: "POST",
            data: {"hash": _hash, "answerTypeId": answerTypeId}
        }).done(function (response) {
            if (response === "error") {
                Materialize.toast("Ошибка при составлении формы", 2000);
                return;
            }
            console.log(JSON.parse(response));
        }).fail(function () {
            Materialize.toast("Ошибка при составлении формы", 2000);
        });
    }

    function renderForm(forms) {
        $(".row.staggered").remove();


        //TODO
    }

}());