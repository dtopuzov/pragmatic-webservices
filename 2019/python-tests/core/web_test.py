# pylint: disable=no-member
import os
import unittest

from core.chrome import Chrome


# noinspection PyUnresolvedReferences
class WebTest(unittest.TestCase):
    browser = None

    @classmethod
    def setUpClass(cls):
        cls.browser = Chrome()

    def setUp(self):
        pass

    def tearDown(self):
        # Get test method name
        test_method_name = self._testMethodName

        # Get outcome
        if hasattr(self, '_outcome'):  # Python 3.4+
            result = self.defaultTestResult()  # these 2 methods have no side effects
            self._feedErrorsToResult(result, self._outcome.errors)
        else:  # Python 3.2 - 3.3 or 3.0 - 3.1 and 2.7
            result = getattr(self, '_outcomeForDoCleanups', self._resultForDoCleanups)

        # Take screen on test fail
        if result.errors or result.failures:
            core_package_path = os.path.dirname(os.path.abspath(__file__))
            project_root = os.path.split(core_package_path)[0]
            png = "{0}.png".format(test_method_name)
            self.browser.driver.get_screenshot_as_file(os.path.join(project_root, 'out', png))

    @classmethod
    def tearDownClass(cls):
        cls.browser.kill()


if __name__ == '__main__':
    unittest.main()
