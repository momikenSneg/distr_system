package nsu.fit.db.model;

import lombok.Data;

@Data
public class MRelation {
    private long id;
    private String username;

    public MRelation(long id, String username) {
        this.id = id;
        this.username = username;
    }
}
