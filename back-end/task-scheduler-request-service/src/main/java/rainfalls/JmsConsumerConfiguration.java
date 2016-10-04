package rainfalls;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import rainfalls.dto.StationDto;
import rainfalls.message.WebscraperMessage;

// TODO: this is test class which should be deleted !

@Configuration
public class JmsConsumerConfiguration {
	 private static final Logger log = LoggerFactory.getLogger(JmsConsumerConfiguration.class);
	
	@Bean
	public SimpleMessageListenerContainer jmsListener( ConnectionFactory connectionFactory ) { 
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer( ); container.setConnectionFactory( connectionFactory );
		
		Object listener = new Object( ) {
			@SuppressWarnings("unused")
			public void handleMessage(WebscraperMessage<StationDto> message) {
				log.info("Received: " + message.getCreated() + ":" + message.getObj().toString());
			}
		};
		
		MessageListenerAdapter adapter = new MessageListenerAdapter( listener );
		container.setMessageListener( adapter );
		container.setDestinationName( "webscraper-requests-channel" );
		
		return container;
	}
}
