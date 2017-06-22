package at.ac.tuwien.ase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;

/**
 * Created by Michael on 14.06.2017.
 */
public class Line {

    private String name;
    private Departures departures;
    private String type;
    private int lineId;
    private String towards;
    private String direction;
    private String richtungsId;
    private boolean barrierFree;
    private boolean realtimeSupported;
    private boolean trafficjam;
    @JsonIgnore
    private Object attributes;
    @JsonIgnore
    private Object platform;

    public Object getPlatform() {
        return platform;
    }

    public void setPlatform(Object platform) {
        this.platform = platform;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public String getTowards() {
        return towards;
    }

    public void setTowards(String towards) {
        this.towards = towards;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRichtungsId() {
        return richtungsId;
    }

    public void setRichtungsId(String richtungsId) {
        this.richtungsId = richtungsId;
    }

    public boolean isBarrierFree() {
        return barrierFree;
    }

    public void setBarrierFree(boolean barrierFree) {
        this.barrierFree = barrierFree;
    }

    public boolean isRealtimeSupported() {
        return realtimeSupported;
    }

    public void setRealtimeSupported(boolean realtimeSupported) {
        this.realtimeSupported = realtimeSupported;
    }

    public boolean isTrafficjam() {
        return trafficjam;
    }

    public void setTrafficjam(boolean trafficjam) {
        this.trafficjam = trafficjam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Departures getDepartures() {
        return departures;
    }

    public void setDepartures(Departures departures) {
        this.departures = departures;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

}
