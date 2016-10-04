package rainfalls.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class represents weather monitoring station which measures atmospheric conditions (e.g. precipitation amounts).
 * 
 * The purpose of DTO is "to transfer data to remote interfaces".
 * In this case, domain object is transform to DTO.
 */
public final class StationDto implements Serializable {

	private static final long serialVersionUID = 4221843891562356417L;
	
	private final Long id;
		
	@NotNull(message = "error.name.notnull")
	@Size(min = 1, max = 255, message = "error.name.size")
	private final String name;

	@NotNull(message = "error.position.notnull")
	private final PositionDto position;
	
	private final String type;

	@JsonCreator
	public StationDto(@JsonProperty("id") Long id, 
					  @JsonProperty("name") String name, 
					  @JsonProperty("position") PositionDto position, 
					  @JsonProperty("type") String type) {
		this.id = id;
		this.name = name;
		this.position = position;
		this.type = type;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public PositionDto getPosition() {
		return position;
	}

	public String getType() {
		return type;
	}
	
    @Override
    public int hashCode(){
        return Objects.hash(name, position, type);
    }
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		StationDto other = (StationDto) obj;
		
		return Objects.equals(name, other.name) &&
			   Objects.equals(position, other.position) &&
			   Objects.equals(type, other.type);
	}
    
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).build();
	}
}
