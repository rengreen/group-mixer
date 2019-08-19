package pl.rengreen.groupmixer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.rengreen.groupmixer.model.Level;
import pl.rengreen.groupmixer.model.Person;
import pl.rengreen.groupmixer.model.Team;
import pl.rengreen.groupmixer.repository.LevelRepository;
import pl.rengreen.groupmixer.service.PersonService;
import pl.rengreen.groupmixer.service.TeamService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    private LevelRepository levelService;
    private PersonService personService;
    private TeamService teamService;

    @Autowired
    public IndexController(LevelRepository levelService, PersonService personService, TeamService teamService) {
        this.levelService = levelService;
        this.personService = personService;
        this.teamService = teamService;
    }

    @GetMapping("/")
    public String showPersonList(Model model) {
        Map<Level, List<String>> personsOnLevels = preparePersonsOnLevels();
        model.addAttribute("personsOnLevels", personsOnLevels);
        model.addAttribute("teamsCreated", false);
        return "index";
    }

    @GetMapping("/teams")
    public String generateTeams(Model model) {
        Map<Level, List<String>> personsOnLevels = preparePersonsOnLevels();
        model.addAttribute("personsOnLevels", personsOnLevels);

        teamService.generateTeams();
        Map<Team, List<Person>> personsInTeams = preparePersonsInTeams();
        model.addAttribute("personsInTeams", personsInTeams);
        model.addAttribute("teamsCreated", true);
        return "index";
    }

    //LinkedHashMap is used to preserve levels order
    private Map<Level, List<String>> preparePersonsOnLevels(){
        List<Level> levels = levelService.findAllByOrderByValue();
        Map<Level, List<String>> personsOnLevels=new LinkedHashMap<>();
        for (Level level:levels) {
            List<String> personNames = personService.findPersonNamesByLevel(level);
            personsOnLevels.put(level, personNames);
        }
        return personsOnLevels;
    }

    private Map<Team, List<Person>> preparePersonsInTeams(){
        List<Team> teams = teamService.findAll();
        Map<Team, List<Person>> personsInTeams=new HashMap<>();
        for (Team team: teams) {
            List<Person> persons = personService.findPersonsByTeam(team);
            personsInTeams.put(team, persons);
        }
        return personsInTeams;
    }

}
