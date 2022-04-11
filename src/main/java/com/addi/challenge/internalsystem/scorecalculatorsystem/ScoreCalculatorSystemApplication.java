package com.addi.challenge.internalsystem.scorecalculatorsystem;

import com.addi.challenge.internalsystem.scorecalculatorsystem.service.ScoreCalculatorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class ScoreCalculatorSystemApplication implements CommandLineRunner {

    private final ScoreCalculatorService scoreCalculatorService;

    public ScoreCalculatorSystemApplication(ScoreCalculatorService scoreCalculatorService) {
        this.scoreCalculatorService = scoreCalculatorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ScoreCalculatorSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        scoreCalculatorService.calculateScore();

    }

}
