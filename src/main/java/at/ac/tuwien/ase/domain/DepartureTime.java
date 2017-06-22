package at.ac.tuwien.ase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by Michael on 16.06.2017.
 */
public class DepartureTime {

    private Date timePlanned;
    private Date timeReal;
    private int countdown;
    @JsonIgnore
    private Object attributes;

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
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
}
