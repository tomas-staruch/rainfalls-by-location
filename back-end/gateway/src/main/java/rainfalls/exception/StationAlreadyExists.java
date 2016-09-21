package rainfalls.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class StationAlreadyExists extends WebApplicationException {
	private static final long serialVersionUID = -2206015856926007303L;

	/**
	 * Create a HTTP 409 (Conflict) exception.
	 */
	public StationAlreadyExists(Long id) {
		super(Response.status(Response.Status.CONFLICT)
					  .entity(new ErrorInfo(Response.Status.NOT_FOUND.getStatusCode(), "Given station exists with id:" + id))
					  .type(MediaType.APPLICATION_JSON_TYPE)
					  .build());
	}
}
