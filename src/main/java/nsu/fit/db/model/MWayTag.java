package nsu.fit.db.model;

import lombok.Data;

@Data
public class MWayTag {
    private long way_id;
    private long tag_id;

    public MWayTag(long way_id, long tag_id) {
        this.way_id = way_id;
        this.tag_id = tag_id;
    }
}
