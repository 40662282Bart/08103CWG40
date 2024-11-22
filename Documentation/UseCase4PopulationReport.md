# USE CASE: <4> <Population Report>

## CHARACTERISTIC INFORMATION

### Goal in Context

As a data analyst, I want a report showing the total population, urban population (in cities), and rural population (not in cities) for each continent, region, and country so that I can analyse population distribution.

### Scope

Global Population Database

### Level

Primary task

### Preconditions

The database contains population data.

### Success End Condition

A report is generated showing the total population, urban population (with percentage), and rural population (with percentage) for each continent, region, and country.

### Failed End Condition

No report is generated.

### Primary Actor

Data Analyst

### Trigger

A request is made to generate a population report.

## MAIN SUCCESS SCENARIO

1.The data analyst requests a population report.

2.The system retrieves population data for each continent, region, and country, including city populations (urban) and non-city populations (rural).

3.The system calculates the total, urban, and rural populations, along with the percentages of urban and rural populations.

4.The system generates a report showing the name of each continent, region, and country with their total, urban (with %), and rural (with %) populations.

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
