package rainfalls.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Custom exception for situation when parameter is wrongly formated
 */
public class ParameterFormatException extends WebApplicationException {
	private static final long serialVersionUID = -1306045833966008305L;

	/**
	 * Create a HTTP 500 (Internal Server Error) exception.
	 */
	public ParameterFormatException(String msg) {
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					  .entity(new ErrorInfo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), msg))
					  .type(MediaType.APPLICATION_JSON_TYPE)
					  .build());
	}
}
