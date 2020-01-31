package eu.sia.meda.connector.jpa.model;

import eu.sia.meda.connector.jpa.utils.JpaConverterJson;
import eu.sia.meda.domain.model.be4fe.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TESTJSONCONV")
public class JsonConverterEntity {

    @Id
    private int id;

    @Column(name = "OBJ")
    @Convert(converter = JpaConverterJson.class)
    private User obj;
}
