"""
Tests for ItBooks search feature.
"""
from pages.itbooks import ItBooksHome


def test_search(browser):
    home = ItBooksHome(browser)
    home.search("WebDriver")
    assert home.is_found("Selenium WebDriver Quick Start Guide")
    assert not home.is_found("My random book")
