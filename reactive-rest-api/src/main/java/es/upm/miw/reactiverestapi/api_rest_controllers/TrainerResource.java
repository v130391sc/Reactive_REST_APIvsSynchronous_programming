package es.upm.miw.reactiverestapi.api_rest_controllers;

import es.upm.miw.reactiverestapi.business_controllers.TrainerBusinessController;
import es.upm.miw.reactiverestapi.dtos.TrainerCreationDto;
import es.upm.miw.reactiverestapi.dtos.TrainerDto;
import es.upm.miw.reactiverestapi.dtos.TrainerPatchDto;
import es.upm.miw.reactiverestapi.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(TrainerResource.TRAINERS)
public class TrainerResource {

    public static final String TRAINERS = "/v0/trainers";
    public static final String SEARCH = "/v0/search";
    static final String ID = "/{id}";
    static final String NAME = "/name";

    private TrainerBusinessController trainerBusinessController;

    @Autowired
    public TrainerResource(TrainerBusinessController trainerBusinessController) {
        this.trainerBusinessController = trainerBusinessController;
    }

    @PostMapping
    public Mono<TrainerDto> create(@RequestBody TrainerCreationDto trainerCreationDto) {
        trainerCreationDto.validate();
        return this.trainerBusinessController.create(trainerCreationDto);
    }

    @GetMapping(value = SEARCH)
    public Flux<TrainerDto> find(@RequestParam String query) {
        if (!"name".equals(query.split(":=")[0])) {
            throw new BadRequestException("query param q is incorrect, missing 'name:='");
        }
        return this.trainerBusinessController.findByName(query.split(":=")[1]);
    }

    @PutMapping(value = ID + NAME)
    public Mono<TrainerDto> updateName(@PathVariable String id, @RequestBody TrainerDto trainerDto){
        trainerDto.validateName();
        return this.trainerBusinessController.updateName(id, trainerDto.getName());
    }

    @PatchMapping
    public Mono<Void> patch(@RequestBody List<TrainerPatchDto> trainerPatchDtos) {
        trainerPatchDtos.forEach(TrainerPatchDto::validate);
        return this.trainerBusinessController.patch(trainerPatchDtos);
    }
}
