package org.RecUnitTest;

import com.google.common.base.Objects;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 25/11/13
 * Time: 08:55
 * To change this template use File | Settings | File Templates.
 */
public class Record {
    private List<Object> arguments;
    private Object result;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    private String methodName;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }



    public List<Object> getArguments() {
        return arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("arguments", arguments)
                .add("result", result)
                .add("methodName", methodName)
                .toString();
    }
}
