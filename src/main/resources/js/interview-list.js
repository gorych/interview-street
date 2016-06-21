;(function () {

    var $tempCard = null;

    $(document).ready(function () {
        $(".prev-page").not(":has(a)").append("<a href='#'><i class='material-icons'>chevron_left</i></a>").addClass("disabled");
        $(".next-page").not(":has(a)").append("<a href='#'><i class='material-icons'>chevron_right</i></a>").addClass("disabled")
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

        $.post(global.rewriteUrl("/interview/delete"), data, global.ajaxCallback)
            .done(function () {
                $tempCard.remove();

                /*Clean link*/
                $tempCard = null;

                $("#delete-interview-modal").closeModal();
                Materialize.toast("Анкета успешно удалена", 2000);
            });
    });

    $(document).on("click", ".lock-btn", function () {
        var that = $(this);
        $.get(global.rewriteUrl("/interview/lock/" + $(this).attr("data-interview-id")), global.ajaxCallback)
            .done(function (response) {
                var $icon = $(that).find("i");
                var lock = ($icon.html() == "lock");

                var msg;
                if (lock) {
                    $icon.html("lock_open");
                    msg = "Анкета открыта для прохождения";
                } else {
                    $icon.html("lock");
                    msg = "Анкета закрыта для прохождения";

                    var data = JSON.parse(response);
                    var $btnHolder = $(that).parent().next();
                    $btnHolder.find(".question-list")
                        .attr("href", global.rewriteUrl("/editor/" + data.hash + "/designer"));
                    $btnHolder.find(".statistics-btn")
                        .attr("href", global.rewriteUrl("/statistics/" + data.hash));
                }

                $icon.attr("title", msg);
                Materialize.toast(msg, 2000);
            });
    });

}());