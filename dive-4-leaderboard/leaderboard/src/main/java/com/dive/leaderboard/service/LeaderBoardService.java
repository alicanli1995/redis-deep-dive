package com.dive.leaderboard.service;

public interface LeaderBoardService {
    void updateScore(String user, double score);

    String getLeaderboard(int start, int end);
}
