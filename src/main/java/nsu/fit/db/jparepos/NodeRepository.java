package nsu.fit.db.jparepos;

import nsu.fit.db.model.MNode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends CrudRepository<MNode, Long> {

    @Query(value = "select * from node " +
            "where earth_distance(ll_to_earth(?1, ?2), ll_to_earth(node.lat, node.lon)) < ?3",
            nativeQuery = true)
    List<MNode> findNearNodes(double longitude, double latitude, double radius);
}
