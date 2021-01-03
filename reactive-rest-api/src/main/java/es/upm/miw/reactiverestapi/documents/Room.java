package es.upm.miw.reactiverestapi.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class Room {

    @Id
    private String id;

    private LocalDateTime schedule;

    private Integer capacity;

    @DBRef
    private Activity activity;

    private List<Equipment> equipments;

    public Room(LocalDateTime schedule, Integer capacity) {
        this.schedule = schedule;
        this.capacity = capacity;
        this.activity = activity;
        this.equipments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDateTime schedule) {
        this.schedule = schedule;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Activity getActivity() {
        return activity;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", schedule='" + schedule + '\'' +
                ", capacity=" + capacity +
                ", equipment=" + equipments +
                ", activity=" + activity +
                '}';
    }
}
