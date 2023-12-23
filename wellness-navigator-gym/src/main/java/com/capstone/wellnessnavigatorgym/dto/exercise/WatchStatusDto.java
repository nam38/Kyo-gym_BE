package com.capstone.wellnessnavigatorgym.dto.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WatchStatusDto {
    private Boolean isWatched;
    private Boolean isVideoFinished;
    private Integer views;
}
