package es.upm.miw.reactiverestapi.dtos;

import es.upm.miw.reactiverestapi.exceptions.BadRequestException;

public class TrainerPatchDto {

    private String trainerId;

    private String newName;

    public TrainerPatchDto() {
        // Empty for framework
    }

    public TrainerPatchDto(String trainerId, String newName) {
        this.trainerId = trainerId;
        this.newName = newName;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public void validate() {
        if (this.trainerId == null || this.trainerId.isEmpty() || this.newName == null || this.newName.isEmpty()) {
            throw new BadRequestException("Incomplete List of TrainerPatchDtos, all Trainers must have both fields");
        }
    }

    @Override
    public String toString() {
        return "TrainerPatchDto{" +
                "trainerId='" + trainerId + '\'' +
                ", newName='" + newName + '\'' +
                '}';
    }
}
