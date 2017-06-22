package at.ac.tuwien.ase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created by Michael on 14.06.2017.
 */
public class Monitor {


    private List<Line> lines;
    @JsonIgnore
    private Object attributes;

    @JsonIgnore
    private Object refTrafficInfoNames;

    public Object getRefTrafficInfoNames() {
        return refTrafficInfoNames;
    }

    public void setRefTrafficInfoNames(Object refTrafficInfoNames) {
        this.refTrafficInfoNames = refTrafficInfoNames;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    private LocationStop locationStop;

    public LocationStop getLocationStop() {
        return locationStop;
    }

    public void setLocationStop(LocationStop locationStop) {
        this.locationStop = locationStop;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
