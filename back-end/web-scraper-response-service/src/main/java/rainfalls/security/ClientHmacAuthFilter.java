package rainfalls.security;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.client.ClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import rainfalls.domain.Station;

/**
 * Client side authorization filter which adds Authorization with signature into header 
 */

@Component
@Provider
public class ClientHmacAuthFilter extends HmacAuthorization implements ClientRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(ClientHmacAuthFilter.class);

	private static final Response DUPLICATED_AUTHORIZATION = Response.status(Response.Status.BAD_REQUEST).entity(String.format("Client request already contains %s header.", AUTHORIZATION_PROPERTY)).build();
	private static final Response INTERNAL_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occured during authorization").build();
 
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        if (requestContext.getHeaders().get(AUTHORIZATION_PROPERTY) != null)
        	requestContext.abortWith(DUPLICATED_AUTHORIZATION);
        
        if(!(requestContext instanceof ClientRequest))
        	requestContext.abortWith(INTERNAL_ERROR);
        
        ClientRequest request = (ClientRequest)requestContext; 
        
		try {
	        requestContext.getHeaders().add(AUTHORIZATION_PROPERTY, buildSignature((Station) request.getEntity()));
		} catch (Exception e) {
			log.error("Unable to create an authorization signature due to exception", e);
			
			requestContext.abortWith(INTERNAL_ERROR);
		}
    }
}
