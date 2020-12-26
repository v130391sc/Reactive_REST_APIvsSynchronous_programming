package es.upm.miw.synchronousrestapi.trainer_resource;

import es.upm.miw.synchronousrestapi.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public TrainerDto create(@RequestBody TrainerCreationDto trainerCreationDto) {
        trainerCreationDto.validate();
        return this.trainerBusinessController.create(trainerCreationDto);
    }

    @GetMapping(value = SEARCH)
    public List<TrainerDto> find(@RequestParam String query) {
        if (!"name".equals(query.split(":=")[0])) {
            throw new BadRequestException("query param q is incorrect, missing 'name:='");
        }
        return this.trainerBusinessController.findByName(query.split(":=")[1]);
    }

    @PutMapping(value = ID + NAME)
    public void updateName(@PathVariable String id, @RequestBody TrainerDto trainerDto){
        trainerDto.validateName();
        this.trainerBusinessController.updateName(id, trainerDto.getName());
    }

    @PatchMapping
    public void patch(@RequestBody List<TrainerPatchDto> trainerPatchDtos) {
        trainerPatchDtos.forEach(TrainerPatchDto::validate);
        this.trainerBusinessController.patch(trainerPatchDtos);
    }
}
