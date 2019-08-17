package pl.rengreen.groupmixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rengreen.groupmixer.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
