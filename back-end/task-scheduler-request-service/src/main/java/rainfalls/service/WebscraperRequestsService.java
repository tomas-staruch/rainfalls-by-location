package rainfalls.service;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import rainfalls.domain.Station;
import rainfalls.message.WebscraperMessage;

/**
 * JMS messages producer which creates and sends {@link WebscraperMessage} to queue.
 */
@Component
public class WebscraperRequestsService {
    private static final Logger log = LoggerFactory.getLogger(WebscraperRequestsService.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private URI stationsUri;

    @Scheduled(fixedRateString = "${scheduler.webscraper.pushing.period}")
    public void sendMessagesToQueue() {
    	List<Station> stations = getAllStations();

    	Date now = new Date();
        log.info("WebscraperRequestsService triggered to send the messages {}", DATE_FORMAT.format(now));
        
        for (Station station : stations) {
    		MessageCreator messageCreator = new MessageCreator( ) {
    			@Override
    			public Message createMessage(Session session) throws JMSException {
    				return session.createObjectMessage(new WebscraperMessage(station, now));
    			}
    		};
    		
    		// TODO move common place to share it with a consumer module
    		jmsTemplate.send("webscraper-requests-channel", messageCreator);	
		}

    }

	private List<Station> getAllStations() {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<List<Station>> response = restTemplate.exchange(stationsUri, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Station>>() { });

        return response.getBody();
	}
	
	@Autowired
	private void setUri(@Value("${gateway.host.url}") String host, @Value("${gateway.stations.path.url}") String path) {
		this.stationsUri = UriComponentsBuilder.fromUriString(host).path(path).build().toUri();
	}
}
