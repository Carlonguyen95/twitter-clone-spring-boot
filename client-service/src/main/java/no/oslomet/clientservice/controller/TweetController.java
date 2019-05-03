package no.oslomet.clientservice.controller;

import no.oslomet.clientservice.model.Tweet;
import no.oslomet.clientservice.model.User;
import no.oslomet.clientservice.service.TweetService;
import no.oslomet.clientservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;

@Controller
public class TweetController {
    @Autowired
    TweetService tweetService;
    @Autowired
    UserService userService;

    @PostMapping("/submitTweet")
    public String saveTweet(@ModelAttribute("tweet") Tweet tweet){
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        tweet.setTimestamp(currentTimeStamp);
        System.out.println("Timestamp: " + currentTimeStamp);
        tweetService.saveTweet(tweet);
        return "redirect:/";
    }

    @GetMapping("/buy/{id}")
    public String updateTweet(@PathVariable("id") long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        tweetService.updateTweet(id, user);

        model.addAttribute("Tweet", tweetService.getTweetById(id));
        model.addAttribute("Tweets", tweetService.getAllTweets());
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteTweet(@PathVariable("id") long id){
        tweetService.deleteTweetById(id);
        return "redirect:/indexAdmin";
    }

}