package nsu.fit.controller;

import nsu.fit.db.model.MWay;
import nsu.fit.db.model.RWay;
import nsu.fit.service.WayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/way")
public class WayController {
    private WayService wayService;
    @Autowired
    WayController(WayService wayService){
        this.wayService = wayService;
    }

    @GetMapping("/{id}")
    RWay read(@PathVariable("id") long id) {
        MWay mWay = wayService.read(id);
        return new RWay(mWay);
    }

    @PostMapping
    RWay create(@RequestBody RWay way) {
        MWay mWay = new MWay(way);
        return new RWay(wayService.create(mWay));
    }

    @PutMapping("/{id}")
    RWay update(@PathVariable("id") long id, @RequestBody RWay way) {
        way.setId(id);
        MWay mWay = new MWay(way);
        return new RWay(wayService.update(mWay));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        wayService.delete(id);
    }
}
