package pl.rengreen.groupmixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rengreen.groupmixer.model.Level;
import pl.rengreen.groupmixer.model.Person;
import pl.rengreen.groupmixer.model.Team;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByLevel(Level level);
}
