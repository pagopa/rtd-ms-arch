package eu.sia.meda.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    @Column(name = "INSERT_DATE")
    private LocalDateTime insertDate;

    @Column(name = "INSERT_USER")
    private String insertUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "ENABLED")
    private boolean enabled = true;
}
