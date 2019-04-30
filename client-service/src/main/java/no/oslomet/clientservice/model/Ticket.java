package no.oslomet.clientservice.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Ticket {
    private long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String film;
    private String cinema;

    private User user;
}
