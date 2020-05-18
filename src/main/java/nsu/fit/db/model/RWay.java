package nsu.fit.db.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RWay {

    private long id;
    private String username;
    private Map<String, String> tags = new HashMap<>();

    public RWay(MWay relation){
        id = relation.getId();
        username = relation.getUsername();
        relation.getTags().forEach( e -> tags.put(e.getK(), e.getV()));
    }
}
