;(function () {

    $(document).ready(function(){
        $(".selected").html("star");
    });

    $(".rating i").hover(function() {
       $(this).html("star");
    });

    $(".rating i").mouseout(function() {
        $(this).parents(".rating").find("i:not('.selected')").html("star_border");
    });

    $(".rating i").click(function() {
        $(this).parents(".rating").find("i")
            .html("star_border")
            .removeClass("selected");

        $(this)
            .addClass("selected")
            .html("star");

        var value = $(this).next().html();
        $(this).parents(".question").find("input").val(value);
    });

}());
