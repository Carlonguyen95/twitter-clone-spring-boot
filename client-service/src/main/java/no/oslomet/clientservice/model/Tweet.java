package no.oslomet.clientservice.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Tweet {
    private long id;
    private long idParent;
    private long idUser;
    private String urlImage;
    private Timestamp timestamp;
    private String text;
}

