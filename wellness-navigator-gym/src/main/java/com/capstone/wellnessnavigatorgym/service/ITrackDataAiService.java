package com.capstone.wellnessnavigatorgym.service;

import com.capstone.wellnessnavigatorgym.entity.TrackDataAi;

import java.util.List;

public interface ITrackDataAiService {
    List<TrackDataAi> getAllTrackDataAi();

    void saveTrackDataAi(TrackDataAi trackDataAi);

    TrackDataAi findTrackDataAiById(Integer id);
}
