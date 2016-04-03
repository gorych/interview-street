<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--JS Render template. Used on the designer page --%>
<script id="stag-list" type="text/x-jsrender">
    <div class='row staggered center'>
        <ul>
            <li><h5>Выберите тип добавляемого вопроса</h5></li>
            <div class='center'>
                <ul class='staggered-item left-align'>
                    <li>
                        <input name='answer-type' type='radio' id='1' value='1'>
                        <label for='1'>Текстовый ответ</label>
                    </li>
                    <li>
                        <input name='answer-type' type='radio' id='2' value='2'>
                        <label for='2'>Одиночный выбор</label>
                    </li>
                    <li>
                        <input name='answer-type' type='radio' id='3' value='3'>
                        <label for='3'>Множественный выбор</label>
                    </li>
                    <li>
                        <input name='answer-type' type='radio' id='4' value='4'>
                        <label for='4'>Рейтинг</label>
                    </li>
                </ul>
            </div>
        </ul>
    </div>
</script>
