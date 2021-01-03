package es.upm.miw.reactiverestapi.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class Room {

    @Id
    private String id;

    private LocalDateTime schedule;

    private Integer capacity;

    private String activityId;

    private List<Equipment> equipments;

    public Room(LocalDateTime schedule, Integer capacity) {
        this.schedule = schedule;
        this.capacity = capacity;
        this.equipments = new ArrayList<>();
    }

}
