$(document).ready(function () {
    $('#addQuestionBtn').click(function () {
        $.ajax({
            url: '/create-question',
            method: 'GET',
        }).done(function (questionId) {
            if (questionId > -1) {
                $("#interview_questions").append("<section class='question'><h5 class='header black-text'>How do you do?</h5></section>");
            }
        });
    });
});




/*<section>
<div class="question">
    <h5 class="header black-text">Ваши предложения</h5>
<div class="answers">
    <form action="#">
    <div class="row">
    <div class="input-field col s6">
    <input id="first_name" type="text" class="validate">
    <label for="first_name">Имя</label>
    </div>
    <div class="input-field col s6">
    <input id="last_name" type="text" class="validate">
    <label for="last_name">Фамилия</label>
    </div>
    <div class="input-field col s12">
    <input id="password" type="password" class="validate">
    <label for="password">Ваши предложения</label>
</div>
</div>
</form>
</div>
</div>
</section>*/