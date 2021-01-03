package es.upm.miw.reactiverestapi.api_rest_controllers;

import es.upm.miw.reactiverestapi.business_controllers.ActivityBusinessController;
import es.upm.miw.reactiverestapi.dtos.ActivityBasicDto;
import es.upm.miw.reactiverestapi.dtos.ActivityCreationDto;
import es.upm.miw.reactiverestapi.dtos.ActivityUpdateDto;
import es.upm.miw.reactiverestapi.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<ActivityBasicDto> create(@RequestBody ActivityCreationDto activityCreationDto) {
        activityCreationDto.validate();
        return this.activityBusinessController.create(activityCreationDto);
    }

    @DeleteMapping(value = ID_ID)
    public Mono<Void> delete(@PathVariable String id) {
        return this.activityBusinessController.delete(id);
    }

    @GetMapping(value = SEARCH)
    public Flux<ActivityBasicDto> find(@RequestParam String q) {
        if (!"client.physicalLevel".equals(q.split(":>")[0])) {
            throw new BadRequestException("query param q is incorrect, missing 'client.physicalLevel:>'");
        }
        return this.activityBusinessController.findByClientPhysicalLevelGreaterThan(Integer.valueOf(q.split(":>")[1]));
    }

    @PutMapping(value = ID_ID + DURATION)
    public Mono<ActivityBasicDto> updateDuration(@PathVariable String id, @RequestBody ActivityUpdateDto activityUpdateDto) {
        activityUpdateDto.validateDuration();
        return this.activityBusinessController.updateDuration(id, activityUpdateDto);
    }
}
