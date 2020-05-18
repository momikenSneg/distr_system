package nsu.fit.controller;

import nsu.fit.db.model.MNode;
import nsu.fit.db.model.RNode;
import nsu.fit.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/node")
public class NodeController {
    private NodeService nodeService;
    @Autowired
    NodeController(NodeService nodeService){
        this.nodeService = nodeService;
    }

    @GetMapping("/{id}")
    RNode read(@PathVariable("id") long id) {
        MNode node = nodeService.read(id);
        return new RNode(node);
    }

    @PostMapping
    RNode create(@RequestBody RNode node) {
        MNode mNode = new MNode(node);
        return new RNode(nodeService.create(mNode));
    }

    @PutMapping("/{id}")
    RNode update(@PathVariable("id") long id, @RequestBody RNode node) {
        node.setId(id);
        MNode mNode = new MNode(node);
        return new RNode(nodeService.update(mNode));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        nodeService.delete(id);
    }

    @GetMapping("/near")
    public List<RNode> findNear(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon, @RequestParam("radius") Double radius) {
        //List<MNode> nodes = nodeService.findNear(lat, lon, radius);
        return nodeService.findNear(lat, lon, radius).stream().map(RNode::new).collect(Collectors.toList());
    }
}
