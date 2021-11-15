"""
Tests for ItBooks search feature.
"""
import requests


def test_search_should_always_return_200():
    response = requests.get("https://api.itbook.store/1.0/search/fakeBook")
    assert response.status_code == 200


def test_search_existing_book():
    response = requests.get("https://api.itbook.store/1.0/search/selenium")
    response_body = response.json()
    assert response_body["error"] == "0"
    assert int(response_body["total"]) > 10
    assert "Selenium" in response_body["books"][0]["title"]
