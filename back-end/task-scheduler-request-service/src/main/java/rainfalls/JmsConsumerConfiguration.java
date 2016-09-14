package rainfalls;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import rainfalls.message.WebscraperMessage;

@Configuration
public class JmsConsumerConfiguration {
	@Bean
	public SimpleMessageListenerContainer jmsListener( ConnectionFactory connectionFactory ) { 
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer( ); container.setConnectionFactory( connectionFactory );
		Object listener = new Object( ) {
			@SuppressWarnings( "unused" )
			public void handleMessage( WebscraperMessage message ) {
				System.out.println("Received: " + message.getCreated() + ":" + message.getStation().toString() );
			}
		};
		MessageListenerAdapter adapter = new MessageListenerAdapter( listener );
		container.setMessageListener( adapter );
		container.setDestinationName( "webscraper-requests-channel" );
		
		return container;
	}
}
