package at.ac.tuwien.ase.service.dto;

import java.util.List;

/**
 * Created by Michael on 22.06.2017.
 */
public class StatisticsDTO {


    private List<LineDTO> line;


    public List<LineDTO> getLine() {
        return line;
    }

    public void setLine(List<LineDTO> line) {
        this.line = line;
    }
}
