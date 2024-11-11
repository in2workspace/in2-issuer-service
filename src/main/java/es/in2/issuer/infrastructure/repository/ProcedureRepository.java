package es.in2.issuer.infrastructure.repository;

import es.in2.issuer.domain.model.entity.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRepository extends CrudRepository<Procedure, String> {
}
