package nsu.fit.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tag")
public class MTag {
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @SequenceGenerator(name="identifier", sequenceName="mytable_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    @Column(name = "id")
    private Long id;
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

    public MTag(String k, String v) {
        this.k = k;
        this.v = v;
    }
}
