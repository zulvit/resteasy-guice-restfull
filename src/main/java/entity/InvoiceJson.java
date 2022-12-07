package entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceJson {
    private final int id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("firm")
    private final String firm;
    @JsonProperty("amount")
    private final String amount;

    @JsonCreator
    public InvoiceJson(@JsonProperty(value = "id") int id,
                       @JsonProperty(value = "name", required = true) String name,
                       @JsonProperty(value = "firm") String firm,
                       @JsonProperty(value = "amount") String amount) {
        this.id = id;
        this.name = name;
        this.firm = firm;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirm() {
        return firm;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", firm='" + firm + '\'' +
                ", amount='" + amount + '\'' +
                '}' + "\n";
    }
}
