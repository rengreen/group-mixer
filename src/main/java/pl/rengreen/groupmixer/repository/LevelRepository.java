package pl.rengreen.groupmixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rengreen.groupmixer.model.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

}
