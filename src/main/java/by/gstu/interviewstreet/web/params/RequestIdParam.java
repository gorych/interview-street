package by.gstu.interviewstreet.web.params;


import by.gstu.interviewstreet.web.params.exceptions.RequestParamException;

public class RequestIdParam extends ReqParam {

    public RequestIdParam(Object value) throws RequestParamException {
        super(value);
    }

    @Override
    public void setValue(Object value) throws RequestParamException {
        try {
            int id = Integer.parseInt(value.toString());
            if (id <= 0) {
                throw new RequestParamException("Value " + value + " is incorrect for ID.");
            }
            super.setValue(id);
        } catch (NumberFormatException e) {
            throw new RequestParamException();
        }
    }
}
