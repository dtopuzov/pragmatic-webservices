## About

Example how we can test UI and APIs with C#.

## Used Technologies

- NUnit
- Microsoft.Net.Http
- Newtonsoft.Json
- Selenium.WebDriver

## Before Run

Install [.NET SDK 8](https://dotnet.microsoft.com/en-us/download/dotnet/8.0).

Setup following environment variables:

```bash
GITHUB_USER=<your github user name>
GITHUB_REPO=<name of test repository owned by user above>
GITHUB_TOKEN=<api key with read/write permissions for the repo above>
```

## Build and Run

```bash
dotnet test --verbosity normal
```

## Resources

[NUnit & Selenium](https://www.youtube.com/watch?v=Dj3TW0wQuz4&list=PLZMWkkQEwOPkg_-aMxUHDUp5DF_zQ5xxK&ab_channel=LambdaTest) video tutorials.

[Automate The Planet](https://automatetheplanet.com/) blog post.

[Generate c# objects from json](https://quicktype.io/)
