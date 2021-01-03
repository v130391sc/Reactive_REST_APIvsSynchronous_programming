package es.upm.miw.reactiverestapi.documents;

public class Equipment {

    private String name;

    private Integer amount;

    public Equipment(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
    }
}
