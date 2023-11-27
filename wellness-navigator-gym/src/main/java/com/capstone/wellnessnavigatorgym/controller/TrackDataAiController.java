package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.entity.TrackDataAi;
import com.capstone.wellnessnavigatorgym.service.ITrackDataAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/track-data-ai")
@CrossOrigin(origins = "http://localhost:3000")
public class TrackDataAiController {

    @Autowired
    private ITrackDataAiService trackDataAiService;

    @GetMapping("")
    public ResponseEntity<List<TrackDataAi>> getAllTrackDataAi() {
        List<TrackDataAi> trackDataAis = this.trackDataAiService.getAllTrackDataAi();
        if (trackDataAis.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(trackDataAis, HttpStatus.OK);
    }
}
