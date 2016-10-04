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

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import rainfalls.domain.jpa.PersistentEntity;

/**
 * Class represents weather monitoring station which measures atmospheric conditions (e.g. precipitation amounts).
 */
@Entity
@Table(name="stations")
public class Station extends PersistentEntity implements Serializable {

	private static final long serialVersionUID = 5201842891562366416L;

	@Column(unique=true, nullable=false)
	private String name;
	
	@Embedded
	private Position position;
	
	@OneToMany(mappedBy="station", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy("measuredDate ASC")
	private Set<Precipitation> precipitations = new LinkedHashSet<>();
	
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
	
	public Set<Precipitation> getPrecipitations() {
		return precipitations;
	}
	
	public void setPrecipitations(Set<Precipitation> precipitations) {
		this.precipitations = precipitations;
	}
	
	public void addPrecipitation(Precipitation precipitations) {
		if(precipitations.getStation() == null) {
			precipitations.setStation(this);
		}
	
		this.precipitations.add(precipitations);
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
