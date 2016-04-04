<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script id="rate-answer-tmpl" type="text/x-jsrender">
    <div data-answer="{{:id}}" class="input-field col offset-l2 l8 m12 s12">
        <input  type="number" min="3" max="10" value="{{:answerType.defaultValue}}"
               title="Количество звезд"/>
        <label class="active">Количество звезд</label>
    </div>
    <div class="col offset-l2 col l8 m12 s12 rating center">
        {^{range start=1 end=answerType.defaultValue}}
            <i class="small material-icons red-text text-lighten-1 hoverable">star_rate</i>
        {{/range}}
    </div>
</script>