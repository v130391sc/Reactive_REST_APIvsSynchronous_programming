package es.upm.miw.reactiverestapi.dtos;

import es.upm.miw.reactiverestapi.exceptions.BadRequestException;

public class ActivityUpdateDto {

    private static final Double MIN_DURATION = 0.0;

    private String id;

    private Double lenght;

    public ActivityUpdateDto() {
        //Empty for the Framework
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLenght() {
        return lenght;
    }

    public void setLenght(Double lenght) {
        this.lenght = lenght;
    }

    public void validateDuration() {
        if (this.lenght == null) {
            throw new BadRequestException("Incomplete, lost duration");
        } else if (this.lenght.compareTo(MIN_DURATION) < 0) {
            throw new BadRequestException("Duration must be equal or more than 0");
        }
    }

    @Override
    public String toString() {
        return "ActivityUpdateDto{" +
                "id='" + id + '\'' +
                ", lenght=" + lenght +
                '}';
    }
}
