package es.upm.miw.reactiverestapi.api_rest_controllers;

import es.upm.miw.reactiverestapi.business_controllers.RoomBusinessController;
import es.upm.miw.reactiverestapi.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RestController
@RequestMapping(RoomResource.ROOMS)
public class RoomResource {

    public static final String ROOMS = "/v0/rooms";

    private RoomBusinessController roomBusinessController;

    private ClientResource clientResource;

    private TrainerResource trainerResource;

    private ActivityResource activityResource;

    @Autowired
    public RoomResource(RoomBusinessController roomBusinessController, ClientResource clientResource, TrainerResource trainerResource, ActivityResource activityResource) {
        this.roomBusinessController = roomBusinessController;
        this.clientResource = clientResource;
        this.trainerResource = trainerResource;
        this.activityResource = activityResource;
    }

    @PostMapping
    public Mono<RoomBasicDto> create(@RequestBody RoomCreationDto roomCreationDto) {
        roomCreationDto.validate();
        return this.roomBusinessController.create(roomCreationDto);
    }

    @PatchMapping
    public Mono<Void> patch(@RequestBody RoomPatchDto roomPatchDto){
        roomPatchDto.validate();
        return this.roomBusinessController.updateAllRoomsSchedule(roomPatchDto);
    }

    @PatchMapping(value = "full")
    public Mono<RoomBasicDto> patchFull(@RequestBody RoomFullDto roomFullDto){
        WebClient client = WebClient.create("http://localhost:8081");
        RoomCreationDto roomCreationDto = new RoomCreationDto();
        Mono<ActivityBasicDto> clientAndTrainer = createClientAndTrainer(client, roomFullDto)
                .doOnNext(activityBasicDto -> {
                    roomCreationDto.setActivityId(activityBasicDto.getId());
                    roomCreationDto.setSeating(roomFullDto.getSeating());
                    roomCreationDto.setActivitySchedule(roomFullDto.getActivitySchedule());
                });

        return Mono.when(clientAndTrainer).then(client.post().uri("/v0/rooms").bodyValue(roomCreationDto).retrieve().bodyToMono(RoomBasicDto.class));
    }

    private Mono<ActivityBasicDto> createClientAndTrainer(WebClient client, RoomFullDto roomFullDto) {
        Mono<ClientBasicDto> clientBasicDtoMono = client.post()
                .uri("/v0/clients")
                .bodyValue(roomFullDto.getClient())
                .retrieve()
                .bodyToMono(ClientBasicDto.class)
                .doOnNext(clientBasicDto -> roomFullDto.getActivity().setClientsIds(Arrays.asList(clientBasicDto.getId())));
        Mono<TrainerDto> trainerDtoMono = client.post()
                .uri("/v0/trainers")
                .bodyValue(roomFullDto.getTrainer())
                .retrieve()
                .bodyToMono(TrainerDto.class)
                .doOnNext(trainerDto -> roomFullDto.getActivity().setTrainerId(trainerDto.getId()));

        return Mono.when(clientBasicDtoMono, trainerDtoMono).then(client.post().uri("/v0/activities").bodyValue(roomFullDto.getActivity()).retrieve().bodyToMono(ActivityBasicDto.class));

    }
}
