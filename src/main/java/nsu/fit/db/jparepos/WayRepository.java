package nsu.fit.db.jparepos;

import nsu.fit.db.model.MWay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WayRepository extends CrudRepository<MWay, Long> {
}
