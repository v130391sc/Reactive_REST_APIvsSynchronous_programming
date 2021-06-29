package es.upm.miw.synchronousrestapi.room_resource;

import es.upm.miw.synchronousrestapi.activity_resource.ActivityCreationDto;
import es.upm.miw.synchronousrestapi.client_resource.ClientCreationDto;
import es.upm.miw.synchronousrestapi.trainer_resource.TrainerCreationDto;

import java.time.LocalDateTime;
import java.util.List;

public class RoomFullDto {

    private ClientCreationDto client;

    private TrainerCreationDto trainer;

    private ActivityCreationDto activity;

    private LocalDateTime activitySchedule;

    private Integer seating;

    public RoomFullDto() {
    }

    public RoomFullDto(ClientCreationDto client, TrainerCreationDto trainer, ActivityCreationDto activity, LocalDateTime activitySchedule, Integer seating) {
        this.client = client;
        this.trainer = trainer;
        this.activity = activity;
        this.activitySchedule = activitySchedule;
        this.seating = seating;
    }

    public ClientCreationDto getClient() {
        return client;
    }

    public void setClient(ClientCreationDto client) {
        this.client = client;
    }

    public TrainerCreationDto getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerCreationDto trainer) {
        this.trainer = trainer;
    }

    public ActivityCreationDto getActivity() {
        return activity;
    }

    public void setActivity(ActivityCreationDto activity) {
        this.activity = activity;
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

    @Override
    public String toString() {
        return "RoomFullDto{" +
                "client=" + client +
                ", trainer=" + trainer +
                ", activity=" + activity +
                ", activitySchedule=" + activitySchedule +
                ", seating=" + seating +
                '}';
    }
}
