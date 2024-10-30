package es.in2.issuer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Procedure {

    @Id
    private UUID procedureId;

    // todo: implement the rest of the class

}
