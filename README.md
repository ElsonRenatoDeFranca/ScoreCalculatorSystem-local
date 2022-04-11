# ScoreCalculator System

## How the score was calculated

The maximum score a person can get is 100. In order to achieve this possible value, the score was calculated according two main criteria: the existence of any judicial record and the person is found at national registry identification system. We have two intermediaries scores that will be added up to each other in order to get the main score. In case the main score is equal or greater than 60, the application will consider the person to be eligible to be a prospect.

## How intermediaries scores were calculated

1- In case the person has any judicial record it its intermediary score will be 0 (zero);
2- In case the person found at CRM matches the person found at National Registry Identification System then its score will be 50 (fifty). Each person attribute will be also evaluated and will have a score associated to it. Each of the following attributes: firstName, lastName, national identfication number, email will have a score associated to them. This score will be 10. This will prevent people with outdated emails or typo in their firstNames and lastNames won´t be prevented from becoming a prospect.

## Items that could be improved

In the future we might add different score values to those people who has any judicial records associated to them and allow them to be prospected. The idea is to assign different values to those who ever had any  judicial records in the past but the process is closed. For this case we would assign a score 30. Another subcategory would be those ones who still have judicial records which are in progress. In this case, the score will be 0 (zero).

## Technical overview

The application consists of main batch application called ScoreCalculator which runs every minute by checking three other microservices: CRM, JudicialRecords, NationalRegistryDataBase in order to validate which person could be transformed into prospect.

List of services that need to be cloned:

1- https://github.com/ElsonRenatoDeFranca/ScoreCalculatorSystem.git
2- https://github.com/ElsonRenatoDeFranca/JudicialRecordsManager.git
3- https://github.com/ElsonRenatoDeFranca/NationalRegistryIdentificationService.git
4- https://github.com/ElsonRenatoDeFranca/CrmSystem.git

NOTE: Since they are not deployed in the cloud yet and we are using in memory database, we need to start them locally and store all information on CRM, JudicialRecords and NationalRegistry APIs before being fully operational. The user can interact with those microservices by using postman:

## NationalRegistry API

GET: http://localhost:8092/api/nationalregistry

GET: http://localhost:8092/api/nationalregistry/{nationalIdentifierNumber}

POST: http://localhost:8092/api/nationalregistry

DELETE: http://localhost:8092/api/nationalregistry/{nationalIdentifierNumber}


## JudicialRecords API

GET: http://localhost:8091/api/judicialrecords/

POST: http://localhost:8091/api/judicialrecords/


## CRM API

GET: http://localhost:8098/api/crm

GET: http://localhost:8098/api/crm/{nationalIdentifierNumber}

POST: http://localhost:8098/api/crm

The swagger documentation is under construction. As well as another API used to show all leads that were turned into prospects.


## Improvements to be done:

1- Create an scheduler task that will start the ScoreCalculator microservice in a scheduled time;
2- Create an extra API to display all prospects found. That API will interact with ScoreCalculator service through Kafka topic;
3- Implement service discovery and fallback and API discovery for all microservices;
4- Finish the open API swagger documentation;
5- Deploy all microservices to the cloud;


