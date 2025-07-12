package com.neulab.fund.service.impl;

import com.neulab.fund.entity.UserSegment;
import com.neulab.fund.repository.UserSegmentRepository;
import com.neulab.fund.service.UserSegmentService;
import com.neulab.fund.vo.UserSegmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

@Service
public class UserSegmentServiceImpl implements UserSegmentService {
    @Autowired
    private UserSegmentRepository userSegmentRepository;

    @Override
    public List<UserSegmentVO> listUserSegments() {
        return userSegmentRepository.findAll().stream().map(segment -> {
            UserSegmentVO vo = new UserSegmentVO();
            vo.setSegmentName(segment.getSegmentName());
            vo.setRule(segment.getRule());
            // userIds字段为JSON数组字符串，转为List<Long>
            if (segment.getUserIds() != null && !segment.getUserIds().isEmpty()) {
                String json = segment.getUserIds().replace("[", "").replace("]", "");
                if (!json.trim().isEmpty()) {
                    vo.setUserIds(Arrays.stream(json.split(",")).map(String::trim).map(Long::valueOf).collect(Collectors.toList()));
                } else {
                    vo.setUserIds(List.of());
                }
            } else {
                vo.setUserIds(List.of());
            }
            return vo;
        }).collect(Collectors.toList());
    }
} 