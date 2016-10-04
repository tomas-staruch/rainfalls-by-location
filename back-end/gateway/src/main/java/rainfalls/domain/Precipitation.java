package rainfalls.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import rainfalls.domain.jpa.PersistentEntity;
import rainfalls.domain.jpa.ZonedDateTimeConverter;

/**
 * Class represents one particular measurement of precipitation in time.
 */
@Entity
@Table(name="precipitations")
public class Precipitation extends PersistentEntity implements Serializable {

	private static final long serialVersionUID = -2007563386698676462L;
	
	@Column(unique=true, nullable=false)
	@Convert(converter = ZonedDateTimeConverter.class)
	private ZonedDateTime measuredDate;

	@Column(nullable=false)
	private BigDecimal amount;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private Station station;
	
	Precipitation() { }

	public Precipitation(ZonedDateTime measuredDate, BigDecimal amount) {
		this.measuredDate = measuredDate;
		this.amount = amount;
	}
	
	public ZonedDateTime getMeasuredDate() {
		return measuredDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
    @Override
    public int hashCode(){
        return Objects.hash(measuredDate, amount);
    }
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Precipitation other = (Precipitation) obj;
		
		return Objects.equals(measuredDate, other.measuredDate) &&
			   Objects.equals(amount, other.amount);
	}
    
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).build();
	}	
}
