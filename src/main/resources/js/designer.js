;(function () {

    var _pathname = window.location.pathname;
    var _hash = _pathname.split("/")[1];

    $.templates({
        questTmpl: "#question-template",
        multiAnswTmpl: "#multi-answer-tmpl",
        textAnswTmpl: "#text-answer-tmpl",
        otherAnswTmpl: "#other-answer-tmpl",
        rateAnswTmpl: "#rate-answer-tmpl"
    });

    var _questionContainer = $("#question-container");

    new Clipboard("#clipboard-btn");

    $("#clipboard-btn").click(function () {
        Materialize.toast("Адрес скопирован в буфер обмена", 2000);
    });

    /*Show staggered list*/
    _questionContainer.on('click', ".add-quest-btn", function () {
        $(".row.staggered").remove();

        $(this).after(
            $("#stag-list").render()
        );

        Materialize.showStaggeredList(".staggered");
    });

    _questionContainer.on('click', ".del-quest", function () {
        var $section = $(this).parents(".section");

        $.ajax({
            url: "/designer/del-question",
            method: "POST",
            data: {
                "hash": _hash,
                "id": $section.attr("data-question")
            }
        }).done(function () {
            $section.remove();
            Materialize.toast("Вопрос успешно удален", 2000);
        }).fail(function () {
            Materialize.toast("Ошибка при удалении вопроса", 2000);
        });
    });

    _questionContainer.on('click', ".move-up, .move-down", function () {
        moveQuestion(this);
    });

    _questionContainer.on('click', ".duplicate", function () {
        var $section = $(this).parents(".section");

        $.ajax({
            url: "/designer/duplicate-question",
            method: "POST",
            data: {
                "id": $section.attr("data-question")
            }
        }).done(function (response) {
            var data = JSON.parse(response);

            $section.after(
                $.render.questTmpl(data)
            );

            updateQuestionNumbers();
        }).fail(function () {
            Materialize.toast("Ошибка при дублировании вопроса", 2000);
        });
    });

    _questionContainer.on('click', ".add-answer, .add-text-answer", function (event) {
        var $section = $(this).parents(".section");
        var textType = $(event.target).is(".add-text-answer");
        var $that = $(this);

        $.ajax({
            url: "/designer/add-answer",
            method: "POST",
            data: {
                "hash": _hash,
                "questId": $section.attr("data-question"),
                "textType": textType ? true : false
            }
        }).done(function (response) {
            var data = JSON.parse(response);

            if (textType) {
                $that.parent().after(
                    $.render.otherAnswTmpl.render(data)
                );
                $that.remove();
            } else {
                $that.parent().before(
                    $.render.multiAnswTmpl.render(data)
                );
            }
        }).fail(function () {
            Materialize.toast("Ошибка при добавлении ответа", 2000);
        });
    });

    _questionContainer.on('click', ".del-answer, .del-text-answer", function () {
        var $row = $(this).parents("[data-answer]");
        var $section = $(this).parents(".section");
        var textType = $(event.target).is(".del-text-answer");

        $.ajax({
            url: "/designer/del-answer",
            method: "POST",
            data: {
                "hash": _hash,
                "questId": $section.attr("data-question"),
                "answerId": $row.attr("data-answer")
            }
        }).done(function () {
            if (textType) {
                $row.prev().append(
                    "<i class='add-text-answer small material-icons deep-orange-text' " +
                    "title='Добавить текстовый ответ'>playlist_add</i>"
                );
            }
            $row.remove();
        }).fail(function (xhr) {
            if (xhr.status === 406) {
                Materialize.toast("Для данного типа вопроса <br/>необходимо минимум 2 ответа", 2000);
                return;
            }
            Materialize.toast("Ошибка при удалении ответа", 2000);
        });
    });

    $(document).on('click', "input[name=answer-type]", function () {
        renderQuestionForm(this);
    });

    //region Helper functions

    function moveQuestion(that) {
        var $section = $(that).parents(".section");
        var $curNumber = $section.find(".number").html();
        var $lastNumber = $(".number:last").html();

        var number = parseInt($curNumber);
        var lastNumber = parseInt($lastNumber);

        var isUp, isDown;
        if ($(event.target).is(".move-up")) {
            isUp = true;
            number -= 1;
        } else if ($(event.target).is(".move-down")) {
            isDown = true;
            number += 1;
        }

        if (number > 0 && number <= lastNumber) {
            $.ajax({
                url: "/designer/move-question",
                method: "POST",
                data: {
                    "id": $section.attr("data-question"),
                    "number": number
                }
            }).done(function () {
                    if (isUp) {
                        var $prevSection = $section.prev();
                        $section.insertBefore($prevSection);
                    }

                    if (isDown) {
                        var $nextSection = $section.next();
                        $section.insertAfter($nextSection);
                    }
                    updateQuestionNumbers();
                }
            ).fail(function () {
                Materialize.toast("Ошибка при перемешении вопроса", 2000);
            });
        } else {
            Materialize.toast("Для данного вопроса операция <br/>недоступна", 2000);
        }
    }

    function renderQuestionForm(that) {
        $.ajax({
            url: "/designer/add-question",
            method: "POST",
            data: {
                "hash": _hash, "answerTypeId": $(that).val(),
                "number": findNumber(that)
            }
        }).done(function (response) {
            var data = JSON.parse(response);

            var $stag = $(".row.staggered");
            var $section = $stag.parent(".section");
            var $btn = $stag.prev(".add-quest-btn");

            var $elem = $section.length ? $section : $btn;

            $elem.after(
                $.render.questTmpl.render(data)
            );

            $stag.remove();
            updateQuestionNumbers();
        }).fail(function () {
            Materialize.toast("Ошибка при составлении вопроса", 2000);
        });
    }

    function findNumber(that) {
        var intNumber;

        var prevNumber = $(that).closest(".section").find('.number').html();
        intNumber = parseInt(prevNumber);
        if (intNumber) {
            return (intNumber + 1);
        }

        var nextNumber = $(that).closest('.staggered').next('.section').find('.number').html();
        intNumber = parseInt(nextNumber);
        if (intNumber) {
            return intNumber;
        }

        return 1;
    }

    function updateQuestionNumbers() {
        $(".number").each(function (index) {
            $(this).html(index + 1);
        });
    }

    //endregion

}());