package rainfalls.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import rainfalls.dto.PrecipitationDto;
import rainfalls.exception.ParameterFormatException;
import rainfalls.exception.PrecipitationNotFoundException;
import rainfalls.security.HmacAuth;

/**
 * REST controller to handle the requests for measurements of precipitation
 */
@Component
@Path("/stations/{stationId}/precipitations")
@Produces("application/json")
public class PrecipitationController {
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final Logger log = LoggerFactory.getLogger(PrecipitationController.class);
	
    private final SimpleDateFormat dateOfMeasurementFormat = new SimpleDateFormat(DATE_FORMAT);

	private final PrecipitationService precipitationService;

	@Autowired
	public PrecipitationController(PrecipitationService precipitationService) {
		this.precipitationService = precipitationService;
	}

    @GET
    public List<PrecipitationDto> read(@PathParam("stationId") Long stationId, @QueryParam("dateOfMeasurement") String dateOfMeasurement) {
    	log.info(String.format("Requested to get all measurements of precipitation from station id:%d", stationId));
    	
    	List<PrecipitationDto> precipitations;
    	if(dateOfMeasurement != null) {
    		// dateOfMeasurement is converted to ZonedDateTime starting at 00:00:00
    		ZonedDateTime startDateTimeOfMeasurement = convertToDate(dateOfMeasurement);
    		ZonedDateTime endDateTimeOfMeasurement = startDateTimeOfMeasurement.plusHours(23).plusMinutes(59).plusSeconds(59);
    		precipitations = precipitationService.findByStationIdAndDateBetween(stationId, startDateTimeOfMeasurement, endDateTimeOfMeasurement);
    	} else {
    		precipitations = precipitationService.findByStationId(stationId);
    	}
    	
    	if(precipitations == null)
    		throw new PrecipitationNotFoundException(stationId);

    	return precipitations;
    }
    
    @POST
    @HmacAuth  
    public Response insert(@Validated PrecipitationDto precipitation) {
    	log.info(String.format("Requested to insert precipitation measurement: %s", precipitation.toString()));

    	return Response.status(201).entity(precipitationService.save(precipitation)).build();
    }
    
    private ZonedDateTime convertToDate(String dateStr) {
    	try {
    		Timestamp timestamp = new Timestamp(dateOfMeasurementFormat.parse(dateStr).getTime());
    		return ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
		} catch (ParseException e) {
			throw new ParameterFormatException(String.format("Unable to parse given date:%s. Expected format is:%s", dateStr, DATE_FORMAT));
		}
    }
}
