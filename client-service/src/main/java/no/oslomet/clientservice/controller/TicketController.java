package no.oslomet.clientservice.controller;


import no.oslomet.clientservice.model.Ticket;
import no.oslomet.clientservice.model.User;
import no.oslomet.clientservice.service.TicketService;
import no.oslomet.clientservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @Autowired
    UserService userService;

    @PostMapping("/saveTicket")
    public String saveTicket(@ModelAttribute("ticket") Ticket ticket){
        ticketService.saveTicket(ticket);
        return "redirect:/indexAdmin";
    }

    @GetMapping("/buy/{id}")
    public String updateTicket(@PathVariable("id") long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        ticketService.updateTicket(id, user);

        model.addAttribute("Ticket", ticketService.getTicketById(id));
        model.addAttribute("Tickets", ticketService.getAllTickets());
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable("id") long id){
        ticketService.deleteTicketById(id);
        return "redirect:/indexAdmin";
    }

}
