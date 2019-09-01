package pl.rengreen.groupmixer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.rengreen.groupmixer.service.LevelService;
import pl.rengreen.groupmixer.service.TeamService;

@Controller
public class IndexController {

    private LevelService levelService;
    private TeamService teamService;

    @Autowired
    public IndexController(LevelService levelService, TeamService teamService) {
        this.levelService = levelService;
        this.teamService = teamService;
    }

    @GetMapping("/")
    public String showPersonsOnLevels(Model model) {
        model.addAttribute("levels", levelService.findAllByOrderByValueAsc());
        model.addAttribute("teamsCreated", false);
        return "index";
    }

    @GetMapping("/teams")
    public String showGeneratedTeams(Model model) {
        model.addAttribute("levels", levelService.findAllByOrderByValueAsc());

        teamService.generateTeams();
        model.addAttribute("teamsCreated", true);
        model.addAttribute("teams", teamService.findAll());

        return "index";
    }

}
