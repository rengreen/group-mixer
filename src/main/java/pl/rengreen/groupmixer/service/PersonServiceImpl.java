package pl.rengreen.groupmixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rengreen.groupmixer.model.Person;
import pl.rengreen.groupmixer.model.Level;
import pl.rengreen.groupmixer.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<String> findPersonNamesByLevel(Level level) {
        List<String> personNames = new ArrayList<>();
        for (Person person: personRepository.findByLevel(level)) {
            personNames.add(person.getName());
        }
        return personNames;
    }

}



