package com.dive.leaderboard;

import com.dive.leaderboard.service.LeaderBoardService;
import com.dive.leaderboard.service.impl.LeaderBoardServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LeaderboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaderboardApplication.class, args);
        Thread t1 = new Thread(new RunnableImpl());
        t1.start();
    }


    private static class RunnableImpl implements Runnable {

        public void run()
        {
            final LeaderBoardService leaderBoardService = new LeaderBoardServiceImpl();
            leaderBoardService.updateScore("user1", 200);
            leaderBoardService.updateScore("user2", 100);
            leaderBoardService.updateScore("user3", 300);
            log.info(leaderBoardService.getLeaderboard(0, 2));
        }
    }
}
