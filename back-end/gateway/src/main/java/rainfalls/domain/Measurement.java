package rainfalls.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="measurements")
public class Measurement extends PersistentEntity implements Serializable {

	private static final long serialVersionUID = -2007563386698676462L;

	@NotNull(message = "error.dateTime.notnull")
	@Column(unique=true, nullable=false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
	private ZonedDateTime dateTime;
	
	@NotNull(message = "error.rainFall.notnull")
	@Column(nullable=false)
	private BigDecimal rainFall;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JsonBackReference
	private Station station;
	
	Measurement() { }

	public Measurement(ZonedDateTime dateTime, BigDecimal rainFall) {
		super();
		this.dateTime = dateTime;
		this.rainFall = rainFall;
	}
	
	public ZonedDateTime getDateTime() {
		return dateTime;
	}

	public BigDecimal getRainFall() {
		return rainFall;
	}
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
    @Override
    public int hashCode(){
        return Objects.hash(dateTime, rainFall);
    }
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Measurement other = (Measurement) obj;
		
		return Objects.equals(dateTime, other.dateTime) &&
			   Objects.equals(rainFall, other.rainFall);
	}
    
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).build();
	}	
}
