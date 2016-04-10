;(function () {

    function Interview() {
        this.id = null;
        this.name = $("[name='name']").val();
        this.endDate = $("#end-date").val();
        this.description = $("[name='description']").val();
        this.audience = $("#audience").val();
        this.goal = $("#goal").val();
        this.hide = true;
        this.type = {
            id: $("[name='type']").val()
        };
    }

    var $postSelect = $("#posts");

    /*Ascent effect*/
    $(document).ready(function () {
        $(".promo-container").removeClass("hide");
        Materialize.fadeInImage('.promo-container')
    });

    $(document).on("click", ".promo", function () {
        $("[name='type']").val($(this).attr("data-type"));
        $(".interview-icon").html($(this).find("i").html());
        $("#title").html($(this).find(".modal-title").html());

        if ($(this).hasClass("close")) {
            $("#goal, #audience")
                .attr("required", true)
                .parent().removeClass("hide");
        }

        if ($(this).hasClass("open")) {
            $("#subdivisions, #posts")
                .attr("required", true)
                .parents(".input-field").removeClass("hide");
        }

        $(".promo-container").addClass("hide");
        $("#add-interview-form").removeClass("hide");

        Materialize.fadeInImage('#add-interview-form');
    });


    $("#submit").click(function () {
        if (!validator.isValidFields()) {
            return;
        }

        var data = [
            new Interview(), $postSelect.val()
        ];

        console.log(JSON.stringify(data));
        $.post("/interview/save", JSON.stringify(data), global.ajaxCallback)
            .done(function (hash) {
                window.location = location.protocol + '//' + location.host + "/" + hash + "/designer";
            });
    });

    $("#subdivisions").change(function () {
        $.post("/editor/load-posts", JSON.stringify($(this).val()))
            .done(function (response) {
                var data = JSON.parse(response);
                if (response.length > 0) {
                    addOptionsToPostSelect(data);
                }
            })
    });

    /*Check correct fields or not*/
    $("select").change(function () {
        validator.toggleSelectValidateClass($(this));
    });

    function addOptionsToPostSelect(data) {
        $postSelect.find("option").remove().end();

        $.each(data, function (index, entry) {
            $.each(entry, function (j, post) {
                var option = document.createElement("option");
                if (index == 0) {
                    $postSelect
                        .append("<option value='-1' disabled selected>Выберите должности</option>")
                        .append("<option value='0'>Все должности</option>");
                }
                option.setAttribute("value", post.id);
                option.textContent = post.name;

                $postSelect.append(option);
            });
        });

        $postSelect.material_select();
    }

}());