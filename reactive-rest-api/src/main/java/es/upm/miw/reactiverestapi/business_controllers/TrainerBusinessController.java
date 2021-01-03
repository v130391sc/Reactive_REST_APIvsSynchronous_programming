package es.upm.miw.reactiverestapi.business_controllers;

import es.upm.miw.reactiverestapi.documents.Trainer;
import es.upm.miw.reactiverestapi.dtos.TrainerCreationDto;
import es.upm.miw.reactiverestapi.dtos.TrainerDto;
import es.upm.miw.reactiverestapi.dtos.TrainerPatchDto;
import es.upm.miw.reactiverestapi.exceptions.NotFoundException;
import es.upm.miw.reactiverestapi.repositories.TrainerReactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class TrainerBusinessController {

    private TrainerReactRepository trainerReactRepository;

    @Autowired
    public TrainerBusinessController(TrainerReactRepository trainerReactRepository) {
        this.trainerReactRepository = trainerReactRepository;
    }

    public Mono<TrainerDto> create(TrainerCreationDto trainerCreationDto) {
        return this.trainerReactRepository.save(new Trainer(trainerCreationDto.getName())).map(TrainerDto::new);
    }

    public Flux<TrainerDto> findByName(String nameFilter) {
        return this.trainerReactRepository.findByNameStartingWith(nameFilter)
                .map(TrainerDto::new);
    }

    public Mono<Void> patch(List<TrainerPatchDto> trainerPatchDtos) {
        Flux<Trainer> trainerFlux = Flux.empty();
        for(TrainerPatchDto trainerPatchDto : trainerPatchDtos){
            Mono<Trainer> trainer = this.trainerReactRepository.findById(trainerPatchDto.getTrainerId())
                    .switchIfEmpty(Mono.error(new NotFoundException("Trainer id: " + trainerPatchDto.getTrainerId())))
                    .map(trainer1 -> {
                        trainer1.setName(trainerPatchDto.getNewName());
                        return trainer1;
                    });
            trainerFlux = trainerFlux.mergeWith(trainer);
        }
        return Mono.when(trainerFlux).then(this.trainerReactRepository.saveAll(trainerFlux).next()).then();
    }

    public Mono<TrainerDto> updateName(String id, String name){
        Mono<Trainer> trainer = this.findTrainerById(id).map(trainer1 -> {
            trainer1.setName(name);
            return trainer1;
        });
        return Mono.when(trainer).then(this.trainerReactRepository.saveAll(trainer).next()).map(TrainerDto::new);
    }

    private Mono<Trainer> findTrainerById(String id) {
        return this.trainerReactRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Trainer id: " + id)));
    }
}
