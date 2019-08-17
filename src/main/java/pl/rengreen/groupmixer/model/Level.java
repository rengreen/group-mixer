package pl.rengreen.groupmixer.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long id;
    private int value;
    private String name;
    private int weight;
    @OneToMany(mappedBy = "level", cascade = CascadeType.PERSIST)
    private List<Person> personsOnLevel;

    public Level() {
    }

    public Level(int value, String name, int weight) {
        this.value = value;
        this.name = name;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Person> getPersonsOnLevel() {
        return personsOnLevel;
    }

    public void setPersonsOnLevel(List<Person> personsOnLevel) {
        this.personsOnLevel = personsOnLevel;
    }

}
