;(function () {

    var _pathname = window.location.pathname;
    var _hash = _pathname.split("/")[1];

    new Clipboard("#clipboard-btn");

    $("#clipboard-btn").click(function () {
        Materialize.toast("Адрес скопирован в буфер обмена", 2000);
    });

    $("[data-answer]").blur(function () {
        alert("Операция временно недоступна.")
    });

    /*Show staggered list*/
    $("#question-container").on('click', ".add-quest-btn", function () {
        $(".row.staggered").remove();

        $(this).after(
            $("#stag-list").render()
        );

        Materialize.showStaggeredList(".staggered");
    });

    $(document).on('click', "input[name=answer-type]", function () {
        renderQuestionForm(this);
    });

    //region Helper functions

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
                Materialize.toast("Ошибка при составлении формы", 2000);
                return;
            }
            var data = JSON.parse(response);
            var $stag = $(".row.staggered");
            var $btn = $stag.prev(".add-quest-btn");

            $btn.after(
                $("#question-template").render(data)
            );

            $stag.remove();
            updateQuestionNumbers();
        }).fail(function () {
            Materialize.toast("Ошибка при составлении формы", 2000);
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