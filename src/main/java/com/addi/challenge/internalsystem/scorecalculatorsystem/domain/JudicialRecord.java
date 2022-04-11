package com.addi.challenge.internalsystem.scorecalculatorsystem.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class JudicialRecord {
    private Long id;
    private String nationalIdentificationNumber;
}
