package at.ac.tuwien.ase.domain;

/**
 * Created by Michael on 19.06.2017.
 */
public class Stop {

    private String name;
    private String rbl;
    private String stationId;
    private boolean tracking;
    private int interval;

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRbl() {
        return rbl;
    }

    public void setRbl(String rbl) {
        this.rbl = rbl;
    }
}
