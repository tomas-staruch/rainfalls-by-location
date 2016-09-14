package rainfalls.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Custom exception for situation when a station is not found
 */
public class StationNotFoundException extends WebApplicationException {
	private static final long serialVersionUID = -7306015833926007306L;

	/**
	 * Create a HTTP 404 (Not Found) exception.
	 */
	public StationNotFoundException(Long id) {
		super(Response.status(Response.Status.NOT_FOUND)
					  .entity(new ErrorInfo(Response.Status.NOT_FOUND.getStatusCode(), "Couldn't find the station with id:" + id))
					  .type(MediaType.APPLICATION_JSON_TYPE)
					  .build());
	}
}
