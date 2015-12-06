package by.gstu.interviewstreet.web.param;


import by.gstu.interviewstreet.web.param.annotation.AnnotationHandler;
import by.gstu.interviewstreet.web.param.annotation.Range;

public class RequestTextParam extends ReqParam {

    public RequestTextParam(Object value) throws RequestParamException {
        super(value);
    }

    @Override
    @Range(min = 1, max = 255)
    public void setValue(Object value) throws RequestParamException {
        final String clazz = RequestTextParam.class.getName();
        final String method = "setValue";

        final int MIN = AnnotationHandler.getMin(clazz, method);
        final int MAX = AnnotationHandler.getMax(clazz, method);

        super.setValue(value);
        String text = value.toString();
        if (text.length() < MIN || text.length() > MAX) {
            throw new RequestParamException();
        }

    }
}
