package com.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

@Path("/auth")
@RegisterRestClient(configKey="auth-api")
public interface AuthServiceClient {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<LoginResponse> login(LoginRequest loginRequest);
}

record LoginRequest(String username, String password) {}
record LoginResponse(String message) {}