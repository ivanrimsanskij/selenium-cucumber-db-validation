Feature: some feature

  Scenario: Retrieve and store people to DB
    Given I request 565 some amount people from randomuser.me
    When I store their to "DataBase" DB
    Then DB entry "Base" count increases 45