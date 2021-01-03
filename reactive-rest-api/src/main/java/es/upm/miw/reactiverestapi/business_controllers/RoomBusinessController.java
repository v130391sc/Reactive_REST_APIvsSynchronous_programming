package es.upm.miw.reactiverestapi.business_controllers;

import es.upm.miw.reactiverestapi.documents.Activity;
import es.upm.miw.reactiverestapi.documents.Room;
import es.upm.miw.reactiverestapi.dtos.RoomBasicDto;
import es.upm.miw.reactiverestapi.dtos.RoomCreationDto;
import es.upm.miw.reactiverestapi.dtos.RoomPatchDto;
import es.upm.miw.reactiverestapi.exceptions.BadRequestException;
import es.upm.miw.reactiverestapi.exceptions.NotFoundException;
import es.upm.miw.reactiverestapi.repositories.ActivityReactRepository;
import es.upm.miw.reactiverestapi.repositories.RoomReactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class RoomBusinessController {

    private RoomReactRepository roomReactRepository;

    private ActivityReactRepository activityReactRepository;

    @Autowired
    public RoomBusinessController(RoomReactRepository roomReactRepository, ActivityReactRepository activityReactRepository) {
        this.roomReactRepository = roomReactRepository;
        this.activityReactRepository = activityReactRepository;
    }

    public Mono<RoomBasicDto> create(RoomCreationDto roomCreationDto) {
        Room room = new Room(roomCreationDto.getActivitySchedule(), roomCreationDto.getSeating());
        Mono<Activity> activity = this.activityReactRepository.findById(roomCreationDto.getActivityId())
                .switchIfEmpty(Mono.error(new NotFoundException("Activity id: " + roomCreationDto.getActivityId()))).map(activity1 -> {
                    room.setActivityId(activity1.getId());
                    return activity1;
                });
        return Mono.when(activity).then(this.roomReactRepository.save(room)).map(RoomBasicDto::new);
    }

    public Mono<Void> updateAllRoomsSchedule(RoomPatchDto roomPatchDto){
        Flux<Room> roomFlux = Flux.empty();
        if(roomPatchDto.getPath().equals("schedule")){
            roomFlux = this.roomReactRepository.findAll().map(room -> {
                room.setSchedule(room.getSchedule().plusHours(roomPatchDto.getNewValue()));
                return room;
            });
        } else {
            throw new BadRequestException("RoomPatchDto is invalid");
        }
        return Mono.when(roomFlux).then(this.roomReactRepository.saveAll(roomFlux).next()).then();
    }
}
