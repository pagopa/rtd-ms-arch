package eu.sia.meda.connector.jpa.model;

import eu.sia.meda.connector.jpa.utils.JpaConverterJson;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TESTJSONCONV")
public class JsonConverterEntity {

    @Id
    private int id;

    @Column(name = "OBJ")
    @Convert(converter = JpaConverterJson.class)
    private List<String> obj;
}
