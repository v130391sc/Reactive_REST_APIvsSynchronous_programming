package es.upm.miw.synchronousrestapi.room_resource;

import es.upm.miw.synchronousrestapi.activity_resource.ActivityBasicDto;
import es.upm.miw.synchronousrestapi.activity_resource.ActivityCreationDto;
import es.upm.miw.synchronousrestapi.activity_resource.ActivityResource;
import es.upm.miw.synchronousrestapi.client_resource.ClientBasicDto;
import es.upm.miw.synchronousrestapi.client_resource.ClientCreationDto;
import es.upm.miw.synchronousrestapi.client_resource.ClientResource;
import es.upm.miw.synchronousrestapi.trainer_resource.TrainerCreationDto;
import es.upm.miw.synchronousrestapi.trainer_resource.TrainerDto;
import es.upm.miw.synchronousrestapi.trainer_resource.TrainerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
    public RoomBasicDto create(@RequestBody RoomCreationDto roomCreationDto) {
        roomCreationDto.validate();
        return this.roomBusinessController.create(roomCreationDto);
    }

    @PatchMapping
    public void patch(@RequestBody RoomPatchDto roomPatchDto){
        roomPatchDto.validate();
        this.roomBusinessController.updateAllRoomsSchedule(roomPatchDto);
    }

    @PatchMapping(value = "full")
    public RoomBasicDto patchFull(@RequestBody RoomFullDto roomFullDto){
        String createClientURL = "http://localhost:8080/v0/clients";
        String createTrainerURL = "http://localhost:8080/v0/trainers";
        String createActivityURL = "http://localhost:8080/v0/activities";
        String createRoomURL = "http://localhost:8080/v0/rooms";

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<ClientCreationDto> clientRequest = new HttpEntity<>(roomFullDto.getClient());
        ClientBasicDto clientBasicDto = restTemplate.postForObject(createClientURL, clientRequest, ClientBasicDto.class);

        HttpEntity<TrainerCreationDto> trainerRequest = new HttpEntity<>(roomFullDto.getTrainer());
        TrainerDto trainerDto = restTemplate.postForObject(createTrainerURL, trainerRequest, TrainerDto.class);

        roomFullDto.getActivity().setClientsIds(Arrays.asList(clientBasicDto.getId()));
        roomFullDto.getActivity().setTrainerId(trainerDto.getId());

        HttpEntity<ActivityCreationDto> activityRequest = new HttpEntity<>(roomFullDto.getActivity());
        ActivityBasicDto activityBasicDto = restTemplate.postForObject(createActivityURL, activityRequest, ActivityBasicDto.class);

        RoomCreationDto roomCreationDto = new RoomCreationDto(roomFullDto.getActivitySchedule(), roomFullDto.getSeating(), activityBasicDto.getId());

        HttpEntity<RoomCreationDto> roomRequest = new HttpEntity<>(roomCreationDto);
        return restTemplate.postForObject(createRoomURL, roomRequest, RoomBasicDto.class);
    }
}
