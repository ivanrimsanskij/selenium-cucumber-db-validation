# Selenium + Cucumber + DB Validation

End-to-end UI test automation framework that scrapes product data from a live website and validates that it is correctly persisted to a relational database.

The single scenario:
1. Opens allo.ua, searches for iphone, and scrapes the code and name of the first 3 products (Selenium).
2. Stores the scraped data into a MySQL table (`@When I store them to DB`).
3. Reads the data back and asserts it matches what was scraped from the site (`@Then I verify ...`).
4. Truncates the table after the scenario (`@After("@database")`).

## Tech stack

| Area | Tool |
|------|------|
| Language | Java 21 |
| Build | Maven |
| UI automation | Selenium 4.44 (Chrome, headless) |
| BDD | Cucumber 7 + Gherkin |
| Test runner | TestNG (`AbstractTestNGCucumberTests`) |
| Database | MySQL (Connector/J 9) |
| Reporting | Allure |
| Logging | SLF4J + Logback |
| Boilerplate | Lombok |

## Prerequisites

- JDK 21
- Maven 3.9+
- Google Chrome installed (the matching driver is downloaded automatically by Selenium Manager — no manual setup)
- MySQL server running locally on `localhost:3306`

> The JDBC URL is currently hardcoded in
> [`CucumberRunnerTest.java`](src/test/java/org/prog/automation/CucumberRunnerTest.java) as
> jdbc:mysql://localhost:3306/iphones. Containerization is intentionally out of scope for this
> project, so you must have a local MySQL instance. If your host/port/schema differ, edit that line.

## Database setup

Create the schema and the table the test expects:


CREATE DATABASE IF NOT EXISTS iphones;
USE iphones;

CREATE TABLE IF NOT EXISTS iphones (
    Code VARCHAR(255),
    Name VARCHAR(255)
);


## Configuration (credentials)

DB credentials are not stored in the repo — they are read from environment variables at runtime:

| Variable | Description |
|----------|-------------|
| Db_User | MySQL username |
| Db_Password | MySQL password |

Set them before running (note the exact capitalization):

macOS / Linux

export Db_User="root"
export Db_Password="your_password"


Windows (PowerShell)

$env:Db_User="root"
$env:Db_Password="your_password"


## Run the tests


mvn clean test


The suite runs the scenarios tagged @dbHw. Chrome runs headless, so no browser window opens.

## Allure report


mvn allure:serve


Opens the generated HTML report in your browser.

## Project structure

```test
src/test
├── java/org/prog/automation
│   ├── AlloPage.java            -> Page Object: allo.ua search + product scraping
│   ├── Selenium.java            -> Step defs: @Given request N iphones
│   ├── Db.java                  -> Step defs: @When store / @Then verify in DB
│   ├── Hooks.java               -> @After: truncates the table
│   ├── DataManager.java         -> Shared state between steps
│   └── CucumberRunnerTest.java  -> TestNG runner + suite setup (driver & DB)
└── resources
    ├── features/automation.feature
    └── testng.xml
```


## Notes

- The test depends on the live allo.ua markup; if the site changes its layout, the
  CSS/ID locators in AlloPage.java may need updating.
- Built as a learning/portfolio project to demonstrate combining UI automation with
  database-level validation in a single BDD flow.
