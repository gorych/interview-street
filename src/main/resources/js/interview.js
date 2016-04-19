;(function () {

    $(document).on('input', ".rating", function () {
        global.updateStars($(this));
    });

}());
