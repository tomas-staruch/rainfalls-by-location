package rainfalls.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import rainfalls.domain.Position;
import rainfalls.domain.Station;
import rainfalls.dto.PositionDto;
import rainfalls.dto.StationDto;
import rainfalls.repository.StationRepository;

/**
 * Common service class for station business logic.
 *
 * Note: actually there isn't any business logic, the purpose of the class is to get the objects from DB and transform them.
 */
@Component
class StationService {

	private final StationRepository stationRepository;

	@Autowired
	public StationService(StationRepository stationRepository) {		
		this.stationRepository = stationRepository;
	}
	
    public List<StationDto> findAll() {
    	return toDTOs(stationRepository.findAll(new Sort(Sort.Direction.ASC, "name")));
    }

	public StationDto findOne(Long id) {
		return toDTO(stationRepository.findOne(id));
	}

	public StationDto findByName(String name) {
		return toDTO(stationRepository.findByName(name));
	}
	
	public StationDto save(StationDto stationDto) {
		Station station = stationRepository.saveAndFlush(fromDTO(stationDto));
		
		return toDTO(station);
	}

	private List<StationDto> toDTOs(List<Station> stations) {
		return stations.stream().map(s -> toDTO(s)).collect(Collectors.toList());
	}
	
	private StationDto toDTO(Station station) {
		return station == null ? null : new StationDto(station.getId(),
							  						   station.getName(), 
													   new PositionDto(station.getPosition().getLatitude(), 
															  		   station.getPosition().getLongitude()),
													   station.getType());
	}
	
	private Station fromDTO(StationDto dto) {
		return dto == null ? null : new Station(dto.getName(), 
												new Position(dto.getPosition().getLatitude(),
													 	     dto.getPosition().getLongitude()), 
												dto.getType());
	}
}
