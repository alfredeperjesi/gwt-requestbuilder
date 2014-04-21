package alfredeperjesi.spike.gwt.shared;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * Created by Alfréd on 21/04/14.
 */
public class ExceptionDTO {
    private static final String EXCEPTION_CLASS = "exceptionClass";
    private static final String MESSAGE = "message";

    private final String exceptionClass;
    private final String message;

    private ExceptionDTO(String exceptionClass, String message) {
        this.exceptionClass = exceptionClass;
        this.message = message;
    }

    public static ExceptionDTO exceptionDTO(Throwable throwable) {
        return new ExceptionDTO(throwable.getClass().getName(), throwable.getMessage());
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ExceptionDTO{" +
                "exceptionClass='" + exceptionClass + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String toJson() {
        JSONObject json = new JSONObject();

        json.put(EXCEPTION_CLASS, new JSONString(getExceptionClass()));
        json.put(MESSAGE, new JSONString(getMessage()));

        return json.toString();
    }
}
