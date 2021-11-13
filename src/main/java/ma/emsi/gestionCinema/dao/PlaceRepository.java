package ma.emsi.gestionCinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ma.emsi.gestionCinema.entities.Place;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface PlaceRepository extends JpaRepository <Place,Long>{
}
