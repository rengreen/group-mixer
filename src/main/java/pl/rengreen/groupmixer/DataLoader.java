package pl.rengreen.groupmixer;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import pl.rengreen.groupmixer.model.Level;
import pl.rengreen.groupmixer.repository.LevelRepository;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Service
public class DataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final LevelRepository levelRepository;

    @Autowired
    public DataLoader(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
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
            levelRepository.save(level);
        }

        logger.info("Levels loaded from " + fileName + " file");
        levelRepository.findAll().stream()
                .map(x -> "saved level " + x.getValue() + ": " + x.getName() + ", weight: " + x.getWeight())
                .forEach(logger::info);


    }
}
