package rainfalls.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import rainfalls.dto.StationDto;

/**
 * Central class for synchronous HTTP access to resources defined by {@link URI}
 */
@Component
class HttpService<T> {
    
    private static final String HTTP_HEADER_PARAMETERS = "parameters";
    
    @Autowired
    private RestTemplate restTemplate;
    
    private final URI uri;
    
    public HttpService(@Value("${gateway.host.url}") String host, @Value("${gateway.stations.path.url}") String path) {
    	this.uri = UriComponentsBuilder.fromUriString(host).path(path).build().toUri();
	}
    
	public List<StationDto> getEntities() {
		HttpEntity<String> entity = buildRequestEntity();
		// TODO generate JSON schema
		// https://github.com/FasterXML/jackson-module-jsonSchema
		// http://json-schema.org
		
		// TODO generate POJOs from schema
		// http://stackoverflow.com/questions/1957406/generate-java-class-from-json
		
		// TODO read http://stackoverflow.com/questions/21987295/using-spring-resttemplate-in-generic-method-with-generic-parameter
        ResponseEntity<List<StationDto>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<List<StationDto>>() { });

        return response.getBody();
	}

	private HttpEntity<String> buildRequestEntity() {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        
		return new HttpEntity<String>(HTTP_HEADER_PARAMETERS, headers);
	}
}
