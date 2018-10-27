# API and UI Tests with C#

## NuGet Packages in this solutions

Microsoft.Net.Http
Newtonsoft.Json
Selenium.WebDriver
Selenium.Chrome.WebDriver
Selenium.Support

## Before Run

Configuration for GitHub user, repo and password is stored in `App.config`.
Please edit the file before running the project.

## Build and Run

When yhou first open the project on new machine you may need to clean and rebuild.
Build -> Clean
Build -> Rebuild Solution

## Troubleshoot HTTP Trafic

Enable proxy and capture trafic.

Enable proxy in VS 2017 Community Edition:
- Open `%ProgramFiles(x86)%\Microsoft Visual Studio\2017\community\Common7\IDE`
- Find `devenv.exe.config`
- In the configuration file, find the <system.net> block, and then add this code:
```
<defaultProxy enabled="true" useDefaultCredentials="true">
    <proxy bypassonlocal="True" proxyaddress=" HYPERLINK "http://<yourproxy:port#>" http://<yourproxy:port#>"/>
</defaultProxy>
```

## HowTos

[Hooks in MsTest](https://stackoverflow.com/questions/2382552/is-it-possible-to-execute-code-once-before-all-tests-run)

[Generate c# objects from json](http://json2csharp.com/)

[Group and Run Automated Tests Using Test Categories](https://msdn.microsoft.com/en-us/library/dd286683.aspx)

[Most Complete Selenium WebDriver C# Cheat Sheet](https://automatetheplanet.com/selenium-webdriver-csharp-cheat-sheet/)

## Selenium Tutorials

http://toolsqa.com/selenium-c-sharp/

## Video Tutorials

[C# for Automation Testing](https://www.youtube.com/playlist?list=PL6tu16kXT9Pp3NFZgLbPZXEykeGQwxGSx)
[BDD and Specflow](https://www.youtube.com/watch?v=tfq9FlkyaUM&index=1&list=PL6tu16kXT9Pp3wrsaYyNRnK1QkvVv6qdI)
[Automated Build+Deploy+Test with TFS and Selenium](https://www.youtube.com/playlist?list=PL6tu16kXT9PrnirBYc9kyUWM3ODffrwDt)

## Blogs

*Automate The Planet*
https://automatetheplanet.com/