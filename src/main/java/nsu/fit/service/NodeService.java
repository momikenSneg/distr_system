package nsu.fit.service;

import nsu.fit.db.jparepos.NodeRepository;
import nsu.fit.db.jparepos.TagRepository;
import nsu.fit.db.model.MNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeService {
    private NodeRepository nodeRepository;
    private TagRepository tagRepository;
    @Autowired
    NodeService(NodeRepository nodeRepository, TagRepository tagRepository){
        this.nodeRepository = nodeRepository;
        this.tagRepository = tagRepository;
    }

    public MNode create(MNode node){

        node.getTags().forEach( e -> e.getNodes().add(node));
        MNode createdNode = nodeRepository.save(node);
        return createdNode;
    }

    public MNode read(long id){
        return nodeRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public MNode update(MNode node) {
        MNode updatedNode = nodeRepository.findById(node.getId()).orElseThrow(NullPointerException::new);
        return nodeRepository.save(node);
    }

    public void delete(long id) {
        MNode node = nodeRepository.findById(id).orElseThrow(NullPointerException::new);
        nodeRepository.delete(node);
    }

    public List<MNode> findNear (double longitude, double latitude, double radius){
        return nodeRepository.findNearNodes(longitude, latitude, radius);
    }

}
