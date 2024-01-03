package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.entity.UserDataAi;
import com.capstone.wellnessnavigatorgym.repository.IUserDataAiRepository;
import com.capstone.wellnessnavigatorgym.service.IUserDataAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataAiServiceImpl implements IUserDataAiService {

    @Autowired
    private IUserDataAiRepository userDataAiRepository;

    @Override
    public void saveUserDataAi(UserDataAi userDataAi) {
        if (userDataAi != null) {
            userDataAiRepository.save(userDataAi);
        } else {
            throw new IllegalArgumentException("userDataAi cannot be null");
        }
    }
}
