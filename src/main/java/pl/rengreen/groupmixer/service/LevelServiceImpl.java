package pl.rengreen.groupmixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rengreen.groupmixer.model.Level;
import pl.rengreen.groupmixer.repository.LevelRepository;

import java.util.List;

@Service
public class LevelServiceImpl implements LevelService{

    private final LevelRepository levelRepository;

    @Autowired
    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public List<Level> findAllByOrderByValueAsc() {
        return levelRepository.findAllByOrderByValueAsc();
    }

}



