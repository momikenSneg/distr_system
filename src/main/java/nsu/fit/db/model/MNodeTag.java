package nsu.fit.db.model;

import lombok.Data;

@Data
public class MNodeTag {
    private long node_id;
    private long tag_id;

    public MNodeTag(long node_id, long tag_id) {
        this.node_id = node_id;
        this.tag_id = tag_id;
    }
}
