package pl.rengreen.groupmixer.service;

import pl.rengreen.groupmixer.model.Level;
import pl.rengreen.groupmixer.model.Person;
import pl.rengreen.groupmixer.model.Team;

import java.util.List;

public interface PersonService {

    List<String> findPersonNamesByLevel(Level level);

    List<Person> findPersonsByTeam(Team team);
}
