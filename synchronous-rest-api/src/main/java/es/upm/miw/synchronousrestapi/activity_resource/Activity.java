package es.upm.miw.synchronousrestapi.activity_resource;

import es.upm.miw.synchronousrestapi.client_resource.Client;
import es.upm.miw.synchronousrestapi.trainer_resource.Trainer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Activity {

    @Id
    private String id;

    private String name;

    private Double duration;

    private Integer level;

    @DBRef
    private List<Client> clients;

    @DBRef
    private Trainer trainer;

    public Activity(String name, Double duration, Integer level, Trainer trainer) {
        this.name = name;
        this.duration = duration;
        this.level = level;
        this.trainer = trainer;
        this.clients = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Client> getClients() {
        return clients;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", level=" + level +
                ", clients=" + clients +
                ", trainer=" + trainer +
                '}';
    }
}




