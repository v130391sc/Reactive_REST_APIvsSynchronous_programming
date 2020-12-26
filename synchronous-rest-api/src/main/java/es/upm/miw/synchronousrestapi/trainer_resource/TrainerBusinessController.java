package es.upm.miw.synchronousrestapi.trainer_resource;

import es.upm.miw.synchronousrestapi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TrainerBusinessController {

    private TrainerDao trainerDao;

    @Autowired
    public TrainerBusinessController(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    public TrainerDto create(TrainerCreationDto trainerCreationDto) {
        Trainer trainer = new Trainer(trainerCreationDto.getName());
        this.trainerDao.save(trainer);
        return new TrainerDto(trainer);
    }

    public List<TrainerDto> findByName(String nameFilter) {
        return this.trainerDao.findByNameStartingWith(nameFilter).stream()
                .map(TrainerDto::new)
                .collect(Collectors.toList());
    }

    public void patch(List<TrainerPatchDto> trainerPatchDtos) {
        List<Trainer> trainerList = new ArrayList<>();
        trainerPatchDtos.stream().forEach(trainerPatchDto -> {
            Trainer trainer = this.trainerDao.findById(trainerPatchDto.getTrainerId()).orElseThrow(() -> new NotFoundException("Trainer id: " + trainerPatchDto.getTrainerId()));
            trainer.setName(trainerPatchDto.getNewName());
            trainerList.add(trainer);
        });
        this.trainerDao.saveAll(trainerList);
    }

    private Trainer findTrainerById(String id) {
        return this.trainerDao.findById(id).orElseThrow(() -> new NotFoundException("Trainer id: " + id));
    }

    public void updateName(String id, String name){
        Trainer trainer = this.findTrainerById(id);
        trainer.setName(name);
        this.trainerDao.save(trainer);
    }
}
