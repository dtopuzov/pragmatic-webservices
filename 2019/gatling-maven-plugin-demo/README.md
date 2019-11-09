gatling-maven-plugin-demo
=========================

Simple showcase of a maven project using the gatling-maven-plugin.

To test it out, simply execute the following command:

    $mvn gatling:test -Dgatling.simulationClass=computerdatabase.BasicSimulation

or simply:

    $mvn gatling:test

GitHub API Demos
================
Create repo called `test` in your account.

Set environment variables with your GitHub user and password and run tests:
```
export USER=<user>
export PASS=<pass>
mvn gatling:test -Dgatling.simulationClass=github.IssuesAPI
```