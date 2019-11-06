import os
import unittest

from core.chrome import Chrome


class WebTest(unittest.TestCase):
    browser = None

    @classmethod
    def setUpClass(cls):
        cls.browser = Chrome()

    def setUp(self):
        pass

    def tearDown(self):
        project_root = os.path.dirname(os.path.abspath(__file__))
        png = "{0}.png".format(self._testMethodName)
        self.browser.driver.get_screenshot_as_file(os.path.join(project_root, 'out', png))

    @classmethod
    def tearDownClass(cls):
        cls.browser.kill()


if __name__ == '__main__':
    unittest.main()
