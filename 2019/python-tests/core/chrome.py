from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager


class Chrome(object):
    driver = None
    implicitly_wait = None

    def __init__(self, implicitly_wait=30):
        """
        Init browser.
        :param implicitly_wait: Implicit wait in seconds.
        """
        self.driver = webdriver.Chrome(executable_path=ChromeDriverManager().install())
        self.driver.implicitly_wait(implicitly_wait)
        self.driver.maximize_window()

    def open(self, url):
        """
        Open url.
        :param url: url.
        """
        self.driver.get(url)

    def kill(self):
        """
        Stop browser.
        """
        if self.driver is not None:
            self.driver.quit()
