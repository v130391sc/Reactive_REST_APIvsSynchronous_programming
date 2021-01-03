package es.upm.miw.reactiverestapi.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class Client {

    @Id
    private String id;

    private String name;

    private Integer physicalLevel;

    private LocalDateTime birthDate;

    public Client(String name, Integer physicalLevel, LocalDateTime birthDate) {
        this.name = name;
        this.physicalLevel = physicalLevel;
        this.birthDate = birthDate;
    }
}
