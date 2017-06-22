package at.ac.tuwien.ase.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Michael on 16.06.2017.
 */
@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String rbl;
    private int interval;
    private String title;
    private boolean track;
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<StationTime> stationTimes;

    @ManyToOne
    private ViennaLine viennaLine;

    public boolean isTrack() {
        return track;
    }

    public void setTrack(boolean track) {
        this.track = track;
    }

    public String getRbl() {
        return rbl;
    }

    public void setRbl(String rbl) {
        this.rbl = rbl;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public List<StationTime> getStationTimes() {
        return stationTimes;
    }

    public void setStationTimes(List<StationTime> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ViennaLine getViennaLine() {
        return viennaLine;
    }

    public void setViennaLine(ViennaLine viennaLine) {
        this.viennaLine = viennaLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
