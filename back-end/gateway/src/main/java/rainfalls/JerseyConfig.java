package rainfalls;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import rainfalls.security.HmacAuthFilter;
import rainfalls.service.MeasurementController;
import rainfalls.service.StationController;

@Configuration
@ApplicationPath("/rainfalls")
public class JerseyConfig extends ResourceConfig {
	
    public JerseyConfig() {
    	register(StationController.class);
    	register(MeasurementController.class);
    	register(HmacAuthFilter.class);
    }
}
