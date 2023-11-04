package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.entity.Day;
import com.capstone.wellnessnavigatorgym.repository.IDayRepository;
import com.capstone.wellnessnavigatorgym.service.IDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayServiceImpl implements IDayService {

    @Autowired
    private IDayRepository dayRepository;

    @Override
    public List<Day> findAll() {
        return dayRepository.findAll();
    }
}
