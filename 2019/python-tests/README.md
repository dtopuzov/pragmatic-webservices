## Web & API Integration Tests Demo

### About
Demo project to show how we can test both web and rest api with same solution.

### Tutorials
[Testing in Python](https://realpython.com/python-testing/)

### Libraries

[requests](https://realpython.com/python-requests/) is used for rest api calls.

[selenium](https://selenium-python.readthedocs.io/) is used to drive the browser.

### Execute Tests

Install requirements:
```bash
pip3 install --upgrade -r requirements.txt --user
```

Run lint to check for styling issues:
```bash
python3 -m pylint core tests --disable=missing-docstring
```

Run tests:
```bash
python3 -m unittest discover -s tests
```