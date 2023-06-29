package com.dive.rate.limit.service;

public interface RateLimitService {
    boolean isAllowed(String key, int maxRequests, int durationInSeconds);
}
