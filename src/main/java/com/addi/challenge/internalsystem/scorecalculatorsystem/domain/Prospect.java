package com.addi.challenge.internalsystem.scorecalculatorsystem.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Builder
@Entity(name = "PROSPECT")
public class Prospect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @ToString.Include
    private String nationalIdentificationNumber;
    @ToString.Include
    private Integer score;
}
