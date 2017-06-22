package at.ac.tuwien.ase.domain;

import javax.persistence.Entity;

/**
 * Created by Michael on 16.06.2017.
 */

public class Departures {

    private Departure[] departure;

    public Departure[] getDeparture() {
        return departure;
    }

    public void setDeparture(Departure[] departure) {
        this.departure = departure;
    }
}
