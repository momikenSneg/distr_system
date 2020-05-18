package nsu.fit.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class RNode {
    private long id;
    private double longitude;
    private double latitude;
    private String username;
    private Map<String, String> tags = new HashMap<>();

    public RNode (MNode node){
        id = node.getId();
        latitude = node.getLat();
        longitude = node.getLon();
        username = node.getUsername();
        node.getTags().forEach(e -> tags.put(e.getK(), e.getV()));
    }
}
