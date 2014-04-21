package alfredeperjesi.spike.gwt.server.infrastructure.integration.rest.api;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Alfréd on 18/04/14.
 */
public class LogResource {
    public final String message;

    public final String exceptionClass;

    @JsonCreator
    public LogResource(@JsonProperty("message") String message, @JsonProperty("exceptionClass") String exceptionClass) {
        this.message = message;
        this.exceptionClass = exceptionClass;
    }

    @Override
    public String toString() {
        return "LogResource{" +
                "exceptionClass='" + exceptionClass + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
