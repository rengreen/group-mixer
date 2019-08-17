package pl.rengreen.groupmixer.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Person> personsInTeam;
    private int points;

    public Team() {
    }
    public Team(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersonsInTeam() {
        return personsInTeam;
    }

    public void setPersonsInTeam(List<Person> personsInTeam) {
        this.personsInTeam = personsInTeam;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
}
