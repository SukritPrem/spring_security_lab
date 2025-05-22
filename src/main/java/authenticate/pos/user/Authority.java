package authenticate.pos.user;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Authority {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    private String name;
    @JoinColumn(
            name = "client"
    )
    @ManyToOne
    private User client;

    public Authority() {
    }

    public String getName() {
        return this.name;
    }
}

