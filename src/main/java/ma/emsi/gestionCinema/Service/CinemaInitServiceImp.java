package ma.emsi.gestionCinema.Service;


import ma.emsi.gestionCinema.dao.*;
import ma.emsi.gestionCinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImp implements IcinemaInitService{
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ProjectionFilmRepository projectionFilmRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void initVilles() {
        Stream.of("Casablanca","Bouznika","Sale","Oujda","Marrakech").forEach(nameVille ->{
            Ville ville=new Ville();
            ville.setName(nameVille);
            villeRepository.save(ville);
        });
    }
    @Override
    public void initCategories() {
        Stream.of("Dramatique","romantique","Action").forEach(c->{
            Categorie categorie=new Categorie();
            categorie.setName(c);
            categorieRepository.save(categorie);
        });
    }
    @Override
    public void initCinemas() {
    villeRepository.findAll().forEach(ville->{
        Stream.of("Megarama","Atlas","Renaissance","Daouliz","Imax").forEach(nameCinema->{
            Cinema cinema= new Cinema();
            cinema.setName(nameCinema);
            cinema.setNombreSalles(3+(int) Math.random()*7);
            cinema.setVille(ville);
            cinemaRepository.save(cinema);
        });
    });
    }
    @Override
    public void initFilms() {
        double []duree =new double[]{1,1.5,2,2.5,3};
        List<Categorie> categories=categorieRepository.findAll();
        Stream.of("Je suis Une Legende","Jocker","Le Parrain","The Suicide Squad","Titanic").forEach(titre->{
            Film film = new Film();
            film.setTitre(titre);
            film.setDuree(duree[new Random().nextInt(duree.length)]);
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            film.setPhoto(titre.replace(" ","")+".jpg");
            filmRepository.save(film);
        });
    }
    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for(int i=0;i<salle.getNombrePlace();i++){
                Place place=new Place();
                place.setNumero(i+1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });
    }
    @Override
    public void initProjectionFilms() {
        double [] prix =new double[]{30,50,60,70,90,100};
        List<Film> films=filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                  int index=new Random().nextInt(films.size());
                  Film film= films.get(index);
                  seanceRepository.findAll().forEach(seance-> {
                      ProjectionFilm projectionFilm=new ProjectionFilm();
                      projectionFilm.setDateProjection(new Date());
                      projectionFilm.setFilm(film);
                      projectionFilm.setPrix(prix[new Random().nextInt(prix.length)]);
                      projectionFilm.setSalle(salle);
                      projectionFilm.setSeance(seance);
                      projectionFilmRepository.save(projectionFilm);
                  });
                });
            });
        });
    }
    @Override
    public void initSalles() {
     cinemaRepository.findAll().forEach(cinema -> {
         for(int i=0;i<cinema.getNombreSalles();i++){
             Salle salle= new Salle();
             salle.setName("salle"+(i+1));
             salle.setCinema(cinema);
             salle.setNombrePlace(15+(int)(Math.random()*20));
             salleRepository.save(salle);
         }
     });
    }
    @Override
    public void initSeances() {
        DateFormat dateFormat=new SimpleDateFormat("HH:mm");
    Stream.of("12:00","15:00","17:00","20:00").forEach(s ->{
       Seance seance=new Seance();
        try {
            seance.setHeureDebut(dateFormat.parse(s));
            seanceRepository.save(seance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    });
    }
    @Override
    public void initTickets() {
        projectionFilmRepository.findAll().forEach(projection -> {
            projection.getSalle().getPlaces().forEach(place -> {
                Ticket ticket=new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projection.getPrix());
                ticket.setProjectionFilm(projection);
                ticket.setReserve(false);
                ticketRepository.save(ticket);
            });
        });
    }
}
