package com.dive.leaderboard.service.impl;

import com.dive.leaderboard.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final HostAndPort hostAndPort = new HostAndPort("localhost", 6379);
    private final Jedis jedis = new Jedis(hostAndPort);


    @Override
    public void updateScore(String user, double score) {
        jedis.zadd("leaderboard", score, user);
    }

    @Override
    public String getLeaderboard(int start, int end) {
        Set<Tuple> leaderboard = jedis.zrevrangeWithScores("leaderboard", start, end);
        StringBuilder sb = new StringBuilder();
        sb.append("\nLeaderboard : \n");
        AtomicInteger rank = new AtomicInteger(start + 1);
        leaderboard.forEach(tuple -> {
            String user = tuple.getElement();
            double score = tuple.getScore();

            sb.append(rank.get()).append(". ").append(user).append(" - ").append(score).append("\n");
            rank.getAndIncrement();
        });

        return sb.toString();
    }
}
