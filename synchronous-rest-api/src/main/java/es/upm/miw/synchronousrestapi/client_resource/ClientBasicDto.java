package es.upm.miw.synchronousrestapi.client_resource;

public class ClientBasicDto {

    private String id;

    private String name;

    public ClientBasicDto() {
    }

    public ClientBasicDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClientBasicDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
