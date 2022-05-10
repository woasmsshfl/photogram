package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void 구독하기(Integer fromUserId, Integer toUserId) {
        subscribeRepository.mSubscribe(fromUserId, toUserId);
    }

    @Transactional
    public void 구독취소하기(Integer fromUserId, Integer toUserId) {
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
    
}
