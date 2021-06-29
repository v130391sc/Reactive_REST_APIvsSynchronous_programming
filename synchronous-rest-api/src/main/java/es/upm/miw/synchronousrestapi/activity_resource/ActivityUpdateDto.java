package es.upm.miw.synchronousrestapi.activity_resource;

import es.upm.miw.synchronousrestapi.exceptions.BadRequestException;

public class ActivityUpdateDto {

    private static final Double MIN_DURATION = 0.0;

    private String id;

    private Double length;

    public ActivityUpdateDto() {
        //Empty for the Framework
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public void validateDuration() {
        if (this.length == null) {
            throw new BadRequestException("Incomplete, lost duration");
        } else if (this.length.compareTo(MIN_DURATION) < 0) {
            throw new BadRequestException("Duration must be equal or more than 0");
        }
    }

    @Override
    public String toString() {
        return "ActivityUpdateDto{" +
                "id='" + id + '\'' +
                ", lenght=" + length +
                '}';
    }
}
