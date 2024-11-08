# USE CASE: <1> <Country Report>

## CHARACTERISTIC INFORMATION

### Goal in Context

As a data analyst, I want a report that lists all countries with relevant details so that I can analyse country population data.

### Scope

Global Population Database

### Level

Primary task

### Preconditions

The database contains population data.

### Success End Condition

A report is generated listing all countries with code, name, continent, region, population, and capital city information.

### Failed End Condition

No report is generated.

### Primary Actor

Data Analyst

### Trigger

A request is made to generate a country report.

## MAIN SUCCESS SCENARIO

1.The data analyst requests a report of all countries.
2.The system retrieves data on all countries from the database, including each country's code, name, continent, region, population, and capital.
3.The system sorts the countries by population in descending order.
4.The system generates a report, displaying the required columns for each country.
5.The report is available for the analyst to view or export.

## EXTENSIONS

2. **Database Connection Issue**
    If the system cannot connect to the database:
    The system alerts the data analyst of the connection error.
    The analyst retries the report generation once the database is accessible.
4. **Incomplete Country Data**
    If data for a country is missing, the system leaves a note in the report.

## SUB-VARIATIONS

N/A

## SCHEDULE

**DUE DATE**: 6 December 2024
