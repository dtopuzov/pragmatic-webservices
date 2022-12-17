# GiHub Tests

## About

Example how we can test UI and APIs with C#.

## Used Technologies

NUnit
Microsoft.Net.Http
Newtonsoft.Json
Selenium.WebDriver
WebDriverManager

## Before Run

Configuration for GitHub user, repo and password is stored in `appsettings.live.json`.
Please edit the file before running the project.

## Build and Run

```bash
dotnet restore
dotnet build --no-restore
dotnet test --no-build --verbosity normal
```

## Resources

[NUnit & Selenium](https://www.youtube.com/watch?v=Dj3TW0wQuz4&list=PLZMWkkQEwOPkg_-aMxUHDUp5DF_zQ5xxK&ab_channel=LambdaTest) video tutorials.

[Automate The Planet](https://automatetheplanet.com/) blog post.

[Generate c# objects from json](https://quicktype.io/)
