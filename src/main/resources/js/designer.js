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

    $("#question-container").on('click', ".add-quest-btn", function () {
        $(".row.staggered").remove();

        $(this).after(
            $("#stag-list").render()
        );

        Materialize.showStaggeredList(".staggered");
    });

    $(document).on('click', "input[name=answer-type]", function () {
        showQuestionForm(this);
    });

    //region Helper functions

    function showQuestionForm(that) {
        $.ajax({
            url: "/build-form",
            method: "POST",
            data: {"hash": _hash, "answerTypeId": $(that).attr("id")}
        }).done(function (response) {
            if (response === "error") {
                Materialize.toast("Ошибка при составлении формы", 2000);
                return;
            }

            var data = JSON.parse(response);

            var $stag = $(".row.staggered");
            var $btn = $stag.prev(".add-quest-btn");
            var $form = $("#form-template").render(data);

            $btn.after($form);
            $stag.remove();

            updateQuestionNumbers();
            Materialize.fadeInImage($form);
        }).fail(function () {
            Materialize.toast("Ошибка при составлении формы", 2000);
        });
    }

    function updateQuestionNumbers() {
        $(".number").each(function (index) {
            $(this).html(index + 1);
        });
    }

}());