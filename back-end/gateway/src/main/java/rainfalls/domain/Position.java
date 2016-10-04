package rainfalls.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Class represents the coordinates at geographic coordinate system.
 */

@Embeddable 
public class Position implements Serializable {

	private static final long serialVersionUID = -4894298482384678137L;

	private double latitude;
	private double longitude;
	
	Position() { }

	public Position(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
    @Override
    public int hashCode(){
        return Objects.hash(latitude, longitude);
    }
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Position other = (Position) obj;
		
		return Objects.equals(latitude, other.latitude) &&
			   Objects.equals(longitude, other.longitude);
	}
    
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).build();
	}
}
