package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    String BASE_URL = "http://localhost:8081/user";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(restTemplate.getForObject(BASE_URL + "/" + email, User.class));
        if(!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return getUserDetails(user.get());
    }

    private UserDetails getUserDetails(User user){
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole()).build();
    }

    public User getUserByEmail(String email){
        User user = restTemplate.getForObject(BASE_URL + "/" + email, User.class);
        return user;
    }

    public User registerUser(User user){
        return restTemplate.postForObject(BASE_URL, user, User.class);
    }

    public List<User> getAllUsers(){
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, User[].class)
        ).collect(Collectors.toList());
    }

    public void deleteUserById(long id){
        restTemplate.delete(BASE_URL + "/" + id);
    }

    public void updateUserPasswordById(User user){
        restTemplate.postForObject(BASE_URL, user, User[].class);
    }

}
