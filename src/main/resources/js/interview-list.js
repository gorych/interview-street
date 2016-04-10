;(function () {

    var toastDuration = 2000;
    var $tempCard = null;

    $("#hide-chip-btn").click(function () {
        $.get("/hide-chip", global.ajaxCallback());
    });

    $(document).on("click", ".delete-btn", function () {
        var $interviewId = $(this).attr("data-interview-id");

        $("#submit-delete-btn").attr("data-temp-id", $interviewId);
        $tempCard = $(this).closest(".card");

        $("#delete-interview-modal").openModal();
    });

    $("#submit-delete-btn").click(function () {
        var data = {
            data: JSON.stringify({id: $(this).attr("data-temp-id")})
        };

        $.post("/interview/delete", data, global.ajaxCallback)
            .done(function () {
                $tempCard.remove();

                /*Clean link*/
                $tempCard = null;

                $("#delete-interview-modal").closeModal();
                Materialize.toast("Анкета успешно удалена", toastDuration);
            });
    });

    $(document).on("click", ".lock-btn", function () {
        var that = $(this);
        $.get("/interview/lock/" + $(this).attr("data-interview-id"), global.ajaxCallback)
            .done(function () {
                var $icon = $(that).find("i");
                var lock = ($icon.html() == "lock");

                var msg;
                if (lock) {
                    $icon.html("lock_open");
                    msg = "Анкета открыта для прохождения";
                } else {
                    $icon.html("lock");
                    msg = "Анкета закрыта для прохождения";
                }

                $icon.attr("title", msg);
                Materialize.toast(msg, toastDuration);
            });
    });

    $(".edit-interview-btn").each(function () {
    });

}());