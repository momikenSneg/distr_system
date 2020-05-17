package nsu.fit.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tag")
public class MTag {
    @Id
    @Column(name = "id")
    private long id;
    @Column (name = "k")
    private String k;
    @Column (name = "v")
    private String v;
    @ManyToMany (mappedBy="tags")
    List<MNode> nodes = new ArrayList<>();
    @ManyToMany (mappedBy="tags")
    List<MRelation> relations = new ArrayList<>();
    @ManyToMany (mappedBy="tags")
    List<MWay> ways = new ArrayList<>();

    public MTag(long id, String k, String v) {
        this.id = id;
        this.k = k;
        this.v = v;
    }
}
