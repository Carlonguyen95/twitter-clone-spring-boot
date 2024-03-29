package no.oslomet.clientservice.controller;


import no.oslomet.clientservice.model.Friend;
import no.oslomet.clientservice.model.Tweet;
import no.oslomet.clientservice.model.User;
import no.oslomet.clientservice.service.FriendService;
import no.oslomet.clientservice.service.TweetService;
import no.oslomet.clientservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    FriendService friendService;
    @Autowired
    TweetService tweetService;
    @Autowired
    UserService userService;

    private List<Tweet> tweetList = new ArrayList<>();
    private List<Friend> friendList = new ArrayList<>();

    @SuppressWarnings("Duplicates")
    @GetMapping("/")
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());

        tweetList = tweetService.getAllTweets();
        friendList = friendService.getAllFriends();

        List<Friend> filteredFriendList = new ArrayList<>();
        List<Friend> filteredFollowerList = new ArrayList<>();
        List<Tweet> filteredTweetList = new ArrayList<>();

        if(user != null){
            filteredFriendList = friendService.getMyFriends(friendList, user);
            filteredFollowerList = friendService.getMyFollowers(friendList, user);
            filteredTweetList = tweetService.getMyTweets(tweetList, user);

            model.addAttribute("friendList", filteredFriendList);
            model.addAttribute("followerList", filteredFollowerList);
            model.addAttribute("tweetCountList", filteredTweetList);
            model.addAttribute("user", user);
        }

        model.addAttribute("tweetList", tweetList);
        return "/home";
    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/friendstweets")
    public String loadFriendsTweets(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());

        tweetList = tweetService.getAllTweets();
        friendList = friendService.getAllFriends();

        List<Friend> filteredFriendList = new ArrayList<>();
        List<Friend> filteredFollowerList = new ArrayList<>();
        List<Tweet> filteredTweetList = new ArrayList<>();
        List<Tweet> filteredMyFriendsTweetsList = new ArrayList<>();

        if(user != null){
            filteredFriendList = friendService.getMyFriends(friendList, user);
            filteredFollowerList = friendService.getMyFollowers(friendList, user);
            filteredTweetList = tweetService.getMyTweets(tweetList, user);
            filteredMyFriendsTweetsList = tweetService.getMyFriendsTweets(filteredFriendList, tweetList);
        }

        model.addAttribute("friendList", filteredFriendList);
        model.addAttribute("followerList", filteredFollowerList);
        model.addAttribute("tweetCountList", filteredTweetList);
        model.addAttribute("tweetList", filteredMyFriendsTweetsList);
        return "/friends";
    }

    @GetMapping("/friendlist")
    public String getFriendList(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());



        return "/friendlist";
    }

//    @GetMapping("/indexAdmin")
//    public String indexAdmin(Model model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.getUserByEmail(auth.getName());
//
//        model.addAttribute("user", user);
//
//        ticketList.clear();
//        ticketList = ticketService.getAllTickets();
//        model.addAttribute("ticketList", ticketList);
//
//        if(user.getRole().equals("USER")){
//            return "redirect:/home";
//        }
//
//        return "indexAdmin";
//    }

}
