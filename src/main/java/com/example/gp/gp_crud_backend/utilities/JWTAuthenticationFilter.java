package com.example.gp.gp_crud_backend.utilities;


import java.util.Optional;

// JWTAuthenticationFilter.java
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private JWTUtil jwtUtil;

    @Override
    public void filter(ContainerRequestContext requestContext)  {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        // Validate token first
        var user = jwtUtil.validateToken(token);
        if (user.isEmpty()) {
            abortWithUnauthorized(requestContext);
            return;
        }
        
        // Check cache if token valid

        var user_hash = jwtUtil.getHashSignatureFromToken(token);
        var user_name = jwtUtil.getUsernameFromToken(token);
        // Check if user is cached in the cache
        Optional<String> cachedUser = UserCache.getUser(user_hash);
        if (cachedUser.isPresent()) {
            // User is cached, proceed with the request
            return;
        }

        

        // Cache the token and user
        UserCache.addHash(user_hash, user_name);
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
            Response.status(Response.Status.UNAUTHORIZED)
                   .build());
    }
}
