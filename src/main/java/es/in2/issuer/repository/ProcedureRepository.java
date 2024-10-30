package es.in2.issuer.repository;

import es.in2.issuer.model.entity.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRepository extends CrudRepository<Procedure, String> {
}
