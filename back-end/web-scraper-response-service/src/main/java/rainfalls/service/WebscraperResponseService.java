package rainfalls.service;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import rainfalls.domain.Position;
import rainfalls.domain.Station;
import rainfalls.security.ClientHmacAuthFilter;

/**
 * JMS messages consumer which creates and POST {@link Station} to Gateway.
 */
@Component
public class WebscraperResponseService {
	private static final Logger log = LoggerFactory.getLogger(WebscraperResponseService.class);
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

	private URI stationsUri;
	
    @Scheduled(fixedRateString = "${scheduler.webscraper.pulling.period}")
    public void getMessagesFromQueue() {
        log.info("WebscraperResponseService triggered to pull the messages {}", DATE_FORMAT.format(new Date()));
        
        Client client = ClientBuilder.newClient(new ClientConfig().register(ClientHmacAuthFilter.class));
        WebTarget webTarget = client.target(stationsUri);
        
        // TODO get a station from JMS queue 
        Station station = null;
        
        Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(station, MediaType.APPLICATION_JSON_TYPE));

        if(response.getStatusInfo().equals(Response.Status.CREATED)) {
        	Station confirmedStation = response.readEntity(Station.class);
            log.info("Sucessfully posted station into Gateway with id: {}", confirmedStation.getId());      	
        } else {
        	log.error("Got status {} when posted station into Gateway", response.getStatus());
        }
    }

	@Autowired
	private void setUri(@Value("${gateway.host.url}") String host, @Value("${gateway.stations.path.url}") String path) {
		this.stationsUri = UriBuilder.fromUri(host).path(path).build();
	}
}
