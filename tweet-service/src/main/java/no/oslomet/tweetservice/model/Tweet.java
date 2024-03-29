package no.oslomet.tweetservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tweet_id")
    private long id;
    private long idParent;
    private long idUser;
    private String urlImage;
    private Timestamp timestamp;
    private String text;

    public Tweet(long idParent, long idUser, String urlImage, Timestamp timestamp, String text) {
        this.idParent = idParent;
        this.idUser = idUser;
        this.urlImage = urlImage;
        this.timestamp = timestamp;
        this.text = text;
    }
}
