package pl.rengreen.groupmixer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.rengreen.groupmixer.model.Level;
import pl.rengreen.groupmixer.repository.LevelRepository;
import pl.rengreen.groupmixer.service.PersonService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    private final LevelRepository levelService;
    private final PersonService personService;

    @Autowired
    public IndexController(LevelRepository levelService, PersonService personService) {
        this.levelService = levelService;
        this.personService = personService;
    }

    @GetMapping("/")
    public String showPersonList(Model model) {
        Map<Level, List<String>> personsOnLevels = preparePersonsOnLevels();
        model.addAttribute("personsOnLevels", personsOnLevels);
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

}
