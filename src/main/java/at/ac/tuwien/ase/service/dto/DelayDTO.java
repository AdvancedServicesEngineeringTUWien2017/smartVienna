package at.ac.tuwien.ase.service.dto;

/**
 * Created by Michael on 22.06.2017.
 */
public class DelayDTO {


    private String overallDelay;
    private String averageDelay;
    private String highestDelay;
    private String shortestDelay;

    public String getOverallDelay() {
        return overallDelay;
    }

    public void setOverallDelay(String overallDelay) {
        this.overallDelay = overallDelay;
    }

    public String getAverageDelay() {
        return averageDelay;
    }

    public void setAverageDelay(String averageDelay) {
        this.averageDelay = averageDelay;
    }

    public String getHighestDelay() {
        return highestDelay;
    }

    public void setHighestDelay(String highestDelay) {
        this.highestDelay = highestDelay;
    }

    public String getShortestDelay() {
        return shortestDelay;
    }

    public void setShortestDelay(String shortestDelay) {
        this.shortestDelay = shortestDelay;
    }
}
