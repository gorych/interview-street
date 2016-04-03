;(function () {

    var _pathname = window.location.pathname;
    var _hash = _pathname.split("/")[1];

    var _questionContainer = $("#question-container");

    new Clipboard("#clipboard-btn");

    $("#clipboard-btn").click(function () {
        Materialize.toast("Адрес скопирован в буфер обмена", 2000);
    });

    $("[data-answer]").blur(function () {
        alert("Операция временно недоступна.")
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
                "id": $section.attr("data-question")
            }
        }).done(function (response) {
            if (response !== "success") {
                Materialize.toast("Ошибка при удалении вопроса", 2000);
                return;
            }
            $section.remove();
            Materialize.toast("Вопрос успешно удален", 2000);
        }).fail(function () {
            Materialize.toast("Ошибка при удалении вопроса", 2000);
        });
    });

    _questionContainer.on('click', ".move-up, .move-down", function () {
        moveQuestion(this);
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
        }

        if ($(event.target).is(".move-down")) {
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
            if (response === "error") {
                Materialize.toast("Ошибка при составлении вопроса", 2000);
                return;
            }
            var data = JSON.parse(response);
            console.log(data);

            var $stag = $(".row.staggered");
            var $section = $stag.parent(".section");
            var $btn = $stag.prev(".add-quest-btn");

            var $elem = $section.length ? $section : $btn;

            $elem.after(
                $("#question-template").render(data)
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

}());