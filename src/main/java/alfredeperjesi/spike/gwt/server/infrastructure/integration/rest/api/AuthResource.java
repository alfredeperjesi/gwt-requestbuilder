package alfredeperjesi.spike.gwt.server.infrastructure.integration.rest.api;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Alfréd on 18/04/14.
 */
public class AuthResource {
    public final String userName;

    public final String password;

    @JsonCreator
    public AuthResource(@JsonProperty("userName") String userName, @JsonProperty("password") String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthResource{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
