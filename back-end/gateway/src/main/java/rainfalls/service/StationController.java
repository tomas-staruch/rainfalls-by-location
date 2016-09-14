package rainfalls.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import rainfalls.domain.Station;
import rainfalls.exception.StationNotFoundException;
import rainfalls.repository.StationRepository;

/**
 * REST controller to handle the requests for station
 */
@Component
@Path("/stations")
@Produces("application/json")
public class StationController {
	private static final Logger log = LoggerFactory.getLogger(StationController.class);

	private final StationRepository stationRepository;

	@Autowired
	public StationController(StationRepository stationRepository) {		
		this.stationRepository = stationRepository;
	}

    @GET
    public List<Station> read() {
    	log.info("Requested to get all stations...");
    	
    	return stationRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }
    
    @GET
    @Path("{id}")
    public Response readOne(@PathParam("id") Long id) {
    	log.info(String.format("Requested to get station id:%d", id));
    	
    	Station station = stationRepository.findOne(id);
    	
    	if(station == null)
    		throw new StationNotFoundException(id);
    	
    	return Response.status(200).entity(station).build();
    }
}
