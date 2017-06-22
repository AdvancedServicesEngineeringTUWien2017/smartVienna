package at.ac.tuwien.ase.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Michael on 16.06.2017.
 */
@Entity
public class StationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(unique = true)
    private Date timePlanned;
    //@Column(unique = true)
    private Date timeReal;
    private int countdown;
    private long delayInSeconds;
    private String towards;
    private String richtungsId;

    @ManyToOne
    private Station station;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimePlanned() {
        return timePlanned;
    }

    public void setTimePlanned(Date timePlanned) {
        this.timePlanned = timePlanned;
    }

    public Date getTimeReal() {
        return timeReal;
    }

    public void setTimeReal(Date timeReal) {
        this.timeReal = timeReal;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public long getDelayInSeconds() {
        return delayInSeconds;
    }

    public void setDelayInSeconds(long delayInSeconds) {
        this.delayInSeconds = delayInSeconds;
    }

    public String getTowards() {
        return towards;
    }

    public void setTowards(String towards) {
        this.towards = towards;
    }

    public String getRichtungsId() {
        return richtungsId;
    }

    public void setRichtungsId(String richtungsId) {
        this.richtungsId = richtungsId;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
