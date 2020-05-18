package nsu.fit.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "node")
public class MNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private long id;
    @Column (name = "lon")
    private double lon;
    @Column (name = "lat")
    private double lat;
    @Column (name = "username")
    private String username;
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="node_tag", joinColumns={@JoinColumn(referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(referencedColumnName="id")})
    private List<MTag> tags = new ArrayList<>();

    public MNode(long id, double lon, double lat, String username) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.username = username;
    }

    public MNode (RNode node){
        id = node.getId();
        lon = node.getLongitude();
        lat = node.getLatitude();
        username = node.getUsername();
        node.getTags().forEach( (k,v) -> tags.add(new MTag(k, v)));
    }

//    @Override
//    public String toString() {
//        return "MNode{" +
//                "id=" + id +
//                ", name='" + username + '\'' +
//                ", longitude=" + lon +
//                ", latitude=" + lat +
//                ", tags=" + tags +
//                '}';
//    }

}
