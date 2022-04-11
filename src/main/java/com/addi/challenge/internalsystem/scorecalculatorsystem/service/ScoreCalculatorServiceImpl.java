package com.addi.challenge.internalsystem.scorecalculatorsystem.service;

import com.addi.challenge.internalsystem.scorecalculatorsystem.domain.JudicialRecord;
import com.addi.challenge.internalsystem.scorecalculatorsystem.domain.Person;
import com.addi.challenge.internalsystem.scorecalculatorsystem.domain.Prospect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class ScoreCalculatorServiceImpl implements ScoreCalculatorService {

    private static final int MAXIMUM_JUDICIAL_RECORD_SCORE = 50;
    private static final int ZERO = 0;
    private static final int MAXIMUM_SCORE_PER_PERSON_ATTRIBUTE = 10;
    private static final int MINIMUM_SCORE_ALLOWED_FOR_PROSPECTION = 60;
    private static final int FIXED_RATE_TIME_TO_APPLICATION_STARTUP = 60000;

    private final AsyncRemoteCallService asyncRemoteCallService;
    private final CrmRemoteService crmRemoteService;

    public ScoreCalculatorServiceImpl(AsyncRemoteCallService asyncRemoteCallService, CrmRemoteService syncRemoteCallService) {
        this.asyncRemoteCallService = asyncRemoteCallService;
        this.crmRemoteService = syncRemoteCallService;
    }

    @Override
    @Scheduled(fixedRate = FIXED_RATE_TIME_TO_APPLICATION_STARTUP)
    public void calculateScore() throws ExecutionException, InterruptedException {

        List<Person> allPeopleFromCrm = crmRemoteService.findAll();

        List<Prospect> prospects = new ArrayList<>();

        for (Person personFromCrm : allPeopleFromCrm) {
            CompletableFuture<Person> personFromNationalRegistry = asyncRemoteCallService.findPersonByNationalIdentificationNumber(personFromCrm.getNationalIdentificationNumber());
            CompletableFuture<JudicialRecord> judicialRecord = asyncRemoteCallService.findJudicialRecordByNationalIdentificationNumber(personFromCrm.getNationalIdentificationNumber());
            CompletableFuture.allOf(personFromNationalRegistry, judicialRecord).join();

            Integer personalScore = calculatePersonScoreWhenPersonExistsInNationalRegistry(personFromCrm, personFromNationalRegistry.get());
            Integer judicialScore = calculatePersonScoreWhenPersonHasAnyJudicialRecord(personFromCrm, judicialRecord.get(), personFromNationalRegistry.get());

            Prospect prospect = evaluateProspection(personFromCrm, personalScore, judicialScore);

            if (prospect != null) {
                prospects.add(prospect);
            }

        }

        prospects.forEach(System.err::println);
    }

    private Integer calculatePersonScoreWhenPersonExistsInNationalRegistry(Person personFromCrm, Person personFromNationalRegistry) {
        Integer personScore = ZERO;

        if (personFromNationalRegistry != null) {
            if (personFromCrm.getFirstName().equals(personFromNationalRegistry.getFirstName())) {
                personScore += MAXIMUM_SCORE_PER_PERSON_ATTRIBUTE;
            }
            if (personFromCrm.getLastName().equals(personFromNationalRegistry.getLastName())) {
                personScore += MAXIMUM_SCORE_PER_PERSON_ATTRIBUTE;
            }
            if (personFromCrm.getNationalIdentificationNumber().equals(personFromNationalRegistry.getNationalIdentificationNumber())) {
                personScore += MAXIMUM_SCORE_PER_PERSON_ATTRIBUTE;
            }
            if (personFromCrm.getBirthDate().equals(personFromNationalRegistry.getBirthDate())) {
                personScore += MAXIMUM_SCORE_PER_PERSON_ATTRIBUTE;
            }
            if (personFromCrm.getEmail().equals(personFromNationalRegistry.getEmail())) {
                personScore += MAXIMUM_SCORE_PER_PERSON_ATTRIBUTE;
            }

        }

        return personScore;
    }

    private Integer calculatePersonScoreWhenPersonHasAnyJudicialRecord(Person personFromCrm, JudicialRecord judicialRecord, Person personFromNationalRegistry) {
        int judicialRecordScore = ZERO;

        if (judicialRecord == null && personFromNationalRegistry != null) {
            judicialRecordScore = MAXIMUM_JUDICIAL_RECORD_SCORE;
        } else if (judicialRecord != null && personFromNationalRegistry != null && hasAnyJudicialRecord(personFromCrm, judicialRecord)) {
            judicialRecordScore = ZERO;
        }

        return judicialRecordScore;
    }

    private boolean hasAnyJudicialRecord(Person personFromCrm, JudicialRecord judicialRecord) {
        return personFromCrm.getNationalIdentificationNumber().equals(judicialRecord.getNationalIdentificationNumber());
    }

    private Prospect evaluateProspection(Person personFromCrm, Integer personalScore, Integer judicialRecordScore) {

        int totalScore = personalScore + judicialRecordScore;

        if (totalScore >= MINIMUM_SCORE_ALLOWED_FOR_PROSPECTION) {
            return Prospect.builder()
                    .nationalIdentificationNumber(personFromCrm.getNationalIdentificationNumber())
                    .score(totalScore)
                    .build();
        }
        return null;

    }

}

