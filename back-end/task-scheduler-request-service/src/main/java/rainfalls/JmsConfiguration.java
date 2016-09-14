package rainfalls;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

import com.rabbitmq.jms.admin.RMQConnectionFactory;

@Configuration
public class JmsConfiguration {

	@Bean
    public ConnectionFactory connectionFactory() {
        return new RMQConnectionFactory();
    }
}
