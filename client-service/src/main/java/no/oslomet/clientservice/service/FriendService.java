package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.Friend;
import no.oslomet.clientservice.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {
    String BASE_URL = "http://localhost:8081/friend";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Friend> getAllFriends(){
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Friend[].class)
        ).collect(Collectors.toList());
    }
    @SuppressWarnings("Duplicates")
    public List<Friend> getMyFriends(List<Friend> list, User user){
        List<Friend> filteredFriendList = new ArrayList<>();

        for(Friend f : list){
            if(f.getUser_id() == user.getId()){
                filteredFriendList.add(f);
            }
        }

        return filteredFriendList;
    }

    @SuppressWarnings("Duplicates")
    public List<Friend> getMyFollowers(List<Friend> list, User user){
        List<Friend> filteredFollowerList = new ArrayList<>();

        for(Friend f : list){
            if(f.getUser_id() == user.getId()){
                filteredFollowerList.add(f);
            }
        }

        return filteredFollowerList;
    }
}
