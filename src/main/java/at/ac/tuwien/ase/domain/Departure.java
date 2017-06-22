package at.ac.tuwien.ase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created by Michael on 16.06.2017.
 */
public class Departure {

    private DepartureTime departureTime;

    @JsonIgnore
    private Object vehicle;

    public Object getVehicle() {
        return vehicle;
    }

    public void setVehicle(Object vehicle) {
        this.vehicle = vehicle;
    }

    public DepartureTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(DepartureTime departureTime) {
        this.departureTime = departureTime;
    }
}
