package at.ac.tuwien.ase.domain;

/**
 * Created by Michael on 16.06.2017.
 */
public class Geometry {

    private String type;
    private Double[] coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }
}
