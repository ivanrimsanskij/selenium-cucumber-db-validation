Feature: Sm ft

  @dbHw @database
  Scenario: Requesting iphones from Allo.ua and post to DateBase
  Given I request 3 iphones from Allo.ua
  When I store them to DB
    Then I verify that iphones are correctly stored in DB



