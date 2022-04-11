package com.addi.challenge.internalsystem.scorecalculatorsystem.service;

import java.util.concurrent.ExecutionException;

public interface ScoreCalculatorService {
    void calculateScore() throws ExecutionException, InterruptedException;
}
