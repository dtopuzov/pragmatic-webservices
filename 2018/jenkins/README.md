# Jenkins

## About Jenkins

Web: https://jenkins.io/

Docs: https://jenkins.io/doc/

Tutorial:
https://www.tutorialspoint.com/jenkins/

## Installation

### Windows 

TODO

### Linux

TODO

### macOS
Install Homebrew:
```bash
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```
Install Jenkins:
```
brew install jenkins
```
Start it manually:
```bash
jenkins
```
Auto-start it as service:
```bash
brew services start jenkins
```

## Initial Setup

### Start Jenkins 

For example with `jenkins` command or `java -jar jenkins.war`.

### Get Password

When you start it for first time you will see somethings like this in console:
```bash
Jenkins initial setup is required. An admin user has been created and a password generated.
Please use the following password to proceed to installation:

a0bb2a50cfe43979be07e9as8d52c69f

This may also be found at: /Users/dtopuzov/.jenkins/secrets/initialAdminPassword
```     

Copy the password, you will need it in next step.

### Open Jenkins in Browser and Login 

1. Open `http://localhost:8080` in your browser.
2. You will see `Unlock Jenkins` screen where you should enter the password from previous step.
3. On `Customize Jenkins` you can do default installation or specify plugins, see next step for popular plugins.

Popular plugins:
- [Folders](https://plugins.jenkins.io/cloudbees-folder)
- [Build Timeout](https://plugins.jenkins.io/build-timeout)
- [Timestamper](https://plugins.jenkins.io/timestamper)
- [Workspace Cleanup](https://plugins.jenkins.io/ws-cleanup)
- [Gradle](https://plugins.jenkins.io/gradle)
- [HTML Publisher](https://plugins.jenkins.io/htmlpublisher)
- [https://plugins.jenkins.io/junit](https://plugins.jenkins.io/junit)
- [Parameterized Trigger](https://plugins.jenkins.io/parameterized-trigger)
- [Git](https://plugins.jenkins.io/git)
- [GitHub](https://plugins.jenkins.io/github)
- [Active Directory](https://plugins.jenkins.io/active-directory)
- [Email Extension](https://plugins.jenkins.io/email-ext)
- [Mailer](https://plugins.jenkins.io/mailer)

## Usage

Now initial setup is complete and we are ready to use our Jenkins.

### Install Additional Plugins

Jenkins Home -> Manage Jenkins -> Manage Plugins

Then you can find and install [Green Balls](https://plugins.jenkins.io/greenballs) plugin to make blue balls green.

### Create Jobs

TODO