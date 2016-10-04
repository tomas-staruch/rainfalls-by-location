package rainfalls;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import rainfalls.domain.Precipitation;
import rainfalls.domain.Position;
import rainfalls.domain.Station;
import rainfalls.repository.StationRepository;

/**
 * Load a dummy data in order to be application testable
 * 
 * You can use curl to get the user data in json format back, e.g.:
 * GET list of all stations:
 * curl -i http://localhost:8080/rainfalls/stations/
 * 
 * GET all measurements of particular station 
 * curl -i http://localhost:8080/rainfalls/stations/1/precipitations
 */
@Component
public class TestingStationLoader implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger log = LoggerFactory.getLogger(TestingStationLoader.class);
	
	@Autowired
	private StationRepository stationRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		log.info("Loading of dummy data started ...");

		Station testingStation = new Station("A test station", new Position(48.966546, 21.706613), "APS2");
		testingStation.addPrecipitation(new Precipitation(ZonedDateTime.of(2016, 9, 1, 00, 00, 00, 0000, ZoneId.of("UTC+0")), new BigDecimal(0)));
		testingStation.addPrecipitation(new Precipitation(ZonedDateTime.of(2016, 9, 1, 01, 00, 00, 0000, ZoneId.of("UTC+0")), new BigDecimal(2)));
		testingStation.addPrecipitation(new Precipitation(ZonedDateTime.of(2016, 9, 1, 02, 00, 00, 0000, ZoneId.of("UTC+0")), new BigDecimal(5)));
		testingStation.addPrecipitation(new Precipitation(ZonedDateTime.of(2016, 9, 1, 03, 00, 00, 0000, ZoneId.of("UTC+0")), new BigDecimal(3)));
		testingStation.addPrecipitation(new Precipitation(ZonedDateTime.of(2016, 9, 1, 04, 00, 00, 0000, ZoneId.of("UTC+0")), new BigDecimal(0)));
		
		stationRepository.saveAndFlush(testingStation);

	}

}
