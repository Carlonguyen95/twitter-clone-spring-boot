package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.Friend;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
