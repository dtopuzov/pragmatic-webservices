"""
Pytest fixture for web tests.
"""
import os
import pytest
from selenium import webdriver


@pytest.fixture(scope="session")
def browser() -> webdriver:
    # Initialize the WebDriver instance
    headless = os.getenv("HEADLESS", default="False")
    options = webdriver.ChromeOptions()
    if headless.lower() == "true":
        options.add_argument("--headless=new")
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-dev-shm-usage")
    options.add_argument("--disable-gpu")
    options.add_argument("--window-size=1366,768")
    options.add_argument('log-level=3')

    # Start new browser instance
    driver = webdriver.Chrome(options=options)

    # Return the WebDriver instance for the setup
    yield driver

    # Close browser
    driver.quit()
