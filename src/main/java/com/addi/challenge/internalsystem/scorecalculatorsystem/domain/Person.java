package com.addi.challenge.internalsystem.scorecalculatorsystem.domain;

import lombok.*;

@Getter
@EqualsAndHashCode(exclude = {"id"})
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private String nationalIdentificationNumber;
    private String birthDate;
    private String email;

}
