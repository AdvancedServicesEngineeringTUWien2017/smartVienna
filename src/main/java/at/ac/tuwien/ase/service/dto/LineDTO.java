package at.ac.tuwien.ase.service.dto;

/**
 * Created by Michael on 22.06.2017.
 */
public class LineDTO {

    private String name;

    private DelayDTO statistics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DelayDTO getStatistics() {
        return statistics;
    }

    public void setStatistics(DelayDTO statistics) {
        this.statistics = statistics;
    }
}
