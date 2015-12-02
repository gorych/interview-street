package by.gstu.interviewstreet.web.params;


import by.gstu.interviewstreet.web.params.exceptions.RequestParamException;

public class RequestTextParam extends ReqParam {

    public RequestTextParam(Object value) throws RequestParamException {
        super(value);
    }

    @Override
    public void setValue(Object value) throws RequestParamException {
        final int MIN_LENGTH = 1;
        final int MAX_LENGTH = 255;

        super.setValue(value);
        String text = value.toString();
        if (text.length() < MIN_LENGTH || text.length() > MAX_LENGTH) {
            throw new RequestParamException();
        }

    }
}
