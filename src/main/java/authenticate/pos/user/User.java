package authenticate.pos.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(
        name = "client"
)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            name = "username"
    )
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private EncryptionAlgorithm algorithm;
    @OneToMany(
            mappedBy = "client",
            fetch = FetchType.EAGER
    )
    private List<Authority> authorities;

    public User() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EncryptionAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(EncryptionAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public List<Authority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public static enum EncryptionAlgorithm {
        BCRYPT,
        SCRYPT;

        private EncryptionAlgorithm() {
        }
    }
}

