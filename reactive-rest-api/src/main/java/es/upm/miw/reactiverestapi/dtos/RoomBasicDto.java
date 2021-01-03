package es.upm.miw.reactiverestapi.dtos;

import es.upm.miw.reactiverestapi.documents.Room;

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
