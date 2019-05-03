package no.oslomet.clientservice.model;

import lombok.Data;

@Data
public class Friend {
    private long id;
    private long user_id;
    private long user_id_following;


}
