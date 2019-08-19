package pl.rengreen.groupmixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        System.out.println("start");
        List<Person> starterki = personRepository.findByLevel(levelRepository.findByName("Starterki"));
        List<Person> juniorki = personRepository.findByLevel(levelRepository.findByName("Juniorki"));
        List<Person> szeregowe = personRepository.findByLevel(levelRepository.findByName("Szeregowe"));
        List<Person> komandoski = personRepository.findByLevel(levelRepository.findByName("Komandoski"));

        System.out.println("starterki: " + starterki.toString());
        System.out.println("juniorki: " + juniorki.toString());
        System.out.println("szeregowe: " + szeregowe.toString());
        System.out.println("komandoski: " + komandoski.toString());

        Collections.shuffle(starterki);
        Collections.shuffle(juniorki);
        Collections.shuffle(szeregowe);
        Collections.shuffle(komandoski);

        List<Person> allMembers = new ArrayList<>();
        allMembers.addAll(komandoski);
        allMembers.addAll(szeregowe);
        allMembers.addAll(juniorki);
        allMembers.addAll(starterki);
        System.out.println("all members: " + allMembers.toString());

        teamRepository.findByName("Gamerki").setPoints(0);
        teamRepository.findByName("Heroski").setPoints(0);
        teamRepository.findByName("Kosmonautki").setPoints(0);
        teamRepository.findByName("Żony Hollywood").setPoints(0);

        List<Team> teams = new ArrayList<>(Arrays.asList(
                teamRepository.findByName("Gamerki"),
                teamRepository.findByName("Heroski"),
                teamRepository.findByName("Kosmonautki"),
                teamRepository.findByName("Żony Hollywood")
        ));

        int position = 0;
        for (int k = 0; k < 5; k++) {
            Collections.shuffle(teams);
            teams.sort(Comparator.comparing(Team::getPoints));

            for (Team team : teams) {
                Person person = allMembers.get(position);
                int point = team.getPoints() + person.getLevel().getWeight();
                team.setPoints(point);
                person.setTeam(team);
                personRepository.save(person);
                position++;
            }
        }
    }
}
