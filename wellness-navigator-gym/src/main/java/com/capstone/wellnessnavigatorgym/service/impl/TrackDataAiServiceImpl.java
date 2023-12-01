package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.entity.TrackDataAi;
import com.capstone.wellnessnavigatorgym.repository.ITrackDataAiRepository;
import com.capstone.wellnessnavigatorgym.service.ITrackDataAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackDataAiServiceImpl implements ITrackDataAiService {

    @Autowired
    private ITrackDataAiRepository trackDataAiRepository;

    public List<TrackDataAi> getAllTrackDataAi() {
        return trackDataAiRepository.findAll();
    }
}
