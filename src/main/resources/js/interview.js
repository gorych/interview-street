;(function () {

    var $ratingStar = $(".rating i");

    $(document).ready(function () {
        $(".selected").html("star");
    });

    $ratingStar.hover(function () {
        $(this).html("star");
    });

    $ratingStar.mouseout(function () {
        $(this).parents(".rating").find("i:not('.selected')").html("star_border");
    });

    $ratingStar.click(function () {
        $(this).parents(".rating").find("i")
            .html("star_border")
            .removeClass("selected");

        $(this)
            .addClass("selected")
            .html("star");

        var value = $(this).next().html();
        $(this).parents(".question").find("input").val(value);
    });

    $(".optional-answer").change(function () {
        var input = $(this).closest(".col").next().find(".optional-text");
        if ($(this).is(":checked")) {
            $(input).focus();
        }
    });

    $(".optional-text").click(function () {
        $(this).closest(".col").prev().find("input").prop("checked", true);
    });

    $("#send-form-btn").click(function () {
        if (!validator.isCorrectForm()) {
            return;
        }

        var questions = [];

        /*Collect data for server*/
        $("[data-quest]").each(function () {
            var questId = $(this).attr("data-quest");

            var answers = [];
            $(this).find("input:checked").each(function () {
                var label = $(this).next();

                var id = $(this).attr("id");
                var text = ($(label).is("label") && !$(this).hasClass("optional-answer"))
                    ? $(label).html() : $(this).parent().next().find("input").val();
                answers.push({id: id, text: text});
            });

            $(this).find("input[type='hidden'], input[type='text']:not('.optional-text')").each(function () {
                answers.push({id: $(this).attr("id"), text: $(this).val()})
            });

            questions.push({id: questId, answers: answers});
        });

        alert(JSON.stringify(questions));

        var data = {
            "hash": $("#hash").val(),
            "data": JSON.stringify(questions)
        };
        $.post(global.rewriteUrl("/respondent/send/interview"), data, global.ajaxCallback)
            .done(function(){
                alert("OK11111");
            });
        //alert(JSON.stringify(questions));
    });


}());
