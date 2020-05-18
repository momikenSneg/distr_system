package nsu.fit.service;

import nsu.fit.db.jparepos.NodeRepository;
import nsu.fit.db.jparepos.TagRepository;
import nsu.fit.db.jparepos.WayRepository;
import nsu.fit.db.model.MNode;
import nsu.fit.db.model.MWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WayService {
    private WayRepository wayRepository;
    private TagRepository tagRepository;
    @Autowired
    WayService(WayRepository wayRepository, TagRepository tagRepository){
        this.wayRepository = wayRepository;
        this.tagRepository = tagRepository;
    }

    public MWay create(MWay way){
        MWay createdNode = wayRepository.save(way);
        tagRepository.saveAll(way.getTags());
        return createdNode;
    }

    public MWay read(long id){
        return wayRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public MWay update(MWay way) {
        MWay updatedNode = wayRepository.findById(way.getId()).orElseThrow(NullPointerException::new);
        return wayRepository.save(way);
    }

    public void delete(long id) {
        MWay node = wayRepository.findById(id).orElseThrow(NullPointerException::new);
        wayRepository.delete(node);
    }
}
