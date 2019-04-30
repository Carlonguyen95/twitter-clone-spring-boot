package no.oslomet.clientservice.controller;


import no.oslomet.clientservice.model.Ticket;
import no.oslomet.clientservice.model.Tweet;
import no.oslomet.clientservice.model.User;
import no.oslomet.clientservice.service.TicketService;
import no.oslomet.clientservice.service.TweetService;
import no.oslomet.clientservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    TicketService ticketService;
    @Autowired
    TweetService tweetService;
    @Autowired
    UserService userService;

    private List<Tweet> tweetList = new ArrayList<>();


    @GetMapping({"/", "/login"})
    public String home(){
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model ){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        List<Tweet> filteredList = new ArrayList<>();

        tweetList.clear();
        tweetList = tweetService.getAllTweets();

        for(Tweet t : tweetList){
            if(t.getIdParent() == user.getId()){
                filteredList.add(t);
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("tweetList", filteredList);

        return "index";
    }

    @GetMapping("/indexAdmin")
    public String indexAdmin(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());

        model.addAttribute("user", user);

//        ticketList.clear();
//        ticketList = ticketService.getAllTickets();
//        model.addAttribute("ticketList", ticketList);
//
//        if(user.getRole().equals("USER")){
//            return "redirect:/home";
//        }

        return "indexAdmin";
    }

    @GetMapping("/myTickets")
    public String myTickets(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());

        model.addAttribute("user", user);

        List<Ticket> filteredList = new ArrayList<>();
        List<Ticket> myTicketList = ticketService.getAllTickets();

        for(Ticket t : myTicketList){
            if(t.getUser() != null){
                if(t.getUser().getEmail().equals(user.getEmail())){
                    filteredList.add(t);
                }
            }
        }

        model.addAttribute("myTicketList", filteredList);
        return "myTickets";
    }

    @GetMapping("/tweets")
    public String tweets(){
        return "/home";
    }
}
