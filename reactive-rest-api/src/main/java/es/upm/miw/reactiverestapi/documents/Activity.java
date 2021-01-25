package es.upm.miw.reactiverestapi.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class Activity {

    @Id
    private String id;

    private String name;

    private Double duration;

    private Integer level;

    private List<String> clientsIds;

    private String trainerId;

    public Activity(String name, Double duration, Integer level) {
        this.name = name;
        this.duration = duration;
        this.level = level;
        this.clientsIds = new ArrayList<>();
    }

}




