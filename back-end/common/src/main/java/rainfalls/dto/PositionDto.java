package rainfalls.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class represents the coordinates at geographic coordinate system.
 * 
 * The purpose of DTO is "to transfer data to remote interfaces".
 */
public final class PositionDto implements Serializable {

	private static final long serialVersionUID = 1231843891958356412L;
	
    @NotNull(message = "error.latitude.notnull")
    @Min(value = -90, message = "error.latitude.out.of.range")
    @Max(value = 90, message = "error.latitude.out.of.range")
	private final double latitude;
    
    @NotNull(message = "error.longitude.notnull")
    @Min(value = -180, message = "error.longitude.out.of.range")
    @Max(value = 180, message = "error.longitude.out.of.range")   
	private final double longitude;

    @JsonCreator
	public PositionDto(@JsonProperty("latitude") double latitude, 
					   @JsonProperty("longitude") double longitude) {
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
		
		PositionDto other = (PositionDto) obj;
		
		return Objects.equals(latitude, other.latitude) &&
			   Objects.equals(longitude, other.longitude);
	}
    
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).build();
	}
}
