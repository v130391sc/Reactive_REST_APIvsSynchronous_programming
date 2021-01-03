package es.upm.miw.reactiverestapi.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Equipment {

    private String name;

    private Integer amount;

    public Equipment(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }
}
