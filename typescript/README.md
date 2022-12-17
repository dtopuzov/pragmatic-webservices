# Web and API Tests

## Used Technologies

- [Jest](https://jestjs.io/) as unit testing framework and runner.
- [SuperTest](https://www.npmjs.com/package/supertest) to interact with REST APIs.
- [Selenium](https://www.npmjs.com/package/selenium-webdriver) to drive browsers.
- [TypeScript](https://www.typescriptlang.org/) to get autocompletion and type safety.
- [ESLint](https://github.com/eslint/eslint) to enforce code consistency.

## Execute Tests

First install all the dependencies:

```bash
npm ci
```

API Tests:

```bash
npm run test:api
```

Web Tests:

```bash
npm run test:web
```

## Lint

```bash
npm run lint
```

Notes:

- ESLint can be also integrated in VS Code via [plugin](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint).
- [EditorConfig for VS Code](https://marketplace.visualstudio.com/items?itemName=EditorConfig.EditorConfig) allow the IDE to auto format files and avoid some ESLint warnings and errors.
- Spell check is not enforced with rules, but [Code Spell Checker](https://marketplace.visualstudio.com/items?itemName=streetsidesoftware.code-spell-checker) might be useful.
