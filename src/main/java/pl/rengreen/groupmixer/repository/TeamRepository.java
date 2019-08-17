package pl.rengreen.groupmixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rengreen.groupmixer.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
