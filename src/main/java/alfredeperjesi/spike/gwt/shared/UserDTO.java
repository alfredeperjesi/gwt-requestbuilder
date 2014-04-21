package alfredeperjesi.spike.gwt.shared;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * Created by Alfréd on 21/04/14.
 */
public class UserDTO {
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";

    private final String username;
    private final String password;

    private UserDTO(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(USER_NAME, new JSONString(username));
        jsonObject.put(PASSWORD, new JSONString(password));

        return jsonObject.toString();
    }

    public static UserDTO userDTO(String user, String password) {
        return new UserDTO(user, password);
    }
}
