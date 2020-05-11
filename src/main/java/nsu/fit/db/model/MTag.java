package nsu.fit.db.model;

import lombok.Data;

@Data
public class MTag {
    private long id;
    private String k;
    private String v;

    public MTag(long id, String k, String v) {
        this.id = id;
        this.k = k;
        this.v = v;
    }
}
