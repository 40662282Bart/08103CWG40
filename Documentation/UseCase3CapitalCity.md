# USE CASE: <3> <Capital City Report>

## CHARACTERISTIC INFORMATION

### Goal in Context

As a Disaster Response Manager, I want a report listing all capital cities with relevant details so that I can identify heavily populated capitals for resource allocation.

### Scope

Global Population Database

### Level

Primary task

### Preconditions

The database contains population data.

### Success End Condition

A report is generated listing all Capital Cities with Name, Country and population information.

### Failed End Condition

No report is generated.

### Primary Actor

Disaster Response Manager

### Trigger

A request is made to generate a Capital City report.

## MAIN SUCCESS SCENARIO

1.The Manager requests a report of all Capital Cities.

2.The system retrieves data on all Capital Cities from the database, including name, country and population.

3.The system sorts the Capital Cities by population in descending order.

4.The system generates a report, displaying the required columns for each Capital City.

5.The report is available for the manager to view or export.

## EXTENSIONS

2. **Database Connection Issue**
    If the system cannot connect to the database:
    The system alerts the data analyst of the connection error.
    The Manager retries the report generation once the database is accessible.
4. **Incomplete Capital City Data**
    If data for a country is missing, the system leaves a note in the report.

## SUB-VARIATIONS

N/A

## SCHEDULE

**DUE DATE**: 6 December 2024
