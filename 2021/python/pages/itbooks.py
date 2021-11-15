"""
https://itbook.store/ home page abstraction.
"""
from selenium import webdriver
from selenium.webdriver import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait


class ItBooksHome:
    # URL
    URL = "https://itbook.store/"

    # Locators
    SEARCH_BOX = (By.ID, "q")

    # Initializer
    def __init__(self, browser: webdriver):
        self.browser = browser
        self.wait = WebDriverWait(self.browser, 10)
        self.browser.get(self.URL)

    # Interaction Methods
    def search(self, title: str) -> None:
        element = self.wait.until(lambda b: b.find_element(*self.SEARCH_BOX))
        element.clear()
        element.send_keys(title)
        element.send_keys(Keys.ENTER)

    def is_found(self, title: str) -> bool:
        selector = (By.CSS_SELECTOR, "a[title='" + title + "']")
        return len(self.browser.find_elements(*selector)) > 0
