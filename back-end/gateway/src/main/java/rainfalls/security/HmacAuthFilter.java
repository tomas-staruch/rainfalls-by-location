package rainfalls.security;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import rainfalls.domain.Station;

/**
 * Back-end side authorization filter which compares Authorization signature from header with one created by itself
 */

@Component
@Provider
@HmacAuth
public class HmacAuthFilter extends HmacAuthorization implements ContainerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(HmacAuthFilter.class);
	
	private static final Response NO_AUTHORIZATION = Response.status(Response.Status.FORBIDDEN).entity("The resource requires authorization.").build();
	private static final Response INTERNAL_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occured during authorization").build();
	private static final Response NOT_AUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).entity("Failed to authenticate the request").build();

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		final List<String> authorization = requestContext.getHeaders().get(AUTHORIZATION_PROPERTY);
		
	    if(authorization == null || authorization.isEmpty())
        	requestContext.abortWith(NO_AUTHORIZATION);
	    
	    if (!(requestContext instanceof ContainerRequest))
        	requestContext.abortWith(INTERNAL_ERROR);
	    
    	ContainerRequest request = (ContainerRequest) requestContext;
    	request.bufferEntity();

		try {	
			String signatureHash = authorization.get(0);
			
		    if(!signatureHash.equals(buildSignature(request.readEntity(Station.class))))
		    	requestContext.abortWith(NOT_AUTHORIZED);
		} catch (Exception e) {
			log.error("Unable to compare signatures due to exception", e);
			
			requestContext.abortWith(INTERNAL_ERROR);
		}
	}
}
