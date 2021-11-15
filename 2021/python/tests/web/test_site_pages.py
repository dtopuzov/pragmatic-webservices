"""
Some fake tests.
The purpose is just to have more than one class with test
in order to demonstrate parallel test execution.
"""


def test_privacy_policy(browser):
    url = "https://itbook.store/privacypolicy"
    browser.get(url)
    assert browser.current_url == url


def test_free_books_page(browser):
    url = "https://itbook.store/books/free"
    browser.get(url)
    assert browser.current_url == url


def test_api_page(browser):
    url = "https://api.itbook.store/"
    browser.get(url)
    assert browser.current_url == url
