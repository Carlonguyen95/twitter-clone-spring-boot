package no.oslomet.clientservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    public User(){

    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    private List<Ticket> tickets = new ArrayList<>();
}
