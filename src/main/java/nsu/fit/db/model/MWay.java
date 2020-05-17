package nsu.fit.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Way")
public class MWay {
    @Id
    @Column(name = "id")
    private long id;
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
}
