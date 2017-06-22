package at.ac.tuwien.ase.repository;

import at.ac.tuwien.ase.domain.Station;
import at.ac.tuwien.ase.domain.StationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Michael on 16.06.2017.
 */
public interface StationTimeRepository extends JpaRepository<StationTime, String> {

    List<StationTime> findByStationRbl(String rbl);

    StationTime findByTimePlannedAndStation(Date timePlanned, Station station);


    @Query(value = "SELECT c.name, avg(a.delay_in_seconds) as avg, max(a.delay_in_seconds) as max, " +
        "min(a.delay_in_seconds) as min, sum(a.delay_in_seconds) as sum FROM STATION_TIME a, STATION b, VIENNA_LINE c "+
        "WHERE a.station_id = b.id AND c.id = b.vienna_line_id and b.rbl=?1 GROUP BY c.name", nativeQuery = true)
    List<Object[]> getStatistics(String rbl);
}
