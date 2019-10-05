import unittest

from core.chrome import Chrome


class WebTest(unittest.TestCase):
    browser = None

    @classmethod
    def setUpClass(cls):
        cls.browser = Chrome()

    def setUp(self):
        print()

    def tearDown(self):
        print()

    @classmethod
    def tearDownClass(cls):
        cls.browser.kill()


if __name__ == '__main__':
    unittest.main()
