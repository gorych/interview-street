<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script id="optional-answer-tmpl" type="text/x-jsrender">
     <div data-answer="{{:id}}" class="row narrow-row">
        <div class="input-field  col offset-l2 l7 m11 s11">
            <input type="text" length="100"
                   value="Другой вариант" title="Примечание для текстового ответа"/>
            <label class="active">Введите примечание</label>
        </div>
        <div class="col icon-col l1 m1 s1">
            <i class="del-text-answer small material-icons red-text text-lighten-1"
               title="Удалить ответ">delete_forever</i>
        </div>
    </div>
</script>
