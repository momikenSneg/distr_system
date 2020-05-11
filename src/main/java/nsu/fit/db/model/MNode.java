package nsu.fit.db.model;

import lombok.Data;

@Data
public class MNode {
    private long id;
    private double lon;
    private double lat;
    private String username;

    public MNode(long id, double lon, double lat, String username) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.username = username;
    }


}
