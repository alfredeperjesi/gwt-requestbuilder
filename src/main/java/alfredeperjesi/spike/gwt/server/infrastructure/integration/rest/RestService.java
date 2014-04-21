package alfredeperjesi.spike.gwt.server.infrastructure.integration.rest;

import alfredeperjesi.spike.gwt.server.infrastructure.integration.rest.api.AuthResource;
import alfredeperjesi.spike.gwt.server.infrastructure.integration.rest.api.LogResource;
import org.apache.http.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Alfréd on 18/04/14.
 */
@Path("/")
public class RestService {

    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";

    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response auth(AuthResource authResource, @Context HttpServletRequest request) {
        System.out.println(authResource.toString());
        boolean authorized = USER_NAME.equalsIgnoreCase(authResource.userName) && PASSWORD.equalsIgnoreCase(authResource.password);
        if(authorized) {
            HttpSession session = request.getSession(true);
            session.setAttribute(USER_NAME, USER_NAME);
            System.out.println(session);
        }
        return Response.status(getResponseStatus(authorized)).build();
    }

    @POST
    @Path("/log")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response log(LogResource logResource) {
        System.out.println(logResource.toString());
        return Response.ok().build();
    }

    @GET
    @Path("/tables/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tables(@PathParam("username") String userName) {
        System.out.println(userName);
        return Response.ok().build();
    }

    @DELETE
    @Path("/unAuth")
    public Response unAuth(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        System.out.println(session);
        if(session != null) {
            session.invalidate();
        }
        return Response.status(getResponseStatus(session != null)).build();
    }

    private int getResponseStatus(boolean authorized) {
        return authorized?200:401;
    }
}
