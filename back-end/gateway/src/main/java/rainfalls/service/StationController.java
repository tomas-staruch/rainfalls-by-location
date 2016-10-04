package rainfalls.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import rainfalls.dto.StationDto;
import rainfalls.exception.StationAlreadyExists;
import rainfalls.exception.StationNotFoundException;
import rainfalls.security.HmacAuth;

/**
 * REST controller to handle the requests for Station
 */
@Component
@Path("/stations")
@Produces("application/json")
public class StationController {
	private static final Logger log = LoggerFactory.getLogger(StationController.class);

	private final StationService stationService;

	@Autowired
	public StationController(StationService stationService) {		
		this.stationService = stationService;
	}

    @GET
    public List<StationDto> read() {
    	log.info("Requested to get all stations...");
    	
    	return stationService.findAll();
    }
    
    @GET
    @Path("{id}")
    public Response readOne(@PathParam("id") Long id) {
    	log.info(String.format("Requested to get station id:%d", id));
    	
    	StationDto station = stationService.findOne(id);
    	
    	if(station == null)
    		throw new StationNotFoundException(id);
    	
    	return Response.status(200).entity(station).build();
    }
    
    @POST
    @HmacAuth  
    public Response insert(@Validated StationDto station) {
    	log.info(String.format("Requested to insert station: %s", station.toString()));
    	
    	StationDto existingStation = stationService.findByName(station.getName());
    	if(existingStation != null) 
    		throw new StationAlreadyExists(existingStation.getId());
    	
    	station = stationService.save(station);
    	
    	return Response.status(201).entity(station).build();
    }
}
