import os

import requests
from selenium.webdriver.common.by import By

from core.web_test import WebTest


class TestGitHub(WebTest):
    API_URL = "https://api.github.com/repos/ws-test-user/test/issues/1"
    WEB_URL = "https://github.com/ws-test-user/test/issues/1"

    def tearDown(self):
        project_root = os.path.dirname(os.path.abspath(__file__))
        png = "{0}.png".format(self._testMethodName)
        self.browser.driver.get_screenshot_as_png(os.path.join(project_root, 'out', png))

    def test_issue_details(self):
        """
        Verify issues details page shows correct data.
        """
        response = requests.get(self.API_URL)
        assert response.status_code == 200, "Failed to get data from API."
        response_body = response.json()

        api_title = response_body.get("title")
        api_author = response_body.get("user").get("login")
        api_state = response_body.get("state")

        self.browser.open(self.WEB_URL)
        web_title = self.browser.driver.find_element(By.CLASS_NAME, "js-issue-title").text
        web_author = self.browser.driver.find_element(By.CLASS_NAME, "author").text
        web_state = self.browser.driver.find_element(By.CLASS_NAME, "State").text

        self.assertEqual(api_title, web_title, "Title is not correct!")
        self.assertEqual(api_author, web_author, "Author is not correct!")
        self.assertEqual(api_state.lower(), web_state.lower(), "State is wrong!")
