package pl.rengreen.groupmixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rengreen.groupmixer.model.Person;
import pl.rengreen.groupmixer.model.Team;
import pl.rengreen.groupmixer.repository.LevelRepository;
import pl.rengreen.groupmixer.repository.PersonRepository;
import pl.rengreen.groupmixer.repository.TeamRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

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
        Stack<Person> orderedPersons = createOrderedPersons();

        List<Team> teams = teamRepository.findAll();
        teams.forEach(team -> team.setPoints(0));

        int maxPersonInGroup = (int) Math.ceil((double) orderedPersons.size() / teams.size());

        IntStream.range(0, maxPersonInGroup)
                .forEach(k -> {
                    shuffleAndSort(teams);
                    teams.stream()
                            .filter(team -> !orderedPersons.empty())
                            .forEach(team -> addPersonToTeam(orderedPersons, team));
                });
    }

    private void shuffleAndSort(List<Team> teams) {
        Collections.shuffle(teams);
        teams.sort(Comparator.comparing(Team::getPoints));
    }

    private void addPersonToTeam(Stack<Person> orderedPersons, Team team) {
        Person person = orderedPersons.pop();
        int point = team.getPoints() + person.getLevel().getWeight();
        team.setPoints(point);
        person.setTeam(team);
        personRepository.save(person);
    }

    private Stack<Person> createOrderedPersons() {
        Stack<Person> orderedPersons = new Stack<>();
        levelRepository.findAllByOrderByValueAsc()
                .forEach(level -> shuffleAndPush(personRepository.findByLevel(level), orderedPersons));
        return orderedPersons;
    }

    private void shuffleAndPush(List<Person> persons, Stack<Person> orderedPersons) {
        Collections.shuffle(persons);
        persons.forEach(orderedPersons::push);
    }
}
