package nsu.fit.controller;

import nsu.fit.db.model.MRelation;
import nsu.fit.db.model.RRelation;
import nsu.fit.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relation")
public class RelationController {
    private RelationService relationService;
    @Autowired
    RelationController(RelationService relationService){
        this.relationService = relationService;
    }

    @GetMapping("/{id}")
    RRelation read(@PathVariable("id") long id) {
        MRelation mRelation = relationService.read(id);
        return new RRelation(mRelation);
    }

    @PostMapping
    RRelation create(@RequestBody RRelation relation) {
        MRelation mRelation = new MRelation(relation);
        return new RRelation(relationService.create(mRelation));
    }

    @PutMapping("/{id}")
    RRelation update(@PathVariable("id") long id, @RequestBody RRelation relation) {
        relation.setId(id);
        MRelation mRelation = new MRelation(relation);
        return new RRelation(relationService.update(mRelation));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        relationService.delete(id);
    }
}
