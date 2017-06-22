package at.ac.tuwien.ase.service.dto;

/**
 * Created by Michael on 21.06.2017.
 */
public class TrackDTO
{
    private String rbl;
    private int interval;

    public TrackDTO()
    {

    }
    public TrackDTO(String rbl, int interval)
    {
        this.rbl = rbl;
        this.interval = interval;
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
}
