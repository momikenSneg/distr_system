package nsu.fit.service;

import nsu.fit.db.jparepos.RelationRepository;
import nsu.fit.db.jparepos.TagRepository;
import nsu.fit.db.model.MRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationService {
    private RelationRepository relationRepository;
    private TagRepository tagRepository;
    @Autowired
    RelationService(RelationRepository relationRepository, TagRepository tagRepository){
        this.relationRepository = relationRepository;
        this.tagRepository = tagRepository;
    }

    public MRelation create(MRelation relation){
        MRelation createdRelation = relationRepository.save(relation);
        tagRepository.saveAll(relation.getTags());
        return createdRelation;
    }

    public MRelation read(long id){
        return relationRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public MRelation update(MRelation relation) {
        relationRepository.findById(relation.getId()).orElseThrow(NullPointerException::new);
        return relationRepository.save(relation);
    }

    public void delete(long id) {
        MRelation relation = relationRepository.findById(id).orElseThrow(NullPointerException::new);
        relationRepository.delete(relation);
    }
}
