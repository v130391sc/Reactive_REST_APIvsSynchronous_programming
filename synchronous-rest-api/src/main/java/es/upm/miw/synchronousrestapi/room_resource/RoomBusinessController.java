package es.upm.miw.synchronousrestapi.room_resource;

import es.upm.miw.synchronousrestapi.activity_resource.Activity;
import es.upm.miw.synchronousrestapi.activity_resource.ActivityDao;
import es.upm.miw.synchronousrestapi.exceptions.BadRequestException;
import es.upm.miw.synchronousrestapi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RoomBusinessController {

    private RoomDao roomDao;

    private ActivityDao activityDao;

    @Autowired
    public RoomBusinessController(RoomDao roomDao, ActivityDao activityDao) {
        this.roomDao = roomDao;
        this.activityDao = activityDao;
    }

    public RoomBasicDto create(RoomCreationDto roomCreationDto) {
        Activity activity = this.activityDao.findById(roomCreationDto.getActivityId())
                .orElseThrow(() -> new NotFoundException("Activity id: " + roomCreationDto.getActivityId()));
        Room room = new Room(roomCreationDto.getActivitySchedule(), roomCreationDto.getSeating(), activity);
        this.roomDao.save(room);
        return new RoomBasicDto(room);
    }

    public void updateAllRoomsSchedule(RoomPatchDto roomPatchDto){
        if(roomPatchDto.getPath().equals("schedule")){
            List<Room> roomList = this.roomDao.findAll();
            for (Room room : roomList) {
                room.setSchedule(room.getSchedule().plusHours(roomPatchDto.getNewValue()));
            }
            this.roomDao.saveAll(roomList);
        } else {
            throw new BadRequestException("RoomPatchDto is invalid");
        }
    }
}
