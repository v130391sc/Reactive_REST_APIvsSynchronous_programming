package es.upm.miw.synchronousrestapi.room_resource;

import java.time.LocalDateTime;

public class RoomBasicDto {

    private String id;

    private LocalDateTime activitySchedule;

    public RoomBasicDto() {
        // Empty for framework
    }

    public RoomBasicDto(Room room) {
        this.id = room.getId();
        this.activitySchedule = room.getSchedule();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getActivitySchedule() {
        return activitySchedule;
    }

    public void setActivitySchedule(LocalDateTime activitySchedule) {
        this.activitySchedule = activitySchedule;
    }

    @Override
    public String toString() {
        return "RoomBasicDto{" +
                "id='" + id + '\'' +
                ", activitySchedule=" + activitySchedule +
                '}';
    }
}
