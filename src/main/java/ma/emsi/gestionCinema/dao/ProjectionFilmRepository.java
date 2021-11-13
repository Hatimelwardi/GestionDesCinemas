package ma.emsi.gestionCinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ma.emsi.gestionCinema.entities.ProjectionFilm;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface ProjectionFilmRepository extends JpaRepository <ProjectionFilm,Long>{
}
