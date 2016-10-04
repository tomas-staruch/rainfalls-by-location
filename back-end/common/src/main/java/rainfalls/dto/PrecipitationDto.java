package rainfalls.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Class represents one particular measurement of precipitation in time.
 * 
 * The purpose of DTO is "to transfer data to remote interfaces".
 */
public final class PrecipitationDto implements Serializable {

	private static final long serialVersionUID = -2327563386698556461L;

	@NotNull(message = "error.dateTime.notnull")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
	private final ZonedDateTime dateTime;
	
	@NotNull(message = "error.amount.notnull")
	@Min(value = 0, message = "error.amount.min.value")
	private final BigDecimal amount;

	public PrecipitationDto(ZonedDateTime dateTime, BigDecimal amount) {
		this.dateTime = dateTime;
		this.amount = amount;
	}
	
	public ZonedDateTime getDateTime() {
		return dateTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
    @Override
    public int hashCode(){
        return Objects.hash(dateTime, amount);
    }
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		PrecipitationDto other = (PrecipitationDto) obj;
		
		return Objects.equals(dateTime, other.dateTime) &&
			   Objects.equals(amount, other.amount);
	}
    
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).build();
	}
}
