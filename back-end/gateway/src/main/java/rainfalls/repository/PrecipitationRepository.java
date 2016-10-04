package rainfalls.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rainfalls.domain.Precipitation;

public interface PrecipitationRepository extends JpaRepository<Precipitation, Long> {

	List<Precipitation> findByStationIdOrderByMeasuredDate(Long stationId);

	@Query("FROM Precipitation t WHERE t.station.id = :stationId AND measuredDate BETWEEN :from AND :to") 
	List<Precipitation> findByStationIdAndMeasuredDateOrderByMeasuredDate(@Param("stationId") Long stationId, 
																		  @Param("from") ZonedDateTime from, 
																		  @Param("to") ZonedDateTime to); 

}
