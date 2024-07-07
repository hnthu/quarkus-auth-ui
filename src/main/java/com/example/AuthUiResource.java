package com.example;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Uni;

@Path("/ui")
public class AuthUiResource {

    @Inject
    Template login;

    @Inject
    Template result;

    @Inject
    @RestClient
    AuthServiceClient authServiceClient;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return login.instance();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> login(@FormParam("username") String username, @FormParam("password") String password) {
        return authServiceClient.login(new LoginRequest(username, password))
            .onItem().transform(response -> result.data("message", response.message()))
            .onFailure().recoverWithItem(e -> result.data("message", "Login failed: " + e.getMessage()));
    }
}