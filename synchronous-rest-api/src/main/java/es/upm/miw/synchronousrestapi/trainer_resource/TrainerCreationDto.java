package es.upm.miw.synchronousrestapi.trainer_resource;

import es.upm.miw.synchronousrestapi.exceptions.BadRequestException;

public class TrainerCreationDto {

    private String name;

    public TrainerCreationDto() {
        // Empty for framework
    }

    public TrainerCreationDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void validate() {
        if (this.name == null || this.name.isEmpty()) {
            throw new BadRequestException("Incomplete TrainerCreationDto");
        }
    }

    @Override
    public String toString() {
        return "TrainerCreationDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
