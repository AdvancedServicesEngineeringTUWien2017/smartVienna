package at.ac.tuwien.ase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.JSONObject;

/**
 * Created by Michael on 16.06.2017.
 */
public class Properties {

    private String name;
    private String title;
    private String municipality;
    private String municipalityId;
    private String type;
    private String coordName;
    @JsonIgnore
    private Object gate;
    private Attributes attributes;

    public Object getGate() {
        return gate;
    }

    public void setGate(Object gate) {
        this.gate = gate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(String municipalityId) {
        this.municipalityId = municipalityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoordName() {
        return coordName;
    }

    public void setCoordName(String coordName) {
        this.coordName = coordName;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}
