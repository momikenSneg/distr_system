package nsu.fit.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Relation")
public class MRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Relation_tag", joinColumns={@JoinColumn(referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(referencedColumnName="id")})
    Set<MTag> tags = new HashSet<>();

    public MRelation(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public MRelation (RRelation relation){
        id = relation.getId();
        username = relation.getUsername();
        relation.getTags().forEach( (k,v) -> tags.add(new MTag(k, v)));
    }

}
