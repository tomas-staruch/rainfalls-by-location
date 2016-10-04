package rainfalls.message;

import java.io.Serializable;
import java.util.Date;

/**
 * Wrapper for an object with additional info which is passed into a queue
 */
public class WebscraperMessage<T> implements Serializable {
	
	private static final long serialVersionUID = 8431440850693305810L;

	private final T obj;
	private final Date created;

	public WebscraperMessage(T obj, Date dateTime) {
		this.obj = obj;
		this.created = dateTime;
	}
	
	public T getObj() {
		return obj;
	}

	public Date getCreated() {
		return created;
	}
}
