package no.oslomet.clientservice.model;

import lombok.Data;

@Data
public class User {
    private long id;
    private String screenName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    public User(){
    }

    public User(String screenName, String firstName, String lastName, String email, String password) {
        this.screenName = screenName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
