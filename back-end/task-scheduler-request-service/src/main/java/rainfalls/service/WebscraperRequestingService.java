package rainfalls.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import rainfalls.dto.StationDto;
import rainfalls.message.WebscraperMessage;

/**
 * JMS messages producer which creates and sends {@link WebscraperMessage} to queue.
 */
@Component
public class WebscraperRequestingService {
    private static final Logger log = LoggerFactory.getLogger(WebscraperRequestingService.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
    	
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Autowired
    private HttpService<StationDto> httpService;

    @Scheduled(fixedRateString = "${scheduler.webscraper.pushing.period}")
    public void sendMessagesToQueue() {
        log.info("WebscraperRequestsService triggered to send the messages {}", DATE_FORMAT.format(new Date()));
        
        List<StationDto> stations = httpService.getEntities();
        
        for (StationDto station : stations) {
    		MessageCreator messageCreator = new MessageCreator( ) {
    			@Override
    			public Message createMessage(Session session) throws JMSException {
    				return session.createObjectMessage(new WebscraperMessage<StationDto>(station, new Date()));
    			}
    		};
    		
    		// TODO move common place to share it with a consumer module
    		jmsTemplate.send("webscraper-requests-channel", messageCreator);	
		}
    }
}
