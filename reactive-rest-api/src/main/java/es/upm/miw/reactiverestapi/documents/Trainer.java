package es.upm.miw.reactiverestapi.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class Trainer {

    @Id
    private String id;

    private String name;

    public Trainer(String name) {
        this.name = name;
    }

}
