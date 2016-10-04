package rainfalls.domain.jpa;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * ZonedDateTime isn't supported by JPA (stored in binary format in DB), that's why conversion is required
 */
@Converter
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(ZonedDateTime entityValue) {
		return Timestamp.from(entityValue.toInstant());
	}

	@Override
	public ZonedDateTime convertToEntityAttribute(Timestamp dbData) {
        return dbData.toLocalDateTime().atZone(ZoneId.systemDefault());
	}
}
