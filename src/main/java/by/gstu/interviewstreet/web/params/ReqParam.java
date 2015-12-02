package by.gstu.interviewstreet.web.params;

import by.gstu.interviewstreet.web.params.exceptions.RequestParamException;

import java.io.Serializable;

public class ReqParam implements Serializable {

    private Object value;

    public ReqParam() {
    }

    public ReqParam(Object value) throws RequestParamException {
        setValue(value);
    }

    public Object getValue() {
        return value;
    }

    public Integer intValue() {
        return (Integer)getValue();
    }

    public String stringValue() {
        return getValue().toString();
    }

    public void setValue(Object value) throws RequestParamException {
        if (value == null) {
            throw new RequestParamException("");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return "ReqParam{" +
                "value=" + value +
                '}';
    }
}
