package com.deep.dive.session.store.service;

public interface UserService {
    void login(String username);

    String getSessionId();
}
