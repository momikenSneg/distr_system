package nsu.fit.db.model;

import lombok.Data;

@Data
public class MRelationTag {
    private long relation_id;
    private long tag_id;

    public MRelationTag(long relation_id, long tag_id) {
        this.relation_id = relation_id;
        this.tag_id = tag_id;
    }
}
