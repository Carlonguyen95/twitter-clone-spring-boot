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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TweetController {
    @Autowired
    TweetService tweetService;
    @Autowired
    UserService userService;
    @Autowired
    FriendService friendService;

    private List<Tweet> tweetList = new ArrayList<>();
    private List<Friend> friendList = new ArrayList<>();

    @PostMapping("/submitTweet")
    public String saveTweet(@ModelAttribute("tweet") Tweet tweet){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());

        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        tweet.setTimestamp(currentTimeStamp);
        tweet.setIdUser(user.getId());

        tweetService.saveTweet(tweet);
        return "redirect:/";
    }

    @SuppressWarnings("Duplicates")
    @PostMapping("/reply/{id}")
    public String replyToTweet(@PathVariable("id") long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());

        if(user == null){
            return "/login";
        }
//        tweetList = tweetService.getAllTweets();
//        friendList = friendService.getAllFriends();
//
//        List<Friend> filteredFriendList = new ArrayList<>();
//        List<Friend> filteredFollowerList = new ArrayList<>();
//        List<Tweet> filteredTweetList = new ArrayList<>();
//
//        if(user != null){
//            filteredFriendList = friendService.getMyFriends(friendList, user);
//            filteredFollowerList = friendService.getMyFollowers(friendList, user);
//            filteredTweetList = tweetService.getMyTweets(tweetList, user);
//
//            model.addAttribute("friendList", filteredFriendList);
//            model.addAttribute("followerList", filteredFollowerList);
//            model.addAttribute("tweetCountList", filteredTweetList);
//            model.addAttribute("user", user);
//        }
//
//        model.addAttribute("tweetList", tweetList);
        return "redirect:/";
    }

}