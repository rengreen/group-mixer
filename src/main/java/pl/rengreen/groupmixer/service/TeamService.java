package pl.rengreen.groupmixer.service;

import org.springframework.stereotype.Service;
import pl.rengreen.groupmixer.model.Team;

import java.util.List;

@Service
public interface TeamService {

    public List<Team> findAll();

    void generateTeams();
}
