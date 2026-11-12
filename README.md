# OrangeHRM – Web UI Automation Framework (Java + Selenium)

Read-only UI automation suite for the public OrangeHRM demo site:
`https://opensource-demo.orangehrmlive.com`

This README explains **how to run** the suite. See `FRAMEWORK_PLAN.md` for how it is built and why.

## Prerequisites

* Java 17+
* Maven 3.6+
* Google Chrome (or Firefox) installed - Selenium Manager downloads the matching driver automatically

## Run the full suite

```bash
mvn clean test
```

This runs `testng.xml` (39 test runs from 20 test methods, several data-driven from `TestData.xlsx`)
against Chrome and writes `reports/ExtentReport-chrome.html`.

## Run just the smoke tests

```bash
mvn clean test -Dsuite.file=smoke.xml
```

## Switch browser / headless / credentials

Any value in `src/test/resources/config.properties` can be overridden with `-D`:

```bash
mvn clean test -Dbrowser=firefox -Dheadless=true
```

## View the report

Open `reports/ExtentReport-<browser>.html` in a browser. Every test step (click, type, assertion) is
logged, with a screenshot on both pass and failure.

## Project layout

See `FRAMEWORK_PLAN.md` section 3 for the full structure. In short:

* `src/main/java/com/orangehrm/pages` - Page Objects (Login, Dashboard, Admin, PIM, 404, Forgot Password)
* `src/main/java/com/orangehrm/components` -- Header (top bar / user dropdown) and Sidebar (main menu),
  shared across pages
* `src/main/java/com/orangehrm/base` - `BasePage`: every UI action is a try/catch method that logs a
  report step
* `src/main/java/com/orangehrm/utils` - config, driver factory, Excel reader (POI), Extent report
  helpers, assertion helpers
* `src/test/java/com/orangehrm/tests` - test classes, one package per module (login, dashboard,
  navigation, admin, pim, negative)
* `src/test/resources/testdata/TestData.xlsx` - data for the `@DataProvider` methods (invalid logins,
  menu items, module navigation, employee search)

## Test inventory

| Module | Test IDs | Methods | Data-driven? |
|---|---|---|---|
| Login | TC_LOGIN_001 – 005 | 5 | TC_LOGIN_003 (3 rows) |
| Dashboard | TC_HOME_001 – 004 | 4 | - |
| Navigation | TC_NAV_001 – 003 | 3 | TC_NAV_002 (12 rows), TC_NAV_003 (6 rows) |
| Admin module | TC_ADM_001 – 003 | 3 | - |
| PIM module | TC_PIM_001 – 003 | 3 | TC_PIM_003 (2 rows) |
| Negative / edge cases | TC_NEG_001 – 002 | 2 | - |
| **Total** | | **20 methods -> 39 runs** | |

## Safety notes

* The only account used is the OrangeHRM demo's own public `Admin` / `admin123` login, which the
  application itself publishes for this exact purpose - no personal or real data is involved.
* No employee, user or record is created, edited or deleted. Search and Add-button tests only read the
  page; the "Add" button is checked for visibility and never clicked.
* Negative tests only check redirects and error pages - no credentials are submitted anywhere except
  the documented valid/invalid login cases.
