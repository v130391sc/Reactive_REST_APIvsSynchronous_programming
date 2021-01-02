package es.upm.miw.reactiverestapi.dtos;

import es.upm.miw.reactiverestapi.documents.Trainer;
import es.upm.miw.reactiverestapi.exceptions.BadRequestException;

public class TrainerDto {

    private String id;

    private String name;

    public TrainerDto() {
        // Empty for framework
    }

    public TrainerDto(Trainer trainer) {
        this.id = trainer.getId();
        this.name = trainer.getName();
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

    public void validateName() {
        if (this.name == null || this.name.isEmpty()) {
            throw new BadRequestException("Incomplete, lost name");
        }
    }

    @Override
    public String toString() {
        return "TrainerDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
