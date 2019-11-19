package pl.rengreen.groupmixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rengreen.groupmixer.model.Level;
import pl.rengreen.groupmixer.model.Person;
import pl.rengreen.groupmixer.model.Team;
import pl.rengreen.groupmixer.repository.LevelRepository;
import pl.rengreen.groupmixer.repository.PersonRepository;
import pl.rengreen.groupmixer.repository.TeamRepository;

import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private PersonRepository personRepository;
    private LevelRepository levelRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PersonRepository personRepository, LevelRepository levelRepository) {
        this.teamRepository = teamRepository;
        this.personRepository = personRepository;
        this.levelRepository = levelRepository;
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public void generateTeams() {
        List<Person> allPersons = createOrderedPersonList();

        List<Team> teams = teamRepository.findAll();
        for (Team team : teams) {
            team.setPoints(0);
        }

        int position = 0;
        int maxPersonInGroup=(int) Math.ceil((double) allPersons.size()/teams.size());

        for (int k = 0; k < maxPersonInGroup; k++) {
            Collections.shuffle(teams);
            teams.sort(Comparator.comparing(Team::getPoints));

            for (Team team : teams) {
                if (position<allPersons.size()) {
                    Person person = allPersons.get(position);
                    int point = team.getPoints() + person.getLevel().getWeight();
                    team.setPoints(point);
                    person.setTeam(team);
                    personRepository.save(person);
                    position++;
                }
            }
        }
    }

    private List<Person> createOrderedPersonList(){
        List<Person> orderedPersons = new ArrayList<>();

        for (Level level : levelRepository.findAllByOrderByValueDesc()) {
            List<Person> persons = personRepository.findByLevel(level);
            Collections.shuffle(persons);
            orderedPersons.addAll(persons);
        }
        return orderedPersons;
    }
}
