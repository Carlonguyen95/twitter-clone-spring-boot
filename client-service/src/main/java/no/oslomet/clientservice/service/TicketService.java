package no.oslomet.clientservice.service;


import no.oslomet.clientservice.model.Ticket;
import no.oslomet.clientservice.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    String BASE_URL = "http://localhost:8090/tickets";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Ticket> getAllTickets(){
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Ticket[].class)
        ).collect(Collectors.toList());
    }

    public Ticket getTicketById(long id){
        Ticket ticket = restTemplate.getForObject(BASE_URL + "/" +id, Ticket.class);
        return ticket;
    }

    public Ticket saveTicket(Ticket ticket){
        return restTemplate.postForObject(BASE_URL, ticket, Ticket.class);
    }

    public void updateTicket(long id, User user){
        restTemplate.put(BASE_URL + "/" + id, user);
    }

    public void deleteTicketById(long id){
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
