package com.fiveguys.robocar.service;

import com.fiveguys.robocar.entity.Match;
import com.fiveguys.robocar.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository){
        this.matchRepository = matchRepository;
    }

    public void saveMatch(Match match){
        matchRepository.save(match);
    }

    public Match findMatchById(String id){
        return matchRepository.findById(id).orElse(null);
    }

}
