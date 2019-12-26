package eu.sia.meda.connector.hbase.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@ToString
@EqualsAndHashCode
public class Contact implements Serializable {
    @JsonProperty
    String name;
    @JsonProperty
    Integer number;

    public Contact() {

    }

    public Contact(String name, Integer number) {
        this.name = name;
        this.number = number;
    }
}
