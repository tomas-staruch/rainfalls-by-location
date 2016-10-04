package rainfalls.service;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rainfalls.domain.Precipitation;
import rainfalls.dto.PrecipitationDto;
import rainfalls.repository.PrecipitationRepository;

@Component
class PrecipitationService {

	private final PrecipitationRepository precipitationRepository;

	@Autowired
	public PrecipitationService(PrecipitationRepository precipitationRepository) {		
		this.precipitationRepository = precipitationRepository;
	}
	
	public List<PrecipitationDto> findByStationId(Long stationId) {
		List<Precipitation> precipitations = precipitationRepository.findByStationIdOrderByMeasuredDate(stationId);

		return toDTOs(precipitations);
	}
	
	public List<PrecipitationDto> findByStationIdAndDateBetween(Long stationId, ZonedDateTime startDateTimeOfMeasurement, ZonedDateTime endDateTimeOfMeasurement) {
		List<Precipitation> precipitations = precipitationRepository.findByStationIdAndMeasuredDateOrderByMeasuredDate(stationId, startDateTimeOfMeasurement, endDateTimeOfMeasurement);

		return toDTOs(precipitations);
	}
	
	public PrecipitationDto findOne(Long id) {
		return toDTO(precipitationRepository.findOne(id));
	}
	
	public PrecipitationDto save(PrecipitationDto dto) {
		Precipitation precipitation = precipitationRepository.saveAndFlush(fromDTO(dto));
		
		return toDTO(precipitation);
	}
	
	private List<PrecipitationDto> toDTOs(Collection<Precipitation> measurements) {
		return measurements.stream().map(s -> toDTO(s)).collect(Collectors.toList());
	}
	
	private PrecipitationDto toDTO(Precipitation measurement) {
		return measurement == null ? null : new PrecipitationDto(measurement.getMeasuredDate(), measurement.getAmount());
	}
	
	private Precipitation fromDTO(PrecipitationDto dto) {
		return dto == null ? null : new Precipitation(dto.getDateTime(), dto.getAmount());
	}
}
