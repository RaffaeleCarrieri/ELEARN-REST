package it.elearn.rest.utente.repository;

import it.elearn.rest.utente.model.UtenteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<UtenteModel, UUID> {

}
