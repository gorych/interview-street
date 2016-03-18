(function ($) {
    $(function () {
        $('.slider').slider({full_width: true});
        $('.modal-trigger').leanModal({
            dismissible: false
        });
        $('.button-collapse').sideNav();

        /*Default min date for datepicker*/
        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);

        $('.datepicker').pickadate({
            selectYears: 10,
            format: 'dd-mmm-yyyy',
            formatSubmit: 'yyyy/mm/dd',
            min: tomorrow
        });
        $('select').material_select();
    });
})(jQuery);