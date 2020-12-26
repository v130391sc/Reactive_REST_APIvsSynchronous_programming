package es.upm.miw.synchronousrestapi.activity_resource;

import es.upm.miw.synchronousrestapi.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ActivityResource.ACTIVITIES)
public class ActivityResource {

    public static final String ACTIVITIES = "/v0/activities";
    static final String SEARCH = "/search";
    static final String ID_ID = "/{id}";
    static final String DURATION = "/duration";

    private ActivityBusinessController activityBusinessController;

    @Autowired
    public ActivityResource(ActivityBusinessController activityBusinessController) {
        this.activityBusinessController = activityBusinessController;
    }

    @PostMapping
    public ActivityBasicDto create(@RequestBody ActivityCreationDto activityCreationDto) {
        activityCreationDto.validate();
        return this.activityBusinessController.create(activityCreationDto);
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) {
        this.activityBusinessController.delete(id);
    }

    @GetMapping(value = SEARCH)
    public List<ActivityBasicDto> find(@RequestParam String q) {
        if (!"client.physicalLevel".equals(q.split(":>")[0])) {
            throw new BadRequestException("query param q is incorrect, missing 'client.physicalLevel:>'");
        }
        return this.activityBusinessController.findByClientPhysicalLevelGreaterThan(Integer.valueOf(q.split(":>")[1]));
    }

    @PutMapping(value = ID_ID + DURATION)
    public void updateDuration(@PathVariable String id, @RequestBody ActivityUpdateDto activityUpdateDto) {
        activityUpdateDto.validateDuration();
        this.activityBusinessController.updateDuration(id, activityUpdateDto);
    }
}
