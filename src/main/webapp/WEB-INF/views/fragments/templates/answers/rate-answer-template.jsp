<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script id="rate-answer-tmpl" type="text/x-jsrender">
    <div data-answer="{{:answers[0].id}}" class="input-field col offset-l2 l8 m12 s12">
        <input class="rating"  type="number" min="3" max="10" value="{{:answers[0].type.defaultValue}}"
               title="Количество звезд"/>
        <label class="active">Количество звезд</label>
    </div>
    <div class="col offset-l2 col l8 m12 s12 rating center">
        {^{range start=1 end=answers[0].type.defaultValue}}
            <i class="small material-icons red-text text-lighten-1 hoverable">star_rate</i>
        {{/range}}
    </div>
</script>