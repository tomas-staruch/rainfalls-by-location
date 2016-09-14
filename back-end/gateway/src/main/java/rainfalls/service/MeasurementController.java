package rainfalls.service;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rainfalls.domain.Measurement;
import rainfalls.domain.Station;
import rainfalls.exception.StationNotFoundException;
import rainfalls.repository.StationRepository;

/**
 * REST controller to handle the requests for station measurements
 */
@Component
@Path("/stations/{stationId}/measurements")
@Produces("application/json")
public class MeasurementController {
	private static final Logger log = LoggerFactory.getLogger(MeasurementController.class);

	private final StationRepository stationRepository;

	@Autowired
	public MeasurementController(StationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}

    @GET
    public Set<Measurement> read(@PathParam("stationId") Long stationId) {
    	log.info(String.format("Requested to get all measurments from station id:%d", stationId));
    	Station station = stationRepository.findOne(stationId);
    	
    	if(station == null)
    		throw new StationNotFoundException(stationId);

    	return station.getMeasurements();
    }
}
