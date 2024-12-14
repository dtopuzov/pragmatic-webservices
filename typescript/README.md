# Web and API Tests Demos

## Used Technologies

- [Jest](https://jestjs.io/) as unit testing framework and runner.
- [SuperTest](https://www.npmjs.com/package/supertest) to interact with REST APIs.
- [Selenium](https://www.npmjs.com/package/selenium-webdriver) to drive browsers.
- [TypeScript](https://www.typescriptlang.org/) to get autocompletion and type safety.
- [ESLint](https://github.com/eslint/eslint) to enforce code consistency.

## Required Setup

Install [NodeJS](https://nodejs.org/).

Setup following environment variables:

```bash
export GITHUB_USER=<your github user name>
export GITHUB_REPO=<name of test repository owned by user above>
export GITHUB_TOKEN=<api key with read/write permissions for the repo above>
```

## Execute Tests

First install all the dependencies:

```bash
npm ci
```

Run Tests:

```bash
npm run test
```

## Lint

```bash
npm run lint
```

Notes:

- ESLint can be also integrated in VS Code via [plugin](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint).
- [EditorConfig for VS Code](https://marketplace.visualstudio.com/items?itemName=EditorConfig.EditorConfig) allow the IDE to auto format files and avoid some ESLint warnings and errors.
- [Code Spell Checker](https://marketplace.visualstudio.com/items?itemName=streetsidesoftware.code-spell-checker) might be useful as well.
