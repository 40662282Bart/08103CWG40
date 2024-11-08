# USE CASE: <5> <Language Subfunction>

## CHARACTERISTIC INFORMATION

### Goal in Context

As a localization engineer, I want a report on the number of speakers of specified languages, including the percentage of the world population, so that I can evaluate language support needs.

### Scope

Global Population Database

### Level

Subfunction

### Preconditions

The database contains population data.

### Success End Condition

A report is generated showing the number of speakers for each specified language, along with their percentage of the world population.

### Failed End Condition

No report is generated.

### Primary Actor

Localization Engineer

### Trigger

A request is made to view language data.

## MAIN SUCCESS SCENARIO

1.The localization engineer selects the languages to be included in the report (Chinese, English, Hindi, Spanish, Arabic).

2.The system validates the selected languages and retrieves speaker population data for each from the database.

3.The system calculates the total number of speakers for each language across all relevant countries.

4.The system calculates the percentage of the world population for each language.

5.The system generates a report showing each language, the total number of speakers, and their percentage of the global population.

6.The report is available for the localization engineer to view or export for further analysis.

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
