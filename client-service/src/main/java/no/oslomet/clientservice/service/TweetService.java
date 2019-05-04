package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.Tweet;
import no.oslomet.clientservice.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetService {
    String BASE_URL = "http://localhost:8082/tweet";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Tweet> getAllTweets(){
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Tweet[].class)
        ).collect(Collectors.toList());
    }

    public Tweet getTweetById(long id){
        Tweet tweet = restTemplate.getForObject(BASE_URL + "/" +id, Tweet.class);
        return tweet;
    }

    public Tweet saveTweet(Tweet tweet){
        return restTemplate.postForObject(BASE_URL, tweet, Tweet.class);
    }

    public void updateTweet(long id, User user){
        restTemplate.put(BASE_URL + "/" + id, user);
    }

    public void deleteTweetById(long id){
        restTemplate.delete(BASE_URL + "/" + id);
    }

    public List<Tweet> getMyTweets(List<Tweet> list, User user){
        List<Tweet> filteredTweetList = new ArrayList<>();

        for(Tweet t : list){
            if(t.getIdUser() == user.getId()){
                filteredTweetList.add(t);
            }
        }

        return filteredTweetList;
    }
}
