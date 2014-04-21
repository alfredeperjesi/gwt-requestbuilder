package alfredeperjesi.spike.gwt.client;

import alfredeperjesi.spike.gwt.shared.ExceptionDTO;
import alfredeperjesi.spike.gwt.shared.UserDTO;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;

import javax.ws.rs.core.MediaType;

import static alfredeperjesi.spike.gwt.shared.ExceptionDTO.exceptionDTO;
import static alfredeperjesi.spike.gwt.shared.UserDTO.userDTO;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtRequestBuilder implements EntryPoint {
    private static final String SEND_BUTTON_CONTAINER = "sendButtonContainer";
    private static final String LOG_IN = "Log in";
    private static final String LOG_OUT = "Log out";
    private static final String GET_TABLES = "Get tables";
    private static final String EMPTY = "";
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";
    private static final UserDTO USER_DTO = userDTO(USER_NAME, PASSWORD);
    private static final String OK = " OK";
    private static final String FAILED = " failed ";
    private static final String ERROR = "Error ";

    private static final String REST_AUTH_URL = "/rest/auth";
    private static final String REST_TABLES_TEST_URL = "/rest/tables/test";
    private static final String REST_LOG_URL = "/rest/log";
    private static final String REST_UN_AUTH_URL = "/rest/unAuth";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String EXCEPTION_LOG_ON_SERVER = "Exception log on server";

    private final static GWT.UncaughtExceptionHandler LOGGER_EXCEPTION_HANDLER = new GWT.UncaughtExceptionHandler() {

        @Override
        public void onUncaughtException(final Throwable throwable) {

            ExceptionDTO exceptionDTO = unwrap(throwable);
            Window.alert(ERROR + "\n" + throwable.getMessage());
            sendRest(RequestBuilder.POST, REST_LOG_URL, exceptionDTO.toJson(), EXCEPTION_LOG_ON_SERVER);
        }

        private ExceptionDTO unwrap(final Throwable throwable) {

            if (throwable instanceof UmbrellaException) {
                UmbrellaException ue = (UmbrellaException) throwable;
                if (ue.getCauses().size() == 1) {
                    return unwrap(ue.getCauses().iterator().next());
                }
            }
            return exceptionDTO(throwable);
        }
    };

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(LOGGER_EXCEPTION_HANDLER);

        final Button login = new Button(LOG_IN);
        final Button get = new Button(GET_TABLES);
        final Button logout = new Button(LOG_OUT);
        final Button exception = new Button(EXCEPTION_LOG_ON_SERVER);

        RootPanel.get(SEND_BUTTON_CONTAINER).add(login);
        RootPanel.get(SEND_BUTTON_CONTAINER).add(logout);
        RootPanel.get(SEND_BUTTON_CONTAINER).add(get);
        RootPanel.get(SEND_BUTTON_CONTAINER).add(exception);

        exception.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                throw new RuntimeException(ERROR);
            }
        });

        login.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                sendRest(RequestBuilder.POST, REST_AUTH_URL, USER_DTO.toJson(), LOG_IN);
            }
        });

        logout.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                sendRest(RequestBuilder.DELETE, REST_UN_AUTH_URL, EMPTY, LOG_OUT);
            }
        });
        get.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                sendRest(RequestBuilder.GET, REST_TABLES_TEST_URL, EMPTY, GET_TABLES);
            }
        });

    }

    private static void sendRest(final RequestBuilder.Method httpMethod, final String restUrl, final String jsonString, final String operation) {
        RequestBuilder builder = new RequestBuilder(httpMethod, URL.encode(restUrl));

        try {

            builder.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
            builder.setRequestData(jsonString);
            builder.setCallback(new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    Window.alert(ERROR + exception.getMessage());
                }

                public void onResponseReceived(Request request, Response response) {
                    if (Response.SC_OK == response.getStatusCode()) {
                        Window.alert(operation + OK);
                    } else {
                        Window.alert(operation + FAILED + response.getStatusCode());
                    }
                }
            });
            builder.send();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

}
