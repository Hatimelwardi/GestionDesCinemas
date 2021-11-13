package ma.emsi.gestionCinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ma.emsi.gestionCinema.entities.Seance;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface SeanceRepository extends JpaRepository <Seance,Long>{
}
