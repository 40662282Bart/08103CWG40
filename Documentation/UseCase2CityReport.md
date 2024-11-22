# USE CASE: <2> <City Report>

## CHARACTERISTIC INFORMATION

### Goal in Context

As a data analyst, I want a report that lists all cities along with their respective details so that I can analyse city population data.

### Scope

Global Population Database

### Level

Primary task

### Preconditions

The database contains population data of cities.

### Success End Condition

A report is generated listing all cities with Name, Country, District's population information.

### Failed End Condition

No report is generated.

### Primary Actor

Data Analyst

### Trigger

A request is made to generate a City report.

## MAIN SUCCESS SCENARIO

1.The data analyst requests a report of all cities.

2.The system retrieves data on all cities from the database, including each city’s name, country, district, and population.

3.The system sorts the cities by population in descending order.

4.The system generates a report, displaying the required columns for each city.

5.The report is available for the analyst to view or export.


## EXTENSIONS

2. **Database Connection Issue**
    If the system cannot connect to the database:
    The system alerts the data analyst of the connection error.
    The analyst retries the report generation once the database is accessible.
4. **Incomplete City Data**
    If data for a city is missing, the system leaves a note in the report.

## SUB-VARIATIONS

N/A

## SCHEDULE

**DUE DATE**: 6 December 2024
