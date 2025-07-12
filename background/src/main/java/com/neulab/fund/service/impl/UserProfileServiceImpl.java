package com.neulab.fund.service.impl;

import com.neulab.fund.entity.UserProfile;
import com.neulab.fund.repository.UserProfileRepository;
import com.neulab.fund.service.UserProfileService;
import com.neulab.fund.vo.UserProfileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfileVO getUserProfile(Long userId) {
        UserProfileVO vo = new UserProfileVO();
        userProfileRepository.findByUserId(userId).ifPresent(profile -> {
            vo.setUserId(profile.getUserId());
            vo.setProfileText(profile.getProfileText());
            vo.setTags(profile.getTags());
        });
        return vo;
    }
} 