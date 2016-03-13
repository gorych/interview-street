(function ($) {
    $(function () {
        $('.slider').slider({full_width: true});
        $('.modal-trigger').leanModal({
            dismissible: false
        });
        $('.button-collapse').sideNav();
        $('.datepicker').pickadate({selectYears: 20});
        $('select').not('.disabled').material_select();
    });
})(jQuery);