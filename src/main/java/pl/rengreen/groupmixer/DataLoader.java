package pl.rengreen.groupmixer;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import pl.rengreen.groupmixer.model.Level;
import pl.rengreen.groupmixer.model.Person;
import pl.rengreen.groupmixer.model.Team;
import pl.rengreen.groupmixer.repository.LevelRepository;
import pl.rengreen.groupmixer.repository.PersonRepository;
import pl.rengreen.groupmixer.repository.TeamRepository;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Service
public class DataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final LevelRepository levelRepository;
    private final PersonRepository personRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public DataLoader(LevelRepository levelRepository, PersonRepository personRepository, TeamRepository teamRepository) {
        this.levelRepository = levelRepository;
        this.personRepository = personRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... strings) throws IOException, ParseException {

        String fileName;
        JSONArray jsonArray;
        JSONParser parser = new JSONParser();

        //load levels from file
        fileName = "src/main/resources/static/json/levels.json";
        jsonArray = (JSONArray) parser.parse(new FileReader(fileName));

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            int value = (int) (long) jsonObject.get("value");
            String name = (String) jsonObject.get("name");
            int weight = (int) (long) jsonObject.get("weight");
            Level level = new Level(value, name, weight);
            if (!levelRepository.existsByValue(value)) levelRepository.save(level);
        }

        logger.info("Levels loaded from " + fileName + " file");
        levelRepository.findAll().stream()
                .map(x -> "saved level " + x.getValue() + ": " + x.getName() + ", weight: " + x.getWeight())
                .forEach(logger::info);

        //load persons from file
        fileName = "src/main/resources/static/json/persons.json";
        jsonArray = (JSONArray) parser.parse(new FileReader(fileName));

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            String name = (String) jsonObject.get("name");
            int levelValue = (int) (long) jsonObject.get("level");
            Level level = levelRepository.findByValue(levelValue);
            Person person = new Person(name, level);
            if (!personRepository.existsByName(name)) personRepository.save(person);
        }

        logger.info("Person data loaded from " + fileName + " file");
        personRepository.findAll().stream()
                .map(p -> "saved person: " + p.getName() + ", level: " + p.getLevel().getName())
                .forEach(logger::info);

        //load teams from file
        fileName = "src/main/resources/static/json/teams.json";
        jsonArray = (JSONArray) parser.parse(new FileReader(fileName));

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            String name = (String) jsonObject.get("name");
            if (!teamRepository.existsByName(name)) teamRepository.save(new Team(name));
        }

        logger.info("Empty teams loaded from " + fileName + " file");
        teamRepository.findAll().stream()
                .map(t -> "saved team: " + t.getName())
                .forEach(logger::info);
    }
}