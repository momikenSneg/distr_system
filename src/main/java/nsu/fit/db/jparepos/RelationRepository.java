package nsu.fit.db.jparepos;

import nsu.fit.db.model.MRelation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepository extends CrudRepository<MRelation, Long> {
}
