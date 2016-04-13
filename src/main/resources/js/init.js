(function ($) {
    $(function () {
        $('.slider').slider({full_width: true});

        $('.button-collapse').sideNav();

        /*Min date for datepicker*/
        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);

        $('.datepicker').pickadate({
            // Strings and translations
            monthsFull: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
            monthsShort: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек'],
            weekdaysFull: ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота', 'Воскресенье'],
            weekdaysShort: ['Пон', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб', 'Вскр'],
            showMonthsShort: undefined,
            showWeekdaysFull: undefined,

            // Buttons
            today: null,
            clear: 'Сброс',
            close: 'Выбрать',
            selectYears: 10,
            format: 'yyyy-mm-dd',
            formatSubmit: 'yyyy-mm-dd',
            min: tomorrow,

            onClose: function () {
                validator.isValidDate($("#end-date"));
            }

        });

        $('select').material_select();

        $('textarea').trigger('autoresize');
    });
})(jQuery);