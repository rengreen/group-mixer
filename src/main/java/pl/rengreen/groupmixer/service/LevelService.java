package pl.rengreen.groupmixer.service;

import pl.rengreen.groupmixer.model.Level;

import java.util.List;

public interface LevelService {

    List<Level> findAllByOrderByValueAsc();

}
