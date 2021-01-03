package es.upm.miw.reactiverestapi.dtos;

import es.upm.miw.reactiverestapi.exceptions.BadRequestException;

import java.time.LocalDateTime;

public class RoomCreationDto {

    private static final Integer MIN_SEATING = 0;
    private LocalDateTime activitySchedule;
    private Integer seating;
    private String activityId;

    public RoomCreationDto() {
        //Empty for framework
    }

    public RoomCreationDto(LocalDateTime activitySchedule, Integer seating, String activityId) {
        this.activitySchedule = activitySchedule;
        this.seating = seating;
        this.activityId = activityId;
    }

    public LocalDateTime getActivitySchedule() {
        return activitySchedule;
    }

    public void setActivitySchedule(LocalDateTime activitySchedule) {
        this.activitySchedule = activitySchedule;
    }

    public Integer getSeating() {
        return seating;
    }

    public void setSeating(Integer seating) {
        this.seating = seating;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public void validate() {
        if (this.activitySchedule == null || this.seating == null || this.activityId == null || this.activityId.isEmpty()) {
            throw new BadRequestException("Incomplete RoomCreationDto");
        } else if (this.seating.compareTo(MIN_SEATING) < 0) {
            throw new BadRequestException("Seating must be greater than zero");
        }
    }

    @Override
    public String toString() {
        return "RoomCreationDto{" +
                "activitySchedule=" + activitySchedule +
                ", seating=" + seating +
                ", activityId='" + activityId + '\'' +
                '}';
    }
}
