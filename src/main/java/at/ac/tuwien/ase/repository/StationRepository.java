package at.ac.tuwien.ase.repository;

import at.ac.tuwien.ase.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Michael on 16.06.2017.
 */
public interface StationRepository extends JpaRepository<Station, String> {

    Station findByName(String name);

    List<Station> findByRbl(String rbl);

    List<Station> findByTitle(String title);

    Station findByRblAndViennaLineName(String rbl, String lineName);

    Station findByRblAndStationTimesTimePlannedAndViennaLineName(String rbl, Date timePlanned, String lineName);
}
