package io.github.codexrm.server.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "usercodex", nullable = false)
    private String userCodex;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    private List<Reference> referenceList = new ArrayList<>();

    public User() {}

    public User(String userCodex, String password) {
        this.userCodex = userCodex;
        this.password = password;
    }

    public List<Reference> getReferenceList() {
        return referenceList;
    }

    public void setReferenceList(List<Reference> referenceList) {
        this.referenceList = referenceList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserCodex() {
        return userCodex;
    }

    public void setUserCodex(String userCodex) {
        this.userCodex = userCodex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {this.id = id;}
}