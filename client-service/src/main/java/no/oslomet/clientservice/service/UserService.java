package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

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

}
