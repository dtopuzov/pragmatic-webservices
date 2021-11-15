# Python Demos

## About 

Sample web and api tests against [itbook.store](https://itbook.store/).

Technologies:
- [pytest](https://pypi.org/project/pytest/) as unit testing framework.
- [pytest-xdist](https://pypi.org/project/pytest-xdist/) to run tests in parallel.
- [requests](https://pypi.org/project/requests/) to perform api calls.
- [selenium](https://pypi.org/project/selenium/) to drive browsers.
- [webdriver-manager](https://pypi.org/project/webdriver-manager/) to handle browser drivers.

## Requirements

- [Python](https://www.python.org/downloads/)
- [pipenv](https://pypi.org/project/pipenv/)

## Run Tests

First before run any test you should install dependencies:
```bash
pipenv install
```

Run Tests:
```bash
pipenv run pytest ./tests -s -n 2 --junitxml=results.xml
```
- Number of parallel threads is controlled by `-n <number>` option.
- Optionally you can set environment variable `HEADLESS` (default value is `False`)