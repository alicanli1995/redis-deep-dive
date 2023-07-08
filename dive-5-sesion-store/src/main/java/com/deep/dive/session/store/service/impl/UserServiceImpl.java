package com.deep.dive.session.store.service.impl;

import com.deep.dive.session.store.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SessionRepository sessionRepository;

    public void login(String username) {
        Session session = sessionRepository.findById(getSessionId());
        session.setAttribute("username", username);
        sessionRepository.save(session);
    }

    @Override
    public String getSessionId() {
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }

}
