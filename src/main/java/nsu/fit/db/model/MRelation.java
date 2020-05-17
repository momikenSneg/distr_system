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
    @Column(name = "id")
    private long id;
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
}
