package eu.sia.meda.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;


@Data
@AllArgsConstructor @NoArgsConstructor
public class DummyEntity implements Serializable {
    @Id
    private String id;
}
