package nsu.fit.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Way")
public class MWay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Way_tag", joinColumns={@JoinColumn(referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(referencedColumnName="id")})
    List<MTag> tags = new ArrayList<>();

    public MWay(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public MWay (RWay way){
        id = way.getId();
        username = way.getUsername();
        way.getTags().forEach( (k,v) -> tags.add(new MTag(k, v)));
    }

}
