package com.dive.leaderboard.controller;


import com.dive.leaderboard.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/leaderboard")
public class LeaderBoardController {

    private final LeaderBoardService leaderBoardService;

    @PostMapping("/{user}/{score}")
    public void updateScore(@PathVariable String user, @PathVariable double score) {
        leaderBoardService.updateScore(user, score);
    }

    @GetMapping("/{start}/{end}")
    public String getLeaderboard(@PathVariable int start, @PathVariable int end) {
        return leaderBoardService.getLeaderboard(start, end);
    }

}
