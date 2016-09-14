package rainfalls.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import rainfalls.domain.Station;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldFindStations() {
        ResponseEntity<Object> response = restTemplate.exchange("/stations", HttpMethod.GET, HttpEntity.EMPTY, Object.class);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldFindOneStation() {
        ResponseEntity<Station> response = restTemplate.exchange("/stations/1", HttpMethod.GET, HttpEntity.EMPTY, Station.class);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody() != null);
    }
}
