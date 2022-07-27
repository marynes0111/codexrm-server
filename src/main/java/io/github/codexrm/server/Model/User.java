package io.github.codexrm.server.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
@Table(name = "\"User\"")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "select u from User u"),
        @NamedQuery(name = "User.findById", query = "select u from User u where u.id = :id"),
        @NamedQuery(name = "User.existsById", query = "select (count(u) > 0) from User u where u.id = :id"),
        @NamedQuery(name = "User.deleteById", query = "delete from User u where u.id = :id"),
        @NamedQuery(name = "User.updateById", query = "update User u set u. = : where u.id = :id")
})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "usercodex", nullable = false)
    private String userCodex;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @OneToMany(mappedBy = "userId")
    private ArrayList<Reference> referencesList = new ArrayList<>();

    public User() {}

    public User (Integer id, String userCodex, String password) {
        this.id = id;
        this.userCodex = userCodex;
        this.password = password;
        this.referencesList = new ArrayList<>();
    }

    public ArrayList<Reference> getReferencesList() {
        return referencesList;
    }

    public void setReferencesList(ArrayList<Reference> references) {
        this.referencesList = references;
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

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userCodex='" + userCodex + '\'' +
                ", password='" + password + '\'' +
                ", referencesList=" + referencesList +
                '}';
    }
}