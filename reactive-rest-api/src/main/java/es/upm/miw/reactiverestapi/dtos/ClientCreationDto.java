package es.upm.miw.reactiverestapi.dtos;

import es.upm.miw.reactiverestapi.exceptions.BadRequestException;

import java.time.LocalDateTime;

public class ClientCreationDto {

    private static final Integer CLIENT_MIN_LEVEL = 0;
    private static final Integer MAX_LEVEL = 10;
    private String fullName;
    private Integer strongLevel;
    private LocalDateTime birthDay;

    public ClientCreationDto() {
    }

    public ClientCreationDto(String nombre, Integer physicalLevel, LocalDateTime birthDate) {
        this.fullName = nombre;
        this.strongLevel = physicalLevel;
        this.birthDay = birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getStrongLevel() {
        return strongLevel;
    }

    public void setStrongLevel(Integer strongLevel) {
        this.strongLevel = strongLevel;
    }

    public LocalDateTime getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay;
    }

    public void validate() {
        if (this.fullName == null || this.fullName.isEmpty() || this.strongLevel == null || this.birthDay == null) {
            throw new BadRequestException("Incomplete ClientCreationDto");
        } else if (this.strongLevel.compareTo(CLIENT_MIN_LEVEL) < 0 || this.strongLevel.compareTo(MAX_LEVEL) > 0) {
            throw new BadRequestException("physical level must be between 0 to 10");
        }
    }

    @Override
    public String toString() {
        return "ClientCreationDto{" +
                "nombre='" + fullName + '\'' +
                ", physicalLevel=" + strongLevel +
                ", birthDate=" + birthDay +
                '}';
    }
}
