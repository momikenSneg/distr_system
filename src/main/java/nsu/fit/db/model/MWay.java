package nsu.fit.db.model;

import lombok.Data;

@Data
public class MWay {
    private long id;
    private String username;

    public MWay(long id, String username) {
        this.id = id;
        this.username = username;
    }
}
