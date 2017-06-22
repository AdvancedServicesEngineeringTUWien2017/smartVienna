package at.ac.tuwien.ase.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Michael on 16.06.2017.
 */
@Entity
public class ViennaLine {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "viennaLine", cascade = CascadeType.PERSIST)
    private List<Station> station;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getStation() {
        return station;
    }

    public void setStation(List<Station> station) {
        this.station = station;
    }

}
