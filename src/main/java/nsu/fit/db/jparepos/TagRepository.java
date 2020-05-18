package nsu.fit.db.jparepos;

import nsu.fit.db.model.MTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<MTag, Long> {
}
