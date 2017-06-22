package at.ac.tuwien.ase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Michael on 16.06.2017.
 */
public class LocationStop {

    private String type;
    private Geometry geometry;
    private Properties properties;
    @JsonIgnore
    private Object attributes;

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
