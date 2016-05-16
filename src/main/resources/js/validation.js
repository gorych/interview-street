var validator = validator || {};

(function () {

    validator.toggleSelectValidateClass = function (selector) {
        var hiddenInput = $(selector).prevAll("input.select-dropdown");
        var value = $(selector).val();

        if (value.length < 1) {
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

    /*Check input with type 'date'*/
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

    validator.isValidFields = function ($form) {
        $form.find("[required]").each(function (i, elem) {
            if ($(elem).not("select")) {
                validator.toggleInputValidateClass(elem);
            }
        });

        return ($form.find("input.invalid").length < 1);
    };

    var _errorBlock = "<div class='error-block col s12 m12 l12'>" +
        "<h6 class='error red darken-2 z-depth-1'>" +
        "Вы не ответили на этот вопрос или на его часть.</h6></div>";

    validator.isCorrectForm = function () {
        $(".error-block").remove();
        var isCorrect = true;

        $("[data-quest]").each(function () {
            var input = $(this).find("input").first();

            if ($(input).is(":radio") || $(input).is(":checkbox")) {
                if ($(this).find("input:checked").length < 1) {
                    $(this).append(_errorBlock);
                    isCorrect = false;
                }
            }

            if ($(input).is(":text") || $(input).is(":hidden")) {
                if (!$(this).find("input").val()) {
                    $(this).append(_errorBlock);
                    isCorrect = false;
                }
            }
        });

        $(".optional-text").each(function () {
            if ($(this).closest(".col").prev().find("input").is(":checked") && !$(this).val()) {
                $(this).parents("[data-quest]").append(_errorBlock);
                isCorrect = false;
            }
        });

        global.scrollToElement($(".error-block").first().parent());

        return isCorrect;
    };

}());
