package es.upm.miw.synchronousrestapi.room_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public RoomBasicDto create(@RequestBody RoomCreationDto roomCreationDto) {
        roomCreationDto.validate();
        return this.roomBusinessController.create(roomCreationDto);
    }

    @PatchMapping
    public void patch(@RequestBody RoomPatchDto roomPatchDto){
        roomPatchDto.validate();
        this.roomBusinessController.updateAllRoomsSchedule(roomPatchDto);
    }
}
