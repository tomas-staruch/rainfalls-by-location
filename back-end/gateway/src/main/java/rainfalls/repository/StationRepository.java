package rainfalls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rainfalls.domain.Station;

public interface StationRepository extends JpaRepository<Station, Long> { 
	
	Station findByName(String name);
	
	@Query("SELECT CASE WHEN COUNT(1) > 0 THEN true ELSE false END FROM Station t WHERE LOWER(t.name) = LOWER(:name)")
    public boolean existsByName(@Param("name") String name);	
}
