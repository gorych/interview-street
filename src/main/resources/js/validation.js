var validator = validator || {};

(function () {

    validator.checkFormInputs = function () {
        $("input[type='text']").each(function(){
            if($(this).val().length < 1){
                $(this).after($("#error-block").clone().removeClass("hide"));
            }
        });
    };

    validator.toggleSelectValidateClass = function (selector) {
        var hiddenInput = $(selector).prevAll("input.select-dropdown");
        var value = $(selector).val();

        if (!value || value.length < 1) {
            $(hiddenInput)
                .removeClass("valid valid-select")
                .addClass("invalid invalid-select");
        } else {
            $(hiddenInput)
                .removeClass("invalid invalid-select")
                .addClass("valid valid-select");
        }
    };

    validator.toggleInputValidateClass = function (input) {
        if (!input.value || input.validity.patternMismatch) {
            $(input).removeClass("valid").addClass("invalid");
        } else {
            $(input).removeClass("invalid").addClass("valid");
        }
    };

    /*Check date with type 'date'*/
    validator.isValidDate = function (dateField) {
        var dateInput = dateField[0];
        var hiddenInput = document.getElementsByName(dateInput.name + "_submit")[0];

        if (!hiddenInput.value || hiddenInput.validity.patternMismatch) {
            dateInput.classList.remove("valid");
            dateInput.classList.add("invalid");
        } else {
            dateInput.classList.remove("invalid");
            dateInput.classList.add("valid");
        }
    };

    validator.isValidFields = function () {
        var $form = $("#add-interview-form");

        $form.find("[required]").each(function (i, elem) {
            if ($(elem).is("select")) {
                validator.toggleSelectValidateClass(elem);
            } else {
                validator.toggleInputValidateClass(elem);
            }
        });

        return ($form.find("input.invalid").length < 1);
    };

}());
