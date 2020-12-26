package es.upm.miw.synchronousrestapi.room_resource;

public class EquipmentDto {

    private String equipment;

    private String quantity;

    public EquipmentDto() {
        //Empty for framework
    }

    public EquipmentDto(String equipment, String quantity) {
        this.equipment = equipment;
        this.quantity = quantity;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "EquipmentDto{" +
                "equipment='" + equipment + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
