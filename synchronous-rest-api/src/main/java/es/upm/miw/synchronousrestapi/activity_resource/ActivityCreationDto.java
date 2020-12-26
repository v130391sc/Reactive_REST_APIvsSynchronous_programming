package es.upm.miw.synchronousrestapi.activity_resource;

import es.upm.miw.synchronousrestapi.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;

public class ActivityCreationDto {

    private static final Integer ACTIVITY_MIN_LEVEL = 0;
    private static final Integer ACTIVITY_MAX_LEVEL = 10;
    private static final Double ZERO = 0.0;
    private String activityName;
    private Double length;
    private Integer minLevelRequired;
    private List<String> clientsIds;
    private String trainerId;

    public ActivityCreationDto() {
    }

    public ActivityCreationDto(String activityName, Double length, Integer minLevelRequired, String trainerId) {
        this.activityName = activityName;
        this.length = length;
        this.minLevelRequired = minLevelRequired;
        this.clientsIds = new ArrayList<>();
        this.trainerId = trainerId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getMinLevelRequired() {
        return minLevelRequired;
    }

    public void setMinLevelRequired(Integer minLevelRequired) {
        this.minLevelRequired = minLevelRequired;
    }

    public List<String> getClientsIds() {
        return clientsIds;
    }

    public void setClientsIds(List<String> clientsIds) {
        this.clientsIds = clientsIds;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public void validate() {
        if (this.activityName == null || this.activityName.isEmpty() || this.length == null || this.minLevelRequired == null) {
            throw new BadRequestException("Incomplete ClientCreationDto");
        } else if (this.minLevelRequired.compareTo(ACTIVITY_MIN_LEVEL) < 0 || this.minLevelRequired.compareTo(ACTIVITY_MAX_LEVEL) > 0) {
            throw new BadRequestException("Activity Level must be between 0 to 10");
        } else if (this.length.compareTo(ZERO) < 0) {
            throw new BadRequestException("Length must be greater than 0");
        }
    }

    @Override
    public String toString() {
        return "ActivityCreationDto{" +
                "activityName='" + activityName + '\'' +
                ", length='" + length + '\'' +
                ", minLevelRequired='" + minLevelRequired + '\'' +
                ", clientsIds=" + clientsIds +
                ", trainerId='" + trainerId + '\'' +
                '}';
    }
}
