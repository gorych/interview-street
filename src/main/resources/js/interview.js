;(function () {

    $(".rating i").click(function () {
        $(this).parent().find("i").removeClass("selected");
        $(this)
            .addClass("selected")
            .prevUntil("input").addClass("selected");

    });

}());
