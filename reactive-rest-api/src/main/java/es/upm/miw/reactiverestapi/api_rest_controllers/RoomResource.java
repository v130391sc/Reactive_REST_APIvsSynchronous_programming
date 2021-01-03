package es.upm.miw.reactiverestapi.api_rest_controllers;

import es.upm.miw.reactiverestapi.business_controllers.RoomBusinessController;
import es.upm.miw.reactiverestapi.dtos.RoomBasicDto;
import es.upm.miw.reactiverestapi.dtos.RoomCreationDto;
import es.upm.miw.reactiverestapi.dtos.RoomPatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(RoomResource.ROOMS)
public class RoomResource {

    public static final String ROOMS = "/v0/rooms";

    private RoomBusinessController roomBusinessController;

    @Autowired
    public RoomResource(RoomBusinessController roomBusinessController) {
        this.roomBusinessController = roomBusinessController;
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
}
