package rainfalls.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Custom exception for situation when the measurements of precipitation for station are not found
 */
public class PrecipitationNotFoundException extends WebApplicationException {
	private static final long serialVersionUID = -7306015833926007306L;

	/**
	 * Create a HTTP 404 (Not Found) exception.
	 */
	public PrecipitationNotFoundException(Long stationId) {
		super(Response.status(Response.Status.NOT_FOUND)
					  .entity(new ErrorInfo(Response.Status.NOT_FOUND.getStatusCode(), 
							  				"Couldn't find the measurements of precipitation for station id:" + stationId))
					  .type(MediaType.APPLICATION_JSON_TYPE)
					  .build());
	}
}