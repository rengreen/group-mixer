package pl.rengreen.groupmixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rengreen.groupmixer.model.Level;

import java.util.List;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

    boolean existsByValue(int value);

    Level findByValue(int levelValue);

    List<Level> findAllByOrderByValue();

    Level findByName(String levelName);
}
