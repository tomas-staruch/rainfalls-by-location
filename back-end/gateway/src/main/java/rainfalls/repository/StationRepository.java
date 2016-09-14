package rainfalls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rainfalls.domain.Station;

public interface StationRepository extends JpaRepository<Station, Long> { }
