package rainfalls.message;

import java.io.Serializable;
import java.util.Date;

import rainfalls.domain.Station;

/**
 * Wrapper for {@link Station} object with additional info which is passed into a queue
 */
public class WebscraperMessage implements Serializable {
	
	private static final long serialVersionUID = 8431440850693305810L;

	private final Station station;
	private final Date created;

	public WebscraperMessage(Station station, Date dateTime) {
		this.station = station;
		this.created = dateTime;
	}
	
	public Station getStation() {
		return station;
	}

	public Date getCreated() {
		return created;
	}
}
