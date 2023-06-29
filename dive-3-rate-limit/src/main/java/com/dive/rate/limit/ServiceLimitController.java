package com.dive.rate.limit;

import com.dive.rate.limit.service.RateLimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ServiceLimitController {

    private final RateLimitService rateLimiterService;

    @GetMapping("/{clientId}")
    public ResponseEntity<String> checkLimit(@PathVariable String clientId) {

        boolean allowed = rateLimiterService.isAllowed(clientId, 2, 10); // Allow 10 requests per minute

        if (allowed) {
            // Process the request
            return ResponseEntity.ok("Request processed successfully.");
        } else {
            // Return rate limit exceeded response
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit exceeded. Please try again later.");
        }
    }


}
