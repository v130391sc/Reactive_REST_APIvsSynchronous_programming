package es.upm.miw.reactiverestapi.business_controllers;

import es.upm.miw.reactiverestapi.documents.Activity;
import es.upm.miw.reactiverestapi.documents.Client;
import es.upm.miw.reactiverestapi.documents.Trainer;
import es.upm.miw.reactiverestapi.dtos.ActivityBasicDto;
import es.upm.miw.reactiverestapi.dtos.ActivityCreationDto;
import es.upm.miw.reactiverestapi.dtos.ActivityUpdateDto;
import es.upm.miw.reactiverestapi.exceptions.NotFoundException;
import es.upm.miw.reactiverestapi.repositories.ActivityReactRepository;
import es.upm.miw.reactiverestapi.repositories.ClientReactRepository;
import es.upm.miw.reactiverestapi.repositories.TrainerReactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ActivityBusinessController {

    private ActivityReactRepository activityReactRepository;

    private TrainerReactRepository trainerReactRepository;

    private ClientReactRepository clientReactRepository;

    @Autowired
    public ActivityBusinessController(ActivityReactRepository activityReactRepository, TrainerReactRepository trainerReactRepository, ClientReactRepository clientReactRepository) {
        this.activityReactRepository = activityReactRepository;
        this.trainerReactRepository = trainerReactRepository;
        this.clientReactRepository = clientReactRepository;
    }

    public Mono<ActivityBasicDto> create(ActivityCreationDto activityCreationDto) {
        Mono<Trainer> trainer = Mono.empty();
        Flux<Client> client = Flux.empty();
        Activity activity = new Activity(activityCreationDto.getActivityName(), activityCreationDto.getLength(), activityCreationDto.getMinLevelRequired());
        if (activityCreationDto.getTrainerId() != null) {
            trainer = this.trainerReactRepository.findById(activityCreationDto.getTrainerId())
                    .switchIfEmpty(Mono.error(new NotFoundException("Trainer id: " + activityCreationDto.getTrainerId()))).map(trainer1 -> {
                        activity.setTrainerId(trainer1.getId());
                        return trainer1;
                    });
        }
        if (activityCreationDto.getClientsIds() != null) {
            client = Flux.fromStream(activityCreationDto.getClientsIds().stream())
                    .flatMap(clientId -> this.clientReactRepository.findById(clientId)
                            .switchIfEmpty(Mono.error(new NotFoundException("Client id: " + clientId))))
                    .doOnNext(client1 -> activity.getClientsIds().add(client1.getId()));
        }
        return Mono.when(trainer, client).then(this.activityReactRepository.save(activity)).map(ActivityBasicDto::new);
    }

    public Mono<Void> delete(String id) {
        return Mono.when(this.find(id)).then(this.activityReactRepository.deleteById(id));
    }

    private Mono<Activity> find(String id) {
        return this.activityReactRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Activity id: " + id)));
    }

    public Flux<ActivityBasicDto> findByClientPhysicalLevelGreaterThan(Integer value) {
        return this.activityReactRepository.findAll()
                .flatMap(activity -> this.anyMatchActivityClientPhysicalLevelGreaterThan(activity, value))
                .map(ActivityBasicDto::new);
    }

    private Flux<Activity> anyMatchActivityClientPhysicalLevelGreaterThan(Activity activity, Integer value) {
        Flux<Activity> activityFlux = Flux.empty();
        for(String clientId : activity.getClientsIds()){
            Mono<Activity> clientMono = this.clientReactRepository.findById(clientId).filter(client1 -> client1.getPhysicalLevel() > value).map(client -> activity);
            activityFlux = activityFlux.mergeWith(clientMono);
        }
        return activityFlux.distinct();
    }

    public Mono<ActivityBasicDto> updateDuration(String id, ActivityUpdateDto activityUpdateDto) {
        Mono<Activity> activity = this.activityReactRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Activity id: " + id)))
                .map(activity1 -> {
                    activity1.setDuration(activityUpdateDto.getLenght());
                    return activity1;
                });
        return Mono.when(activity).then(this.activityReactRepository.saveAll(activity).next()).map(ActivityBasicDto::new);
    }
}
