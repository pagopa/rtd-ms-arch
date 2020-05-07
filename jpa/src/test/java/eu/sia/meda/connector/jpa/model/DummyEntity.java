package eu.sia.meda.connector.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.sia.meda.connector.jpa.utils.JpaConverterJson;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "DUMMYTABLE")
public class DummyEntity implements Serializable {

    @Id
    private int id;

    @Column(name = "OBJ")
    @Convert(converter = JpaConverterJson.class)
    private Object obj;

    private LocalDate localDate;
}
