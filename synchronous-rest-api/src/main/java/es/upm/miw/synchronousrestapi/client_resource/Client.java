package es.upm.miw.synchronousrestapi.client_resource;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhysicalLevel() {
        return physicalLevel;
    }

    public void setPhysicalLevel(Integer physicalLevel) {
        this.physicalLevel = physicalLevel;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", physicalLevel=" + physicalLevel +
                ", birthDate=" + birthDate +
                '}';
    }
}
