package ma.emsi.gestionCinema.web;
import lombok.Data;
import ma.emsi.gestionCinema.dao.FilmRepository;
import ma.emsi.gestionCinema.dao.TicketRepository;
import ma.emsi.gestionCinema.entities.Film;
import ma.emsi.gestionCinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin("*")
public class RestController {
    @Autowired
   private FilmRepository filmRepository ;
    @Autowired
    private TicketRepository ticketRepository;
   /* @GetMapping("/listeFilms")
    public List<Film> getFilm(){
       return filmRepository.findAll();
    }*/
    @GetMapping(value = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte [] image(@PathVariable(name = "id")Long id) throws IOException {
        Film f=filmRepository.findById(id).get();
        String photoName=f.getPhoto();
        File file= new File(System.getProperty("user.home")+"/cinemaapp/images/"+photoName);
        Path path= Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
        List<Ticket> ticketsList=new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket->{
            Ticket ticket =ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setCodePayement(ticketForm.getCodePayement());
            ticket.setReserve(true);
            ticketRepository.save(ticket);
            ticketsList.add(ticket);
        });
        return ticketsList;
    }
}
@Data
class TicketForm{
    private String nomClient;
    private int codePayement;
    private List<Long> tickets=new ArrayList<>();
}