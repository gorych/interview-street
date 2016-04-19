;(function () {

    var _questionContainer = $("#question-container");
    var _hash = $.cookie("hash");

    $.templates({
        questTmpl: "#question-template",
        multiAnswTmpl: "#multi-answer-tmpl",
        textAnswTmpl: "#text-answer-tmpl",
        optionalAnswTmpl: "#optional-answer-tmpl",
        rateAnswTmpl: "#rate-answer-tmpl"
    });

    new Clipboard("#clipboard-btn");

    $("#init-tbox")
        .val($("#initial-text").val())
        .blur(function () {
            var data = {
                hash: _hash,
                text: $(this).val()
            };
            $.post(global.rewriteUrl("/interview/update-introductory-text"), data, global.ajaxCallback);
        });

    $("#clipboard-btn").click(function () {
        Materialize.toast("Адрес скопирован в буфер обмена", 2000);
    });

    /*Reset focused input to save its value*/
    $(window).on("beforeunload", function () {
        $("input:focus, textarea:focus").blur();
    });

    /*Show list of question types*/
    _questionContainer.on('click', ".add-quest-btn", function () {
        $(".row.staggered").remove();
        $(this).after($("#stag-list").render());

        Materialize.showStaggeredList(".staggered");
    });

    /*Select focused input content*/
    _questionContainer.on("focus", "input", function () {
        $(this).select();
    });

    /*Save data when input has lost focus*/
    _questionContainer.on("blur", "input", function () {
        var data = {
            "questId": $(this).parents("[data-question]").attr("data-question"),
            "text": $(this).val()
        };

        if ($(this).parents().is("[data-answer]")) {
            data.answerId = $(this).parents("[data-answer]").attr("data-answer");
            $.post(global.rewriteUrl("/designer/save-answer"), data, global.ajaxCallback);
            return;
        }

        if ($(this).parents().is("[data-question]")) {
            data.hash = _hash;
            $.post(global.rewriteUrl("/designer/save-question"), data, global.ajaxCallback);
        }
    });

    /*Del question btn listener*/
    _questionContainer.on('click', ".del-quest", function () {
        var $section = $(this).parents(".section");
        var data = {
            "hash": _hash,
            "id": $section.attr("data-question")
        };

        $.post(global.rewriteUrl("/designer/del-question"), data, global.ajaxCallback)
            .done(function () {
                $section.remove();
                updateQuestionNumbers();
                Materialize.toast("Вопрос успешно удален", 2000);
            });
    });

    /*Move question up or down*/
    _questionContainer.on('click', ".move-up, .move-down", function () {
        var $section = $(this).parents(".section");

        var number;
        var success;
        if ($(event.target).is(".move-up")) {
            number = findPrevOrNextNumber(this, "prev");
            success = function () {
                var $prevSection = $section.prev();
                $section.insertBefore($prevSection);
            };
        }

        if ($(event.target).is(".move-down")) {
            number = findPrevOrNextNumber(this, "next");
            success = function () {
                var $nextSection = $section.next();
                $section.insertAfter($nextSection);
            };
        }

        var lastNumber = $(".number:last").html();
        if (!number || number > lastNumber) {
            Materialize.toast("Для данного вопроса операция <br/>недоступна", 2000);
            return;
        }

        var data = {
            "id": $section.attr("data-question"),
            "number": number
        };

        $.post(global.rewriteUrl("/designer/move-question"), data, global.ajaxCallback)
            .done(function () {
                success();
                updateQuestionNumbers();
            });
    });

    /*Duplicate question*/
    _questionContainer.on('click', ".duplicate", function () {
        var $section = $(this).parents(".section");
        $.post(global.rewriteUrl("/designer/duplicate-question"), {"id": $section.attr("data-question")}, global.ajaxCallback)
            .done(function (response) {
                var data = JSON.parse(response);

                $section.after($.render.questTmpl(data));
                updateQuestionNumbers();
            });
    });

    /*Add new answer*/
    _questionContainer.on('click', ".add-answer, .add-text-answer", function (event) {
        var textType = $(event.target).is(".add-text-answer") ? true : false;
        var $section = $(this).parents(".section");
        var $that = $(this);

        var data = {
            "hash": _hash,
            "questId": $section.attr("data-question"),
            "textType": textType
        };

        var success;
        if (textType) {
            success = function (obj) {
                $that.parent().after($.render.optionalAnswTmpl.render(obj));
                $that.remove();
            };
        } else {
            success = function (obj) {
                $that.parent().before($.render.multiAnswTmpl.render(obj));
            };
        }

        $.post(global.rewriteUrl("/designer/add-answer"), data, global.ajaxCallback)
            .done(function (response) {
                var obj = JSON.parse(response);
                console.log(obj);
                success(obj);
            });
    });

    /*Del answer from question*/
    _questionContainer.on('click', ".del-answer, .del-text-answer", function () {
        var $row = $(this).parents("[data-answer]");
        var $section = $(this).parents(".section");
        var textType = $(event.target).is(".del-text-answer");

        var data = {
            "hash": _hash,
            "questId": $section.attr("data-question"),
            "answerId": $row.attr("data-answer")
        };

        $.post(global.rewriteUrl("/designer/del-answer"), data, global.ajaxCallback)
            .done(function () {
                if (textType) {
                    $row.prev().append(
                        "<i class='add-text-answer small material-icons deep-orange-text' " +
                        "title='Добавить текстовый ответ'>playlist_add</i>"
                    );
                }
                $row.remove();
            })
            .fail(function (xhr) {
                if (xhr.status === 406) {
                    Materialize.toast("Для данного типа вопроса <br/>необходимо минимум 2 ответа <br/>(не включая текстовый)", 3000);
                }
            });
    });

    _questionContainer.on('input', ".rating", function () {
        global.updateStars($(this));
    });

    /*Listener for question types list. Add new question*/
    $(document).on('click', "input[name=answer-type]", function () {
        var data = {
            "hash": _hash,
            "questTypeId": $(this).val(),
            "number": findSuitableNumber($(this))
        };

        $.post(global.rewriteUrl("/designer/add-question"), data, global.ajaxCallback)
            .done(function (response) {
                var data = JSON.parse(response);

                var $stag = $(".row.staggered");
                var $section = $stag.parents(".section");
                var $btn = $stag.prev(".add-quest-btn");

                var $elem = $section.length ? $section : $btn;
                $elem.after($.render.questTmpl.render(data));

                $stag.remove();
                updateQuestionNumbers();
            });
    });

    //region Helper functions

    /*Find prev or next number*/
    function findPrevOrNextNumber(that, which) {
        var $section = $(that).parents(".section");
        var $curNumber = $section.find(".number").html();
        var number = parseInt($curNumber);

        if (which === "prev") {
            return (number - 1);
        }

        if (which === "next") {
            return (number + 1);
        }

        return -1;
    }

    /*Find number of new question*/
    function findSuitableNumber(that) {
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

    /*Update all question numbers*/
    function updateQuestionNumbers() {
        $(".number").each(function (index) {
            $(this).html(index + 1);
        });
    }

    //endregion
}());