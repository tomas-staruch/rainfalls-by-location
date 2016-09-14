package rainfalls.domain;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="stations")
public class Station extends PersistentEntity implements Serializable {

	private static final long serialVersionUID = 5201842891562366416L;
	
	@NotNull(message = "error.name.notnull")
	@Size(min = 1, max = 255, message = "error.name.size")
	@Column(unique=true, nullable=false)
	private String name;
	
	@Embedded
	private Position position;
	
	@OneToMany(mappedBy="station", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy("dateTime ASC")
	@JsonIgnore
	private Set<Measurement> measurements = new LinkedHashSet<>();
	
	private String type;
	
	Station() { }

	public Station(String name, Position position, String type) {
		this.name = name;
		this.position = position;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Position getPosition() {
		return position;
	}

	public String getType() {
		return type;
	}
	
	public Set<Measurement> getMeasurements() {
		return measurements;
	}
	
	public void setMeasurements(Set<Measurement> measurements) {
		this.measurements = measurements;
	}
	
	public void addMeasurement(Measurement measurement) {
		if(measurement.getStation() == null) {
			measurement.setStation(this);
		}
	
		this.measurements.add(measurement);
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
		
		Station other = (Station) obj;
		
		return Objects.equals(name, other.name) &&
			   Objects.equals(position, other.position) &&
			   Objects.equals(type, other.type);
	}
    
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).build();
	}
}
